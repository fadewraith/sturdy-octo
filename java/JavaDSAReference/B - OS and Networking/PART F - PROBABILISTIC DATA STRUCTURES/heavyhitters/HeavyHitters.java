package probabilistic.heavyhitters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * HEAVY HITTERS / FREQUENT ITEMS IN A STREAM (Misra-Gries and Space-Saving)
 *
 * WHAT IT IS:
 * Find the items that appear more than n/k times in a stream of n items, using only
 * O(k) memory - one pass, without storing a counter for every distinct item.
 *
 * WHEN TO USE THIS:
 * - Top talkers in network traffic (DDoS detection), trending hashtags/searches,
 *   hot keys in a cache or database shard, most-viewed products.
 * - Count-Min Sketch (CountMinSketch.java) answers "how often did X appear?";
 *   these algorithms answer "WHICH items are frequent?" directly.
 *
 * 1. MISRA-GRIES (generalised Boyer-Moore majority vote, BoyerMooreMajority.java)
 *    Keep at most k-1 (item, count) pairs.
 *      - item already tracked -> count++
 *      - free slot            -> track it with count 1
 *      - no free slot         -> DECREMENT every counter (drop those that hit 0);
 *                                the new item is not stored
 *    Each decrement round "cancels" k distinct items at once, which can happen at
 *    most n/k times. So:  true - n/k <= estimate <= true   (an UNDERestimate),
 *    and every item with frequency > n/k is guaranteed to still be tracked.
 *
 * 2. SPACE-SAVING (Metwally et al.)
 *    Keep exactly k (item, count, error) entries.
 *      - item tracked -> count++
 *      - free slot    -> track with count 1, error 0
 *      - full         -> REPLACE the entry with the SMALLEST count m: the new item
 *                        takes count m + 1 and error m (it may have appeared up to
 *                        m times before while untracked)
 *    Guarantees: count - error <= true <= count (an OVERestimate, with a known
 *    bound), every item with frequency > n/k is tracked, and the reported top
 *    items are very accurate in practice.
 *
 * COMPLEXITY:
 * Misra-Gries: O(1) amortized per item (decrements are paid for by increments), O(k) space.
 * Space-Saving: O(k) per replacement here (min scan; a "stream-summary" linked
 * structure makes it O(1)), O(k) space.
 */
public class HeavyHitters {

    // ---------------- 1. Misra-Gries ----------------
    public static class MisraGries<T> {
        private final int k;
        private final Map<T, Long> counters = new HashMap<>();
        private long n = 0;

        public MisraGries(int k) {
            if (k < 2) throw new IllegalArgumentException("k must be >= 2");
            this.k = k;
        }

        public void add(T item) {
            n++;
            Long c = counters.get(item);
            if (c != null) {
                counters.put(item, c + 1);
            } else if (counters.size() < k - 1) {
                counters.put(item, 1L);
            } else {
                // cancel one occurrence of k distinct items: the k-1 tracked + the new one
                Iterator<Map.Entry<T, Long>> it = counters.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<T, Long> e = it.next();
                    if (e.getValue() == 1) it.remove();
                    else e.setValue(e.getValue() - 1);
                }
            }
        }

        /** Lower bound on the true count (true - n/k <= estimate <= true). */
        public long estimate(T item) { return counters.getOrDefault(item, 0L); }

        /** Candidates for frequency > n/k (a superset of the true heavy hitters). */
        public Map<T, Long> candidates() { return new HashMap<>(counters); }

        public long streamLength() { return n; }
    }

    // ---------------- 2. Space-Saving ----------------
    public static class SpaceSaving<T> {
        public static class Entry<T> {
            public final T item;
            public long count, error;
            Entry(T item, long count, long error) { this.item = item; this.count = count; this.error = error; }
            @Override public String toString() { return item + "=" + count + "(+-" + error + ")"; }
        }

        private final int k;
        private final Map<T, Entry<T>> entries = new HashMap<>();
        private long n = 0;

        public SpaceSaving(int k) {
            if (k < 1) throw new IllegalArgumentException("k must be >= 1");
            this.k = k;
        }

        public void add(T item) {
            n++;
            Entry<T> e = entries.get(item);
            if (e != null) {
                e.count++;
            } else if (entries.size() < k) {
                entries.put(item, new Entry<>(item, 1, 0));
            } else {
                Entry<T> min = null;
                for (Entry<T> x : entries.values()) if (min == null || x.count < min.count) min = x;
                entries.remove(min.item);
                entries.put(item, new Entry<>(item, min.count + 1, min.count)); // inherits the minimum as error
            }
        }

        /** Upper bound on the true count (0 if not tracked: then true count <= min count). */
        public long estimate(T item) { Entry<T> e = entries.get(item); return e == null ? 0 : e.count; }

        public long error(T item) { Entry<T> e = entries.get(item); return e == null ? 0 : e.error; }

        /** Tracked entries, highest count first. */
        public List<Entry<T>> top() {
            List<Entry<T>> list = new ArrayList<>(entries.values());
            list.sort((a, b) -> Long.compare(b.count, a.count));
            return list;
        }

        public long streamLength() { return n; }
    }

    public static void main(String[] args) {
        System.out.println("--- HEAVY HITTERS DEMO ---");
        // A stream of 100,000 page views: a few hot pages, a long tail of cold ones
        java.util.Random rng = new java.util.Random(9);
        String[] hot = {"/home", "/search", "/cart"};
        double[] share = {0.20, 0.12, 0.08};
        Map<String, Long> truth = new HashMap<>();
        MisraGries<String> mg = new MisraGries<>(20);
        SpaceSaving<String> ss = new SpaceSaving<>(20);
        for (int i = 0; i < 100_000; i++) {
            double r = rng.nextDouble();
            String page = r < share[0] ? hot[0] : r < share[0] + share[1] ? hot[1]
                    : r < share[0] + share[1] + share[2] ? hot[2] : "/item/" + rng.nextInt(50_000);
            truth.merge(page, 1L, Long::sum);
            mg.add(page);
            ss.add(page);
        }
        System.out.println("Using 20 counters for 100,000 views (n/k = 5,000):");
        for (String p : hot) {
            System.out.printf("  %-8s true %6d | Misra-Gries %6d (under by <= 5000) | Space-Saving %6d (over by <= %d)%n",
                    p, truth.get(p), mg.estimate(p), ss.estimate(p), ss.error(p));
        }
        System.out.println("Space-Saving top 3: " + ss.top().subList(0, 3));
    }
}
