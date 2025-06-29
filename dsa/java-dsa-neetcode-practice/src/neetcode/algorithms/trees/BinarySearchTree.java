package neetcode.algorithms.trees;

public class BinarySearchTree {

    /**
     * implementation of Binary Search Tree
     * Sets -> Ordered & TreeSet
     * Maps -> Ordered & TreeMap
     * */

    public int val;
    public BinarySearchTree left = null;
    public BinarySearchTree right = null;

    public BinarySearchTree(int val) {
        this.val = val;
    }

    public static boolean search (BinarySearchTree root, int target) {
        if (root == null) return false;

        if (target > root.val) return search(root.right, target);
        else if (target < root.val) return search (root.left, target);
        return true;
    }

    public static BinarySearchTree insert (BinarySearchTree root, int val) {
        if (root == null) return new BinarySearchTree(val);

        if (val > root.val) root.right = insert(root.right, val);
        else if (val < root.val) root.left = insert(root.left, val);
        return root;
    }

    public static BinarySearchTree minValueNode (BinarySearchTree root) {
        BinarySearchTree current = root;
        while (current != null && current.left != null) {
            current = current.left;
        }
        return current;
    }

    public static BinarySearchTree maxValueNode (BinarySearchTree root) {
        BinarySearchTree current = root;
        while (current != null && current.right != null) {
            current = current.right;
        }
        return current;
    }

    /**
     * Case 1 - 0 or 1 child
     * Case 2 - 2 children
     * */
    public static BinarySearchTree remove (BinarySearchTree root, int val) {
        if (root == null) return null;

        if (val > root.val) root.right = remove(root.right, val);
        else if (val < root.val) root.left = remove(root.left, val);
        else {
            // both if & else - covers Case 1
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;
            else { // Case 2
                BinarySearchTree minNode = minValueNode(root.right);
                root.val = minNode.val;
                root.right = remove(root.right, minNode.val);
            }
        }
        return root;
    }

    public static void printTree(BinarySearchTree root, String prefix, boolean isLeft) {
        if (root != null) {
            System.out.println(prefix + (isLeft ? "├── " : "└── ") + root.val);
            printTree(root.left, prefix + (isLeft ? "│   " : "    "), true);
            printTree(root.right, prefix + (isLeft ? "│   " : "    "), false);
        }
    }

    public static void printTree(BinarySearchTree root, int level) {
        if (root != null) {
            printTree(root.right, level + 1);
            if (level != 0) {
                for (int i = 0; i < level - 1; i++)
                    System.out.print("|\t");
                System.out.println("|-------" + root.val);
            } else
                System.out.println(root.val);
            printTree(root.left, level + 1);
        }
    }

    /**
     * Inorder, preorder, and postorder traversals all fall under Depth-First Search (DFS) type. This is because they explore one branch of a tree as deeply as possible before backtracking and proceeding to another branch.
     *
     * It gives a sorted sequence of elements if applied to a BST.
     * In-order traversal: left -> root -> right
     *
     * It processes the root first, which is useful when you need to work on the "hierarchical structure" of the tree
     * Pre-order traversal: root -> left -> right
     *
     * Often used for deleting trees (since child nodes are processed first).
     * Post-order traversal: left -> right -> root
     * */
    public static void inOrderTraversal(BinarySearchTree root) {
        if (root != null) {
            inOrderTraversal(root.left);
            System.out.print(root.val + " ");
            inOrderTraversal(root.right);
        }
    }

    public static void preOrderTraversal(BinarySearchTree root) {
        if (root != null) {
            System.out.print(root.val + " ");
            preOrderTraversal(root.left);
            preOrderTraversal(root.right);
        }
    }

    public static void postOrderTraversal(BinarySearchTree root) {
        if (root != null) {
            postOrderTraversal(root.left);
            postOrderTraversal(root.right);
            System.out.print(root.val + " ");
        }
    }

    public static void main(String[] args) {
        BinarySearchTree root = new BinarySearchTree(10);
        insert(root, 5);
        insert(root, 15);
        insert(root, 3);
        insert(root, 7);
        insert(root, 12);
        insert(root, 18);
        insert(root, 1);
        insert(root, 4);
        insert(root, 6);
        insert(root, 8);
        insert(root, 11);
        insert(root, 13);
        insert(root, 17);
        insert(root, 20);

        System.out.println("BST structure:");
//        printTree(root, "", true);
        printTree(root, 0);
//        remove(root, 10);
//        System.out.println("BST structure after removing 10:");
//        printTree(root, 0);
        System.out.println("In-order traversal:");
        inOrderTraversal(root);
        System.out.println();
        System.out.println("Pre-order traversal:");
        preOrderTraversal(root);
        System.out.println();
        System.out.println("Post-order traversal:");
        postOrderTraversal(root);
        System.out.println();
    }

}
