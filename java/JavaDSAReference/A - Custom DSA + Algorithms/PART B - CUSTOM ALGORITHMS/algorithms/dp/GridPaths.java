package algorithms.dp;

/**
 * GRID PATH DP (Unique Paths, Unique Paths with Obstacles, Minimum Path Sum)
 *
 * WHAT IT IS:
 * A robot starts at the top-left of an R x C grid and may only move RIGHT or DOWN.
 * - Unique Paths: how many different routes reach the bottom-right?
 * - With Obstacles: same, but some cells are blocked (1 = blocked).
 * - Minimum Path Sum: every cell has a cost; find the cheapest route.
 *
 * WHEN TO USE THIS:
 * The "2D grid, moves only right/down" shape is the gentlest introduction to 2D DP,
 * and the same table appears in LCS, edit distance and many matrix problems.
 *
 * STRATEGY:
 * The last move into cell (r, c) came either from above (r-1, c) or from the
 * left (r, c-1). So:
 *   paths[r][c] = paths[r-1][c] + paths[r][c-1]           (0 if (r, c) is blocked)
 *   cost[r][c]  = grid[r][c] + min(cost[r-1][c], cost[r][c-1])
 * SPACE TRICK: row r only needs row r-1, so one 1D array is enough:
 * dp[c] still holds the value from the row above until we overwrite it, and
 * dp[c-1] already holds the current row's left neighbour.
 *
 * MATH SHORTCUT (no obstacles): every route is (R-1) downs and (C-1) rights in some
 * order, so the answer is the binomial coefficient C(R+C-2, R-1).
 *
 * COMPLEXITY:
 * Time: O(R * C)
 * Space: O(C) with the rolling 1D array.
 */
public class GridPaths {

    /** Number of right/down routes in an empty rows x cols grid. */
    public static long uniquePaths(int rows, int cols) {
        long[] dp = new long[cols];
        java.util.Arrays.fill(dp, 1); // first row: only one way (all rights)
        for (int r = 1; r < rows; r++) {
            for (int c = 1; c < cols; c++) {
                dp[c] += dp[c - 1]; // from above (old dp[c]) + from the left (new dp[c-1])
            }
        }
        return dp[cols - 1];
    }

    /** Same answer via the binomial coefficient C(rows+cols-2, rows-1). */
    public static long uniquePathsCombinatorics(int rows, int cols) {
        int n = rows + cols - 2, k = Math.min(rows - 1, cols - 1);
        long result = 1;
        for (int i = 1; i <= k; i++) {
            result = result * (n - k + i) / i; // stays an integer at every step
        }
        return result;
    }

    /** obstacleGrid[r][c] == 1 means blocked. */
    public static long uniquePathsWithObstacles(int[][] obstacleGrid) {
        int cols = obstacleGrid[0].length;
        long[] dp = new long[cols];
        dp[0] = obstacleGrid[0][0] == 1 ? 0 : 1;
        for (int[] row : obstacleGrid) {
            for (int c = 0; c < cols; c++) {
                if (row[c] == 1) dp[c] = 0;           // nothing passes through a wall
                else if (c > 0) dp[c] += dp[c - 1];
            }
        }
        return dp[cols - 1];
    }

    /** Cheapest right/down route sum from top-left to bottom-right (both cells included). */
    public static long minPathSum(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        long[] dp = new long[cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (r == 0 && c == 0) dp[c] = grid[0][0];
                else if (r == 0) dp[c] = dp[c - 1] + grid[r][c];       // top row: only from the left
                else if (c == 0) dp[c] = dp[c] + grid[r][c];           // left column: only from above
                else dp[c] = Math.min(dp[c], dp[c - 1]) + grid[r][c];
            }
        }
        return dp[cols - 1];
    }

    public static void main(String[] args) {
        System.out.println("--- GRID PATHS DP DEMO ---");

        System.out.println("Unique paths 3 x 7: " + uniquePaths(3, 7)
                + " (binomial: " + uniquePathsCombinatorics(3, 7) + ")"); // 28

        int[][] obstacles = {
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
        };
        System.out.println("With a wall in the middle: " + uniquePathsWithObstacles(obstacles)); // 2

        int[][] costs = {
            {1, 3, 1},
            {1, 5, 1},
            {4, 2, 1}
        };
        System.out.println("Minimum path sum: " + minPathSum(costs)); // 7 (1 -> 3 -> 1 -> 1 -> 1)
    }
}
