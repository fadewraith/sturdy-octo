package tree.btree;

/**
 * B-TREE
 * 
 * What it is:
 * A self-balancing search tree in which nodes can have more than two children. 
 * It is specifically designed to work well on magnetic disks or other direct-access 
 * secondary storage devices, and is the underlying data structure for most relational 
 * database indexes (like PostgreSQL, MySQL) and file systems.
 * 
 * Approach/Strategy:
 * A B-Tree is defined by a minimum degree `t`. 
 * - Every node (except root) must contain at least `t-1` keys and at most `2t-1` keys.
 * - Every internal node has number of children = number of keys + 1.
 * - All leaves appear on the exact same level (perfectly balanced).
 * - Insert: We proactively split full nodes (nodes with `2t-1` keys) on our way down 
 *   the tree from the root to the leaf. This guarantees that when we reach the leaf, 
 *   there is room to insert the new key without violating the B-Tree property, 
 *   avoiding the need to traverse back up.
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Insert         | O(t * log_t N)  | O(log_t N) stack | Very flat tree = few disk accesses
 * Search         | O(t * log_t N)  | O(1) iterative   | Binary search inside the node is possible
 * 
 * Real-world analogy:
 * Imagine a filing system where opening a drawer (loading a disk block) is very slow, 
 * but once the drawer is open, looking at the folders inside it is extremely fast. 
 * A B-Tree makes the drawers huge (lots of keys per node) so the tree is very shallow, 
 * drastically reducing the number of drawers you have to open to find what you need.
 */
public class BTree<T extends Comparable<T>> {

    private BTreeNode<T> root;
    private final int t; // Minimum degree

    /**
     * Internal B-Tree Node
     */
    private static class BTreeNode<T extends Comparable<T>> {
        T[] keys;
        BTreeNode<T>[] children;
        int n; // Current number of keys
        boolean leaf;
        int t; // Minimum degree

        @SuppressWarnings("unchecked")
        public BTreeNode(int t, boolean leaf) {
            this.t = t;
            this.leaf = leaf;
            
            // Maximum number of keys is 2t - 1
            this.keys = (T[]) new Comparable[2 * t - 1];
            
            // Maximum number of children is 2t
            this.children = (BTreeNode<T>[]) new BTreeNode[2 * t];
            this.n = 0;
        }

        /**
         * Traverses the subtree rooted at this node
         */
        public void traverse() {
            int i;
            for (i = 0; i < this.n; i++) {
                // If not a leaf, traverse the child before printing the key
                if (!this.leaf) {
                    this.children[i].traverse();
                }
                System.out.print(this.keys[i] + " ");
            }
            
            // Traverse the final child
            if (!this.leaf) {
                this.children[i].traverse();
            }
        }

        /**
         * Searches for a key in the subtree rooted at this node.
         * Returns the node containing the key, or null.
         */
        public BTreeNode<T> search(T k) {
            int i = 0;
            // Find the first key greater than or equal to k
            while (i < n && k.compareTo(keys[i]) > 0) {
                i++;
            }

            // If found at this node
            if (i < n && keys[i].compareTo(k) == 0) {
                return this;
            }

            // If key is not found and this is a leaf node
            if (leaf) {
                return null;
            }

            // Go to the appropriate child
            return children[i].search(k);
        }

        /**
         * Inserts a new key into this node (assuming this node is NOT full).
         */
        public void insertNonFull(T k) {
            int i = n - 1;

            if (leaf) {
                // Shift keys to the right to make room
                while (i >= 0 && keys[i].compareTo(k) > 0) {
                    keys[i + 1] = keys[i];
                    i--;
                }
                // Insert the new key
                keys[i + 1] = k;
                n = n + 1;
            } else {
                // Find the child which is going to have the new key
                while (i >= 0 && keys[i].compareTo(k) > 0) {
                    i--;
                }
                i++; // The child index is i+1

                // Check if the child is full
                if (children[i].n == 2 * t - 1) {
                    // Split the child
                    splitChild(i, children[i]);

                    // After split, the middle key of children[i] goes up and 
                    // children[i] is split into two. See which of the two 
                    // is going to have the new key.
                    if (keys[i].compareTo(k) < 0) {
                        i++;
                    }
                }
                children[i].insertNonFull(k);
            }
        }

        /**
         * Splits a full child `y` of this node. `i` is the index of `y` in the child array.
         * This node must NOT be full when this is called.
         */
        public void splitChild(int i, BTreeNode<T> y) {
            // Create a new node which is going to store (t-1) keys of y
            BTreeNode<T> z = new BTreeNode<>(y.t, y.leaf);
            z.n = t - 1;

            // Copy the last (t-1) keys of y to z
            for (int j = 0; j < t - 1; j++) {
                z.keys[j] = y.keys[j + t];
            }

            // Copy the last t children of y to z (if y is not a leaf)
            if (!y.leaf) {
                for (int j = 0; j < t; j++) {
                    z.children[j] = y.children[j + t];
                }
            }

            // Reduce the number of keys in y
            y.n = t - 1;

            // Create space for new child z in this node
            for (int j = n; j >= i + 1; j--) {
                children[j + 1] = children[j];
            }
            children[i + 1] = z;

            // Create space for the middle key of y in this node
            for (int j = n - 1; j >= i; j--) {
                keys[j + 1] = keys[j];
            }
            
            // Move the middle key of y up to this node
            keys[i] = y.keys[t - 1];
            n = n + 1;
        }
    }

    /**
     * Constructor for B-Tree with minimum degree t.
     */
    public BTree(int t) {
        if (t < 2) throw new IllegalArgumentException("Minimum degree must be at least 2");
        this.t = t;
        this.root = null;
    }

    /**
     * Traverses the B-Tree in sorted order.
     */
    public void traverse() {
        if (root != null) {
            root.traverse();
            System.out.println();
        } else {
            System.out.println("Tree is empty.");
        }
    }

    /**
     * Searches for a key in the B-Tree.
     */
    public boolean search(T k) {
        if (root == null) return false;
        return root.search(k) != null;
    }

    /**
     * Inserts a key into the B-Tree.
     */
    public void insert(T k) {
        // If tree is empty
        if (root == null) {
            root = new BTreeNode<>(t, true);
            root.keys[0] = k;
            root.n = 1;
        } else {
            // If root is full, then tree grows in height
            if (root.n == 2 * t - 1) {
                // Allocate memory for new root
                BTreeNode<T> s = new BTreeNode<>(t, false);

                // Make old root a child of new root
                s.children[0] = root;

                // Split the old root and move 1 key up to the new root
                s.splitChild(0, root);

                // Decide which of the two children is going to have the new key
                int i = 0;
                if (s.keys[0].compareTo(k) < 0) {
                    i++;
                }
                s.children[i].insertNonFull(k);

                // Change root
                root = s;
            } else {
                // If root is not full, just insert it
                root.insertNonFull(k);
            }
        }
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- B-TREE DEMO ---");
        
        // A B-Tree with minimum degree 3. 
        // This means nodes can have up to 5 keys and 6 children.
        BTree<Integer> bTree = new BTree<>(3);
        
        System.out.println("Inserting elements: 10, 20, 5, 6, 12, 30, 7, 17");
        int[] elements = {10, 20, 5, 6, 12, 30, 7, 17};
        for (int e : elements) {
            bTree.insert(e);
        }
        
        System.out.print("Sorted Traversal: ");
        bTree.traverse(); // Expected: 5 6 7 10 12 17 20 30
        
        System.out.println("\nSearch 6: " + bTree.search(6)); // true
        System.out.println("Search 15: " + bTree.search(15)); // false
        
        System.out.println("\nInserting more to force node splitting: 1, 3, 4, 8, 9, 11, 13, 14, 15, 16");
        int[] moreElements = {1, 3, 4, 8, 9, 11, 13, 14, 15, 16};
        for (int e : moreElements) {
            bTree.insert(e);
        }
        
        System.out.print("Sorted Traversal after splits: ");
        bTree.traverse();
        // The tree should have grown and safely split nodes while maintaining perfectly flat leaf levels!
    }
}
