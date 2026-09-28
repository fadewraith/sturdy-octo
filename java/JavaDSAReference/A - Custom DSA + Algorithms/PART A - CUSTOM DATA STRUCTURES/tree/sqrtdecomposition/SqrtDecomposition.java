package tree.sqrtdecomposition;

/**
 * SQUARE ROOT (SQRT) DECOMPOSITION
 * 
 * What it is:
 * A technique used to answer range queries and point updates in O(sqrt(N)) time.
 * While slower than a Segment Tree (which takes O(log N)), Sqrt Decomposition is 
 * famous for being incredibly easy to implement in high-pressure interview scenarios 
 * and requires far less code.
 * 
 * Approach/Strategy:
 * We divide the array of size N into smaller "blocks" of size `sqrt(N)`. 
 * There will be roughly `sqrt(N)` such blocks.
 * - Build: We iterate over the array and precalculate the answer (e.g., sum) for each block.
 * - Point Update: We update the original array AND update the precalculated value 
 *   for the specific block that contains this index.
 * - Range Query: For a range [L, R], we sum up the fully overlapped blocks entirely in O(1) 
 *   per block, and for the partially overlapped blocks at the ends of the range, we just 
 *   loop through those specific array elements one by one.
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Build          | O(N)            | O(sqrt(N))       | Blocks array size
 * Update         | O(1)            | O(1)             | Updates arr and block instantly
 * Range Query    | O(sqrt(N))      | O(1) extra       | Traverses at most ~3*sqrt(N) elements
 * 
 * Real-world analogy:
 * Imagine counting the total money in 100 cash registers. Instead of counting all 100, 
 * you group them into 10 zones. The manager of each zone pre-counts their 10 registers. 
 * If you need the sum from register 15 to 85, you manually count registers 15-19, 
 * then ask the managers of zones 3 through 8 for their totals, then manually count 
 * registers 80-85.
 */
public class SqrtDecomposition {

    private final int[] arr;
    private final int[] blocks;
    private final int blockSize;
    private final int n;

    public SqrtDecomposition(int[] arr) {
        this.n = arr.length;
        this.arr = new int[n];
        
        // Block size is usually ceil(sqrt(N))
        this.blockSize = (int) Math.ceil(Math.sqrt(n));
        
        // Number of blocks is also roughly sqrt(N)
        this.blocks = new int[blockSize];
        
        build(arr);
    }

    /**
     * Builds the blocks in O(N) time.
     */
    private void build(int[] input) {
        for (int i = 0; i < n; i++) {
            this.arr[i] = input[i];
            
            // i / blockSize gives the index of the block this element belongs to
            int blockIndex = i / blockSize;
            blocks[blockIndex] += arr[i];
        }
    }

    /**
     * Updates a single index in O(1) time.
     */
    public void update(int index, int newValue) {
        if (index < 0 || index >= n) throw new IllegalArgumentException("Index out of bounds");
        
        int blockIndex = index / blockSize;
        
        // Remove the old value from the block sum, add the new value
        blocks[blockIndex] = blocks[blockIndex] - arr[index] + newValue;
        
        // Update the original array
        arr[index] = newValue;
    }

    /**
     * Queries the sum in the range [l, r] in O(sqrt(N)) time.
     */
    public int rangeQuery(int l, int r) {
        if (l < 0 || r >= n || l > r) throw new IllegalArgumentException("Invalid range");
        
        int sum = 0;
        int leftBlock = l / blockSize;
        int rightBlock = r / blockSize;
        
        if (leftBlock == rightBlock) {
            // Case 1: L and R are in the exact same block. 
            // We cannot use the precalculated block sum. We must loop manually.
            for (int i = l; i <= r; i++) {
                sum += arr[i];
            }
        } else {
            // Case 2: L and R span across multiple blocks.
            
            // A. Manually sum the tail end of the left block
            // (From L until the end of that specific block)
            int leftBlockEnd = (leftBlock + 1) * blockSize - 1;
            for (int i = l; i <= leftBlockEnd; i++) {
                sum += arr[i];
            }
            
            // B. Add the fully overlapped middle blocks in O(1) each!
            for (int b = leftBlock + 1; b <= rightBlock - 1; b++) {
                sum += blocks[b];
            }
            
            // C. Manually sum the beginning of the right block
            // (From the start of that block until R)
            int rightBlockStart = rightBlock * blockSize;
            for (int i = rightBlockStart; i <= r; i++) {
                sum += arr[i];
            }
        }
        
        return sum;
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- SQRT DECOMPOSITION DEMO ---");
        
        int[] arr = {1, 5, 2, 4, 6, 1, 3, 5, 7, 10}; 
        // 10 elements. sqrt(10) is approx 4. 
        // Blocks: [1, 5, 2, 4], [6, 1, 3, 5], [7, 10]
        // B-Sums:      12      ,      15      ,    17
        
        SqrtDecomposition sqrtDec = new SqrtDecomposition(arr);
        
        System.out.println("Initial array: [1, 5, 2, 4, 6, 1, 3, 5, 7, 10]");
        
        // 1. Query within the same block
        System.out.println("Sum range [1, 2] (5+2): " + sqrtDec.rangeQuery(1, 2)); // 7
        
        // 2. Query spanning multiple blocks
        // Range [1, 8] -> 5+2+4 (part of B0) + 15 (all of B1) + 7+10 (all of B2)
        // Wait, range [1, 8] doesn't include index 9. So B2 only gives 7.
        // Sum: 5+2+4 + 6+1+3+5 + 7 = 33
        System.out.println("Sum range [1, 8]: " + sqrtDec.rangeQuery(1, 8)); // 33
        
        // 3. Update element
        System.out.println("\nUpdating index 6 (value 3) to 13...");
        sqrtDec.update(6, 13);
        
        // Re-query spanning multiple blocks
        System.out.println("Sum range [1, 8] after update: " + sqrtDec.rangeQuery(1, 8)); // 43
    }
}
