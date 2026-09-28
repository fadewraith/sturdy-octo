package algorithms.divideandconquer;

import java.util.Arrays;

/**
 * CLOSEST PAIR OF POINTS
 * 
 * WHAT IT IS:
 * Given a set of points on a 2D plane, find the two points that are closest to each other.
 * 
 * WHEN TO USE THIS:
 * - Air traffic control (detecting planes that are dangerously close).
 * - Brute force checks every pair (N^2). Divide & Conquer reduces this to O(N log N).
 * 
 * STRATEGY:
 * 1. Sort all points by X coordinate.
 * 2. Divide the points into two halves by a vertical line down the middle.
 * 3. Recursively find the shortest distance in the Left half (dLeft) and Right half (dRight).
 * 4. Let `d = min(dLeft, dRight)`.
 * 5. TRICKY PART: The actual closest pair might straddle the dividing line!
 *    We create a "strip" of width `2d` centered on the line, gather all points in it, 
 *    sort them by Y coordinate, and check distances strictly within the strip.
 *    (Geometry proves we only ever need to check at most 7 neighbors in this strip, making it O(N)!)
 * 
 * COMPLEXITY:
 * Time: O(N log N)
 * Space: O(N)
 */
public class ClosestPairOfPoints {

    static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static double closestPair(Point[] points) {
        // Sort points by X coordinate
        Arrays.sort(points, (p1, p2) -> p1.x - p2.x);
        return closestUtil(points, 0, points.length - 1);
    }

    private static double closestUtil(Point[] points, int left, int right) {
        // Base case: If 3 or fewer points, just use brute force
        if (right - left <= 3) {
            return bruteForce(points, left, right);
        }

        // Divide
        int mid = left + (right - left) / 2;
        Point midPoint = points[mid];

        // Conquer
        double dl = closestUtil(points, left, mid);
        double dr = closestUtil(points, mid + 1, right);
        double d = Math.min(dl, dr);

        // Build the strip array that contains points close to the middle line
        Point[] strip = new Point[right - left + 1];
        int j = 0;
        for (int i = left; i <= right; i++) {
            if (Math.abs(points[i].x - midPoint.x) < d) {
                strip[j++] = points[i];
            }
        }

        // Find the closest points in the strip
        return Math.min(d, stripClosest(strip, j, d));
    }

    private static double stripClosest(Point[] strip, int size, double d) {
        double min = d;
        // Sort strip by Y coordinate
        Arrays.sort(strip, 0, size, (p1, p2) -> p1.y - p2.y);

        // Geometry trick: the inner loop runs AT MOST 7 times!
        for (int i = 0; i < size; ++i) {
            for (int j = i + 1; j < size && (strip[j].y - strip[i].y) < min; ++j) {
                min = Math.min(min, dist(strip[i], strip[j]));
            }
        }
        return min;
    }

    private static double bruteForce(Point[] p, int start, int end) {
        double min = Double.MAX_VALUE;
        for (int i = start; i <= end; ++i) {
            for (int j = i + 1; j <= end; ++j) {
                min = Math.min(min, dist(p[i], p[j]));
            }
        }
        return min;
    }

    private static double dist(Point p1, Point p2) {
        return Math.sqrt((p1.x - p2.x)*(p1.x - p2.x) + (p1.y - p2.y)*(p1.y - p2.y));
    }

    public static void main(String[] args) {
        System.out.println("--- CLOSEST PAIR OF POINTS DEMO ---");
        Point[] points = {
            new Point(2, 3), new Point(12, 30),
            new Point(40, 50), new Point(5, 1),
            new Point(12, 10), new Point(3, 4)
        };
        
        System.out.println("The smallest distance is " + String.format("%.4f", closestPair(points)));
        // Expected: Distance between (2,3) and (3,4) is sqrt(1^2 + 1^2) = 1.4142
    }
}
