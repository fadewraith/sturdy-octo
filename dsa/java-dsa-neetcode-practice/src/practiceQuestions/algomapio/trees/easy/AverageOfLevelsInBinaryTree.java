package practiceQuestions.algomapio.trees.easy;

import practiceQuestions.algomapio.trees.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AverageOfLevelsInBinaryTree {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Compute the average value of nodes at each level in a binary tree.
     * Initialize an empty list to store the averages of each level.
     * Create a deque and add the root node to it.
     * While the deque is not empty, process each level:
     * Get the number of nodes (n) at the current level from the deque’s length.
     * Initialize a sum (summ) to 0 for the current level’s values.
     * For each of the n nodes, pop the leftmost node, add its value to summ, and append its left and right children (if they exist) to the deque.
     * Compute the average as summ divided by n and append it to the averages list.
     * Return the list of averages.
     * */

    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> avgs = new ArrayList<>();
        if (root == null) return avgs;

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            double sum = 0;
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                sum += node.val;

                if (node.left != null) {
                    q.add(node.left);
                }
                if (node.right != null) {
                    q.add(node.right);
                }
            }
            avgs.add(sum / size);
        }

        return avgs;
    }
}

/**
 * Detailed Explanation
 * Understanding the Problem
 * The "Average of Levels in Binary Tree" problem challenges you to compute the average value of all nodes at each level in a binary tree. This means for every level (or depth) in the tree, you need to gather the values of the nodes, calculate their mean, and return a list of these averages. This is a common problem in breadth-first search (BFS) tree traversal patterns and is frequently encountered in technical interviews.
 *
 * Algorithm Strategy
 * The most efficient way to solve this problem is to use a level-order traversal, also known as BFS (Breadth-First Search). The idea is to traverse the tree level by level using a queue (typically implemented with a deque for efficient pops from the left). At each level, you sum all node values and divide by the number of nodes at that level to compute the average.
 *
 * First, you initialize a queue and insert the root node. While the queue is not empty, you repeat the following steps: determine the number of nodes at the current level, initialize a running sum, and then iterate through all nodes at that level. For each node, you add its value to the sum and enqueue its left and right children (if they exist). After processing all nodes at that level, compute the average by dividing the sum by the count, and append it to the result list.
 *
 * Time and Space Complexity
 * Time Complexity: O(n), where n is the total number of nodes in the binary tree. Each node is visited exactly once.
 * Space Complexity: O(w), where w is the maximum width of the tree (i.e., the number of nodes at the widest level), due to the queue holding nodes at each level.
 * Why This Approach Works
 * Level-order traversal guarantees that we process all nodes on the same level together, which is exactly what we need for calculating averages per level. Using a queue ensures efficient access and updates, and because we track the number of nodes per level explicitly, computing the mean becomes straightforward. This approach is both intuitive and scalable, making it an ideal solution for trees of any size or structure.
 *
 * Conclusion
 * This problem highlights how traversal strategies like BFS can be adapted to solve numerical aggregation problems in tree structures. By understanding how to manage and process each level of a tree separately, you not only solve this problem efficiently but also build a foundation for tackling other level-based binary tree challenges.
 * */
