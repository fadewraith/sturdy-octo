package geometry.lines;

/**
 * WHAT IT IS: Line Intersection detection using cross products and orientation.
 * STRATEGY: Determine if two line segments intersect by checking if the endpoints of one segment straddle the line formed by the other, and vice versa.
 * TIME/SPACE COMPLEXITY: Time: O(1). Space: O(1).
 * REAL-WORLD ANALOGY / USE CASE: Detecting collision between trajectories in gaming or robotics.
 * WHEN TO USE / COMBINATION: When doing sweep-line intersection, this is the core primitive check.
 * 
 * PSEUDOCODE:
 * def intersect(p1, q1, p2, q2):
 *    o1 = orientation(p1, q1, p2)
 *    o2 = orientation(p1, q1, q2)
 *    o3 = orientation(p2, q2, p1)
 *    o4 = orientation(p2, q2, q1)
 *    if (o1 != o2 and o3 != o4) return true
 *    if (o1 == 0 and onSegment(p1, p2, q1)) return true
 *    ... (check collinear segments)
 *    return false
 */
public class LineIntersection {

    static class Point {
        int x, y;
        Point(int x, int y) { this.x = x; this.y = y; }
    }

    static boolean onSegment(Point p, Point q, Point r) {
        if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
            q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y))
           return true;
        return false;
    }

    static int orientation(Point p, Point q, Point r) {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val == 0) return 0;
        return (val > 0) ? 1 : 2;
    }

    static boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        if (o1 != o2 && o3 != o4) return true;

        if (o1 == 0 && onSegment(p1, p2, q1)) return true;
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;

        return false;
    }

    public static void main(String[] args) {
        System.out.println("--- Line Intersection ---");
        Point p1 = new Point(1, 1), q1 = new Point(10, 1);
        Point p2 = new Point(1, 2), q2 = new Point(10, 2);
        System.out.println("Test 1 (parallel): " + doIntersect(p1, q1, p2, q2)); // false
        
        Point p3 = new Point(10, 0), q3 = new Point(0, 10);
        Point p4 = new Point(0, 0), q4 = new Point(10, 10);
        System.out.println("Test 2 (cross): " + doIntersect(p3, q3, p4, q4)); // true
        
        Point p5 = new Point(-5, -5), q5 = new Point(0, 0);
        Point p6 = new Point(1, 1), q6 = new Point(3, 3);
        System.out.println("Test 3 (collinear disjoint): " + doIntersect(p5, q5, p6, q6)); // false
    }
}
