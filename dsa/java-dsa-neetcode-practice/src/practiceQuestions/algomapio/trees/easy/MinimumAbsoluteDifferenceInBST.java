package practiceQuestions.algomapio.trees.easy;

import practiceQuestions.algomapio.trees.TreeNode;

public class MinimumAbsoluteDifferenceInBST {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Find the minimum absolute difference between the values of any two nodes in a binary search tree (BST).
     * Initialize a list min_distance with infinity to store the smallest difference found.
     * Initialize a list prev with None to track the previously visited node’s value during in-order traversal.
     * Define a recursive DFS function to perform an in-order traversal (left, root, right):
     * If the node is None, return.
     * Recursively traverse the left subtree.
     * If prev[0] is not None, update min_distance[0] to the minimum of min_distance[0] and the difference between the current node’s value and prev[0].
     * Update prev[0] to the current node’s value.
     * Recursively traverse the right subtree.
     * Call DFS on the root and return min_distance[0].
     * */

    private TreeNode prev = null;
    private int minDiff = Integer.MAX_VALUE;

    public int getMinimumDifference(TreeNode root) {
        inOrderTraversal(root);
        return minDiff;
    }

    private void inOrderTraversal(TreeNode node) {
        if (node == null) {
            return;
        }

        // Traverse the left subtree
        inOrderTraversal(node.left);

        // Process the current node
        if (prev != null) {
            minDiff = Math.min(minDiff, node.val - prev.val);
        }

        // Update the previous node to the current node
        prev = node;

        // Traverse the right subtree
        inOrderTraversal(node.right);
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Minimum Absolute Difference in BST
 * The goal of this problem is to compute the smallest absolute difference between the values of any two nodes in a Binary Search Tree (BST). Because a BST is a tree where the left child is smaller and the right child is larger than the parent node, we can leverage its sorted nature for an efficient solution. The challenge lies in finding the closest pair of values without having to compare every pair explicitly.
 *
 * For example, if a BST contains the values 2, 4, and 6, the differences are 2 (between 2 and 4) and 2 (between 4 and 6). The minimum of these is 2, which would be the correct result.
 *
 * Optimal Approach: In-Order Traversal with a Running Comparison
 * The most efficient way to solve this problem is by using an in-order traversal, which visits the nodes of a BST in ascending order. This allows us to compare each node only with its immediate predecessor, as the smallest absolute difference will always occur between two adjacent values in a sorted sequence.
 *
 * As we perform the traversal, we keep track of the value of the previously visited node. For each node visited, we compute the absolute difference between the current node and the previous one. If this difference is smaller than the current minimum, we update our result. By the time the traversal is complete, we will have found the smallest such difference.
 *
 * Time and Space Complexity
 * The time complexity of this approach is O(n), where n is the number of nodes in the BST. Each node is visited exactly once during the traversal. The space complexity is O(h), where h is the height of the tree. This space is used by the recursion stack and can be as small as O(log n) for a balanced tree or as large as O(n) for a completely skewed tree.
 *
 * Edge Cases to Consider
 * The problem assumes that the BST has at least two nodes, so there's no need to handle the case where the tree is empty or has only one node. However, it's important to initialize the minimum difference with a large value (such as infinity) and ensure the previous node tracker starts as None so that the first comparison is skipped properly.
 *
 * Conclusion
 * The “Minimum Absolute Difference in BST” problem is a great example of how in-order traversal can be leveraged for efficient range-based calculations in a BST. Rather than comparing every pair of nodes, we make use of the tree’s ordering properties to compare only adjacent values in sorted order. This strategy minimizes both time and space, making it a highly efficient solution that exemplifies the power of recursion and tree traversal patterns.
 * */