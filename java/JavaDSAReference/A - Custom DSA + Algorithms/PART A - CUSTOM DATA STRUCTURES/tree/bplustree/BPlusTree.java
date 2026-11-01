package tree.bplustree;

import array.MyArrayList;

/**
 * B+ TREE (FROM SCRATCH)
 *
 * WHAT IT IS:
 * An advanced, self-balancing tree data structure.
 * CRITICAL DIFFERENCE FROM B-TREE:
 * In a B+ tree, ALL data values are stored strictly in the leaf nodes.
 * Internal nodes only store keys used for routing/navigation.
 * Additionally, leaf nodes are linked together in a linked list for blazing fast
 * range scans!
 *
 * WHERE IT IS USED: Almost every database index (MySQL InnoDB, PostgreSQL) and many
 * file systems. Wide nodes = few disk reads; linked leaves = cheap "WHERE x BETWEEN a AND b".
 *
 * RULES (order m = maxDegree):
 * - A node has at most m children and at most m - 1 keys.
 * - Every node except the root has at least MIN_KEYS = (m - 1) / 2 keys.
 * - Routing: in an internal node, child[i] holds keys < keys[i],
 *   child[i + 1] holds keys >= keys[i].
 * - All leaves are at the same depth.
 *
 * OPERATIONS:
 * - insert: put the key in its leaf. If the leaf overflows, split it in half and COPY
 *   the right half's first key up to the parent. If an internal node overflows, split
 *   it and MOVE its middle key up. A split of the root grows the tree by one level.
 * - delete: remove the key from its leaf. If a node underflows, first try to BORROW a
 *   key from a sibling; if both siblings are at the minimum, MERGE with one. If the root
 *   is left with no keys, the tree shrinks by one level.
 *
 * COMPLEXITY (n keys, order m):
 * get / insert / delete: O(m * log_m n)
 * rangeQuery: O(m * log_m n + k) for k results (walk the leaf linked list)
 */
public class BPlusTree<K extends Comparable<K>, V> {

    private class Node {
        boolean isLeaf;
        Object[] keys;
        Node[] children; // internal nodes only
        Object[] values; // leaves only
        Node next;       // leaves only: the leaf to the right
        int numKeys;

        // Arrays get one extra slot so a node can overflow by one key before it splits
        @SuppressWarnings("unchecked")
        Node(boolean isLeaf) {
            this.isLeaf = isLeaf;
            this.keys = new Object[maxDegree];
            if (isLeaf) {
                this.values = new Object[maxDegree];
            } else {
                this.children = (Node[]) java.lang.reflect.Array.newInstance(Node.class, maxDegree + 1);
            }
        }

        @SuppressWarnings("unchecked")
        K key(int i) {
            return (K) keys[i];
        }
    }

    // Returned by insert when a child split: the separator key and the new right node
    private class Split {
        K key;
        Node right;

        Split(K key, Node right) {
            this.key = key;
            this.right = right;
        }
    }

    private Node root;
    private final int maxDegree;
    private final int minKeys;
    private int size;

    public BPlusTree(int degree) {
        if (degree < 3) throw new IllegalArgumentException("B+ tree order must be >= 3");
        this.maxDegree = degree;
        this.minKeys = (degree - 1) / 2;
        this.root = new Node(true);
    }

    public int size() {
        return size;
    }

    // Index of the child to follow for key: the first i with key < keys[i]
    private int childIndex(Node node, K key) {
        int i = 0;
        while (i < node.numKeys && key.compareTo(node.key(i)) >= 0) i++;
        return i;
    }

    private Node findLeaf(K key) {
        Node curr = root;
        while (!curr.isLeaf) curr = curr.children[childIndex(curr, key)];
        return curr;
    }

    // --------------------------------------------------------
    // 1. SEARCH
    // --------------------------------------------------------

    /** Returns the value for key, or null if the key is not present. */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        Node leaf = findLeaf(key);
        for (int i = 0; i < leaf.numKeys; i++) {
            if (leaf.key(i).compareTo(key) == 0) return (V) leaf.values[i];
        }
        return null;
    }

    public boolean containsKey(K key) {
        Node leaf = findLeaf(key);
        for (int i = 0; i < leaf.numKeys; i++) {
            if (leaf.key(i).compareTo(key) == 0) return true;
        }
        return false;
    }

    // --------------------------------------------------------
    // 2. INSERT (an existing key gets its value updated)
    // --------------------------------------------------------

    public void insert(K key, V value) {
        Split split = insertRec(root, key, value);
        if (split != null) {
            // The root split: make a new root above the two halves
            Node newRoot = new Node(false);
            newRoot.keys[0] = split.key;
            newRoot.children[0] = root;
            newRoot.children[1] = split.right;
            newRoot.numKeys = 1;
            root = newRoot;
        }
    }

    private Split insertRec(Node node, K key, V value) {
        if (node.isLeaf) {
            int pos = 0;
            while (pos < node.numKeys && node.key(pos).compareTo(key) < 0) pos++;
            if (pos < node.numKeys && node.key(pos).compareTo(key) == 0) {
                node.values[pos] = value; // upsert
                return null;
            }
            for (int j = node.numKeys; j > pos; j--) {
                node.keys[j] = node.keys[j - 1];
                node.values[j] = node.values[j - 1];
            }
            node.keys[pos] = key;
            node.values[pos] = value;
            node.numKeys++;
            size++;
            return node.numKeys == maxDegree ? splitLeaf(node) : null;
        }

        int ci = childIndex(node, key);
        Split split = insertRec(node.children[ci], key, value);
        if (split == null) return null;

        // Put the separator at position ci and the new child right after child ci
        for (int j = node.numKeys; j > ci; j--) {
            node.keys[j] = node.keys[j - 1];
            node.children[j + 1] = node.children[j];
        }
        node.keys[ci] = split.key;
        node.children[ci + 1] = split.right;
        node.numKeys++;
        return node.numKeys == maxDegree ? splitInternal(node) : null;
    }

    private Split splitLeaf(Node leaf) {
        int mid = leaf.numKeys / 2;
        Node right = new Node(true);
        for (int j = mid; j < leaf.numKeys; j++) {
            right.keys[j - mid] = leaf.keys[j];
            right.values[j - mid] = leaf.values[j];
            leaf.keys[j] = null;
            leaf.values[j] = null;
        }
        right.numKeys = leaf.numKeys - mid;
        leaf.numKeys = mid;
        right.next = leaf.next; // keep the leaf linked list intact
        leaf.next = right;
        return new Split(right.key(0), right); // COPY up: key stays in the leaf too
    }

    private Split splitInternal(Node node) {
        int mid = node.numKeys / 2;
        K upKey = node.key(mid); // MOVE up: middle key leaves this level
        Node right = new Node(false);
        for (int j = mid + 1; j < node.numKeys; j++) {
            right.keys[j - mid - 1] = node.keys[j];
            node.keys[j] = null;
        }
        for (int j = mid + 1; j <= node.numKeys; j++) {
            right.children[j - mid - 1] = node.children[j];
            node.children[j] = null;
        }
        right.numKeys = node.numKeys - mid - 1;
        node.keys[mid] = null;
        node.numKeys = mid;
        return new Split(upKey, right);
    }

    // --------------------------------------------------------
    // 3. DELETE
    // --------------------------------------------------------

    /** Removes key. Returns true if it was present. */
    public boolean delete(K key) {
        boolean removed = deleteRec(root, key);
        if (!root.isLeaf && root.numKeys == 0) {
            root = root.children[0]; // tree shrinks by one level
        }
        return removed;
    }

    private boolean deleteRec(Node node, K key) {
        if (node.isLeaf) {
            int pos = 0;
            while (pos < node.numKeys && node.key(pos).compareTo(key) != 0) pos++;
            if (pos == node.numKeys) return false;
            for (int j = pos; j < node.numKeys - 1; j++) {
                node.keys[j] = node.keys[j + 1];
                node.values[j] = node.values[j + 1];
            }
            node.numKeys--;
            node.keys[node.numKeys] = null;
            node.values[node.numKeys] = null;
            size--;
            return true;
        }

        int ci = childIndex(node, key);
        boolean removed = deleteRec(node.children[ci], key);
        if (removed && node.children[ci].numKeys < minKeys) {
            fixUnderflow(node, ci);
        }
        // Note: an internal key equal to the deleted key may stay as a separator.
        // That is fine - separators only need to route correctly, not be live keys.
        return removed;
    }

    private void fixUnderflow(Node parent, int i) {
        Node child = parent.children[i];
        Node left = (i > 0) ? parent.children[i - 1] : null;
        Node right = (i < parent.numKeys) ? parent.children[i + 1] : null;

        if (left != null && left.numKeys > minKeys) {
            borrowFromLeft(parent, i, left, child);
        } else if (right != null && right.numKeys > minKeys) {
            borrowFromRight(parent, i, child, right);
        } else if (left != null) {
            merge(parent, i - 1, left, child);
        } else {
            merge(parent, i, child, right);
        }
    }

    private void borrowFromLeft(Node parent, int i, Node left, Node child) {
        // Make room at the front of child
        for (int j = child.numKeys; j > 0; j--) child.keys[j] = child.keys[j - 1];
        if (child.isLeaf) {
            for (int j = child.numKeys; j > 0; j--) child.values[j] = child.values[j - 1];
            child.keys[0] = left.keys[left.numKeys - 1];
            child.values[0] = left.values[left.numKeys - 1];
            left.keys[left.numKeys - 1] = null;
            left.values[left.numKeys - 1] = null;
            parent.keys[i - 1] = child.keys[0]; // new smallest key of child
        } else {
            for (int j = child.numKeys + 1; j > 0; j--) child.children[j] = child.children[j - 1];
            // Rotate through the parent: separator comes down, left's last key goes up
            child.keys[0] = parent.keys[i - 1];
            child.children[0] = left.children[left.numKeys];
            parent.keys[i - 1] = left.keys[left.numKeys - 1];
            left.keys[left.numKeys - 1] = null;
            left.children[left.numKeys] = null;
        }
        left.numKeys--;
        child.numKeys++;
    }

    private void borrowFromRight(Node parent, int i, Node child, Node right) {
        if (child.isLeaf) {
            child.keys[child.numKeys] = right.keys[0];
            child.values[child.numKeys] = right.values[0];
            for (int j = 0; j < right.numKeys - 1; j++) {
                right.keys[j] = right.keys[j + 1];
                right.values[j] = right.values[j + 1];
            }
            right.keys[right.numKeys - 1] = null;
            right.values[right.numKeys - 1] = null;
            right.numKeys--;
            child.numKeys++;
            parent.keys[i] = right.keys[0]; // new smallest key of right
        } else {
            // Rotate through the parent: separator comes down, right's first key goes up
            child.keys[child.numKeys] = parent.keys[i];
            child.children[child.numKeys + 1] = right.children[0];
            parent.keys[i] = right.keys[0];
            for (int j = 0; j < right.numKeys - 1; j++) right.keys[j] = right.keys[j + 1];
            for (int j = 0; j < right.numKeys; j++) right.children[j] = right.children[j + 1];
            right.keys[right.numKeys - 1] = null;
            right.children[right.numKeys] = null;
            right.numKeys--;
            child.numKeys++;
        }
    }

    // Merges b (right) into a (left); parent.keys[sep] is the separator between them
    private void merge(Node parent, int sep, Node a, Node b) {
        if (a.isLeaf) {
            for (int j = 0; j < b.numKeys; j++) {
                a.keys[a.numKeys + j] = b.keys[j];
                a.values[a.numKeys + j] = b.values[j];
            }
            a.numKeys += b.numKeys;
            a.next = b.next; // unlink b from the leaf list
        } else {
            a.keys[a.numKeys] = parent.keys[sep]; // separator comes down
            for (int j = 0; j < b.numKeys; j++) a.keys[a.numKeys + 1 + j] = b.keys[j];
            for (int j = 0; j <= b.numKeys; j++) a.children[a.numKeys + 1 + j] = b.children[j];
            a.numKeys += b.numKeys + 1;
        }
        // Remove separator sep and child sep + 1 (= b) from the parent
        for (int j = sep; j < parent.numKeys - 1; j++) parent.keys[j] = parent.keys[j + 1];
        for (int j = sep + 1; j < parent.numKeys; j++) parent.children[j] = parent.children[j + 1];
        parent.keys[parent.numKeys - 1] = null;
        parent.children[parent.numKeys] = null;
        parent.numKeys--;
    }

    // --------------------------------------------------------
    // 4. RANGE QUERIES (the reason B+ trees exist)
    // --------------------------------------------------------

    /** All values with startKey <= key <= endKey, in key order. */
    @SuppressWarnings("unchecked")
    public MyArrayList<V> rangeQuery(K startKey, K endKey) {
        MyArrayList<V> result = new MyArrayList<>();
        Node curr = findLeaf(startKey); // one descent...
        while (curr != null) {          // ...then just walk right along the leaves
            for (int i = 0; i < curr.numKeys; i++) {
                K k = curr.key(i);
                if (k.compareTo(endKey) > 0) return result;
                if (k.compareTo(startKey) >= 0) result.add((V) curr.values[i]);
            }
            curr = curr.next;
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public void rangeScan(K startKey, K endKey) {
        System.out.println("Range Scan Results [" + startKey + ", " + endKey + "]:");
        Node curr = findLeaf(startKey);
        while (curr != null) {
            for (int i = 0; i < curr.numKeys; i++) {
                K currentKey = curr.key(i);
                if (currentKey.compareTo(endKey) > 0) return;
                if (currentKey.compareTo(startKey) >= 0) {
                    System.out.println("  Key: " + currentKey + ", Value: " + (V) curr.values[i]);
                }
            }
            curr = curr.next;
        }
    }

    /** Height of the tree (1 = only a root leaf). */
    public int height() {
        int h = 1;
        Node curr = root;
        while (!curr.isLeaf) {
            curr = curr.children[0];
            h++;
        }
        return h;
    }

    public static void main(String[] args) {
        System.out.println("--- GENERIC B+ TREE (FROM SCRATCH) DEMO ---");
        BPlusTree<Integer, String> tree = new BPlusTree<>(4);

        for (int k = 1; k <= 20; k++) {
            tree.insert(k * 10, "row-" + k);
        }
        System.out.println("Inserted 20 keys. Size = " + tree.size() + ", height = " + tree.height());
        System.out.println("get(70)  = " + tree.get(70));
        System.out.println("get(75)  = " + tree.get(75));

        tree.insert(70, "row-7-updated");
        System.out.println("After update, get(70) = " + tree.get(70) + ", size still " + tree.size());

        tree.rangeScan(45, 95);

        for (int k = 1; k <= 20; k += 2) tree.delete(k * 10); // delete half the keys
        System.out.println("After deleting 10 keys: size = " + tree.size() + ", height = " + tree.height());
        System.out.println("rangeQuery(0, 1000) = " + tree.rangeQuery(0, 1000));
    }
}
