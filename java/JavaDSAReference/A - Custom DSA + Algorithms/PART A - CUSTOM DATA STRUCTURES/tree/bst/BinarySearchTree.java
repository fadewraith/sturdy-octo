package tree.bst;

import array.MyArrayList;
import queue.linkedlistbased.LinkedListQueue;
import stack.linkedlistbased.LinkedListStack;

/**
 * BINARY SEARCH TREE (BST) Node
 */
class TreeNode<T> {
    T data;
    TreeNode<T> left;
    TreeNode<T> right;

    TreeNode(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

/**
 * BINARY SEARCH TREE
 * 
 * What it is:
 * A tree data structure where each node has at most two children. 
 * Property: For any node, all elements in its left subtree are SMALLER, 
 * and all elements in its right subtree are LARGER.
 * 
 * Approach/Strategy:
 * Recursion is the most natural way to traverse and modify a BST because every 
 * subtree is itself a BST. We also provide iterative traversals using explicit 
 * Stacks/Queues (which we import from our earlier implementations!) to demonstrate 
 * understanding of the call stack.
 * 
 * Time/Space Complexity:
 * Operation      | Average Time | Worst Time | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Insert         | O(log N)     | O(N)       | O(log N) stack   | Worst case is a linked list (skewed)
 * Delete         | O(log N)     | O(N)       | O(log N) stack   | 3 cases (0, 1, or 2 children)
 * Search         | O(log N)     | O(N)       | O(1) iterative   | -
 * Traversals     | O(N)         | O(N)       | O(H) stack       | H is height of tree
 * 
 * Real-world analogy:
 * A library catalog where you split the search space in half each time. If you want 
 * a book starting with 'M', and the current book is 'G', you know you must go to the right.
 * 
 * Why implement this yourself?
 * Understanding BST properties, especially the deletion cases and iterative traversals, 
 * is fundamental for tackling complex tree interview questions. Java uses Red-Black trees 
 * (a balanced BST) for its TreeSet/TreeMap.
 */
public class BinarySearchTree<T extends Comparable<T>> {

    private TreeNode<T> root;

    public BinarySearchTree() {
        this.root = null;
    }

    // --------------------------------------------------------
    // 1. INSERTION
    // --------------------------------------------------------

    public void insert(T data) {
        root = insertRec(root, data);
    }

    private TreeNode<T> insertRec(TreeNode<T> node, T data) {
        if (node == null) {
            return new TreeNode<>(data);
        }
        int cmp = data.compareTo(node.data);
        if (cmp < 0) {
            node.left = insertRec(node.left, data);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, data);
        }
        // If cmp == 0, duplicate. We ignore duplicates in this standard BST.
        return node;
    }

    // --------------------------------------------------------
    // 2. SEARCH
    // --------------------------------------------------------

    public boolean search(T data) {
        return get(data) != null;
    }

    public T get(T data) {
        TreeNode<T> curr = root;
        while (curr != null) {
            int cmp = data.compareTo(curr.data);
            if (cmp == 0) return curr.data;
            else if (cmp < 0) curr = curr.left;
            else curr = curr.right;
        }
        return null;
    }

    // --------------------------------------------------------
    // 3. DELETION (The 3 Cases)
    // --------------------------------------------------------

    public void delete(T data) {
        root = deleteRec(root, data);
    }

    private TreeNode<T> deleteRec(TreeNode<T> node, T data) {
        if (node == null) return null;

        int cmp = data.compareTo(node.data);
        if (cmp < 0) {
            node.left = deleteRec(node.left, data);
        } else if (cmp > 0) {
            node.right = deleteRec(node.right, data);
        } else {
            // Node found! Handle the 3 cases:

            // Case 1 & 2: No child or 1 child
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // Case 3: 2 children
            // Strategy: Find the inorder successor (smallest in the right subtree)
            // Replace current node's data with successor's data, then delete successor.
            node.data = findMinRec(node.right);
            node.right = deleteRec(node.right, node.data);
        }
        return node;
    }

    // --------------------------------------------------------
    // 4. UTILITIES (Min, Max, Height, Balance, Valid)
    // --------------------------------------------------------

    public T findMin() {
        if (root == null) throw new IllegalStateException("Tree is empty");
        return findMinRec(root);
    }
    private T findMinRec(TreeNode<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node.data;
    }

    public T findMax() {
        if (root == null) throw new IllegalStateException("Tree is empty");
        TreeNode<T> node = root;
        while (node.right != null) {
            node = node.right;
        }
        return node.data;
    }

    public int height() {
        return heightRec(root);
    }
    private int heightRec(TreeNode<T> node) {
        if (node == null) return -1; // -1 so a single node tree has height 0
        return 1 + Math.max(heightRec(node.left), heightRec(node.right));
    }

    public boolean checkIfBalanced() {
        return checkBalanceRec(root) != -1;
    }
    private int checkBalanceRec(TreeNode<T> node) {
        if (node == null) return 0;
        
        int leftH = checkBalanceRec(node.left);
        if (leftH == -1) return -1;
        
        int rightH = checkBalanceRec(node.right);
        if (rightH == -1) return -1;
        
        if (Math.abs(leftH - rightH) > 1) return -1; // Unbalanced
        
        return 1 + Math.max(leftH, rightH);
    }

    public boolean checkIfValidBST() {
        return isValidBSTRec(root, null, null);
    }
    private boolean isValidBSTRec(TreeNode<T> node, T min, T max) {
        if (node == null) return true;
        if (min != null && node.data.compareTo(min) <= 0) return false;
        if (max != null && node.data.compareTo(max) >= 0) return false;
        return isValidBSTRec(node.left, min, node.data) && isValidBSTRec(node.right, node.data, max);
    }

    // --------------------------------------------------------
    // 5. TRAVERSALS (Recursive & Iterative)
    // --------------------------------------------------------

    public void inorderRecursive() {
        System.out.print("Inorder (Rec): ");
        inorderRecHelper(root);
        System.out.println();
    }
    private void inorderRecHelper(TreeNode<T> node) {
        if (node != null) {
            inorderRecHelper(node.left);
            System.out.print(node.data + " ");
            inorderRecHelper(node.right);
        }
    }

    public void inorderIterative() {
        System.out.print("Inorder (Iter): ");
        LinkedListStack<TreeNode<T>> stack = new LinkedListStack<>();
        TreeNode<T> curr = root;

        while (curr != null || !stack.isEmpty()) {
            // Drill down left
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            // Process node
            curr = stack.pop();
            System.out.print(curr.data + " ");
            // Move right
            curr = curr.right;
        }
        System.out.println();
    }

    public void preorderRecursive() {
        System.out.print("Preorder (Rec): ");
        preorderRecHelper(root);
        System.out.println();
    }
    private void preorderRecHelper(TreeNode<T> node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preorderRecHelper(node.left);
            preorderRecHelper(node.right);
        }
    }

    public void preorderIterative() {
        System.out.print("Preorder (Iter): ");
        if (root == null) {
            System.out.println();
            return;
        }
        LinkedListStack<TreeNode<T>> stack = new LinkedListStack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode<T> curr = stack.pop();
            System.out.print(curr.data + " ");
            
            // Push right child FIRST so left child is popped first
            if (curr.right != null) stack.push(curr.right);
            if (curr.left != null) stack.push(curr.left);
        }
        System.out.println();
    }

    public void postorderRecursive() {
        System.out.print("Postorder (Rec): ");
        postorderRecHelper(root);
        System.out.println();
    }
    private void postorderRecHelper(TreeNode<T> node) {
        if (node != null) {
            postorderRecHelper(node.left);
            postorderRecHelper(node.right);
            System.out.print(node.data + " ");
        }
    }

    public void postorderIterative() {
        System.out.print("Postorder (Iter): ");
        if (root == null) {
            System.out.println();
            return;
        }
        LinkedListStack<TreeNode<T>> stack1 = new LinkedListStack<>();
        LinkedListStack<TreeNode<T>> stack2 = new LinkedListStack<>();
        stack1.push(root);

        // Process like Preorder but (Root, Right, Left) and push to stack2
        while (!stack1.isEmpty()) {
            TreeNode<T> curr = stack1.pop();
            stack2.push(curr);
            
            if (curr.left != null) stack1.push(curr.left);
            if (curr.right != null) stack1.push(curr.right);
        }
        
        // Print stack2 to reverse it to (Left, Right, Root)
        while (!stack2.isEmpty()) {
            System.out.print(stack2.pop().data + " ");
        }
        System.out.println();
    }

    public void levelOrderBFS() {
        System.out.print("Level Order (BFS): ");
        if (root == null) {
            System.out.println();
            return;
        }
        LinkedListQueue<TreeNode<T>> queue = new LinkedListQueue<>();
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            TreeNode<T> curr = queue.dequeue();
            System.out.print(curr.data + " ");
            if (curr.left != null) queue.enqueue(curr.left);
            if (curr.right != null) queue.enqueue(curr.right);
        }
        System.out.println();
    }
    
    /**
     * Helper to return all elements in sorted (inorder) order as a list.
     * Required by TreeSet.
     */
    public MyArrayList<T> toSortedList() {
        MyArrayList<T> list = new MyArrayList<>();
        LinkedListStack<TreeNode<T>> stack = new LinkedListStack<>();
        TreeNode<T> curr = root;

        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            list.add(curr.data);
            curr = curr.right;
        }
        return list;
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- BINARY SEARCH TREE DEMO ---");
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        
        int[] elements = {50, 30, 20, 40, 70, 60, 80};
        for (int el : elements) {
            bst.insert(el);
        }
        
        System.out.println("Valid BST? " + bst.checkIfValidBST()); // true
        System.out.println("Balanced? " + bst.checkIfBalanced()); // true
        System.out.println("Height: " + bst.height()); // 2
        System.out.println("Min: " + bst.findMin() + " | Max: " + bst.findMax()); // 20 | 80
        
        // Traversals
        bst.inorderRecursive();
        bst.inorderIterative();
        
        bst.preorderRecursive();
        bst.preorderIterative();
        
        bst.postorderRecursive();
        bst.postorderIterative();
        
        bst.levelOrderBFS();
        
        // Deletions
        System.out.println("\nDeleting 20 (leaf)...");
        bst.delete(20);
        bst.inorderRecursive(); // 30 40 50 60 70 80
        
        System.out.println("Deleting 30 (1 child - 40)...");
        bst.delete(30);
        bst.inorderRecursive(); // 40 50 60 70 80
        
        System.out.println("Deleting 50 (2 children - 40 and 70)...");
        bst.delete(50);
        bst.inorderRecursive(); // 40 60 70 80
    }
}
