package practiceQuestions.algomapio.trees.easy;

import practiceQuestions.algomapio.trees.TreeNode;

public class PathSum {

    /**
     *  Step-by-Step Thought Process
     * Understand the problem: Determine if a binary tree has a root-to-leaf path where the sum of node values equals a given targetSum.
     * Define a recursive function has_sum that takes a node and the current sum (cur_sum):
     * If the node is None, return False (no valid path).
     * Add the node’s value to cur_sum.
     * If the node is a leaf (no left or right child), return True if cur_sum equals targetSum, False otherwise.
     * Recursively check the left and right subtrees, returning True if either has a valid path.
     * Call has_sum on the root with an initial cur_sum of 0 and return the result.
     * */

    public boolean hasPathSum(TreeNode root, int targetSum) {
        return hasSum(root, 0, targetSum);
    }

    private boolean hasSum(TreeNode node, int currentSum, int targetSum) {
        if (node == null) return false;

        currentSum += node.val;

        if (node.left == null && node.right == null) {
            return currentSum == targetSum;
        }

        return hasSum(node.left, currentSum, targetSum) || hasSum(node.right, currentSum, targetSum);
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Path Sum
 * The “Path Sum” problem involves determining whether a binary tree has any root-to-leaf path such that the sum of all node values along that path is equal to a given target value. A valid path must begin at the root node and end at a leaf node, where a leaf node is defined as a node with no children. The challenge is to verify whether any such path exists in the tree.
 *
 * For example, in a binary tree with root value 5, and left and right subtrees that contain values adding up to 22, the function should return true if there’s at least one path from the root to a leaf that totals 22. If no such path exists, the function should return false.
 *
 * Recursive Approach: Depth-First Search with Accumulated Sum
 * The most natural and efficient way to solve this problem is through recursion. Starting from the root, we use a helper function that tracks the current path sum as we move down the tree. At each node, we add the node’s value to an ongoing total. If we reach a leaf node, we check whether the accumulated sum matches the target. If it does, we've found a valid path and return true.
 *
 * If the current node is null, the function immediately returns false, as there’s no path to explore. Otherwise, we proceed to check the left and right child nodes recursively. If either recursive call returns true, it means a valid path has been found. This depth-first traversal continues until either a valid path is confirmed or all paths have been exhausted.
 *
 * Time and Space Complexity
 * The time complexity of this recursive approach is O(n), where n is the number of nodes in the tree. This is because, in the worst case, we may need to visit every node to determine whether a valid path exists. The space complexity is O(h), where h is the height of the tree, which corresponds to the depth of the recursive call stack. In a balanced tree, this would be O(log n); in the worst case (a skewed tree), it could be O(n).
 *
 * Edge Cases to Consider
 * One important case to consider is an empty tree. In this case, since there are no nodes and hence no paths, the function should return false. It’s also crucial to ensure that the check for a valid path is done only at the leaf level. A common mistake is to return true if the path sum matches the target before confirming that the node is actually a leaf.
 *
 * Conclusion
 * The “Path Sum” problem provides a straightforward example of how recursion can be used to traverse a binary tree while carrying information along the path. By combining a running total with careful checks for leaf nodes, we can efficiently determine whether any root-to-leaf path matches the required sum. This technique is widely applicable in other tree-related problems involving path-based constraints or value aggregation.
 * */