// These are simplified, from-scratch educational implementations to understand the underlying math. Real-world ML work uses libraries (scikit-learn, TensorFlow, etc.) — these exist here purely for conceptual completeness, not as production advice.
/**
 * WHAT IT IS: Supervised learning algorithm for classification or regression based on proximity.
 * STRATEGY: To classify a new point, calculate distance to all training points, find K nearest, and take majority vote (for classification).
 * TIME/SPACE COMPLEXITY: Time: O(N * d) per query where N=training points, d=dimensions. Space: O(N * d) to store training data.
 * REAL-WORLD ANALOGY / USE CASE: Recommendation systems, pattern recognition.
 * WHEN TO USE / COMBINATION: Small to medium datasets where instance-based learning is appropriate.
 * 
 * PSEUDOCODE:
 * For a given query point:
 *   Calculate distances to all training points
 *   Sort distances
 *   Take top K nearest
 *   Return majority class label
 */
package ml.knn;

import java.util.*;

public class KNearestNeighbors {

    static class DataPoint {
        double[] features;
        int label;

        DataPoint(double[] features, int label) {
            this.features = features;
            this.label = label;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- K-Nearest Neighbors ---");
        List<DataPoint> trainingData = Arrays.asList(
            new DataPoint(new double[]{1.0, 1.0}, 0),
            new DataPoint(new double[]{2.0, 1.0}, 0),
            new DataPoint(new double[]{1.0, 2.0}, 0),
            new DataPoint(new double[]{8.0, 8.0}, 1),
            new DataPoint(new double[]{9.0, 8.0}, 1),
            new DataPoint(new double[]{8.0, 9.0}, 1)
        );

        double[] query = new double[]{2.0, 2.0};
        int k = 3;
        
        System.out.println("Query point: " + Arrays.toString(query));
        int predicted = predict(trainingData, query, k);
        System.out.println("Predicted class: " + predicted);
        
        double[] query2 = new double[]{7.0, 7.0};
        System.out.println("Query point: " + Arrays.toString(query2));
        int predicted2 = predict(trainingData, query2, k);
        System.out.println("Predicted class: " + predicted2);
    }

    public static int predict(List<DataPoint> trainingData, double[] query, int k) {
        List<DistanceRecord> distances = new ArrayList<>();
        
        for (DataPoint pt : trainingData) {
            double dist = distance(pt.features, query);
            distances.add(new DistanceRecord(dist, pt.label));
        }
        
        distances.sort(Comparator.comparingDouble(r -> r.distance));
        
        Map<Integer, Integer> classVotes = new HashMap<>();
        for (int i = 0; i < k && i < distances.size(); i++) {
            int label = distances.get(i).label;
            classVotes.put(label, classVotes.getOrDefault(label, 0) + 1);
        }
        
        int majorityClass = -1;
        int maxVotes = -1;
        for (Map.Entry<Integer, Integer> entry : classVotes.entrySet()) {
            if (entry.getValue() > maxVotes) {
                maxVotes = entry.getValue();
                majorityClass = entry.getKey();
            }
        }
        
        return majorityClass;
    }
    
    private static double distance(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += Math.pow(a[i] - b[i], 2);
        }
        return Math.sqrt(sum);
    }
    
    static class DistanceRecord {
        double distance;
        int label;
        
        DistanceRecord(double distance, int label) {
            this.distance = distance;
            this.label = label;
        }
    }
}
