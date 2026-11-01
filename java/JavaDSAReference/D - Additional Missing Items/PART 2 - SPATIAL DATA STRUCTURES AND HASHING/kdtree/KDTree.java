package spatial.kdtree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * KD-TREE (K-Dimensional Tree)
 * 
 * WHAT IT IS:
 * A space-partitioning data structure for organizing points in a k-dimensional space.
 * It's a binary search tree where every internal node splits the space across one of 
 * the dimensions. (e.g., At depth 0 split by X, at depth 1 split by Y, at depth 2 
 * split by X again).
 * 
 * APPLIES TO:
 * - Multi-dimensional point data (2D/3D coordinates).
 * 
 * WHEN TO USE THIS:
 * - Nearest-neighbor or range queries over points in low-to-moderate dimensional space.
 * - (Note: Performance degrades to O(N) brute force in very high dimensions, an issue 
 *   known as the "curse of dimensionality").
 * 
 * COMPLEXITY:
 * Time: O(log N) average for insertion and search.
 * Space: O(N)
 * 
 * PSEUDOCODE (Insert):
 * 1. Start at root, tracking depth = 0.
 * 2. Calculate current splitting axis `cd = depth % K`.
 * 3. Compare the new point's `cd` coordinate with the current node's `cd` coordinate.
 * 4. If smaller, go left. If larger/equal, go right.
 * 5. Increment depth and repeat until a null leaf is found.
 */
public class KDTree {

    private static final int K = 2; // 2-Dimensional in this demo

    static class Node {
        int[] point; // Array of coordinates
        Node left, right;

        Node(int[] point) {
            this.point = point;
        }
    }

    private Node root;

    public void insert(int[] point) {
        root = insertRec(root, point, 0);
    }

    private Node insertRec(Node root, int[] point, int depth) {
        if (root == null) {
            return new Node(point);
        }

        // Calculate current dimension (axis)
        int cd = depth % K;

        // Compare the new point with root on the current dimension
        if (point[cd] < root.point[cd]) {
            root.left = insertRec(root.left, point, depth + 1);
        } else {
            root.right = insertRec(root.right, point, depth + 1);
        }

        return root;
    }

    public boolean search(int[] point) {
        return searchRec(root, point, 0);
    }

    private boolean searchRec(Node root, int[] point, int depth) {
        if (root == null) return false;

        boolean areEqual = true;
        for (int i = 0; i < K; i++) {
            if (root.point[i] != point[i]) {
                areEqual = false;
                break;
            }
        }
        if (areEqual) return true;

        int cd = depth % K;

        // Traverse based on the current dimension
        if (point[cd] < root.point[cd]) {
            return searchRec(root.left, point, depth + 1);
        } else {
            return searchRec(root.right, point, depth + 1);
        }
    }

    // --------------------------------------------------------
    // NEAREST NEIGHBOR - the main reason KD-trees exist
    // --------------------------------------------------------

    private static long distSq(int[] a, int[] b) {
        long sum = 0;
        for (int i = 0; i < K; i++) {
            long d = (long) a[i] - b[i];
            sum += d * d;
        }
        return sum;
    }

    private int[] best;
    private long bestDist;

    /**
     * Returns the stored point closest to target (Euclidean), or null if the tree is empty.
     *
     * 1. Go down the side of the splitting line that contains the target first
     *    (the nearest point is most likely there).
     * 2. On the way back up, only visit the OTHER side if the splitting line is
     *    closer than the best distance found so far - otherwise nothing over there
     *    can beat it. This pruning is what makes it ~O(log N) instead of O(N).
     */
    public int[] nearestNeighbor(int[] target) {
        best = null;
        bestDist = Long.MAX_VALUE;
        nearestRec(root, target, 0);
        return best;
    }

    private void nearestRec(Node node, int[] target, int depth) {
        if (node == null) return;
        long d = distSq(node.point, target);
        if (d < bestDist) {
            bestDist = d;
            best = node.point;
        }

        int cd = depth % K;
        long diff = (long) target[cd] - node.point[cd];
        Node near = diff < 0 ? node.left : node.right;
        Node far = diff < 0 ? node.right : node.left;

        nearestRec(near, target, depth + 1);
        if (diff * diff < bestDist) { // the splitting line is closer than the best so far
            nearestRec(far, target, depth + 1);
        }
    }

    // --------------------------------------------------------
    // RANGE SEARCH - all points inside an axis-aligned box
    // --------------------------------------------------------

    /** All points p with min[i] <= p[i] <= max[i] for every dimension i. */
    public List<int[]> rangeSearch(int[] min, int[] max) {
        List<int[]> result = new ArrayList<>();
        rangeRec(root, min, max, 0, result);
        return result;
    }

    private void rangeRec(Node node, int[] min, int[] max, int depth, List<int[]> result) {
        if (node == null) return;
        boolean inside = true;
        for (int i = 0; i < K; i++) {
            if (node.point[i] < min[i] || node.point[i] > max[i]) {
                inside = false;
                break;
            }
        }
        if (inside) result.add(node.point);

        int cd = depth % K;
        // Left subtree has values < node's, right has >= node's on axis cd.
        // Skip a side when the box lies completely on the other side of the line.
        if (min[cd] < node.point[cd]) rangeRec(node.left, min, max, depth + 1, result);
        if (max[cd] >= node.point[cd]) rangeRec(node.right, min, max, depth + 1, result);
    }

    public static void main(String[] args) {
        System.out.println("--- KD-TREE DEMO ---");
        KDTree kdt = new KDTree();
        
        int[][] points = {{3, 6}, {17, 15}, {13, 15}, {6, 12}, {9, 1}, {2, 7}, {10, 19}};
        
        System.out.println("Inserting points into KD-Tree: " + Arrays.deepToString(points));
        for (int[] p : points) {
            kdt.insert(p);
        }
        
        int[] query1 = {10, 19};
        System.out.println("Searching for [10, 19]: " + kdt.search(query1)); // Expected: true
        
        int[] query2 = {12, 19};
        System.out.println("Searching for [12, 19]: " + kdt.search(query2)); // Expected: false

        int[] target = {12, 14};
        System.out.println("Nearest to [12, 14]: " + Arrays.toString(kdt.nearestNeighbor(target))); // Expected: [13, 15]

        List<int[]> inBox = kdt.rangeSearch(new int[]{0, 5}, new int[]{10, 15});
        StringBuilder sb = new StringBuilder();
        for (int[] p : inBox) sb.append(Arrays.toString(p)).append(' ');
        System.out.println("Points with 0<=x<=10 and 5<=y<=15: " + sb); // [3, 6] [6, 12] [2, 7]
    }
}
