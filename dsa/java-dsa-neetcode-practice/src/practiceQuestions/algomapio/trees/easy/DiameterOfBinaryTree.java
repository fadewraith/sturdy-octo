package practiceQuestions.algomapio.trees.easy;

import practiceQuestions.algomapio.trees.TreeNode;

public class DiameterOfBinaryTree {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Find the length of the longest path between any two nodes inraj a binary tree, measured by the number of edges.
     * Initialize a list largest_diameter with a single element 0 to track the maximum diameter.
     * Define a recursive height function that returns the height of a subtree.
     * If the root is None, return 0.
     * Recursively compute the left subtree’s height.
     * Recursively compute the right subtree’s height.
     * Calculate the diameter through the current node as the sum of left and right heights.
     * Update largest_diameter[0] to the maximum of itself and the current diameter.
     * Return the height as 1 plus the maximum of left and right heights.
     * Call the height function on the root and return largest_diameter[0].
     *
     * // Time Complexity: O(n)
     * // Space Complexity: O(h) // "h" is the height of the tree
     * */

    private int largestDiameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        height(root);
        return largestDiameter;
    }

    private int height(TreeNode node) {
        if (node == null) return 0;

        int leftHeight = height(node.left);
        int rightHeight = height(node.right);
        int diameter = leftHeight + rightHeight;

        largestDiameter = Math.max(largestDiameter, diameter);

        return 1 + Math.max(leftHeight, rightHeight);
    }
}

/**
 * Detailed Explanation
 * Understanding the Problem: Diameter of Binary Tree
 * The “Diameter of Binary Tree” problem challenges us to find the length of the longest path between any two nodes in a binary tree. This path does not necessarily have to pass through the root. The length is measured in terms of edges, not the number of nodes. The goal is to identify the path that connects the farthest pair of nodes and return the number of edges on that path.
 *
 * For example, consider a binary tree where one side has a long left-leaning branch and the other side has a deep right-leaning branch. The longest path may span from a leaf node on the left side to a leaf node on the right side, going through the root or another node. Understanding this path requires analyzing the structure of the tree recursively.
 *
 * Recursive Approach: Postorder Traversal and Height Calculation
 * The optimal solution is built around the concept of computing the height of each subtree using a postorder traversal. At each node, we calculate the height of the left and right subtrees recursively. Once we have the heights, we can determine the potential diameter at that node by summing the heights of its left and right children.
 *
 * A variable is used to track the global maximum diameter encountered so far. This variable is updated whenever the sum of the left and right heights at a node is greater than the current maximum. After computing the diameter through the current node, the function returns the height of the current node as one plus the maximum of its children's heights. This ensures that each call provides the correct height back to its parent, while also checking for a larger diameter in the process.
 *
 * Time and Space Complexity
 * The time complexity of this approach is O(n), where n is the number of nodes in the tree. This is because each node is visited once to compute its height and potential contribution to the diameter. The space complexity is O(h), where h is the height of the tree. This space is used by the call stack in the recursive implementation, which is O(log n) for balanced trees and O(n) for skewed trees.
 *
 * Edge Cases to Consider
 * It is important to handle the case of an empty tree, where the diameter should be zero. A single-node tree should also return a diameter of zero, since there are no edges. The implementation must also correctly handle skewed trees, where all nodes are aligned on one side. In such cases, the diameter is equal to the number of nodes minus one.
 *
 * Conclusion
 * The “Diameter of Binary Tree” problem showcases a powerful pattern in tree algorithms: combining local height computations with global state updates. By recursively evaluating subtree heights and updating the longest path found so far, we can compute the diameter in a single traversal. This approach is not only efficient but also highlights how tree-based problems often require balancing local recursion with global tracking to arrive at an optimal solution.
 * */