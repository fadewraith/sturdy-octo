package geometry.convexhull;

import java.util.Arrays;

/**
 * CONVEX HULL - ANDREW'S MONOTONE CHAIN
 *
 * WHAT IT IS:
 * The convex hull is the smallest convex polygon containing all the points (a
 * rubber band snapped around nails). Monotone chain builds it in O(n log n) and is
 * the most robust and simplest of the classic algorithms - usually the one to
 * write in practice.
 *
 * WHEN TO USE THIS:
 * - Collision detection, shape analysis, the first step of many geometry
 *   algorithms (diameter via rotating calipers, minimum bounding box).
 * - Compare: GrahamScan.java (sorts by ANGLE around a pivot - needs care with
 *   collinear points and atan2/precision), JarvisMarch.java (O(n h), good when the
 *   hull has few points h).
 *
 * STRATEGY:
 * 1. Sort points by x, then by y. (No angles - only comparisons and cross products.)
 * 2. LOWER hull: go left to right; before adding a point, pop from the hull while
 *    the last two hull points and the new point make a NON-left turn
 *    (cross <= 0), i.e. the middle point is not on the hull.
 * 3. UPPER hull: the same, right to left.
 * 4. Join them (dropping the duplicated endpoints).
 * cross(o, a, b) = (a - o) x (b - o): > 0 left turn (counter-clockwise),
 * < 0 right turn, = 0 collinear. Using `<= 0` removes collinear points from the
 * hull edges; use `< 0` to keep them.
 * Exact with integer coordinates (long arithmetic), so no precision issues.
 *
 * COMPLEXITY:
 * Time: O(n log n) for the sort, O(n) for the two passes. Space: O(n).
 */
public class MonotoneChain {

    public static long cross(long[] o, long[] a, long[] b) {
        return (a[0] - o[0]) * (b[1] - o[1]) - (a[1] - o[1]) * (b[0] - o[0]);
    }

    /** Hull vertices in counter-clockwise order starting from the lowest-leftmost point, no collinear points. */
    public static long[][] convexHull(long[][] input) {
        long[][] pts = input.clone();
        Arrays.sort(pts, (p, q) -> p[0] != q[0] ? Long.compare(p[0], q[0]) : Long.compare(p[1], q[1]));
        // remove duplicate points
        int m = 0;
        for (int i = 0; i < pts.length; i++) if (i == 0 || pts[i][0] != pts[i - 1][0] || pts[i][1] != pts[i - 1][1]) pts[m++] = pts[i];
        if (m <= 2) return Arrays.copyOf(pts, m);

        long[][] hull = new long[2 * m][];
        int k = 0;
        for (int i = 0; i < m; i++) {                           // lower hull
            while (k >= 2 && cross(hull[k - 2], hull[k - 1], pts[i]) <= 0) k--;
            hull[k++] = pts[i];
        }
        for (int i = m - 2, lower = k + 1; i >= 0; i--) {       // upper hull
            while (k >= lower && cross(hull[k - 2], hull[k - 1], pts[i]) <= 0) k--;
            hull[k++] = pts[i];
        }
        return Arrays.copyOf(hull, k - 1);                      // last point equals the first
    }

    public static void main(String[] args) {
        System.out.println("--- MONOTONE CHAIN CONVEX HULL DEMO ---");
        long[][] pts = {{0, 0}, {1, 1}, {2, 2}, {4, 4}, {0, 3}, {1, 2}, {3, 1}, {3, 3}, {2, 0}, {4, 0}};
        long[][] hull = convexHull(pts);
        StringBuilder sb = new StringBuilder();
        for (long[] p : hull) sb.append("(").append(p[0]).append(",").append(p[1]).append(") ");
        System.out.println("Hull (counter-clockwise): " + sb);
        // (0,0) (4,0) (4,4) (0,3) - (2,0) is collinear on the bottom edge, so it's dropped

        long[][] line = {{0, 0}, {1, 1}, {2, 2}, {3, 3}};
        System.out.println("All collinear -> hull is the 2 endpoints: " + Arrays.deepToString(convexHull(line)));
    }
}
