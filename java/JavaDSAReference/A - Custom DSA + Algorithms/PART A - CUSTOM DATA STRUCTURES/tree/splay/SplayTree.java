package tree.splay;

import array.MyArrayList;

/**
 * SPLAY TREE
 *
 * What it is:
 * A self-adjusting binary search tree: every access (search, insert, delete) moves the
 * accessed key to the ROOT using rotations ("splaying"). No balance info is stored at all.
 *
 * Why it's interesting:
 * - A single operation can be O(N) (the tree can become a long path), but any sequence of
 *   M operations costs O(M log N) in total: AMORTIZED O(log N) per operation.
 * - Frequently/recently used keys stay near the top, so skewed access patterns ("hot"
 *   keys, repeated lookups of the same item) are much faster than in AVL/Red-Black trees.
 * Used in caches, memory allocators, some network routers and garbage collectors.
 *
 * Approach/Strategy - TOP-DOWN splay (Sleator & Tarjan), done in ONE pass without recursion:
 * Walk down from the root toward the key, splitting the tree into three parts:
 *   L = everything known to be smaller, R = everything known to be larger, and the
 *   middle tree still being searched. Two steps down in the same direction (zig-zig)
 *   first rotate, which roughly halves the depth of the path - that is what gives the
 *   amortized O(log N) bound. At the end, reassemble: L | key | R.
 *
 * Time/Space Complexity:
 * Operation                | Amortized | Worst single op
 * --------------------------------------------------------
 * insert / remove / contains | O(log N) | O(N)
 * min / max                  | O(log N) | O(N)
 * Space                      | O(N), no extra fields per node
 */
public class SplayTree<T extends Comparable<T>> implements Iterable<T> {

    private static class Node<T> {
        T value;
        Node<T> left, right;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root;
    private int size;

    /**
     * Top-down splay: returns the new root, which holds 'key' if present, otherwise the
     * last node visited on the search path (its in-order predecessor or successor).
     */
    private Node<T> splay(Node<T> t, T key) {
        if (t == null) return null;
        Node<T> header = new Node<>(null);   // header.right collects L, header.left collects R
        Node<T> leftMax = header, rightMin = header;
        while (true) {
            int c = key.compareTo(t.value);
            if (c < 0) {
                if (t.left == null) break;
                if (key.compareTo(t.left.value) < 0) { // zig-zig: rotate right first
                    Node<T> y = t.left;
                    t.left = y.right;
                    y.right = t;
                    t = y;
                    if (t.left == null) break;
                }
                rightMin.left = t;                   // t and its right side go to R
                rightMin = t;
                t = t.left;
            } else if (c > 0) {
                if (t.right == null) break;
                if (key.compareTo(t.right.value) > 0) { // zag-zag: rotate left first
                    Node<T> y = t.right;
                    t.right = y.left;
                    y.left = t;
                    t = y;
                    if (t.right == null) break;
                }
                leftMax.right = t;                   // t and its left side go to L
                leftMax = t;
                t = t.right;
            } else {
                break;
            }
        }
        // Reassemble: L | t | R
        leftMax.right = t.left;
        rightMin.left = t.right;
        t.left = header.right;
        t.right = header.left;
        return t;
    }

    // ---------------- operations ----------------

    /** Adds x (it becomes the root). Returns false if already present (it still moves to the root). */
    public boolean insert(T x) {
        if (x == null) throw new IllegalArgumentException("null not supported");
        if (root == null) {
            root = new Node<>(x);
            size = 1;
            return true;
        }
        root = splay(root, x);
        int c = x.compareTo(root.value);
        if (c == 0) return false;
        Node<T> n = new Node<>(x);
        if (c < 0) {             // new root: old root goes to the right, its left part to our left
            n.left = root.left;
            n.right = root;
            root.left = null;
        } else {
            n.right = root.right;
            n.left = root;
            root.right = null;
        }
        root = n;
        size++;
        return true;
    }

    /** True if present. Splays the key (or its neighbor) to the root. */
    public boolean contains(T x) {
        if (x == null || root == null) return false;
        root = splay(root, x);
        return x.compareTo(root.value) == 0;
    }

    /** Removes x. Returns true if it was present. */
    public boolean remove(T x) {
        if (x == null || root == null) return false;
        root = splay(root, x);
        if (x.compareTo(root.value) != 0) return false;
        if (root.left == null) {
            root = root.right;
        } else {
            // Splaying the LEFT subtree for x brings its maximum to the top (with no right child),
            // so the old right subtree can simply hang there.
            Node<T> right = root.right;
            root = splay(root.left, x);
            root.right = right;
        }
        size--;
        return true;
    }

    public T min() {
        if (root == null) return null;
        Node<T> n = root;
        while (n.left != null) n = n.left;
        root = splay(root, n.value); // accessing it brings it to the root
        return root.value;
    }

    public T max() {
        if (root == null) return null;
        Node<T> n = root;
        while (n.right != null) n = n.right;
        root = splay(root, n.value);
        return root.value;
    }

    /** The current root (the most recently accessed key), or null. */
    public T rootValue() {
        return root == null ? null : root.value;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** Height, computed level by level (no recursion: a splay tree can be N levels deep). */
    public int height() {
        if (root == null) return 0;
        MyArrayList<Node<T>> level = new MyArrayList<>();
        level.add(root);
        int h = 0;
        while (!level.isEmpty()) {
            h++;
            MyArrayList<Node<T>> next = new MyArrayList<>();
            for (int i = 0; i < level.size(); i++) {
                Node<T> n = level.get(i);
                if (n.left != null) next.add(n.left);
                if (n.right != null) next.add(n.right);
            }
            level = next;
        }
        return h;
    }

    /** Ascending order, with an explicit stack (no recursion). Does not splay. */
    @Override
    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            private final MyArrayList<Node<T>> stack = new MyArrayList<>();
            { pushLeft(root); }

            private void pushLeft(Node<T> n) {
                while (n != null) {
                    stack.add(n);
                    n = n.left;
                }
            }

            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public T next() {
                if (stack.isEmpty()) throw new java.util.NoSuchElementException();
                Node<T> n = stack.remove(stack.size() - 1);
                pushLeft(n.right);
                return n.value;
            }
        };
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- SPLAY TREE DEMO ---");
        SplayTree<Integer> tree = new SplayTree<>();
        for (int i = 1; i <= 1000; i++) tree.insert(i); // sorted inserts: each new key becomes the root
        System.out.println("After inserting 1..1000 in order: height = " + tree.height() + " (a long path!)");

        tree.contains(1); // deepest node: one splay roughly halves the path
        System.out.println("After one lookup of 1: root = " + tree.rootValue() + ", height = " + tree.height());
        for (int i = 0; i < 20; i++) tree.contains((i * 389) % 1000 + 1);
        System.out.println("After 20 scattered lookups: height = " + tree.height());

        tree.contains(500);
        System.out.println("Looked up 500 -> it is now the root: " + tree.rootValue());
        tree.remove(500);
        System.out.println("Removed 500: size = " + tree.size() + ", contains(500) = " + tree.contains(500)
                + ", min = " + tree.min() + ", max = " + tree.max());
    }
}
