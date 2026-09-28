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

    public static void printEulerPath(int[][] graph) {
        int V = graph.length;
        int[] edgeCount = new int[V];
        
        // Clone graph because we destroy edges as we traverse
        int[][] tempGraph = new int[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                tempGraph[i][j] = graph[i][j];
                if (graph[i][j] > 0) {
                    edgeCount[i]++;
                }
            }
        }

        // 1. Find starting node. 
        // In a directed graph, if it has a path, start node has out-degree - in-degree == 1.
        // For simplicity in this un-directed demo, we just start at node 0.
        int startNode = 0; 
        
        IntStack currentPath = new IntStack(V * V);
        IntStack finalCircuit = new IntStack(V * V);

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

        System.out.print("Eulerian Path: ");
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
        // Expected: A path that hits every single edge exactly once.
    }
}
