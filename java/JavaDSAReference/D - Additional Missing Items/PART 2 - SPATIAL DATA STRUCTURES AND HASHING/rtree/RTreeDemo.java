package spatial.rtree;

import java.util.ArrayList;
import java.util.List;

/**
 * R-TREE (Rectangle Tree)
 * 
 * WHAT IT IS:
 * A highly advanced, bounding-box based spatial index. It groups nearby objects 
 * and represents them with their Minimum Bounding Rectangle (MBR) at each tree level.
 * 
 * APPLIES TO:
 * - Real spatial databases (like PostGIS), GIS systems.
 * 
 * WHEN TO USE THIS:
 * - Indexing EXTENDED spatial objects (polygons, lines, rectangles), not just points. 
 * - When spatial data is stored on disk (R-Trees are optimized for disk I/O, much 
 *   like B-Trees are for 1D data).
 * 
 * STRATEGY:
 * - Similar to a B-Tree, an R-Tree is a balanced tree where nodes have a variable 
 *   number of children (between m and M).
 * - When searching for a region, the tree descends ONLY into bounding boxes that 
 *   intersect the query region.
 * 
 * COMPLEXITY:
 * Time: O(log M N) for search.
 * Space: O(N)
 * 
 * NOTE: 
 * A production-grade R-Tree implementation is massive (handling node splitting 
 * via quadratic or linear algorithms, re-inserting, balancing).
 * This file provides a structural demonstration of the query mechanism to show 
 * how the bounding-box intersection logic works.
 */
public class RTreeDemo {

    static class Rectangle {
        double minX, minY, maxX, maxY;

        Rectangle(double minX, double minY, double maxX, double maxY) {
            this.minX = minX; this.minY = minY;
            this.maxX = maxX; this.maxY = maxY;
        }

        boolean intersects(Rectangle other) {
            return !(this.minX > other.maxX || 
                     this.maxX < other.minX || 
                     this.minY > other.maxY || 
                     this.maxY < other.minY);
        }

        @Override
        public String toString() {
            return String.format("Rect[%.1f,%.1f to %.1f,%.1f]", minX, minY, maxX, maxY);
        }
    }

    static class Node {
        boolean isLeaf;
        Rectangle boundingBox;
        List<Node> children; // If not leaf, contains sub-nodes
        List<Rectangle> data; // If leaf, contains actual spatial objects

        Node(boolean isLeaf, Rectangle boundingBox) {
            this.isLeaf = isLeaf;
            this.boundingBox = boundingBox;
            this.children = new ArrayList<>();
            this.data = new ArrayList<>();
        }
    }

    // A mock root for demonstration
    private Node root;

    public RTreeDemo(Node root) {
        this.root = root;
    }

    /**
     * Search the R-Tree for all rectangles intersecting the query region.
     */
    public List<Rectangle> search(Rectangle queryRegion) {
        List<Rectangle> results = new ArrayList<>();
        searchRecursive(root, queryRegion, results);
        return results;
    }

    private void searchRecursive(Node node, Rectangle queryRegion, List<Rectangle> results) {
        // If the query region doesn't intersect this node's bounding box, prune this entire branch!
        if (!node.boundingBox.intersects(queryRegion)) {
            return;
        }

        if (node.isLeaf) {
            // Leaf node: Check actual data objects
            for (Rectangle item : node.data) {
                if (item.intersects(queryRegion)) {
                    results.add(item);
                }
            }
        } else {
            // Internal node: Recursively search children
            for (Node child : node.children) {
                searchRecursive(child, queryRegion, results);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- R-TREE CONCEPT DEMO ---");
        
        // Manual construction of a tiny R-Tree
        Node root = new Node(false, new Rectangle(0, 0, 100, 100));
        
        Node child1 = new Node(true, new Rectangle(0, 0, 50, 50));
        child1.data.add(new Rectangle(10, 10, 20, 20));
        child1.data.add(new Rectangle(30, 30, 40, 40));
        
        Node child2 = new Node(true, new Rectangle(50, 50, 100, 100));
        child2.data.add(new Rectangle(60, 60, 70, 70));
        child2.data.add(new Rectangle(80, 80, 90, 90));
        
        root.children.add(child1);
        root.children.add(child2);
        
        RTreeDemo rtree = new RTreeDemo(root);
        
        Rectangle query = new Rectangle(15, 15, 35, 35);
        System.out.println("Querying Region: " + query);
        
        List<Rectangle> hits = rtree.search(query);
        System.out.println("Found intersecting objects:");
        for (Rectangle hit : hits) {
            System.out.println("  " + hit);
        }
        // Expected: Rect[10.0,10.0 to 20.0,20.0] and Rect[30.0,30.0 to 40.0,40.0]
    }
}
