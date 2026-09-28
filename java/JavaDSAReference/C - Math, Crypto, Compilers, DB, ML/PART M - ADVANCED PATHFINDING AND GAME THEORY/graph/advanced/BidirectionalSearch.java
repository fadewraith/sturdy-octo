/**
 * WHAT IT IS: Bidirectional Search is a graph search algorithm that finds the shortest path by running two simultaneous searches: one forward from the initial state and the other backward from the goal.
 * STRATEGY: Run BFS from both the start and the goal. The search stops when the two search frontiers intersect.
 * TIME/SPACE COMPLEXITY: Time: O(b^(d/2)), Space: O(b^(d/2)) where b is branching factor and d is distance.
 * REAL-WORLD ANALOGY / USE CASE: Digging a tunnel from both sides of a mountain and meeting in the middle. Used in social network friend-finding (degrees of separation).
 * WHEN TO USE / COMBINATION: Use when you know both the start and the goal state explicitly and the graph is relatively uniform in branching.
 * 
 * PSEUDOCODE:
 * initialize forward queue Q_f and backward queue Q_b
 * initialize visited sets V_f and V_b
 * while both queues are not empty:
 *   expand next layer of Q_f, add to V_f
 *   if intersection with V_b found -> return path
 *   expand next layer of Q_b, add to V_b
 *   if intersection with V_f found -> return path
 */
package algorithms.graph.advanced;

// These aren't separate algorithms so much as OPTIMIZATIONS/ADAPTATIONS of Dijkstra's and A* for specific real-world constraints (memory, dynamic changes, precomputation budget, grid structure).

import java.util.*;

public class BidirectionalSearch {

    static class Graph {
        int V;
        List<List<Integer>> adj;

        Graph(int V) {
            this.V = V;
            adj = new ArrayList<>(V);
            for (int i = 0; i < V; i++) adj.add(new ArrayList<>());
        }

        void addEdge(int u, int v) {
            adj.get(u).add(v);
            adj.get(v).add(u);
        }
    }

    public static int bidirectionalSearch(Graph g, int src, int dest) {
        if (src == dest) return 0;

        boolean[] visitedF = new boolean[g.V];
        boolean[] visitedB = new boolean[g.V];
        int[] parentF = new int[g.V];
        int[] parentB = new int[g.V];
        Arrays.fill(parentF, -1);
        Arrays.fill(parentB, -1);

        Queue<Integer> qF = new LinkedList<>();
        Queue<Integer> qB = new LinkedList<>();

        qF.add(src);
        visitedF[src] = true;

        qB.add(dest);
        visitedB[dest] = true;

        int intersectNode = -1;

        while (!qF.isEmpty() && !qB.isEmpty()) {
            intersectNode = expand(qF, visitedF, visitedB, parentF, g.adj);
            if (intersectNode != -1) break;

            intersectNode = expand(qB, visitedB, visitedF, parentB, g.adj);
            if (intersectNode != -1) break;
        }

        if (intersectNode == -1) return -1; // No path

        // Path reconstruction logic would go here, calculating distance for simplicity
        int dist = 0;
        int curr = intersectNode;
        while (curr != src) { curr = parentF[curr]; dist++; }
        curr = intersectNode;
        while (curr != dest) { curr = parentB[curr]; dist++; }
        
        return dist;
    }

    private static int expand(Queue<Integer> q, boolean[] visitedCurrent, boolean[] visitedOther, int[] parent, List<List<Integer>> adj) {
        int curr = q.poll();
        for (int neighbor : adj.get(curr)) {
            if (!visitedCurrent[neighbor]) {
                parent[neighbor] = curr;
                visitedCurrent[neighbor] = true;
                q.add(neighbor);
            }
            if (visitedOther[neighbor]) {
                return neighbor;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println("--- Bidirectional Search Tests ---");
        Graph g = new Graph(15);
        g.addEdge(0, 1); g.addEdge(1, 2); g.addEdge(2, 3);
        g.addEdge(3, 4); g.addEdge(4, 5); g.addEdge(5, 6);
        
        System.out.println("Test 1 (Path exists): Distance from 0 to 6 -> " + bidirectionalSearch(g, 0, 6)); // Expected: 6
        System.out.println("Test 2 (Same node): Distance from 3 to 3 -> " + bidirectionalSearch(g, 3, 3)); // Expected: 0
        System.out.println("Test 3 (No path): Distance from 0 to 14 -> " + bidirectionalSearch(g, 0, 14)); // Expected: -1
    }
}
