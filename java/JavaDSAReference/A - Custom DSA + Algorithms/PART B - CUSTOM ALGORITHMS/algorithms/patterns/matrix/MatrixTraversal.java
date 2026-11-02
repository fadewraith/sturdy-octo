package algorithms.patterns.matrix;

import java.util.Arrays;

/**
 * MATRIX PATTERNS (Spiral Order, Spiral Matrix, Rotate Image, Set Matrix Zeroes)
 *
 * WHAT IT IS:
 * Four classic in-place / index-juggling matrix problems.
 *
 * 1. SPIRAL ORDER: read an R x C matrix clockwise from the outside in.
 *    Keep four boundaries top, bottom, left, right. Walk the top row (then top++),
 *    the right column (right--), the bottom row backwards (bottom--), the left column
 *    upwards (left++). The checks `top <= bottom` / `left <= right` before the last
 *    two walks stop a single remaining row or column from being read twice.
 *
 * 2. SPIRAL MATRIX: the reverse - fill 1..n*n into an n x n matrix in spiral order.
 *
 * 3. ROTATE IMAGE 90 degrees clockwise, IN PLACE (n x n):
 *    TRANSPOSE (swap a[i][j] and a[j][i]) and then REVERSE EACH ROW.
 *    Counter-clockwise: transpose, then reverse each COLUMN (or reverse rows first).
 *    Check with one cell: clockwise rotation sends (i, j) to (j, n-1-i).
 *
 * 4. SET MATRIX ZEROES: if a cell is 0, zero its whole row and column. O(1) space:
 *    use the FIRST ROW and FIRST COLUMN as marker arrays, with two booleans to
 *    remember whether the first row/column themselves had a zero. Zero the inner
 *    cells first, the first row/column last (otherwise the markers get destroyed).
 *
 * COMPLEXITY:
 * All four: Time O(R * C).
 * Space: O(1) extra (spiral order's output array aside).
 */
public class MatrixTraversal {

    public static int[] spiralOrder(int[][] matrix) {
        if (matrix.length == 0) return new int[0];
        int rows = matrix.length, cols = matrix[0].length;
        int[] out = new int[rows * cols];
        int k = 0;
        int top = 0, bottom = rows - 1, left = 0, right = cols - 1;
        while (top <= bottom && left <= right) {
            for (int c = left; c <= right; c++) out[k++] = matrix[top][c];
            top++;
            for (int r = top; r <= bottom; r++) out[k++] = matrix[r][right];
            right--;
            if (top <= bottom) {
                for (int c = right; c >= left; c--) out[k++] = matrix[bottom][c];
                bottom--;
            }
            if (left <= right) {
                for (int r = bottom; r >= top; r--) out[k++] = matrix[r][left];
                left++;
            }
        }
        return out;
    }

    public static int[][] generateSpiralMatrix(int n) {
        int[][] m = new int[n][n];
        int value = 1;
        int top = 0, bottom = n - 1, left = 0, right = n - 1;
        while (top <= bottom && left <= right) {
            for (int c = left; c <= right; c++) m[top][c] = value++;
            top++;
            for (int r = top; r <= bottom; r++) m[r][right] = value++;
            right--;
            if (top <= bottom) { for (int c = right; c >= left; c--) m[bottom][c] = value++; bottom--; }
            if (left <= right) { for (int r = bottom; r >= top; r--) m[r][left] = value++; left++; }
        }
        return m;
    }

    /** Rotates a square matrix 90 degrees clockwise in place. */
    public static void rotateClockwise(int[][] m) {
        transpose(m);
        for (int[] row : m) {
            for (int l = 0, r = row.length - 1; l < r; l++, r--) { int t = row[l]; row[l] = row[r]; row[r] = t; }
        }
    }

    /** Rotates a square matrix 90 degrees counter-clockwise in place. */
    public static void rotateCounterClockwise(int[][] m) {
        transpose(m);
        for (int top = 0, bottom = m.length - 1; top < bottom; top++, bottom--) {
            int[] t = m[top]; m[top] = m[bottom]; m[bottom] = t; // reverse the column order
        }
    }

    private static void transpose(int[][] m) {
        int n = m.length;
        for (int[] row : m) if (row.length != n) throw new IllegalArgumentException("matrix must be square");
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) { int t = m[i][j]; m[i][j] = m[j][i]; m[j][i] = t; }
        }
    }

    /** Zeroes the row and column of every 0 cell, in place, with O(1) extra space. */
    public static void setZeroes(int[][] m) {
        if (m.length == 0) return;
        int rows = m.length, cols = m[0].length;
        boolean firstRowZero = false, firstColZero = false;
        for (int c = 0; c < cols; c++) if (m[0][c] == 0) firstRowZero = true;
        for (int r = 0; r < rows; r++) if (m[r][0] == 0) firstColZero = true;

        // mark: m[r][0] = 0 means "row r gets zeroed", m[0][c] = 0 means "column c gets zeroed"
        for (int r = 1; r < rows; r++)
            for (int c = 1; c < cols; c++)
                if (m[r][c] == 0) { m[r][0] = 0; m[0][c] = 0; }

        for (int r = 1; r < rows; r++)
            for (int c = 1; c < cols; c++)
                if (m[r][0] == 0 || m[0][c] == 0) m[r][c] = 0;

        if (firstRowZero) for (int c = 0; c < cols; c++) m[0][c] = 0;
        if (firstColZero) for (int r = 0; r < rows; r++) m[r][0] = 0;
    }

    public static void main(String[] args) {
        System.out.println("--- MATRIX PATTERNS DEMO ---");
        int[][] m = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12}
        };
        System.out.println("Spiral order: " + Arrays.toString(spiralOrder(m)));
        // [1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7]
        System.out.println("Spiral matrix n=3: " + Arrays.deepToString(generateSpiralMatrix(3)));
        // [[1, 2, 3], [8, 9, 4], [7, 6, 5]]

        int[][] img = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        rotateClockwise(img);
        System.out.println("Rotated clockwise: " + Arrays.deepToString(img)); // [[7, 4, 1], [8, 5, 2], [9, 6, 3]]
        rotateCounterClockwise(img);
        System.out.println("And back:          " + Arrays.deepToString(img)); // [[1, 2, 3], [4, 5, 6], [7, 8, 9]]

        int[][] z = {{0, 1, 2, 0}, {3, 4, 5, 2}, {1, 3, 1, 5}};
        setZeroes(z);
        System.out.println("Set zeroes: " + Arrays.deepToString(z)); // [[0, 0, 0, 0], [0, 4, 5, 0], [0, 3, 1, 0]]
    }
}
