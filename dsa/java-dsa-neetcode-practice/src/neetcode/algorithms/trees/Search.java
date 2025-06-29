package neetcode.algorithms.trees;

public class Search {

    public boolean search(TreeNode root, int target) {
        if (root == null) {
            return false;
        }

        if (target > root.val) {
            return search(root.right, target);
        } else if (target < root.val) {
            return search(root.left, target);
        } else {
            return true;
        }
    }
}
