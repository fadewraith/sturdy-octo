package algorithms.tree;

/**
 * MORRIS TRAVERSAL (Inorder)
 * 
 * WHAT IT IS:
 * An extremely advanced tree traversal algorithm that performs an Inorder traversal 
 * (Left, Root, Right) in strictly O(1) EXTRA SPACE.
 * 
 * WHEN TO USE THIS:
 * - This is usually a "do you know the advanced trick" interview follow-up. 
 * - Normal recursive inorder takes O(H) stack space. Iterative with a Stack takes O(H) space.
 * - Morris Traversal is for strictly memory-constrained environments where O(1) space is mandatory.
 * 
 * STRATEGY (Threaded Binary Tree):
 * We dynamically alter the tree structure DURING the traversal!
 * Whenever we are at a node, we find its Inorder Predecessor (the right-most node 
 * in its left subtree). We take the predecessor's NULL right pointer and wire it 
 * to point BACK to the current node (creating a temporary cycle, or "thread").
 * We then traverse left.
 * Later, when we reach that predecessor again, the thread brings us right back up 
 * to the parent, preventing us from needing a Stack! We then sever the thread to 
 * restore the tree back to its original state.
 * 
 * COMPLEXITY:
 * Time: O(N) (Every edge is traversed at most 3 times)
 * Space: O(1)
 */
public class MorrisTraversal {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int x) { val = x; }
    }

    public static void inorderMorris(TreeNode root) {
        TreeNode curr = root;

        while (curr != null) {
            if (curr.left == null) {
                // If no left child, we visit the node and move right
                System.out.print(curr.val + " ");
                curr = curr.right;
            } else {
                // Find the inorder predecessor of curr
                TreeNode predecessor = curr.left;
                while (predecessor.right != null && predecessor.right != curr) {
                    predecessor = predecessor.right;
                }

                // Make curr the right child of its inorder predecessor (CREATE THREAD)
                if (predecessor.right == null) {
                    predecessor.right = curr;
                    curr = curr.left;
                } 
                // Revert the changes made in the 'if' part to restore the original tree (SEVER THREAD)
                else {
                    predecessor.right = null;
                    System.out.print(curr.val + " ");
                    curr = curr.right;
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("--- MORRIS TRAVERSAL (O(1) Space Inorder) DEMO ---");
        
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        System.out.print("Inorder Output: ");
        inorderMorris(root); 
        // Expected: 4 2 5 1 3
    }
}
