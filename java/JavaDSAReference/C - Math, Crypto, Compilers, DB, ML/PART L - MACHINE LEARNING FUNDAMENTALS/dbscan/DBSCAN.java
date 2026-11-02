package ml.dbscan;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DBSCAN (Density-Based Spatial Clustering of Applications with Noise)
 *
 * WHAT IT IS:
 * Clusters = regions where points are DENSELY packed, separated by sparse regions.
 * Unlike k-means (KMeansClustering.java) it needs NO number of clusters, finds
 * clusters of ANY SHAPE (rings, moons, snakes) and labels outliers as NOISE.
 *
 * WHEN TO USE THIS:
 * - Geospatial hotspots (crime, GPS stops, taxi pickups), anomaly detection,
 *   clustering with unknown k or irregular shapes. Weak when densities vary a lot
 *   (HDBSCAN fixes that).
 *
 * DEFINITIONS (parameters eps and minPts):
 * - neighbours(p) = points within distance eps of p (including p itself).
 * - CORE point: has at least minPts neighbours.
 * - BORDER point: not core, but within eps of a core point.
 * - NOISE: everything else (label -1).
 * A cluster = all core points connected through chains of core points within eps,
 * plus the border points attached to them.
 *
 * STRATEGY:
 * For each unvisited point p: if p is not core, mark it noise (for now). If it is
 * core, start a NEW cluster and BFS: every neighbour joins the cluster (noise points
 * become border points); neighbours that are themselves core are expanded further.
 * A border point reachable from two clusters keeps the first one (the only
 * order-dependent part of DBSCAN).
 *
 * COMPLEXITY:
 * O(n^2) with the brute-force neighbour search used here; O(n log n) on average
 * with a spatial index (KD-tree, R-tree, grid). Space: O(n).
 */
public class DBSCAN {

    public static final int NOISE = -1;
    private static final int UNVISITED = -2;

    /** Returns a cluster id (0, 1, 2, ...) per point, or NOISE (-1). */
    public static int[] cluster(double[][] points, double eps, int minPts) {
        int n = points.length;
        int[] label = new int[n];
        Arrays.fill(label, UNVISITED);
        int clusterId = 0;
        for (int p = 0; p < n; p++) {
            if (label[p] != UNVISITED) continue;
            List<Integer> nb = neighbours(points, p, eps);
            if (nb.size() < minPts) { label[p] = NOISE; continue; } // may become a border point later
            label[p] = clusterId;
            ArrayDeque<Integer> queue = new ArrayDeque<>(nb);
            while (!queue.isEmpty()) {
                int q = queue.poll();
                if (label[q] == NOISE) label[q] = clusterId;          // noise -> border point
                if (label[q] != UNVISITED) continue;
                label[q] = clusterId;
                List<Integer> qn = neighbours(points, q, eps);
                if (qn.size() >= minPts) queue.addAll(qn);            // q is core: keep expanding
            }
            clusterId++;
        }
        return label;
    }

    static List<Integer> neighbours(double[][] pts, int p, double eps) {
        List<Integer> out = new ArrayList<>();
        double eps2 = eps * eps;
        for (int i = 0; i < pts.length; i++) {
            double d2 = 0;
            for (int j = 0; j < pts[p].length; j++) { double diff = pts[p][j] - pts[i][j]; d2 += diff * diff; }
            if (d2 <= eps2) out.add(i);
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println("--- DBSCAN DEMO ---");
        java.util.Random rng = new java.util.Random(8);
        List<double[]> pts = new ArrayList<>();
        // a ring of radius 10 around a dense blob at the centre: k-means can't separate these
        for (int i = 0; i < 300; i++) { double a = rng.nextDouble() * 2 * Math.PI; pts.add(new double[]{10 * Math.cos(a) + rng.nextGaussian() * 0.3, 10 * Math.sin(a) + rng.nextGaussian() * 0.3}); }
        for (int i = 0; i < 60; i++) pts.add(new double[]{rng.nextGaussian() * 0.8, rng.nextGaussian() * 0.8});
        pts.add(new double[]{20, 20});            // two outliers
        pts.add(new double[]{-18, 5});
        double[][] data = pts.toArray(new double[0][]);

        int[] labels = cluster(data, 2.0, 4);
        int clusters = Arrays.stream(labels).max().getAsInt() + 1;
        long noise = Arrays.stream(labels).filter(l -> l == NOISE).count();
        System.out.println("Found " + clusters + " clusters and " + noise + " noise points");
        System.out.println("Ring points share one cluster: " + Arrays.stream(labels, 0, 300).distinct().count() + " distinct label(s)");
        System.out.println("Blob points share one cluster: " + Arrays.stream(labels, 300, 360).distinct().count() + " distinct label(s)");
        System.out.println("Outliers labelled: " + labels[360] + ", " + labels[361]); // -1, -1
    }
}
