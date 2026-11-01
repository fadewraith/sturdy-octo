/**
 * WHAT IT IS: Jump Point Search (JPS) is a highly optimized version of A* for uniform cost grids.
 * STRATEGY: It skips over uniform areas of the grid by "jumping" straight and diagonally, only adding nodes to the open list when a "forced neighbor" (like an obstacle corner) is detected.
 * TIME/SPACE COMPLEXITY: Time: Often orders of magnitude faster than A* in practice, Space: O(V) but with far fewer nodes stored in the open list.
 * REAL-WORLD ANALOGY / USE CASE: 2D tile-based video games (like RTS games) where agents need to navigate open maps with some obstacles quickly.
 * WHEN TO USE / COMBINATION: When navigating uniform-cost grid environments (like 2D arrays). Useless on arbitrary graphs or weighted grids.
 *
 * MOVEMENT MODEL used here:
 * - 8 directions. Straight step costs 1, diagonal step costs sqrt(2).
 * - A diagonal step is allowed only if BOTH orthogonal cells next to it are free
 *   (no squeezing between two walls or cutting a wall's corner).
 * JPS returns exactly the same path COST as A* under this model - it just expands far fewer nodes.
 *
 * WHY IT WORKS (symmetry breaking):
 * On an open grid there are many equally short paths. JPS commits to one canonical
 * order ("go straight/diagonal as long as nothing interesting happens") and only stops at
 * JUMP POINTS - cells where a different, possibly better, direction becomes available
 * because a wall just ended (a "forced neighbor").
 *
 * PSEUDOCODE:
 * Similar to A*, but instead of expanding immediate neighbors:
 * for each (pruned) direction from the current node:
 *   jump along direction until obstacle, map edge, goal, or forced neighbor found
 *   if valid jump point found, add it to the open list with g = g(current) + octile distance
 */
package algorithms.graph.advanced;
// These aren't separate algorithms so much as OPTIMIZATIONS/ADAPTATIONS of Dijkstra's and A* for specific real-world constraints (memory, dynamic changes, precomputation budget, grid structure).

import java.util.*;

public class JumpPointSearch {

    static final double SQRT2 = Math.sqrt(2);

    static class Point {
        int r, c;
        Point(int r, int c) { this.r = r; this.c = c; }
        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point)) return false;
            Point point = (Point) o;
            return r == point.r && c == point.c;
        }
        @Override public int hashCode() { return Objects.hash(r, c); }
        @Override public String toString() { return "(" + r + "," + c + ")"; }
    }

    /** Result of a search: the cost and the full cell-by-cell path (empty if no path). */
    static class Result {
        double cost;
        List<Point> path = new ArrayList<>();
        int expanded; // nodes taken off the open list
    }

    private final int[][] grid; // 1 = wall
    private final int R, C;
    private Point goal;

    public JumpPointSearch(int[][] grid) {
        this.grid = grid;
        this.R = grid.length;
        this.C = grid[0].length;
    }

    private boolean walkable(int r, int c) {
        return r >= 0 && r < R && c >= 0 && c < C && grid[r][c] == 0;
    }

    // Octile distance: exact cost of the best 8-directional path with no walls
    private static double octile(int r1, int c1, int r2, int c2) {
        int dr = Math.abs(r1 - r2), dc = Math.abs(c1 - c2);
        return Math.max(dr, dc) + (SQRT2 - 1) * Math.min(dr, dc);
    }

    // ---------- the jump ----------

    /**
     * Moves from (r - dr, c - dc) into (r, c) and keeps going in direction (dr, dc).
     * Returns the first jump point found, or null if we hit a wall / the edge.
     */
    private Point jump(int r, int c, int dr, int dc) {
        while (true) {
            if (!walkable(r, c)) return null;
            if (r == goal.r && c == goal.c) return new Point(r, c);

            if (dr != 0 && dc != 0) {
                // Diagonal: stop here if a straight jump from this cell finds something.
                // (A diagonal move is "two straight moves at once", so both must be checked.)
                if (jump(r + dr, c, dr, 0) != null || jump(r, c + dc, 0, dc) != null) {
                    return new Point(r, c);
                }
            } else if (dr != 0) {
                // Moving vertically: a FORCED neighbor appears when the cell beside us is open
                // but the cell beside where we came FROM was a wall (a wall just ended).
                if ((walkable(r, c - 1) && !walkable(r - dr, c - 1)) ||
                    (walkable(r, c + 1) && !walkable(r - dr, c + 1))) {
                    return new Point(r, c);
                }
            } else {
                // Moving horizontally: same idea, rotated
                if ((walkable(r - 1, c) && !walkable(r - 1, c - dc)) ||
                    (walkable(r + 1, c) && !walkable(r + 1, c - dc))) {
                    return new Point(r, c);
                }
            }

            // Keep going. A diagonal step needs both orthogonal cells free (no corner cutting).
            if (dr != 0 && dc != 0 && !(walkable(r + dr, c) && walkable(r, c + dc))) return null;
            r += dr;
            c += dc;
        }
    }

    // ---------- pruned neighbors ----------

    /** Directions worth exploring from 'node', given we arrived from 'parent' (null at the start). */
    private List<int[]> prunedDirections(Point node, Point parent) {
        List<int[]> dirs = new ArrayList<>();
        int r = node.r, c = node.c;
        if (parent == null) {
            // Start node: every legal direction
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dr == 0 && dc == 0) continue;
                    if (!walkable(r + dr, c + dc)) continue;
                    if (dr != 0 && dc != 0 && !(walkable(r + dr, c) && walkable(r, c + dc))) continue;
                    dirs.add(new int[]{dr, dc});
                }
            }
            return dirs;
        }

        int dr = Integer.signum(r - parent.r), dc = Integer.signum(c - parent.c);
        if (dr != 0 && dc != 0) {
            // Diagonal: continue diagonally plus the two straight components
            boolean vert = walkable(r + dr, c), horiz = walkable(r, c + dc);
            if (vert) dirs.add(new int[]{dr, 0});
            if (horiz) dirs.add(new int[]{0, dc});
            if (vert && horiz) dirs.add(new int[]{dr, dc});
        } else if (dr != 0) {
            // Vertical: straight ahead, plus sideways (and the diagonals ahead) when open
            boolean ahead = walkable(r + dr, c), left = walkable(r, c - 1), right = walkable(r, c + 1);
            if (ahead) {
                dirs.add(new int[]{dr, 0});
                if (left) dirs.add(new int[]{dr, -1});
                if (right) dirs.add(new int[]{dr, 1});
            }
            if (left) dirs.add(new int[]{0, -1});
            if (right) dirs.add(new int[]{0, 1});
        } else {
            // Horizontal: rotated version of the vertical case
            boolean ahead = walkable(r, c + dc), up = walkable(r - 1, c), down = walkable(r + 1, c);
            if (ahead) {
                dirs.add(new int[]{0, dc});
                if (up) dirs.add(new int[]{-1, dc});
                if (down) dirs.add(new int[]{1, dc});
            }
            if (up) dirs.add(new int[]{-1, 0});
            if (down) dirs.add(new int[]{1, 0});
        }
        return dirs;
    }

    // ---------- A* over jump points ----------

    public Result search(Point start, Point goal) {
        this.goal = goal;
        Result result = new Result();
        if (!walkable(start.r, start.c) || !walkable(goal.r, goal.c)) {
            result.cost = -1;
            return result;
        }

        Map<Point, Double> gScore = new HashMap<>();
        Map<Point, Point> parent = new HashMap<>();
        Set<Point> closed = new HashSet<>();
        PriorityQueue<Object[]> open = new PriorityQueue<>(Comparator.comparingDouble(o -> (double) o[0]));
        gScore.put(start, 0.0);
        open.add(new Object[]{octile(start.r, start.c, goal.r, goal.c), start});

        while (!open.isEmpty()) {
            Point cur = (Point) open.poll()[1];
            if (!closed.add(cur)) continue; // stale entry
            result.expanded++;

            if (cur.equals(goal)) {
                result.cost = gScore.get(cur);
                result.path = expandPath(cur, parent);
                return result;
            }

            for (int[] d : prunedDirections(cur, parent.get(cur))) {
                Point jp = jump(cur.r + d[0], cur.c + d[1], d[0], d[1]);
                if (jp == null || closed.contains(jp)) continue;
                double ng = gScore.get(cur) + octile(cur.r, cur.c, jp.r, jp.c); // straight/diagonal line
                if (ng < gScore.getOrDefault(jp, Double.POSITIVE_INFINITY)) {
                    gScore.put(jp, ng);
                    parent.put(jp, cur);
                    open.add(new Object[]{ng + octile(jp.r, jp.c, goal.r, goal.c), jp});
                }
            }
        }
        result.cost = -1; // no path
        return result;
    }

    // Jump points are connected by straight or 45-degree lines: fill in the cells between them
    private static List<Point> expandPath(Point end, Map<Point, Point> parent) {
        List<Point> jumpPoints = new ArrayList<>();
        for (Point p = end; p != null; p = parent.get(p)) jumpPoints.add(p);
        Collections.reverse(jumpPoints);
        List<Point> cells = new ArrayList<>();
        cells.add(jumpPoints.get(0));
        for (int i = 1; i < jumpPoints.size(); i++) {
            Point a = jumpPoints.get(i - 1), b = jumpPoints.get(i);
            int dr = Integer.signum(b.r - a.r), dc = Integer.signum(b.c - a.c);
            int r = a.r, c = a.c;
            while (r != b.r || c != b.c) {
                r += dr;
                c += dc;
                cells.add(new Point(r, c));
            }
        }
        return cells;
    }

    /** Kept for compatibility: returns the path cost rounded to 3 decimals, or -1 if no path. */
    public static double simpleJPSDistance(int[][] grid, Point start, Point goal) {
        double cost = new JumpPointSearch(grid).search(start, goal).cost;
        return cost < 0 ? -1 : Math.round(cost * 1000) / 1000.0;
    }

    public static void main(String[] args) {
        System.out.println("--- Jump Point Search Tests ---");
        int[][] grid = {
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0},
            {0, 1, 1, 0, 0},
            {0, 0, 0, 0, 0}
        };
        Result res = new JumpPointSearch(grid).search(new Point(0, 0), new Point(4, 4));
        System.out.println("Test 1 (Path exists): cost = " + String.format("%.3f", res.cost) + ", path = " + res.path
                + ", nodes expanded = " + res.expanded);
        System.out.println("Test 2 (Start == Goal): " + simpleJPSDistance(grid, new Point(2, 2), new Point(2, 2))); // Expected: 0
        int[][] blocked = {{0, 1}, {1, 0}};
        System.out.println("Test 3 (No path, diagonal squeeze not allowed): " + simpleJPSDistance(blocked, new Point(0, 0), new Point(1, 1))); // Expected: -1

        // Big open room: JPS expands only a handful of nodes
        int[][] room = new int[60][60];
        for (int r = 5; r < 55; r++) room[r][30] = 1; // one long wall
        Result big = new JumpPointSearch(room).search(new Point(30, 0), new Point(30, 59));
        System.out.println("Test 4 (60x60 room with a wall): cost = " + String.format("%.3f", big.cost)
                + ", nodes expanded = " + big.expanded + " (plain A* would expand hundreds)");
    }
}
