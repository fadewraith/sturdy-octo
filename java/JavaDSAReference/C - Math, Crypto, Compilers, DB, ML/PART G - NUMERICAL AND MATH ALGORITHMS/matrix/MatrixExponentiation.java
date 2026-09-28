package math.matrix;

/**
 * WHAT IT IS: Matrix Exponentiation. Computes A^n efficiently.
 * STRATEGY: Use divide and conquer (binary exponentiation) to compute powers in O(log n) matrix multiplications.
 * TIME/SPACE COMPLEXITY: Time: O(M^3 log n) where M is matrix size, Space: O(M^2).
 * REAL-WORLD ANALOGY / USE CASE: Finding nth Fibonacci number in O(log n), graph paths of length N.
 * WHEN TO USE / COMBINATION: Linear recurrence relations for huge N.
 * 
 * PSEUDOCODE:
 * res = Identity
 * base = A
 * while n > 0:
 *   if n is odd, res = res * base
 *   base = base * base
 *   n >>= 1
 */
public class MatrixExponentiation {
    public static int[][] power(int[][] a, int n) {
        int size = a.length;
        int[][] res = new int[size][size];
        for (int i = 0; i < size; i++) res[i][i] = 1;
        
        int[][] base = new int[size][size];
        for (int i=0; i<size; i++) System.arraycopy(a[i], 0, base[i], 0, size);
        
        while (n > 0) {
            if (n % 2 == 1) res = multiply(res, base);
            base = multiply(base, base);
            n /= 2;
        }
        return res;
    }
    
    private static int[][] multiply(int[][] a, int[][] b) {
        int n = a.length;
        int[][] res = new int[n][n];
        for (int i=0; i<n; i++)
            for (int j=0; j<n; j++)
                for (int k=0; k<n; k++)
                    res[i][j] += a[i][k] * b[k][j];
        return res;
    }
    
    public static void main(String[] args) {
        int[][] fibMatrix = {{1, 1}, {1, 0}};
        int[][] res = power(fibMatrix, 5); // 5th power gives F(6) and F(5)
        System.out.println("Result (Fib matrix^5): " + java.util.Arrays.deepToString(res));
    }
}
