package geometry.delaunay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * DELAUNAY TRIANGULATION (Bowyer-Watson incremental algorithm with a vertex at infinity)
 *
 * WHAT IT IS:
 * A triangulation of a point set in which NO point lies inside the CIRCUMCIRCLE of
 * any triangle (the "empty circle" property). Among all triangulations it maximises
 * the smallest angle - it avoids long skinny triangles.
 *
 * WHEN TO USE THIS:
 * - Terrain and mesh generation (GIS elevation models, finite-element meshes, games),
 *   interpolating scattered measurements, the dual of the VORONOI diagram (nearest
 *   facility regions), Euclidean minimum spanning trees (the EMST is a subgraph).
 *
 * STRATEGY (Bowyer-Watson):
 * Insert points one at a time:
 *   a. find all triangles in CONFLICT with the new point (circumcircle contains it);
 *   b. together they form a star-shaped hole; its BOUNDARY = edges belonging to
 *      exactly one conflicting triangle;
 *   c. delete those triangles and connect every boundary edge to the new point.
 *
 * THE OUTSIDE - A VERTEX AT INFINITY (instead of the textbook "super-triangle"):
 * The common shortcut wraps all points in a huge super-triangle and deletes it at
 * the end. But when three hull points are nearly collinear, a super-triangle corner
 * can land inside the circle through a hull edge, and hull triangles go missing -
 * however big the super-triangle is. Here every hull edge (p, q) instead gets a
 * GHOST triangle (p, q, INF), oriented so the outside lies on the left of p -> q.
 * A new point d conflicts with a ghost exactly when d is strictly OUTSIDE that hull
 * edge, or lies ON the edge segment. That is the limit of the circumcircle test as
 * the third vertex goes to infinity, and it needs no huge coordinates
 * (this is how CGAL does it).
 *
 * IN-CIRCLE TEST: for a counter-clockwise triangle (a, b, c), point d is inside the
 * circumcircle iff the 3x3 determinant
 *   | ax-dx  ay-dy  (ax-dx)^2 + (ay-dy)^2 |
 *   | bx-dx  by-dy  (bx-dx)^2 + (by-dy)^2 |   > 0
 *   | cx-dx  cy-dy  (cx-dx)^2 + (cy-dy)^2 |
 * Production code uses exact/adaptive arithmetic (Shewchuk's predicates); doubles
 * are fine for "random" inputs but can fail on many cocircular points (e.g. a grid).
 *
 * COMPLEXITY:
 * This simple version scans all triangles per insertion: O(n^2). With a point-location
 * structure and random insertion order it is O(n log n) expected.
 */
public class DelaunayTriangulation {

    private static final int INF = -1;

    public static double orient(double[] a, double[] b, double[] c) {
        return (b[0] - a[0]) * (c[1] - a[1]) - (b[1] - a[1]) * (c[0] - a[0]);
    }

    /** > 0 if d is strictly inside the circumcircle of the counter-clockwise triangle abc. */
    public static double inCircle(double[] a, double[] b, double[] c, double[] d) {
        double adx = a[0] - d[0], ady = a[1] - d[1], bdx = b[0] - d[0], bdy = b[1] - d[1], cdx = c[0] - d[0], cdy = c[1] - d[1];
        double ad = adx * adx + ady * ady, bd = bdx * bdx + bdy * bdy, cd = cdx * cdx + cdy * cdy;
        return adx * (bdy * cd - bd * cdy) - ady * (bdx * cd - bd * cdx) + ad * (bdx * cdy - bdy * cdx);
    }

    // Is point d in conflict with triangle t? (Ghost triangles always store INF last.)
    private static boolean conflict(double[][] p, int[] t, double[] d) {
        if (t[2] != INF) return inCircle(p[t[0]], p[t[1]], p[t[2]], d) > 0;
        double[] a = p[t[0]], b = p[t[1]];
        double o = orient(a, b, d);
        if (o > 0) return true;                                   // strictly outside this hull edge
        if (o < 0) return false;
        // on the line: conflict only if strictly between a and b
        double dot = (d[0] - a[0]) * (b[0] - a[0]) + (d[1] - a[1]) * (b[1] - a[1]);
        double len2 = (b[0] - a[0]) * (b[0] - a[0]) + (b[1] - a[1]) * (b[1] - a[1]);
        return dot > 0 && dot < len2;
    }

    /** Puts INF (if present) in the last slot, keeping the cyclic (orientation) order. */
    private static int[] normalize(int x, int y, int z) {
        if (x == INF) return new int[]{y, z, x};
        if (y == INF) return new int[]{z, x, y};
        return new int[]{x, y, z};
    }

    /** Triangles as index triples (counter-clockwise) into `points`. Duplicate points are ignored. */
    public static List<int[]> triangulate(double[][] p) {
        int n = p.length;
        List<int[]> result = new ArrayList<>();
        // find a first non-degenerate triangle
        int a = 0, b = -1, c = -1;
        for (int i = 1; i < n && b == -1; i++) if (p[i][0] != p[a][0] || p[i][1] != p[a][1]) b = i;
        if (b == -1) return result;
        for (int i = 1; i < n && c == -1; i++) if (orient(p[a], p[b], p[i]) != 0) c = i;
        if (c == -1) return result;                             // all points collinear: no triangles
        if (orient(p[a], p[b], p[c]) < 0) { int t = b; b = c; c = t; }

        List<int[]> tris = new ArrayList<>();
        tris.add(new int[]{a, b, c});
        tris.add(new int[]{b, a, INF});                         // ghosts: outside is left of each reversed edge
        tris.add(new int[]{c, b, INF});
        tris.add(new int[]{a, c, INF});
        Set<Long> inserted = new HashSet<>();
        for (int i : new int[]{a, b, c}) inserted.add(key(p[i]));

        for (int i = 0; i < n; i++) {
            if (!inserted.add(key(p[i]))) continue;             // the first three, or a duplicate point
            List<int[]> bad = new ArrayList<>(), keep = new ArrayList<>();
            for (int[] t : tris) (conflict(p, t, p[i]) ? bad : keep).add(t);
            Map<Long, int[]> edgeDir = new HashMap<>();
            Map<Long, Integer> count = new HashMap<>();
            for (int[] t : bad) {
                for (int e = 0; e < 3; e++) {
                    int u = t[e], v = t[(e + 1) % 3];
                    long k = edgeKey(u, v, n);
                    count.merge(k, 1, Integer::sum);
                    edgeDir.put(k, new int[]{u, v});           // the edge in the triangle's own direction
                }
            }
            for (Map.Entry<Long, Integer> e : count.entrySet()) {
                if (e.getValue() != 1) continue;               // interior edge of the hole
                int[] edge = edgeDir.get(e.getKey());
                keep.add(normalize(edge[0], edge[1], i));
            }
            tris = keep;
        }
        for (int[] t : tris) if (t[2] != INF) result.add(t);
        return result;
    }

    private static long key(double[] pt) { return Double.doubleToLongBits(pt[0]) * 31 + Double.doubleToLongBits(pt[1]); }

    private static long edgeKey(int u, int v, int n) {
        long x = u + 1L, y = v + 1L;                            // INF (-1) becomes 0
        return Math.min(x, y) * (n + 2L) + Math.max(x, y);
    }

    public static void main(String[] args) {
        System.out.println("--- DELAUNAY TRIANGULATION DEMO ---");
        double[][] square = {{0, 0}, {4, 0}, {4, 3}, {0, 3}, {2, 1.4}};
        List<int[]> tris = triangulate(square);
        System.out.println("Square + interior point -> " + tris.size() + " triangles:");
        for (int[] t : tris) System.out.println("  " + java.util.Arrays.toString(t));

        java.util.Random rng = new java.util.Random(6);
        double[][] pts = new double[200][];
        for (int i = 0; i < pts.length; i++) pts[i] = new double[]{rng.nextDouble() * 100, rng.nextDouble() * 100};
        List<int[]> t2 = triangulate(pts);
        int violations = 0;
        for (int[] t : t2) for (int k = 0; k < pts.length; k++)
            if (k != t[0] && k != t[1] && k != t[2] && inCircle(pts[t[0]], pts[t[1]], pts[t[2]], pts[k]) > 1e-9) violations++;
        System.out.println("200 random points -> " + t2.size() + " triangles (= 2n - 2 - hull size), empty-circle violations: " + violations);

        // nearly collinear hull points: the case that breaks the super-triangle shortcut
        double[][] flat = {{0, 0}, {50, 1e-7}, {100, 0}, {50, 40}};
        System.out.println("Nearly collinear bottom edge -> " + triangulate(flat).size() + " triangles (expected 2n - 2 - h = 3)");
    }
}
