package tree.fenwick;

/**
 * FENWICK TREE (Binary Indexed Tree / BIT)
 * 
 * What it is:
 * A data structure that can efficiently update elements and calculate prefix sums 
 * in a table of numbers. It takes much less space than a Segment Tree (just an array 
 * of size N+1) and is significantly easier to code, though slightly less versatile.
 * 
 * Approach/Strategy:
 * The core magic relies on bitwise operations, specifically isolating the lowest 
 * set bit using `(index & -index)`.
 * - 1-based Indexing: BITs natively use 1-based indexing to make the bit math work.
 * - Update: To add a `delta` to an index, we add it to the current index, then move 
 *   to the next responsible node by ADDING the lowest set bit (`index += index & -index`) 
 *   and repeat until we reach the end of the array.
 * - Prefix Sum: To get the sum from 1 to `index`, we add the value at `index`, then 
 *   move to the parent node by SUBTRACTING the lowest set bit (`index -= index & -index`) 
 *   and repeat until index hits 0.
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Build          | O(N)            | O(N)             | Linear time fast-build
 * Update         | O(log N)        | O(1) extra       | Bitwise climbing
 * Prefix Sum     | O(log N)        | O(1) extra       | Bitwise descending
 * Range Sum      | O(log N)        | O(1) extra       | Prefix(R) - Prefix(L-1)
 * 
 * Real-world analogy:
 * Imagine a cascading system of piggy banks based on binary sizes. If you drop a coin 
 * into bank 3, it overflows slightly to inform bank 4, which overflows to inform bank 8. 
 * When you want the total from 1 to 7, you just ask bank 7 (which knows about 7), 
 * bank 6 (which knows about 5 and 6), and bank 4 (which knows about 1 to 4).
 * 
 * Note vs Segment Tree: 
 * BIT is faster and uses less memory (O(N) instead of O(4N)), but Segment Trees 
 * can easily handle Range Maximum/Minimum queries, whereas standard BITs are strictly 
 * limited to reversible operations (like Sum, XOR).
 */
public class FenwickTree {

    private final int[] bit;
    private final int n;

    /**
     * Initializes the BIT. The internal array is 1-based, so its size is arr.length + 1.
     */
    public FenwickTree(int[] arr) {
        this.n = arr.length;
        this.bit = new int[n + 1];
        buildFast(arr);
    }

    /**
     * Builds the tree in strictly O(N) time instead of O(N log N).
     */
    private void buildFast(int[] arr) {
        // 1. Copy array into 1-based BIT array
        for (int i = 0; i < n; i++) {
            bit[i + 1] = arr[i];
        }

        // 2. Cascade the sums upward using bitwise logic
        for (int i = 1; i <= n; i++) {
            int parentIndex = i + (i & -i);
            if (parentIndex <= n) {
                bit[parentIndex] += bit[i];
            }
        }
    }

    /**
     * Adds `delta` to the element at the given `index` (0-based for the user).
     * Time Complexity: O(log N)
     */
    public void update(int index, int delta) {
        if (index < 0 || index >= n) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        
        // Convert to 1-based internal index
        index = index + 1;
        
        // Cascade the update up the tree
        while (index <= n) {
            bit[index] += delta;
            index += (index & -index); // Add lowest set bit
        }
    }

    /**
     * Returns the sum of elements from index 0 to `index` (0-based for the user).
     * Time Complexity: O(log N)
     */
    public int prefixSum(int index) {
        if (index < 0 || index >= n) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        
        int sum = 0;
        // Convert to 1-based internal index
        index = index + 1;
        
        // Accumulate the sum going down the tree
        while (index > 0) {
            sum += bit[index];
            index -= (index & -index); // Subtract lowest set bit
        }
        return sum;
    }

    /**
     * Returns the sum of elements in the range [l, r] (0-based for the user).
     * Time Complexity: O(log N)
     */
    public int rangeSum(int l, int r) {
        if (l < 0 || r >= n || l > r) {
            throw new IllegalArgumentException("Invalid range");
        }
        
        // sum[l...r] = prefixSum(r) - prefixSum(l - 1)
        if (l == 0) {
            return prefixSum(r);
        } else {
            return prefixSum(r) - prefixSum(l - 1);
        }
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- FENWICK TREE (BIT) DEMO ---");
        
        int[] arr = {1, 3, 5, 7, 9, 11};
        // Indices:  0  1  2  3  4   5
        // Prefixes: 1, 4, 9,16,25, 36
        
        FenwickTree bit = new FenwickTree(arr);
        
        System.out.println("Initial array: [1, 3, 5, 7, 9, 11]");
        
        // 1. Prefix Sum
        System.out.println("Prefix sum up to index 3 (1+3+5+7): " + bit.prefixSum(3)); // Expected: 16
        
        // 2. Range Sum
        System.out.println("Range sum from index 1 to 4 (3+5+7+9): " + bit.rangeSum(1, 4)); // Expected: 24
        
        // 3. Update
        System.out.println("\nUpdating index 2 (currently 5). Adding +4 delta (becomes 9)...");
        // We pass the DELTA, not the absolute new value!
        bit.update(2, 4); 
        
        // Array conceptually becomes: [1, 3, 9, 7, 9, 11]
        
        System.out.println("New prefix sum up to index 3 (1+3+9+7): " + bit.prefixSum(3)); // Expected: 20
        System.out.println("New range sum from index 1 to 4 (3+9+7+9): " + bit.rangeSum(1, 4)); // Expected: 28
    }
}
