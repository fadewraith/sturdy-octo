package practiceQuestions.algomapio.trees.easy;

import practiceQuestions.algomapio.trees.TreeNode;

public class MaximumDepthOfBinaryTree {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Find the maximum depth of a binary tree, which is the number of nodes along the longest path from the root to a leaf.
     * If the root is None, return 0 as the depth of an empty tree.
     * Recursively compute the depth of the left subtree by calling maxDepth on root.left.
     * Recursively compute the depth of the right subtree by calling maxDepth on root.right.
     * Return 1 plus the maximum of the left and right subtree depths, accounting for the current node.
     * */

    public int maxDepth(TreeNode root) {
        if (root == null) return 0;

        int left = maxDepth(root.left);
        int right = maxDepth(root.right);

        return 1 + Math.max(left, right);
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Maximum Depth of a Binary Tree
 * The “Maximum Depth of Binary Tree” problem asks us to determine how deep a given binary tree goes. Depth, in this context, refers to the number of nodes along the longest path from the root node down to a leaf node. A leaf is defined as a node with no children. For example, a tree with just one node (the root) has a depth of one, and each additional level of child nodes increases the depth by one.
 *
 * This problem is fundamental in tree-based recursion and gives great practice in understanding how to compute values that depend on traversing an entire binary structure. The final result represents how tall or “deep” the tree is from top to bottom.
 *
 * Recursive Solution: Depth-First Traversal
 * The optimal solution uses a simple recursive approach. At each node, we calculate the depth of its left and right subtrees by calling the same function recursively on each child. If a node is null, meaning it has no children or we’ve hit the bottom of a branch, we return a depth of zero. Once both the left and right subtree depths are known, we take the maximum of those two values and add one to account for the current node itself. This way, the recursion bubbles up the maximum path length all the way to the root.
 *
 * This method essentially performs a postorder traversal of the tree—visiting the children first, then computing the result at the parent level. It guarantees that each node is visited only once, making it highly efficient for solving this type of problem.
 *
 * Time and Space Complexity
 * The time complexity of the recursive approach is O(n), where n is the total number of nodes in the tree. Every node is visited exactly once to determine its depth. The space complexity is O(h), where h is the height of the tree. This space is used by the call stack during recursion. In the worst-case scenario—when the tree is completely unbalanced—the height could be n, leading to O(n) space. In a balanced tree, the height is log(n), resulting in O(log n) space.
 *
 * Edge Cases to Consider
 * The algorithm should handle an empty tree by returning a depth of zero. This is usually the base case in the recursion. Trees with only one node should return a depth of one, as there is a single level. Additionally, the solution should correctly handle skewed trees, where all nodes appear on only one side—left or right—as this still counts as increasing the depth level by one at each node.
 *
 * Conclusion
 * The “Maximum Depth of Binary Tree” problem is a perfect introduction to recursive traversal techniques. It demonstrates how a global property like depth can be calculated using simple recursive logic and local decisions at each node. Once you’re familiar with this technique, it becomes much easier to tackle more advanced recursive problems involving binary trees, such as computing balanced heights, diameters, or levels.
 * */