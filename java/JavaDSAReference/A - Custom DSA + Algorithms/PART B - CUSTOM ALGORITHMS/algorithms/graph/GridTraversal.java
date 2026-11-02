package algorithms.graph;

/**
 * GRID TRAVERSAL (Number of Islands, Max Area, Flood Fill, Shortest Path in a Grid)
 *
 * WHAT IT IS:
 * A 2D grid is a graph in disguise: every cell is a vertex, and it has edges to its
 * 4 neighbours (up/down/left/right) - or 8 if diagonals count.
 *
 * WHEN TO USE THIS:
 * - Counting connected regions (islands, lakes, blobs in an image).
 * - Paint-bucket tool in image editors (flood fill).
 * - Shortest path for a robot/game character on a tile map (BFS, all moves cost 1).
 *
 * STRATEGY:
 * - Number of Islands: scan every cell. When you find unvisited land, that's a NEW
 *   island: count it, then BFS/DFS to mark the whole island as visited.
 * - Max Area: same, but count cells during each BFS and keep the largest.
 * - Flood Fill: BFS from the start cell, recolouring every connected cell that has
 *   the ORIGINAL colour. (If the new colour equals the old one, do nothing - or the
 *   "visited" check would never stop.)
 * - Shortest Path: plain BFS - the first time we reach a cell is via a shortest path.
 *
 * WHY BFS WITH AN ARRAY QUEUE AND NOT RECURSIVE DFS?
 * A 1000 x 1000 all-land grid would recurse a million levels deep and blow the stack.
 * A queue of cell ids (row * cols + col) never overflows; every cell enters it once.
 *
 * COMPLEXITY:
 * Time: O(R * C) - each cell is visited a constant number of times.
 * Space: O(R * C) for the visited array and the queue.
 */
public class GridTraversal {

    private static final int[] DR4 = {-1, 1, 0, 0};
    private static final int[] DC4 = {0, 0, -1, 1};
    private static final int[] DR8 = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] DC8 = {-1, 0, 1, -1, 1, -1, 0, 1};

    /** grid[r][c] == 1 is land, 0 is water. Islands connect 4-directionally. */
    public static int numIslands(int[][] grid) {
        if (grid.length == 0) return 0;
        int rows = grid.length, cols = grid[0].length;
        boolean[] seen = new boolean[rows * cols];
        int[] queue = new int[rows * cols];
        int islands = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 1 && !seen[r * cols + c]) {
                    islands++;
                    bfsArea(grid, r, c, seen, queue);
                }
            }
        }
        return islands;
    }

    /** Size (cell count) of the largest island, 0 if there is no land. */
    public static int maxAreaOfIsland(int[][] grid) {
        if (grid.length == 0) return 0;
        int rows = grid.length, cols = grid[0].length;
        boolean[] seen = new boolean[rows * cols];
        int[] queue = new int[rows * cols];
        int best = 0;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (grid[r][c] == 1 && !seen[r * cols + c])
                    best = Math.max(best, bfsArea(grid, r, c, seen, queue));
        return best;
    }

    // Marks the island containing (r, c) and returns its size
    private static int bfsArea(int[][] grid, int r, int c, boolean[] seen, int[] queue) {
        int rows = grid.length, cols = grid[0].length;
        int head = 0, tail = 0;
        seen[r * cols + c] = true;
        queue[tail++] = r * cols + c;
        while (head < tail) {
            int cell = queue[head++];
            int cr = cell / cols, cc = cell % cols;
            for (int d = 0; d < 4; d++) {
                int nr = cr + DR4[d], nc = cc + DC4[d];
                if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;
                int id = nr * cols + nc;
                if (grid[nr][nc] == 1 && !seen[id]) {
                    seen[id] = true; // mark when ENQUEUED, so no cell is added twice
                    queue[tail++] = id;
                }
            }
        }
        return tail; // every cell of the island was enqueued exactly once
    }

    /** Paint-bucket: recolour the 4-connected region of (sr, sc). Modifies image in place. */
    public static int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        int oldColor = image[sr][sc];
        if (oldColor == newColor) return image;
        int rows = image.length, cols = image[0].length;
        int[] queue = new int[rows * cols];
        int head = 0, tail = 0;
        image[sr][sc] = newColor; // recolouring doubles as the "visited" mark
        queue[tail++] = sr * cols + sc;
        while (head < tail) {
            int cell = queue[head++];
            int cr = cell / cols, cc = cell % cols;
            for (int d = 0; d < 4; d++) {
                int nr = cr + DR4[d], nc = cc + DC4[d];
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && image[nr][nc] == oldColor) {
                    image[nr][nc] = newColor;
                    queue[tail++] = nr * cols + nc;
                }
            }
        }
        return image;
    }

    /**
     * Shortest Path in a Binary Matrix: fewest cells on a path from top-left to
     * bottom-right through 0-cells, moving in 8 directions. -1 if impossible.
     */
    public static int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        if (n == 0) return -1;
        int m = grid[0].length;
        if (grid[0][0] != 0 || grid[n - 1][m - 1] != 0) return -1;
        int[] dist = new int[n * m]; // 0 = not reached
        int[] queue = new int[n * m];
        int head = 0, tail = 0;
        dist[0] = 1;
        queue[tail++] = 0;
        while (head < tail) {
            int cell = queue[head++];
            int r = cell / m, c = cell % m;
            if (cell == n * m - 1) return dist[cell];
            for (int d = 0; d < 8; d++) {
                int nr = r + DR8[d], nc = c + DC8[d];
                if (nr < 0 || nr >= n || nc < 0 || nc >= m || grid[nr][nc] != 0) continue;
                int id = nr * m + nc;
                if (dist[id] == 0) {
                    dist[id] = dist[cell] + 1;
                    queue[tail++] = id;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println("--- GRID TRAVERSAL DEMO ---");

        int[][] grid = {
            {1, 1, 0, 0, 0},
            {1, 1, 0, 0, 1},
            {0, 0, 1, 0, 1},
            {0, 0, 0, 1, 1}
        };
        System.out.println("Number of islands: " + numIslands(grid));      // 3
        System.out.println("Max island area:   " + maxAreaOfIsland(grid)); // 4

        int[][] image = {
            {1, 1, 1},
            {1, 1, 0},
            {1, 0, 1}
        };
        floodFill(image, 1, 1, 2);
        System.out.println("Flood fill from (1,1) with 2: " + java.util.Arrays.deepToString(image));
        // [[2, 2, 2], [2, 2, 0], [2, 0, 1]] - the bottom-right 1 is not connected

        int[][] maze = {
            {0, 0, 0},
            {1, 1, 0},
            {1, 1, 0}
        };
        System.out.println("Shortest clear path (8 directions): " + shortestPathBinaryMatrix(maze)); // 4
    }
}
