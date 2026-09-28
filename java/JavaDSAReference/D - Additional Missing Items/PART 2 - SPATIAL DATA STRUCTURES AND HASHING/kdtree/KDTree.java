package spatial.kdtree;

import java.util.Arrays;

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
    }
}
