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
 * STRATEGY (Guttman's original R-Tree, 1984):
 * - A balanced tree; every node holds between MIN_ENTRIES and MAX_ENTRIES entries
 *   (the root may hold fewer). All leaves are on the same level.
 * - SEARCH: descend ONLY into children whose bounding box intersects the query region.
 * - INSERT:
 *   1. ChooseLeaf: from the root, always pick the child whose box needs the LEAST
 *      ENLARGEMENT to include the new rectangle (ties: smaller area).
 *   2. Add it to that leaf. If the leaf now has too many entries, SPLIT it.
 *   3. QUADRATIC SPLIT: pick the two entries that would waste the most area if put
 *      together as "seeds" of two groups; then repeatedly assign the entry with the
 *      strongest preference for one group. Each group must reach MIN_ENTRIES.
 *   4. AdjustTree: walk back up, enlarging boxes and splitting parents when needed.
 *      If the root splits, the tree grows one level taller.
 *
 * COMPLEXITY:
 * Search: O(log_M N) when boxes overlap little (worst case O(N) - boxes can overlap).
 * Insert: O(log_M N) to descend + O(M^2) for a quadratic split.
 * Space: O(N)
 */
public class RTreeDemo {

    static final int MAX_ENTRIES = 4;
    static final int MIN_ENTRIES = 2;

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

        double area() {
            return (maxX - minX) * (maxY - minY);
        }

        Rectangle union(Rectangle o) {
            return new Rectangle(Math.min(minX, o.minX), Math.min(minY, o.minY),
                                 Math.max(maxX, o.maxX), Math.max(maxY, o.maxY));
        }

        // How much this box's area grows if it has to cover r as well
        double enlargement(Rectangle r) {
            return union(r).area() - area();
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
        Node parent;

        Node(boolean isLeaf, Rectangle boundingBox) {
            this.isLeaf = isLeaf;
            this.boundingBox = boundingBox;
            this.children = new ArrayList<>();
            this.data = new ArrayList<>();
        }

        int entryCount() {
            return isLeaf ? data.size() : children.size();
        }

        Rectangle entryBox(int i) {
            return isLeaf ? data.get(i) : children.get(i).boundingBox;
        }

        void recomputeBox() {
            Rectangle box = null;
            for (int i = 0; i < entryCount(); i++) {
                box = (box == null) ? entryBox(i) : box.union(entryBox(i));
            }
            boundingBox = box;
        }
    }

    private Node root;
    private int size = 0;

    public RTreeDemo() {
        this.root = new Node(true, null);
    }

    /** Wraps a hand-built tree (kept from the original concept demo). */
    public RTreeDemo(Node root) {
        this.root = root;
    }

    public int size() {
        return size;
    }

    public int height() {
        int h = 1;
        for (Node n = root; !n.isLeaf; n = n.children.get(0)) h++;
        return h;
    }

    // --------------------------------------------------------
    // SEARCH
    // --------------------------------------------------------

    /**
     * Search the R-Tree for all rectangles intersecting the query region.
     */
    public List<Rectangle> search(Rectangle queryRegion) {
        List<Rectangle> results = new ArrayList<>();
        if (root.boundingBox != null) searchRecursive(root, queryRegion, results);
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

    // --------------------------------------------------------
    // INSERT
    // --------------------------------------------------------

    public void insert(Rectangle rect) {
        Node leaf = chooseLeaf(root, rect);
        leaf.data.add(rect);
        size++;
        Node splitOff = leaf.data.size() > MAX_ENTRIES ? split(leaf) : null;
        adjustTree(leaf, splitOff);
    }

    // Descend by least enlargement (ties broken by smaller area)
    private Node chooseLeaf(Node node, Rectangle rect) {
        while (!node.isLeaf) {
            Node best = null;
            double bestEnl = Double.MAX_VALUE, bestArea = Double.MAX_VALUE;
            for (Node child : node.children) {
                double enl = child.boundingBox.enlargement(rect);
                double area = child.boundingBox.area();
                if (enl < bestEnl || (enl == bestEnl && area < bestArea)) {
                    best = child;
                    bestEnl = enl;
                    bestArea = area;
                }
            }
            node = best;
        }
        return node;
    }

    // Walk up: fix bounding boxes, attach split-off siblings, split parents that overflow
    private void adjustTree(Node node, Node splitOff) {
        while (true) {
            node.recomputeBox();
            if (splitOff != null) splitOff.recomputeBox();

            if (node == root) {
                if (splitOff != null) { // root split: grow the tree
                    Node newRoot = new Node(false, null);
                    newRoot.children.add(node);
                    newRoot.children.add(splitOff);
                    node.parent = newRoot;
                    splitOff.parent = newRoot;
                    newRoot.recomputeBox();
                    root = newRoot;
                }
                return;
            }

            Node parent = node.parent;
            Node parentSplit = null;
            if (splitOff != null) {
                parent.children.add(splitOff);
                splitOff.parent = parent;
                if (parent.children.size() > MAX_ENTRIES) parentSplit = split(parent);
            }
            node = parent;
            splitOff = parentSplit;
        }
    }

    /**
     * Quadratic split. Keeps one group in 'node' and returns a NEW node with the other group.
     */
    private Node split(Node node) {
        int n = node.entryCount();
        List<Integer> remaining = new ArrayList<>();
        for (int i = 0; i < n; i++) remaining.add(i);

        // PickSeeds: the pair that would waste the most area if grouped together
        int seedA = 0, seedB = 1;
        double worst = -Double.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Rectangle a = node.entryBox(i), b = node.entryBox(j);
                double waste = a.union(b).area() - a.area() - b.area();
                if (waste > worst) {
                    worst = waste;
                    seedA = i;
                    seedB = j;
                }
            }
        }

        List<Integer> groupA = new ArrayList<>(), groupB = new ArrayList<>();
        groupA.add(seedA);
        groupB.add(seedB);
        remaining.remove(Integer.valueOf(seedA));
        remaining.remove(Integer.valueOf(seedB));
        Rectangle boxA = node.entryBox(seedA), boxB = node.entryBox(seedB);

        while (!remaining.isEmpty()) {
            // If one group needs all remaining entries to reach the minimum, give them all
            if (groupA.size() + remaining.size() == MIN_ENTRIES) {
                groupA.addAll(remaining);
                break;
            }
            if (groupB.size() + remaining.size() == MIN_ENTRIES) {
                groupB.addAll(remaining);
                break;
            }
            // PickNext: the entry with the biggest preference for one group
            int pick = -1;
            double maxDiff = -1;
            for (int idx : remaining) {
                double dA = boxA.enlargement(node.entryBox(idx));
                double dB = boxB.enlargement(node.entryBox(idx));
                if (Math.abs(dA - dB) > maxDiff) {
                    maxDiff = Math.abs(dA - dB);
                    pick = idx;
                }
            }
            remaining.remove(Integer.valueOf(pick));
            Rectangle r = node.entryBox(pick);
            double dA = boxA.enlargement(r), dB = boxB.enlargement(r);
            boolean toA = dA < dB || (dA == dB && (boxA.area() < boxB.area()
                    || (boxA.area() == boxB.area() && groupA.size() <= groupB.size())));
            if (toA) {
                groupA.add(pick);
                boxA = boxA.union(r);
            } else {
                groupB.add(pick);
                boxB = boxB.union(r);
            }
        }

        // Rebuild the two nodes from the index groups
        Node sibling = new Node(node.isLeaf, null);
        if (node.isLeaf) {
            List<Rectangle> old = new ArrayList<>(node.data);
            node.data.clear();
            for (int idx : groupA) node.data.add(old.get(idx));
            for (int idx : groupB) sibling.data.add(old.get(idx));
        } else {
            List<Node> old = new ArrayList<>(node.children);
            node.children.clear();
            for (int idx : groupA) node.children.add(old.get(idx));
            for (int idx : groupB) {
                Node child = old.get(idx);
                sibling.children.add(child);
                child.parent = sibling;
            }
        }
        node.recomputeBox();
        sibling.recomputeBox();
        return sibling;
    }

    public static void main(String[] args) {
        System.out.println("--- R-TREE DEMO ---");
        RTreeDemo rtree = new RTreeDemo();

        // A small "map": buildings in two neighborhoods plus a few scattered ones
        double[][] boxes = {
            {10, 10, 20, 20}, {30, 30, 40, 40}, {12, 35, 18, 42}, {25, 5, 33, 12},
            {60, 60, 70, 70}, {80, 80, 90, 90}, {65, 85, 72, 95}, {85, 55, 95, 62},
            {45, 45, 55, 55}, {5, 70, 15, 80}, {70, 10, 78, 18}, {50, 20, 58, 28}
        };
        for (double[] b : boxes) rtree.insert(new Rectangle(b[0], b[1], b[2], b[3]));
        System.out.println("Inserted " + rtree.size() + " rectangles. Tree height = " + rtree.height()
                + " (max " + MAX_ENTRIES + " entries per node)");

        Rectangle query = new Rectangle(15, 15, 35, 35);
        System.out.println("Querying Region: " + query);

        List<Rectangle> hits = rtree.search(query);
        System.out.println("Found intersecting objects:");
        for (Rectangle hit : hits) {
            System.out.println("  " + hit);
        }
        // Expected: Rect[10,10 to 20,20], Rect[30,30 to 40,40] and Rect[12,35 to 18,42]
        // (the last one only touches the query's top edge - touching counts as intersecting)
    }
}
