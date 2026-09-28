package algorithms.dp;

/**
 * ROD CUTTING PROBLEM
 * 
 * WHAT IT IS:
 * Given a rod of length N and an array of prices for each length, determine the 
 * maximum revenue obtainable by cutting up the rod and selling the pieces.
 * 
 * COMPARISON TO UNBOUNDED KNAPSACK:
 * This problem is structurally almost IDENTICAL to the Unbounded Knapsack (Coin Change). 
 * You have an infinite supply of each piece length, and you want to maximize value 
 * instead of minimizing coins.
 * 
 * STRATEGY (1D DP):
 * `dp[i]` stores the max revenue for a rod of length `i`.
 * For a rod of length `i`, we can make a first cut of length `j` (where j <= i).
 * The revenue is `price[j] + dp[i-j]`. We try all possible first cuts `j` and take the max!
 * 
 * COMPLEXITY:
 * Time: O(N^2) (outer loop for rod length, inner loop for cut length).
 * Space: O(N) for DP array.
 */
public class RodCutting {

    public static int cutRod(int[] price, int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0; // A rod of length 0 sells for 0

        // Build the table val[] in bottom up manner and return the last entry
        for (int i = 1; i <= n; i++) {
            int max_val = Integer.MIN_VALUE;
            
            // Try all possible cuts up to length i
            for (int j = 0; j < i; j++) {
                max_val = Math.max(max_val, price[j] + dp[i - j - 1]);
            }
            dp[i] = max_val;
        }

        return dp[n];
    }

    public static void main(String[] args) {
        System.out.println("--- ROD CUTTING DEMO ---");
        // Prices for lengths 1, 2, 3, 4, 5, 6, 7, 8
        int[] arr = {1, 5, 8, 9, 10, 17, 17, 20};
        int size = arr.length;
        
        System.out.println("Maximum Revenue for rod of length 8: " + cutRod(arr, size));
        // Expected: 22 (By cutting into two pieces of length 2 and 6 -> 5 + 17 = 22)
    }
}
