package educativetutorials.codingpatterns.commonutilities;

import java.util.List;
import java.util.Queue;

public class BinaryTree {

    private TreeNode root;

    private TreeNode createBinaryTree(List<Integer> nodes) {
        if (nodes.isEmpty() || nodes.get(0) == 0) {
            return null;
        }
        TreeNode root = new TreeNode(nodes.get(0));
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int i = 1;
        while (i < nodes.size()) {
            TreeNode curr = q.poll();
            if (i < nodes.size() && nodes.get(i) != 0) {
                curr.left = new TreeNode(nodes.get(i));
                curr.left.parent = curr;
                q.offer(curr.left);
            }
            i++;
            if (i < nodes.size() && nodes.get(i) != 0) {
                curr.right = new TreeNode(nodes.get(i));
                curr.right.parent = curr;
                q.offer(curr.right);
            }
            i++;
        }
        return root;
    }

    public BinaryTree(List<Integer> nodes) {
        this.root = createBinaryTree(nodes);
    }

    public TreeNode find(TreeNode root, int value) {
        if (root == null) {
            return null;
        }
        Queue<TreeNode> q = new LinkedList();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode currentNode = q.poll();
            if (currentNode.data == value) {
                return currentNode;
            }
            if (currentNode.left != null) {
                q.offer(currentNode.left);
            }
            if (currentNode.right != null) {
                q.offer(currentNode.right);
            }
        }
        return null;
    }

    public TreeNode getRoot() {
        return root;
    }
}
