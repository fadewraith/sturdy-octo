package practiceQuestions.algomapio.dp.easy;

import java.util.HashMap;
import java.util.Map;

public class FibonacciNumber {

    /**
     * Solution 1: Simple Recursion
     * We start with the most straightforward approach by directly translating the Fibonacci definition into code.
     *
     * Thought Process: The mathematical definition of Fibonacci is recursive: F(n) = F(n-1) + F(n-2). We implement this by creating a function that calls itself for n-1 and n-2. Base cases are defined for n = 0 (returns 0) and n = 1 (returns 1).
     * Development Steps:
     * Write a function fib(n) that checks base cases.
     * For other cases, return the sum of fib(n-1) and fib(n-2).
     * Analysis:
     * Time Complexity: O(2^n) - Each call branches into two, leading to approximately 2^n operations.
     * Space Complexity: O(n) - The recursion stack grows to depth n.
     * Why Improve?: The exponential time complexity makes this impractical for large n. We need to eliminate redundant calculations.
     * */

    public int fib(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }
    // Time: O(2^n)
    // Space: O(n)

    /**
     * Solution 2: Top Down Memoization
     * To address the inefficiency of redundant calculations, we introduce memoization to store previously computed values.
     *
     * Thought Process: We observe that in the recursive solution, values like F(2) are computed multiple times. By storing results in a dictionary, we can retrieve them in O(1) time instead of recalculating.
     * Development Steps:
     * Create a dictionary memo initialized with base cases {0:0, 1:1}.
     * Define a helper function f(x) that checks if x is in memo. If so, return the stored value; otherwise, compute and store f(x-1) + f(x-2).
     * Call f(n) to get the result.
     * Analysis:
     * Time Complexity: O(n) - Each Fibonacci number is computed once, and lookups are O(1).
     * Space Complexity: O(n) - The dictionary stores n values, and the recursion stack uses O(n) space.
     * */

    private Map<Integer, Integer> memo = new HashMap<>();

    public int fibSolnTwo(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }

        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        int result = fibSolnTwo(n - 1) + fibSolnTwo(n - 2);
        memo.put(n, result);
        return result;
    }

    /**
     * Solution 3: Dynamic Programming (Bottom-Up)
     * We shift to an iterative approach using dynamic programming to avoid recursion and maintain clarity.
     *
     * Thought Process: Instead of computing top-down, we build the Fibonacci sequence bottom-up, storing each number in an array. This avoids the recursion stack and makes the process more explicit.
     * Development Steps:
     * Handle base cases: if n = 0 or n = 1, return 0 or 1.
     * Create an array dp of size n+1, initialized with zeros.
     * Set dp[0] = 0 and dp[1] = 1.
     * Iterate from i = 2 to n, setting dp[i] = dp[i-2] + dp[i-1].
     * Return dp[n].
     * Analysis:
     * Time Complexity: O(n) - The loop performs n-1 iterations with constant-time operations.
     * Space Complexity: O(n) - The dp array stores n+1 values.
     * Why Improve?: The solution is efficient but uses O(n) space to store all Fibonacci numbers, even though we only need the last two to compute the next. Can we optimize space further?
     * */

    public int fibSolnThree(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    /**
     * Solution 4: Iterative with Constant Space
     * We optimize the DP solution by only storing the two numbers needed for each step, reducing space complexity.
     *
     * Thought Process: In the DP array, we only use dp[i-2] and dp[i-1] to compute dp[i]. Instead of an array, we can use two variables to track the previous and current numbers, updating them iteratively.
     * Development Steps:
     * Handle base cases: return 0 for n = 0 and 1 for n = 1.
     * Initialize prev = 0 and cur = 1 for F(0) and F(1).
     * Iterate from i = 2 to n, updating prev and cur using a simultaneous assignment: prev, cur = cur, prev + cur.
     * Return cur, which holds F(n).
     * Analysis:
     * Time Complexity: O(n) - The loop performs n-1 iterations with constant-time updates.
     * Space Complexity: O(1) - Only two variables are used, regardless of n.
     * */

    public int fibSolnFour(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }

        int prev = 0;
        int cur = 1;

        for (int i = 2; i <= n; i++) {
            int next = prev + cur;
            prev = cur;
            cur = next;
        }
        // Bottom Up DP With Constant Space

        return cur;
    }
}

/**
 * Detailed Explanation
 * Understanding the Problem
 * The Fibonacci sequence is a classic mathematical series where each number is the sum of the two preceding ones, starting from 0 and 1. Formally, the sequence begins as: 0, 1, 1, 2, 3, 5, 8, 13, ... and so on. The goal of this problem is to return the nth Fibonacci number using an efficient and optimized approach. While recursion is a natural way to define the sequence, it quickly becomes inefficient due to overlapping subproblems and redundant calculations.
 *
 * Optimal Approach: Bottom-Up Dynamic Programming
 * Instead of using recursion, the optimal way to compute the nth Fibonacci number is with a bottom-up dynamic programming technique. This method avoids recomputation and builds the solution iteratively from the base cases. It also takes advantage of the fact that we only need to remember the last two values at any time, making it highly space-efficient.
 *
 * Step-by-Step Strategy
 * Base Cases: If n is 0, return 0; if n is 1, return 1. These are the first two Fibonacci numbers and serve as our starting point.
 * Initialization: Set two variables: prev = 0 and cur = 1, representing fib(0) and fib(1).
 * Iterate: Loop from i = 2 to n. At each step, compute the next Fibonacci number as next = prev + cur. Then, update prev and cur accordingly: prev = cur and cur = next.
 * Final Result: After completing the loop, cur will contain the value of fib(n), which is returned as the result.
 * Example
 * Let’s say n = 5. The loop would proceed as follows:
 *
 * i = 2 → next = 0 + 1 = 1
 * i = 3 → next = 1 + 1 = 2
 * i = 4 → next = 1 + 2 = 3
 * i = 5 → next = 2 + 3 = 5
 * So fib(5) = 5.
 *
 * Time and Space Complexity
 * Time Complexity: The algorithm runs in O(n) time, as it processes each number from 2 to n exactly once.
 * Space Complexity: O(1), since it uses only two variables (prev and cur) to track the most recent Fibonacci numbers.
 *
 * Why This Approach is Optimal
 * This solution avoids the exponential blowup of recursive approaches and the memory overhead of full dynamic programming tables. It combines the clarity of iteration with the efficiency of constant-space optimization. As a result, it's ideal for large values of n and is commonly used in production environments or competitive programming scenarios.
 * */
