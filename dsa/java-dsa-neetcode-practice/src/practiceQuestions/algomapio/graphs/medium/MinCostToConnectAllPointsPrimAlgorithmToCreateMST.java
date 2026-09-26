package practiceQuestions.algomapio.graphs.medium;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MinCostToConnectAllPointsPrimAlgorithmToCreateMST {
    /**
     * Step-by-Step Thought Process
     * Understand the problem: Find the minimum cost to connect all points in a 2D plane, where the cost between two points is their Manhattan distance, forming a minimum spanning tree (MST).
     * Initialize a min-heap with a tuple (0, 0), representing a distance of 0 to the first point (index 0).
     * Create a set to track visited points and a variable total_cost to accumulate the MST cost.
     * While the number of visited points is less than the total number of points:
     * Pop the smallest distance and its corresponding point index i from the heap.
     * If point i is already visited, skip to the next iteration.
     * Add point i to the visited set and add the distance to total_cost.
     * For each unvisited point j, compute the Manhattan distance from point i to point j as |xi - xj| + |yi - yj|.
     * Push the tuple (distance, j) to the heap for each unvisited point j.
     * Return total_cost as the minimum cost to connect all points.
     * */

    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        int totalCost = 0;
        boolean[] visited = new boolean[n];
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        minHeap.offer(new int[]{0, 0}); // cost, pointIndex

        while (!minHeap.isEmpty()) {
            int[] edge = minHeap.poll();
            int cost = edge[0];
            int point = edge[1];

            if (visited[point]) continue;

            visited[point] = true;
            totalCost += cost;

            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    int newCost = Math.abs(points[point][0] - points[i][0]) + Math.abs(points[point][1] - points[i][1]);
                    minHeap.offer(new int[]{newCost, i});
                }
            }
        }

        return totalCost;
    }
}

/**
 * Detailed Explanation
 * Understanding the Problem: Min Cost to Connect All Points
 * The Min Cost to Connect All Points problem is a classic graph problem where we are given n points in a 2D plane, and we must connect all of them with the minimum total cost. The cost to connect two points is defined by their Manhattan distance, calculated as |x1 - x2| + |y1 - y2|. The challenge is to connect all points such that there is exactly one simple path between any two points—i.e., construct a Minimum Spanning Tree (MST).
 *
 * Optimal Solution Strategy: Prim’s Algorithm
 * To solve this problem efficiently, we use Prim's Algorithm, which is ideal for building an MST when starting from any arbitrary node and growing the tree by always choosing the edge with the lowest cost that connects to an unvisited node. This is well-suited here since we are not given an explicit edge list—we dynamically calculate distances as needed.
 *
 * We initialize a min-heap to always select the lowest-cost edge and a visited set to avoid revisiting points. We start from the first point with a cost of 0 and add it to the heap as the entry point.
 *
 * During each iteration, we pop the smallest entry from the heap. If the point has already been visited, we skip it. Otherwise, we mark it as visited and add the connection cost to our running total. For that point, we calculate the Manhattan distance to all other unvisited points and push those distances into the heap for future consideration. This greedy strategy ensures that at each step, we are only adding the cheapest possible connection to expand our MST.
 *
 * Time and Space Complexity Analysis
 * The time complexity is O(n^2 log n) because in the worst case, for each of the n nodes, we may push up to n distances into the heap, and each heap operation takes log n time. The space complexity is O(n^2) to hold the distances and heap entries in the worst case, and O(n) for the visited set.
 *
 * Why Prim’s Algorithm Works Best
 * Unlike Kruskal’s algorithm, which requires sorting all possible edges (leading to a much higher time complexity of O(n^2 log n) just to sort), Prim’s algorithm incrementally builds the MST and dynamically generates needed edges on-the-fly. This makes it significantly more efficient when we are not explicitly given edges, especially when the number of potential connections is large.
 *
 * Edge Cases and Implementation Tips
 * Be sure to handle edge cases such as when there's only one point—then the total cost is zero. Also, ensure each point is only visited once, or the MST will not be minimal. Using a heap (priority queue) ensures the optimal next edge is always chosen without rescanning all points.
 * */