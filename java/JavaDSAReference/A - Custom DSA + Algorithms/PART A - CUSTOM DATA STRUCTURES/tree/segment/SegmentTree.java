package tree.segment;

/**
 * SEGMENT TREE (with Lazy Propagation)
 * 
 * What it is:
 * A highly versatile tree data structure used for answering Range Queries (like sum, 
 * min, max) and performing Range Updates over an array in logarithmic time.
 * 
 * Approach/Strategy:
 * The tree is strictly binary and usually represented as a flat array where the root 
 * is at index 0 (or 1). For a node at index `i`, its left child is `2*i + 1` and right 
 * child is `2*i + 2`. Each node represents an "interval" or "segment" of the original array.
 * - Build: Recursively divide the array into halves until we reach single elements (leaves). 
 *   Then combine them going up.
 * - Lazy Propagation (Advanced): When updating a large range, updating every leaf takes O(N). 
 *   Instead, we update the highest covering node and leave a "lazy" marker. We only push 
 *   this marker down to its children when we absolutely have to visit them later.
 * 
 * Note: This specific implementation focuses on RANGE SUM queries. 
 * (It can easily be modified for Range Min/Max by changing the `+` operator to `Math.min()`).
 * 
 * Time/Space Complexity:
 * Operation             | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Build                 | O(N)            | O(N)             | Tree array size is 4*N
 * Point Update          | O(log N)        | O(log N) stack   | Updates a single index
 * Range Update (Lazy)   | O(log N)        | O(log N) stack   | Updates a range of indices
 * Range Query           | O(log N)        | O(log N) stack   | Queries a range
 * 
 * Real-world analogy:
 * Imagine a corporate hierarchy. The CEO (root) knows the total revenue of the company. 
 * The VPs (children) know the revenue of their respective departments. If you want to 
 * know the revenue of a specific combination of teams, you ask the highest-level managers 
 * that completely cover those teams, without having to ask every single entry-level employee.
 */
public class SegmentTree {

    private final int[] tree;
    private final int[] lazy;
    private final int n;

    /**
     * Constructs and builds the Segment Tree from the given array.
     */
    public SegmentTree(int[] arr) {
        this.n = arr.length;
        // The maximum size of a segment tree array is 4 * N
        this.tree = new int[4 * n];
        this.lazy = new int[4 * n];
        
        if (n > 0) {
            build(arr, 0, 0, n - 1);
        }
    }

    // --------------------------------------------------------
    // 1. BUILD
    // --------------------------------------------------------

    private void build(int[] arr, int node, int start, int end) {
        if (start == end) {
            // Leaf node will have a single element
            tree[node] = arr[start];
        } else {
            int mid = start + (end - start) / 2;
            int leftChild = 2 * node + 1;
            int rightChild = 2 * node + 2;

            // Recursively build the left and right children
            build(arr, leftChild, start, mid);
            build(arr, rightChild, mid + 1, end);

            // Internal node will have the sum of both of its children
            tree[node] = tree[leftChild] + tree[rightChild];
        }
    }

    // --------------------------------------------------------
    // 2. POINT UPDATE (O(log N))
    // --------------------------------------------------------

    /**
     * Updates a single index in the original array to a new value.
     * (Replaces the value, does not add to it).
     */
    public void pointUpdate(int index, int newValue) {
        if (index < 0 || index >= n) throw new IllegalArgumentException("Index out of bounds");
        pointUpdateRec(0, 0, n - 1, index, newValue);
    }

    private void pointUpdateRec(int node, int start, int end, int index, int newValue) {
        if (start == end) {
            // Leaf node found, update it
            tree[node] = newValue;
        } else {
            int mid = start + (end - start) / 2;
            int leftChild = 2 * node + 1;
            int rightChild = 2 * node + 2;

            if (index <= mid) {
                // If index is in the left child
                pointUpdateRec(leftChild, start, mid, index, newValue);
            } else {
                // If index is in the right child
                pointUpdateRec(rightChild, mid + 1, end, index, newValue);
            }

            // Recalculate the sum of the current node after updating children
            tree[node] = tree[leftChild] + tree[rightChild];
        }
    }

    // --------------------------------------------------------
    // 3. LAZY PROPAGATION HELPERS
    // --------------------------------------------------------

    /**
     * Pushes pending lazy updates down to the children.
     */
    private void propagate(int node, int start, int end) {
        if (lazy[node] != 0) {
            // This node needs to be updated
            // We add (lazy_value * number_of_elements_in_range) to the sum
            tree[node] += lazy[node] * (end - start + 1);

            // If it's not a leaf node, pass the lazy value down to children
            if (start != end) {
                lazy[2 * node + 1] += lazy[node];
                lazy[2 * node + 2] += lazy[node];
            }

            // Clear the lazy value for current node
            lazy[node] = 0;
        }
    }

    // --------------------------------------------------------
    // 4. RANGE UPDATE (Advanced - Lazy Propagation)
    // --------------------------------------------------------

    /**
     * Adds `val` to all elements in the range [l, r].
     */
    public void rangeUpdate(int l, int r, int val) {
        if (l < 0 || r >= n || l > r) throw new IllegalArgumentException("Invalid range");
        rangeUpdateRec(0, 0, n - 1, l, r, val);
    }

    private void rangeUpdateRec(int node, int start, int end, int l, int r, int val) {
        // First, handle any pending lazy updates for this node
        propagate(node, start, end);

        // 1. No overlap
        if (start > end || start > r || end < l) {
            return;
        }

        // 2. Total overlap (The node's range is completely inside [l, r])
        if (start >= l && end <= r) {
            // Apply the update to the lazy array for this node
            lazy[node] += val;
            // Propagate it immediately to update this node's `tree` value
            propagate(node, start, end);
            return;
        }

        // 3. Partial overlap
        int mid = start + (end - start) / 2;
        int leftChild = 2 * node + 1;
        int rightChild = 2 * node + 2;

        rangeUpdateRec(leftChild, start, mid, l, r, val);
        rangeUpdateRec(rightChild, mid + 1, end, l, r, val);

        // Update current node based on children
        tree[node] = tree[leftChild] + tree[rightChild];
    }

    // --------------------------------------------------------
    // 5. RANGE QUERY (O(log N))
    // --------------------------------------------------------

    /**
     * Returns the sum of elements in the range [l, r].
     */
    public int rangeQuery(int l, int r) {
        if (l < 0 || r >= n || l > r) throw new IllegalArgumentException("Invalid range");
        return rangeQueryRec(0, 0, n - 1, l, r);
    }

    private int rangeQueryRec(int node, int start, int end, int l, int r) {
        // Resolve any pending lazy updates before answering the query
        propagate(node, start, end);

        // 1. No overlap
        if (start > end || start > r || end < l) {
            return 0; // Return 0 for sum. (Return Integer.MAX_VALUE if doing range minimum!)
        }

        // 2. Total overlap
        if (start >= l && end <= r) {
            return tree[node];
        }

        // 3. Partial overlap
        int mid = start + (end - start) / 2;
        int leftSum = rangeQueryRec(2 * node + 1, start, mid, l, r);
        int rightSum = rangeQueryRec(2 * node + 2, mid + 1, end, l, r);

        return leftSum + rightSum;
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- SEGMENT TREE DEMO ---");
        
        int[] arr = {1, 3, 5, 7, 9, 11};
        // Indices:  0  1  2  3  4   5
        
        SegmentTree segTree = new SegmentTree(arr);
        
        System.out.println("Initial array: [1, 3, 5, 7, 9, 11]");
        
        // 1. Range Query
        // Sum of index 1 to 3: 3 + 5 + 7 = 15
        System.out.println("Sum of range [1, 3]: " + segTree.rangeQuery(1, 3)); // Expected: 15
        
        // 2. Point Update
        // Update index 1 from 3 to 10. Array becomes: [1, 10, 5, 7, 9, 11]
        System.out.println("\nPoint Update: index 1 becomes 10.");
        segTree.pointUpdate(1, 10);
        
        // Sum of index 1 to 3 is now: 10 + 5 + 7 = 22
        System.out.println("Sum of range [1, 3]: " + segTree.rangeQuery(1, 3)); // Expected: 22
        
        // 3. Range Update (Lazy Propagation)
        // Add 5 to range [0, 2]. 
        // Array was: [1, 10, 5, 7, 9, 11]
        // Becomes:   [6, 15, 10, 7, 9, 11]
        System.out.println("\nRange Update (Lazy): Add 5 to all elements in range [0, 2].");
        segTree.rangeUpdate(0, 2, 5);
        
        // Sum of index 1 to 3 is now: 15 + 10 + 7 = 32
        System.out.println("Sum of range [1, 3]: " + segTree.rangeQuery(1, 3)); // Expected: 32
        
        // Sum of entire array: 6 + 15 + 10 + 7 + 9 + 11 = 58
        System.out.println("Sum of entire array [0, 5]: " + segTree.rangeQuery(0, 5)); // Expected: 58
    }
}
