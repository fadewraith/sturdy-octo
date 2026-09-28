package algorithms.graph;

/**
 * PRIM'S ALGORITHM (Minimum Spanning Tree)
 * 
 * WHAT IT IS:
 * Finds the Minimum Spanning Tree (MST) of a connected, undirected, weighted graph.
 * A Spanning Tree connects ALL vertices with the absolute minimum total edge weight, 
 * and has exactly (V - 1) edges with no cycles.
 * 
 * WHEN TO USE THIS:
 * - Designing networks (laying down fiber optic cables, water pipes) where you need 
 *   to connect all houses with the minimum amount of physical material.
 * - Better than Kruskal's for DENSE graphs.
 * 
 * COMBINATION USAGE:
 * - Prim's = Greedy Algorithm + Min-Heap (or Array Search) + Graph.
 * 
 * STRATEGY:
 * Similar to Dijkstra's! But instead of accumulating total distance from the start, 
 * you ONLY care about the edge weight to attach a new unvisited node to the existing 
 * growing tree.
 * 
 * COMPLEXITY:
 * Time: O(V^2) with array search. O((V+E) log V) if optimized with a Min-Heap.
 * Space: O(V)
 */
public class Prims {

    public static void findMST(int[][] graph) {
        int V = graph.length;
        
        // Array to store the constructed MST (stores the parent of each node)
        int[] parent = new int[V];
        
        // Key values used to pick minimum weight edge
        int[] key = new int[V];
        
        // To represent set of vertices included in MST
        boolean[] inMST = new boolean[V];

        // Initialize all keys as INFINITE
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            inMST[i] = false;
        }

        // Always include first vertex in MST
        key[0] = 0; 
        parent[0] = -1; // First node is always root of MST

        // The MST will have exactly V vertices
        for (int count = 0; count < V - 1; count++) {
            // Pick the minimum key vertex from the set of vertices not yet included
            int u = getMinKeyVertex(key, inMST);
            
            inMST[u] = true;

            // Update key value and parent index of the adjacent vertices of the picked vertex
            for (int v = 0; v < V; v++) {
                // graph[u][v] is non-zero only for adjacent vertices
                // inMST[v] is false for vertices not yet included
                // Update the key only if graph[u][v] is strictly smaller than the current key!
                if (graph[u][v] != 0 && !inMST[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        printMST(parent, graph);
    }

    private static int getMinKeyVertex(int[] key, boolean[] inMST) {
        int min = Integer.MAX_VALUE, minIndex = -1;
        for (int v = 0; v < key.length; v++) {
            if (!inMST[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    private static void printMST(int[] parent, int[][] graph) {
        System.out.println("Edge \tWeight");
        int totalWeight = 0;
        // Start from 1 because 0 is the root (has no parent)
        for (int i = 1; i < graph.length; i++) {
            System.out.println(parent[i] + " - " + i + "\t" + graph[i][parent[i]]);
            totalWeight += graph[i][parent[i]];
        }
        System.out.println("Total MST Weight: " + totalWeight);
    }

    public static void main(String[] args) {
        System.out.println("--- PRIM'S ALGORITHM DEMO ---");
        
        int[][] graph = {
            { 0, 2, 0, 6, 0 },
            { 2, 0, 3, 8, 5 },
            { 0, 3, 0, 0, 7 },
            { 6, 8, 0, 0, 9 },
            { 0, 5, 7, 9, 0 }
        };
        
        findMST(graph);
    }
}
