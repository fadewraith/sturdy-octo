package distributed.crdt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * CRDTs - Conflict-free Replicated Data Types (state-based)
 *
 * WHAT IT IS:
 * Data types that replicas can update INDEPENDENTLY (even while partitioned) and
 * later MERGE in any order, any number of times, always converging to the same
 * state - no coordination, no conflicts, no consensus needed.
 *
 * WHEN TO USE THIS:
 * - Collaborative editors (Figma, Apple Notes, Automerge/Yjs), shopping carts
 *   (Amazon Dynamo paper), like/view counters, Redis Enterprise active-active,
 *   Riak data types, offline-first mobile apps.
 *
 * WHY MERGE ALWAYS WORKS:
 * The states form a semilattice and merge is the "least upper bound", which is
 *   COMMUTATIVE  merge(a, b) == merge(b, a)
 *   ASSOCIATIVE  merge(a, merge(b, c)) == merge(merge(a, b), c)
 *   IDEMPOTENT   merge(a, a) == a
 * so message reordering, duplication and delays don't matter.
 *
 * TYPES HERE:
 * 1. G-Counter (grow-only): one slot per replica; a replica only increments its own
 *    slot. value = sum of slots. merge = element-wise MAX.
 * 2. PN-Counter: two G-Counters, P (increments) and N (decrements). value = P - N.
 * 3. G-Set: grow-only set. merge = union. (Can't remove.)
 * 4. OR-Set (Observed-Remove set, "add wins"): every add gets a unique tag
 *    (replica id + counter). remove(x) deletes only the tags it has SEEN (tombstones
 *    them). An add concurrent with a remove has a tag the remover never saw, so the
 *    element survives. contains(x) = x has a tag that is not tombstoned.
 * 5. LWW-Register (last-writer-wins): keep the value with the highest
 *    (timestamp, replicaId) pair; the replica id breaks timestamp ties so every
 *    replica picks the same winner.
 *
 * COMPLEXITY:
 * Counters: O(replicas) merge, O(replicas) value (could cache).
 * Sets: O(size of tags/tombstones) merge. OR-Set tombstones grow forever unless
 * garbage-collected (real systems use causal contexts / dots to compact them).
 */
public class CRDTs {

    // ---------------- 1. G-Counter ----------------
    public static class GCounter {
        final int id;
        final long[] counts;

        public GCounter(int replicaId, int replicas) { this.id = replicaId; this.counts = new long[replicas]; }

        public void increment(long by) {
            if (by < 0) throw new IllegalArgumentException("G-Counter can only grow");
            counts[id] += by;
        }

        public long value() { long s = 0; for (long c : counts) s += c; return s; }

        public void merge(GCounter other) {
            for (int i = 0; i < counts.length; i++) counts[i] = Math.max(counts[i], other.counts[i]);
        }

        public GCounter copy() { GCounter c = new GCounter(id, counts.length); System.arraycopy(counts, 0, c.counts, 0, counts.length); return c; }
        public String state() { return Arrays.toString(counts); }
    }

    // ---------------- 2. PN-Counter ----------------
    public static class PNCounter {
        final GCounter p, n;

        public PNCounter(int replicaId, int replicas) { p = new GCounter(replicaId, replicas); n = new GCounter(replicaId, replicas); }

        public void increment(long by) { if (by >= 0) p.increment(by); else n.increment(-by); }
        public void decrement(long by) { increment(-by); }
        public long value() { return p.value() - n.value(); }
        public void merge(PNCounter other) { p.merge(other.p); n.merge(other.n); }
        public String state() { return p.state() + n.state(); }
    }

    // ---------------- 3. G-Set ----------------
    public static class GSet<T> {
        final Set<T> items = new HashSet<>();
        public void add(T x) { items.add(x); }
        public boolean contains(T x) { return items.contains(x); }
        public void merge(GSet<T> other) { items.addAll(other.items); }
        public Set<T> value() { return new HashSet<>(items); }
    }

    // ---------------- 4. OR-Set ----------------
    public static class ORSet<T> {
        final String replica;
        long counter = 0;
        final Map<T, Set<String>> adds = new HashMap<>();   // element -> all tags ever added
        final Map<T, Set<String>> removed = new HashMap<>(); // element -> tombstoned tags

        public ORSet(String replica) { this.replica = replica; }

        public void add(T x) {
            adds.computeIfAbsent(x, k -> new HashSet<>()).add(replica + ":" + (++counter)); // unique tag
        }

        /** Removes x as observed HERE: tombstones every tag of x this replica has seen. */
        public void remove(T x) {
            Set<String> seen = adds.get(x);
            if (seen != null) removed.computeIfAbsent(x, k -> new HashSet<>()).addAll(seen);
        }

        public boolean contains(T x) {
            Set<String> tags = adds.get(x);
            if (tags == null) return false;
            Set<String> dead = removed.getOrDefault(x, Set.of());
            for (String t : tags) if (!dead.contains(t)) return true;
            return false;
        }

        public void merge(ORSet<T> other) {
            other.adds.forEach((x, tags) -> adds.computeIfAbsent(x, k -> new HashSet<>()).addAll(tags));
            other.removed.forEach((x, tags) -> removed.computeIfAbsent(x, k -> new HashSet<>()).addAll(tags));
        }

        public Set<T> value() {
            Set<T> out = new HashSet<>();
            for (T x : adds.keySet()) if (contains(x)) out.add(x);
            return out;
        }

        /** Canonical state (for comparing replicas). */
        public String state() { return new TreeSet<>(adds.entrySet().stream().map(e -> e.getKey() + "=" + new TreeSet<>(e.getValue())).toList())
                + "/" + new TreeSet<>(removed.entrySet().stream().map(e -> e.getKey() + "=" + new TreeSet<>(e.getValue())).toList()); }
    }

    // ---------------- 5. LWW-Register ----------------
    public static class LWWRegister<T> {
        final int replicaId;
        T value;
        long timestamp = Long.MIN_VALUE;
        int writer = -1;

        public LWWRegister(int replicaId) { this.replicaId = replicaId; }

        public void set(T v, long timestamp) {
            if (newer(timestamp, replicaId)) { value = v; this.timestamp = timestamp; writer = replicaId; }
        }

        private boolean newer(long ts, int id) { return ts > timestamp || (ts == timestamp && id > writer); }

        public T get() { return value; }

        public void merge(LWWRegister<T> other) {
            if (newer(other.timestamp, other.writer)) { value = other.value; timestamp = other.timestamp; writer = other.writer; }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- CRDT DEMO ---");

        // Two replicas count page views while partitioned, then sync
        GCounter a = new GCounter(0, 2), b = new GCounter(1, 2);
        a.increment(3);
        b.increment(5);
        a.merge(b);
        b.merge(a);
        a.merge(b); // merging again changes nothing (idempotent)
        System.out.println("G-Counter after sync: " + a.value() + " and " + b.value()); // 8 and 8

        PNCounter likesA = new PNCounter(0, 2), likesB = new PNCounter(1, 2);
        likesA.increment(10);
        likesB.decrement(3);
        likesA.merge(likesB);
        System.out.println("PN-Counter: " + likesA.value()); // 7

        // OR-Set: Alice removes "milk" while Bob concurrently re-adds it -> add wins
        ORSet<String> alice = new ORSet<>("alice"), bob = new ORSet<>("bob");
        alice.add("milk");
        alice.add("eggs");
        bob.merge(alice);            // both now see milk and eggs
        alice.remove("milk");        // Alice removes the milk she saw
        bob.add("milk");             // Bob adds milk again (new tag Alice never saw)
        bob.remove("eggs");
        alice.merge(bob);
        bob.merge(alice);
        System.out.println("OR-Set shopping cart: alice=" + new TreeSet<>(alice.value()) + " bob=" + new TreeSet<>(bob.value()));
        // [milk] on both: Bob's concurrent add survives Alice's remove; eggs removed

        LWWRegister<String> r1 = new LWWRegister<>(1), r2 = new LWWRegister<>(2);
        r1.set("blue", 100);
        r2.set("green", 100);        // same timestamp: higher replica id wins everywhere
        r1.merge(r2);
        r2.merge(r1);
        System.out.println("LWW-Register: " + r1.get() + " / " + r2.get()); // green / green
    }
}
