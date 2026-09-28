package algorithms.tree;

/**
 * LOWEST COMMON ANCESTOR (LCA)
 * 
 * WHAT IT IS:
 * Finds the lowest (deepest) node in a Binary Tree that has both node P and node Q 
 * as descendants (where we allow a node to be a descendant of itself).
 * 
 * WHEN TO USE THIS:
 * - When determining the closest shared hierarchy between two objects 
 *   (e.g., finding the closest common manager of two employees in an org chart).
 * 
 * VARIATIONS:
 * 1. Recursive Single-Pass (implemented below): O(N) time. Best for one-off queries.
 * 2. Binary Lifting / Sparse Table: O(N log N) preprocessing, O(log N) per query. 
 *    Best if the tree is static and you have thousands of LCA queries to answer!
 * 3. BST Property: If the tree is a Binary SEARCH Tree, LCA is trivial in O(log N):
 *    Just walk down. The first node you hit that is mathematically BETWEEN P and Q is the LCA.
 * 
 * STRATEGY (Recursive approach):
 * We recursively search down. If we hit null, return null. If we hit P or Q, return it!
 * When the recursion bubbles up, if a node receives NON-NULL from BOTH its left and right 
 * children, it means P is on one side and Q is on the other. That node MUST be the LCA!
 * 
 * COMPLEXITY:
 * Time: O(N) where N is number of nodes.
 * Space: O(H) where H is height of tree (due to recursion stack).
 */
public class LowestCommonAncestor {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int x) { val = x; }
    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // Base case: if root is null, or we found P or Q, return root.
        if (root == null || root == p || root == q) {
            return root;
        }

        // Search in left and right subtrees
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // If BOTH left and right returned something, P and Q are in different subtrees.
        // Therefore, THIS current node is their Lowest Common Ancestor.
        if (left != null && right != null) {
            return root;
        }

        // Otherwise, pass up whichever side actually found a target
        return left != null ? left : right;
    }

    public static void main(String[] args) {
        System.out.println("--- LCA DEMO ---");
        
        // Tree:
        //       3
        //      / \
        //     5   1
        //    / \
        //   6   2
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(5);
        root.right = new TreeNode(1);
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);

        TreeNode p = root.left.left;  // Node 6
        TreeNode q = root.left.right; // Node 2

        TreeNode lca = lowestCommonAncestor(root, p, q);
        System.out.println("LCA of 6 and 2 is: " + lca.val); // Expected: 5
    }
}
