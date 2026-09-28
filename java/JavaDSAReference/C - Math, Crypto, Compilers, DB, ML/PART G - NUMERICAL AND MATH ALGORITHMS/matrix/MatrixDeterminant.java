package math.matrix;

/**
 * WHAT IT IS: Matrix Determinant. A scalar value that is a function of the entries of a square matrix.
 * STRATEGY: Laplace expansion (recursive) or Gaussian elimination. Using Laplace here.
 * TIME/SPACE COMPLEXITY: Time: O(n!), Space: O(n^2) due to submatrices.
 * REAL-WORLD ANALOGY / USE CASE: Volume scaling factor, checking if matrix is invertible (det != 0).
 * WHEN TO USE / COMBINATION: Small matrices. For large, use LUP decomposition.
 * 
 * PSEUDOCODE:
 * if size 1, return A[0][0]
 * for each element in first row, det += sign * A[0][i] * det(submatrix)
 */
public class MatrixDeterminant {
    public static int determinant(int[][] a) {
        int n = a.length;
        if (n == 1) return a[0][0];
        if (n == 2) return a[0][0] * a[1][1] - a[0][1] * a[1][0];
        
        int det = 0;
        for (int i = 0; i < n; i++) {
            int[][] sub = new int[n-1][n-1];
            for (int r = 1; r < n; r++) {
                int cIndex = 0;
                for (int c = 0; c < n; c++) {
                    if (c == i) continue;
                    sub[r-1][cIndex++] = a[r][c];
                }
            }
            int sign = (i % 2 == 0) ? 1 : -1;
            det += sign * a[0][i] * determinant(sub);
        }
        return det;
    }
    public static void main(String[] args) {
        int[][] a = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println("Determinant: " + determinant(a));
    }
}
