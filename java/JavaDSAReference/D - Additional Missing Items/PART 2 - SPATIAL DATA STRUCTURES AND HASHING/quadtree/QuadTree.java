package spatial.quadtree;

import java.util.ArrayList;
import java.util.List;

/**
 * QUAD-TREE
 * 
 * WHAT IT IS:
 * A 2D spatial partitioning tree. Every internal node has exactly four children, 
 * recursively subdividing a 2D space into four quadrants/regions (NW, NE, SW, SE).
 * 
 * APPLIES TO:
 * - Collision detection in games (broad-phase).
 * - Image compression.
 * - Spatial indexing.
 * 
 * WHEN TO USE THIS:
 * - 2D spatial data with UNEVEN density. It automatically creates finer subdivisions 
 *   where points are clustered densely, and leaves coarse subdivisions where space is empty.
 * 
 * STRATEGY:
 * A node represents a bounding box. It holds up to `capacity` points.
 * When a point is inserted, if the node is full, it SUBDIVIDES into 4 child nodes.
 * The points are then passed down into the appropriate children.
 * 
 * COMPLEXITY:
 * Time: O(log N) for insertion and search (assuming relatively even distribution).
 * Space: O(N)
 */
public class QuadTree {

    static class Point {
        double x, y;
        Point(double x, double y) { this.x = x; this.y = y; }
        @Override public String toString() { return "(" + x + ", " + y + ")"; }
    }

    static class Boundary {
        double x, y, width, height; // x, y is the center
        Boundary(double x, double y, double width, double height) {
            this.x = x; this.y = y;
            this.width = width; this.height = height;
        }
        
        boolean contains(Point p) {
            return (p.x >= x - width/2 && p.x <= x + width/2 &&
                    p.y >= y - height/2 && p.y <= y + height/2);
        }
    }

    private Boundary boundary;
    private int capacity;
    private List<Point> points;
    private boolean subdivided = false;
    
    // Four children
    private QuadTree nw, ne, sw, se;

    public QuadTree(Boundary boundary, int capacity) {
        this.boundary = boundary;
        this.capacity = capacity;
        this.points = new ArrayList<>();
    }

    public boolean insert(Point p) {
        // Ignore objects that do not belong in this quad tree
        if (!boundary.contains(p)) {
            return false;
        }

        // If there is space in this quad tree, add the object here
        if (points.size() < capacity) {
            points.add(p);
            return true;
        }

        // Otherwise, subdivide and then add the point to whichever node will accept it
        if (!subdivided) {
            subdivide();
        }

        if (nw.insert(p)) return true;
        if (ne.insert(p)) return true;
        if (sw.insert(p)) return true;
        if (se.insert(p)) return true;
        
        return false;
    }

    private void subdivide() {
        double x = boundary.x;
        double y = boundary.y;
        double w = boundary.width / 2;
        double h = boundary.height / 2;

        nw = new QuadTree(new Boundary(x - w/2, y + h/2, w, h), capacity);
        ne = new QuadTree(new Boundary(x + w/2, y + h/2, w, h), capacity);
        sw = new QuadTree(new Boundary(x - w/2, y - h/2, w, h), capacity);
        se = new QuadTree(new Boundary(x + w/2, y - h/2, w, h), capacity);
        
        subdivided = true;
    }

    public List<Point> query(Boundary range) {
        List<Point> found = new ArrayList<>();
        
        // If the query range doesn't intersect this quad's boundary, abort
        if (!intersects(this.boundary, range)) {
            return found;
        }
        
        // Check points at this quad level
        for (Point p : points) {
            if (range.contains(p)) {
                found.add(p);
            }
        }
        
        // Check children
        if (subdivided) {
            found.addAll(nw.query(range));
            found.addAll(ne.query(range));
            found.addAll(sw.query(range));
            found.addAll(se.query(range));
        }
        
        return found;
    }
    
    private boolean intersects(Boundary b1, Boundary b2) {
        return !(b2.x - b2.width/2 > b1.x + b1.width/2 ||
                 b2.x + b2.width/2 < b1.x - b1.width/2 ||
                 b2.y - b2.height/2 > b1.y + b1.height/2 ||
                 b2.y + b2.height/2 < b1.y - b1.height/2);
    }

    public static void main(String[] args) {
        System.out.println("--- QUAD-TREE DEMO ---");
        // Create a root bounding box centered at (200,200) with width/height 400
        Boundary boundary = new Boundary(200, 200, 400, 400);
        QuadTree qt = new QuadTree(boundary, 4); // Max 4 points per quadrant before splitting
        
        System.out.println("Inserting 10 random points...");
        for (int i = 0; i < 10; i++) {
            qt.insert(new Point(Math.random() * 400, Math.random() * 400));
        }
        
        Point specialPoint = new Point(250, 250);
        qt.insert(specialPoint);
        System.out.println("Inserted special point: " + specialPoint);
        
        // Query a small box
        Boundary queryBox = new Boundary(250, 250, 50, 50);
        List<Point> found = qt.query(queryBox);
        
        System.out.println("Points found in query box centered at (250, 250) of size 50x50:");
        for (Point p : found) {
            System.out.println("  " + p);
        }
    }
}
