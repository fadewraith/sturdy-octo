package geometry.convexhull;

import java.util.*;

/**
 * WHAT IT IS: Jarvis March (Gift Wrapping) algorithm for finding convex hull.
 * STRATEGY: Start with leftmost point. Repeatedly find the point that has the smallest polar angle with respect to the current point, wrapping around the set of points.
 * TIME/SPACE COMPLEXITY: Time: O(n*h) where n is points and h is hull size. Space: O(1) beyond input/output.
 * REAL-WORLD ANALOGY / USE CASE: Gift wrapping around objects.
 * WHEN TO USE / COMBINATION: Good when the hull size 'h' is expected to be very small. Compare to Graham Scan (O(n log n)).
 * 
 * PSEUDOCODE:
 * 1. Initialize p = leftmost point.
 * 2. Do:
 *      Add p to hull.
 *      q = next point (e.g., (p+1)%n)
 *      For each point i:
 *          If orientation(p, i, q) is counterclockwise, update q = i.
 *      p = q
 * 3. While p != leftmost point.
 */
public class JarvisMarch {

    static class Point {
        int x, y;
        Point(int x, int y) { this.x = x; this.y = y; }
        @Override
        public String toString() { return "(" + x + ", " + y + ")"; }
    }

    public static List<Point> convexHull(Point[] points) {
        // 1. Remove duplicate points. With duplicates the walk can hop onto a copy
        //    of the start point and never get back to the original -> infinite loop.
        List<Point> unique = new ArrayList<>();
        for (Point pt : points) {
            boolean seen = false;
            for (Point u : unique) {
                if (u.x == pt.x && u.y == pt.y) { seen = true; break; }
            }
            if (!seen) unique.add(pt);
        }
        int n = unique.size();
        if (n < 3) return unique;

        List<Point> hull = new ArrayList<>();

        // 2. Start at the leftmost point (lowest y on a tie) - it is always on the hull
        int l = 0;
        for (int i = 1; i < n; i++) {
            Point a = unique.get(i), b = unique.get(l);
            if (a.x < b.x || (a.x == b.x && a.y < b.y)) l = i;
        }

        int p = l, q;
        do {
            hull.add(unique.get(p));
            q = (p + 1) % n;
            for (int i = 0; i < n; i++) {
                if (i == p) continue;
                int o = orientation(unique.get(p), unique.get(i), unique.get(q));
                // Take i if it is more counter-clockwise than q.
                // If collinear, take the FARTHER one so middle points on an edge are skipped.
                if (o == 2 || (o == 0 && distSq(unique.get(p), unique.get(i)) > distSq(unique.get(p), unique.get(q)))) {
                    q = i;
                }
            }
            p = q;
        } while (p != l && hull.size() <= n); // size guard: a hull can never have more than n points

        return hull;
    }

    private static int distSq(Point a, Point b) {
        return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
    }

    private static int orientation(Point p, Point q, Point r) {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val == 0) return 0; // colinear
        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    public static void main(String[] args) {
        System.out.println("--- Jarvis March Convex Hull ---");
        Point[] points = {
            new Point(0, 3), new Point(2, 2), new Point(1, 1),
            new Point(2, 1), new Point(3, 0), new Point(0, 0),
            new Point(3, 3)
        };
        List<Point> hull = convexHull(points);
        System.out.println("Hull Points:");
        for (Point p : hull) System.out.println(p);
    }
}
