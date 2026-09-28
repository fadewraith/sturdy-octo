package algorithms.graph;

/**
 * BELLMAN-FORD ALGORITHM
 * 
 * WHAT IT IS:
 * An algorithm to find the shortest path from a single source to all other vertices 
 * in a weighted graph.
 * 
 * WHEN TO USE THIS:
 * - When the graph contains NEGATIVE EDGE WEIGHTS (which breaks Dijkstra's algorithm).
 * - To explicitly detect NEGATIVE WEIGHT CYCLES (if a cycle exists whose edges sum 
 *   to a negative number, you can loop infinitely to achieve negative infinite distance!).
 * 
 * DATA STRUCTURE:
 * Usually an Array of Edges (rather than an Adjacency Matrix).
 * 
 * STRATEGY:
 * It mathematically guarantees the shortest path by relaxing ALL edges exactly (V - 1) times.
 * Why (V - 1)? Because the longest possible path without a cycle in a graph with V 
 * vertices has exactly (V - 1) edges.
 * After (V - 1) iterations, we do ONE MORE iteration. If any distance still decreases, 
 * it mathematically proves there is a Negative Cycle!
 * 
 * COMPLEXITY:
 * Time: O(V * E) - Slower than Dijkstra's, so only use it when negative weights exist.
 * Space: O(V)
 */
public class BellmanFord {

    static class Edge {
        int source, destination, weight;
        Edge(int src, int dest, int weight) {
            this.source = src;
            this.destination = dest;
            this.weight = weight;
        }
    }

    public static void findShortestPaths(int numVertices, Edge[] edges, int source) {
        int[] distances = new int[numVertices];
        
        // Initialize distances to "Infinity"
        for (int i = 0; i < numVertices; i++) {
            distances[i] = Integer.MAX_VALUE;
        }
        distances[source] = 0;

        // Step 1: Relax all edges (V - 1) times
        for (int i = 1; i < numVertices; i++) {
            for (Edge edge : edges) {
                int u = edge.source;
                int v = edge.destination;
                int weight = edge.weight;

                if (distances[u] != Integer.MAX_VALUE && distances[u] + weight < distances[v]) {
                    distances[v] = distances[u] + weight;
                }
            }
        }

        // Step 2: Check for negative-weight cycles
        for (Edge edge : edges) {
            int u = edge.source;
            int v = edge.destination;
            int weight = edge.weight;

            if (distances[u] != Integer.MAX_VALUE && distances[u] + weight < distances[v]) {
                System.out.println("CRITICAL: Graph contains a Negative Weight Cycle!");
                return;
            }
        }

        // Print results
        System.out.println("Node \t Minimum Distance from Source (" + source + ")");
        for (int i = 0; i < numVertices; i++) {
            System.out.println(i + " \t\t " + distances[i]);
        }
    }

    public static void main(String[] args) {
        System.out.println("--- BELLMAN-FORD DEMO ---");
        
        int numVertices = 5;
        // Includes negative weights!
        Edge[] edges = {
            new Edge(0, 1, -1),
            new Edge(0, 2, 4),
            new Edge(1, 2, 3),
            new Edge(1, 3, 2),
            new Edge(1, 4, 2),
            new Edge(3, 2, 5),
            new Edge(3, 1, 1),
            new Edge(4, 3, -3) // Negative weight!
        };

        findShortestPaths(numVertices, edges, 0);
    }
}
