package algorithms.tree;

/**
 * DIAMETER OF A BINARY TREE
 * 
 * WHAT IT IS:
 * The length of the LONGEST path between ANY two nodes in a tree.
 * 
 * CRITICAL CATCH:
 * The longest path may or may NOT pass through the root of the entire tree! 
 * (Imagine a tree where the root only has a short right branch, but the left branch 
 * is massively deep with its own huge left and right sub-branches).
 * 
 * WHEN TO USE THIS:
 * - Network diameter routing (finding the maximum latency between any two end nodes).
 * 
 * STRATEGY:
 * We compute the height/depth of the tree recursively.
 * While doing so, at EVERY SINGLE NODE, we calculate `left_height + right_height`.
 * If this sum is greater than our global maximum diameter, we update the global max.
 * Then we return `max(left, right) + 1` up to the parent so it can compute its own height.
 * 
 * COMPLEXITY:
 * Time: O(N) because we visit every node exactly once.
 * Space: O(H) for recursion stack.
 */
public class DiameterOfTree {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int x) { val = x; }
    }

    private static int maxDiameter = 0;

    public static int diameterOfBinaryTree(TreeNode root) {
        maxDiameter = 0;
        height(root);
        return maxDiameter;
    }

    private static int height(TreeNode node) {
        if (node == null) return 0;

        int leftHeight = height(node.left);
        int rightHeight = height(node.right);

        // Update the global maximum diameter if the path through THIS node is the longest seen so far
        int currentDiameter = leftHeight + rightHeight;
        if (currentDiameter > maxDiameter) {
            maxDiameter = currentDiameter;
        }

        // Return the height of this subtree to the parent
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public static void main(String[] args) {
        System.out.println("--- DIAMETER OF TREE DEMO ---");
        
        // Tree:
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

        System.out.println("Diameter is: " + diameterOfBinaryTree(root)); 
        // Expected: 3 (Path: 4 -> 2 -> 1 -> 3, which is 3 edges long)
    }
}
