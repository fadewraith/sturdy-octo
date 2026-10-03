package tree.segment;

/**
 * ================================================================
 * Data Structure: Segment Tree with Lazy Propagation
 * ================================================================
 * Approach:
 * - A tree structure built on top of an array to allow fast range queries.
 * - 'lazy' array keeps track of pending updates for a segment.
 * - When updating a range, instead of propagating to all leaves (O(N)),
 *   we update the current node and defer child updates to the lazy array (O(log N)).
 * - Before visiting any node, we 'push down' any pending updates from the lazy array.
 *
 * Time Complexity:
 * - Build: O(N)
 * - Range Update: O(log N)
 * - Range Query: O(log N)
 *
 * Space Complexity:
 * - O(N) space. Specifically, tree and lazy arrays of size 4*N.
 *
 * Real-world Use Case:
 * - Processing multiple modifications over continuous ranges of items,
 *   such as updating prices in a range of days, or calculating
 *   running totals for dynamic data.
 * ================================================================
 */
public class SegmentTreeLazy {

    private int[] tree;
    private int[] lazy;
    private int n;

    public SegmentTreeLazy(int[] arr) {
        if (arr == null || arr.length == 0) return;
        n = arr.length;
        tree = new int[4 * n];
        lazy = new int[4 * n];
        build(arr, 0, 0, n - 1);
    }

    private void build(int[] arr, int node, int start, int end) {
        if (start == end) {
            tree[node] = arr[start];
            return;
        }
        int mid = start + (end - start) / 2;
        int leftChild = 2 * node + 1;
        int rightChild = 2 * node + 2;
        build(arr, leftChild, start, mid);
        build(arr, rightChild, mid + 1, end);
        tree[node] = tree[leftChild] + tree[rightChild];
    }

    private void pushDown(int node, int start, int end) {
        if (lazy[node] != 0) {
            // Apply pending update to current node
            tree[node] += (end - start + 1) * lazy[node];

            // If not a leaf, push the lazy value down to children
            if (start != end) {
                lazy[2 * node + 1] += lazy[node];
                lazy[2 * node + 2] += lazy[node];
            }
            // Clear current node's lazy value
            lazy[node] = 0;
        }
    }

    public void updateRange(int l, int r, int val) {
        if (n == 0) return;
        updateRange(0, 0, n - 1, l, r, val);
    }

    private void updateRange(int node, int start, int end, int l, int r, int val) {
        pushDown(node, start, end);

        if (start > end || start > r || end < l) {
            return; // Out of bounds
        }

        if (start >= l && end <= r) {
            // Segment fully inside range
            lazy[node] += val;
            pushDown(node, start, end);
            return;
        }

        int mid = start + (end - start) / 2;
        int leftChild = 2 * node + 1;
        int rightChild = 2 * node + 2;

        updateRange(leftChild, start, mid, l, r, val);
        updateRange(rightChild, mid + 1, end, l, r, val);

        tree[node] = tree[leftChild] + tree[rightChild];
    }

    public int queryRange(int l, int r) {
        if (n == 0) return 0;
        return queryRange(0, 0, n - 1, l, r);
    }

    private int queryRange(int node, int start, int end, int l, int r) {
        pushDown(node, start, end);

        if (start > end || start > r || end < l) {
            return 0; // Out of bounds, return additive identity
        }

        if (start >= l && end <= r) {
            return tree[node]; // Completely inside
        }

        int mid = start + (end - start) / 2;
        int leftChild = 2 * node + 1;
        int rightChild = 2 * node + 2;

        int p1 = queryRange(leftChild, start, mid, l, r);
        int p2 = queryRange(rightChild, mid + 1, end, l, r);

        return p1 + p2;
    }

    public static void main(String[] args) {
        System.out.println("--- Segment Tree with Lazy Propagation Tests ---");

        int[] arr1 = {10};
        SegmentTreeLazy st1 = new SegmentTreeLazy(arr1);
        System.out.println("Single element query (0, 0): " + st1.queryRange(0, 0)); // 10
        st1.updateRange(0, 0, 5);
        System.out.println("Single element query after update: " + st1.queryRange(0, 0)); // 15

        int[] arr2 = {1, 2, 3, 4, 5};
        SegmentTreeLazy st2 = new SegmentTreeLazy(arr2);
        System.out.println("Initial range query (1, 3): " + st2.queryRange(1, 3)); // 2+3+4 = 9
        
        st2.updateRange(0, 4, 10); // full range update, adds 10 to all
        System.out.println("Query (0, 4) after full update (+10): " + st2.queryRange(0, 4)); // 15 + 50 = 65
        
        st2.updateRange(1, 2, 5); // overlapping update
        System.out.println("Query (1, 3) after overlap update (+5 to 1..2): " + st2.queryRange(1, 3)); 
        // arr2 is now: {11, 17, 18, 14, 15}
        // sum(1..3) = 17 + 18 + 14 = 49
    }
}
