package tree.btree;

import array.MyArrayList;

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
 * Delete         | O(t * log_t N)  | O(log_t N) stack | Borrow / merge on the way down
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
    private int size; // number of keys, maintained by insert/delete
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

        // ----------------------------------------------------
        // DELETE (CLRS). Mirror image of insert: insert splits FULL nodes on the way
        // down; delete makes sure every node we step into has at least t keys, so
        // removing one key from it can never break the "t - 1 minimum" rule.
        // ----------------------------------------------------

        // Index of the first key >= k
        private int findKey(T k) {
            int idx = 0;
            while (idx < n && keys[idx].compareTo(k) < 0) idx++;
            return idx;
        }

        /** Removes k from the subtree rooted here. Returns true if k was found. */
        public boolean remove(T k) {
            int idx = findKey(k);

            if (idx < n && keys[idx].compareTo(k) == 0) {
                if (leaf) {
                    removeFromLeaf(idx);           // Case 1: key in a leaf - just delete it
                } else {
                    removeFromNonLeaf(idx);        // Case 2: key in an internal node
                }
                return true;
            }

            if (leaf) return false; // not in the tree

            // Case 3: key is somewhere in children[idx]'s subtree.
            boolean wasLastChild = (idx == n);
            if (children[idx].n < t) fill(idx); // make sure the child has >= t keys first
            // fill() may have merged the last child into its left sibling
            if (wasLastChild && idx > n) return children[idx - 1].remove(k);
            return children[idx].remove(k);
        }

        private void removeFromLeaf(int idx) {
            for (int i = idx + 1; i < n; i++) keys[i - 1] = keys[i];
            keys[n - 1] = null;
            n--;
        }

        private void removeFromNonLeaf(int idx) {
            T k = keys[idx];
            if (children[idx].n >= t) {
                // 2a: left child is big enough - replace k with its predecessor, delete that
                T pred = getPredecessor(idx);
                keys[idx] = pred;
                children[idx].remove(pred);
            } else if (children[idx + 1].n >= t) {
                // 2b: right child is big enough - replace k with its successor, delete that
                T succ = getSuccessor(idx);
                keys[idx] = succ;
                children[idx + 1].remove(succ);
            } else {
                // 2c: both children have t - 1 keys - merge them (k moves down) and recurse
                merge(idx);
                children[idx].remove(k);
            }
        }

        private T getPredecessor(int idx) {
            BTreeNode<T> cur = children[idx];
            while (!cur.leaf) cur = cur.children[cur.n];
            return cur.keys[cur.n - 1];
        }

        private T getSuccessor(int idx) {
            BTreeNode<T> cur = children[idx + 1];
            while (!cur.leaf) cur = cur.children[0];
            return cur.keys[0];
        }

        // children[idx] has only t - 1 keys: borrow from a sibling, or merge with one
        private void fill(int idx) {
            if (idx != 0 && children[idx - 1].n >= t) {
                borrowFromPrev(idx);
            } else if (idx != n && children[idx + 1].n >= t) {
                borrowFromNext(idx);
            } else if (idx != n) {
                merge(idx);
            } else {
                merge(idx - 1);
            }
        }

        // Rotate right: parent key comes down into child, left sibling's last key goes up
        private void borrowFromPrev(int idx) {
            BTreeNode<T> child = children[idx], sibling = children[idx - 1];
            for (int i = child.n - 1; i >= 0; i--) child.keys[i + 1] = child.keys[i];
            if (!child.leaf) {
                for (int i = child.n; i >= 0; i--) child.children[i + 1] = child.children[i];
                child.children[0] = sibling.children[sibling.n];
                sibling.children[sibling.n] = null;
            }
            child.keys[0] = keys[idx - 1];
            keys[idx - 1] = sibling.keys[sibling.n - 1];
            sibling.keys[sibling.n - 1] = null;
            child.n++;
            sibling.n--;
        }

        // Rotate left: parent key comes down into child, right sibling's first key goes up
        private void borrowFromNext(int idx) {
            BTreeNode<T> child = children[idx], sibling = children[idx + 1];
            child.keys[child.n] = keys[idx];
            if (!child.leaf) child.children[child.n + 1] = sibling.children[0];
            keys[idx] = sibling.keys[0];
            for (int i = 1; i < sibling.n; i++) sibling.keys[i - 1] = sibling.keys[i];
            if (!sibling.leaf) {
                for (int i = 1; i <= sibling.n; i++) sibling.children[i - 1] = sibling.children[i];
                sibling.children[sibling.n] = null;
            }
            sibling.keys[sibling.n - 1] = null;
            child.n++;
            sibling.n--;
        }

        // Merge children[idx + 1] and keys[idx] into children[idx] (result has 2t - 1 keys)
        private void merge(int idx) {
            BTreeNode<T> child = children[idx], sibling = children[idx + 1];
            child.keys[t - 1] = keys[idx];
            for (int i = 0; i < sibling.n; i++) child.keys[i + t] = sibling.keys[i];
            if (!child.leaf) {
                for (int i = 0; i <= sibling.n; i++) child.children[i + t] = sibling.children[i];
            }
            for (int i = idx + 1; i < n; i++) keys[i - 1] = keys[i];
            for (int i = idx + 2; i <= n; i++) children[i - 1] = children[i];
            keys[n - 1] = null;
            children[n] = null;
            child.n += sibling.n + 1;
            n--;
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
        size++; // this B-Tree keeps duplicates, so every insert adds a key
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

    /**
     * Deletes a key from the B-Tree. Returns true if it was present.
     */
    public boolean delete(T k) {
        if (root == null) return false;
        boolean removed = root.remove(k);
        if (removed) size--;
        // If the root lost its last key, the tree shrinks by one level
        if (root.n == 0) {
            root = root.leaf ? null : root.children[0];
        }
        return removed;
    }

    // --------------------------------------------------------
    // QUERY HELPERS (return values instead of printing)
    // --------------------------------------------------------

    /** Number of keys (duplicates counted). O(1): a counter kept up to date by insert and delete. */
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return root == null;
    }

    /** All keys in ascending order (child 0, key 0, child 1, key 1, ..., last child). */
    public MyArrayList<T> toSortedList() {
        MyArrayList<T> out = new MyArrayList<>();
        collectRec(root, out);
        return out;
    }

    private void collectRec(BTreeNode<T> node, MyArrayList<T> out) {
        if (node == null) return;
        for (int i = 0; i < node.n; i++) {
            if (!node.leaf) collectRec(node.children[i], out);
            out.add(node.keys[i]);
        }
        if (!node.leaf) collectRec(node.children[node.n], out);
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

        System.out.println("\nDeleting 6 (leaf), 13 (internal), 7, 4, 2 (absent):");
        for (int k : new int[]{6, 13, 7, 4, 2}) {
            System.out.println("  delete(" + k + ") -> " + bTree.delete(k));
        }
        System.out.print("Sorted Traversal after deletes: ");
        bTree.traverse(); // 1 3 5 8 9 10 11 12 14 15 16 17 20 30
    }
}
