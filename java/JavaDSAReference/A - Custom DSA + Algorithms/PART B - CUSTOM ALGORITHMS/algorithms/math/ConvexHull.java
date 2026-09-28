package algorithms.math;

import java.util.Arrays;
import java.util.Comparator;

/**
 * CONVEX HULL (Graham Scan)
 * 
 * WHAT IT IS:
 * Given a set of 2D points, find the smallest convex polygon (represented by a subset 
 * of points) that encloses ALL the points in the set.
 * Imagine wrapping a rubber band around a pegboard!
 * 
 * STRATEGY (Graham Scan):
 * 1. Find the bottom-most point (lowest Y, then lowest X). This point is definitely on the hull.
 * 2. Sort the remaining points by the POLAR ANGLE they make with the bottom-most point.
 * 3. Iterate through the sorted array, pushing points to a Stack.
 * 4. If the next point makes a "Right Turn" with the top two points on the stack, 
 *    the top point is inside the hull, so we pop it! 
 *    (We only want "Left Turns" to keep a convex boundary).
 * 
 * COMPLEXITY:
 * Time: O(N log N) dominated by sorting.
 * Space: O(N) for the stack.
 */
public class ConvexHull {

    static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // A custom Stack
    private static class PointStack {
        Point[] data;
        int top = -1;
        PointStack(int capacity) { data = new Point[capacity]; }
        void push(Point p) { data[++top] = p; }
        Point pop() { return data[top--]; }
        Point peek() { return data[top]; }
        Point peekNextToTop() { return data[top - 1]; }
        boolean isEmpty() { return top == -1; }
    }

    private static Point p0; // The bottom-most point

    // Returns: > 0 for Left turn, < 0 for Right turn, 0 for Collinear
    private static int orientation(Point p, Point q, Point r) {
        return (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
    }

    // Returns square of distance (avoiding Math.sqrt for floating point precision)
    private static int distSq(Point p1, Point p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }

    public static void convexHull(Point[] points) {
        int n = points.length;
        if (n < 3) return; // Convex hull is not possible

        // 1. Find the bottommost point
        int ymin = points[0].y, min = 0;
        for (int i = 1; i < n; i++) {
            int y = points[i].y;
            // Pick bottom-most or left-most in case of tie
            if ((y < ymin) || (ymin == y && points[i].x < points[min].x)) {
                ymin = points[i].y;
                min = i;
            }
        }

        // Place the bottom-most point at first position
        Point temp = points[0];
        points[0] = points[min];
        points[min] = temp;
        p0 = points[0];

        // 2. Sort by polar angle with respect to p0
        Arrays.sort(points, 1, n, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                int o = orientation(p0, p1, p2);
                if (o == 0) { // Collinear, pick the further one!
                    return (distSq(p0, p2) >= distSq(p0, p1)) ? -1 : 1;
                }
                return (o < 0) ? -1 : 1; // Left turns first
            }
        });

        // 3. Process the sorted array
        PointStack stack = new PointStack(n);
        stack.push(points[0]);
        stack.push(points[1]);
        stack.push(points[2]);

        for (int i = 3; i < n; i++) {
            // Keep removing top while the angle formed by points next-to-top, top, 
            // and points[i] makes a non-left turn
            while (stack.top > 0 && orientation(stack.peekNextToTop(), stack.peek(), points[i]) >= 0) {
                stack.pop();
            }
            stack.push(points[i]);
        }

        // Print the hull
        System.out.println("Points in the Convex Hull:");
        while (!stack.isEmpty()) {
            Point p = stack.pop();
            System.out.println("(" + p.x + ", " + p.y + ")");
        }
    }

    public static void main(String[] args) {
        System.out.println("--- CONVEX HULL (GRAHAM SCAN) DEMO ---");
        Point[] points = {
            new Point(0, 3), new Point(1, 1), new Point(2, 2),
            new Point(4, 4), new Point(0, 0), new Point(1, 2),
            new Point(3, 1), new Point(3, 3)
        };
        convexHull(points);
        // Expected: (0,3), (4,4), (3,1), (0,0) (in some order)
    }
}
