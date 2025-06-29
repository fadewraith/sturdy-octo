package neetcode.algorithms.backtracking;

import neetcode.algorithms.trees.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class PathWithoutZero {

//    determine if a path exists from the root of the tree to a leaf node. it must not contain any 0s
    public static boolean canReachLeaf(TreeNode root) {
        if (root == null || root.val == 0) return false;
        if (root.left == null && root.right == null) return true;
        if (canReachLeaf(root.left)) return true;
        if(canReachLeaf(root.right)) return true;
        return false;
    }

    // write a function to find the path from the root to the leaf node
    public static boolean leafPath(TreeNode root, List<Integer> path) {
        if (root == null || root.val == 0) return false;
        path.add(root.val);
        if (root.left == null && root.right == null) return true;
        if (leafPath(root.left, path)) return true;
        if (leafPath(root.right, path)) return true;
        path.remove(path.size() - 1);
        return false;
    }

    // write a main method to test the above functions
    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(0);
        root.right = new TreeNode(1);
        root.left.left = null;
        root.left.right = new TreeNode(7);
        root.right.left = new TreeNode(3);
        root.right.right = new TreeNode(5);
        root.right.left.left = null;
        root.right.left.right = new TreeNode(0);

        List<Integer> path = new ArrayList<>();
        boolean leafedPath = leafPath(root, path);
        if (leafedPath) System.out.println("Path: " + path);
        else System.out.println("No path found");
    }

}
