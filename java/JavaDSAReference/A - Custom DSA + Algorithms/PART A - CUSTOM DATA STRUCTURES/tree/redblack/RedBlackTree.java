package tree.redblack;

/**
 * RED-BLACK TREE
 * 
 * What it is:
 * A self-balancing Binary Search Tree where each node contains an extra bit for denoting 
 * the color of the node, either RED or BLACK. It maintains balance through a set of strict rules:
 * 1. Every node is either red or black.
 * 2. The root is always black.
 * 3. Every leaf (NIL/TNULL) is black.
 * 4. If a node is red, both its children are black (No two consecutive red nodes).
 * 5. Every simple path from a node to a descendant leaf contains the same number of black nodes.
 * 
 * Approach/Strategy:
 * We use a sentinel node (TNULL) to represent all null leaves. This dramatically simplifies 
 * boundary conditions because we can check the color of a "null" node without a NullPointerException.
 * - Insert: Add as a standard BST leaf, color it RED. Then call fixUp to resolve any 
 *   consecutive red-red violations using rotations and recoloring based on the UNCLE's color.
 * - Delete: Standard BST delete. If the physically removed node was BLACK, the black-height 
 *   property is violated, so we call fixUp based on the SIBLING's color to restore balance.
 * 
 * Time/Space Complexity:
 * Operation      | Average Time | Worst Time | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Insert         | O(log N)     | O(log N)   | O(1)             | Iterative fixup
 * Delete         | O(log N)     | O(log N)   | O(1)             | Iterative fixup
 * Search         | O(log N)     | O(log N)   | O(1)             | Standard BST search
 * 
 * Why implement this yourself when Java's TreeMap/TreeSet exists?
 * Red-Black Trees exist because they offer LOOSER balancing than AVL trees. An AVL tree 
 * strictly guarantees a height difference of 1, resulting in many rotations during insertions. 
 * A Red-Black Tree ensures the longest path is no more than twice the shortest path. 
 * This means fewer rotations on average, making it faster for write-heavy workloads. 
 * Java's `TreeMap` and `TreeSet` are built entirely on Red-Black Trees.
 */
public class RedBlackTree<T extends Comparable<T>> {

    private enum Color {
        RED, BLACK
    }

    private class RBNode {
        T data;
        RBNode parent;
        RBNode left;
        RBNode right;
        Color color;

        public RBNode(T data) {
            this.data = data;
        }
    }

    private RBNode root;
    // TNULL acts as the sentinel for all null leaves
    private final RBNode TNULL;

    public RedBlackTree() {
        TNULL = new RBNode(null);
        TNULL.color = Color.BLACK;
        root = TNULL;
    }

    // --------------------------------------------------------
    // 1. ROTATIONS (Helper functions)
    // --------------------------------------------------------
    
    private void leftRotate(RBNode x) {
        RBNode y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(RBNode x) {
        RBNode y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    // --------------------------------------------------------
    // 2. INSERTION
    // --------------------------------------------------------

    public void insert(T key) {
        RBNode node = new RBNode(key);
        node.parent = null;
        node.data = key;
        node.left = TNULL;
        node.right = TNULL;
        node.color = Color.RED; // New nodes are always RED

        RBNode y = null;
        RBNode x = this.root;

        // Standard BST insert
        while (x != TNULL) {
            y = x;
            if (node.data.compareTo(x.data) < 0) {
                x = x.left;
            } else if (node.data.compareTo(x.data) > 0) {
                x = x.right;
            } else {
                return; // Duplicates ignored
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.data.compareTo(y.data) < 0) {
            y.left = node;
        } else {
            y.right = node;
        }

        if (node.parent == null) {
            node.color = Color.BLACK;
            return;
        }
        if (node.parent.parent == null) {
            return;
        }

        insertFixup(node);
    }

    /**
     * Fixes consecutive red-red violations after insertion.
     */
    private void insertFixup(RBNode k) {
        RBNode u; // Uncle node
        while (k.parent.color == Color.RED) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left;
                
                // Case 1: Uncle is RED
                // Action: Recolor parent, uncle, and grandparent. Move pointer up to grandparent.
                if (u.color == Color.RED) {
                    u.color = Color.BLACK;
                    k.parent.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    k = k.parent.parent;
                } else {
                    // Case 2: Uncle is BLACK, new node forms a "triangle" (RL structure)
                    // Action: Rotate at parent first to convert into a "line" case
                    if (k == k.parent.left) {
                        k = k.parent;
                        rightRotate(k);
                    }
                    
                    // Case 3: Uncle is BLACK, new node forms a "line" (RR structure)
                    // Action: Rotate at grandparent and recolor
                    k.parent.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    leftRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right;
                
                // Case 1: Uncle is RED
                if (u.color == Color.RED) {
                    u.color = Color.BLACK;
                    k.parent.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    k = k.parent.parent;
                } else {
                    // Case 2: Uncle is BLACK, new node forms a "triangle" (LR structure)
                    if (k == k.parent.right) {
                        k = k.parent;
                        leftRotate(k);
                    }
                    
                    // Case 3: Uncle is BLACK, new node forms a "line" (LL structure)
                    k.parent.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = Color.BLACK;
    }

    // --------------------------------------------------------
    // 3. DELETION
    // --------------------------------------------------------

    private void rbTransplant(RBNode u, RBNode v) {
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    public void delete(T data) {
        deleteNodeHelper(this.root, data);
    }

    private void deleteNodeHelper(RBNode node, T key) {
        RBNode z = TNULL;
        RBNode x, y;
        
        // Find the node
        while (node != TNULL) {
            if (node.data.compareTo(key) == 0) {
                z = node;
            }
            if (node.data.compareTo(key) <= 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }

        if (z == TNULL) {
            return; // Key not found
        }

        y = z;
        Color yOriginalColor = y.color;
        if (z.left == TNULL) {
            x = z.right;
            rbTransplant(z, z.right);
        } else if (z.right == TNULL) {
            x = z.left;
            rbTransplant(z, z.left);
        } else {
            // Node with 2 children: find inorder successor
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                rbTransplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            rbTransplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        
        // If the removed node was BLACK, the black-height is disrupted
        if (yOriginalColor == Color.BLACK) {
            deleteFixup(x);
        }
    }

    /**
     * Fixes the black-height violation after deleting a black node.
     */
    private void deleteFixup(RBNode x) {
        RBNode s; // Sibling node
        while (x != root && x.color == Color.BLACK) {
            if (x == x.parent.left) {
                s = x.parent.right;
                
                // Case 1: Sibling is RED
                if (s.color == Color.RED) {
                    s.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    leftRotate(x.parent);
                    s = x.parent.right;
                }
                
                // Case 2: Sibling is BLACK, both children are BLACK
                if (s.left.color == Color.BLACK && s.right.color == Color.BLACK) {
                    s.color = Color.RED;
                    x = x.parent;
                } else {
                    // Case 3: Sibling is BLACK, near nephew RED, far nephew BLACK
                    if (s.right.color == Color.BLACK) {
                        s.left.color = Color.BLACK;
                        s.color = Color.RED;
                        rightRotate(s);
                        s = x.parent.right;
                    }
                    
                    // Case 4: Sibling is BLACK, far nephew RED
                    s.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    s.right.color = Color.BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                s = x.parent.left;
                
                // Case 1: Sibling is RED
                if (s.color == Color.RED) {
                    s.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    rightRotate(x.parent);
                    s = x.parent.left;
                }
                
                // Case 2: Sibling is BLACK, both children are BLACK
                if (s.right.color == Color.BLACK && s.left.color == Color.BLACK) {
                    s.color = Color.RED;
                    x = x.parent;
                } else {
                    // Case 3: Sibling is BLACK, near nephew RED, far nephew BLACK
                    if (s.left.color == Color.BLACK) {
                        s.right.color = Color.BLACK;
                        s.color = Color.RED;
                        leftRotate(s);
                        s = x.parent.left;
                    }
                    
                    // Case 4: Sibling is BLACK, far nephew RED
                    s.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    s.left.color = Color.BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = Color.BLACK;
    }

    private RBNode minimum(RBNode node) {
        while (node.left != TNULL) {
            node = node.left;
        }
        return node;
    }

    // --------------------------------------------------------
    // 4. SEARCH & TRAVERSAL
    // --------------------------------------------------------

    public boolean search(T key) {
        RBNode node = root;
        while (node != TNULL) {
            int cmp = key.compareTo(node.data);
            if (cmp == 0) return true;
            else if (cmp < 0) node = node.left;
            else node = node.right;
        }
        return false;
    }

    public void inorder() {
        System.out.print("RB-Tree Inorder: ");
        inorderHelper(this.root);
        System.out.println();
    }

    private void inorderHelper(RBNode node) {
        if (node != TNULL) {
            inorderHelper(node.left);
            System.out.print(node.data + "(" + node.color + ") ");
            inorderHelper(node.right);
        }
    }

    public void printRoot() {
        if (root != TNULL) {
            System.out.println("Root is: " + root.data + " (" + root.color + ")");
        } else {
            System.out.println("Tree is empty.");
        }
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- RED-BLACK TREE DEMO ---");
        
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        
        rbt.insert(55);
        rbt.insert(40);
        rbt.insert(65);
        rbt.insert(60);
        rbt.insert(75);
        rbt.insert(57); // Triggers rebalancing cascades
        
        System.out.println("After insertions:");
        rbt.inorder();
        rbt.printRoot();
        
        System.out.println("\nSearch 65? " + rbt.search(65));
        System.out.println("Search 100? " + rbt.search(100));
        
        System.out.println("\nDeleting 40 (Leaf, Black node - triggers deleteFixup)...");
        rbt.delete(40);
        rbt.inorder();
        rbt.printRoot();
        
        System.out.println("\nDeleting 65 (Internal node)...");
        rbt.delete(65);
        rbt.inorder();
        rbt.printRoot();
    }
}
