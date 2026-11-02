package db.recovery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * ARIES CRASH RECOVERY (Analysis, Redo, Undo) - simplified but faithful
 *
 * WHAT IT IS:
 * The recovery algorithm used by IBM DB2, SQL Server, and (in spirit) most
 * write-ahead-logging databases. After a crash it restores the database so that
 * every COMMITTED transaction's changes are present (durability) and every
 * uncommitted transaction's changes are gone (atomicity).
 *
 * SETTING ("STEAL / NO-FORCE" buffer management - the hardest and most common):
 * - STEAL: dirty pages of UNCOMMITTED transactions may be written to disk (to free
 *   buffer frames) -> recovery must be able to UNDO.
 * - NO-FORCE: commit does NOT write the transaction's pages to disk, only the log
 *   -> recovery must be able to REDO.
 * - WAL rule: before a page goes to disk, the log up to its pageLSN must be on disk;
 *   at commit, the log up to the COMMIT record must be on disk.
 *
 * LOG RECORDS: UPDATE (page, before-image, after-image, prevLSN of the same txn),
 * COMMIT, END, CHECKPOINT (copies of the active-transaction table ATT and dirty-page
 * table DPT), and CLR - "compensation log record", written while undoing; it
 * records the undo AND points (undoNextLSN) to the next record to undo, so undo work
 * is never repeated even if we crash again during recovery.
 * Every page stores pageLSN = LSN of the last log record applied to it.
 *
 * RECOVERY:
 * 1. ANALYSIS: start at the last checkpoint with its ATT and DPT, scan forward:
 *    rebuild which transactions were active (losers) and which pages may be dirty
 *    (recLSN = first log record that dirtied them).
 * 2. REDO - "repeat history": from the smallest recLSN, re-apply EVERY update and CLR
 *    (also those of losers) whose page might not be on disk: skip if the page isn't
 *    in the DPT, if recLSN > LSN, or if pageLSN >= LSN (already on disk).
 * 3. UNDO: roll back all losers together, always undoing the LARGEST LSN next; each
 *    undo writes a CLR. A CLR is never undone - we jump to its undoNextLSN.
 * Recovery is IDEMPOTENT: crashing during recovery and recovering again is safe.
 *
 * SIMPLIFICATIONS: each page holds one int; the checkpoint is a single record
 * (real ARIES uses begin/end checkpoint records + a master record); concurrency
 * control is assumed (strict 2PL), so two active transactions never write the same page.
 *
 * COMPLEXITY:
 * Recovery: O(log length after the checkpoint) record scans for analysis and redo,
 * O(loser records * log L) for undo with a priority queue.
 */
public class ARIESRecovery {

    public enum Type { UPDATE, COMMIT, END, CLR, CHECKPOINT }

    public record LogRecord(long lsn, int tx, Type type, long prevLSN, int page, int before, int after,
                            long undoNextLSN, Map<Integer, Long> attSnapshot, Map<Integer, Long> dptSnapshot) {}

    private final List<LogRecord> log = new ArrayList<>();
    private int flushed = 0;                                   // log[0 .. flushed-1] is durable

    private final Map<Integer, Integer> diskValue = new HashMap<>();
    private final Map<Integer, Long> diskLSN = new HashMap<>();
    private final Map<Integer, Integer> bufValue = new HashMap<>();
    private final Map<Integer, Long> bufLSN = new HashMap<>();
    private final Map<Integer, Long> dpt = new HashMap<>();   // page -> recLSN
    private final Map<Integer, Long> att = new HashMap<>();   // tx -> lastLSN

    public int redoApplied, undone, clrsWritten;

    private long append(int tx, Type type, long prev, int page, int before, int after, long undoNext,
                        Map<Integer, Long> attSnap, Map<Integer, Long> dptSnap) {
        long lsn = log.size();
        log.add(new LogRecord(lsn, tx, type, prev, page, before, after, undoNext, attSnap, dptSnap));
        return lsn;
    }

    private void flushLog(long upTo) { flushed = (int) Math.max(flushed, upTo + 1); }

    private void fetch(int page) {
        if (!bufValue.containsKey(page)) {
            bufValue.put(page, diskValue.getOrDefault(page, 0));
            bufLSN.put(page, diskLSN.getOrDefault(page, -1L));
        }
    }

    // ---------------- normal operation ----------------
    public void begin(int tx) { att.put(tx, -1L); }

    public void write(int tx, int page, int value) {
        if (!att.containsKey(tx)) throw new IllegalStateException("T" + tx + " not active");
        fetch(page);
        long lsn = append(tx, Type.UPDATE, att.get(tx), page, bufValue.get(page), value, -1, null, null);
        bufValue.put(page, value);
        bufLSN.put(page, lsn);
        dpt.putIfAbsent(page, lsn);
        att.put(tx, lsn);
    }

    public void commit(int tx) {
        long lsn = append(tx, Type.COMMIT, att.get(tx), -1, 0, 0, -1, null, null);
        flushLog(lsn);                                         // WAL: commit is durable once its record is
        append(tx, Type.END, lsn, -1, 0, 0, -1, null, null);   // END may be lost in a crash - that's fine
        att.remove(tx);
    }

    public void abort(int tx) {
        long next = att.get(tx);
        while (next != -1) {
            LogRecord r = log.get((int) next);
            if (r.type() == Type.UPDATE) { compensate(r); next = r.prevLSN(); }
            else if (r.type() == Type.CLR) next = r.undoNextLSN();
            else next = r.prevLSN();
        }
        append(tx, Type.END, att.get(tx), -1, 0, 0, -1, null, null);
        att.remove(tx);
    }

    /** Undo one UPDATE by restoring its before-image, logged as a CLR. */
    private void compensate(LogRecord r) {
        fetch(r.page());
        long clr = append(r.tx(), Type.CLR, att.get(r.tx()), r.page(), bufValue.get(r.page()), r.before(), r.prevLSN(), null, null);
        bufValue.put(r.page(), r.before());
        bufLSN.put(r.page(), clr);
        dpt.putIfAbsent(r.page(), clr);
        att.put(r.tx(), clr);
        clrsWritten++;
        undone++;
    }

    /** Writes one dirty page to disk (the buffer manager "steals" it), obeying WAL. */
    public void flushPage(int page) {
        if (!bufValue.containsKey(page)) return;
        flushLog(bufLSN.get(page));                            // WAL rule first
        diskValue.put(page, bufValue.get(page));
        diskLSN.put(page, bufLSN.get(page));
        dpt.remove(page);
    }

    public void checkpoint() {
        long lsn = append(-1, Type.CHECKPOINT, -1, -1, 0, 0, -1, new HashMap<>(att), new HashMap<>(dpt));
        flushLog(lsn);
    }

    /** Power failure: memory (buffer pool, tables, unflushed log tail) is lost. */
    public void crash() {
        while (log.size() > flushed) log.remove(log.size() - 1);
        bufValue.clear();
        bufLSN.clear();
        dpt.clear();
        att.clear();
    }

    public int read(int page) {
        if (bufValue.containsKey(page)) return bufValue.get(page);
        return diskValue.getOrDefault(page, 0);
    }

    // ---------------- recovery ----------------
    public void recover() {
        redoApplied = 0;
        undone = 0;
        // 1. ANALYSIS
        int startScan = 0;
        for (int i = log.size() - 1; i >= 0; i--) {
            if (log.get(i).type() == Type.CHECKPOINT) {
                att.putAll(log.get(i).attSnapshot());
                dpt.putAll(log.get(i).dptSnapshot());
                startScan = i + 1;
                break;
            }
        }
        Set<Integer> committed = new HashSet<>();
        for (int i = startScan; i < log.size(); i++) {
            LogRecord r = log.get(i);
            switch (r.type()) {
                case UPDATE, CLR -> { att.put(r.tx(), r.lsn()); dpt.putIfAbsent(r.page(), r.lsn()); }
                case COMMIT -> { att.put(r.tx(), r.lsn()); committed.add(r.tx()); }
                case END -> att.remove(r.tx());
                default -> { }
            }
        }

        // 2. REDO (repeat history)
        long redoStart = Long.MAX_VALUE;
        for (long rec : dpt.values()) redoStart = Math.min(redoStart, rec);
        for (long i = redoStart; i < log.size(); i++) {
            LogRecord r = log.get((int) i);
            if (r.type() != Type.UPDATE && r.type() != Type.CLR) continue;
            Long recLSN = dpt.get(r.page());
            if (recLSN == null || recLSN > r.lsn()) continue;   // page was already clean on disk
            fetch(r.page());
            if (bufLSN.get(r.page()) >= r.lsn()) continue;     // this change is already in the page
            bufValue.put(r.page(), r.after());
            bufLSN.put(r.page(), r.lsn());
            redoApplied++;
        }

        // 3. UNDO all losers, largest LSN first
        PriorityQueue<long[]> toUndo = new PriorityQueue<>((a, b) -> Long.compare(b[0], a[0]));
        for (Map.Entry<Integer, Long> e : new ArrayList<>(att.entrySet())) {
            if (committed.contains(e.getKey())) {               // committed but END was lost
                append(e.getKey(), Type.END, e.getValue(), -1, 0, 0, -1, null, null);
                att.remove(e.getKey());
            } else if (e.getValue() >= 0) {
                toUndo.add(new long[]{e.getValue(), e.getKey()});
            } else {
                att.remove(e.getKey());                          // began but wrote nothing
            }
        }
        while (!toUndo.isEmpty()) {
            long[] top = toUndo.poll();
            LogRecord r = log.get((int) top[0]);
            long next;
            if (r.type() == Type.UPDATE) { compensate(r); next = r.prevLSN(); }
            else if (r.type() == Type.CLR) next = r.undoNextLSN();  // already undone: skip ahead
            else next = r.prevLSN();
            if (next == -1) {
                append(r.tx(), Type.END, att.get(r.tx()), -1, 0, 0, -1, null, null);
                att.remove(r.tx());
            } else {
                toUndo.add(new long[]{next, r.tx()});
            }
        }
    }

    public int logSize() { return log.size(); }

    public static void main(String[] args) {
        System.out.println("--- ARIES RECOVERY DEMO ---");
        ARIESRecovery db = new ARIESRecovery();
        db.begin(1);
        db.write(1, 10, 100);                 // T1: page 10 = 100
        db.begin(2);
        db.write(2, 20, 200);                 // T2: page 20 = 200
        db.flushPage(20);                     // STEAL: T2's uncommitted change reaches disk
        db.commit(1);                         // NO-FORCE: page 10 never written to disk
        db.checkpoint();
        db.write(2, 30, 300);
        db.begin(3);
        db.write(3, 40, 400);
        db.commit(3);
        System.out.println("Before crash: page10=" + db.read(10) + " page20=" + db.read(20) + " page30=" + db.read(30) + " page40=" + db.read(40));

        db.crash();                           // T2 never committed
        System.out.println("Right after crash (disk only): page10=" + db.read(10) + " page20=" + db.read(20) + " page40=" + db.read(40));
        db.recover();
        System.out.println("After recovery: page10=" + db.read(10) + " page20=" + db.read(20) + " page30=" + db.read(30) + " page40=" + db.read(40)
                + "  (redo applied " + db.redoApplied + ", undone " + db.undone + ")");
        // page10 = 100 and page40 = 400 (committed, redone), page20 = 0 and page30 = 0 (T2 rolled back)

        db.crash();                           // crash again: recovery is idempotent
        db.recover();
        System.out.println("Crash + recover again: page10=" + db.read(10) + " page20=" + db.read(20) + " page40=" + db.read(40));
    }
}
