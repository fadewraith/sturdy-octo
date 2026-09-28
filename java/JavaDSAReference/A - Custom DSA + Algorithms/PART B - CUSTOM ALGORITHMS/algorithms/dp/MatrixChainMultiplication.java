package algorithms.dp;

/**
 * MATRIX CHAIN MULTIPLICATION (Partition DP)
 * 
 * WHAT IT IS:
 * Given a sequence of matrices, find the most efficient way to multiply these 
 * matrices together. 
 * Matrix multiplication is associative: (A(BC)) = ((AB)C). But the ORDER in which 
 * we group them drastically changes the number of scalar multiplications required!
 * 
 * STRATEGY:
 * We use "Partition DP". We evaluate splitting the chain of matrices at every 
 * possible point `k`, recursively calculating the cost of the left side, the right 
 * side, and the cost to multiply the two resulting matrices together.
 * 
 * `dp[i][j]` = Minimum number of scalar multiplications needed to compute the matrix 
 * chain from matrix `i` to matrix `j`.
 * 
 * For a chain A(10x30), B(30x5), C(5x60), the dimension array `p` is [10, 30, 5, 60].
 * Matrix i has dimensions p[i-1] x p[i].
 * 
 * COMPLEXITY:
 * Time: O(N^3)
 * Space: O(N^2)
 */
public class MatrixChainMultiplication {

    public static int matrixChainOrder(int[] p, int n) {
        // dp[i][j] = minimum number of scalar multiplications needed to compute matrix A[i]...A[j]
        // 1-indexed for ease of reading, so size is (n) x (n) where n is length of p
        int[][] dp = new int[n][n];

        // cost is zero when multiplying one matrix
        for (int i = 1; i < n; i++) {
            dp[i][i] = 0;
        }

        // L is chain length
        for (int L = 2; L < n; L++) {
            for (int i = 1; i < n - L + 1; i++) {
                int j = i + L - 1;
                
                dp[i][j] = Integer.MAX_VALUE;
                
                for (int k = i; k <= j - 1; k++) {
                    // Cost = cost of left subchain + cost of right subchain + cost of multiplying them
                    int cost = dp[i][k] + dp[k + 1][j] + p[i - 1] * p[k] * p[j];
                    
                    if (cost < dp[i][j]) {
                        dp[i][j] = cost;
                    }
                }
            }
        }

        return dp[1][n - 1];
    }

    public static void main(String[] args) {
        System.out.println("--- MATRIX CHAIN MULTIPLICATION DEMO ---");
        // Matrix 1: 10x30
        // Matrix 2: 30x5
        // Matrix 3: 5x60
        int[] arr = {10, 30, 5, 60};
        int size = arr.length;
        
        System.out.println("Minimum number of multiplications is " + matrixChainOrder(arr, size));
        // Expected: 4500 ( (AB)C = (10*30*5) + (10*5*60) = 1500 + 3000 = 4500 )
        // Compared to A(BC) which is (30*5*60) + (10*30*60) = 9000 + 18000 = 27000!
    }
}
