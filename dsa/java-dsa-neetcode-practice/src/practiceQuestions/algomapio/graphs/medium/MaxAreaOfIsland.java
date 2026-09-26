package practiceQuestions.algomapio.graphs.medium;

public class MaxAreaOfIsland {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Find the maximum area of an island in a grid, where an island is a group of connected 1s (land).
     * Get the dimensions m (rows) and n (columns) of the grid.
     * Define a DFS function that takes coordinates i, j and returns the area of the island starting at (i, j).
     * In DFS, if i or j is out of bounds or grid[i][j] is not 1, return 0.
     * Mark grid[i][j] as 0 to avoid revisiting, then recursively compute the area by summing 1 (current cell) plus DFS calls for all four adjacent cells (up, down, left, right).
     * Initialize max_area to 0 to track the largest island area.
     * Iterate through each cell (i, j) in the grid; if grid[i][j] is 1, call DFS and update max_area with the maximum of max_area and the DFS result.
     * Return max_area as the result.
     * */

    public int maxAreaOfIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int maxArea = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    maxArea = Math.max(maxArea, dfs(grid, i, j));
                }
            }
        }

        return maxArea;
    }

    private int dfs(int[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 1) {
            return 0;
        }
        grid[i][j] = 0; // Mark as visited
        int area = 1;
        area += dfs(grid, i + 1, j); // Down
        area += dfs(grid, i - 1, j); // Up
        area += dfs(grid, i, j + 1); // Right
        area += dfs(grid, i, j - 1); // Left
        return area;
    }
}

/**
 * Detailed Explanation
 * Understanding the Problem
 * You're given a 2D grid filled with 0s and 1s. A '1' represents land, and a '0' represents water. An island is a group of adjacent land cells connected horizontally or vertically (not diagonally). Your goal is to find the largest possible area of any single island in the grid — that is, the maximum number of connected 1s in any region.
 *
 * Key Insight
 * To solve this, you need to identify each distinct island and measure its size. Since you only want the size of the largest island, you can explore each island one by one and track the maximum size encountered.
 *
 * Approach: Depth-First Search (DFS)
 * The most efficient and intuitive approach to finding island sizes is to use depth-first search. You start from any cell with a 1 and recursively explore all four directions (up, down, left, right) to count the size of the connected region.
 *
 * Each time you visit a land cell (1), mark it as visited (e.g., by setting it to 0) so it won't be counted again. This prevents infinite loops and double-counting.
 *
 * The DFS function returns the total area of the island by summing 1 (the current cell) plus the results of recursive calls to its four neighbors. Each complete DFS traversal returns the size of one island.
 *
 * Algorithm Walkthrough
 * Loop through every cell in the grid. When you find a land cell (1), initiate a DFS starting from that cell. Let the DFS return the area of that island, and update your global max area if this new value is larger. After visiting every cell, the largest island area will be stored in max_area.
 *
 * Time and Space Complexity
 * Time Complexity: O(m × n), where m is the number of rows and n is the number of columns. Every cell is visited at most once.
 *
 * Space Complexity: O(m × n) in the worst case due to the call stack from recursion, if the entire grid is one giant island.
 *
 * Edge Cases
 * - If the grid is entirely water (all 0s), the result is 0.
 * - If the grid is entirely land (all 1s), the result is m × n.
 * - Small grids like 1x1 still work correctly with the DFS-based approach.
 *
 * Conclusion
 * This problem is a perfect example of how DFS can be used to explore 2D space. By using DFS to explore and mark visited land, we efficiently isolate each island and calculate its area, ensuring optimal performance and correctness.
 * */