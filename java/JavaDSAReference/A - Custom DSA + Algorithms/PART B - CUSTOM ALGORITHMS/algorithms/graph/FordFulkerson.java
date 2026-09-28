package algorithms.graph;

/**
 * FORD-FULKERSON (Edmonds-Karp Variant)
 * 
 * WHAT IT IS:
 * An algorithm that computes the Maximum Flow in a flow network.
 * 
 * WHEN TO USE THIS:
 * - Capacity-constrained network graphs (e.g., bandwidth allocation, traffic flow, 
 *   fluid in pipes).
 * - Bipartite Matching problems (e.g., matching job applicants to open positions).
 * 
 * COMBINATION USAGE:
 * - Ford-Fulkerson Method + BFS Augmenting Path search = Edmonds-Karp Algorithm.
 * 
 * STRATEGY:
 * We use a "Residual Graph". We repeatedly use BFS to find any path from Source 
 * to Sink that still has available capacity. Once found, we subtract that capacity 
 * from the forward edges, AND ADD IT TO THE REVERSE EDGES! 
 * (Adding capacity to the reverse edges allows the algorithm to "undo" bad routing 
 * decisions made earlier).
 * We stop when BFS can no longer reach the Sink.
 * 
 * COMPLEXITY:
 * Time: O(V * E^2) for Edmonds-Karp.
 * Space: O(V^2) for the residual matrix.
 */
public class FordFulkerson {

    // Simple custom queue for BFS
    private static class IntQueue {
        int[] data;
        int front = 0, rear = 0;
        IntQueue(int capacity) { data = new int[capacity]; }
        void enqueue(int val) { data[rear++] = val; }
        int dequeue() { return data[front++]; }
        boolean isEmpty() { return front == rear; }
    }

    /**
     * BFS to find an augmenting path from source to sink.
     * Fills parent[] array to reconstruct the path.
     */
    private static boolean bfs(int[][] residualGraph, int source, int sink, int[] parent) {
        int vertices = residualGraph.length;
        boolean[] visited = new boolean[vertices];
        IntQueue queue = new IntQueue(vertices * 10);
        
        queue.enqueue(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.dequeue();

            for (int v = 0; v < vertices; v++) {
                // If not visited AND there is available capacity in the residual graph
                if (!visited[v] && residualGraph[u][v] > 0) {
                    // Stop immediately if we reached the sink!
                    if (v == sink) {
                        parent[v] = u;
                        return true;
                    }
                    queue.enqueue(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        return false; // Sink is unreachable
    }

    public static int maxFlow(int[][] graph, int source, int sink) {
        int vertices = graph.length;
        int[][] residualGraph = new int[vertices][vertices];
        
        // Copy original graph capacities to residual graph
        for (int u = 0; u < vertices; u++) {
            for (int v = 0; v < vertices; v++) {
                residualGraph[u][v] = graph[u][v];
            }
        }

        int[] parent = new int[vertices];
        int maxFlow = 0;

        // Loop while there is an augmenting path from source to sink
        while (bfs(residualGraph, source, sink, parent)) {
            
            // 1. Find the bottleneck capacity (minimum edge capacity) along the path
            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }

            // 2. Update residual capacities of the edges and reverse edges
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residualGraph[u][v] -= pathFlow; // Reduce forward capacity
                residualGraph[v][u] += pathFlow; // Increase REVERSE capacity! (The magic trick)
            }

            // 3. Add path flow to overall flow
            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    public static void main(String[] args) {
        System.out.println("--- FORD-FULKERSON (MAX FLOW) DEMO ---");
        
        // Graph edge capacities
        int[][] graph = {
            {0, 16, 13, 0, 0, 0},
            {0, 0, 10, 12, 0, 0},
            {0, 4, 0, 0, 14, 0},
            {0, 0, 9, 0, 0, 20},
            {0, 0, 0, 7, 0, 4},
            {0, 0, 0, 0, 0, 0}
        };
        
        int source = 0;
        int sink = 5;
        
        System.out.println("The maximum possible flow is " + maxFlow(graph, source, sink)); 
        // Expected: 23
    }
}
