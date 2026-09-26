package practiceQuestions.algomapio.graphs.medium;

import java.util.*;

public class PacificAtlanticWaterFlow {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Find all cells in a 2D grid where water can flow to both the Pacific and Atlantic oceans, based on height conditions.
     * Initialize two deques, p_que and a_que, and two sets, p_seen and a_seen, for Pacific and Atlantic reachable cells, respectively.
     * Get the grid dimensions: m (rows) and n (columns).
     * Add Pacific border cells to p_que and p_seen: first row (0, j) and first column (i, 0).
     * Add Atlantic border cells to a_que and a_seen: last column (i, n-1) and last row (m-1, j).
     * Define a BFS function get_coords that processes a queue and updates a seen set:
     * While the queue is not empty, pop a cell (i, j).
     * For each adjacent cell (up, down, left, right), if it’s within bounds, its height is at least the current cell’s height, and it’s not seen, add it to the queue and seen set.
     * Run get_coords for p_que and p_seen to find all cells reachable from the Pacific.
     * Run get_coords for a_que and a_seen to find all cells reachable from the Atlantic.
     * Return the intersection of p_seen and a_seen as a list of coordinates.
     * */

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        List<List<Integer>> result = new ArrayList<>();
        if (heights == null || heights.length == 0 || heights[0].length == 0) return result;

        int m = heights.length;
        int n = heights[0].length;

        boolean[][] pacific = new boolean[m][n];
        boolean[][] atlantic = new boolean[m][n];

        Queue<int[]> pacQueue = new LinkedList<>();
        Queue<int[]> atlQueue = new LinkedList<>();

        // Add pacific border cells
        for (int i = 0; i < m; i++) {
            pacQueue.add(new int[]{i, 0});
            pacific[i][0] = true;
        }
        for (int j = 0; j < n; j++) {
            pacQueue.add(new int[]{0, j});
            pacific[0][j] = true;
        }

        // Add atlantic border cells
        for (int i = 0; i < m; i++) {
            atlQueue.add(new int[]{i, n - 1});
            atlantic[i][n - 1] = true;
        }
        for (int j = 0; j < n; j++) {
            atlQueue.add(new int[]{m - 1, j});
            atlantic[m - 1][j] = true;
        }

        bfs(pacQueue, pacific, heights);
        bfs(atlQueue, atlantic, heights);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pacific[i][j] && atlantic[i][j]) {
                    result.add(Arrays.asList(i, j));
                }
            }
        }

        return result;
    }

    private void bfs(Queue<int[]> queue, boolean[][] ocean, int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int r = cell[0];
            int c = cell[1];

            for (int[] dir : directions) {
                int newRow = r + dir[0];
                int newCol = c + dir[1];
                if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n
                        && !ocean[newRow][newCol] && heights[newRow][newCol] >= heights[r][c]) {
                    ocean[newRow][newCol] = true;
                    queue.add(new int[]{newRow, newCol});
                }
            }
        }
    }
}

/**
 * Detailed Explanation
 * Understanding the Pacific Atlantic Water Flow Problem
 * The Pacific Atlantic Water Flow problem asks you to identify all coordinates in a matrix where water can flow to both the Pacific and Atlantic oceans. Water can only flow from a cell to its adjacent cells (up, down, left, right) if the neighbor’s height is less than or equal to the current cell’s height. The Pacific ocean touches the left and top edges, while the Atlantic touches the right and bottom edges.
 *
 * Key Insight: Reverse Water Flow Using BFS
 * Instead of checking from each cell whether it can reach both oceans (which would be inefficient), we reverse the flow. We simulate water flowing from the oceans into the land, moving only to higher or equal elevation cells. We perform two BFS traversals: one starting from all Pacific-border cells and another from all Atlantic-border cells. The intersection of the visited cells from both traversals gives us the answer.
 *
 * Step-by-Step Approach Using BFS
 * 1. Initialize two sets: p_seen and a_seen to track cells reachable from the Pacific and Atlantic oceans respectively.
 * 2. Use two queues to perform BFS from the Pacific (top and left edges) and Atlantic (bottom and right edges).
 * 3. In each BFS, for every cell dequeued, check its neighbors. If a neighbor is in bounds, hasn’t been visited, and has a height greater than or equal to the current cell, add it to the queue and mark it as reachable.
 * 4. Once both traversals are done, take the intersection of p_seen and a_seen to find all cells that can reach both oceans.
 *
 * Why This Approach Is Efficient
 * This method performs BFS from the borders inward, rather than simulating flow from every internal cell. Each cell is visited at most twice—once per ocean—which gives us a time complexity of O(m × n), where m and n are the matrix dimensions. The space complexity is also O(m × n) due to the visited sets and queues.
 *
 * Applications and Real-World Relevance
 * The Pacific Atlantic Water Flow problem is a great example of graph traversal on a grid and demonstrates how to efficiently simulate physical processes like fluid flow. Similar logic can be used in topography analysis, water drainage systems, and game development where pathfinding and flow simulation are involved.
 *
 * Conclusion
 * By performing two reverse BFS traversals from the ocean borders, we efficiently determine which cells allow water to reach both the Pacific and Atlantic. This problem showcases how classic algorithms like BFS can be adapted for grid-based simulations and real-world problems.
 * */