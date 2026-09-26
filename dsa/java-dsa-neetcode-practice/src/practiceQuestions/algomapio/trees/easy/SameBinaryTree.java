package practiceQuestions.algomapio.trees.easy;

import practiceQuestions.algomapio.trees.TreeNode;

public class SameBinaryTree {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Determine if two binary trees are identical in structure and node values.
     * Check if both nodes p and q are None; if true, return True as both trees are empty.
     * Check if exactly one of p or q is None; if true, return False as the trees differ in structure.
     * Check if the values of p and q differ; if true, return False as the trees have different values.
     * Recursively check if the left subtrees (p.left, q.left) and right subtrees (p.right, q.right) are identical.
     * Return True only if both recursive checks return True.
     * */

    public boolean isSameTree(TreeNode p, TreeNode q) {
        return isSame(p, q);
    }

    private boolean isSame(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        if (p.val != q.val) return false;

        return isSame(p.left, q.left) && isSame(p.right, q.right);
    }
}

/**
 * Detailed Explanation
 * Understanding the Problem: Same Binary Tree
 * The “Same Binary Tree” problem challenges us to determine whether two binary trees are exactly the same. Two trees are considered identical if they are structurally the same and the nodes at each corresponding position have the same values. This includes ensuring that the left and right subtrees are also identical in both structure and content. It’s a fundamental comparison task that tests recursive traversal and structural understanding of trees.
 *
 * For example, two trees with root values of 1 and identical left and right children (say, nodes with values 2 and 3) would be considered the same. If even one node differs in either value or position, the trees would be considered different.
 *
 * Recursive Approach: Comparing Node by Node
 * The optimal solution to this problem is recursive. We begin by checking the base cases. If both current nodes being compared are null, that means we’ve reached the end of both trees at this path, and they match so far. If only one of the nodes is null, this indicates a structural difference, and the trees are not the same.
 *
 * If both nodes exist, we compare their values. If the values are different, we immediately know the trees are not identical. If the values match, we then recursively compare their left children and their right children. The trees are only considered the same if all recursive calls return true, confirming that both subtrees match at every level.
 *
 * Time and Space Complexity
 * The time complexity is O(n), where n is the number of nodes in the tree. This is because, in the worst case, we have to compare every node in both trees. The space complexity is O(h), where h is the height of the tree, due to the recursion stack. In a balanced binary tree, this results in O(log n) space, and in a completely skewed tree, it becomes O(n).
 *
 * Edge Cases to Consider
 * It’s important to handle scenarios where both trees are empty—this should return true, as two empty trees are considered the same. If only one of the trees is empty, the result should be false. Additionally, even a single node mismatch in value or position should cause the function to return false, reinforcing the requirement that the trees must match perfectly in structure and values.
 *
 * Conclusion
 * The “Same Binary Tree” problem is a straightforward yet essential exercise in recursive tree traversal. It reinforces the pattern of breaking down a tree problem into base cases and recursive steps. By comparing both the values and the structures at each level, we can confidently determine whether two trees are truly identical. This foundational technique is widely used in more complex tree comparison problems and interview questions.
 * */
