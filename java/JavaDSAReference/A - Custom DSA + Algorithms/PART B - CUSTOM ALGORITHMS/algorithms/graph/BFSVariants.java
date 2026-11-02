package algorithms.graph;

import java.util.Arrays;

/**
 * BFS VARIANTS: MULTI-SOURCE BFS and 0-1 BFS
 *
 * 1. MULTI-SOURCE BFS
 * WHAT IT IS: an ordinary BFS that starts with MANY vertices in the queue at
 * distance 0, instead of one. Every vertex then gets its distance to the NEAREST
 * source, in a single O(V + E) pass. (Running a BFS from each source would be
 * O(sources * (V + E)).)
 * Think of it as adding a fake "super source" with an edge to every real source.
 * WHEN TO USE THIS:
 * - Rotting Oranges: all rotten oranges spread at the same time, minute by minute.
 * - 01 Matrix / Walls and Gates: distance from every cell to the nearest 0 / gate.
 * - Nearest hospital / fire station for every house.
 *
 * 2. 0-1 BFS
 * WHAT IT IS: shortest paths when every edge weight is 0 or 1. Use a DEQUE:
 *   - relaxing a 0-weight edge -> push the vertex to the FRONT (same distance level)
 *   - relaxing a 1-weight edge -> push it to the BACK (next distance level)
 * The deque always holds at most two neighbouring distance values, front to back in
 * order, so it behaves like Dijkstra's priority queue without the log factor.
 * A vertex can be pushed more than once; stale entries are just skipped.
 * WHEN TO USE THIS:
 * - "Minimum walls to break / obstacles to remove to reach the exit" (stepping on
 *   an empty cell costs 0, on a wall costs 1).
 * - Minimum edge reversals to make a path (original edge 0, reversed edge 1).
 *
 * COMPLEXITY:
 * Multi-source BFS: Time O(V + E) (O(R * C) on a grid), Space O(V).
 * 0-1 BFS:          Time O(V + E) (O(V^2) with the matrix used here), Space O(V + E).
 */
public class BFSVariants {

    private static final int[] DR = {-1, 1, 0, 0};
    private static final int[] DC = {0, 0, -1, 1};

    /**
     * ROTTING ORANGES. 0 = empty, 1 = fresh, 2 = rotten. Each minute every rotten
     * orange rots its 4 neighbours. Returns the minutes until no fresh orange is
     * left, or -1 if some orange can never rot. The grid is not modified.
     */
    public static int orangesRotting(int[][] grid) {
        if (grid.length == 0) return 0;
        int rows = grid.length, cols = grid[0].length;
        int[] minute = new int[rows * cols];
        Arrays.fill(minute, -1);
        int[] queue = new int[rows * cols];
        int head = 0, tail = 0, fresh = 0;

        // every rotten orange is a source at minute 0
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 2) { minute[r * cols + c] = 0; queue[tail++] = r * cols + c; }
                else if (grid[r][c] == 1) fresh++;
            }
        }

        int elapsed = 0;
        while (head < tail) {
            int cell = queue[head++];
            int r = cell / cols, c = cell % cols;
            for (int d = 0; d < 4; d++) {
                int nr = r + DR[d], nc = c + DC[d];
                if (nr < 0 || nr >= rows || nc < 0 || nc >= cols || grid[nr][nc] != 1) continue;
                int id = nr * cols + nc;
                if (minute[id] == -1) {
                    minute[id] = minute[cell] + 1;
                    elapsed = Math.max(elapsed, minute[id]);
                    fresh--;
                    queue[tail++] = id;
                }
            }
        }
        return fresh == 0 ? elapsed : -1;
    }

    /**
     * 01 MATRIX: for every cell, the 4-directional distance to the nearest 0.
     * Every 0 is a source. Cells with no 0 anywhere get -1.
     */
    public static int[][] distanceToNearestZero(int[][] grid) {
        int rows = grid.length, cols = rows == 0 ? 0 : grid[0].length;
        int[][] dist = new int[rows][cols];
        int[] queue = new int[rows * cols];
        int head = 0, tail = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 0) { dist[r][c] = 0; queue[tail++] = r * cols + c; }
                else dist[r][c] = -1;
            }
        }
        while (head < tail) {
            int cell = queue[head++];
            int r = cell / cols, c = cell % cols;
            for (int d = 0; d < 4; d++) {
                int nr = r + DR[d], nc = c + DC[d];
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[r][c] + 1;
                    queue[tail++] = nr * cols + nc;
                }
            }
        }
        return dist;
    }

    // Growable circular deque of ints (no java.util.ArrayDeque)
    private static class IntDeque {
        int[] data = new int[16];
        int head = 0, size = 0;
        void grow() {
            int[] bigger = new int[data.length * 2];
            for (int i = 0; i < size; i++) bigger[i] = data[(head + i) % data.length];
            data = bigger;
            head = 0;
        }
        void addFirst(int x) {
            if (size == data.length) grow();
            head = (head - 1 + data.length) % data.length;
            data[head] = x;
            size++;
        }
        void addLast(int x) {
            if (size == data.length) grow();
            data[(head + size) % data.length] = x;
            size++;
        }
        int pollFirst() {
            int x = data[head];
            head = (head + 1) % data.length;
            size--;
            return x;
        }
        boolean isEmpty() { return size == 0; }
    }

    /**
     * 0-1 BFS on a weighted matrix: weight[u][v] is 0 or 1 for an edge, -1 for no edge.
     * Returns dist[] from source (Integer.MAX_VALUE = unreachable).
     */
    public static int[] zeroOneBFS(int[][] weight, int source) {
        int V = weight.length;
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;
        IntDeque deque = new IntDeque();
        deque.addFirst(source);
        while (!deque.isEmpty()) {
            int u = deque.pollFirst();
            for (int v = 0; v < V; v++) {
                int w = weight[u][v];
                if (w < 0) continue;
                if (w > 1) throw new IllegalArgumentException("0-1 BFS needs weights 0 or 1, got " + w);
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    if (w == 0) deque.addFirst(v);
                    else deque.addLast(v);
                }
            }
        }
        return dist;
    }

    /**
     * MINIMUM OBSTACLES TO REMOVE: grid of 0 (empty) and 1 (obstacle). Moving into an
     * obstacle cell costs 1 (we remove it), into an empty cell costs 0. Returns the
     * fewest removals needed to walk from top-left to bottom-right (4 directions).
     */
    public static int minObstaclesToRemove(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        int[] dist = new int[rows * cols];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = grid[0][0]; // standing on a starting obstacle means removing it too
        IntDeque deque = new IntDeque();
        deque.addFirst(0);
        while (!deque.isEmpty()) {
            int cell = deque.pollFirst();
            int r = cell / cols, c = cell % cols;
            for (int d = 0; d < 4; d++) {
                int nr = r + DR[d], nc = c + DC[d];
                if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;
                int id = nr * cols + nc, w = grid[nr][nc];
                if (dist[cell] + w < dist[id]) {
                    dist[id] = dist[cell] + w;
                    if (w == 0) deque.addFirst(id);
                    else deque.addLast(id);
                }
            }
        }
        return dist[rows * cols - 1];
    }

    public static void main(String[] args) {
        System.out.println("--- BFS VARIANTS DEMO ---");

        int[][] oranges = {
            {2, 1, 1},
            {1, 1, 0},
            {0, 1, 1}
        };
        System.out.println("Rotting oranges: " + orangesRotting(oranges) + " minutes"); // 4
        int[][] stuck = {{2, 1, 1}, {0, 1, 1}, {1, 0, 1}};
        System.out.println("Unreachable orange: " + orangesRotting(stuck));          // -1

        int[][] mat = {
            {0, 0, 0},
            {0, 1, 0},
            {1, 1, 1}
        };
        System.out.println("\nDistance to nearest 0: " + Arrays.deepToString(distanceToNearestZero(mat)));
        // [[0, 0, 0], [0, 1, 0], [1, 2, 1]]

        // 0 -> 1 costs 1, 0 -> 2 costs 0, 2 -> 1 costs 0, 1 -> 3 costs 1
        int[][] w = new int[4][4];
        for (int[] row : w) Arrays.fill(row, -1);
        w[0][1] = 1; w[0][2] = 0; w[2][1] = 0; w[1][3] = 1;
        System.out.println("\n0-1 BFS distances from 0: " + Arrays.toString(zeroOneBFS(w, 0))); // [0, 0, 0, 1]

        int[][] walls = {
            {0, 1, 1},
            {1, 1, 0},
            {1, 1, 0}
        };
        System.out.println("Fewest obstacles to remove: " + minObstaclesToRemove(walls)); // 2
    }
}
