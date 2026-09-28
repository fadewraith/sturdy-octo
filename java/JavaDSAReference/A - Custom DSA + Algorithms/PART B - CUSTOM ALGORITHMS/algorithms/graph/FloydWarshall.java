package algorithms.graph;

/**
 * FLOYD-WARSHALL ALGORITHM
 * 
 * WHAT IT IS:
 * An algorithm to find the shortest paths between ALL pairs of vertices in a weighted graph.
 * 
 * WHEN TO USE THIS:
 * - When you need to know the distance from EVERY node to EVERY OTHER node.
 * - (Running Dijkstra V times is faster for sparse graphs, but Floyd-Warshall is 
 *   phenomenal for dense graphs or graphs with negative weights).
 * 
 * COMBINATION USAGE:
 * - Floyd-Warshall = Dynamic Programming + Graph Adjacency Matrix.
 * 
 * STRATEGY:
 * We pick a vertex `k` to act as an intermediate vertex. For every pair of vertices 
 * `(i, j)`, we check if going through `k` (i.e., i -> k -> j) is SHORTER than the 
 * current known distance from i to j. We do this for every single vertex as `k`!
 * 
 * PSEUDOCODE:
 * for k from 0 to V:
 *     for i from 0 to V:
 *         for j from 0 to V:
 *             dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])
 * 
 * COMPLEXITY:
 * Time: O(V^3) - A triple nested loop.
 * Space: O(V^2) for the distance matrix.
 */
public class FloydWarshall {

    final static int INF = 99999; // Represents infinity (to prevent integer overflow when adding)

    public static void findAllPairsShortestPath(int[][] graph) {
        int V = graph.length;
        int[][] dist = new int[V][V];

        // Initialize the solution matrix same as input graph matrix
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                dist[i][j] = graph[i][j];
            }
        }

        // Dynamic Programming phase
        // k is the intermediate vertex
        for (int k = 0; k < V; k++) {
            // i is the source vertex
            for (int i = 0; i < V; i++) {
                // j is the destination vertex
                for (int j = 0; j < V; j++) {
                    // If going through k is shorter than going directly from i to j
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        printMatrix(dist);
    }

    private static void printMatrix(int[][] dist) {
        int V = dist.length;
        System.out.println("Shortest distances between every pair of vertices:");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (dist[i][j] == INF) {
                    System.out.print("INF ");
                } else {
                    System.out.print(String.format("%3d ", dist[i][j]));
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println("--- FLOYD-WARSHALL DEMO ---");
        
        // Graph where INF means no direct edge
        int[][] graph = {
            {0,   5,  INF, 10},
            {INF, 0,   3,  INF},
            {INF, INF, 0,   1},
            {INF, INF, INF, 0}
        };

        findAllPairsShortestPath(graph);
    }
}
