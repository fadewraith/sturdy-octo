package algorithms.graph;

/**
 * DIJKSTRA'S ALGORITHM
 * 
 * WHAT IT IS:
 * An algorithm to find the shortest path from a starting node to all other nodes 
 * in a WEIGHTED graph.
 * 
 * WHEN TO USE THIS:
 * - When you need the shortest path in a graph where edges have varying weights 
 *   (e.g., road networks with distance or time).
 * - CRITICAL PRECONDITION: All edge weights MUST BE NON-NEGATIVE. If there are 
 *   negative weights, Dijkstra's will produce incorrect results (use Bellman-Ford).
 * 
 * DATA STRUCTURE:
 * Graph (Adjacency Matrix or List) + Min-Heap (Priority Queue).
 * 
 * COMBINATION USAGE:
 * - Dijkstra's = Greedy Algorithm + Min-Heap + Graph Adjacency List.
 * 
 * PSEUDOCODE:
 * distance[all] = infinity, distance[start] = 0
 * PQ.add((start, 0))
 * while PQ is not empty:
 *     current, dist = PQ.extractMin()
 *     for each neighbor of current:
 *         newDist = dist + weight(current, neighbor)
 *         if newDist < distance[neighbor]:
 *             distance[neighbor] = newDist
 *             PQ.add((neighbor, newDist))
 * 
 * COMPLEXITY:
 * Time: O((V + E) log V) using a Min-Heap. (O(V^2) if using a simple array search).
 * Space: O(V)
 */
public class Dijkstra {

    /**
     * Finds shortest paths from a source node using a simple Array-based search 
     * (O(V^2)) to bypass external PriorityQueue dependencies for this reference.
     */
    public static void findShortestPaths(int[][] graph, int source) {
        int numVertices = graph.length;
        
        int[] distances = new int[numVertices];
        boolean[] visited = new boolean[numVertices];
        
        // Initialize distances to "Infinity"
        for (int i = 0; i < numVertices; i++) {
            distances[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }
        
        distances[source] = 0;
        
        for (int count = 0; count < numVertices - 1; count++) {
            // 1. GREEDY STEP: Find the unvisited node with the minimum distance
            // (In an O((V+E)logV) implementation, this step is replaced by a Min-Heap)
            int u = getMinimumDistanceNode(distances, visited);
            
            if (u == -1) break; // All remaining nodes are unreachable
            visited[u] = true;
            
            // 2. Update the distances of the neighboring nodes
            for (int v = 0; v < numVertices; v++) {
                // If there's an edge, it's unvisited, and the path through `u` is strictly shorter
                if (!visited[v] && graph[u][v] != 0 && distances[u] != Integer.MAX_VALUE 
                    && distances[u] + graph[u][v] < distances[v]) {
                    distances[v] = distances[u] + graph[u][v];
                }
            }
        }
        
        printSolution(distances, source);
    }

    private static int getMinimumDistanceNode(int[] distances, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < distances.length; v++) {
            if (!visited[v] && distances[v] <= min) {
                min = distances[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    private static void printSolution(int[] distances, int source) {
        System.out.println("Node \t Minimum Distance from Source (" + source + ")");
        for (int i = 0; i < distances.length; i++) {
            System.out.println(i + " \t\t " + distances[i]);
        }
    }

    public static void main(String[] args) {
        System.out.println("--- DIJKSTRA'S ALGORITHM DEMO ---");
        
        // Adjacency Matrix representing a graph (0 means no edge)
        int[][] graph = {
            { 0,  4,  0,  0,  0,  0,  0,  8,  0 }, // 0
            { 4,  0,  8,  0,  0,  0,  0, 11,  0 }, // 1
            { 0,  8,  0,  7,  0,  4,  0,  0,  2 }, // 2
            { 0,  0,  7,  0,  9, 14,  0,  0,  0 }, // 3
            { 0,  0,  0,  9,  0, 10,  0,  0,  0 }, // 4
            { 0,  0,  4, 14, 10,  0,  2,  0,  0 }, // 5
            { 0,  0,  0,  0,  0,  2,  0,  1,  6 }, // 6
            { 8, 11,  0,  0,  0,  0,  1,  0,  7 }, // 7
            { 0,  0,  2,  0,  0,  0,  6,  7,  0 }  // 8
        };
        
        findShortestPaths(graph, 0);
    }
}
