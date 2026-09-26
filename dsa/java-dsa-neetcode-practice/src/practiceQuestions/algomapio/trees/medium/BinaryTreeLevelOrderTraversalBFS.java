package practiceQuestions.algomapio.trees.medium;

import practiceQuestions.algomapio.trees.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTreeLevelOrderTraversalBFS {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Return the level-order traversal of a binary tree as a list of lists, where each list contains the node values at a given level.
     * Check if the root is None; if so, return None.
     * Initialize a deque and append the root node to it.
     * Initialize an empty list (ans) to store the result.
     * While the deque is not empty, process each level:
     * Create an empty list (level) to store the current level’s node values.
     * Get the number of nodes (n) at the current level from the deque’s length.
     * For each of the n nodes, pop the leftmost node, append its value to the level list, and append its left and right children (if they exist) to the deque.
     * Append the level list to the result list (ans).
     * Return the result list (ans).
     * */

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) return ans;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            int n = queue.size();

            for (int i = 0; i < n; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);

                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }

            ans.add(level);
        }

        return ans;
    }
}