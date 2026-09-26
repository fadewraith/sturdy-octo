package practiceQuestions.algomapio.dp.medium;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CoinChange {

    /**
     * Brute Force Method
     * Understand the problem: Find the fewest number of coins needed to make up a given amount, or return -1 if impossible.
     * If the amount is 0, return 0.
     * If the amount is negative, return -1.
     * Initialize min_cnt to -1 to track the minimum number of coins.
     * For each coin, recursively compute the number of coins needed for amount minus coin.
     * If the recursive call returns a non-negative number, update min_cnt to the minimum of min_cnt (if non-negative) and the recursive result plus 1.
     * Return min_cnt.
     * */

    public int coinChange(int[] coins, int amount) {
        if (amount == 0)
            return 0;
        else if (amount < 0)
            return -1;

        int min_cnt = -1;
        for (int coin : coins) {
            int cnt = coinChange(coins, amount - coin);
            if (cnt >= 0)
                min_cnt = min_cnt < 0 ? cnt + 1 : Math.min(min_cnt, cnt + 1);
        }
        return min_cnt;
    }

    /**
     * Why the Brute-Force Solution is Inefficient
     * The brute-force solution uses naive recursion, leading to:
     *
     * Time Complexity: O(|coins|^amount), as each amount can branch into |coins| recursive calls, up to amount depth.
     * Space Complexity: O(amount), due to the recursion stack.
     * Performance Issue: The exponential time complexity causes significant slowdowns for large amounts, as the same subproblems are recomputed repeatedly.
     * 💡 Optimal Solution 1: Top Down
     * Sort Coins: The coins array is sorted in ascending order. This allows the solution to break the loop early when a coin is too large for the remaining amount, optimizing the recursive calls.
     * Initialize Memoization Dictionary: A dictionary memo is created to store the minimum number of coins needed for specific amounts. It is initialized with the base case memo[0] = 0, as zero coins are needed to make an amount of 0.
     * Define Recursive Function: A helper function min_coins(amt) computes the minimum number of coins needed to make the amount amt:
     * Memoization Check: If amt is already in memo, return the stored value memo[amt], avoiding recomputation.
     * Compute Minimum Coins: Initialize minn to float('inf') to track the minimum number of coins. Iterate through each coin in coins:
     * Compute the remaining amount diff = amt - coin.
     * If diff < 0, break the loop, as larger coins (due to sorting) will also be too big.
     * Otherwise, recursively call min_coins(diff) to get the minimum coins needed for diff, add 1 for the current coin, and update minn with the minimum of its current value and 1 + min_coins(diff).
     * Store and Return: Store minn in memo[amt] and return it as the result for the current amount.
     * Execute and Return Result: Call min_coins(amount) to compute the minimum coins needed for the target amount. If the result is less than float('inf'), return it as the minimum number of coins. Otherwise, return -1, indicating that the amount cannot be made with the given coins.
     * */

    public int coinChange2(int[] coins, int amount) {
        Arrays.sort(coins);
        Map<Integer, Integer> memo = new HashMap<>();
        memo.put(0, 0);

        return minCoins(amount, coins, memo);
    }

    private int minCoins(int amt, int[] coins, Map<Integer, Integer> memo) {
        if (memo.containsKey(amt)) return memo.get(amt);
        int minn = Integer.MAX_VALUE;

        for (int coin : coins) {
            int diff = amt - coin;
            if (diff < 0) break;
            int res = minCoins(diff, coins, memo);
            if (res != Integer.MAX_VALUE) {
                minn = Math.min(minn, 1 + res);
            }
        }

        memo.put(amt, minn);
        return minn;
    }

    /**
     * Optimal Solution 2: Bottom Up
     * Initialize DP Array: We create an array dp of size amount + 1, where dp[i] represents the minimum number of coins needed to make amount i. Initialize all elements to Infinity to indicate that no solution has been found yet, except for dp[0] = 0, as zero coins are needed to make an amount of 0.
     * Sort Coins: Sort the coins array in ascending order. This allows us to break the inner loop early when a coin is too large to contribute to the current amount, optimizing the computation.
     * Fill DP Array: For each amount i from 1 to amount, iterate through each coin denomination in coins:
     * If the current amount i is less than the coin value (i - coin < 0), break the loop, as larger coins will also be too big (due to sorting).
     * Otherwise, update dp[i] as the minimum of its current value and dp[i - coin] + 1. This represents using the current coin (adding 1 to the coin count) plus the minimum number of coins needed for the remaining amount (i - coin).
     * Return Result: After filling the dp array, check dp[amount]. If it is still Infinity, it means no combination of coins can make the amount, so return -1. Otherwise, return dp[amount], which is the minimum number of coins needed.
     * */

    public int coinChange3(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0; // base case: 0 amount requires 0 coins
        Arrays.sort(coins);

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i - coin < 0) break;
                if (dp[i - coin] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }

    /**
     * Detailed Explanation
     * Understanding the Coin Change Problem
     * The Coin Change problem is a classic example of dynamic programming in action. Given an array of distinct coin denominations and a target amount, the task is to determine the minimum number of coins needed to make up that amount. If the amount cannot be formed with any combination of coins, return -1. This problem tests optimization under constraints and is commonly used to evaluate recursion, memoization, and bottom-up DP skills.
     *
     * Why Naive Recursion Is Inefficient
     * A brute-force approach would recursively subtract each coin denomination from the total and attempt to solve the subproblem for the remaining amount. While intuitive, this leads to an enormous number of repeated calculations and redundant branches in the recursion tree.
     *
     * Time Complexity: O(kn), where k is the number of coins and n is the amount. Each sub-amount spawns up to k recursive calls.
     * Space Complexity: O(n), due to the recursion stack depth.
     * Drawback: Fails quickly for moderately large inputs (e.g., amount > 1000) because of exponential blow-up.
     * Efficient Strategy Using Bottom-Up Dynamic Programming
     * To solve this problem optimally, we use a bottom-up DP approach that systematically builds solutions for smaller subproblems and uses them to compute the answer for larger amounts. Instead of recomputing, we store the minimum number of coins needed to form each amount from 0 to the target.
     *
     * Step-by-Step Breakdown of the Optimal DP Solution
     * First, initialize a DP array dp of size amount + 1, where each element represents the minimum number of coins needed to make up that amount. We initialize all entries with a sentinel value like Infinity, except dp[0] = 0 (base case, zero coins needed for amount 0).
     *
     * Then, iterate from 1 to the target amount. For each amount i, loop through the coin denominations. If the coin is less than or equal to i, update dp[i] as the minimum between its current value and dp[i - coin] + 1.
     *
     * Finally, return dp[amount] if it's not Infinity. If it remains Infinity, it means there's no way to make that amount with the given coins, so return -1.
     *
     * Time and Space Complexity
     * Time Complexity: O(n × k), where n is the target amount and k is the number of coin denominations.
     * Space Complexity: O(n), as we maintain a one-dimensional DP array of size amount+1.
     * Conclusion
     * The Coin Change problem elegantly demonstrates how a complex optimization problem can be efficiently solved using dynamic programming. With a bottom-up approach, you reduce redundant work and achieve scalable performance, making it ideal for large inputs and competitive programming environments. Mastering this problem builds a strong foundation for tackling other problems like Unbounded Knapsack and Minimum Path Sum.
     * */
}
