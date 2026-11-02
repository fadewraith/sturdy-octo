package algorithms.dp;

import java.util.Arrays;

/**
 * COIN CHANGE (Unbounded Knapsack)
 * 
 * WHAT IT IS:
 * Given an array of coin denominations and a total amount, find the minimum number 
 * of coins needed to make up that amount.
 * 
 * CRITICAL DISTINCTION:
 * This is the "Unbounded" Knapsack problem because you have an INFINITE supply of 
 * each coin denomination!
 * 
 * STRATEGY:
 * We use a 1D DP array `dp[i]` where `i` is the target amount, and the value is the 
 * minimum coins needed.
 * We initialize the array to `amount + 1` (acting as Infinity).
 * For each coin, we update the `dp` array for all amounts from `coin` to `target`.
 * `dp[i] = min(dp[i], dp[i - coin] + 1)`
 * 
 * COUNTING THE WAYS (Coin Change II):
 * "How many different ways make the amount?" uses the same table, with + instead of min:
 *   ways[0] = 1 (one way to make 0: take nothing),  ways[i] += ways[i - coin]
 * The LOOP ORDER decides what counts as "different":
 *   - coins OUTER, amounts INNER -> COMBINATIONS: {1,2} and {2,1} are the same way,
 *     because coins are added in a fixed order (all 1s, then all 2s, ...).
 *   - amounts OUTER, coins INNER -> PERMUTATIONS (ordered sequences): 1+2 and 2+1
 *     are different, because any coin may be the LAST one added to every amount.
 *
 * COMPLEXITY:
 * Time: O(Amount * Coins)
 * Space: O(Amount) for the 1D array.
 */
public class CoinChange {

    public static int minCoins(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        
        // Initialize with a value larger than any possible answer (acting as infinity)
        Arrays.fill(dp, amount + 1);
        
        // Base case: 0 coins needed to make amount 0
        dp[0] = 0;
        
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                // If we use this coin, the total coins needed is 1 + (coins needed for the remainder)
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
        
        // If dp[amount] is still > amount, it means it was impossible to make that sum
        return dp[amount] > amount ? -1 : dp[amount];
    }

    /** Number of COMBINATIONS of coins (order ignored) that sum to amount. */
    public static long countWays(int[] coins, int amount) {
        long[] ways = new long[amount + 1];
        ways[0] = 1;
        for (int coin : coins) {                 // coins outer -> each multiset counted once
            if (coin <= 0) throw new IllegalArgumentException("coins must be positive");
            for (int i = coin; i <= amount; i++) {
                ways[i] += ways[i - coin];
            }
        }
        return ways[amount];
    }

    /** Number of ordered SEQUENCES of coins that sum to amount (1+2 and 2+1 differ). */
    public static long countOrderedWays(int[] coins, int amount) {
        long[] ways = new long[amount + 1];
        ways[0] = 1;
        for (int i = 1; i <= amount; i++) {      // amounts outer -> any coin can come last
            for (int coin : coins) {
                if (coin <= 0) throw new IllegalArgumentException("coins must be positive");
                if (coin <= i) ways[i] += ways[i - coin];
            }
        }
        return ways[amount];
    }

    public static void main(String[] args) {
        System.out.println("--- COIN CHANGE DEMO ---");
        
        int[] coins = {1, 2, 5};
        int amount = 11;
        
        System.out.println("Coins available: " + Arrays.toString(coins));
        System.out.println("Target Amount: " + amount);
        System.out.println("Minimum coins needed: " + minCoins(coins, amount));
        // Expected: 3 (5 + 5 + 1)

        int[] small = {1, 2, 5};
        System.out.println("\nWays to make 5 from " + Arrays.toString(small) + ":");
        System.out.println("  combinations: " + countWays(small, 5));        // 4 (5, 2+2+1, 2+1+1+1, 1x5)
        System.out.println("  ordered:      " + countOrderedWays(small, 5)); // 9
    }
}
