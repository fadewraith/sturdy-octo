/**
 * WHAT IT IS: D* Lite is an incremental heuristic search algorithm. It builds on A* but handles dynamic graphs where edge costs change during path execution.
 * STRATEGY: Searches backwards from the goal to the start. If edge costs change (e.g., an obstacle appears), it only updates the affected nodes rather than recalculating the whole path.
 * TIME/SPACE COMPLEXITY: Time: O(V log V) for initial search, but updates are much faster. Space: O(V).
 * REAL-WORLD ANALOGY / USE CASE: Mars rovers or autonomous robots moving through unknown terrain where obstacles are discovered on the fly.
 * WHEN TO USE / COMBINATION: When the graph changes dynamically during traversal and full recalculation using A* would be too slow.
 *
 * KEY IDEAS:
 * - g(s):   current best-known cost from s to the GOAL (search runs backwards).
 * - rhs(s): one-step lookahead = min over neighbors s' of cost(s, s') + g(s').
 * - A node is "consistent" when g == rhs. Only inconsistent nodes sit in the priority queue.
 * - key(s) = [min(g, rhs) + h(start, s) + km,  min(g, rhs)]  (compared lexicographically).
 * - km: when the robot moves, h(start, .) shrinks for every node. Instead of re-keying the whole
 *   queue, we add the distance moved to km - a constant offset that keeps old keys comparable.
 *
 * This is the "optimized D* Lite" from Koenig & Likhachev (2002), on a 4-connected grid
 * where every move costs 1 and blocked cells cost infinity.
 *
 * PSEUDOCODE:
 * initialize rhs and g values to infinity
 * rhs(goal) = 0
 * put goal in priority queue
 * compute_shortest_path()
 * while start != goal:
 *   move to best neighbor
 *   if edge costs change:
 *     km += h(last_start, start)
 *     update rhs values of affected nodes
 *     compute_shortest_path()   <- only re-touches nodes affected by the change
 */
package algorithms.graph.advanced;
// These aren't separate algorithms so much as OPTIMIZATIONS/ADAPTATIONS of Dijkstra's and A* for specific real-world constraints (memory, dynamic changes, precomputation budget, grid structure).

import java.util.*;

public class DStarLite {

    static final double INF = Double.POSITIVE_INFINITY;
    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private final int rows, cols;
    private final boolean[][] blocked; // what the robot currently BELIEVES about the map
    private final double[][] g, rhs;
    private int startR, startC;
    private final int goalR, goalC;
    private double km = 0;

    // Priority queue with lazy deletion: openKey holds the CURRENT key of every node in the
    // open list. A heap entry is valid only if it matches openKey (stale entries are skipped).
    private final PriorityQueue<double[]> heap = new PriorityQueue<>(DStarLite::compareKeys);
    private final Map<Integer, double[]> openKey = new HashMap<>();

    /** Number of nodes popped and processed - shows how little work replanning needs. */
    public int expansions = 0;

    public DStarLite(boolean[][] knownBlocked, int startR, int startC, int goalR, int goalC) {
        this.rows = knownBlocked.length;
        this.cols = knownBlocked[0].length;
        this.blocked = new boolean[rows][cols];
        for (int r = 0; r < rows; r++) this.blocked[r] = knownBlocked[r].clone();
        this.g = new double[rows][cols];
        this.rhs = new double[rows][cols];
        for (double[] row : g) Arrays.fill(row, INF);
        for (double[] row : rhs) Arrays.fill(row, INF);
        this.startR = startR;
        this.startC = startC;
        this.goalR = goalR;
        this.goalC = goalC;

        rhs[goalR][goalC] = 0;
        insertOrUpdate(goalR, goalC, calculateKey(goalR, goalC));
    }

    // ---------- helpers ----------

    private static int compareKeys(double[] a, double[] b) {
        int c = Double.compare(a[0], b[0]);
        return c != 0 ? c : Double.compare(a[1], b[1]);
    }

    private int id(int r, int c) {
        return r * cols + c;
    }

    private double h(int r1, int c1, int r2, int c2) {
        return Math.abs(r1 - r2) + Math.abs(c1 - c2); // Manhattan: admissible on a 4-connected grid
    }

    private boolean inside(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    private double cost(int r1, int c1, int r2, int c2) {
        return (blocked[r1][c1] || blocked[r2][c2]) ? INF : 1;
    }

    private double[] calculateKey(int r, int c) {
        double m = Math.min(g[r][c], rhs[r][c]);
        return new double[]{m + h(startR, startC, r, c) + km, m};
    }

    private void insertOrUpdate(int r, int c, double[] key) {
        openKey.put(id(r, c), key);
        heap.add(new double[]{key[0], key[1], r, c});
    }

    private void removeFromOpen(int r, int c) {
        openKey.remove(id(r, c)); // heap entry becomes stale and is skipped later
    }

    // Drops stale heap entries so the top is a real open node
    private double[] top() {
        while (!heap.isEmpty()) {
            double[] e = heap.peek();
            double[] current = openKey.get(id((int) e[2], (int) e[3]));
            if (current != null && current[0] == e[0] && current[1] == e[1]) return e;
            heap.poll();
        }
        return null;
    }

    // Recompute rhs from neighbors (never for the goal), then fix queue membership
    private void updateVertex(int r, int c) {
        if (r != goalR || c != goalC) {
            double best = INF;
            for (int[] d : DIRS) {
                int nr = r + d[0], nc = c + d[1];
                if (inside(nr, nc)) best = Math.min(best, cost(r, c, nr, nc) + g[nr][nc]);
            }
            rhs[r][c] = best;
        }
        if (g[r][c] != rhs[r][c]) insertOrUpdate(r, c, calculateKey(r, c));
        else removeFromOpen(r, c);
    }

    // ---------- the core ----------

    public void computeShortestPath() {
        // Paper's loop condition: TopKey < key(start)  OR  rhs(start) > g(start)
        while (true) {
            double[] topEntry = top();
            if (topEntry == null) break; // empty queue = TopKey is [inf, inf]
            boolean topBeforeStart = compareKeys(topEntry, calculateKey(startR, startC)) < 0;
            boolean startUnderSettled = rhs[startR][startC] > g[startR][startC];
            if (!topBeforeStart && !startUnderSettled) break;

            int r = (int) topEntry[2], c = (int) topEntry[3];
            double[] kOld = {topEntry[0], topEntry[1]};
            double[] kNew = calculateKey(r, c);
            expansions++;

            if (compareKeys(kOld, kNew) < 0) {
                insertOrUpdate(r, c, kNew);              // key is outdated (km changed): re-queue
            } else if (g[r][c] > rhs[r][c]) {
                g[r][c] = rhs[r][c];                     // overconsistent: lower g, like Dijkstra
                removeFromOpen(r, c);
                for (int[] d : DIRS) {
                    int nr = r + d[0], nc = c + d[1];
                    if (inside(nr, nc)) updateVertex(nr, nc);
                }
            } else {
                g[r][c] = INF;                           // underconsistent: path got worse, raise g
                updateVertex(r, c);
                for (int[] d : DIRS) {
                    int nr = r + d[0], nc = c + d[1];
                    if (inside(nr, nc)) updateVertex(nr, nc);
                }
            }
        }
    }

    /** Cost from the current start to the goal under the current map belief. */
    public double costToGoal() {
        return rhs[startR][startC];
    }

    /** Best next cell from the current start, or null if the goal is unreachable. */
    public int[] nextStep() {
        double best = INF;
        int[] step = null;
        for (int[] d : DIRS) {
            int nr = startR + d[0], nc = startC + d[1];
            if (!inside(nr, nc)) continue;
            double v = cost(startR, startC, nr, nc) + g[nr][nc];
            if (v < best) {
                best = v;
                step = new int[]{nr, nc};
            }
        }
        return step;
    }

    /** The robot moved: new start. km grows by how far the heuristic origin shifted. */
    public void moveStart(int r, int c) {
        km += h(startR, startC, r, c);
        startR = r;
        startC = c;
    }

    /** A cell turned out to be blocked (or free). Only its neighborhood is updated. */
    public void setBlocked(int r, int c, boolean isBlocked) {
        if (blocked[r][c] == isBlocked) return;
        blocked[r][c] = isBlocked;
        updateVertex(r, c);
        for (int[] d : DIRS) {
            int nr = r + d[0], nc = c + d[1];
            if (inside(nr, nc)) updateVertex(nr, nc);
        }
    }

    // ---------- demo: a robot that only sees adjacent cells ----------

    /** Runs the robot from start to goal on trueMap, starting with an empty belief. Returns the path. */
    public static List<int[]> navigate(boolean[][] trueMap, int sr, int sc, int gr, int gc, boolean verbose) {
        int R = trueMap.length, C = trueMap[0].length;
        DStarLite planner = new DStarLite(new boolean[R][C], sr, sc, gr, gc); // assumes all free at first
        planner.computeShortestPath();
        List<int[]> path = new ArrayList<>();
        path.add(new int[]{sr, sc});
        int r = sr, c = sc;

        while (r != gr || c != gc) {
            // Sense the 4 neighbors; update the belief if something differs
            boolean changed = false;
            for (int[] d : DIRS) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < R && nc >= 0 && nc < C && trueMap[nr][nc] != planner.blocked[nr][nc]) {
                    planner.setBlocked(nr, nc, trueMap[nr][nc]);
                    changed = true;
                    if (verbose) System.out.println("  sensed wall at (" + nr + "," + nc + ") -> replanning");
                }
            }
            if (changed) planner.computeShortestPath();

            if (planner.costToGoal() == INF) {
                if (verbose) System.out.println("  goal is unreachable!");
                return null;
            }
            int[] step = planner.nextStep();
            r = step[0];
            c = step[1];
            planner.moveStart(r, c);
            path.add(step);
        }
        if (verbose) System.out.println("  node expansions in total: " + planner.expansions);
        return path;
    }

    public static void main(String[] args) {
        System.out.println("--- D* Lite (robot on an unknown grid) ---");
        // 1 = wall. The robot does NOT know about the walls until it is next to them.
        int[][] map = {
            {0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 1, 0},
            {1, 1, 1, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };
        boolean[][] trueMap = new boolean[map.length][map[0].length];
        for (int r = 0; r < map.length; r++)
            for (int c = 0; c < map[0].length; c++) trueMap[r][c] = map[r][c] == 1;

        List<int[]> path = navigate(trueMap, 2, 0, 4, 0, true);
        StringBuilder sb = new StringBuilder();
        for (int[] p : path) sb.append("(").append(p[0]).append(",").append(p[1]).append(") ");
        System.out.println("Path taken (" + (path.size() - 1) + " moves): " + sb);

        // Fully known map: D* Lite cost must equal a plain BFS shortest path
        DStarLite known = new DStarLite(trueMap, 2, 0, 4, 0);
        known.computeShortestPath();
        System.out.println("With the full map known up front, cost = " + known.costToGoal() + " (BFS shortest path = 10)");
    }
}
