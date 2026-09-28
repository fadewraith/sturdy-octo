/**
 * WHAT IT IS: Iterative Deepening A* (IDA*) is a variant of A* that uses depth-first search with a dynamically increasing depth limit (threshold) based on the f-score (g + h).
 * STRATEGY: Perform DFS, but cut off branches where f-score exceeds the current threshold. If goal is not found, update the threshold to the minimum f-score that exceeded the previous threshold and repeat.
 * TIME/SPACE COMPLEXITY: Time: O(b^d), Space: O(d) - memory footprint is significantly smaller than A* since it doesn't need an Open/Closed list.
 * REAL-WORLD ANALOGY / USE CASE: Solving puzzle games like the 15-puzzle or Rubik's cube where standard A* would run out of memory.
 * WHEN TO USE / COMBINATION: When memory is heavily constrained and the search space is massive, making standard A* impossible due to PriorityQueue size.
 * 
 * PSEUDOCODE:
 * threshold = heuristic(start)
 * while true:
 *   temp = search(start, 0, threshold)
 *   if temp == FOUND return path
 *   if temp == INFINITY return NOT_FOUND
 *   threshold = temp
 */
package algorithms.graph.advanced;

// These aren't separate algorithms so much as OPTIMIZATIONS/ADAPTATIONS of Dijkstra's and A* for specific real-world constraints (memory, dynamic changes, precomputation budget, grid structure).

import java.util.*;

public class IDAStar {

    static class Edge {
        int to; double weight;
        Edge(int to, double weight) { this.to = to; this.weight = weight; }
    }

    private static final double FOUND = -1.0;

    public static double idaStar(int numNodes, List<List<Edge>> adj, double[] heuristics, int start, int goal) {
        if (start == goal) return 0.0;
        
        double threshold = heuristics[start];
        Set<Integer> path = new HashSet<>();
        path.add(start);

        while (true) {
            double temp = search(start, 0, threshold, path, adj, heuristics, goal);
            if (temp == FOUND) {
                return threshold; // For simplicity, we just return the total cost (which aligns with threshold when found)
            }
            if (temp == Double.POSITIVE_INFINITY) {
                return -1; // Not found
            }
            threshold = temp;
        }
    }

    private static double search(int node, double g, double threshold, Set<Integer> path, List<List<Edge>> adj, double[] heuristics, int goal) {
        double f = g + heuristics[node];
        if (f > threshold) return f;
        if (node == goal) return FOUND;

        double min = Double.POSITIVE_INFINITY;
        for (Edge e : adj.get(node)) {
            if (!path.contains(e.to)) {
                path.add(e.to);
                double temp = search(e.to, g + e.weight, threshold, path, adj, heuristics, goal);
                if (temp == FOUND) return FOUND;
                if (temp < min) min = temp;
                path.remove(e.to);
            }
        }
        return min;
    }

    public static void main(String[] args) {
        System.out.println("--- IDA* Tests ---");
        int numNodes = 4;
        List<List<Edge>> adj = new ArrayList<>();
        for (int i=0; i<numNodes; i++) adj.add(new ArrayList<>());
        
        adj.get(0).add(new Edge(1, 1));
        adj.get(1).add(new Edge(2, 2));
        adj.get(2).add(new Edge(3, 1));
        adj.get(0).add(new Edge(3, 5)); // Direct but expensive path
        
        double[] heuristics = {3.0, 2.0, 1.0, 0.0}; // Perfect heuristic for optimal path
        
        System.out.println("Test 1 (Optimal Path Cost): 0 to 3 -> " + idaStar(numNodes, adj, heuristics, 0, 3)); // Expected: 4.0
        
        double[] badHeuristics = {0, 0, 0, 0}; // Behaves like iterative deepening uniform cost search
        System.out.println("Test 2 (Zero Heuristic): 0 to 3 -> " + idaStar(numNodes, adj, badHeuristics, 0, 3)); // Expected: 4.0
        
        System.out.println("Test 3 (Start == Goal): 2 to 2 -> " + idaStar(numNodes, adj, heuristics, 2, 2)); // Expected: 0.0
    }
}
