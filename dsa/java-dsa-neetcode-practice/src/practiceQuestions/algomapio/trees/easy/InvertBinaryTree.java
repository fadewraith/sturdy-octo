package practiceQuestions.algomapio.trees.easy;

import practiceQuestions.algomapio.trees.TreeNode;

public class InvertBinaryTree {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Invert a binary tree by swapping the left and right children of every node.
     * If the root is None, return None.
     * Swap the left and right child pointers of the current root.
     * Recursively invert the left subtree.
     * Recursively invert the right subtree.
     * Return the root of the inverted tree.
     * */

    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        invertTree(root.left);
        invertTree(root.right);

        return root;
    }
}

/**
 * Detailed Explanation
 * Understanding the Problem: Invert Binary Tree
 * The “Invert Binary Tree” problem requires us to transform a given binary tree into its mirror image. This means that for every node in the tree, we must swap its left and right subtrees. Visually, the left and right sides of the tree flip, resulting in a symmetric structure relative to the root. The operation must be performed recursively on every node, ensuring that all descendants are also mirrored.
 *
 * For example, consider a tree where the root has a left child 2 and a right child 3. After inversion, the left child becomes 3 and the right becomes 2. This transformation is applied at every level down the tree to ensure the entire structure is mirrored.
 *
 * Recursive Approach: Postorder Traversal
 * A straightforward way to solve this problem is through recursion. The recursive process involves three steps at each node. First, we check whether the current node is null; if it is, we return immediately. Otherwise, we recursively call the function to invert the left subtree, then recursively invert the right subtree. After processing both subtrees, we swap the pointers so that the left child becomes the right and the right becomes the left.
 *
 * This approach naturally follows a postorder traversal pattern, where children are processed before their parent node. By visiting each node only once and swapping its children, we ensure the entire tree is correctly inverted with minimal complexity.
 *
 * Time and Space Complexity
 * The time complexity of this solution is O(n), where n is the number of nodes in the binary tree. This is because each node is visited exactly once during the recursive traversal. The space complexity depends on the depth of the recursion stack. In the worst case of an unbalanced tree, the depth may reach O(n), while in the average case of a balanced tree, the space complexity is O(log n) due to the height of the tree.
 *
 * Edge Cases to Consider
 * The algorithm should handle an empty tree by returning null immediately. It should also correctly handle trees with only one node, in which case there is nothing to swap and the tree remains the same. If a node has only one child, the algorithm should still perform the swap, ensuring even asymmetric trees are correctly mirrored.
 *
 * Conclusion
 * Inverting a binary tree is a classic example of how recursion can be used to traverse and manipulate tree structures efficiently. The simplicity of the solution belies its elegance—by applying a basic swap operation at every node, we can completely transform the structure of the tree. This problem is commonly featured in coding interviews and is a great exercise in understanding recursive tree traversal patterns.
 * */