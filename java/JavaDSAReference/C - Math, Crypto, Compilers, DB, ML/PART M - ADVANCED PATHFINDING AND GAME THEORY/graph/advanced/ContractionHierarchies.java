/**
 * WHAT IT IS: Contraction Hierarchies (CH) is a preprocessing technique to speed up shortest-path routing in large graphs.
 * STRATEGY: Order nodes by "importance" and contract them (remove them and add shortcut edges between their neighbors if they formed the shortest path). Query using bidirectional Dijkstra where you only relax edges going to "more important" nodes.
 * TIME/SPACE COMPLEXITY: Query Time: O(log V) or O(sqrt V) depending on structure. Space: O(V + E) with added shortcuts. Precomputation takes significant time.
 * REAL-WORLD ANALOGY / USE CASE: Google Maps routing across the country, where local roads are ignored in favor of highways (shortcuts) for the bulk of the journey.
 * WHEN TO USE / COMBINATION: When dealing with massive static road networks where you need millions of queries per second and can afford upfront precomputation.
 * 
 * PSEUDOCODE:
 * Preprocessing:
 * order nodes by importance
 * for each node v in order:
 *   for each pair (u, w) adjacent to v:
 *     if shortest path u->w goes through v:
 *       add shortcut edge (u, w) with weight cost(u,v)+cost(v,w)
 * Query:
 * Bidirectional Dijkstra only following upward edges (to higher importance nodes).
 */
package algorithms.graph.advanced;

// These aren't separate algorithms so much as OPTIMIZATIONS/ADAPTATIONS of Dijkstra's and A* for specific real-world constraints (memory, dynamic changes, precomputation budget, grid structure).

import java.util.*;

public class ContractionHierarchies {

    static class Edge {
        int to; int weight;
        Edge(int to, int weight) { this.to = to; this.weight = weight; }
    }

    // A highly simplified conceptual mockup. Real CH involves complex node ordering (edge difference, etc.) and witness searches.
    public static void addShortcuts(List<List<Edge>> adj, int[] importance) {
        int n = adj.size();
        // Contract nodes in order of importance (0 to N-1 assumed here as rank)
        for (int v = 0; v < n; v++) {
            // Find in-neighbors and out-neighbors logically higher than v
            // For simplicity, we just look at all pairs around v
            for (Edge in : adj.get(v)) {
                for (Edge out : adj.get(v)) {
                    if (in.to != out.to && importance[in.to] > importance[v] && importance[out.to] > importance[v]) {
                        // In reality, do a witness search here. We blindly add the shortcut for demonstration.
                        adj.get(in.to).add(new Edge(out.to, in.weight + out.weight));
                    }
                }
            }
        }
    }

    public static int chQuery(List<List<Edge>> adj, int[] importance, int src, int dest) {
        if (src == dest) return 0;
        
        int n = adj.size();
        int[] distF = new int[n]; Arrays.fill(distF, Integer.MAX_VALUE);
        int[] distB = new int[n]; Arrays.fill(distB, Integer.MAX_VALUE);
        
        PriorityQueue<int[]> pqF = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        PriorityQueue<int[]> pqB = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        
        distF[src] = 0; pqF.add(new int[]{src, 0});
        distB[dest] = 0; pqB.add(new int[]{dest, 0});
        
        while (!pqF.isEmpty() || !pqB.isEmpty()) {
            if (!pqF.isEmpty()) {
                int[] curr = pqF.poll();
                int u = curr[0], d = curr[1];
                if (d <= distF[u]) {
                    for (Edge e : adj.get(u)) {
                        if (importance[e.to] > importance[u] && distF[u] + e.weight < distF[e.to]) {
                            distF[e.to] = distF[u] + e.weight;
                            pqF.add(new int[]{e.to, distF[e.to]});
                        }
                    }
                }
            }
            if (!pqB.isEmpty()) {
                int[] curr = pqB.poll();
                int u = curr[0], d = curr[1];
                if (d <= distB[u]) {
                    for (Edge e : adj.get(u)) {
                        // Assuming undirected graph for reverse search simplicity
                        if (importance[e.to] > importance[u] && distB[u] + e.weight < distB[e.to]) {
                            distB[e.to] = distB[u] + e.weight;
                            pqB.add(new int[]{e.to, distB[e.to]});
                        }
                    }
                }
            }
        }
        
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (distF[i] != Integer.MAX_VALUE && distB[i] != Integer.MAX_VALUE) {
                best = Math.min(best, distF[i] + distB[i]);
            }
        }
        
        return best == Integer.MAX_VALUE ? -1 : best;
    }

    public static void main(String[] args) {
        System.out.println("--- Contraction Hierarchies (Conceptual) Tests ---");
        int n = 5;
        List<List<Edge>> adj = new ArrayList<>();
        for (int i=0; i<n; i++) adj.add(new ArrayList<>());
        
        // 0-1-2-3-4 graph
        adj.get(0).add(new Edge(1, 1)); adj.get(1).add(new Edge(0, 1));
        adj.get(1).add(new Edge(2, 1)); adj.get(2).add(new Edge(1, 1));
        adj.get(2).add(new Edge(3, 1)); adj.get(3).add(new Edge(2, 1));
        adj.get(3).add(new Edge(4, 1)); adj.get(4).add(new Edge(3, 1));
        
        // Node 2 is the most important (center)
        int[] importance = {0, 1, 4, 2, 3};
        
        addShortcuts(adj, importance);
        
        System.out.println("Test 1 (0 to 4): " + chQuery(adj, importance, 0, 4)); // Expected: 4
        System.out.println("Test 2 (1 to 3): " + chQuery(adj, importance, 1, 3)); // Expected: 2
        System.out.println("Test 3 (Start == Goal): " + chQuery(adj, importance, 2, 2)); // Expected: 0
    }
}
