package math.matrix;

/**
 * WHAT IT IS: Strassen's Matrix Multiplication.
 * STRATEGY: Divide and conquer algorithm that reduces the number of recursive multiplications from 8 to 7.
 * TIME/SPACE COMPLEXITY: Time: O(n^2.81), Space: O(n^2).
 * REAL-WORLD ANALOGY / USE CASE: Large scale matrix multiplications in scientific computing.
 * WHEN TO USE / COMBINATION: For large matrices where the asymptotic improvement outweighs the larger constant factor.
 * 
 * PSEUDOCODE:
 * Divide A, B into 4 submatrices.
 * Compute 7 products P1..P7 using additions and recursive multiplications.
 * Combine P1..P7 into C11, C12, C21, C22.
 */
public class StrassenMatrixMultiplication {
    // simplified implementation for exact powers of 2
    public static int[][] multiply(int[][] A, int[][] B) {
        int n = A.length;
        if (n <= 1) return new int[][]{{A[0][0] * B[0][0]}};
        
        int newSize = n / 2;
        int[][] a11 = new int[newSize][newSize], a12 = new int[newSize][newSize], a21 = new int[newSize][newSize], a22 = new int[newSize][newSize];
        int[][] b11 = new int[newSize][newSize], b12 = new int[newSize][newSize], b21 = new int[newSize][newSize], b22 = new int[newSize][newSize];

        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                a11[i][j] = A[i][j];
                a12[i][j] = A[i][j + newSize];
                a21[i][j] = A[i + newSize][j];
                a22[i][j] = A[i + newSize][j + newSize];

                b11[i][j] = B[i][j];
                b12[i][j] = B[i][j + newSize];
                b21[i][j] = B[i + newSize][j];
                b22[i][j] = B[i + newSize][j + newSize];
            }
        }
        
        int[][] p1 = multiply(add(a11, a22), add(b11, b22));
        int[][] p2 = multiply(add(a21, a22), b11);
        int[][] p3 = multiply(a11, sub(b12, b22));
        int[][] p4 = multiply(a22, sub(b21, b11));
        int[][] p5 = multiply(add(a11, a12), b22);
        int[][] p6 = multiply(sub(a21, a11), add(b11, b12));
        int[][] p7 = multiply(sub(a12, a22), add(b21, b22));
        
        int[][] c11 = add(sub(add(p1, p4), p5), p7);
        int[][] c12 = add(p3, p5);
        int[][] c21 = add(p2, p4);
        int[][] c22 = add(sub(add(p1, p3), p2), p6);
        
        int[][] C = new int[n][n];
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                C[i][j] = c11[i][j];
                C[i][j + newSize] = c12[i][j];
                C[i + newSize][j] = c21[i][j];
                C[i + newSize][j + newSize] = c22[i][j];
            }
        }
        return C;
    }
    
    private static int[][] add(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) C[i][j] = A[i][j] + B[i][j];
        return C;
    }
    
    private static int[][] sub(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) C[i][j] = A[i][j] - B[i][j];
        return C;
    }
    
    public static void main(String[] args) {
        int[][] a = {{1, 2}, {3, 4}};
        int[][] b = {{2, 0}, {1, 2}};
        int[][] res = multiply(a, b);
        System.out.println("Result: " + java.util.Arrays.deepToString(res));
    }
}
