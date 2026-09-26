package practiceQuestions.algomapio.trees.easy;

import practiceQuestions.algomapio.trees.TreeNode;

public class SymmetricTree {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Determine if a binary tree is symmetric, i.e., its left and right subtrees are mirror images.
     * Define a helper function same(root1, root2) to check if two subtrees are mirror images:
     * If both root1 and root2 are None, return True.
     * If exactly one of root1 or root2 is None, return False.
     * If root1.val does not equal root2.val, return False.
     * Recursively check if root1.left is a mirror of root2.right and root1.right is a mirror of root2.left.
     * Call same(root, root) to check if the tree is symmetric with itself.
     * Return the result of the same function.
     * */

    public boolean isSymmetric(TreeNode root) {
        return isMirror(root, root);
    }

    private boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        if (t1.val != t2.val) return false;

        return isMirror(t1.left, t2.right) && isMirror(t1.right, t2.left);
    }

}

