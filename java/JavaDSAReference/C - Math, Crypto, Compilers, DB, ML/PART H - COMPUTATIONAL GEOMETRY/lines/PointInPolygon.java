package geometry.lines;

/**
 * WHAT IT IS: Point in Polygon test using Ray Casting method.
 * STRATEGY: Cast a horizontal ray from the point to infinity and count how many times it crosses polygon edges. Odd means inside, even means outside.
 * TIME/SPACE COMPLEXITY: Time: O(N) where N is the number of vertices. Space: O(1).
 * REAL-WORLD ANALOGY / USE CASE: Geofencing, determining if a user is within a designated area on a map.
 * WHEN TO USE / COMBINATION: Used in GIS software. Can be optimized with bounding boxes.
 * 
 * PSEUDOCODE:
 * intersections = 0
 * For each edge in polygon:
 *   If ray intersects edge:
 *     intersections++
 * return intersections % 2 != 0
 */
public class PointInPolygon {

    static class Point {
        double x, y;
        Point(double x, double y) { this.x = x; this.y = y; }
    }

    public static boolean isInside(Point[] polygon, Point p) {
        int n = polygon.length;
        if (n < 3) return false;

        boolean inside = false;
        Point p1 = polygon[0], p2;

        for (int i = 1; i <= n; i++) {
            p2 = polygon[i % n];
            if (p.y > Math.min(p1.y, p2.y)) {
                if (p.y <= Math.max(p1.y, p2.y)) {
                    if (p.x <= Math.max(p1.x, p2.x)) {
                        if (p1.y != p2.y) {
                            double xinters = (p.y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y) + p1.x;
                            if (p1.x == p2.x || p.x <= xinters) {
                                inside = !inside;
                            }
                        }
                    }
                }
            }
            p1 = p2;
        }
        return inside;
    }

    public static void main(String[] args) {
        System.out.println("--- Point in Polygon (Ray Casting) ---");
        Point[] polygon = {
            new Point(0, 0), new Point(10, 0), new Point(10, 10), new Point(0, 10)
        };
        
        Point p1 = new Point(5, 5);
        System.out.println("Point (5,5) inside square? " + isInside(polygon, p1)); // true
        
        Point p2 = new Point(20, 20);
        System.out.println("Point (20,20) inside square? " + isInside(polygon, p2)); // false
        
        Point p3 = new Point(10, 5); // on edge
        System.out.println("Point (10,5) inside square? " + isInside(polygon, p3)); // true (depends on strict boundary rules, typically raycast includes some edges)
    }
}
