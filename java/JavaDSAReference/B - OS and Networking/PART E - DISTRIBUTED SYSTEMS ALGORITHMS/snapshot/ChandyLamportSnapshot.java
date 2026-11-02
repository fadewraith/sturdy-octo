package distributed.snapshot;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * CHANDY-LAMPORT DISTRIBUTED SNAPSHOT
 *
 * WHAT IT IS:
 * Records a CONSISTENT global state of a running distributed system - every
 * process's local state plus the messages that were "in the wire" - without
 * stopping the system. There is no global clock, so you can't just say "everyone
 * record at 12:00:00".
 *
 * WHEN TO USE THIS:
 * - Checkpointing for recovery (Apache Flink's checkpoints are a variant of this).
 * - Detecting stable properties: deadlock, termination, "is any money lost?".
 * - Debugging distributed systems.
 *
 * ASSUMPTIONS: channels are FIFO and reliable; every pair of processes is connected.
 *
 * STRATEGY (marker messages):
 * Initiator: record own state, send a MARKER on every outgoing channel, start
 * recording every incoming channel.
 * Process p receives a MARKER on channel c:
 *   - first marker p has seen: record own state; the state of channel c is EMPTY;
 *     send MARKERs on all outgoing channels; start recording all OTHER incoming
 *     channels.
 *   - otherwise: stop recording c. Its state = the messages received on c since
 *     p started recording it.
 * Done when every process has received a marker on every incoming channel.
 *
 * WHY IT'S CONSISTENT:
 * FIFO means the marker separates "before the snapshot" from "after" on every
 * channel. A message sent after the sender's snapshot travels BEHIND the marker,
 * so the receiver has already recorded its state when it arrives -> it's never
 * counted. A message sent before the sender's snapshot but received after the
 * receiver's snapshot is exactly what the channel recording captures.
 * Here: processes transfer money; a consistent snapshot must show the exact total
 * (balances + money in transit), even though transfers keep happening.
 *
 * COMPLEXITY:
 * O(N^2) marker messages for N fully-connected processes (one per channel).
 */
public class ChandyLamportSnapshot {

    static final int MARKER = -1; // a channel entry: an amount > 0, or MARKER

    public static class Result {
        public final long actualTotal;
        public final long[] recordedBalances;
        public final long[][] recordedInTransit; // [from][to] sum of money recorded on that channel
        public final boolean completed;
        Result(long actualTotal, long[] rb, long[][] rt, boolean completed) {
            this.actualTotal = actualTotal; this.recordedBalances = rb; this.recordedInTransit = rt; this.completed = completed;
        }
        public long recordedTotal() {
            long s = 0;
            for (long b : recordedBalances) s += b;
            for (long[] row : recordedInTransit) for (long x : row) s += x;
            return s;
        }
    }

    private final int n;
    private final long[] balance;
    private final ArrayDeque<Integer>[][] channel; // channel[from][to], FIFO
    private final Random random;

    // snapshot state
    private final boolean[] recorded;
    private final long[] recordedBalance;
    private final boolean[][] recording;     // recording[from][to]: "to" is recording channel from->to
    private final boolean[][] markerSeen;    // markerSeen[from][to]: marker arrived on from->to
    private final long[][] inTransit;

    @SuppressWarnings("unchecked")
    public ChandyLamportSnapshot(long[] initialBalances, Random random) {
        this.n = initialBalances.length;
        this.balance = initialBalances.clone();
        this.channel = new ArrayDeque[n][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) channel[i][j] = new ArrayDeque<>();
        this.random = random;
        this.recorded = new boolean[n];
        this.recordedBalance = new long[n];
        this.recording = new boolean[n][n];
        this.markerSeen = new boolean[n][n];
        this.inTransit = new long[n][n];
    }

    private long totalNow() {
        long s = 0;
        for (long b : balance) s += b;
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) for (int m : channel[i][j]) if (m != MARKER) s += m;
        return s;
    }

    private void recordState(int p) {
        recorded[p] = true;
        recordedBalance[p] = balance[p];
        for (int q = 0; q < n; q++) {
            if (q == p) continue;
            channel[p][q].addLast(MARKER);        // markers on every outgoing channel
            if (!markerSeen[q][p]) recording[q][p] = true; // record incoming channels not yet closed
        }
    }

    /** Random transfer from a random process with money. */
    private void sendRandomTransfer() {
        int from = random.nextInt(n);
        if (balance[from] == 0 || n < 2) return;
        int to = random.nextInt(n - 1);
        if (to >= from) to++;
        int amount = 1 + random.nextInt((int) Math.min(balance[from], 50));
        balance[from] -= amount;
        channel[from][to].addLast(amount);
    }

    /** Delivers the head of a random non-empty channel. Returns false if all are empty. */
    private boolean deliverRandom() {
        List<int[]> nonEmpty = new ArrayList<>();
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) if (!channel[i][j].isEmpty()) nonEmpty.add(new int[]{i, j});
        if (nonEmpty.isEmpty()) return false;
        int[] c = nonEmpty.get(random.nextInt(nonEmpty.size()));
        int from = c[0], to = c[1];
        int msg = channel[from][to].pollFirst();
        if (msg == MARKER) {
            markerSeen[from][to] = true;
            if (!recorded[to]) recordState(to);   // first marker: channel from->to is recorded as empty
            recording[from][to] = false;          // stop recording this channel
        } else {
            balance[to] += msg;
            if (recording[from][to]) inTransit[from][to] += msg; // in flight at snapshot time
        }
        return true;
    }

    private boolean snapshotComplete() {
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) if (i != j && !markerSeen[i][j]) return false;
        return true;
    }

    /** Runs `before` random steps, starts a snapshot at `initiator`, keeps running until it completes. */
    public Result run(int before, int initiator) {
        long total = totalNow();
        for (int s = 0; s < before; s++) {
            if (random.nextBoolean()) sendRandomTransfer(); else deliverRandom();
        }
        recordState(initiator);
        int guard = 0;
        while (!snapshotComplete() && guard++ < 10_000_000) {
            if (random.nextInt(3) == 0) sendRandomTransfer(); // the system keeps working during the snapshot
            else deliverRandom();
        }
        if (totalNow() != total) throw new IllegalStateException("simulation lost money");
        return new Result(total, recordedBalance.clone(), inTransit, snapshotComplete());
    }

    public static void main(String[] args) {
        System.out.println("--- CHANDY-LAMPORT SNAPSHOT DEMO ---");
        long[] start = {1000, 1000, 1000, 1000};
        ChandyLamportSnapshot system = new ChandyLamportSnapshot(start, new Random(11));
        Result r = system.run(200, 0);
        System.out.println("Recorded balances:   " + Arrays.toString(r.recordedBalances));
        long transit = 0;
        for (long[] row : r.recordedInTransit) for (long x : row) transit += x;
        System.out.println("Recorded in transit: " + transit);
        System.out.println("Snapshot total " + r.recordedTotal() + " == real total " + r.actualTotal
                + " ? " + (r.recordedTotal() == r.actualTotal)); // true: the cut is consistent
    }
}
