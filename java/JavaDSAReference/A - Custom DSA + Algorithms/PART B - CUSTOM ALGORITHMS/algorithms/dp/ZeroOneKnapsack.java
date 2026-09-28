package algorithms.dp;

/**
 * 0/1 KNAPSACK PROBLEM
 * 
 * WHAT IT IS:
 * Given weights and values of N items, put these items in a knapsack of capacity W 
 * to get the maximum total value.
 * 
 * CRITICAL DISTINCTION:
 * Unlike the "Fractional" Knapsack (where you can break items apart and use a Greedy 
 * approach), the "0/1" Knapsack means you must either take the whole item (1) or 
 * leave it (0). This requires Dynamic Programming!
 * 
 * STRATEGY:
 * We use a 2D array `dp[i][w]`.
 * `i` represents considering the first `i` items.
 * `w` represents the current capacity of the bag we are evaluating.
 * For every item, we have a choice:
 * 1. Exclude it: Value is `dp[i-1][w]` (same value as without it).
 * 2. Include it: Value is `val[i-1] + dp[i-1][w - weight[i-1]]` (its value + max value of the remaining capacity).
 * We take the max of these two choices!
 * 
 * COMPLEXITY:
 * Time: O(N * W) where N is number of items, W is capacity.
 * Space: O(N * W) for the 2D array (can be optimized to O(W) using a 1D array).
 */
public class ZeroOneKnapsack {

    public static int knapsack(int W, int[] wt, int[] val, int n) {
        int[][] dp = new int[n + 1][W + 1];

        // Build table dp[][] in bottom up manner
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                
                if (i == 0 || w == 0) {
                    dp[i][w] = 0; // Base case: 0 items or 0 capacity = 0 value
                } 
                else if (wt[i - 1] <= w) {
                    // Item CAN fit in the bag. Choose max of including or excluding it.
                    dp[i][w] = Math.max(
                        val[i - 1] + dp[i - 1][w - wt[i - 1]], // Include
                        dp[i - 1][w]                           // Exclude
                    );
                } 
                else {
                    // Item CANNOT fit. We must exclude it.
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        return dp[n][W];
    }

    public static void main(String[] args) {
        System.out.println("--- 0/1 KNAPSACK DEMO ---");
        int[] val = {60, 100, 120};
        int[] wt = {10, 20, 30};
        int W = 50;
        int n = val.length;
        
        System.out.println("Max Value: " + knapsack(W, wt, val, n));
        // Expected: 220 (Takes item 2 (100v, 20w) and item 3 (120v, 30w))
    }
}
