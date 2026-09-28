// These are simplified, from-scratch educational implementations to understand the underlying math. Real-world ML work uses libraries (scikit-learn, TensorFlow, etc.) — these exist here purely for conceptual completeness, not as production advice.
/**
 * WHAT IT IS: Unsupervised learning algorithm that partitions N observations into K clusters.
 * STRATEGY: Initialize K centroids. Repeat: assign each point to nearest centroid, update centroids to mean of assigned points. Stop when centroids don't change.
 * TIME/SPACE COMPLEXITY: Time: O(I * K * N * d) where I=iterations, K=clusters, N=points, d=dimensions. Space: O(K * d + N).
 * REAL-WORLD ANALOGY / USE CASE: Customer segmentation, color quantization in images.
 * WHEN TO USE / COMBINATION: When exploring unlabeled data to find inherent groupings. Often combined with PCA for dimensionality reduction first.
 * 
 * PSEUDOCODE:
 * Initialize K centroids randomly
 * Loop until convergence:
 *   For each point: find nearest centroid
 *   For each cluster: compute new centroid as mean of points
 */
package ml.kmeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KMeansClustering {

    public static void main(String[] args) {
        System.out.println("--- K-Means Clustering ---");
        List<double[]> data = Arrays.asList(
            new double[]{1.0, 1.0},
            new double[]{1.5, 2.0},
            new double[]{3.0, 4.0},
            new double[]{5.0, 7.0},
            new double[]{3.5, 5.0},
            new double[]{4.5, 5.0},
            new double[]{3.5, 4.5}
        );
        
        int k = 2;
        System.out.println("Clustering into " + k + " clusters:");
        List<Integer> assignments = kMeans(data, k, 100);
        
        for (int i = 0; i < data.size(); i++) {
            System.out.println("Point " + Arrays.toString(data.get(i)) + " -> Cluster " + assignments.get(i));
        }
    }

    public static List<Integer> kMeans(List<double[]> data, int k, int maxIterations) {
        if (data == null || data.isEmpty()) return new ArrayList<>();
        int dimensions = data.get(0).length;
        
        // 1. Centroid initialization (simple approach: pick first K points)
        List<double[]> centroids = new ArrayList<>();
        for (int i = 0; i < k && i < data.size(); i++) {
            centroids.add(Arrays.copyOf(data.get(i), dimensions));
        }
        
        List<Integer> assignments = new ArrayList<>(Collections.nCopies(data.size(), 0));
        
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            boolean changed = false;
            
            // 2. Assignment step
            for (int i = 0; i < data.size(); i++) {
                double[] point = data.get(i);
                int nearest = 0;
                double minDistance = Double.MAX_VALUE;
                
                for (int j = 0; j < k; j++) {
                    double dist = distance(point, centroids.get(j));
                    if (dist < minDistance) {
                        minDistance = dist;
                        nearest = j;
                    }
                }
                
                if (assignments.get(i) != nearest) {
                    changed = true;
                    assignments.set(i, nearest);
                }
            }
            
            // 4. Convergence check
            if (!changed) break;
            
            // 3. Update step
            int[] counts = new int[k];
            double[][] newCentroids = new double[k][dimensions];
            
            for (int i = 0; i < data.size(); i++) {
                int cluster = assignments.get(i);
                counts[cluster]++;
                for (int d = 0; d < dimensions; d++) {
                    newCentroids[cluster][d] += data.get(i)[d];
                }
            }
            
            for (int j = 0; j < k; j++) {
                if (counts[j] > 0) {
                    for (int d = 0; d < dimensions; d++) {
                        centroids.get(j)[d] = newCentroids[j][d] / counts[j];
                    }
                }
            }
        }
        
        return assignments;
    }
    
    private static double distance(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += Math.pow(a[i] - b[i], 2);
        }
        return Math.sqrt(sum);
    }
}

class Collections {
    public static <T> List<T> nCopies(int n, T o) {
        List<T> list = new ArrayList<>(n);
        for(int i=0; i<n; i++) list.add(o);
        return list;
    }
}
