package math.matrix;

import java.util.Arrays;

/**
 * LU DECOMPOSITION with partial pivoting  (P A = L U)
 *
 * WHAT IT IS:
 * Factor a square matrix A into a LOWER-triangular L (1s on the diagonal) and an
 * UPPER-triangular U, after reordering rows with a permutation P. It is Gaussian
 * elimination (GaussianElimination.java) with the elimination multipliers SAVED
 * in L instead of thrown away.
 *
 * WHEN TO USE THIS:
 * - Solving A x = b for MANY right-hand sides b: factor once in O(n^3), then each
 *   solve is two triangular substitutions, O(n^2). (Circuit simulation, finite
 *   elements, Newton's method in many dimensions.)
 * - Determinant: det(A) = (+/-1 from the row swaps) * product of U's diagonal.
 * - Inverse: solve A x = e_i for each unit vector (rarely needed - solve instead).
 * - What LAPACK's dgetrf / numpy.linalg.solve do under the hood.
 *
 * STRATEGY (Doolittle, in place):
 * For each column k:
 *   1. PARTIAL PIVOTING: swap up the row with the largest |A[i][k]| (i >= k).
 *      Dividing by a tiny pivot amplifies rounding errors; a zero pivot is fatal.
 *   2. For every row i below: factor l = A[i][k] / A[k][k]; store l in A[i][k]
 *      (that's L), and subtract l * row k from the rest of row i (that's U).
 * Solve P A x = P b:  L y = P b (forward substitution), then U x = y (back
 * substitution).
 *
 * COMPLEXITY:
 * Factorisation: O(n^3) (about 2n^3/3 flops). Each solve: O(n^2). Space: O(n^2).
 */
public class LUDecomposition {

    private final double[][] lu;   // L below the diagonal (unit diagonal implied), U on and above
    private final int[] perm;      // row i of P*A is row perm[i] of A
    private final int swaps;
    private final boolean singular;

    public LUDecomposition(double[][] a) {
        int n = a.length;
        for (double[] row : a) if (row.length != n) throw new IllegalArgumentException("matrix must be square");
        lu = new double[n][];
        for (int i = 0; i < n; i++) lu[i] = a[i].clone();
        perm = new int[n];
        for (int i = 0; i < n; i++) perm[i] = i;
        int s = 0;
        boolean sing = false;
        for (int k = 0; k < n; k++) {
            int pivot = k;                                         // 1. partial pivoting
            for (int i = k + 1; i < n; i++) if (Math.abs(lu[i][k]) > Math.abs(lu[pivot][k])) pivot = i;
            if (pivot != k) {
                double[] t = lu[k]; lu[k] = lu[pivot]; lu[pivot] = t;
                int tp = perm[k]; perm[k] = perm[pivot]; perm[pivot] = tp;
                s++;
            }
            if (Math.abs(lu[k][k]) < 1e-12) { sing = true; continue; }
            for (int i = k + 1; i < n; i++) {                      // 2. eliminate below the pivot
                double l = lu[i][k] / lu[k][k];
                lu[i][k] = l;                                      // store the multiplier (L)
                for (int j = k + 1; j < n; j++) lu[i][j] -= l * lu[k][j];
            }
        }
        swaps = s;
        singular = sing;
    }

    public boolean isSingular() { return singular; }

    public double[][] lower() {
        int n = lu.length;
        double[][] l = new double[n][n];
        for (int i = 0; i < n; i++) { for (int j = 0; j < i; j++) l[i][j] = lu[i][j]; l[i][i] = 1; }
        return l;
    }

    public double[][] upper() {
        int n = lu.length;
        double[][] u = new double[n][n];
        for (int i = 0; i < n; i++) for (int j = i; j < n; j++) u[i][j] = lu[i][j];
        return u;
    }

    /** The permutation as a matrix P, so that P A = L U. */
    public double[][] permutation() {
        int n = lu.length;
        double[][] p = new double[n][n];
        for (int i = 0; i < n; i++) p[i][perm[i]] = 1;
        return p;
    }

    public double determinant() {
        double det = (swaps % 2 == 0) ? 1 : -1;
        for (int i = 0; i < lu.length; i++) det *= lu[i][i];
        return det;
    }

    public double[] solve(double[] b) {
        if (singular) throw new ArithmeticException("matrix is singular");
        int n = lu.length;
        double[] y = new double[n];
        for (int i = 0; i < n; i++) {                              // L y = P b
            double s = b[perm[i]];
            for (int j = 0; j < i; j++) s -= lu[i][j] * y[j];
            y[i] = s;
        }
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {                         // U x = y
            double s = y[i];
            for (int j = i + 1; j < n; j++) s -= lu[i][j] * x[j];
            x[i] = s / lu[i][i];
        }
        return x;
    }

    public double[][] inverse() {
        int n = lu.length;
        double[][] inv = new double[n][n];
        for (int c = 0; c < n; c++) {
            double[] e = new double[n];
            e[c] = 1;
            double[] col = solve(e);
            for (int r = 0; r < n; r++) inv[r][c] = col[r];
        }
        return inv;
    }

    public static double[][] multiply(double[][] a, double[][] b) {
        int n = a.length, m = b[0].length, k = b.length;
        double[][] c = new double[n][m];
        for (int i = 0; i < n; i++) for (int t = 0; t < k; t++) for (int j = 0; j < m; j++) c[i][j] += a[i][t] * b[t][j];
        return c;
    }

    public static void main(String[] args) {
        System.out.println("--- LU DECOMPOSITION DEMO ---");
        double[][] a = {{2, 1, 1}, {4, -6, 0}, {-2, 7, 2}};
        LUDecomposition lu = new LUDecomposition(a);
        System.out.println("P = " + Arrays.deepToString(lu.permutation()));
        System.out.println("L = " + Arrays.deepToString(lu.lower()));
        System.out.println("U = " + Arrays.deepToString(lu.upper()));
        System.out.println("P*A == L*U ? " + Arrays.deepEquals(round(multiply(lu.permutation(), a)), round(multiply(lu.lower(), lu.upper()))));
        System.out.println("det(A) = " + lu.determinant()); // -16

        // one factorisation, several right-hand sides
        for (double[] b : new double[][]{{5, -2, 9}, {1, 0, 0}}) {
            System.out.println("solve A x = " + Arrays.toString(b) + " -> x = " + Arrays.toString(round(lu.solve(b))));
        }
        // first: x = (1, 1, 2)
        System.out.println("Singular matrix detected? " + new LUDecomposition(new double[][]{{1, 2}, {2, 4}}).isSingular());
    }

    private static double[][] round(double[][] m) { double[][] r = new double[m.length][]; for (int i = 0; i < m.length; i++) r[i] = round(m[i]); return r; }
    private static double[] round(double[] v) { double[] r = new double[v.length]; for (int i = 0; i < v.length; i++) r[i] = Math.round(v[i] * 1e9) / 1e9 + 0.0; return r; }
}
