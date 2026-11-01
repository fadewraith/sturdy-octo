package tree.interval;

import array.MyArrayList;

/**
 * INTERVAL TREE
 *
 * What it is:
 * Stores intervals [low, high] (with a value each) and answers OVERLAP questions fast:
 *   - "does anything overlap [10, 20]?"           (meeting-room / calendar conflicts)
 *   - "list everything that overlaps [10, 20]"
 *   - "which intervals contain point 15?"         (stabbing query)
 *
 * Approach/Strategy (augmented balanced BST, as in CLRS chapter 14):
 * - A self-balancing AVL tree ordered by the interval's LOW end (then high end).
 * - AUGMENTATION: every node also stores `max` = the largest HIGH end anywhere in its
 *   subtree. Rotations and inserts/deletes recompute it from the children in O(1).
 * - Pruning with `max`: if a subtree's max < query.low, NOTHING in it can reach the
 *   query, so skip the whole subtree. If a node's low > query.high, nothing to its
 *   right can overlap either (they all start even later).
 *
 * Two closed intervals [a, b] and [c, d] overlap  <=>  a <= d  AND  c <= b.
 *
 * Time/Space Complexity:
 * Operation                     | Time
 * -------------------------------------------------------
 * insert / delete               | O(log N)
 * overlapsAny (find one)        | O(log N)
 * findAllOverlapping / stabbing | O(log N + m)  (m = number of results)
 * Space                         | O(N)
 *
 * Generic: endpoints can be any Comparable type (Integer, Double, LocalTime...).
 * An identical [low, high] inserted twice keeps ONE entry and updates its value.
 */
public class IntervalTree<K extends Comparable<K>, V> implements Iterable<IntervalTree.Interval<K, V>> {

    /** One stored interval with its value (what queries return). */
    public static class Interval<K, V> {
        private final K low, high;
        private V value;

        Interval(K low, K high, V value) {
            this.low = low;
            this.high = high;
            this.value = value;
        }

        public K getLow() {
            return low;
        }

        public K getHigh() {
            return high;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "[" + low + ", " + high + "]=" + value;
        }
    }

    private class Node {
        Interval<K, V> interval;
        K max;          // largest high endpoint in this subtree (the augmentation)
        int height = 1;
        Node left, right;

        Node(Interval<K, V> interval) {
            this.interval = interval;
            this.max = interval.high;
        }
    }

    private Node root;
    private int size;

    // ---------------- helpers ----------------

    private int compareIntervals(K lowA, K highA, Interval<K, V> b) {
        int c = lowA.compareTo(b.low);
        return c != 0 ? c : highA.compareTo(b.high);
    }

    private K maxOf(K a, K b) {
        if (a == null) return b;
        if (b == null) return a;
        return a.compareTo(b) >= 0 ? a : b;
    }

    private int height(Node n) {
        return n == null ? 0 : n.height;
    }

    /** Recompute height and max from the children - must run after ANY child change. */
    private void update(Node n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
        n.max = maxOf(n.interval.high, maxOf(n.left == null ? null : n.left.max, n.right == null ? null : n.right.max));
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right;
        x.right = y;
        update(y); // y is now below x: fix y first
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
            if (height(n.left.left) < height(n.left.right)) n.left = rotateLeft(n.left); // LR case
            return rotateRight(n);
        }
        if (balance < -1) {
            if (height(n.right.right) < height(n.right.left)) n.right = rotateRight(n.right); // RL case
            return rotateLeft(n);
        }
        return n;
    }

    private static <K extends Comparable<K>> boolean overlaps(K lowA, K highA, K lowB, K highB) {
        return lowA.compareTo(highB) <= 0 && lowB.compareTo(highA) <= 0;
    }

    private void checkInterval(K low, K high) {
        if (low == null || high == null) throw new IllegalArgumentException("endpoints must not be null");
        if (low.compareTo(high) > 0) throw new IllegalArgumentException("low > high: [" + low + ", " + high + "]");
    }

    // ---------------- insert / delete ----------------

    public void insert(K low, K high, V value) {
        checkInterval(low, high);
        root = insertRec(root, low, high, value);
    }

    private Node insertRec(Node n, K low, K high, V value) {
        if (n == null) {
            size++;
            return new Node(new Interval<>(low, high, value));
        }
        int c = compareIntervals(low, high, n.interval);
        if (c == 0) {
            n.interval.value = value; // same interval: update
            return n;
        }
        if (c < 0) n.left = insertRec(n.left, low, high, value);
        else n.right = insertRec(n.right, low, high, value);
        return rebalance(n);
    }

    /** Removes the interval [low, high]. Returns true if it was present. */
    public boolean delete(K low, K high) {
        checkInterval(low, high);
        int before = size;
        root = deleteRec(root, low, high);
        return size < before;
    }

    private Node deleteRec(Node n, K low, K high) {
        if (n == null) return null;
        int c = compareIntervals(low, high, n.interval);
        if (c < 0) n.left = deleteRec(n.left, low, high);
        else if (c > 0) n.right = deleteRec(n.right, low, high);
        else {
            if (n.left == null || n.right == null) {
                size--;
                return n.left != null ? n.left : n.right;
            }
            Node succ = n.right; // two children: copy the in-order successor here, then delete it
            while (succ.left != null) succ = succ.left;
            n.interval = succ.interval;
            n.right = deleteRec(n.right, succ.interval.low, succ.interval.high);
        }
        return rebalance(n);
    }

    // ---------------- queries ----------------

    /**
     * Any ONE interval overlapping [low, high], or null. O(log N):
     * go left only if the left subtree's max reaches low - otherwise nothing there can overlap,
     * and (CLRS proof) if the left side has no overlap, the right side can't either.
     */
    public Interval<K, V> overlapsAny(K low, K high) {
        checkInterval(low, high);
        Node n = root;
        while (n != null) {
            if (overlaps(low, high, n.interval.low, n.interval.high)) return n.interval;
            if (n.left != null && n.left.max.compareTo(low) >= 0) n = n.left;
            else n = n.right;
        }
        return null;
    }

    /** All intervals overlapping [low, high], sorted by low end. */
    public MyArrayList<Interval<K, V>> findAllOverlapping(K low, K high) {
        checkInterval(low, high);
        MyArrayList<Interval<K, V>> out = new MyArrayList<>();
        collect(root, low, high, out);
        return out;
    }

    private void collect(Node n, K low, K high, MyArrayList<Interval<K, V>> out) {
        if (n == null || n.max.compareTo(low) < 0) return;   // nothing in here reaches 'low'
        collect(n.left, low, high, out);
        if (overlaps(low, high, n.interval.low, n.interval.high)) out.add(n.interval);
        if (n.interval.low.compareTo(high) <= 0) collect(n.right, low, high, out); // right side starts >= this low
    }

    /** All intervals containing the point (a "stabbing query"). */
    public MyArrayList<Interval<K, V>> stab(K point) {
        return findAllOverlapping(point, point);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** Intervals in order of low end (then high end). */
    @Override
    public java.util.Iterator<Interval<K, V>> iterator() {
        MyArrayList<Interval<K, V>> all = new MyArrayList<>();
        inorder(root, all);
        return all.iterator();
    }

    private void inorder(Node n, MyArrayList<Interval<K, V>> out) {
        if (n == null) return;
        inorder(n.left, out);
        out.add(n.interval);
        inorder(n.right, out);
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- INTERVAL TREE DEMO (meeting room bookings, minutes since 9:00) ---");
        IntervalTree<Integer, String> room = new IntervalTree<>();
        room.insert(0, 30, "standup");
        room.insert(60, 120, "design review");
        room.insert(90, 150, "1:1 with manager");
        room.insert(200, 260, "lunch talk");
        room.insert(250, 300, "planning");

        System.out.println("Bookings: " + iterate(room));
        System.out.println("Does [40, 55] clash with anything? " + room.overlapsAny(40, 55));   // null
        System.out.println("Does [100, 110] clash? " + room.overlapsAny(100, 110));
        System.out.println("Everything overlapping [110, 210]: " + room.findAllOverlapping(110, 210));
        System.out.println("What is happening at minute 255? " + room.stab(255));
        room.delete(60, 120);
        System.out.println("After cancelling the design review, [100, 110] -> " + room.findAllOverlapping(100, 110));
    }

    private static String iterate(IntervalTree<Integer, String> t) {
        StringBuilder sb = new StringBuilder();
        for (Interval<Integer, String> i : t) sb.append(i).append(' ');
        return sb.toString().trim();
    }
}
