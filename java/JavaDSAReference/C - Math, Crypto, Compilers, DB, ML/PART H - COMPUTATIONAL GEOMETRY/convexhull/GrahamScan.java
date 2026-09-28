package geometry.convexhull;

import java.util.*;

/**
 * WHAT IT IS: Graham Scan algorithm to find the convex hull of a set of points.
 * STRATEGY: Find the lowest point. Sort remaining points by polar angle with the lowest point.
 * Use a stack to keep track of the convex hull, removing points that make a right turn.
 * TIME/SPACE COMPLEXITY: Time: O(N log N) due to sorting, where N is the number of points. Space: O(N) for stack.
 * REAL-WORLD ANALOGY / USE CASE: Finding the boundary of a set of coordinates (e.g., fencing a group of trees with minimum wire length).
 * WHEN TO USE / COMBINATION: When you need the convex hull in 2D efficiently. Compare to Jarvis March which is O(nh) where h is hull size.
 * 
 * PSEUDOCODE:
 * 1. Find point P0 with lowest Y (and lowest X if tied).
 * 2. Sort points by polar angle with P0.
 * 3. Initialize Stack with P0, P1, P2.
 * 4. For i = 3 to n-1:
 *      While angle between next-to-top, top, and points[i] makes a non-left turn:
 *          Pop stack
 *      Push points[i]
 * 5. Stack contains convex hull.
 */
public class GrahamScan {

    static class Point {
        int x, y;
        Point(int x, int y) { this.x = x; this.y = y; }
        @Override
        public String toString() { return "(" + x + ", " + y + ")"; }
    }

    public static List<Point> convexHull(Point[] points) {
        int n = points.length;
        if (n < 3) return Arrays.asList(points);

        int min = 0;
        for (int i = 1; i < n; i++) {
            if (points[i].y < points[min].y || (points[i].y == points[min].y && points[i].x < points[min].x)) {
                min = i;
            }
        }

        Point temp = points[0];
        points[0] = points[min];
        points[min] = temp;

        final Point p0 = points[0];
        Arrays.sort(points, 1, n, (p1, p2) -> {
            int o = orientation(p0, p1, p2);
            if (o == 0) return (distSq(p0, p2) >= distSq(p0, p1)) ? -1 : 1;
            return (o == 2) ? -1 : 1; // 2 is counterclockwise
        });

        int m = 1; 
        for (int i = 1; i < n; i++) {
            while (i < n - 1 && orientation(p0, points[i], points[i + 1]) == 0) {
                i++;
            }
            points[m] = points[i];
            m++;
        }

        if (m < 3) return new ArrayList<>();

        Stack<Point> stack = new Stack<>();
        stack.push(points[0]);
        stack.push(points[1]);
        stack.push(points[2]);

        for (int i = 3; i < m; i++) {
            while (stack.size() > 1 && orientation(nextToTop(stack), stack.peek(), points[i]) != 2) {
                stack.pop();
            }
            stack.push(points[i]);
        }

        return new ArrayList<>(stack);
    }

    private static Point nextToTop(Stack<Point> stack) {
        Point p = stack.pop();
        Point res = stack.peek();
        stack.push(p);
        return res;
    }

    private static int distSq(Point p1, Point p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }

    private static int orientation(Point p, Point q, Point r) {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val == 0) return 0; // colinear
        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    public static void main(String[] args) {
        System.out.println("--- Graham Scan Convex Hull ---");
        Point[] points = {
            new Point(0, 3), new Point(1, 1), new Point(2, 2),
            new Point(4, 4), new Point(0, 0), new Point(1, 2),
            new Point(3, 1), new Point(3, 3)
        };
        List<Point> hull = convexHull(points);
        System.out.println("Hull Points:");
        for (Point p : hull) System.out.println(p);
    }
}
