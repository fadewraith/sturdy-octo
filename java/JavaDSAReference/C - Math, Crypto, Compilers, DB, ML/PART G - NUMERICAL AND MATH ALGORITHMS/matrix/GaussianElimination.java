package math.matrix;

/**
 * WHAT IT IS: Gaussian Elimination. Algorithm to solve systems of linear equations.
 * STRATEGY: Forward elimination to get upper triangular matrix, then backward substitution.
 * TIME/SPACE COMPLEXITY: Time: O(n^3), Space: O(n^2).
 * REAL-WORLD ANALOGY / USE CASE: Solving linear equations.
 * WHEN TO USE / COMBINATION: Systems with unique solutions.
 * 
 * PSEUDOCODE:
 * for k = 0 to n:
 *   find pivot row
 *   swap pivot
 *   eliminate below
 * back substitute
 */
public class GaussianElimination {
    public static double[] solve(double[][] A, double[] B) {
        int n = B.length;
        for (int p = 0; p < n; p++) {
            // Find pivot
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }
            
            // Swap rows in A and B
            double[] tempA = A[p]; A[p] = A[max]; A[max] = tempA;
            double tempB = B[p]; B[p] = B[max]; B[max] = tempB;
            
            // Pivot within A and B
            for (int i = p + 1; i < n; i++) {
                double alpha = A[i][p] / A[p][p];
                B[i] -= alpha * B[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }
        
        // Back substitution
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (B[i] - sum) / A[i][i];
        }
        return x;
    }

    public static void main(String[] args) {
        double[][] A = {{2, 1, -1}, {-3, -1, 2}, {-2, 1, 2}};
        double[] B = {8, -11, -3};
        double[] x = solve(A, B);
        System.out.println("Solution: " + java.util.Arrays.toString(x));
    }
}
