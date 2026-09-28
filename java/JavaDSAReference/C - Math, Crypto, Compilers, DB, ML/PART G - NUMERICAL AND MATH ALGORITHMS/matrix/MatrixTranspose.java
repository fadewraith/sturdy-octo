package math.matrix;

/**
 * WHAT IT IS: Matrix Transpose. Swaps rows and columns.
 * STRATEGY: Iterate i, j and assign res[j][i] = A[i][j].
 * TIME/SPACE COMPLEXITY: Time: O(n * m), Space: O(n * m).
 * REAL-WORLD ANALOGY / USE CASE: Changing perspective of data, preparing for certain matrix operations.
 * WHEN TO USE / COMBINATION: Pre-processing step for other algorithms.
 * 
 * PSEUDOCODE:
 * for i = 0 to r
 *   for j = 0 to c
 *     res[j][i] = A[i][j]
 */
public class MatrixTranspose {
    public static int[][] transpose(int[][] a) {
        int r = a.length, c = a[0].length;
        int[][] res = new int[c][r];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                res[j][i] = a[i][j];
            }
        }
        return res;
    }
    public static void main(String[] args) {
        int[][] a = {{1, 2, 3}, {4, 5, 6}};
        int[][] res = transpose(a);
        System.out.println("Transposed: " + java.util.Arrays.deepToString(res));
    }
}
