package treap;

import java.util.Random;

/**
 * TREAP (Tree + Heap)
 * 
 * What it is:
 * A randomized Binary Search Tree. Every node contains TWO values:
 * 1. A Key (which follows standard BST rules: left < parent < right)
 * 2. A Priority (a random number, which follows Max-Heap rules: parent > children)
 * 
 * Approach/Strategy:
 * By assigning a completely random priority to every element as it is inserted, 
 * the tree mathematically guarantees that it will remain probabilistically balanced 
 * (O(log N) height) regardless of the order in which keys are inserted! 
 * This prevents the worst-case O(N) linked-list degradation of a standard BST 
 * without the incredibly complex code of an AVL or Red-Black Tree.
 * 
 * - Insert: We insert the key just like a normal BST. We assign it a random priority. 
 *   Then, while returning up the recursion stack, if the new child's priority is 
 *   higher than its parent's priority, we apply a single rotation (Left or Right) 
 *   to push the parent down and bring the child up, restoring the Max-Heap property.
 * - Delete: We find the node. If it's a leaf, we just delete it. If it has children, 
 *   we look at the children's priorities. We rotate the child with the HIGHER priority 
 *   up (pushing the node we want to delete down). We repeat this until the node 
 *   becomes a leaf, then simply delete it.
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity (Avg) | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Search         | O(log N)              | O(1)             | BST search
 * Insert         | O(log N)              | O(log N) stack   | Random priority + rotations
 * Delete         | O(log N)              | O(log N) stack   | Rotate down to leaf
 * 
 * Real-world analogy:
 * Imagine a company hierarchy. The horizontal departments are organized by Name 
 * (Key: BST). But the vertical promotions are based on a lottery (Priority: Heap). 
 * Even if you hire people in alphabetical order, the random promotions ensure 
 * the company structure stays bushy and balanced rather than one single straight line.
 */
public class Treap<T extends Comparable<T>> {

    private static class TreapNode<T> {
        T key;
        int priority;
        TreapNode<T> left;
        TreapNode<T> right;

        public TreapNode(T key) {
            this.key = key;
            // Generate a random priority
            this.priority = new Random().nextInt(1000000);
        }
    }

    private TreapNode<T> root;

    public Treap() {
        this.root = null;
    }

    // --------------------------------------------------------
    // ROTATIONS (The exact same as AVL, just used differently)
    // --------------------------------------------------------

    private TreapNode<T> rightRotate(TreapNode<T> y) {
        TreapNode<T> x = y.left;
        TreapNode<T> T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        return x;
    }

    private TreapNode<T> leftRotate(TreapNode<T> x) {
        TreapNode<T> y = x.right;
        TreapNode<T> T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        return y;
    }

    // --------------------------------------------------------
    // INSERTION
    // --------------------------------------------------------

    public void insert(T key) {
        root = insertRec(root, key);
    }

    private TreapNode<T> insertRec(TreapNode<T> node, T key) {
        // 1. Standard BST insertion
        if (node == null) {
            return new TreapNode<>(key);
        }

        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            node.left = insertRec(node.left, key);
            // 2. Fix Heap property (if child priority > parent priority)
            if (node.left.priority > node.priority) {
                node = rightRotate(node);
            }
        } else if (cmp > 0) {
            node.right = insertRec(node.right, key);
            // 2. Fix Heap property
            if (node.right.priority > node.priority) {
                node = leftRotate(node);
            }
        }
        // cmp == 0 means duplicate key, typically ignored in standard sets

        return node;
    }

    // --------------------------------------------------------
    // DELETION
    // --------------------------------------------------------

    public void delete(T key) {
        root = deleteRec(root, key);
    }

    private TreapNode<T> deleteRec(TreapNode<T> node, T key) {
        if (node == null) {
            return null; // Key not found
        }

        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            node.left = deleteRec(node.left, key);
        } else if (cmp > 0) {
            node.right = deleteRec(node.right, key);
        } else {
            // Node found!

            // Case 1: Leaf node (No children)
            if (node.left == null && node.right == null) {
                return null;
            }
            
            // Case 2: One child
            else if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            
            // Case 3: Two children
            // We rotate the child with the HIGHER priority up, which pushes the current node down.
            // We repeat this until the current node becomes a leaf or has one child, 
            // at which point it is safely deleted.
            else {
                if (node.left.priority > node.right.priority) {
                    node = rightRotate(node);
                    // Now the node we want to delete is on the right
                    node.right = deleteRec(node.right, key);
                } else {
                    node = leftRotate(node);
                    // Now the node we want to delete is on the left
                    node.left = deleteRec(node.left, key);
                }
            }
        }
        return node;
    }

    // --------------------------------------------------------
    // SEARCH & TRAVERSAL
    // --------------------------------------------------------

    public boolean search(T key) {
        TreapNode<T> current = root;
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp == 0) return true;
            else if (cmp < 0) current = current.left;
            else current = current.right;
        }
        return false;
    }

    public void inorder() {
        System.out.print("Treap Inorder: ");
        inorderRec(root);
        System.out.println();
    }

    private void inorderRec(TreapNode<T> node) {
        if (node != null) {
            inorderRec(node.left);
            System.out.print(node.key + "(p:" + node.priority + ") ");
            inorderRec(node.right);
        }
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- TREAP (TREE + HEAP) DEMO ---");
        
        Treap<Integer> treap = new Treap<>();
        
        // Even though we insert sorted data (which would break a normal BST into a linked list),
        // the random priorities will force rotations and keep the Treap balanced!
        int[] sortedData = {10, 20, 30, 40, 50, 60, 70};
        System.out.println("Inserting perfectly sorted data: 10 to 70");
        
        for (int i : sortedData) {
            treap.insert(i);
        }
        
        // Notice the priorities printed in the inorder traversal. 
        // Because of the rotations, the root will be the one that randomly got the highest priority.
        treap.inorder();
        
        System.out.println("\nSearch 40: " + treap.search(40)); // true
        System.out.println("Search 100: " + treap.search(100)); // false
        
        System.out.println("\nDeleting 40...");
        treap.delete(40);
        
        System.out.println("Search 40: " + treap.search(40)); // false
        treap.inorder();
    }
}
