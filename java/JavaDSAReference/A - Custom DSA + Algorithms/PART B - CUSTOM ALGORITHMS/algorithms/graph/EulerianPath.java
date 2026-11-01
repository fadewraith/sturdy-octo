package algorithms.graph;

/**
 * EULERIAN PATH / CIRCUIT (Hierholzer's Algorithm)
 * 
 * WHAT IT IS:
 * Eulerian Path: A trail in a finite graph that visits EVERY EDGE exactly once.
 * Eulerian Circuit: An Eulerian trail that starts and ends on the same vertex.
 * 
 * CRITICAL DISTINCTION:
 * Do not confuse this with a Hamiltonian Path!
 * Eulerian: Visit every EDGE exactly once (Easy, O(V + E) time).
 * Hamiltonian: Visit every VERTEX exactly once (NP-Hard Traveling Salesman Problem!).
 * 
 * WHEN TO USE THIS:
 * - Routing problems where every connection must be utilized (e.g., a snowplow 
 *   that must drive down every single street in a city, DNA fragment assembly).
 * 
 * STRATEGY (Hierholzer's Algorithm):
 * 1. Find a valid starting node (Odd degree for Path, any non-zero degree for Circuit).
 * 2. Do a DFS, destroying edges as you walk across them.
 * 3. When you get stuck (no outgoing edges left), push the node to a stack.
 * 4. The stack popped in reverse order gives the exact Eulerian Path!
 * 
 * COMPLEXITY:
 * Time: O(V + E)
 * Space: O(V + E)
 */
public class EulerianPath {

    private static class IntStack {
        int[] data;
        int top = -1;
        IntStack(int capacity) { data = new int[capacity]; }
        void push(int val) { data[++top] = val; }
        int pop() { return data[top--]; }
        boolean isEmpty() { return top == -1; }
    }

    /**
     * Undirected graph as an adjacency matrix. graph[u][v] = number of edges
     * between u and v (so multi-edges are allowed). Self-loops are not supported.
     */
    public static void printEulerPath(int[][] graph) {
        int V = graph.length;
        int[] edgeCount = new int[V]; // degree = number of edge ends at each vertex
        int totalEdges = 0;

        // Clone graph because we destroy edges as we traverse
        int[][] tempGraph = new int[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                tempGraph[i][j] = graph[i][j];
                edgeCount[i] += graph[i][j];
                if (j > i) totalEdges += graph[i][j];
            }
        }

        // 1. Find starting node (Euler's theorem for undirected graphs):
        //    - 0 odd-degree vertices -> Eulerian CIRCUIT, start anywhere with an edge
        //    - 2 odd-degree vertices -> Eulerian PATH, must start at one of them
        //    - anything else         -> no Eulerian path exists
        int oddCount = 0;
        int startNode = -1;
        for (int i = 0; i < V; i++) {
            if (edgeCount[i] % 2 == 1) {
                oddCount++;
                if (startNode == -1 || edgeCount[startNode] % 2 == 0) startNode = i;
            } else if (startNode == -1 && edgeCount[i] > 0) {
                startNode = i;
            }
        }
        if (totalEdges == 0) {
            System.out.println("Eulerian Path: (graph has no edges)");
            return;
        }
        if (oddCount != 0 && oddCount != 2) {
            System.out.println("No Eulerian Path: " + oddCount + " vertices have odd degree (need 0 or 2).");
            return;
        }

        IntStack currentPath = new IntStack(totalEdges + 2);
        IntStack finalCircuit = new IntStack(totalEdges + 2);

        currentPath.push(startNode);
        int currentVertex = startNode;

        // 2. Walk until stuck
        while (!currentPath.isEmpty()) {
            if (edgeCount[currentVertex] > 0) {
                currentPath.push(currentVertex);
                
                // Find next adjacent edge
                int nextVertex = -1;
                for (int i = 0; i < V; i++) {
                    if (tempGraph[currentVertex][i] > 0) {
                        nextVertex = i;
                        break;
                    }
                }
                
                // Remove the edge (destroy it so we don't cross it again)
                tempGraph[currentVertex][nextVertex]--;
                tempGraph[nextVertex][currentVertex]--; // (assuming undirected)
                edgeCount[currentVertex]--;
                edgeCount[nextVertex]--;
                
                currentVertex = nextVertex;
            } else {
                // Stuck! Push to final circuit and backtrack
                finalCircuit.push(currentVertex);
                currentVertex = currentPath.pop();
            }
        }

        // 3. Connectivity check: a path of E edges has E + 1 vertices.
        // If we used fewer edges, some edges are in another component.
        if (finalCircuit.top + 1 != totalEdges + 1) {
            System.out.println("No Eulerian Path: the edges are not all connected.");
            return;
        }

        System.out.print(oddCount == 0 ? "Eulerian Circuit: " : "Eulerian Path: ");
        while (!finalCircuit.isEmpty()) {
            System.out.print(finalCircuit.pop() + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("--- EULERIAN PATH (HIERHOLZER'S) DEMO ---");
        
        // Graph with an Eulerian Circuit (A bow-tie shape)
        int[][] graph = {
            {0, 1, 1, 0, 0},
            {1, 0, 1, 0, 0},
            {1, 1, 0, 1, 1},
            {0, 0, 1, 0, 1},
            {0, 0, 1, 1, 0}
        };

        printEulerPath(graph);
        // Expected: A circuit that hits every single edge exactly once.

        // Path graph 1 - 0 - 2: odd vertices are 1 and 2, so we must start at one of them
        int[][] pathGraph = {
            {0, 1, 1},
            {1, 0, 0},
            {1, 0, 0}
        };
        printEulerPath(pathGraph); // Expected: 1 0 2

        // Star with 3 leaves: 4 odd vertices -> impossible
        int[][] star = {
            {0, 1, 1, 1},
            {1, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 0, 0, 0}
        };
        printEulerPath(star);
    }
}
