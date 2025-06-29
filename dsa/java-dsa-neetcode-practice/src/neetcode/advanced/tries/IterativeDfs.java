package neetcode.advanced.tries;

import java.util.Stack;

public class IterativeDfs {

    // Time and space: O(n)
    public static void inorder(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;

        while (curr != null || !stack.isEmpty()) {
            if (curr != null) {
                stack.push(curr);
                curr = curr.left;
            } else {
                curr = stack.pop();
                System.out.println(curr.val);
                curr = curr.right;
            }
        }
    }

    // Time and space: O(n)
    public static void preorder(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;

        while (curr != null || !stack.isEmpty()) {
            if (curr != null) {
                System.out.println(curr.val);
                if (curr.right != null) {
                    stack.push(curr.right);
                }
                curr = curr.left;
            } else {
                curr = stack.pop();
            }
        }
    }

    // Time and space: O(n)
    public static void postorder(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        Stack<Boolean> visit = new Stack<>();
        visit.push(false);

        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            boolean visited = visit.pop();
            if (curr != null) {
                if (visited) {
                    System.out.println(curr.val);
                } else {
                    stack.push(curr);
                    visit.push(true);
                    stack.push(curr.right);
                    visit.push(false);
                    stack.push(curr.left);
                    visit.push(false);
                }
            }
        }
    }
}