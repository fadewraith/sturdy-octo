package geometry.lines;

import java.util.Arrays;
import java.util.Comparator;

/**
 * WHAT IT IS: Closest Pair of Points (Divide and Conquer).
 * STRATEGY: Sort points by X. Divide set in half, find closest pair in left and right halves. Let d = min(dL, dR). Look for pairs crossing the dividing line with distance < d, by sorting points in the strip by Y.
 * TIME/SPACE COMPLEXITY: Time: O(n log n) compared to naive O(n^2). Space: O(n) for recursive calls and strip array.
 * REAL-WORLD ANALOGY / USE CASE: Air traffic control to find airplanes too close to each other.
 * WHEN TO USE / COMBINATION: When searching spatial coordinates for neighbors. Can also be done with Sweep Line or Quadtrees.
 * 
 * PSEUDOCODE:
 * closest(P):
 *   if |P| <= 3 return brute_force(P)
 *   mid = |P|/2
 *   dL = closest(P[0..mid])
 *   dR = closest(P[mid..end])
 *   d = min(dL, dR)
 *   strip = points within d of P[mid].x
 *   sort strip by Y
 *   for i in strip:
 *     check next 7 points in strip, update d if closer
 *   return d
 */
public class ClosestPairOfPoints {

    static class Point {
        double x, y;
        Point(double x, double y) { this.x = x; this.y = y; }
    }

    public static double closestPair(Point[] points) {
        Point[] px = points.clone();
        Point[] py = points.clone();
        Arrays.sort(px, Comparator.comparingDouble(p -> p.x));
        Arrays.sort(py, Comparator.comparingDouble(p -> p.y));
        return closestUtil(px, py, px.length);
    }

    private static double closestUtil(Point[] px, Point[] py, int n) {
        if (n <= 3) return bruteForce(px, n);

        int mid = n / 2;
        Point midPoint = px[mid];

        Point[] pyl = new Point[mid];
        Point[] pyr = new Point[n - mid];
        int li = 0, ri = 0;
        for (int i = 0; i < n; i++) {
            if (py[i].x <= midPoint.x && li < mid) {
                pyl[li++] = py[i];
            } else {
                pyr[ri++] = py[i];
            }
        }

        double dl = closestUtil(Arrays.copyOfRange(px, 0, mid), pyl, mid);
        double dr = closestUtil(Arrays.copyOfRange(px, mid, n), pyr, n - mid);
        double d = Math.min(dl, dr);

        Point[] strip = new Point[n];
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (Math.abs(py[i].x - midPoint.x) < d) {
                strip[j++] = py[i];
            }
        }

        return Math.min(d, stripClosest(strip, j, d));
    }

    private static double bruteForce(Point[] p, int n) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                double dist = dist(p[i], p[j]);
                if (dist < min) min = dist;
            }
        }
        return min;
    }

    private static double stripClosest(Point[] strip, int size, double d) {
        double min = d;
        for (int i = 0; i < size; ++i) {
            for (int j = i + 1; j < size && (strip[j].y - strip[i].y) < min; ++j) {
                double dist = dist(strip[i], strip[j]);
                if (dist < min) min = dist;
            }
        }
        return min;
    }

    private static double dist(Point p1, Point p2) {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }

    public static void main(String[] args) {
        System.out.println("--- Closest Pair of Points ---");
        Point[] P = {
            new Point(2, 3), new Point(12, 30),
            new Point(40, 50), new Point(5, 1),
            new Point(12, 10), new Point(3, 4)
        };
        System.out.println("The smallest distance is " + closestPair(P));
    }
}
