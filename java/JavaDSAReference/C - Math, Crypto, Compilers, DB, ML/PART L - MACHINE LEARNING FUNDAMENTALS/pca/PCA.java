package ml.pca;

import java.util.Arrays;

/**
 * PRINCIPAL COMPONENT ANALYSIS (PCA) via the Jacobi eigenvalue algorithm
 *
 * WHAT IT IS:
 * Finds the directions (PRINCIPAL COMPONENTS) along which the data varies most.
 * Projecting onto the top k components reduces d dimensions to k while keeping as
 * much of the variance (information) as possible.
 *
 * WHEN TO USE THIS:
 * - Dimensionality reduction before clustering/classification (KMeansClustering.java),
 *   visualising high-dimensional data in 2D, compressing features, removing noise,
 *   Eigenfaces (face recognition), decorrelating features.
 *
 * STRATEGY:
 * 1. CENTER the data: subtract each feature's mean.
 * 2. COVARIANCE matrix C = X^T X / (n - 1)   (d x d, symmetric).
 * 3. EIGENDECOMPOSITION of C: eigenvectors = principal directions, eigenvalues =
 *    variance along each. Sort by eigenvalue, largest first.
 * 4. PROJECT: scores = X_centered . V_k.
 * "Explained variance ratio" of component i = lambda_i / sum of all lambdas.
 *
 * EIGENVALUES WITH THE JACOBI METHOD (for symmetric matrices):
 * Repeatedly pick the largest off-diagonal entry A[p][q] and apply a plane
 * ROTATION that zeroes it. Each rotation keeps the eigenvalues; the matrix
 * converges to diagonal (the eigenvalues), and the product of all rotations holds
 * the eigenvectors as columns. Simple and very accurate for small d. (Libraries use
 * SVD of X directly, which is more stable for large or ill-conditioned data.)
 *
 * COMPLEXITY:
 * Covariance: O(n d^2). Jacobi: about O(d^3) per sweep, a handful of sweeps.
 * Projection: O(n d k).
 */
public class PCA {

    private double[] mean;
    private double[] eigenvalues;     // sorted descending
    private double[][] components;    // components[i] = i-th unit eigenvector (length d)

    public PCA fit(double[][] data) {
        int n = data.length, d = data[0].length;
        if (n < 2) throw new IllegalArgumentException("need at least 2 samples");
        mean = new double[d];
        for (double[] row : data) for (int j = 0; j < d; j++) mean[j] += row[j] / n;
        double[][] cov = new double[d][d];
        for (double[] row : data)
            for (int i = 0; i < d; i++)
                for (int j = 0; j < d; j++) cov[i][j] += (row[i] - mean[i]) * (row[j] - mean[j]) / (n - 1);

        double[][][] eig = jacobi(cov);
        double[] values = eig[0][0];
        double[][] vectors = eig[1];  // columns are eigenvectors
        Integer[] order = new Integer[d];
        for (int i = 0; i < d; i++) order[i] = i;
        Arrays.sort(order, (a, b) -> Double.compare(values[b], values[a]));
        eigenvalues = new double[d];
        components = new double[d][d];
        for (int r = 0; r < d; r++) {
            eigenvalues[r] = values[order[r]];
            for (int i = 0; i < d; i++) components[r][i] = vectors[i][order[r]];
            // fix the sign so results are deterministic: largest |entry| positive
            int big = 0;
            for (int i = 1; i < d; i++) if (Math.abs(components[r][i]) > Math.abs(components[r][big])) big = i;
            if (components[r][big] < 0) for (int i = 0; i < d; i++) components[r][i] = -components[r][i];
        }
        return this;
    }

    /** Eigen-decomposition of a symmetric matrix: {{eigenvalues}}, eigenvector matrix (columns). */
    public static double[][][] jacobi(double[][] matrix) {
        int d = matrix.length;
        double[][] a = new double[d][];
        for (int i = 0; i < d; i++) a[i] = matrix[i].clone();
        double[][] v = new double[d][d];
        for (int i = 0; i < d; i++) v[i][i] = 1;

        for (int sweep = 0; sweep < 100 * d * d; sweep++) {
            int p = 0, q = 1;
            double max = 0;
            for (int i = 0; i < d; i++)                       // largest off-diagonal entry
                for (int j = i + 1; j < d; j++)
                    if (Math.abs(a[i][j]) > max) { max = Math.abs(a[i][j]); p = i; q = j; }
            if (max < 1e-12) break;

            // rotation angle that makes a[p][q] zero
            double theta = (a[q][q] - a[p][p]) / (2 * a[p][q]);
            double t = Math.signum(theta) / (Math.abs(theta) + Math.sqrt(theta * theta + 1));
            if (theta == 0) t = 1;
            double c = 1 / Math.sqrt(t * t + 1), s = t * c;

            for (int k = 0; k < d; k++) {                     // A = R^T A R (columns, then rows)
                double akp = a[k][p], akq = a[k][q];
                a[k][p] = c * akp - s * akq;
                a[k][q] = s * akp + c * akq;
            }
            for (int k = 0; k < d; k++) {
                double apk = a[p][k], aqk = a[q][k];
                a[p][k] = c * apk - s * aqk;
                a[q][k] = s * apk + c * aqk;
            }
            for (int k = 0; k < d; k++) {                     // accumulate eigenvectors
                double vkp = v[k][p], vkq = v[k][q];
                v[k][p] = c * vkp - s * vkq;
                v[k][q] = s * vkp + c * vkq;
            }
        }
        double[] values = new double[d];
        for (int i = 0; i < d; i++) values[i] = a[i][i];
        return new double[][][]{{values}, v};
    }

    /** Projects samples onto the first k components. */
    public double[][] transform(double[][] data, int k) {
        double[][] out = new double[data.length][k];
        for (int s = 0; s < data.length; s++)
            for (int c = 0; c < k; c++)
                for (int j = 0; j < mean.length; j++) out[s][c] += (data[s][j] - mean[j]) * components[c][j];
        return out;
    }

    /** Maps k-dimensional scores back to the original space (lossy if k < d). */
    public double[][] inverseTransform(double[][] scores) {
        double[][] out = new double[scores.length][mean.length];
        for (int s = 0; s < scores.length; s++)
            for (int j = 0; j < mean.length; j++) {
                out[s][j] = mean[j];
                for (int c = 0; c < scores[s].length; c++) out[s][j] += scores[s][c] * components[c][j];
            }
        return out;
    }

    public double[] eigenvalues() { return eigenvalues.clone(); }
    public double[][] components() { return components; }

    public double[] explainedVarianceRatio() {
        double total = 0;
        for (double e : eigenvalues) total += e;
        double[] r = new double[eigenvalues.length];
        for (int i = 0; i < r.length; i++) r[i] = eigenvalues[i] / total;
        return r;
    }

    public static void main(String[] args) {
        System.out.println("--- PCA DEMO ---");
        // 3D points lying almost on the line t * (1, 2, 0.5) plus small noise
        java.util.Random rng = new java.util.Random(4);
        double[][] data = new double[200][];
        for (int i = 0; i < data.length; i++) {
            double t = rng.nextGaussian() * 5;
            data[i] = new double[]{t + rng.nextGaussian() * 0.3, 2 * t + rng.nextGaussian() * 0.3, 0.5 * t + rng.nextGaussian() * 0.3};
        }
        PCA pca = new PCA().fit(data);
        System.out.printf("Explained variance ratio: %s%n", Arrays.toString(Arrays.stream(pca.explainedVarianceRatio()).map(v -> Math.round(v * 1000) / 1000.0).toArray()));
        double[] pc1 = pca.components()[0];
        System.out.printf("First component: (%.3f, %.3f, %.3f) ~ (1, 2, 0.5) / |(1, 2, 0.5)| = (0.436, 0.873, 0.218)%n", pc1[0], pc1[1], pc1[2]);

        double[][] reduced = pca.transform(data, 1);
        double[][] restored = pca.inverseTransform(reduced);
        double err = 0;
        for (int i = 0; i < data.length; i++) for (int j = 0; j < 3; j++) err += Math.pow(data[i][j] - restored[i][j], 2);
        System.out.printf("3D -> 1D -> 3D mean squared reconstruction error: %.4f (only the noise is lost)%n", err / data.length);
    }
}
