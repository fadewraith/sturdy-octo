package tree.avl;

/**
 * AVL TREE
 * 
 * What it is:
 * A self-balancing Binary Search Tree (BST) named after its inventors (Adelson-Velsky and Landis).
 * In an AVL tree, the heights of the two child subtrees of ANY node differ by at most one.
 * If at any time they differ by more than one, rebalancing is done to restore this property.
 * 
 * Approach/Strategy:
 * Every node stores its height. During `insert` or `delete`, we update heights on the way 
 * back up the recursive call stack. We then check the `balance factor` (Height of Left - Height of Right).
 * If the balance factor is > 1 or < -1, the tree is unbalanced, and we apply one of four rotations:
 * 1. Left-Left (LL) Case   -> Right Rotation
 * 2. Right-Right (RR) Case -> Left Rotation
 * 3. Left-Right (LR) Case  -> Left Rotation on left child, then Right Rotation on root
 * 4. Right-Left (RL) Case  -> Right Rotation on right child, then Left Rotation on root
 * 
 * Time/Space Complexity:
 * Operation      | Average Time | Worst Time | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Insert         | O(log N)     | O(log N)   | O(log N) stack   | Strictly bounded unlike normal BST
 * Delete         | O(log N)     | O(log N)   | O(log N) stack   | Strictly bounded unlike normal BST
 * Search         | O(log N)     | O(log N)   | O(1) iterative   | -
 * Traversals     | O(N)         | O(N)       | O(log N) stack   | -
 * 
 * Real-world analogy:
 * Imagine stacking boxes. If one side gets too tall, it might tip over (performance degrades to O(N)).
 * An AVL tree acts like a warehouse worker who shifts boxes around every time a new one is added, 
 * ensuring the stacks remain perfectly level and structurally sound at all times.
 * 
 * Why implement this yourself?
 * AVL trees heavily test your ability to visualize and implement pointer manipulations 
 * (rotations). While Java uses Red-Black trees internally (which are slightly faster for 
 * insertions/deletions), AVL trees are strictly more balanced, making lookups slightly faster.
 */
public class AVLTree<T extends Comparable<T>> {

    private class AVLNode {
        T data;
        AVLNode left;
        AVLNode right;
        int height;

        AVLNode(T data) {
            this.data = data;
            this.height = 1; // New node is initially added at leaf (height 1)
        }
    }

    private AVLNode root;

    public AVLTree() {
        this.root = null;
    }

    // --------------------------------------------------------
    // 1. HEIGHT & BALANCE FACTOR HELPERS
    // --------------------------------------------------------

    /**
     * Safely gets the height of a node (returns 0 if node is null).
     */
    private int getHeight(AVLNode node) {
        if (node == null) return 0;
        return node.height;
    }

    /**
     * Calculates the balance factor (Left Height - Right Height).
     * > 1 means left-heavy. < -1 means right-heavy.
     */
    private int getBalanceFactor(AVLNode node) {
        if (node == null) return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    /**
     * Updates the height of a node based on its children.
     */
    private void updateHeight(AVLNode node) {
        if (node != null) {
            node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        }
    }

    // --------------------------------------------------------
    // 2. ROTATIONS
    // --------------------------------------------------------

    /**
     * Right Rotation (Solves LL Case)
     *      y                               x
     *     / \                            /   \
     *    x   T3  -- Right Rotate -->    z     y
     *   / \                            / \   / \
     *  z   T2                         T0 T1 T2 T3
     * / \
     *T0 T1
     */
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights (order matters! y is now lower than x)
        updateHeight(y);
        updateHeight(x);

        // Return new root
        return x;
    }

    /**
     * Left Rotation (Solves RR Case)
     *    x                               y
     *   / \                            /   \
     *  T0  y   -- Left Rotate -->     x     z
     *     / \                        / \   / \
     *    T1  z                      T0 T1 T2 T3
     *       / \
     *      T2 T3
     */
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T1 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T1;

        // Update heights
        updateHeight(x);
        updateHeight(y);

        // Return new root
        return y;
    }

    // --------------------------------------------------------
    // 3. INSERTION WITH REBALANCING
    // --------------------------------------------------------

    public void insert(T data) {
        root = insertRec(root, data);
    }

    private AVLNode insertRec(AVLNode node, T data) {
        // 1. Perform standard BST insert
        if (node == null) {
            return new AVLNode(data);
        }

        int cmp = data.compareTo(node.data);
        if (cmp < 0) {
            node.left = insertRec(node.left, data);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, data);
        } else {
            // Duplicate keys not allowed in standard set/map context
            return node;
        }

        // 2. Update height of this ancestor node
        updateHeight(node);

        // 3. Get the balance factor to check whether this node became unbalanced
        int balance = getBalanceFactor(node);

        // 4. If unbalanced, there are 4 cases:

        // Case 1: Left-Left (LL)
        if (balance > 1 && data.compareTo(node.left.data) < 0) {
            return rightRotate(node);
        }

        // Case 2: Right-Right (RR)
        if (balance < -1 && data.compareTo(node.right.data) > 0) {
            return leftRotate(node);
        }

        // Case 3: Left-Right (LR)
        if (balance > 1 && data.compareTo(node.left.data) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Case 4: Right-Left (RL)
        if (balance < -1 && data.compareTo(node.right.data) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node; // Unchanged node pointer
    }

    // --------------------------------------------------------
    // 4. DELETION WITH REBALANCING
    // --------------------------------------------------------

    public void delete(T data) {
        root = deleteRec(root, data);
    }

    private AVLNode deleteRec(AVLNode node, T data) {
        // 1. Perform standard BST delete
        if (node == null) return node;

        int cmp = data.compareTo(node.data);
        if (cmp < 0) {
            node.left = deleteRec(node.left, data);
        } else if (cmp > 0) {
            node.right = deleteRec(node.right, data);
        } else {
            // Node to be deleted found

            // Node with only one child or no child
            if ((node.left == null) || (node.right == null)) {
                AVLNode temp = null;
                if (temp == node.left) temp = node.right;
                else temp = node.left;

                // No child case
                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    // One child case: copy the contents of the non-empty child
                    node = temp;
                }
            } else {
                // Node with two children: Get inorder successor (smallest in right subtree)
                AVLNode temp = getMinValueNode(node.right);
                node.data = temp.data;
                // Delete the inorder successor
                node.right = deleteRec(node.right, temp.data);
            }
        }

        // If tree had only one node, return
        if (node == null) return node;

        // 2. Update height
        updateHeight(node);

        // 3. Get balance factor
        int balance = getBalanceFactor(node);

        // 4. If unbalanced, there are 4 cases:

        // Case 1: Left-Left (LL)
        if (balance > 1 && getBalanceFactor(node.left) >= 0) {
            return rightRotate(node);
        }

        // Case 2: Left-Right (LR)
        if (balance > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Case 3: Right-Right (RR)
        if (balance < -1 && getBalanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }

        // Case 4: Right-Left (RL)
        if (balance < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private AVLNode getMinValueNode(AVLNode node) {
        AVLNode curr = node;
        while (curr.left != null) {
            curr = curr.left;
        }
        return curr;
    }

    // --------------------------------------------------------
    // 5. SEARCH & TRAVERSAL
    // --------------------------------------------------------

    public boolean search(T data) {
        AVLNode curr = root;
        while (curr != null) {
            int cmp = data.compareTo(curr.data);
            if (cmp == 0) return true;
            else if (cmp < 0) curr = curr.left;
            else curr = curr.right;
        }
        return false;
    }

    public void inorder() {
        System.out.print("AVL Inorder: ");
        inorderRec(root);
        System.out.println();
    }

    private void inorderRec(AVLNode node) {
        if (node != null) {
            inorderRec(node.left);
            // Printing height and balance factor to visualize structure
            System.out.print(node.data + "(H:" + node.height + ", BF:" + getBalanceFactor(node) + ") ");
            inorderRec(node.right);
        }
    }

    public void printRoot() {
        if (root != null) {
            System.out.println("Root is: " + root.data + " (Height: " + root.height + ")");
        } else {
            System.out.println("Tree is empty.");
        }
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- AVL TREE DEMO ---");
        
        AVLTree<Integer> avl = new AVLTree<>();
        
        // Let's force a Right-Right (RR) imbalance which requires a Left Rotation
        System.out.println("\nInserting 10, 20, 30 (Forces Left Rotation at 10)");
        avl.insert(10);
        avl.insert(20);
        avl.insert(30);
        avl.inorder(); // Expected: 10, 20, 30 but perfectly balanced
        avl.printRoot(); // Expected root is 20
        
        // Let's force a Left-Left (LL) imbalance
        System.out.println("\nInserting 5, 2 (Forces Right Rotation at 10)");
        avl.insert(5);
        avl.insert(2);
        avl.inorder();
        avl.printRoot(); // Root remains 20
        
        // Let's force a Left-Right (LR) imbalance
        System.out.println("\nInserting 7 (Forces LR Rotation at 5)");
        avl.insert(7);
        avl.inorder();
        
        // Searching
        System.out.println("\nSearch 30? " + avl.search(30)); // true
        System.out.println("Search 100? " + avl.search(100)); // false
        
        // Deletions
        System.out.println("\nDeleting 20 (The Root!)...");
        avl.delete(20);
        avl.inorder();
        avl.printRoot(); // Should re-assign root and maintain perfect balance
        
        System.out.println("\nDeleting 10...");
        avl.delete(10);
        avl.inorder();
    }
}
