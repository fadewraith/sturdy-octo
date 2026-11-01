package tree.segment;

/**
 * GENERIC SEGMENT TREE (pluggable combine function)
 *
 * What it is:
 * SegmentTree.java answers range SUM queries on ints. This version works for ANY type
 * and ANY associative combine function: sum, min, max, gcd, product, string concat,
 * "max subarray" records... You pass the function and its identity value:
 *
 *   new GenericSegmentTree<>(values, Integer::sum, 0)                   // range sum
 *   new GenericSegmentTree<>(values, Math::min, Integer.MAX_VALUE)      // range min
 *   new GenericSegmentTree<>(values, (a, b) -> a + b, "")               // string concat
 *
 * Rules for the combine function:
 * - ASSOCIATIVE: combine(combine(a, b), c) == combine(a, combine(b, c)), so we can
 *   group the range in whatever pieces the tree gives us.
 * - It does NOT need to be commutative: this implementation always combines in
 *   left-to-right order, so things like concatenation or matrix products work.
 * - identity: combine(identity, x) == x (0 for sum, +infinity for min, "" for concat).
 *
 * Approach/Strategy (iterative, "bottom-up" segment tree):
 * Array of size 2n. Leaves live at tree[n..2n-1]; parent of node i is i/2, so
 * tree[i] = combine(tree[2i], tree[2i+1]). No recursion, no 4n array.
 * Query [l, r]: start at both leaves and climb. Whenever the left boundary is a RIGHT
 * child, its node is fully inside the range: take it and move right. Same for the right
 * boundary being a LEFT child. Left pieces are combined in order on the left, right
 * pieces on the right - that is what keeps non-commutative functions correct.
 *
 * Time/Space Complexity:
 * Operation   | Time
 * --------------------------
 * build       | O(N)
 * set(i, v)   | O(log N)
 * query(l, r) | O(log N)
 * get(i)      | O(1)
 * Space       | O(N)
 */
public class GenericSegmentTree<T> {

    /** The merge operation. A tiny interface of our own (works with lambdas and method refs). */
    @FunctionalInterface
    public interface Combiner<T> {
        T combine(T left, T right);
    }

    private final int n;
    private final Object[] tree;
    private final Combiner<T> combiner;
    private final T identity;

    public GenericSegmentTree(T[] values, Combiner<T> combiner, T identity) {
        this.n = values.length;
        this.tree = new Object[2 * Math.max(n, 1)];
        this.combiner = combiner;
        this.identity = identity;
        for (int i = 0; i < n; i++) tree[n + i] = values[i];        // leaves
        for (int i = n - 1; i >= 1; i--) tree[i] = merge(2 * i, 2 * i + 1); // parents, bottom-up
    }

    @SuppressWarnings("unchecked")
    private T at(int node) {
        return (T) tree[node];
    }

    private T merge(int leftNode, int rightNode) {
        return combiner.combine(at(leftNode), at(rightNode));
    }

    private void checkIndex(int i) {
        if (i < 0 || i >= n) throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + n);
    }

    public int size() {
        return n;
    }

    /** Current value at index i. */
    public T get(int i) {
        checkIndex(i);
        return at(n + i);
    }

    /** Replaces the value at index i and fixes every ancestor. */
    public void set(int i, T value) {
        checkIndex(i);
        int node = n + i;
        tree[node] = value;
        for (node /= 2; node >= 1; node /= 2) tree[node] = merge(2 * node, 2 * node + 1);
    }

    /** combine(values[l], values[l+1], ..., values[r]) - inclusive range, left to right. */
    public T query(int l, int r) {
        if (l < 0 || r >= n || l > r) throw new IllegalArgumentException("Invalid range [" + l + ", " + r + "]");
        T leftResult = identity, rightResult = identity;
        int lo = l + n, hi = r + n + 1; // half-open [lo, hi) over leaf positions
        while (lo < hi) {
            if ((lo & 1) == 1) leftResult = combiner.combine(leftResult, at(lo++));  // lo is a right child
            if ((hi & 1) == 1) rightResult = combiner.combine(at(--hi), rightResult); // hi-1 is a left child
            lo /= 2;
            hi /= 2;
        }
        return combiner.combine(leftResult, rightResult);
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    private static int gcd(int a, int b) {
        return b == 0 ? Math.abs(a) : gcd(b, a % b);
    }

    public static void main(String[] args) {
        System.out.println("--- GENERIC SEGMENT TREE DEMO ---");
        Integer[] data = {5, 2, 8, 6, 3, 7, 4, 12};

        GenericSegmentTree<Integer> sum = new GenericSegmentTree<>(data, Integer::sum, 0);
        GenericSegmentTree<Integer> min = new GenericSegmentTree<>(data, Math::min, Integer.MAX_VALUE);
        GenericSegmentTree<Integer> max = new GenericSegmentTree<>(data, Math::max, Integer.MIN_VALUE);
        GenericSegmentTree<Integer> g = new GenericSegmentTree<>(data, GenericSegmentTree::gcd, 0);

        System.out.println("Data: [5, 2, 8, 6, 3, 7, 4, 12], range [2, 5] = {8, 6, 3, 7}");
        System.out.println("sum = " + sum.query(2, 5) + ", min = " + min.query(2, 5)
                + ", max = " + max.query(2, 5) + ", gcd[2..3] = " + g.query(2, 3)); // 24, 3, 8, 2

        sum.set(3, 100);
        System.out.println("After set(3, 100): sum[2..5] = " + sum.query(2, 5)); // 118

        // Non-commutative: concatenation must keep left-to-right order
        String[] letters = {"s", "e", "g", "m", "e", "n", "t"};
        GenericSegmentTree<String> text = new GenericSegmentTree<>(letters, (a, b) -> a + b, "");
        System.out.println("concat[0..6] = " + text.query(0, 6) + ", concat[2..4] = " + text.query(2, 4)); // segment, gme
    }
}
