package practiceQuestions.algomapio.dp.easy;

import java.util.HashMap;
import java.util.Map;

public class ClimbingStairs {

    /**
     * Brute Force Method
     * Understand the problem: Find the number of distinct ways to climb n stairs, taking 1 or 2 steps at a time.
     * Use recursion to compute the number of ways:
     * If n equals 1, return 1, as there is only one way (one step).
     * If n equals 2, return 2, as there are two ways (two 1-steps or one 2-step).
     * For n > 2, the number of ways is the sum of ways to climb n-1 stairs (taking 1 step) and n-2 stairs (taking 2 steps).
     * Return the recursive sum of climbStairs(n-2) and climbStairs(n-1).
     * */

    public int climbStairs(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;

        return climbStairs(n-2) + climbStairs(n-1);
    }

    /**
     * Why the Brute-Force Solution is Inefficient
     * The brute-force solution uses naive recursion, leading to:
     *
     * Time Complexity: O(2^n), as each call branches into two recursive calls, forming a binary tree of depth n.
     * Space Complexity: O(n), due to the recursion stack.
     * Performance Issue: The exponential time complexity causes significant slowdowns for large n, as many subproblems are recomputed repeatedly.
     * Top Down Method
     * Initialize Memoization Dictionary: We create a dictionary memo to store the number of ways to climb i steps for specific values of i. It is initialized with base cases: memo[1] = 1 (there is 1 way to climb 1 step: take one 1-step) and memo[2] = 2 (there are 2 ways to climb 2 steps: two 1-steps or one 2-step).
     * Define Recursive Function: We define a helper function f(n) that computes the number of ways to climb n steps:
     * Base Case Check: If n is in memo, return the stored value memo[n]. This avoids recomputing results for previously solved subproblems.
     * Recursive Case: If n is not in memo, compute the number of ways to climb n steps by summing the ways to climb n-2 steps (followed by a 2-step) and n-1 steps (followed by a 1-step). Store the result in memo[n] as f(n-2) + f(n-1) and return it.
     * Execute and Return: Call f(n) with the input n and return the result, which represents the total number of distinct ways to climb n steps.
     * */

    public int climbStairsSolnTwo(int n) {
        Map<Integer, Integer> memo = new HashMap<>();
        memo.put(1, 1);
        memo.put(2, 2);

        class Helper {
            int f(int n) {
                if (memo.containsKey(n)) {
                    return memo.get(n);
                }
                memo.put(n, f(n-2) + f(n-1));
                return memo.get(n);
            }
        }

        Helper helper = new Helper();
        return helper.f(n);
    }

    /**
     * Bottom Up Method
     * Handle Base Cases: If n == 1, there is only 1 way to climb 1 step (take one 1-step), so return 1. If n == 2, there are 2 ways to climb 2 steps (two 1-steps or one 2-step), so return 2. These checks handle the smallest inputs directly.
     * Initialize DP Array: We create an array dp of size n to store the number of ways to climb i+1 steps at index i. Initialize dp[0] = 1 (ways to climb 1 step) and dp[1] = 2 (ways to climb 2 steps) as the base cases for the dynamic programming approach.
     * Fill DP Array: For each step i from 2 to n-1 (corresponding to 3 to n steps):
     * Compute dp[i] as the sum of dp[i-2] (ways to climb i-1 steps, followed by a 2-step) and dp[i-1] (ways to climb i steps, followed by a 1-step).
     * This reflects the fact that to reach step i+1, you can come from step i-1 (via a 2-step) or step i (via a 1-step).
     * Return Result: Return dp[n-1], which represents the number of ways to climb n steps.
     * */

    public int climbStairsSolnThree(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;

        int[] dp = new int[n];
        dp[0] = 1;
        dp[1] = 2;

        for (int i = 2; i < n; i++) {
            dp[i] = dp[i-2] + dp[i-1];
        }

        return dp[n-1];
    }

    /**
     * Optimal Solution: Constant Space
     * Remove DP Array: Instead of using a dp array of size n, the solution introduces two variables:
     * two_back: Initialized to 1, representing the number of ways to climb 1 step (equivalent to dp[0]).
     * one_back: Initialized to 2, representing the number of ways to climb 2 steps (equivalent to dp[1]).
     * These variables store the results for the previous two steps, which are sufficient to compute the next step’s value.
     * Iterative Computation with Variables: The loop from i = 2 to n-1 is retained, but instead of filling a dp array, it computes the number of ways for the current step using the variables:
     * Compute next_num = two_back + one_back, which represents the number of ways to climb i+1 steps (equivalent to dp[i] in the first solution).
     * Update the variables for the next iteration: set two_back = one_back (shift the previous step’s value) and one_back = next_num (store the current step’s value).
     * This mimics the recurrence relation dp[i] = dp[i-2] + dp[i-1] but only keeps the two values needed at each step.
     * Return Result: After the loop, one_back holds the number of ways to climb n steps (equivalent to dp[n-1]), so we return one_back.
     * */

    public int climbStairsSolnFour(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;

        int twoBack = 1;
        int oneBack = 2;

        for (int i = 2; i < n; i++) {
            int nextNum = twoBack + oneBack;
            twoBack = oneBack;
            oneBack = nextNum;
        }

        return oneBack;
    }

    /**
     * Detailed Explanation
     * Understanding the Problem: Climbing Stairs
     * You're climbing a staircase with n steps. Each move, you can climb either 1 step or 2 steps. The question is how many distinct ways there are to reach the top.
     *
     * n = 2 → 2 ways: 1 + 1 or 2
     * n = 3 → 3 ways: 1 + 1 + 1, 1 + 2 or 2 + 1
     * n = 4 → 5 ways
     * The Key Insight: Look at the Last Move
     * Every way of reaching step n ends with one final move, and there are only two options: a 1-step from step n - 1, or a 2-step from step n - 2. Those two groups never overlap, and together they cover every path. So:
     *
     * ways(n) = ways(n - 1) + ways(n - 2), with ways(1) = 1 and ways(2) = 2.
     *
     * That's the Fibonacci recurrence shifted by one place: the answers run 1, 2, 3, 5, 8, 13, and so on.
     *
     * Brute Force: Plain Recursion
     * Writing the recurrence straight into a recursive function gives the right answer, but slowly. Every call branches into two more, and the same subproblems are solved again and again: ways(n - 2) is computed once on its own and again inside ways(n - 1). The call tree roughly doubles at each level, so this takes O(2n) time and O(n) space for the call stack. It times out near the upper limit of n = 45.
     *
     * Top Down: Memoization
     * Keep the recursion, but remember every answer. Start a dictionary with the base cases, memo = {1: 1, 2: 2}, and return the stored value whenever a step has already been solved. Each step from 1 to n is now computed exactly once.
     *
     * Time: O(n). Space: O(n) for the memo and the call stack.
     *
     * Bottom Up: Tabulation
     * Turn it around and fill a table from the bottom step up, with no recursion at all. Let dp[i] be the number of ways to reach step i + 1:
     *
     * dp[0] = 1 and dp[1] = 2
     * for each i from 2 to n - 1: dp[i] = dp[i - 2] + dp[i - 1]
     * the answer is dp[n - 1]
     * Time: O(n). Space: O(n).
     *
     * Optimal: Constant Space
     * Each table entry only uses the two before it, so the rest of the table is wasted. Keep just two variables: two_back (ways to reach two steps back) and one_back (ways to reach the previous step), starting at 1 and 2. Each iteration computes two_back + one_back, then shifts both variables forward one step. When the loop ends, one_back holds the answer.
     *
     * Time: O(n). Space: O(1). This is the version to give in an interview.
     *
     * Why This Problem Matters
     * Climbing Stairs is the classic first dynamic programming problem because it shows the whole process in miniature: find a recurrence, notice the repeated subproblems, cache them, then shrink the cache to only what you need. The same "what was the last move?" reasoning carries straight into House Robber, Min Cost Climbing Stairs, Decode Ways and Coin Change.
     * */
}
