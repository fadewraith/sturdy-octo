package practiceQuestions.algomapio.heaps.medium;

import java.util.PriorityQueue;

public class KClosestPointsToOrigin {

    /**
     *  Step-by-Step Thought Process
     * Understand the problem: Find the k points closest to the origin (0,0) from a list of points, using Euclidean distance.
     * Define a function to compute the squared Euclidean distance (x² + y²) for a point (x, y).
     * Initialize an empty max heap to store up to k points, storing tuples of (-distance, x, y).
     * For each point, compute its distance; if the heap has fewer than k points, push the point; otherwise, push and pop to keep the k closest.
     * Extract the x, y coordinates from the heap and return them as the result.
     * */

    public int[][] kClosest(int[][] points, int k) {
        // Custom comparator to sort based on distance
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> {
            int distA = a[0] * a[0] + a[1] * a[1];
            int distB = b[0] * b[0] + b[1] * b[1];
            return Integer.compare(distB, distA); // Max-heap
        });

        for (int[] point : points) {
            maxHeap.add(point);
            if (maxHeap.size() > k) {
                maxHeap.poll(); // Remove the farthest point
            }
        }

        // Convert the heap to an array
        int[][] result = new int[k][2];
        for (int i = 0; i < k; i++) {
            result[i] = maxHeap.poll();
        }

        return result;
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: K Closest Points to Origin
 * The “K Closest Points to Origin” problem asks us to find the k points nearest to the origin (0,0) from a given list of 2D points. The distance is measured using the Euclidean formula, but since we only care about comparing distances (not actual values), we can safely use the squared distance formula to avoid unnecessary square root calculations.
 *
 * This type of problem is common in computational geometry, spatial indexing, and real-world systems like nearest-neighbor searches in maps or recommendation engines.
 *
 * Brute-Force Strategy with Max Heap
 * To solve the problem efficiently, we use a max heap to keep track of the k closest points. As we iterate through each point, we calculate its squared distance from the origin. We maintain a heap of size at most k containing the closest points seen so far.
 *
 * To simulate a max heap using Python’s built-in heapq module (which is a min heap), we store the negative of the squared distance. If the heap grows beyond size k, we remove the point with the largest distance. At the end, the heap will contain exactly the k points closest to the origin.
 *
 * Why Squared Distance?
 * The standard Euclidean distance formula is √(x² + y²). However, since square roots are monotonic, the relative order of distances is preserved even without computing the square root. That’s why we use the x² + y² value instead — it's simpler and faster.
 *
 * Time and Space Complexity
 * The time complexity of this heap-based approach is O(n log k), where n is the number of points and k is the number of closest points we want. Each insertion into the heap takes O(log k), and we do this for all points.
 *
 * The space complexity is O(k) for the heap that stores the closest points.
 *
 * Alternative Approach: Sorting
 * An alternate solution is to sort the list of points by their squared distances and return the first k. This takes O(n log n) time due to the sorting step, and O(n) space if not sorting in place. While this approach is simpler, the heap-based solution is more efficient when k is much smaller than n.
 * */