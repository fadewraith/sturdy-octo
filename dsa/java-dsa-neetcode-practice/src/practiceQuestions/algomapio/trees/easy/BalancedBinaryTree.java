package practiceQuestions.algomapio.trees.easy;

import practiceQuestions.algomapio.trees.TreeNode;

public class BalancedBinaryTree {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Determine if a binary tree is height-balanced, meaning the height difference between left and right subtrees of every node is at most 1.
     * Define a helper function to compute the height of a subtree recursively.
     * Use a boolean array to track whether the tree is balanced, allowing early termination if imbalance is detected.
     * In the height function, return 0 for a null node.
     * Recursively compute the left subtree’s height; if imbalance is already detected, return early.
     * Compute the right subtree’s height and check if the absolute difference between left and right heights exceeds 1.
     * If imbalance is found, set the boolean flag to false and return early.
     * Return the height as 1 plus the maximum of left and right heights.
     * Call the height function on the root and return the boolean flag.
     * */

    public boolean isBalanced(TreeNode root) {
        return height(root) != -1;
    }

    private int height(TreeNode node) {
        if (node == null) return 0;

        int leftHeight = height(node.left);
        if (leftHeight == -1) return -1;

        int rightHeight = height(node.right);
        if (rightHeight == -1) return -1;

        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }

        return 1 + Math.max(leftHeight, rightHeight);
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Balanced Binary Tree
 * The “Balanced Binary Tree” problem asks whether a given binary tree is height-balanced. A binary tree is considered balanced if, for every node in the tree, the difference in height between the left and right subtrees is no more than one. This definition ensures that the tree is roughly symmetrical, preventing performance issues like those found in highly skewed trees.
 *
 * For example, a tree where every left and right child is populated evenly will be balanced, whereas a tree with all nodes on just one side will not be. The goal is to return a boolean value indicating whether the tree meets this condition.
 *
 * Optimal Approach: Bottom-Up Recursive Traversal
 * A highly efficient way to solve this problem is by using a bottom-up recursive strategy. The main idea is to check the height of each subtree while simultaneously verifying that it is balanced. If at any point a subtree is unbalanced, we can stop checking further and return false early, avoiding unnecessary computation.
 *
 * The process begins with a recursive helper function that computes the height of a subtree. For each node, we recursively calculate the height of the left and right subtrees. If either subtree is found to be unbalanced, we propagate an error signal upwards, often using a special value like -1 to indicate failure. If the height difference between the left and right subtrees exceeds one, we return -1 to indicate that the current subtree is unbalanced. Otherwise, we return the height of the current subtree, which is one plus the maximum height of its two children.
 *
 * This bottom-up strategy ensures that each node is visited only once, and the computation is short-circuited as soon as an imbalance is found, making the algorithm both elegant and efficient.
 *
 * Time and Space Complexity
 * The time complexity of the bottom-up approach is O(n), where n is the number of nodes in the tree. This is because every node is visited exactly once to compute height and balance. The space complexity is O(h), where h is the height of the tree, due to the recursive call stack. In the worst case, this can be O(n) for a skewed tree and O(log n) for a balanced tree.
 *
 * Edge Cases to Consider
 * An empty tree is considered balanced and should return true. Similarly, a single-node tree is inherently balanced. The algorithm must handle these base cases correctly to avoid null pointer errors and ensure logical completeness. Also, skewed trees with long single branches must be tested to verify that the solution identifies unbalanced configurations appropriately.
 *
 * Conclusion
 * The “Balanced Binary Tree” problem is a classic example of combining recursion with structural analysis of binary trees. By leveraging a bottom-up approach, we not only compute necessary values like height but also evaluate balance in a single traversal. This strategy avoids redundant calculations and performs early exits, making it ideal for large-scale or performance-critical systems. It's a fundamental pattern for problems that involve checking global tree properties based on local subtree metrics.
 * */