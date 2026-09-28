package math.matrix;

/**
 * WHAT IT IS: Naive Matrix Multiplication. Computes the product of two matrices.
 * STRATEGY: Iterate through rows of first matrix, columns of second, and compute dot product.
 * TIME/SPACE COMPLEXITY: Time: O(n^3), Space: O(n^2) for the result.
 * REAL-WORLD ANALOGY / USE CASE: Applying multiple linear transformations.
 * WHEN TO USE / COMBINATION: When matrices are small.
 * 
 * PSEUDOCODE:
 * for i = 0 to rows1
 *   for j = 0 to cols2
 *     for k = 0 to cols1
 *       C[i][j] += A[i][k] * B[k][j]
 */
public class MatrixMultiplication {
    public static int[][] multiply(int[][] a, int[][] b) {
        int r1 = a.length, c1 = a[0].length;
        int c2 = b[0].length;
        int[][] res = new int[r1][c2];
        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                for (int k = 0; k < c1; k++) {
                    res[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return res;
    }
    public static void main(String[] args) {
        int[][] a = {{1, 2}, {3, 4}};
        int[][] b = {{2, 0}, {1, 2}};
        int[][] res = multiply(a, b);
        System.out.println("Result: " + java.util.Arrays.deepToString(res));
    }
}
