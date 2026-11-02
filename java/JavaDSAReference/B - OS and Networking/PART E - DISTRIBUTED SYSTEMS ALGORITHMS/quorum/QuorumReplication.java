package distributed.quorum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * QUORUM REPLICATION (N, W, R) with versioned values and read repair
 *
 * WHAT IT IS:
 * A value is stored on N replicas. A WRITE succeeds once W replicas acknowledge it;
 * a READ asks R replicas and returns the value with the highest version.
 * Used by Dynamo, Cassandra, Riak, and (as majority quorums) by Paxos/Raft.
 *
 * THE KEY RULE:  R + W > N   ->   every read quorum OVERLAPS every write quorum.
 * Pigeonhole: R + W replicas can't all be distinct among only N, so at least one
 * replica in any read set saw the latest successful write. Picking the highest
 * version therefore returns the latest value ("strong" reads).
 * With R + W <= N a read may miss every replica that has the latest write and
 * return stale data (eventual consistency) - in exchange for lower latency.
 * Typical choices: N=3, W=2, R=2 (balanced); W=1, R=N (fast writes);
 * W=N, R=1 (fast reads, but one dead replica blocks writes).
 * Also W > N/2 makes two concurrent writes impossible to both succeed on disjoint sets.
 *
 * VERSIONS:
 * Each write gets a version from a global counter here (real systems use
 * timestamps, vector clocks or the leader's log index). A replica only accepts a
 * write whose version is newer than what it has.
 *
 * READ REPAIR:
 * After a read, the coordinator pushes the newest value to any replica in the read
 * set that returned an older version, so replicas converge over time.
 *
 * SIMULATION:
 * Replicas can be DOWN. Operations contact replicas in random order and stop once
 * they have W (or R) answers from live replicas; an operation fails if fewer than
 * W (or R) replicas are alive.
 *
 * COMPLEXITY:
 * Write: O(W) messages (O(N) contacts at most). Read: O(R) messages + repairs.
 */
public class QuorumReplication {

    static class Replica {
        String value;
        long version = 0;
        boolean up = true;
    }

    public static class Versioned {
        public final String value;
        public final long version;
        Versioned(String value, long version) { this.value = value; this.version = version; }
        @Override public String toString() { return value + "@v" + version; }
    }

    private final Replica[] replicas;
    private final int w, r;
    private final Random random;
    private long nextVersion = 1;
    private int readRepairs = 0;

    public QuorumReplication(int n, int w, int r, Random random) {
        if (w < 1 || w > n || r < 1 || r > n) throw new IllegalArgumentException("need 1 <= W, R <= N");
        this.replicas = new Replica[n];
        for (int i = 0; i < n; i++) replicas[i] = new Replica();
        this.w = w;
        this.r = r;
        this.random = random;
    }

    public boolean isStrong() { return r + w > replicas.length; }

    public void setUp(int replica, boolean up) { replicas[replica].up = up; }

    private List<Integer> liveInRandomOrder() {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < replicas.length; i++) if (replicas[i].up) ids.add(i);
        Collections.shuffle(ids, random);
        return ids;
    }

    /** Returns the version written, or -1 if fewer than W replicas are alive. */
    public long write(String value) {
        List<Integer> live = liveInRandomOrder();
        if (live.size() < w) return -1;
        long version = nextVersion++;
        for (int i = 0; i < w; i++) {              // first W live replicas acknowledge
            Replica rep = replicas[live.get(i)];
            if (version > rep.version) { rep.value = value; rep.version = version; }
        }
        return version;
    }

    /** Newest value among R live replicas (null value if never written), or null if fewer than R are alive. */
    public Versioned read() {
        List<Integer> live = liveInRandomOrder();
        if (live.size() < r) return null;
        Replica newest = null;
        List<Integer> asked = live.subList(0, r);
        for (int id : asked) {
            Replica rep = replicas[id];
            if (newest == null || rep.version > newest.version) newest = rep;
        }
        // read repair: bring stale replicas in the read set up to date
        for (int id : asked) {
            Replica rep = replicas[id];
            if (rep.version < newest.version) {
                rep.value = newest.value;
                rep.version = newest.version;
                readRepairs++;
            }
        }
        return new Versioned(newest.value, newest.version);
    }

    public int readRepairs() { return readRepairs; }

    public static void main(String[] args) {
        System.out.println("--- QUORUM REPLICATION DEMO ---");
        Random rng = new Random(7);

        for (int[] cfg : new int[][]{{3, 2, 2}, {3, 1, 1}}) {
            QuorumReplication store = new QuorumReplication(cfg[0], cfg[1], cfg[2], rng);
            int stale = 0, reads = 0;
            for (int i = 1; i <= 1000; i++) {
                long v = store.write("value-" + i);
                Versioned got = store.read();
                reads++;
                if (got.version != v) stale++;
            }
            System.out.printf("N=%d W=%d R=%d (R+W>N: %b): %d of %d reads were stale, %d read repairs%n",
                    cfg[0], cfg[1], cfg[2], store.isStrong(), stale, reads, store.readRepairs());
        }
        // N=3 W=2 R=2 -> 0 stale reads. N=3 W=1 R=1 -> roughly 2/3 stale.

        QuorumReplication store = new QuorumReplication(3, 2, 2, rng);
        store.write("x");
        store.setUp(0, false);
        System.out.println("\nOne replica down: write ok? " + (store.write("y") > 0) + ", read = " + store.read());
        store.setUp(1, false);
        System.out.println("Two replicas down: write ok? " + (store.write("z") > 0) + ", read = " + store.read());
        // true, y@v2 ; false, null (no quorum - unavailable rather than inconsistent)
    }
}
