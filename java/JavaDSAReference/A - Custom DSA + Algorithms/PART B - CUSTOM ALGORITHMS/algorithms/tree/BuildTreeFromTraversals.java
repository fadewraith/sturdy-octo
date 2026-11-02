package algorithms.tree;

import java.util.Arrays;

/**
 * CONSTRUCT BINARY TREE FROM TRAVERSALS (Preorder + Inorder, Postorder + Inorder)
 *
 * WHAT IT IS:
 * Rebuild the exact binary tree from two of its traversal orders.
 * Requires DISTINCT values (otherwise the tree may not be unique).
 *
 * WHY INORDER IS NEEDED:
 * Preorder (root, left, right) tells you WHO the root is - it's the first element.
 * Inorder (left, root, right) tells you WHAT is on each side - everything before the
 * root's position is the left subtree, everything after is the right subtree.
 * Preorder + Postorder alone is ambiguous: a root with one child can't tell if the
 * child is left or right.
 *
 * STRATEGY (Preorder + Inorder):
 * 1. Take the next value from preorder -> that's the root of the current subtree.
 * 2. Find its position k in inorder. Left subtree = inorder[lo .. k-1],
 *    right subtree = inorder[k+1 .. hi].
 * 3. Build left first, then right (preorder lists the left subtree first).
 * Postorder + Inorder is the mirror image: take roots from the END of postorder
 * and build the RIGHT subtree first.
 *
 * FAST POSITION LOOKUP WITHOUT A HASHMAP:
 * Pack each inorder value with its index into one long: (value << 32) | index.
 * Sorting these longs sorts by value; a binary search then finds any value's
 * inorder index in O(log N). (With a HashMap it would be O(1) per lookup.)
 *
 * COMPLEXITY:
 * Time: O(N log N) here (N lookups of O(log N)); O(N) with a hash map.
 * Space: O(N) for the lookup table, plus O(H) recursion (H = tree height).
 */
public class BuildTreeFromTraversals {

    public static class TreeNode {
        public int val;
        public TreeNode left, right;
        public TreeNode(int val) { this.val = val; }
    }

    // (value << 32) | index, sorted -> binary search by value
    private static long[] buildIndex(int[] inorder) {
        long[] keys = new long[inorder.length];
        for (int i = 0; i < inorder.length; i++) keys[i] = ((long) inorder[i] << 32) | i;
        Arrays.sort(keys);
        for (int i = 1; i < keys.length; i++) {
            if ((keys[i] >> 32) == (keys[i - 1] >> 32)) throw new IllegalArgumentException("values must be distinct");
        }
        return keys;
    }

    private static int indexOf(long[] keys, int value) {
        int lo = 0, hi = keys.length - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            long v = keys[mid] >> 32; // arithmetic shift keeps the sign of negative values
            if (v == value) return (int) (keys[mid] & 0xFFFFFFFFL);
            if (v < value) lo = mid + 1;
            else hi = mid - 1;
        }
        throw new IllegalArgumentException("value " + value + " is not in the inorder array");
    }

    // ---------------- Preorder + Inorder ----------------
    private static int preIndex;

    public static TreeNode buildFromPreorderInorder(int[] preorder, int[] inorder) {
        if (preorder.length != inorder.length) throw new IllegalArgumentException("lengths differ");
        long[] keys = buildIndex(inorder);
        preIndex = 0;
        return buildPre(preorder, keys, 0, inorder.length - 1);
    }

    private static TreeNode buildPre(int[] preorder, long[] keys, int lo, int hi) {
        if (lo > hi) return null;
        TreeNode root = new TreeNode(preorder[preIndex++]);
        int k = indexOf(keys, root.val);
        if (k < lo || k > hi) throw new IllegalArgumentException("traversals don't describe the same tree");
        root.left = buildPre(preorder, keys, lo, k - 1);
        root.right = buildPre(preorder, keys, k + 1, hi);
        return root;
    }

    // ---------------- Postorder + Inorder ----------------
    private static int postIndex;

    public static TreeNode buildFromPostorderInorder(int[] postorder, int[] inorder) {
        if (postorder.length != inorder.length) throw new IllegalArgumentException("lengths differ");
        long[] keys = buildIndex(inorder);
        postIndex = postorder.length - 1;
        return buildPost(postorder, keys, 0, inorder.length - 1);
    }

    private static TreeNode buildPost(int[] postorder, long[] keys, int lo, int hi) {
        if (lo > hi) return null;
        TreeNode root = new TreeNode(postorder[postIndex--]);
        int k = indexOf(keys, root.val);
        if (k < lo || k > hi) throw new IllegalArgumentException("traversals don't describe the same tree");
        root.right = buildPost(postorder, keys, k + 1, hi); // right first: we read postorder backwards
        root.left = buildPost(postorder, keys, lo, k - 1);
        return root;
    }

    // ---------------- Traversals (for checking) ----------------
    private static int fill;

    public static int[] preorder(TreeNode root, int size) { int[] a = new int[size]; fill = 0; pre(root, a); return a; }
    public static int[] inorder(TreeNode root, int size)  { int[] a = new int[size]; fill = 0; in(root, a); return a; }
    public static int[] postorder(TreeNode root, int size) { int[] a = new int[size]; fill = 0; post(root, a); return a; }

    private static void pre(TreeNode n, int[] a)  { if (n == null) return; a[fill++] = n.val; pre(n.left, a); pre(n.right, a); }
    private static void in(TreeNode n, int[] a)   { if (n == null) return; in(n.left, a); a[fill++] = n.val; in(n.right, a); }
    private static void post(TreeNode n, int[] a) { if (n == null) return; post(n.left, a); post(n.right, a); a[fill++] = n.val; }

    public static void main(String[] args) {
        System.out.println("--- BUILD TREE FROM TRAVERSALS DEMO ---");
        //        3
        //       / \
        //      9   20
        //         /  \
        //        15   7
        int[] pre = {3, 9, 20, 15, 7};
        int[] in = {9, 3, 15, 20, 7};
        int[] post = {9, 15, 7, 20, 3};

        TreeNode a = buildFromPreorderInorder(pre, in);
        System.out.println("From preorder + inorder:  root=" + a.val + ", left=" + a.left.val
                + ", right=" + a.right.val + ", right.left=" + a.right.left.val); // 3, 9, 20, 15
        System.out.println("  its postorder: " + Arrays.toString(postorder(a, 5))); // [9, 15, 7, 20, 3]

        TreeNode b = buildFromPostorderInorder(post, in);
        System.out.println("From postorder + inorder: preorder = " + Arrays.toString(preorder(b, 5))); // [3, 9, 20, 15, 7]
    }
}
