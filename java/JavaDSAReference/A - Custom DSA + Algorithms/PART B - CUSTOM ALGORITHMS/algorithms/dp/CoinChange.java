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

    public static void main(String[] args) {
        System.out.println("--- COIN CHANGE DEMO ---");
        
        int[] coins = {1, 2, 5};
        int amount = 11;
        
        System.out.println("Coins available: " + Arrays.toString(coins));
        System.out.println("Target Amount: " + amount);
        System.out.println("Minimum coins needed: " + minCoins(coins, amount));
        // Expected: 3 (5 + 5 + 1)
    }
}
