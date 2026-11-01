package tree.orderstatistic;

import array.MyArrayList;

/**
 * ORDER STATISTIC TREE
 *
 * What it is:
 * A sorted set that can also answer, in O(log N):
 *   - select(k): the k-th smallest element (0-based)        e.g. "the median", "the 10th best score"
 *   - rank(x):   how many elements are smaller than x       e.g. "what position is this player in?"
 *   - countBetween(lo, hi): how many elements fall in a range
 * java.util.TreeSet cannot do select/rank faster than O(N).
 *
 * Approach/Strategy (augmented balanced BST, CLRS chapter 14):
 * A self-balancing AVL tree where every node also stores `size` = number of nodes in its
 * subtree. Rotations recompute it from the children in O(1).
 *   select(k): let L = size(left). If k < L go left; if k == L this node is the answer;
 *              otherwise go right looking for k - L - 1.
 *   rank(x):   walk down; every time we go RIGHT, everything in the left subtree plus the
 *              current node is smaller than x, so add size(left) + 1.
 *
 * Time/Space Complexity:
 * Operation                        | Time
 * ---------------------------------------------
 * add / remove / contains          | O(log N)
 * select / rank / countBetween     | O(log N)
 * size                             | O(1)
 * Space                            | O(N)
 *
 * Interview uses: running median, "count of smaller numbers after self", leaderboards,
 * sliding-window k-th smallest. Set semantics: duplicates are ignored (store
 * (value, uniqueId) pairs if you need duplicates).
 */
public class OrderStatisticTree<T extends Comparable<T>> implements Iterable<T> {

    private class Node {
        T value;
        int height = 1;
        int size = 1;   // nodes in this subtree (the augmentation)
        Node left, right;

        Node(T value) {
            this.value = value;
        }
    }

    private Node root;

    // ---------------- AVL helpers ----------------

    private int height(Node n) {
        return n == null ? 0 : n.height;
    }

    private int size(Node n) {
        return n == null ? 0 : n.size;
    }

    private void update(Node n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
        n.size = 1 + size(n.left) + size(n.right);
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right;
        x.right = y;
        update(y);
        update(x);
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        update(x);
        update(y);
        return y;
    }

    private Node rebalance(Node n) {
        update(n);
        int balance = height(n.left) - height(n.right);
        if (balance > 1) {
            if (height(n.left.left) < height(n.left.right)) n.left = rotateLeft(n.left);
            return rotateRight(n);
        }
        if (balance < -1) {
            if (height(n.right.right) < height(n.right.left)) n.right = rotateRight(n.right);
            return rotateLeft(n);
        }
        return n;
    }

    // ---------------- set operations ----------------

    /** Adds x. Returns false if it was already present. */
    public boolean add(T x) {
        if (x == null) throw new IllegalArgumentException("null not supported");
        int before = size();
        root = addRec(root, x);
        return size() > before;
    }

    private Node addRec(Node n, T x) {
        if (n == null) return new Node(x);
        int c = x.compareTo(n.value);
        if (c == 0) return n;
        if (c < 0) n.left = addRec(n.left, x);
        else n.right = addRec(n.right, x);
        return rebalance(n);
    }

    /** Removes x. Returns true if it was present. */
    public boolean remove(T x) {
        if (x == null) return false;
        int before = size();
        root = removeRec(root, x);
        return size() < before;
    }

    private Node removeRec(Node n, T x) {
        if (n == null) return null;
        int c = x.compareTo(n.value);
        if (c < 0) n.left = removeRec(n.left, x);
        else if (c > 0) n.right = removeRec(n.right, x);
        else {
            if (n.left == null || n.right == null) return n.left != null ? n.left : n.right;
            Node succ = n.right;
            while (succ.left != null) succ = succ.left;
            n.value = succ.value;
            n.right = removeRec(n.right, succ.value);
        }
        return rebalance(n);
    }

    public boolean contains(T x) {
        Node n = root;
        while (n != null) {
            int c = x.compareTo(n.value);
            if (c == 0) return true;
            n = c < 0 ? n.left : n.right;
        }
        return false;
    }

    public int size() {
        return size(root);
    }

    public boolean isEmpty() {
        return root == null;
    }

    // ---------------- order statistics ----------------

    /** The k-th smallest element, 0-based (select(0) = min, select(size-1) = max). */
    public T select(int k) {
        if (k < 0 || k >= size()) throw new IndexOutOfBoundsException("k: " + k + ", size: " + size());
        Node n = root;
        while (true) {
            int leftSize = size(n.left);
            if (k < leftSize) n = n.left;
            else if (k == leftSize) return n.value;
            else {
                k -= leftSize + 1; // skip the left subtree and this node
                n = n.right;
            }
        }
    }

    /** Number of elements strictly smaller than x (x need not be in the set). */
    public int rank(T x) {
        int r = 0;
        Node n = root;
        while (n != null) {
            int c = x.compareTo(n.value);
            if (c <= 0) n = n.left;
            else {
                r += size(n.left) + 1; // left subtree and this node are all < x
                n = n.right;
            }
        }
        return r;
    }

    /** Number of elements e with lo <= e <= hi. */
    public int countBetween(T lo, T hi) {
        if (lo.compareTo(hi) > 0) return 0;
        return rank(hi) + (contains(hi) ? 1 : 0) - rank(lo);
    }

    /** Lower median (select((size - 1) / 2)). */
    public T median() {
        if (isEmpty()) throw new IllegalStateException("empty");
        return select((size() - 1) / 2);
    }

    /** Ascending order. */
    @Override
    public java.util.Iterator<T> iterator() {
        MyArrayList<T> all = new MyArrayList<>(size());
        inorder(root, all);
        return all.iterator();
    }

    private void inorder(Node n, MyArrayList<T> out) {
        if (n == null) return;
        inorder(n.left, out);
        out.add(n.value);
        inorder(n.right, out);
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- ORDER STATISTIC TREE DEMO (leaderboard) ---");
        OrderStatisticTree<Integer> scores = new OrderStatisticTree<>();
        int[] incoming = {720, 450, 980, 610, 830, 300, 560};
        for (int s : incoming) {
            scores.add(s);
            System.out.println("score " + s + " arrives -> running median = " + scores.median());
        }
        int n = scores.size();
        System.out.println("Highest score: " + scores.select(n - 1) + ", 2nd highest: " + scores.select(n - 2));
        System.out.println("Scores below 700: " + scores.rank(700) + " -> a 700 would be ranked #" + (n - scores.rank(700) + 1) + " from the top");
        System.out.println("Scores between 500 and 850: " + scores.countBetween(500, 850)); // 560, 610, 720, 830 = 4
    }
}
