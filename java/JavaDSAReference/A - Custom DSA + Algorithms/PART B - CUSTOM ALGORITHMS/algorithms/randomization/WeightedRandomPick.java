package algorithms.randomization;

import java.util.Random;

/**
 * WEIGHTED RANDOM PICK (Prefix Sums + Binary Search, and Vose's Alias Method)
 *
 * WHAT IT IS:
 * Pick index i with probability weight[i] / totalWeight.
 *   weights [1, 3] -> index 0 a quarter of the time, index 1 three quarters.
 *
 * WHEN TO USE THIS:
 * - Load balancing by server capacity, A/B tests with uneven splits, loot tables in
 *   games, sampling words by frequency, weighted lotteries.
 *
 * STRATEGY 1 - PREFIX SUMS + BINARY SEARCH:
 * Lay the weights end to end on a number line: [1, 3] -> index 0 owns [0, 1),
 * index 1 owns [1, 4). prefix = [1, 4]. Draw r uniformly in [0, total) and find the
 * first prefix[i] > r with binary search. Build O(N), pick O(log N).
 *
 * STRATEGY 2 - VOSE'S ALIAS METHOD (O(1) per pick):
 * Scale each weight so the average is 1 (p[i] = w[i] * n / total). Build n columns
 * of height 1; each column holds AT MOST TWO indices: its own index (share prob[i])
 * and one "alias" for the rest. Built by repeatedly pairing a small item (< 1) with a
 * large one (>= 1): the small one fills its column, the large one tops it up and
 * shrinks by the same amount.
 * Pick: choose a column uniformly, then flip a biased coin: own index with
 * probability prob[col], otherwise alias[col].
 * Build O(N), pick O(1) - best when you pick millions of times.
 *
 * COMPLEXITY:
 * Prefix sums:  build O(N), pick O(log N), Space O(N).
 * Alias method: build O(N), pick O(1),     Space O(N).
 */
public class WeightedRandomPick {

    // ---------------- 1. Prefix sums + binary search ----------------
    private final long[] prefix;
    private final Random random;

    public WeightedRandomPick(int[] weights, Random random) {
        if (weights.length == 0) throw new IllegalArgumentException("no weights");
        this.prefix = new long[weights.length];
        long sum = 0;
        for (int i = 0; i < weights.length; i++) {
            if (weights[i] < 0) throw new IllegalArgumentException("negative weight");
            sum += weights[i];
            prefix[i] = sum;
        }
        if (sum == 0) throw new IllegalArgumentException("total weight is 0");
        this.random = random;
    }

    public long totalWeight() {
        return prefix[prefix.length - 1];
    }

    /** Deterministic core: the index that owns point r of [0, total). */
    public int indexFor(long r) {
        int lo = 0, hi = prefix.length - 1;
        while (lo < hi) {                 // first i with prefix[i] > r
            int mid = (lo + hi) >>> 1;
            if (prefix[mid] > r) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }

    public int pick() {
        long r = (long) (random.nextDouble() * totalWeight()); // uniform in [0, total)
        return indexFor(Math.min(r, totalWeight() - 1));
    }

    // ---------------- 2. Vose's alias method ----------------
    public static class AliasMethod {
        private final double[] prob;
        private final int[] alias;
        private final Random random;

        public AliasMethod(double[] weights, Random random) {
            int n = weights.length;
            if (n == 0) throw new IllegalArgumentException("no weights");
            double total = 0;
            for (double w : weights) {
                if (w < 0) throw new IllegalArgumentException("negative weight");
                total += w;
            }
            if (total <= 0) throw new IllegalArgumentException("total weight is 0");
            this.prob = new double[n];
            this.alias = new int[n];
            this.random = random;

            double[] scaled = new double[n];
            int[] small = new int[n], large = new int[n]; // two stacks of indices
            int ns = 0, nl = 0;
            for (int i = 0; i < n; i++) {
                scaled[i] = weights[i] * n / total;
                if (scaled[i] < 1.0) small[ns++] = i;
                else large[nl++] = i;
            }
            while (ns > 0 && nl > 0) {
                int s = small[--ns], l = large[--nl];
                prob[s] = scaled[s];               // s keeps its share of column s
                alias[s] = l;                      // l fills the rest of column s
                scaled[l] = (scaled[l] + scaled[s]) - 1.0;
                if (scaled[l] < 1.0) small[ns++] = l;
                else large[nl++] = l;
            }
            // whatever is left has (up to rounding) exactly 1.0
            while (nl > 0) { int l = large[--nl]; prob[l] = 1.0; alias[l] = l; }
            while (ns > 0) { int s = small[--ns]; prob[s] = 1.0; alias[s] = s; }
        }

        /** Exact probability that pick() returns index i (from the built table). */
        public double probabilityOf(int i) {
            double p = 0;
            for (int col = 0; col < prob.length; col++) {
                if (col == i) p += prob[col];
                if (alias[col] == i) p += 1.0 - prob[col];
            }
            return p / prob.length;
        }

        public int pick() {
            int col = random.nextInt(prob.length);
            return random.nextDouble() < prob[col] ? col : alias[col];
        }
    }

    public static void main(String[] args) {
        System.out.println("--- WEIGHTED RANDOM PICK DEMO ---");
        int[] weights = {1, 3, 6};
        Random rng = new Random(42);
        WeightedRandomPick picker = new WeightedRandomPick(weights, rng);
        System.out.println("Weights [1, 3, 6]: point 0 -> " + picker.indexFor(0) + ", point 3 -> "
                + picker.indexFor(3) + ", point 4 -> " + picker.indexFor(4)); // 0, 1, 2

        int trials = 100_000;
        int[] counts = new int[3];
        for (int i = 0; i < trials; i++) counts[picker.pick()]++;
        System.out.printf("Prefix-sum picks: %.3f %.3f %.3f (expected 0.1 0.3 0.6)%n",
                counts[0] / (double) trials, counts[1] / (double) trials, counts[2] / (double) trials);

        AliasMethod alias = new AliasMethod(new double[]{1, 3, 6}, rng);
        counts = new int[3];
        for (int i = 0; i < trials; i++) counts[alias.pick()]++;
        System.out.printf("Alias picks:      %.3f %.3f %.3f (table: %.3f %.3f %.3f)%n",
                counts[0] / (double) trials, counts[1] / (double) trials, counts[2] / (double) trials,
                alias.probabilityOf(0), alias.probabilityOf(1), alias.probabilityOf(2));
    }
}
