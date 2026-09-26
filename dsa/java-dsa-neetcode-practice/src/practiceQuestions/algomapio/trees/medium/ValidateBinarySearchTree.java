package practiceQuestions.algomapio.trees.medium;

import practiceQuestions.algomapio.trees.TreeNode;

public class ValidateBinarySearchTree {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Determine if a binary tree is a valid Binary Search Tree (BST), where for each node, all values in the left subtree are less than the node’s value, and all values in the right subtree are greater.
     * Define a helper function is_valid that takes a node and the valid range for its values (minn and maxx).
     * If the node is None, return True, as an empty subtree is valid.
     * Check if the node’s value is within the valid range (greater than minn and less than maxx). If not, return False.
     * Recursively validate the left subtree with the range (minn, node.val) to ensure all values are less than the node’s value.
     * Recursively validate the right subtree with the range (node.val, maxx) to ensure all values are greater than the node’s value.
     * Return True only if both subtrees are valid.
     * Call is_valid on the root with the initial range of negative infinity to positive infinity and return the result.
     * */

    public boolean isValidBST(TreeNode root) {
        return isValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValid(TreeNode node, long min, long max) {
        if (node == null) {
            return true;
        }

        if (node.val <= min || node.val >= max) {
            return false;
        }

        return isValid(node.left, min, node.val) && isValid(node.right, node.val, max);
    }
}

/**
 * Detailed Explanation
 * Understanding the Problem: Validate Binary Search Tree
 * The “Validate Binary Search Tree” problem challenges us to determine whether a given binary tree adheres to the properties of a binary search tree (BST). In a valid BST, for every node, all values in its left subtree must be strictly less than the node’s value, and all values in its right subtree must be strictly greater. This must hold true recursively for all subtrees of the tree.
 *
 * A common mistake is to only check that the left and right child nodes follow the rule relative to the current node, without considering the entire range constraints that propagate through the tree. For example, a value deep in the right subtree of a left child must still be less than the root.
 *
 * Approach: Recursive Validation with Ranges
 * The optimal approach is to perform a recursive traversal where each node is validated against a dynamic range of valid values. At the root, the allowed range is from negative infinity to positive infinity. As we descend into the left subtree, the upper bound becomes the value of the current node. For the right subtree, the lower bound becomes the value of the current node. This ensures that each node is compared not only with its immediate parent but also with all its ancestors.
 *
 * For example, if a node with value 10 has a left child of 5 and a right child of 15, and 15’s left child is 6, that would violate the BST rule because 6 is less than 10, even though it is in the right subtree of 15. The recursive validation strategy would catch this, while a naive approach might not.
 *
 * Time and Space Complexity
 * The time complexity is O(n), where n is the number of nodes in the tree. Each node is visited exactly once during the traversal. The space complexity is O(h), where h is the height of the tree. This space is used by the recursion stack. In a balanced tree, this would be O(log n), while in the worst case (a skewed tree), it could be O(n).
 *
 * Edge Cases to Consider
 * An empty tree is considered a valid BST. A single-node tree is also valid by definition. It's important to use strict inequality when comparing values—duplicate values are not allowed in a proper BST if the definition assumes uniqueness. Additionally, the solution must work even with very large or very small values, so using float('-inf') and float('inf') as initial bounds is a safe and practical approach.
 *
 * Conclusion
 * The “Validate Binary Search Tree” problem is a foundational question that teaches how to properly enforce BST invariants across the entire structure of a tree. By passing dynamic value constraints down the tree and validating each node against those bounds, we ensure correctness at all levels. This recursive, range-based strategy is not only elegant but also efficient and scalable to large binary trees.
 * */

