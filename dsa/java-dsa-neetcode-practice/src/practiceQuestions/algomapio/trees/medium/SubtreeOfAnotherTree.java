package practiceQuestions.algomapio.trees.medium;

import practiceQuestions.algomapio.trees.TreeNode;

public class SubtreeOfAnotherTree {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Determine if a binary tree subRoot is a subtree of another binary tree root.
     * Define a helper function sameTree(p, q) to check if two trees are identical:
     * If both p and q are None, return True.
     * If exactly one of p or q is None, return False.
     * If p.val does not equal q.val, return False.
     * Recursively check if the left and right subtrees are identical.
     * Define a helper function has_subtree(root) to check if subRoot is a subtree of root:
     * If root is None, return False.
     * If sameTree(root, subRoot) is True, return True.
     * Recursively check if subRoot is a subtree of root.left or root.right.
     * Call has_subtree(root) and return the result.
     * */

    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        return hasSubtree(root, subRoot);
    }

    private boolean hasSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null) {
            return false;
        }

        if (isSameTree(root, subRoot)) {
            return true;
        }

        return hasSubtree(root.left, subRoot) || hasSubtree(root.right, subRoot);
    }

    private boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }

        if (p == null || q == null) {
            return false;
        }

        if (p.val != q.val) {
            return false;
        }

        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}

/**
 * Detailed Explanation
 * Understanding the Problem: Subtree of Another Tree
 * The “Subtree of Another Tree” problem asks whether a binary tree subRoot is a subtree of another binary tree root. A subtree is defined as a node in the larger tree and all of its descendants. This means the goal is to determine if any node in the main tree has a structure and node values identical to the given smaller tree.
 *
 * For example, if subRoot is a three-node tree with values 4, 1, and 2, and root contains this exact subtree somewhere in its structure, the function should return true. If not, the function should return false. This is not just about values being present, but the structure and arrangement must match exactly.
 *
 * Approach: Recursive Tree Comparison
 * The solution involves two main recursive functions. First, a helper function compares two trees to see if they are identical. This function checks whether both trees are empty (in which case they match), whether only one is empty (in which case they do not match), and whether the root values are equal. If the values are equal, it recursively compares their left and right children. This ensures that every node and branch structure is identical.
 *
 * The second part of the solution involves traversing the main tree. At every node of the larger root tree, we check whether the subtree rooted at that node matches subRoot using the helper function. If a match is found, we return true immediately. If not, we recursively check both the left and right children of the current node. This process ensures that we examine every possible location where subRoot could exist in root.
 *
 * Time and Space Complexity
 * The time complexity of this algorithm is O(m * n), where n is the number of nodes in the main tree and m is the number of nodes in subRoot. In the worst case, for every node in root, we may compare it with subRoot, and each comparison may involve checking all of subRoot. The space complexity is O(h), where h is the height of the tree, due to recursive call stack usage. This could be O(log n) for balanced trees or O(n) in the worst case.
 *
 * Edge Cases to Consider
 * If subRoot is null, the result should always be true because an empty tree is considered a subtree of any tree. Conversely, if root is null but subRoot is not, the result must be false. It's also important to ensure that tree structure and node values are checked together—subRoot must match both the form and content of a part of the main tree.
 *
 * Conclusion
 * The “Subtree of Another Tree” problem is a classic recursive tree comparison challenge. By using a combination of subtree equality checks and full traversal of the main tree, we can efficiently determine whether one tree exists within another. This technique is foundational in many tree-related problems and helps develop a solid understanding of how to recursively compare and navigate binary trees.
 * */
