package geometry.polygon;

/**
 * POLYGON & DISTANCE PRIMITIVES (Shoelace area, centroid, orientation, point-segment distance)
 *
 * WHAT IT IS:
 * The small formulas most geometry code is built from.
 *
 * 1. SHOELACE FORMULA (area of a simple polygon from its vertices, in order):
 *      2 * signedArea = sum over edges of (x_i * y_{i+1} - x_{i+1} * y_i)
 *    Each term is the signed area of the triangle (origin, v_i, v_{i+1}) (a cross
 *    product); parts outside the polygon cancel out. POSITIVE = vertices are
 *    counter-clockwise, NEGATIVE = clockwise, so the sign gives the ORIENTATION.
 *    Works for any simple (non-self-intersecting) polygon, convex or not.
 *
 * 2. PICK'S THEOREM (lattice polygons): area = interior + boundary/2 - 1, so the
 *    number of integer points strictly inside = area - boundary/2 + 1, where
 *    boundary points on an edge = gcd(|dx|, |dy|).
 *
 * 3. CENTROID (centre of mass of the polygon's area):
 *      Cx = sum (x_i + x_{i+1}) * cross_i / (6A),   Cy likewise with y.
 *
 * 4. POINT-TO-SEGMENT DISTANCE:
 *    Project P onto the line through A and B:  t = ((P - A) . (B - A)) / |B - A|^2.
 *    CLAMP t to [0, 1] (the closest point can't be past an endpoint), then measure
 *    the distance from P to A + t (B - A). Without the clamp you'd get the distance
 *    to the infinite LINE instead.
 *
 * WHEN TO USE THIS:
 * - GIS (area of a land parcel), CAD, games (collision, "closest point on a path"),
 *   snapping GPS points to roads, simplifying polylines (Ramer-Douglas-Peucker
 *   uses point-segment distance), mesh processing.
 *
 * COMPLEXITY:
 * Area, centroid, perimeter, Pick counts: O(n). Point-segment distance: O(1).
 */
public class PolygonGeometry {

    /** Signed area: positive if the vertices are counter-clockwise. */
    public static double signedArea(double[][] poly) {
        double twice = 0;
        int n = poly.length;
        for (int i = 0; i < n; i++) {
            double[] a = poly[i], b = poly[(i + 1) % n];
            twice += a[0] * b[1] - b[0] * a[1];
        }
        return twice / 2;
    }

    public static double area(double[][] poly) { return Math.abs(signedArea(poly)); }

    public static boolean isCounterClockwise(double[][] poly) { return signedArea(poly) > 0; }

    public static double perimeter(double[][] poly) {
        double p = 0;
        for (int i = 0; i < poly.length; i++) {
            double[] a = poly[i], b = poly[(i + 1) % poly.length];
            p += Math.hypot(b[0] - a[0], b[1] - a[1]);
        }
        return p;
    }

    public static double[] centroid(double[][] poly) {
        double a = signedArea(poly), cx = 0, cy = 0;
        if (a == 0) throw new IllegalArgumentException("degenerate polygon (zero area)");
        for (int i = 0; i < poly.length; i++) {
            double[] p = poly[i], q = poly[(i + 1) % poly.length];
            double cross = p[0] * q[1] - q[0] * p[1];
            cx += (p[0] + q[0]) * cross;
            cy += (p[1] + q[1]) * cross;
        }
        return new double[]{cx / (6 * a), cy / (6 * a)};
    }

    /** Integer points ON the boundary of a polygon with integer vertices. */
    public static long boundaryLatticePoints(long[][] poly) {
        long b = 0;
        for (int i = 0; i < poly.length; i++) {
            long[] p = poly[i], q = poly[(i + 1) % poly.length];
            b += gcd(Math.abs(q[0] - p[0]), Math.abs(q[1] - p[1]));
        }
        return b;
    }

    /** Integer points strictly INSIDE (Pick's theorem). */
    public static long interiorLatticePoints(long[][] poly) {
        long twiceArea = 0;
        for (int i = 0; i < poly.length; i++) {
            long[] p = poly[i], q = poly[(i + 1) % poly.length];
            twiceArea += p[0] * q[1] - q[0] * p[1];
        }
        twiceArea = Math.abs(twiceArea);
        // A = I + B/2 - 1  ->  I = (2A - B + 2) / 2
        return (twiceArea - boundaryLatticePoints(poly) + 2) / 2;
    }

    private static long gcd(long a, long b) { while (b != 0) { long t = a % b; a = b; b = t; } return a; }

    /** Shortest distance from point p to the segment ab. */
    public static double pointSegmentDistance(double[] p, double[] a, double[] b) {
        double dx = b[0] - a[0], dy = b[1] - a[1];
        double len2 = dx * dx + dy * dy;
        if (len2 == 0) return Math.hypot(p[0] - a[0], p[1] - a[1]);   // a == b: segment is a point
        double t = ((p[0] - a[0]) * dx + (p[1] - a[1]) * dy) / len2;  // projection parameter
        t = Math.max(0, Math.min(1, t));                               // clamp onto the segment
        double cx = a[0] + t * dx, cy = a[1] + t * dy;
        return Math.hypot(p[0] - cx, p[1] - cy);
    }

    /** Distance to the infinite line through a and b (for comparison). */
    public static double pointLineDistance(double[] p, double[] a, double[] b) {
        double dx = b[0] - a[0], dy = b[1] - a[1];
        return Math.abs(dx * (p[1] - a[1]) - dy * (p[0] - a[0])) / Math.hypot(dx, dy);
    }

    public static void main(String[] args) {
        System.out.println("--- POLYGON GEOMETRY DEMO ---");
        double[][] lShape = {{0, 0}, {4, 0}, {4, 1}, {1, 1}, {1, 3}, {0, 3}}; // non-convex "L", counter-clockwise
        System.out.println("L-shape area " + area(lShape) + " (expected 4 + 2 = 6), CCW? " + isCounterClockwise(lShape));
        double[] c = centroid(lShape);
        System.out.printf("centroid (%.3f, %.3f), perimeter %.1f%n", c[0], c[1], perimeter(lShape));

        double[][] reversed = {{0, 3}, {1, 3}, {1, 1}, {4, 1}, {4, 0}, {0, 0}};
        System.out.println("Same polygon clockwise: signed area " + signedArea(reversed));

        long[][] triangle = {{0, 0}, {4, 0}, {0, 4}};
        System.out.println("Lattice triangle (0,0),(4,0),(0,4): boundary points " + boundaryLatticePoints(triangle)
                + ", interior points " + interiorLatticePoints(triangle)); // 12, 3

        double[] a = {0, 0}, b = {10, 0};
        System.out.println("\nSegment (0,0)-(10,0):");
        for (double[] p : new double[][]{{5, 3}, {-3, 4}, {13, -4}}) {
            System.out.printf("  point (%.0f, %.0f): to segment %.1f, to infinite line %.1f%n", p[0], p[1], pointSegmentDistance(p, a, b), pointLineDistance(p, a, b));
        }
        // (5,3): 3 and 3;  (-3,4): 5 vs 4;  (13,-4): 5 vs 4 - clamping matters past the ends
    }
}
