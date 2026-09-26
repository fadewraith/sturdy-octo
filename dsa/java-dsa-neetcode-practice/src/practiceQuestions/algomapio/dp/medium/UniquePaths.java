package practiceQuestions.algomapio.dp.medium;

import java.util.HashMap;
import java.util.Map;

public class UniquePaths {

    /**
     * Solution 1: Simple Recursion
     * We begin by directly modeling the problem’s recursive nature, where each position can be reached by moving right or down.
     *
     * Thought Process: To reach position (i,j), you must come from either (i-1,j) (moving down) or (i,j-1) (moving right). The number of paths to (i,j) is the sum of paths to these two positions. The base case is the starting point (0,0), which has exactly one path (staying there), and any position outside the grid has zero paths.
     * Development Steps:
     * Define a function paths(i, j) to compute the number of paths to position (i,j).
     * For the base case, if i == j == 0, return 1.
     * If the position is out of bounds (i < 0, j < 0, i == m, or j == n), return 0.
     * Otherwise, return the sum of paths(i-1, j) and paths(i, j-1).
     * Call paths(m-1, n-1) to get the number of paths to the bottom-right corner.
     * Analysis:
     * Time Complexity: O(2^(m*n)) - Each position branches into two recursive calls (down or right), forming a binary tree with depth up to m+n, leading to approximately 2^(m*n) operations in the worst case.
     * Space Complexity: O(m*n) - The recursion stack can grow to depth m+n along the longest path in the grid.
     * Why Improve?: The O(2^(m*n)) time complexity is impractical for large grids due to redundant calculations for the same positions. We need to cache results to avoid recomputing paths.
     * */

    public int uniquePaths(int m, int n) {
        // Recursive Solution
        // Time: O(2^(m*n))
        // Space: O(m*n)
        return paths(m - 1, n - 1, m, n);
    }

    private int paths(int i, int j, int m, int n) {
        if (i == 0 && j == 0) return 1;
        if (i < 0 || j < 0 || i == m || j == n) return 0;
        return paths(i - 1, j, m, n) + paths(i, j - 1, m, n);
    }

    /**
     * Solution 2: Top Down Dynamic Programming (Memoization)
     * We optimize the recursive solution by storing computed results to eliminate redundant calculations.
     *
     * Thought Process: The recursive solution recomputes paths for the same positions multiple times. By caching the number of paths to each position (i,j) in a dictionary, we can retrieve results in O(1) time, reducing the number of computations.
     * Development Steps:
     * Initialize a dictionary memo with the base case {(0,0): 1} (one path to the starting point).
     * Define a function paths(i, j) that checks if (i,j) is in memo. If so, return the cached value.
     * If out of bounds (i < 0, j < 0, i == m, or j == n), return 0.
     * Otherwise, compute paths(i, j-1) + paths(i-1, j), store the result in memo[(i,j)], and return it.
     * Call paths(m-1, n-1) to get the result.
     * Analysis:
     * Time Complexity: O(m*n) - Each position (i,j) is computed once, with O(1) lookups and operations per call. There are m*n positions in the grid.
     * Space Complexity: O(m*n) - The memo dictionary stores up to m*n entries, and the recursion stack uses O(m+n) space.
     * */

    private Map<String, Integer> memo = new HashMap<>();

    public int uniquePaths2(int m, int n) {
        memo.put("0,0", 1);
        return paths(m - 1, n - 1, m, n);
    }

    private int paths2(int i, int j, int m, int n) {
        String key = i + "," + j;
        if (memo.containsKey(key)) return memo.get(key);
        if (i < 0 || j < 0 || i >= m || j >= n) return 0;

        int val = paths2(i - 1, j, m, n) + paths2(i, j - 1, m, n);
        memo.put(key, val);
        return val;
    }

    /**
     * Solution 3: Bottom Up Dynamic Programming (Tabulation)
     * We shift to an iterative approach using a dynamic programming grid to compute paths bottom-up.
     *
     * Thought Process: Instead of computing top-down, we build the number of paths to each position iteratively. We use a 2D array where dp[i][j] stores the number of paths to position (i,j), computed as the sum of paths from above and left.
     * Development Steps:
     * Create a 2D array dp of size m x n, initialized with zeros.
     * Set dp[0][0] = 1 (base case: one path to the starting point).
     * Iterate over each position (i,j) in the grid from 0 to m-1 and 0 to n-1.
     * For each position, if not (0,0), set dp[i][j] = dp[i-1][j] + dp[i][j-1] (adding paths from above and left, if valid).
     * Return dp[m-1][n-1], the number of paths to the bottom-right corner.
     * Analysis:
     * Time Complexity: O(m*n) - The nested loops iterate over m*n positions, with O(1) operations per position.
     * Space Complexity: O(m*n) - The dp array stores m*n values.
     * */

    public int uniquePaths3(int m, int n) {
        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = 1;
                } else {
                    int fromTop = (i > 0) ? dp[i - 1][j] : 0;
                    int fromLeft = (j > 0) ? dp[i][j - 1] : 0;
                    dp[i][j] = fromTop + fromLeft;
                }
            }
        }

        return dp[m - 1][n - 1];
    }
}

/**
 * Detailed Explanation
 * Problem Overview: Unique Paths in a Grid
 * The Unique Paths problem is a classic dynamic programming challenge that appears in many algorithm interviews. You are given an m x n grid, and your task is to determine how many distinct paths exist to move from the top-left corner to the bottom-right corner by only moving right or down. This is a foundational problem that tests understanding of combinatorics and dynamic programming strategies.
 *
 * Understanding the Brute-Force Recursive Approach
 * A naive solution uses recursion to explore all possible paths from the starting cell to the goal. At each cell (i, j), you have two choices: move right or move down. The recursive function sums the number of valid paths from these two options.
 *
 * While conceptually straightforward, this brute-force approach is highly inefficient for larger grids due to repeated recomputation of the same subproblems. For example, the number of recursive calls grows exponentially as O(2m+n), making this solution infeasible beyond small grid sizes.
 *
 * Optimizing with Dynamic Programming
 * The optimal solution to the Unique Paths problem uses bottom-up dynamic programming to compute the number of ways to reach each cell by building up from the base case. Instead of recalculating overlapping subproblems, we store the result of each computation in a 2D table.
 *
 * You initialize a 2D array dp of size m x n, where dp[i][j] represents the number of ways to reach cell (i, j). The first row and first column are filled with 1s because there is only one way to move strictly right or strictly down. Each remaining cell is computed as the sum of the paths from the cell above and the cell to the left.
 *
 * The final result is stored in dp[m-1][n-1], which represents the total number of unique paths from the top-left to the bottom-right.
 *
 * Time and Space Complexity
 * Time Complexity: O(m × n), where m and n are the grid dimensions.
 * Space Complexity: O(m × n), for the DP table. This can be optimized to O(n) if you only store the current and previous rows.
 * Conclusion
 * The Unique Paths problem is a powerful introduction to dynamic programming concepts and optimization techniques. By transitioning from brute-force recursion to a tabulation-based approach, you dramatically improve efficiency and scalability. Mastering this pattern helps tackle a wide range of grid traversal and DP problems, including obstacles, cost minimization, and more complex movement constraints.
 * */
