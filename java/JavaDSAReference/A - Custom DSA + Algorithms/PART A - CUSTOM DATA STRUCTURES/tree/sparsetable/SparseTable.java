package tree.sparsetable;

/**
 * SPARSE TABLE
 * 
 * What it is:
 * A data structure that answers Range Minimum Queries (RMQ), Range Maximum Queries, 
 * or GCD queries on a STATIC array in strictly O(1) time. 
 * Note: The array must be static. Sparse Tables do not support point updates.
 * 
 * Approach/Strategy:
 * We precompute the answers for all intervals whose lengths are powers of 2.
 * `table[i][j]` stores the minimum value in the range starting at index `i` 
 * with length `2^j` (so from index `i` to `i + 2^j - 1`).
 * - Build: We fill the table dynamically. A range of length 2^j is split into two 
 *   overlapping halves of length 2^(j-1). The min of the whole is the min of the two halves.
 * - Query: For a range [L, R] of length `len = R - L + 1`, we find the largest power of 2 
 *   that fits inside `len` (let's call it `2^k`). We then take the minimum of two overlapping 
 *   blocks: one starting at `L` of length `2^k`, and one ending at `R` of length `2^k`.
 *   Because MIN is an idempotent operation (min(A, A) = A), the overlap doesn't affect the answer!
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Build          | O(N log N)      | O(N log N)       | Precomputing power-of-2 intervals
 * Query          | O(1)            | O(1)             | Just reading two overlapped values
 * 
 * Real-world analogy:
 * Imagine you want to know the shortest person in a lineup from position 3 to 10. 
 * You have pre-calculated photos of the shortest person in groups of size 1, 2, 4, and 8.
 * To find the shortest from 3 to 10 (length 8), you look at the photo of size 4 starting at 3, 
 * and the photo of size 4 ending at 10. They overlap, but the shortest person is guaranteed 
 * to be the shortest among those two photos.
 */
public class SparseTable {

    private final int[][] table;
    private final int[] log2;
    private final int n;
    private final int maxLog;

    public SparseTable(int[] arr) {
        this.n = arr.length;
        
        // 1. Precompute logs for O(1) query time (avoiding Math.log calls)
        this.log2 = new int[n + 1];
        log2[1] = 0;
        for (int i = 2; i <= n; i++) {
            log2[i] = log2[i / 2] + 1;
        }
        
        // The maximum power of 2 needed is log2[n]
        this.maxLog = log2[n];
        
        // table[i][j] will store the minimum for the range [i, i + 2^j - 1]
        this.table = new int[n][maxLog + 1];
        
        build(arr);
    }

    /**
     * Builds the Sparse Table in O(N log N) time.
     */
    private void build(int[] arr) {
        // Base case: intervals of length 2^0 = 1 are just the elements themselves
        for (int i = 0; i < n; i++) {
            table[i][0] = arr[i];
        }
        
        // Build intervals of length 2^j
        for (int j = 1; j <= maxLog; j++) {
            // i + 2^j - 1 must be < n to stay within bounds
            for (int i = 0; i + (1 << j) <= n; i++) {
                // The interval of length 2^j is the minimum of two intervals of length 2^(j-1)
                // 1. Starts at i, length 2^(j-1)
                // 2. Starts at i + 2^(j-1), length 2^(j-1)
                table[i][j] = Math.min(
                    table[i][j - 1], 
                    table[i + (1 << (j - 1))][j - 1]
                );
            }
        }
    }

    /**
     * Returns the minimum value in the range [L, R] in strictly O(1) time.
     */
    public int queryMin(int l, int r) {
        if (l < 0 || r >= n || l > r) {
            throw new IllegalArgumentException("Invalid range");
        }
        
        // Length of the range
        int len = r - l + 1;
        
        // Largest power of 2 that is <= len
        int k = log2[len];
        
        // Return the minimum of the two overlapping blocks of length 2^k
        // Block 1: starts at L
        // Block 2: ends at R (so starts at R - 2^k + 1)
        return Math.min(
            table[l][k],
            table[r - (1 << k) + 1][k]
        );
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- SPARSE TABLE (RMQ) DEMO ---");
        
        int[] arr = {7, 2, 3, 0, 5, 10, 3, 12, 18};
        // Indices:  0  1  2  3  4   5  6   7   8
        
        SparseTable st = new SparseTable(arr);
        
        System.out.println("Static Array: [7, 2, 3, 0, 5, 10, 3, 12, 18]");
        
        // Range [0, 4] -> {7, 2, 3, 0, 5} => Min is 0
        System.out.println("queryMin(0, 4): " + st.queryMin(0, 4)); // 0
        
        // Range [4, 7] -> {5, 10, 3, 12} => Min is 3
        System.out.println("queryMin(4, 7): " + st.queryMin(4, 7)); // 3
        
        // Range [0, 2] -> {7, 2, 3} => Min is 2
        System.out.println("queryMin(0, 2): " + st.queryMin(0, 2)); // 2
        
        // Range [5, 8] -> {10, 3, 12, 18} => Min is 3
        System.out.println("queryMin(5, 8): " + st.queryMin(5, 8)); // 3
        
        // Point query Range [8, 8] -> {18} => Min is 18
        System.out.println("queryMin(8, 8): " + st.queryMin(8, 8)); // 18
    }
}
