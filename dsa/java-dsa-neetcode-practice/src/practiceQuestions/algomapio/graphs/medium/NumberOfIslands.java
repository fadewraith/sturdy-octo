package practiceQuestions.algomapio.graphs.medium;

public class NumberOfIslands {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Count the number of islands in a 2D grid, where an island is a group of connected '1's (land) surrounded by '0's (water).
     * Get the dimensions of the grid: m (rows) and n (columns).
     * Define a DFS function that takes coordinates i, j to mark an island as visited:
     * If i or j is out of bounds or grid[i][j] is not '1', return.
     * Mark grid[i][j] as '0' to avoid revisiting.
     * Recursively call DFS on the four adjacent cells (right, down, left, up).
     * Initialize num_islands to 0 to count the islands.
     * Iterate through each cell (i, j) in the grid:
     * If grid[i][j] is '1', increment num_islands and call DFS(i, j) to mark the entire island.
     * Return num_islands as the total number of islands.
     * */

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;

        int m = grid.length;
        int n = grid[0].length;
        int numIslands = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    numIslands++;
                    dfs(grid, i, j);
                }
            }
        }

        return numIslands;
    }

    private void dfs(char[][] grid, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;
        if (i < 0 || i >= m || j < 0 || j >= n || grid[i][j] != '1') return;
        grid[i][j] = '0';
        dfs(grid, i, j + 1);
        dfs(grid, i + 1, j);
        dfs(grid, i, j - 1);
        dfs(grid, i - 1, j);
    }
}

/**
 * Detailed Explanation
 * Understanding the Problem
 * You're given a 2D grid of characters representing a map of '1's (land) and '0's (water). An island is formed by connecting adjacent lands horizontally or vertically. Your task is to count how many islands exist in the grid.
 *
 * The key challenge is ensuring that each island is only counted once, even though it may span multiple grid cells. To handle this, we will use a depth-first search (DFS) traversal to "sink" or mark visited cells so we don’t count them again.
 *
 * Approach: Depth-First Search (DFS)
 * The grid can be traversed cell by cell. Whenever we find a cell with '1', we recognize it as the start of a new island. We then initiate a DFS from that cell to traverse the rest of the island and mark all of its land cells as visited (for example, by changing '1' to '0').
 *
 * The DFS explores all four directions (up, down, left, right). If we stay within bounds and find a land cell, we continue the DFS from that point. This continues recursively until the entire island is marked.
 *
 * By doing this for every unvisited '1' we encounter, we ensure each island is counted exactly once.
 *
 * Edge Cases
 * - An empty grid (0 rows or 0 columns) should return 0.
 * - A grid full of '0's has no islands and also returns 0.
 * - A grid with all '1's and no '0's will return 1, as the entire grid is one large island.
 *
 * Time and Space Complexity
 * Time Complexity: O(m × n), where m is the number of rows and n is the number of columns in the grid. Each cell is visited once.
 *
 * Space Complexity: O(m × n) in the worst case due to the recursion stack in DFS if the entire grid is filled with land.
 *
 * Conclusion
 * This problem is a classic use case for graph traversal in 2D space. The DFS-based strategy is both intuitive and effective for marking and counting islands. It ensures no part of an island is double-counted, and handles complex shapes and placements of land in the grid seamlessly.
 * */