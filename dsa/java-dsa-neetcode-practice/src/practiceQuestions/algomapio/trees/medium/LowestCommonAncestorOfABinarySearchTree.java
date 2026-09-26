package practiceQuestions.algomapio.trees.medium;

import practiceQuestions.algomapio.trees.TreeNode;

public class LowestCommonAncestorOfABinarySearchTree {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Find the lowest common ancestor of two nodes p and q in a binary search tree (BST).
     * Initialize a list lca with the root node to store the lowest common ancestor.
     * Define a recursive search function that takes a node as input.
     * If the node is None, return to skip null nodes.
     * Update lca[0] to the current node as a potential ancestor.
     * If the current node is either p or q, return as no further search is needed.
     * If the current node’s value is less than both p’s and q’s values, recursively search the right subtree.
     * If the current node’s value is greater than both p’s and q’s values, recursively search the left subtree.
     * Otherwise, the current node is the lowest common ancestor, so return.
     * Call search on the root and return lca[0].
     * */

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode lca = root;

        while (root != null) {
            if (root.val < p.val && root.val < q.val) {
                root = root.right;
            } else if (root.val > p.val && root.val > q.val) {
                root = root.left;
            } else {
                lca = root;
                break;
            }
        }

        return lca;
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Lowest Common Ancestor of a Binary Search Tree
 * The goal of this problem is to find the lowest common ancestor (LCA) of two given nodes in a Binary Search Tree (BST). The LCA of two nodes p and q in a BST is defined as the lowest node in the tree that has both p and q as descendants (a node can be a descendant of itself). Due to the ordered structure of BSTs, this problem can be solved more efficiently than in general binary trees.
 *
 * For example, if the BST is structured such that node 6 is an ancestor of both nodes 2 and 8, and no node lower than 6 satisfies this condition, then 6 is the LCA.
 *
 * Approach: Binary Search Logic on Tree Structure
 * Because the tree is a BST, all nodes in the left subtree of a node have smaller values, and all nodes in the right subtree have larger values. This allows us to navigate the tree similarly to how we would in a binary search:
 *
 * If both p and q have values less than the current node, the LCA must lie in the left subtree. If both values are greater, the LCA must lie in the right subtree. But if one value is on the left and the other is on the right—or if the current node is equal to either p or q—then the current node is the LCA.
 *
 * This decision-making process allows us to stop the search early and return the result in logarithmic time in the best-case scenario, especially when the tree is balanced.
 *
 * Time and Space Complexity
 * The time complexity is O(h), where h is the height of the tree. In a balanced BST, this is O(log n), where n is the number of nodes. However, in a skewed tree (e.g., all nodes on one side), the complexity can degrade to O(n). The space complexity is O(1) if we use an iterative approach or O(h) if we use recursion due to the function call stack.
 *
 * Edge Cases to Consider
 * If either p or q is the root itself, then the root is the LCA. If one of the nodes is a direct ancestor of the other, the ancestor is the LCA. It’s also important to assume that both nodes exist in the tree and that the tree is a valid BST for this approach to work correctly.
 *
 * Conclusion
 * The “Lowest Common Ancestor of a BST” problem is a classic example of how leveraging the properties of binary search trees can lead to elegant and efficient solutions. By applying binary search logic to the structure of the tree, we can pinpoint the LCA without visiting all nodes, resulting in a faster and more scalable algorithm.
 * */