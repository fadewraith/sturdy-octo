package algorithms.graph;

/**
 * TARJAN'S ALGORITHM FOR BRIDGES (Articulation Edges)
 * 
 * WHAT IT IS:
 * Finds all "Bridges" in an UNDIRECTED graph.
 * A Bridge is an edge whose removal completely disconnects the graph into 
 * two or more separate components.
 * 
 * WHEN TO USE THIS:
 * - Network reliability analysis (e.g., finding the single-point-of-failure 
 *   internet cables that would take down an entire country if cut).
 * 
 * STRATEGY:
 * It uses the EXACT SAME discovery_time and low_link logic as Tarjan's SCC!
 * The only difference is what we do when we analyze an edge u-v:
 * If `low_link[v] > discovery_time[u]`, it means vertex `v` (and all its children) 
 * have NO OTHER WAY back to `u` or above `u` EXCEPT by taking the edge u-v. 
 * Therefore, u-v is a bridge!
 * 
 * COMPLEXITY:
 * Time: O(V + E)
 * Space: O(V)
 */
public class TarjansBridges {

    private int time = 0;

    public void findBridges(int[][] graph) {
        int V = graph.length;
        boolean[] visited = new boolean[V];
        int[] disc = new int[V];
        int[] low = new int[V];
        int[] parent = new int[V];

        // Initialize
        for (int i = 0; i < V; i++) {
            parent[i] = -1;
        }

        // Call recursive helper for every unvisited node (handles disconnected graphs)
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                dfs(graph, i, visited, disc, low, parent);
            }
        }
    }

    private void dfs(int[][] graph, int u, boolean[] visited, int[] disc, int[] low, int[] parent) {
        visited[u] = true;
        disc[u] = low[u] = ++time;

        for (int v = 0; v < graph.length; v++) {
            if (graph[u][v] == 1) { // If there's an edge
                
                // If v is not visited, recurse!
                if (!visited[v]) {
                    parent[v] = u;
                    dfs(graph, v, visited, disc, low, parent);

                    // Check if subtree rooted at v has a connection back to an ancestor of u
                    low[u] = Math.min(low[u], low[v]);

                    // THE BRIDGE CONDITION:
                    // If the lowest vertex reachable from v is STILL below u in DFS tree, 
                    // then u-v is the only way to reach v.
                    if (low[v] > disc[u]) {
                        System.out.println("Bridge found: " + u + " - " + v);
                    }
                } 
                // Update low-link value of u for parent function calls.
                // Ignore the edge back to the direct parent!
                else if (v != parent[u]) {
                    low[u] = Math.min(low[u], disc[v]);
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- TARJAN'S BRIDGES DEMO ---");
        
        int V = 5;
        int[][] graph = new int[V][V];
        // Triangle 0-1-2
        graph[0][1] = 1; graph[1][0] = 1;
        graph[1][2] = 1; graph[2][1] = 1;
        graph[2][0] = 1; graph[0][2] = 1;
        
        // Bridge 0-3
        graph[0][3] = 1; graph[3][0] = 1;
        
        // Bridge 3-4
        graph[3][4] = 1; graph[4][3] = 1;

        TarjansBridges t = new TarjansBridges();
        t.findBridges(graph);
        // Expected: 3-4 and 0-3 are bridges. 0-1, 1-2, 2-0 are not because they form a cycle.
    }
}
