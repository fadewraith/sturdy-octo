package db.locking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * STRICT TWO-PHASE LOCKING (2PL) LOCK MANAGER with DEADLOCK DETECTION
 *
 * WHAT IT IS:
 * Transactions lock rows before using them:
 *   SHARED (S) lock to read  - many transactions may share it
 *   EXCLUSIVE (X) lock to write - only one, and no S locks at the same time
 *          held:  S     X
 *   want S       ok    wait
 *   want X       wait  wait
 * TWO-PHASE rule: a transaction first only ACQUIRES locks (growing phase), then only
 * RELEASES them (shrinking phase) - never acquire after the first release. This
 * guarantees CONFLICT-SERIALIZABLE schedules (as if transactions ran one by one).
 * STRICT 2PL: release everything only at commit/abort - so no one ever reads data
 * that might still be rolled back (no cascading aborts). This is what MySQL InnoDB,
 * SQL Server and DB2 do for serializable isolation.
 * (MVCC.java is the other big approach: readers never block writers.)
 *
 * DEADLOCKS:
 * T1 holds A and waits for B while T2 holds B and waits for A - neither can move.
 * Build the WAITS-FOR graph (edge Ti -> Tj if Ti waits for a lock Tj holds); a
 * CYCLE = deadlock. Resolve by aborting a VICTIM (here: the transaction whose
 * request closes the cycle) - it releases its locks and the others continue.
 * Alternatives: timeouts, or prevention schemes like wait-die / wound-wait.
 *
 * LOCK UPGRADE: a transaction holding S may ask for X; it waits until it's the only
 * holder. Two transactions upgrading the same row deadlock - a classic case.
 *
 * COMPLEXITY:
 * acquire/release: O(holders) per row. Deadlock check: O(T + E) DFS over the
 * waits-for graph for T transactions.
 */
public class TwoPhaseLocking {

    public enum Mode { SHARED, EXCLUSIVE }
    public enum Result { GRANTED, WAITING, DEADLOCK_ABORTED }

    private final Map<String, Map<Integer, Mode>> holders = new HashMap<>(); // row -> (tx -> mode)
    private final Map<Integer, String> waitingFor = new HashMap<>();         // tx -> row it waits for
    private final Map<Integer, Mode> waitingMode = new HashMap<>();
    private final Set<Integer> shrinking = new HashSet<>();                  // tx that released (2PL check)
    private final List<String> log = new ArrayList<>();

    private boolean compatible(String row, int tx, Mode mode) {
        Map<Integer, Mode> h = holders.getOrDefault(row, Map.of());
        for (Map.Entry<Integer, Mode> e : h.entrySet()) {
            if (e.getKey() == tx) continue;                                   // own locks never conflict
            if (mode == Mode.EXCLUSIVE || e.getValue() == Mode.EXCLUSIVE) return false;
        }
        return true;
    }

    /** Requests a lock. WAITING means the caller must retry later (see retryWaiting). */
    public Result acquire(int tx, String row, Mode mode) {
        if (shrinking.contains(tx)) throw new IllegalStateException("2PL violation: T" + tx + " already released locks");
        Mode held = holders.getOrDefault(row, Map.of()).get(tx);
        if (held == Mode.EXCLUSIVE || held == mode) return Result.GRANTED;    // already strong enough
        if (compatible(row, tx, mode)) {
            holders.computeIfAbsent(row, k -> new HashMap<>()).put(tx, mode);
            waitingFor.remove(tx);
            waitingMode.remove(tx);
            log.add("T" + tx + " " + (mode == Mode.SHARED ? "S" : "X") + "-lock " + row);
            return Result.GRANTED;
        }
        waitingFor.put(tx, row);
        waitingMode.put(tx, mode);
        if (inDeadlock(tx)) {
            log.add("T" + tx + " waiting for " + row + " closes a cycle -> abort T" + tx);
            abort(tx);
            return Result.DEADLOCK_ABORTED;
        }
        log.add("T" + tx + " waits for " + row);
        return Result.WAITING;
    }

    /** Who tx is waiting on: the other holders of the row it wants that conflict with it. */
    public Set<Integer> waitsFor(int tx) {
        Set<Integer> out = new LinkedHashSet<>();
        String row = waitingFor.get(tx);
        if (row == null) return out;
        Mode want = waitingMode.get(tx);
        for (Map.Entry<Integer, Mode> e : holders.getOrDefault(row, Map.of()).entrySet()) {
            if (e.getKey() != tx && (want == Mode.EXCLUSIVE || e.getValue() == Mode.EXCLUSIVE)) out.add(e.getKey());
        }
        return out;
    }

    /** DFS from tx in the waits-for graph: is tx on a cycle? */
    private boolean inDeadlock(int tx) {
        Set<Integer> seen = new HashSet<>();
        List<Integer> stack = new ArrayList<>(waitsFor(tx));
        while (!stack.isEmpty()) {
            int t = stack.remove(stack.size() - 1);
            if (t == tx) return true;
            if (seen.add(t)) stack.addAll(waitsFor(t));
        }
        return false;
    }

    /** Commit (strict 2PL): release every lock at once. */
    public void commit(int tx) { releaseAll(tx); log.add("T" + tx + " commits"); }

    public void abort(int tx) { releaseAll(tx); log.add("T" + tx + " aborted, locks released"); }

    private void releaseAll(int tx) {
        shrinking.add(tx);
        waitingFor.remove(tx);
        waitingMode.remove(tx);
        for (Map<Integer, Mode> h : holders.values()) h.remove(tx);
    }

    /** Retries every waiting request once; returns the transactions that got their lock. */
    public List<Integer> retryWaiting() {
        List<Integer> granted = new ArrayList<>();
        for (Integer tx : new ArrayList<>(waitingFor.keySet())) {
            String row = waitingFor.get(tx);
            if (row != null && compatible(row, tx, waitingMode.get(tx))) {
                Mode m = waitingMode.get(tx);
                if (acquire(tx, row, m) == Result.GRANTED) granted.add(tx);
            }
        }
        return granted;
    }

    public Map<Integer, Mode> holdersOf(String row) { return new HashMap<>(holders.getOrDefault(row, Map.of())); }
    public boolean isWaiting(int tx) { return waitingFor.containsKey(tx); }
    public List<String> log() { return log; }

    public static void main(String[] args) {
        System.out.println("--- STRICT 2PL + DEADLOCK DETECTION DEMO ---");
        TwoPhaseLocking lm = new TwoPhaseLocking();
        System.out.println("T1 S(A): " + lm.acquire(1, "A", Mode.SHARED));
        System.out.println("T2 S(A): " + lm.acquire(2, "A", Mode.SHARED) + "   (shared locks are compatible)");
        System.out.println("T3 X(A): " + lm.acquire(3, "A", Mode.EXCLUSIVE) + "   (blocked by two readers)");
        lm.commit(1);
        lm.commit(2);
        System.out.println("T1, T2 commit -> retry gives lock to " + lm.retryWaiting());
        lm.commit(3);

        System.out.println("\nClassic deadlock:");
        System.out.println("T4 X(A): " + lm.acquire(4, "A", Mode.EXCLUSIVE));
        System.out.println("T5 X(B): " + lm.acquire(5, "B", Mode.EXCLUSIVE));
        System.out.println("T4 X(B): " + lm.acquire(4, "B", Mode.EXCLUSIVE));
        System.out.println("T5 X(A): " + lm.acquire(5, "A", Mode.EXCLUSIVE) + "   (cycle T5 -> T4 -> T5)");
        System.out.println("T4 retries and gets B: " + lm.retryWaiting());
        System.out.println("\nLock manager log:");
        for (String line : lm.log()) System.out.println("  " + line);
    }
}
