package spatial.kdtree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * K-DIMENSIONAL KD-TREE (any number of dimensions, a value per point)
 *
 * What it is:
 * KDTree.java is fixed at 2D int points. This version stores points in K dimensions
 * (double coordinates) with a value attached - e.g. 3D positions, or feature vectors
 * for "find the most similar items".
 *
 * Approach/Strategy:
 * A binary tree where the node at depth d splits space on axis d % K:
 * smaller coordinate goes left, greater-or-equal goes right.
 *   - nearest(target): go down the side containing the target first, then visit the
 *     other side only if the splitting plane is closer than the best match so far.
 *   - kNearest(target, k): same, keeping the k best in a MAX-heap (the worst of the k
 *     is on top, so it is cheap to check "is this one better than my worst?").
 *   - rangeSearch(min, max): skip a side when the box lies entirely on the other side.
 *   - delete(point): replace the node with the MINIMUM on its splitting axis from its right
 *     subtree (or from the left subtree, which is then moved to the right), recursively.
 *   - build(points): pick the MEDIAN on the current axis as the root -> balanced tree.
 *
 * Time/Space Complexity:
 * Operation          | Average         | Worst
 * ---------------------------------------------------
 * insert/get/delete  | O(log N)        | O(N) (unbalanced inserts)
 * nearest            | O(log N)        | O(N)
 * kNearest           | O(k log k + log N) typical
 * rangeSearch        | O(sqrt N + m) in 2D (m = results)
 * build (balanced)   | O(N log N) with sorting per level: O(N log^2 N)
 * Space              | O(N)
 *
 * The "curse of dimensionality": for high K (say > 20) nearest-neighbor pruning stops
 * working and searches visit most of the tree - use approximate methods (LSH, HNSW) there.
 */
public class GenericKDTree<V> {

    /** A stored point with its value. */
    public static class Entry<V> {
        private final double[] point;
        private V value;

        Entry(double[] point, V value) {
            this.point = point.clone(); // defensive copy: callers cannot move points behind our back
            this.value = value;
        }

        public double[] getPoint() {
            return point.clone();
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return Arrays.toString(point) + "=" + value;
        }
    }

    private static class Node<V> {
        Entry<V> entry;
        Node<V> left, right;

        Node(Entry<V> entry) {
            this.entry = entry;
        }
    }

    private final int k;
    private Node<V> root;
    private int size;

    public GenericKDTree(int dimensions) {
        if (dimensions < 1) throw new IllegalArgumentException("dimensions must be >= 1");
        this.k = dimensions;
    }

    private void check(double[] p) {
        if (p == null || p.length != k) throw new IllegalArgumentException("point must have " + k + " coordinates");
    }

    private static boolean samePoint(double[] a, double[] b) {
        return Arrays.equals(a, b);
    }

    private static double distSq(double[] a, double[] b) {
        double s = 0;
        for (int i = 0; i < a.length; i++) {
            double d = a[i] - b[i];
            s += d * d;
        }
        return s;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int dimensions() {
        return k;
    }

    // ---------------- insert / get ----------------

    /** Adds the point, or updates its value if the exact point is already stored. */
    public void insert(double[] point, V value) {
        check(point);
        if (root == null) {
            root = new Node<>(new Entry<>(point, value));
            size++;
            return;
        }
        Node<V> cur = root;
        for (int depth = 0; ; depth++) {
            if (samePoint(cur.entry.point, point)) {
                cur.entry.value = value;
                return;
            }
            int axis = depth % k;
            boolean goLeft = point[axis] < cur.entry.point[axis];
            Node<V> next = goLeft ? cur.left : cur.right;
            if (next == null) {
                Node<V> node = new Node<>(new Entry<>(point, value));
                if (goLeft) cur.left = node; else cur.right = node;
                size++;
                return;
            }
            cur = next;
        }
    }

    private Node<V> findNode(double[] point) {
        Node<V> cur = root;
        for (int depth = 0; cur != null; depth++) {
            if (samePoint(cur.entry.point, point)) return cur;
            int axis = depth % k;
            cur = point[axis] < cur.entry.point[axis] ? cur.left : cur.right;
        }
        return null;
    }

    public V get(double[] point) {
        check(point);
        Node<V> n = findNode(point);
        return n == null ? null : n.entry.value;
    }

    public boolean contains(double[] point) {
        check(point);
        return findNode(point) != null;
    }

    // ---------------- delete ----------------

    /** Removes the exact point. Returns true if it was present. */
    public boolean delete(double[] point) {
        check(point);
        if (findNode(point) == null) return false;
        root = deleteRec(root, point, 0);
        size--;
        return true;
    }

    private Node<V> deleteRec(Node<V> node, double[] point, int depth) {
        int axis = depth % k;
        if (samePoint(node.entry.point, point)) {
            if (node.right != null) {
                // Replace with the smallest point (on this axis) from the right subtree
                Entry<V> min = findMin(node.right, axis, depth + 1);
                node.entry = min;
                node.right = deleteRec(node.right, min.point, depth + 1);
            } else if (node.left != null) {
                // No right subtree: take the min from the LEFT, and make that subtree the right one
                // (everything in it is >= the new min on this axis, so it belongs on the right)
                Entry<V> min = findMin(node.left, axis, depth + 1);
                node.entry = min;
                node.right = deleteRec(node.left, min.point, depth + 1);
                node.left = null;
            } else {
                return null; // leaf: just drop it
            }
            return node;
        }
        if (point[axis] < node.entry.point[axis]) node.left = deleteRec(node.left, point, depth + 1);
        else node.right = deleteRec(node.right, point, depth + 1);
        return node;
    }

    /** Entry with the smallest coordinate on 'axis' in this subtree. */
    private Entry<V> findMin(Node<V> node, int axis, int depth) {
        if (node == null) return null;
        Entry<V> best = node.entry;
        if (depth % k == axis) {
            // This node splits on the same axis: the minimum can only be on the left (or here)
            Entry<V> l = findMin(node.left, axis, depth + 1);
            if (l != null && l.point[axis] < best.point[axis]) best = l;
            return best;
        }
        // Different axis: the minimum could be anywhere below
        Entry<V> l = findMin(node.left, axis, depth + 1), r = findMin(node.right, axis, depth + 1);
        if (l != null && l.point[axis] < best.point[axis]) best = l;
        if (r != null && r.point[axis] < best.point[axis]) best = r;
        return best;
    }

    // ---------------- nearest neighbor ----------------

    private Entry<V> best;
    private double bestDist;

    /** Closest stored point to target (Euclidean), or null if empty. */
    public Entry<V> nearest(double[] target) {
        check(target);
        best = null;
        bestDist = Double.POSITIVE_INFINITY;
        nearestRec(root, target, 0);
        return best;
    }

    private void nearestRec(Node<V> node, double[] target, int depth) {
        if (node == null) return;
        double d = distSq(node.entry.point, target);
        if (d < bestDist) {
            bestDist = d;
            best = node.entry;
        }
        int axis = depth % k;
        double diff = target[axis] - node.entry.point[axis];
        Node<V> near = diff < 0 ? node.left : node.right, far = diff < 0 ? node.right : node.left;
        nearestRec(near, target, depth + 1);
        if (diff * diff < bestDist) nearestRec(far, target, depth + 1); // plane closer than best: check
    }

    /** The k closest stored points, nearest first. */
    public List<Entry<V>> kNearest(double[] target, int count) {
        check(target);
        if (count <= 0) return new ArrayList<>();
        // Max-heap by distance: the WORST of the current best 'count' sits on top
        PriorityQueue<Object[]> heap = new PriorityQueue<>((a, b) -> Double.compare((double) b[0], (double) a[0]));
        kNearestRec(root, target, 0, count, heap);
        List<Object[]> sorted = new ArrayList<>(heap);
        sorted.sort((a, b) -> Double.compare((double) a[0], (double) b[0]));
        List<Entry<V>> result = new ArrayList<>();
        for (Object[] item : sorted) {
            @SuppressWarnings("unchecked")
            Entry<V> e = (Entry<V>) item[1];
            result.add(e);
        }
        return result;
    }

    private void kNearestRec(Node<V> node, double[] target, int depth, int count, PriorityQueue<Object[]> heap) {
        if (node == null) return;
        double d = distSq(node.entry.point, target);
        if (heap.size() < count) heap.add(new Object[]{d, node.entry});
        else if (d < (double) heap.peek()[0]) {
            heap.poll();
            heap.add(new Object[]{d, node.entry});
        }
        int axis = depth % k;
        double diff = target[axis] - node.entry.point[axis];
        Node<V> near = diff < 0 ? node.left : node.right, far = diff < 0 ? node.right : node.left;
        kNearestRec(near, target, depth + 1, count, heap);
        // Visit the far side if we still need points, or the plane is closer than our worst
        if (heap.size() < count || diff * diff < (double) heap.peek()[0]) {
            kNearestRec(far, target, depth + 1, count, heap);
        }
    }

    // ---------------- range search ----------------

    /** All entries with min[i] <= p[i] <= max[i] for every axis. */
    public List<Entry<V>> rangeSearch(double[] min, double[] max) {
        check(min);
        check(max);
        List<Entry<V>> out = new ArrayList<>();
        rangeRec(root, min, max, 0, out);
        return out;
    }

    private void rangeRec(Node<V> node, double[] min, double[] max, int depth, List<Entry<V>> out) {
        if (node == null) return;
        double[] p = node.entry.point;
        boolean inside = true;
        for (int i = 0; i < k && inside; i++) inside = p[i] >= min[i] && p[i] <= max[i];
        if (inside) out.add(node.entry);
        int axis = depth % k;
        if (min[axis] < p[axis]) rangeRec(node.left, min, max, depth + 1, out);   // left side holds < p[axis]
        if (max[axis] >= p[axis]) rangeRec(node.right, min, max, depth + 1, out); // right side holds >= p[axis]
    }

    // ---------------- balanced build ----------------

    /**
     * Builds a BALANCED tree from many points at once: the median on the current axis
     * becomes the root, recursively. Height ~ log2(N), unlike one-by-one inserts of
     * sorted data. Duplicate points keep the last value.
     */
    public static <V> GenericKDTree<V> build(int dimensions, List<double[]> points, List<V> values) {
        if (points.size() != values.size()) throw new IllegalArgumentException("points and values differ in length");
        GenericKDTree<V> tree = new GenericKDTree<>(dimensions);
        List<Entry<V>> entries = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            tree.check(points.get(i));
            entries.add(new Entry<>(points.get(i), values.get(i)));
        }
        // Remove duplicate points (keep the last value) so every stored point is unique
        List<Entry<V>> unique = new ArrayList<>();
        java.util.Map<String, Entry<V>> seen = new java.util.HashMap<>();
        for (Entry<V> e : entries) {
            Entry<V> prev = seen.put(Arrays.toString(e.point), e);
            if (prev != null) unique.remove(prev);
            unique.add(e);
        }
        tree.root = tree.buildRec(unique, 0);
        tree.size = unique.size();
        return tree;
    }

    private Node<V> buildRec(List<Entry<V>> list, int depth) {
        if (list.isEmpty()) return null;
        int axis = depth % k;
        list.sort((a, b) -> Double.compare(a.point[axis], b.point[axis]));
        int mid = list.size() / 2;
        // Move mid left past equal coordinates, so every point in the LEFT part is strictly
        // smaller on this axis (matches the "< goes left, >= goes right" rule of search)
        while (mid > 0 && list.get(mid - 1).point[axis] == list.get(mid).point[axis]) mid--;
        Node<V> node = new Node<>(list.get(mid));
        node.left = buildRec(new ArrayList<>(list.subList(0, mid)), depth + 1);
        node.right = buildRec(new ArrayList<>(list.subList(mid + 1, list.size())), depth + 1);
        return node;
    }

    /** Height of the tree (0 = empty) - shows the effect of a balanced build. */
    public int height() {
        return heightRec(root);
    }

    private int heightRec(Node<V> n) {
        return n == null ? 0 : 1 + Math.max(heightRec(n.left), heightRec(n.right));
    }

    public static void main(String[] args) {
        System.out.println("--- K-DIMENSIONAL KD-TREE DEMO (3D) ---");
        GenericKDTree<String> space = new GenericKDTree<>(3);
        space.insert(new double[]{0, 0, 0}, "origin");
        space.insert(new double[]{5, 1, 2}, "probe A");
        space.insert(new double[]{-3, 4, 1}, "probe B");
        space.insert(new double[]{2, 2, 2}, "probe C");
        space.insert(new double[]{9, 9, 9}, "far station");

        double[] ship = {2, 1, 1.5};
        System.out.println("Nearest to ship " + Arrays.toString(ship) + ": " + space.nearest(ship));
        System.out.println("3 nearest: " + space.kNearest(ship, 3));
        System.out.println("Inside box [-1..3]^3: " + space.rangeSearch(new double[]{-1, -1, -1}, new double[]{3, 3, 3}));
        space.delete(new double[]{2, 2, 2});
        System.out.println("After deleting probe C, nearest to ship: " + space.nearest(ship));

        // Balanced build vs one-by-one inserts of SORTED points
        List<double[]> pts = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < 1023; i++) {
            pts.add(new double[]{i, i});
            ids.add(i);
        }
        GenericKDTree<Integer> naive = new GenericKDTree<>(2);
        for (int i = 0; i < pts.size(); i++) naive.insert(pts.get(i), ids.get(i));
        GenericKDTree<Integer> balanced = GenericKDTree.build(2, pts, ids);
        System.out.println("\n1023 sorted points: height after one-by-one inserts = " + naive.height()
                + ", after balanced build = " + balanced.height());
    }
}
