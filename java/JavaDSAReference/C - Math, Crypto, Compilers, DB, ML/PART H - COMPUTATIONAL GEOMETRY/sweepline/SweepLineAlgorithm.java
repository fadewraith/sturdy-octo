package geometry.sweepline;

import java.util.*;

/**
 * WHAT IT IS: Sweep Line Algorithm for detecting intersections among a set of line segments.
 * STRATEGY: Move a vertical sweep line across the plane from left to right. Maintain a sorted structure (TreeSet) of active segments intersecting the line.
 * Check for intersections only between adjacent segments in the active set.
 * TIME/SPACE COMPLEXITY: Time: O((n + I) log n) where n is segments and I is intersections. Space: O(n) for BST/TreeMap.
 * REAL-WORLD ANALOGY / USE CASE: VLSI design, mapping roads/rivers for collisions, computer graphics rendering.
 * WHEN TO USE / COMBINATION: Applies to: detecting all intersections efficiently. Combine Sweep Line + a sorted structure (BST/TreeMap) to track active segments.
 * 
 * PSEUDOCODE:
 * Events = sort segment endpoints by X
 * ActiveSegments = BST ordered by Y
 * For event in Events:
 *   If event is Left Endpoint:
 *     Insert segment into ActiveSegments
 *     Check intersection with segment above and segment below
 *   If event is Right Endpoint:
 *     Check intersection between segment above and segment below
 *     Remove segment from ActiveSegments
 */
public class SweepLineAlgorithm {

    static class Point implements Comparable<Point> {
        double x, y;
        Point(double x, double y) { this.x = x; this.y = y; }
        @Override
        public int compareTo(Point other) {
            if (this.x != other.x) return Double.compare(this.x, other.x);
            return Double.compare(this.y, other.y);
        }
    }

    static class Segment {
        Point left, right;
        int id;
        Segment(Point p1, Point p2, int id) {
            if (p1.compareTo(p2) < 0) { left = p1; right = p2; }
            else { left = p2; right = p1; }
            this.id = id;
        }
    }

    static class Event implements Comparable<Event> {
        double x;
        boolean isLeft;
        Segment seg;
        Event(double x, boolean isLeft, Segment seg) {
            this.x = x;
            this.isLeft = isLeft;
            this.seg = seg;
        }
        @Override
        public int compareTo(Event other) {
            if (this.x != other.x) return Double.compare(this.x, other.x);
            // Process left endpoints before right if same x
            if (this.isLeft != other.isLeft) return this.isLeft ? -1 : 1;
            return Integer.compare(this.seg.id, other.seg.id);
        }
    }

    public static boolean hasIntersection(List<Segment> segments) {
        List<Event> events = new ArrayList<>();
        for (Segment s : segments) {
            events.add(new Event(s.left.x, true, s));
            events.add(new Event(s.right.x, false, s));
        }
        Collections.sort(events);

        // TreeSet ordered by Y coordinate of left endpoint as approximation for simplicity
        // In full implementation, it should evaluate Y at the current sweep line X.
        TreeSet<Segment> active = new TreeSet<>((s1, s2) -> {
            if (s1.left.y != s2.left.y) return Double.compare(s1.left.y, s2.left.y);
            return Integer.compare(s1.id, s2.id);
        });

        for (Event ev : events) {
            Segment s = ev.seg;
            if (ev.isLeft) {
                active.add(s);
                Segment above = active.higher(s);
                Segment below = active.lower(s);
                if (above != null && doIntersect(s, above)) return true;
                if (below != null && doIntersect(s, below)) return true;
            } else {
                Segment above = active.higher(s);
                Segment below = active.lower(s);
                if (above != null && below != null && doIntersect(above, below)) return true;
                active.remove(s);
            }
        }
        return false;
    }

    static int orientation(Point p, Point q, Point r) {
        double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val == 0) return 0;
        return (val > 0) ? 1 : 2;
    }

    static boolean doIntersect(Segment s1, Segment s2) {
        Point p1 = s1.left, q1 = s1.right;
        Point p2 = s2.left, q2 = s2.right;

        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        if (o1 != o2 && o3 != o4) return true;
        // ignoring collinear overlaps for basic sweep line intersection detection
        return false;
    }

    public static void main(String[] args) {
        System.out.println("--- Sweep Line Algorithm (Intersection Detection) ---");
        List<Segment> segments = new ArrayList<>();
        segments.add(new Segment(new Point(1, 5), new Point(4, 5), 1));
        segments.add(new Segment(new Point(2, 5), new Point(10, 1), 2));
        segments.add(new Segment(new Point(3, 2), new Point(10, 3), 3));
        segments.add(new Segment(new Point(6, 4), new Point(9, 4), 4));

        System.out.println("Any intersections? " + hasIntersection(segments));
    }
}
