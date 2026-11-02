package geometry.clipping;

import java.util.ArrayList;
import java.util.List;

/**
 * CLIPPING (Cohen-Sutherland line clipping, Sutherland-Hodgman polygon clipping)
 *
 * WHAT IT IS:
 * Cutting geometry down to the part inside a window - the step every 2D/3D renderer
 * runs before drawing (anything outside the screen or view volume is discarded).
 *
 * WHEN TO USE THIS:
 * - Graphics pipelines (GPUs clip triangles against the view frustum), map tile
 *   rendering, GIS "intersect with region", CAD, UI toolkits clipping to a widget.
 *
 * 1. COHEN-SUTHERLAND (segment vs axis-aligned rectangle)
 *    Give each endpoint a 4-bit OUTCODE: LEFT, RIGHT, BOTTOM, TOP of the window.
 *      both codes 0            -> fully inside: accept
 *      (code1 & code2) != 0    -> both on the same outside side: reject
 *      otherwise               -> move an outside endpoint to the window edge it
 *                                 crosses (line intersection) and repeat
 *    The bit tests make the common cases (all in / all out) nearly free.
 *
 * 2. SUTHERLAND-HODGMAN (polygon vs CONVEX clip polygon)
 *    Clip the subject polygon against each clip EDGE in turn (each edge defines an
 *    inside half-plane). Walking the subject's edges S -> E:
 *      S in,  E in   -> output E
 *      S in,  E out  -> output the intersection
 *      S out, E in   -> output the intersection, then E
 *      S out, E out  -> output nothing
 *    The output of one edge is the input to the next. Works for any subject polygon
 *    (concave subjects may produce degenerate zero-width "bridges"); the clip polygon
 *    must be convex (Weiler-Atherton / Greiner-Hormann handle general clips).
 *
 * COMPLEXITY:
 * Cohen-Sutherland: O(1) per segment (at most 4 clipping steps).
 * Sutherland-Hodgman: O(n * m) for n subject and m clip vertices.
 */
public class Clipping {

    private static final int INSIDE = 0, LEFT = 1, RIGHT = 2, BOTTOM = 4, TOP = 8;

    private static int outcode(double x, double y, double xmin, double ymin, double xmax, double ymax) {
        int code = INSIDE;
        if (x < xmin) code |= LEFT; else if (x > xmax) code |= RIGHT;
        if (y < ymin) code |= BOTTOM; else if (y > ymax) code |= TOP;
        return code;
    }

    /** Clips segment (x0,y0)-(x1,y1) to the rectangle; returns {x0, y0, x1, y1} or null if nothing is inside. */
    public static double[] cohenSutherland(double x0, double y0, double x1, double y1,
                                           double xmin, double ymin, double xmax, double ymax) {
        int c0 = outcode(x0, y0, xmin, ymin, xmax, ymax), c1 = outcode(x1, y1, xmin, ymin, xmax, ymax);
        while (true) {
            if ((c0 | c1) == 0) return new double[]{x0, y0, x1, y1};   // trivially accept
            if ((c0 & c1) != 0) return null;                            // trivially reject
            int out = c0 != 0 ? c0 : c1;                                // pick an endpoint outside
            double x, y;
            if ((out & TOP) != 0)         { x = x0 + (x1 - x0) * (ymax - y0) / (y1 - y0); y = ymax; }
            else if ((out & BOTTOM) != 0) { x = x0 + (x1 - x0) * (ymin - y0) / (y1 - y0); y = ymin; }
            else if ((out & RIGHT) != 0)  { y = y0 + (y1 - y0) * (xmax - x0) / (x1 - x0); x = xmax; }
            else                          { y = y0 + (y1 - y0) * (xmin - x0) / (x1 - x0); x = xmin; }
            if (out == c0) { x0 = x; y0 = y; c0 = outcode(x0, y0, xmin, ymin, xmax, ymax); }
            else           { x1 = x; y1 = y; c1 = outcode(x1, y1, xmin, ymin, xmax, ymax); }
        }
    }

    private static double cross(double[] a, double[] b, double[] p) {
        return (b[0] - a[0]) * (p[1] - a[1]) - (b[1] - a[1]) * (p[0] - a[0]);
    }

    private static double[] intersect(double[] s, double[] e, double[] a, double[] b) {
        double c1 = cross(a, b, s), c2 = cross(a, b, e);
        double t = c1 / (c1 - c2);                                      // where the sign changes along s -> e
        return new double[]{s[0] + t * (e[0] - s[0]), s[1] + t * (e[1] - s[1])};
    }

    /** Clips `subject` against a CONVEX, counter-clockwise `clip` polygon. */
    public static List<double[]> sutherlandHodgman(List<double[]> subject, List<double[]> clip) {
        List<double[]> output = new ArrayList<>(subject);
        for (int i = 0; i < clip.size() && !output.isEmpty(); i++) {
            double[] a = clip.get(i), b = clip.get((i + 1) % clip.size()); // inside = left of a -> b
            List<double[]> input = output;
            output = new ArrayList<>();
            for (int j = 0; j < input.size(); j++) {
                double[] s = input.get((j - 1 + input.size()) % input.size()), e = input.get(j);
                boolean sIn = cross(a, b, s) >= 0, eIn = cross(a, b, e) >= 0;
                if (eIn) {
                    if (!sIn) output.add(intersect(s, e, a, b));
                    output.add(e);
                } else if (sIn) {
                    output.add(intersect(s, e, a, b));
                }
            }
        }
        return output;
    }

    public static double area(List<double[]> poly) {
        double s = 0;
        for (int i = 0; i < poly.size(); i++) {
            double[] p = poly.get(i), q = poly.get((i + 1) % poly.size());
            s += p[0] * q[1] - q[0] * p[1];
        }
        return Math.abs(s) / 2;
    }

    public static void main(String[] args) {
        System.out.println("--- CLIPPING DEMO ---");
        // window [0, 10] x [0, 10]
        double[][] segs = {{-5, 5, 15, 5}, {2, 2, 8, 8}, {-5, -5, -1, 20}, {-2, 8, 8, 18}};
        for (double[] s : segs) {
            double[] c = cohenSutherland(s[0], s[1], s[2], s[3], 0, 0, 10, 10);
            System.out.printf("segment (%.0f,%.0f)-(%.0f,%.0f) -> %s%n", s[0], s[1], s[2], s[3],
                    c == null ? "rejected" : String.format("(%.1f,%.1f)-(%.1f,%.1f)", c[0], c[1], c[2], c[3]));
        }
        // (-5,5)-(15,5) -> (0,5)-(10,5); the inside one is unchanged; the left one is rejected;
        // (-2,8)-(8,18) only touches the corner (0,10), so it clips to a single point

        List<double[]> triangle = List.of(new double[]{-5, -2}, new double[]{15, -2}, new double[]{5, 14});
        List<double[]> window = List.of(new double[]{0, 0}, new double[]{10, 0}, new double[]{10, 10}, new double[]{0, 10});
        List<double[]> clipped = sutherlandHodgman(triangle, window);
        System.out.print("\nTriangle clipped to the window: ");
        for (double[] p : clipped) System.out.printf("(%.2f, %.2f) ", p[0], p[1]);
        System.out.printf("%narea %.3f (triangle %.1f, window %.1f)%n", area(clipped), area(triangle), area(window));
    }
}
