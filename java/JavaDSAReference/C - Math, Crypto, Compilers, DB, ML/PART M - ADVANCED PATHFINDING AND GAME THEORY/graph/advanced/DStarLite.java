/**
 * WHAT IT IS: D* Lite is an incremental heuristic search algorithm. It builds on A* but handles dynamic graphs where edge costs change during path execution.
 * STRATEGY: Searches backwards from the goal to the start. If edge costs change (e.g., an obstacle appears), it only updates the affected nodes rather than recalculating the whole path.
 * TIME/SPACE COMPLEXITY: Time: O(V log V) for initial search, but updates are much faster. Space: O(V).
 * REAL-WORLD ANALOGY / USE CASE: Mars rovers or autonomous robots moving through unknown terrain where obstacles are discovered on the fly.
 * WHEN TO USE / COMBINATION: When the graph changes dynamically during traversal and full recalculation using A* would be too slow.
 * 
 * PSEUDOCODE:
 * initialize rhs and g values to infinity
 * rhs(goal) = 0
 * put goal in priority queue
 * compute_shortest_path()
 * while start != goal:
 *   if edge costs change:
 *     update rhs values of affected nodes
 *     update priority queue
 *     compute_shortest_path()
 *   move to best neighbor
 */
package algorithms.graph.advanced;

// These aren't separate algorithms so much as OPTIMIZATIONS/ADAPTATIONS of Dijkstra's and A* for specific real-world constraints (memory, dynamic changes, precomputation budget, grid structure).

import java.util.*;

public class DStarLite {

    // Conceptual simplified implementation demonstrating the backward search and RHS values.
    // A complete robust implementation involves complex key updates and tie-breaking.
    
    static final double INF = Double.POSITIVE_INFINITY;

    static class Node {
        int id;
        double g = INF, rhs = INF;
        Node(int id) { this.id = id; }
    }

    public static void computeShortestPathMockup(Node[] nodes, int[][] adjCost, int start, int goal) {
        // Mocking the rhs update and backward propagation for demonstration
        nodes[goal].rhs = 0;
        boolean changed = true;
        
        while (changed) {
            changed = false;
            for (int u = 0; u < nodes.length; u++) {
                if (u == goal) continue;
                double minRhs = INF;
                for (int v = 0; v < nodes.length; v++) {
                    if (adjCost[u][v] != -1) {
                        minRhs = Math.min(minRhs, adjCost[u][v] + nodes[v].g);
                    }
                }
                if (nodes[u].rhs != minRhs) {
                    nodes[u].rhs = minRhs;
                    changed = true;
                }
                
                // Make g locally consistent
                if (nodes[u].g != nodes[u].rhs) {
                    nodes[u].g = nodes[u].rhs;
                    changed = true;
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- D* Lite (Conceptual) Tests ---");
        int numNodes = 4;
        Node[] nodes = new Node[numNodes];
        for (int i=0; i<numNodes; i++) nodes[i] = new Node(i);
        
        int[][] adjCost = {
            {-1, 1, 4, -1},
            {1, -1, 1, 5},
            {4, 1, -1, 1},
            {-1, 5, 1, -1}
        }; // -1 means no edge
        
        int start = 0, goal = 3;
        nodes[goal].rhs = 0;
        nodes[goal].g = 0; // Initialize goal
        
        computeShortestPathMockup(nodes, adjCost, start, goal);
        
        System.out.println("Test 1 (Initial Setup): Cost from start (0) to goal (3) is: " + nodes[start].g); // Expected: 3.0 (0->1->2->3)
        
        // Edge changes dynamically! (e.g. edge from 2 to 3 gets blocked)
        System.out.println("Obstacle detected! Edge 2->3 cost becomes infinity.");
        adjCost[2][3] = -1; 
        
        // In real D* Lite, we would only update affected nodes in PQ. Here we re-run the convergence.
        for(Node n : nodes) if(n.id != goal) { n.g = INF; n.rhs = INF; } // Reset for mock
        computeShortestPathMockup(nodes, adjCost, start, goal);
        
        System.out.println("Test 2 (After dynamic update): Cost from start (0) to goal (3) is: " + nodes[start].g); // Expected: 6.0 (0->1->3)
        
        System.out.println("Test 3 (Unreachable): " + (nodes[start].g == INF ? "Infinity" : nodes[start].g));
    }
}
