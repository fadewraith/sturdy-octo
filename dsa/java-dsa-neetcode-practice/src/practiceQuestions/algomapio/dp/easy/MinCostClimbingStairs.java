package practiceQuestions.algomapio.dp.easy;

import java.util.HashMap;
import java.util.Map;

public class MinCostClimbingStairs {

    /**
     * Solution 1: Simple Recursion
     * We begin by directly translating the problem’s recursive nature into code.
     *
     * Thought Process: To reach the top (index n), you must come from either step n-1 or n-2. The minimum cost to reach index i is the minimum of the cost to reach i-1 plus cost[i-1] or the cost to reach i-2 plus cost[i-2]. Base cases are indices 0 and 1, which have a cost of 0 to start from.
     * Development Steps:
     * Define a function min_cost(i) that computes the minimum cost to reach index i.
     * For i < 2, return 0 (base cases for starting at index 0 or 1).
     * For other indices, recursively compute min(cost[i-2] + min_cost(i-2), cost[i-1] + min_cost(i-1)).
     * Call min_cost(n) to get the minimum cost to reach the top.
     * Analysis:
     * Time Complexity: O(2^n) - Each call branches into two recursive calls, forming a binary tree of depth n, leading to approximately 2^n operations.
     * Space Complexity: O(n) - The recursion stack can grow to depth n.
     * Why Improve?: The O(2^n) time complexity is inefficient for large n due to redundant calculations of the same subproblems. We need to eliminate these redundancies.
     * */

    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;

        return min_cost(cost, n);
    }

    private int min_cost(int[] cost, int i) {
        if (i < 2) return 0;
        return Math.min(cost[i - 2] + min_cost(cost, i - 2),
                cost[i - 1] + min_cost(cost, i - 1));
    }

    /**
     * Solution 2: Top-Down Dynamic Programming (Memoization)
     * We optimize the recursive solution by caching results to avoid redundant calculations.
     *
     * Thought Process: The recursive solution recomputes the minimum cost for the same indices multiple times. By storing results in a memoization dictionary, we can retrieve them in O(1) time, reducing the number of computations.
     * Development Steps:
     * Initialize a dictionary memo with base cases {0:0, 1:0} (cost to start at index 0 or 1).
     * Define a function min_cost(i) that checks if i is in memo. If so, return the cached value.
     * If not cached, compute min(cost[i-2] + min_cost(i-2), cost[i-1] + min_cost(i-1)), store it in memo[i], and return it.
     * Call min_cost(n) to get the result.
     * Analysis:
     * Time Complexity: O(n) - Each index i from 2 to n is computed once, with O(1) lookups and operations per call.
     * Space Complexity: O(n) - The memo dictionary stores up to n+1 entries, and the recursion stack uses O(n) space.
     * */

    public int minCostClimbingStairsTwo(int[] cost) {
        int n = cost.length;
        Map<Integer, Integer> memo = new HashMap<>();
        memo.put(0, 0);
        memo.put(1, 0);
        return min_cost(cost, n, memo);
    }

    private int min_cost(int[] cost, int i, Map<Integer, Integer> memo) {
        if (memo.containsKey(i)) return memo.get(i);

        int val = Math.min(cost[i - 2] + min_cost(cost, i - 2, memo),
                cost[i - 1] + min_cost(cost, i - 1, memo));
        memo.put(i, val);
        return val;
    }

    /**
     * Solution 3: Bottom-Up Dynamic Programming (Tabulation)
     * We shift to an iterative approach using a dynamic programming array to compute costs bottom-up.
     *
     * Thought Process: Instead of computing top-down, we build the minimum cost for each index iteratively, starting from the base cases. We use an array to store the minimum cost to reach each index, computing each step based on the previous two.
     * Development Steps:
     * Create an array dp of size n+1, initialized with zeros, where dp[i] represents the minimum cost to reach index i.
     * Base cases are dp[0] = 0 and dp[1] = 0 (no cost to start at index 0 or 1).
     * For i from 2 to n, set dp[i] = min(dp[i-2] + cost[i-2], dp[i-1] + cost[i-1]).
     * Return dp[n], the minimum cost to reach the top.
     * Analysis:
     * Time Complexity: O(n) - The loop iterates from 2 to n, performing O(1) operations per iteration.
     * Space Complexity: O(n) - The dp array stores n+1 values.
     * Why Improve?: The solution is efficient but uses O(n) space to store all intermediate costs, even though we only need the last two values to compute the next. We can optimize space further.
     * */

    public int minCostClimbingStairsThree(int[] cost) {
        int n = cost.length;
        int[] dp = new int[n + 1];

        for (int i = 2; i <= n; i++) {
            dp[i] = Math.min(dp[i - 2] + cost[i - 2], dp[i - 1] + cost[i - 1]);
        }

        return dp[n];
    }

    /**
     * Solution 4: Bottom-Up Dynamic Programming (Constant Space)
     * We optimize the tabulation approach by using only two variables to track the necessary previous costs.
     *
     * Thought Process: Since dp[i] only depends on dp[i-2] and dp[i-1], we can use two variables to store these values instead of an array, updating them iteratively to compute the next cost.
     * Development Steps:
     * Initialize prev = 0 and curr = 0 for the costs to reach indices 0 and 1.
     * Iterate from i = 2 to n.
     * For each i, update prev and curr using simultaneous assignment: prev, curr = curr, min(cost[i-2] + prev, cost[i-1] + curr).
     * After the loop, curr holds the minimum cost to reach index n.
     * Return curr.
     * Analysis:
     * Time Complexity: O(n) - The loop runs from 2 to n, with O(1) operations per iteration.
     * Space Complexity: O(1) - Only two variables (prev, curr) are used, regardless of n.
     * Why Stop Here?: This solution achieves O(n) time and O(1) space complexity, making it optimal. It eliminates recursion and minimizes memory usage while maintaining clarity and correctness.
     * */

    public int minCostClimbingStairsFour(int[] cost) {
        int n = cost.length;
        int prev = 0, curr = 0;

        for (int i = 2; i <= n; i++) {
            int next = Math.min(cost[i - 2] + prev, cost[i - 1] + curr);
            prev = curr;
            curr = next;
        }

        return curr;
    }
}

/**
 * Detailed Explanation
 * What Is the Min Cost Climbing Stairs Problem?
 * The Min Cost Climbing Stairs problem is a popular dynamic programming question that appears in coding interviews. You're given an array cost, where cost[i] is the cost of stepping on the ith stair. You can climb either 1 or 2 steps at a time and start at step 0 or step 1. The goal is to reach just beyond the last step (i.e., the top) with the minimum total cost.
 *
 * Naive Recursive Approach
 * A brute-force recursive solution explores all possible ways to climb to the top by either stepping from i - 1 or i - 2. At each step, it calculates the minimum cost of getting there. Although this works in theory, it's extremely inefficient in practice due to repeated recalculations of the same subproblems.
 *
 * For instance, to reach step 10, the function must compute the costs for step 9 and step 8, which in turn require step 8 and step 7, and so on — leading to a binary recursion tree of exponential size.
 *
 * Time Complexity: O(2ⁿ) due to redundant work. Space Complexity: O(n) from the recursive call stack.
 *
 * Optimized Dynamic Programming Strategy
 * To avoid recomputation, we use a bottom-up dynamic programming approach with constant space. The idea is simple: the cost to reach step i depends only on the cost to reach steps i - 1 and i - 2. We don’t need to store the entire history, just the last two results.
 *
 * We initialize two variables, prev and curr, to 0. These represent the minimum cost to reach the previous two steps. We then iterate from index 2 to n (the step beyond the last stair), computing the cost to reach each step as the minimum of:
 *
 * cost[i - 2] + prev
 * cost[i - 1] + curr
 * After each iteration, we update prev and curr accordingly. At the end, curr contains the minimum cost to reach the top.
 *
 * Time Complexity: O(n). Space Complexity: O(1).
 *
 * Conclusion
 * This problem is an excellent example of how a naive recursive solution can be optimized with dynamic programming. It reinforces key DP concepts like bottom-up computation and space optimization — critical skills for software engineering interviews.
 * */