package practiceQuestions.algomapio.graphs.medium;

import java.util.LinkedList;
import java.util.Queue;

public class RottingOranges {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Compute the minimum time for all oranges in a grid to rot, where rotten oranges (2) infect adjacent fresh oranges (1) each minute, or return -1 if impossible.
     * Define constants: EMPTY (0), FRESH (1), ROTTEN (2).
     * Get the grid dimensions: m (rows) and n (columns).
     * Initialize a deque q to store coordinates of rotten oranges and a counter num_fresh to count fresh oranges.
     * Iterate through each cell (i, j) in the grid: if ROTTEN, append (i, j) to q; if FRESH, increment num_fresh.
     * If num_fresh is 0, return 0 as no fresh oranges need to rot.
     * Initialize num_minutes to -1 to track minutes elapsed.
     * While q is not empty, process each minute:
     * Get the number of rotten oranges q_size in the current minute.
     * Increment num_minutes.
     * For each of the q_size oranges, pop (i, j) from q and check four adjacent cells (right, down, left, up).
     * For each adjacent cell (r, c), if within bounds and FRESH, mark it ROTTEN, decrement num_fresh, and append (r, c) to q.
     * After the loop, if num_fresh is 0, return num_minutes; otherwise, return -1.
     * */

    public int orangesRotting(int[][] grid) {
        final int EMPTY = 0, FRESH = 1, ROTTEN = 2;
        int m = grid.length, n = grid[0].length;
        int numFresh = 0;
        Queue<int[]> q = new LinkedList<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == ROTTEN) {
                    q.offer(new int[]{i, j});
                } else if (grid[i][j] == FRESH) {
                    numFresh++;
                }
            }
        }

        if (numFresh == 0) return 0;

        int numMinutes = -1;
        while (!q.isEmpty()) {
            int qSize = q.size();
            numMinutes++;

            for (int k = 0; k < qSize; k++) {
                int[] pos = q.poll();
                int i = pos[0], j = pos[1];
                for (int[] dir : new int[][]{{0,1},{1,0},{0,-1},{-1,0}}) {
                    int r = i + dir[0], c = j + dir[1];
                    if (r >= 0 && r < m && c >= 0 && c < n && grid[r][c] == FRESH) {
                        grid[r][c] = ROTTEN;
                        numFresh--;
                        q.offer(new int[]{r, c});
                    }
                }
            }
        }

        return numFresh == 0 ? numMinutes : -1;
    }
}

/**
 * Detailed Explanation
 * Rotting Oranges: Problem Summary
 * The Rotting Oranges problem involves a 2D grid representing a box of oranges. Each cell in the grid can either be empty (represented by 0), contain a fresh orange (1), or contain a rotten orange (2). Every minute, any fresh orange that is directly adjacent to a rotten orange (horizontally or vertically) becomes rotten. The goal is to determine the minimum number of minutes required for all fresh oranges to become rotten. If it is impossible to rot all the oranges, the function should return -1.
 *
 * Solution Strategy: Breadth-First Search (BFS)
 * To efficiently solve the Rotting Oranges problem, we use a Breadth-First Search (BFS) approach. This is ideal for problems involving spreading or expansion, such as traversing levels in a grid. By modeling the rotting process minute by minute, we simulate how rot spreads from initially rotten oranges to adjacent fresh ones.
 *
 * First, we scan the grid and collect all positions of the rotten oranges. These positions act as the starting points for the BFS. We also count how many fresh oranges exist at the start. This step ensures we know how many oranges need to be rotted for the final result to be valid.
 *
 * Next, we perform a level-by-level BFS traversal using a queue. For each level (each minute), we process all rotten oranges currently in the queue. For each orange, we examine its four neighboring cells—up, down, left, and right. If a neighbor contains a fresh orange, we mark it as rotten and add it to the queue for the next round of processing. We also decrement the count of fresh oranges.
 *
 * The BFS continues until there are no more oranges to process. After BFS finishes, if there are still fresh oranges remaining, it means not all could be reached, so we return -1. Otherwise, we return the number of minutes it took to rot all the oranges.
 *
 * Time and Space Complexity
 * The time complexity of this algorithm is O(m * n) where m and n are the number of rows and columns in the grid. This is because we visit each cell at most once. The space complexity is also O(m * n) due to the space required for the queue and the grid itself.
 *
 * Why BFS Works Best
 * BFS is particularly well-suited for this problem because it naturally models the spreading of an effect (in this case, rot) across a space over time. Each level in BFS represents a unit of time—exactly what we need to track minutes as oranges rot. Unlike depth-first search, which dives deep into one path, BFS ensures that all adjacent oranges rot simultaneously at each time step.
 *
 * Tips and Edge Cases
 * Don’t forget to handle edge cases, such as when there are no fresh oranges at the start—this should return 0 immediately. Also, make sure not to rot the same orange multiple times. Marking visited oranges right away ensures we avoid infinite loops or redundant processing.
 * */