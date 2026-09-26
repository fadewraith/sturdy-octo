package practiceQuestions.algomapio.trees.medium;

import practiceQuestions.algomapio.trees.TreeNode;

public class KthSmallestElementInABST {
    /**
     * Step-by-Step Thought Process
     * Understand the problem: Find the kth smallest element in a binary search tree (BST), where an in-order traversal yields nodes in ascending order.
     * Initialize a list count with the value k to track the number of nodes left to visit.
     * Initialize a list ans with a single element 0 to store the kth smallest value.
     * Define a recursive DFS function that performs an in-order traversal (left, root, right).
     * If the node is None, return to skip null nodes.
     * Recursively traverse the left subtree.
     * If count[0] equals 1, set ans[0] to the current node’s value, as this is the kth smallest node.
     * Decrement count[0] to indicate a node has been visited.
     * If count[0] is still positive, recursively traverse the right subtree.
     * Call DFS on the root and return ans[0] as the kth smallest element.
     * */

    private int count;
    private int ans;

    public int kthSmallest(TreeNode root, int k) {
        count = k;
        ans = 0;
        dfs(root);
        return ans;
    }

    private void dfs(TreeNode node) {
        if (node == null) {
            return;
        }

        dfs(node.left);

        if (count == 1) {
            ans = node.val;
        }

        count--;
        if (count > 0) {
            dfs(node.right);
        }
    }
}

/**
 * Detailed Explanation
 * Understanding the Problem: Kth Smallest Element in a BST
 * The “Kth Smallest Element in a BST” problem focuses on finding the element that would appear in the kth position if the elements of a Binary Search Tree (BST) were sorted in ascending order. Thanks to the properties of BSTs, where the left subtree contains only nodes with values less than the root and the right subtree contains only nodes with values greater than the root, an in-order traversal of the tree naturally visits the nodes in sorted order.
 *
 * For example, in a BST where an in-order traversal yields the sequence [1, 2, 3, 4, 5], the 3rd smallest element would be 3. This insight allows us to solve the problem efficiently using recursive traversal without needing to build or sort a separate array.
 *
 * Approach: In-Order Traversal with Counting
 * The optimal strategy is to use a recursive depth-first search (DFS) with in-order traversal. During traversal, we maintain a counter that tracks how many nodes we've visited. Starting from the leftmost (smallest) node, we decrement the counter as we visit each node. Once the counter reaches 1, it means the current node is the kth smallest, and we store its value as our result.
 *
 * The traversal continues only as long as necessary, terminating early once the kth element is found. This saves time, especially in larger trees where we might otherwise traverse more nodes than needed.
 *
 * Time and Space Complexity
 * The time complexity of this algorithm is O(h + k), where h is the height of the tree. In the worst case (an unbalanced tree), h can be O(n), but in a balanced tree, it is O(log n). The algorithm visits only k nodes in the best case, making it highly efficient when k is small. The space complexity is O(h) due to the recursion stack used during the traversal.
 *
 * Edge Cases to Consider
 * The function must gracefully handle trees with only one node (where k = 1). It’s also important to ensure k is valid and does not exceed the number of nodes in the tree. If the tree is skewed, the in-order traversal must still proceed correctly down the long branch, whether left-heavy or right-heavy.
 *
 * Conclusion
 * The “Kth Smallest Element in a BST” problem is a classic use case for in-order traversal in binary search trees. It highlights how BST properties enable efficient searching and ordering operations. By combining traversal with a counter and terminating early, we achieve both clarity and performance, making this technique widely applicable in other BST-based problems.
 * */