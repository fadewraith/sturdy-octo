package practiceQuestions.algomapio.dp.medium;

import java.util.HashMap;
import java.util.Map;

public class HouseRobber {

    /**
     * Solution 1: Simple Recursion
     * We begin by modeling the problem recursively, considering the choice to rob or skip each house.
     *
     * Thought Process: To find the maximum money that can be robbed up to house i, we can either rob house i and add its value to the maximum from houses up to i-2 (skipping i-1), or skip house i and take the maximum from houses up to i-1. Base cases are when i = 0 (rob only the first house) or i = 1 (rob the maximum of the first two houses).
     * Development Steps:
     * Define a function helper(i) that computes the maximum money up to index i.
     * If i = 0, return nums[0].
     * If i = 1, return max(nums[0], nums[1]).
     * Otherwise, return max(nums[i] + helper(i-2), helper(i-1)).
     * Call helper(n-1), where n is the length of nums, to get the maximum for all houses.
     * Analysis:
     * Time Complexity: O(2^n) - Each call branches into two recursive calls (rob or skip), forming a binary tree with depth n, leading to approximately 2^n operations.
     * Space Complexity: O(n) - The recursion stack can grow to depth n.
     * */

    public int rob(int[] nums) {
        int n = nums.length;

        class Helper {
            int f(int i) {
                if (i == 0) return nums[0];
                if (i == 1) return Math.max(nums[0], nums[1]);
                return Math.max(nums[i] + f(i-2), f(i-1));
            }
        }

        Helper helper = new Helper();
        return helper.f(n-1);
        // Time: O(2^n)
        // Space: O(n)
    }

    /**
     * Solution 2: Top-Down Dynamic Programming (Memoization)
     * We optimize the recursive solution by caching results to eliminate redundant calculations.
     *
     * Thought Process: The recursive solution recomputes the maximum for the same indices multiple times. By storing results in a memoization dictionary, we can retrieve them in O(1) time, reducing the number of computations.
     * Development Steps:
     * Handle base cases explicitly: if n = 1, return nums[0]; if n = 2, return max(nums[0], nums[1]).
     * Initialize a dictionary memo with {0: nums[0], 1: max(nums[0], nums[1])} for the base cases.
     * Define a function helper(i) that checks if i is in memo. If so, return the cached value.
     * Otherwise, compute max(nums[i] + helper(i-2), helper(i-1)), store it in memo[i], and return it.
     * Call helper(n-1) to get the result.
     * Analysis:
     * Time Complexity: O(n) - Each index from 0 to n-1 is computed once, with O(1) lookups and operations per call.
     * Space Complexity: O(n) - The memo dictionary stores up to n entries, and the recursion stack uses O(n) space.
     * */

    public int robTwo(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];
        if (n == 2) return Math.max(nums[0], nums[1]);

        Map<Integer, Integer> memo = new HashMap<>();
        memo.put(0, nums[0]);
        memo.put(1, Math.max(nums[0], nums[1]));

        class Helper {
            int f(int i) {
                if (memo.containsKey(i)) {
                    return memo.get(i);
                }
                memo.put(i, Math.max(nums[i] + f(i-2), f(i-1)));
                return memo.get(i);
            }
        }

        Helper helper = new Helper();
        return helper.f(n-1);
        // Time: O(n)
        // Space: O(n)
    }

    /**
     *
     * Solution 3: Bottom-Up Dynamic Programming (Tabulation)
     * We shift to an iterative approach using a dynamic programming array to compute the maximum money bottom-up.
     *
     * Thought Process: Instead of computing top-down, we build the maximum money for each index iteratively. We use an array where dp[i] stores the maximum money that can be robbed up to index i, computed as the maximum of robbing house i plus the maximum up to i-2 or skipping house i and taking the maximum up to i-1.
     * Development Steps:
     * Handle base cases: if n = 1, return nums[0]; if n = 2, return max(nums[0], nums[1]).
     * Create an array dp of size n, with dp[0] = nums[0] and dp[1] = max(nums[0], nums[1]).
     * For i from 2 to n-1, set dp[i] = max(nums[i] + dp[i-2], dp[i-1]).
     * Return dp[n-1], the maximum for all houses.
     * Analysis:
     * Time Complexity: O(n) - The loop iterates from 2 to n-1, performing O(1) operations per iteration.
     * Space Complexity: O(n) - The dp array stores n values.
     * */

    public int robThree(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];
        if (n == 2) return Math.max(nums[0], nums[1]);

        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(nums[i] + dp[i-2], dp[i-1]);
        }

        return dp[n-1];
        // Time: O(n)
        // Space: O(n)
    }

    /**
     * Solution 4: Bottom-Up Dynamic Programming (Constant Space)
     * We optimize the tabulation approach by using only two variables to track the necessary previous maximums.
     *
     * Thought Process: Since dp[i] only depends on dp[i-2] and dp[i-1], we can use two variables to store these values instead of an array, updating them iteratively to compute the next maximum.
     * Development Steps:
     * Handle base cases: if n = 1, return nums[0]; if n = 2, return max(nums[0], nums[1]).
     * Initialize prev = nums[0] (maximum for index 0) and curr = max(nums[0], nums[1]) (maximum for index 1).
     * Iterate from i = 2 to n-1.
     * For each i, update prev, curr = curr, max(nums[i] + prev, curr) using simultaneous assignment.
     * Return curr, the maximum for all houses.
     * Analysis:
     * Time Complexity: O(n) - The loop runs from 2 to n-1, with O(1) operations per iteration.
     * Space Complexity: O(1) - Only two variables (prev, curr) are used, regardless of n.
     * */

    public int robFour(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];
        if (n == 2) return Math.max(nums[0], nums[1]);

        int prev = nums[0];
        int curr = Math.max(nums[0], nums[1]);

        for (int i = 2; i < n; i++) {
            int temp = curr;
            curr = Math.max(nums[i] + prev, curr);
            prev = temp;
        }

        return curr;
    }
}

/**
 * Detailed Explanation
 * Problem Overview: House Robber
 * The House Robber problem is a classic dynamic programming challenge. You're given an array of non-negative integers, where each element represents the amount of money in a house. The constraint is that you cannot rob two adjacent houses. Your task is to compute the maximum amount of money you can rob without triggering the alarm by robbing two neighboring houses.
 *
 * Why a Brute-Force Approach Fails
 * A naive solution tries all combinations by choosing either to rob or skip each house. At every index, we make a binary choice: rob this house and skip the next, or skip this house and move to the next one. This leads to an exponential number of recursive calls. Each subproblem is recalculated multiple times, which is both inefficient and slow.
 *
 * For example, if you have 10 houses, the total number of combinations to evaluate is O(2n) — which is not feasible in a real-world scenario.
 *
 * Dynamic Programming Strategy
 * The optimal solution to the House Robber problem uses a bottom-up dynamic programming approach, avoiding recomputation. The core idea is to store the maximum money that can be robbed up to each house, using only the results from the last two houses.
 *
 * Here's how it works: At each step i, the robber decides between two options:
 *
 * Rob house i: Add the value at house i to the maximum loot from house i-2.
 * Skip house i: Carry forward the maximum loot from house i-1.
 * The recurrence relation becomes: dp[i] = max(dp[i - 1], nums[i] + dp[i - 2]).
 * Space Optimization
 * Since each value only depends on the two previous values, we can use two variables, prev and curr, instead of an entire array. This reduces space complexity from O(n) to O(1).
 *
 * We initialize prev as the value of the first house, and curr as the max of the first two. Then for each subsequent house, we update curr as the maximum of curr and nums[i] + prev, then set prev to the previous curr.
 *
 * Time and Space Complexity
 * Time Complexity: O(n), where n is the number of houses.
 * Space Complexity: O(1), due to use of two variables instead of a full DP array.
 * Final Thoughts
 * The House Robber problem teaches core dynamic programming principles — namely, optimal substructure and space optimization. It's an essential pattern for tackling more complex variations like "House Robber II" and "Delete and Earn."
 * */