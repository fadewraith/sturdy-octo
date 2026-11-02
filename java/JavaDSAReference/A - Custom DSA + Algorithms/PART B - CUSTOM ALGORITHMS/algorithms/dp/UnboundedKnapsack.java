package algorithms.dp;

/**
 * UNBOUNDED KNAPSACK
 *
 * WHAT IT IS:
 * Items have a weight and a value, and you may take EACH ITEM ANY NUMBER OF TIMES.
 * Maximise the total value without exceeding the capacity.
 *
 * CRITICAL DISTINCTION (vs 0/1 Knapsack in ZeroOneKnapsack.java):
 * The 1D loop direction is the whole difference:
 *   0/1 knapsack:       for w from CAPACITY DOWN to weight  -> dp[w - weight] is still
 *                       the value WITHOUT this item, so each item is used at most once.
 *   Unbounded knapsack: for w from weight UP to CAPACITY    -> dp[w - weight] may
 *                       already include this item, so it can be reused.
 *
 * FAMILY MEMBERS (same recurrence):
 * - Rod Cutting (RodCutting.java): piece lengths are weights, prices are values.
 * - Coin Change (CoinChange.java): minimise the count instead of maximising value,
 *   or count the number of ways.
 *
 * STRATEGY:
 * dp[w] = best value with total weight at most w.
 * dp[w] = max over items i with weight[i] <= w of dp[w - weight[i]] + value[i].
 *
 * COMPLEXITY:
 * Time: O(N * W) where W is the capacity.
 * Space: O(W)
 */
public class UnboundedKnapsack {

    public static long maxValue(int[] weights, int[] values, int capacity) {
        long[] dp = new long[capacity + 1];
        for (int i = 0; i < weights.length; i++) {
            if (weights[i] <= 0) throw new IllegalArgumentException("weights must be positive");
            for (int w = weights[i]; w <= capacity; w++) {   // ascending -> item can repeat
                dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
            }
        }
        return dp[capacity];
    }

    /** How many copies of each item an optimal answer takes. */
    public static int[] chooseItems(int[] weights, int[] values, int capacity) {
        long[] dp = new long[capacity + 1];
        int[] lastItem = new int[capacity + 1]; // item added last to reach dp[w], -1 = none
        java.util.Arrays.fill(lastItem, -1);
        for (int w = 1; w <= capacity; w++) {
            dp[w] = dp[w - 1];                  // allow unused capacity
            lastItem[w] = -1;
            for (int i = 0; i < weights.length; i++) {
                if (weights[i] <= w && dp[w - weights[i]] + values[i] > dp[w]) {
                    dp[w] = dp[w - weights[i]] + values[i];
                    lastItem[w] = i;
                }
            }
        }
        int[] counts = new int[weights.length];
        int w = capacity;
        while (w > 0) {
            if (lastItem[w] == -1) w--;          // this unit of capacity was left empty
            else { counts[lastItem[w]]++; w -= weights[lastItem[w]]; }
        }
        return counts;
    }

    public static void main(String[] args) {
        System.out.println("--- UNBOUNDED KNAPSACK DEMO ---");
        int[] weights = {5, 10, 15};
        int[] values = {10, 30, 20};
        int capacity = 100;
        System.out.println("Max value: " + maxValue(weights, values, capacity)); // 300 (ten of weight 10)
        System.out.println("Copies taken: " + java.util.Arrays.toString(chooseItems(weights, values, capacity))); // [0, 10, 0]

        int[] w2 = {1, 3, 4, 5};
        int[] v2 = {10, 40, 50, 70};
        System.out.println("Capacity 8: " + maxValue(w2, v2, 8)
                + " " + java.util.Arrays.toString(chooseItems(w2, v2, 8))); // 110 = 40 + 70 (weights 3 + 5)
    }
}
