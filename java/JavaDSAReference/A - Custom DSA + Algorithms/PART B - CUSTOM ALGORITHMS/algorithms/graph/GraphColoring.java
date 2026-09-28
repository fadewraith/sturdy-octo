package algorithms.graph;

/**
 * GRAPH COLORING (Greedy Approach)
 * 
 * WHAT IT IS:
 * Assigns colors to vertices of a graph such that no two adjacent vertices share 
 * the same color, using the minimum number of colors possible (Chromatic Number).
 * 
 * WHEN TO USE THIS:
 * - Exam timetabling (Subjects are vertices, conflicts are edges. Minimum colors = 
 *   minimum time slots needed).
 * - Register allocation in compiler optimization.
 * 
 * STRATEGY:
 * Finding the EXACT minimum number of colors for any graph is NP-Complete (requires Backtracking).
 * However, the GREEDY approach is extremely fast (O(V+E)) and guarantees it uses 
 * at most (d + 1) colors, where d is the maximum degree of a vertex.
 * 
 * We process vertices one by one, assigning the lowest numbered color that is NOT 
 * currently used by any of its adjacent vertices.
 * 
 * COMPLEXITY:
 * Time: O(V^2) for adjacency matrix, O(V + E) for adjacency list.
 * Space: O(V)
 */
public class GraphColoring {

    public static void greedyColoring(int[][] graph) {
        int V = graph.length;
        int[] result = new int[V];

        // Initialize all vertices as unassigned (-1)
        for (int i = 0; i < V; i++) {
            result[i] = -1;
        }

        // Assign the first color to first vertex
        result[0] = 0;

        // A temporary array to store the available colors.
        // False value means color is available.
        boolean[] available = new boolean[V];
        
        // Assign colors to remaining V-1 vertices
        for (int u = 1; u < V; u++) {
            
            // 1. Mark colors of all adjacent vertices as unavailable
            for (int i = 0; i < V; i++) {
                if (graph[u][i] == 1 && result[i] != -1) {
                    available[result[i]] = true;
                }
            }

            // 2. Find the first available color
            int color;
            for (color = 0; color < V; color++) {
                if (!available[color]) {
                    break;
                }
            }

            // 3. Assign the found color
            result[u] = color;

            // 4. Reset the values back to false for the next iteration
            for (int i = 0; i < V; i++) {
                if (graph[u][i] == 1 && result[i] != -1) {
                    available[result[i]] = false;
                }
            }
        }

        // Print the result
        System.out.println("Vertex \t Color");
        for (int u = 0; u < V; u++) {
            System.out.println(u + " \t " + result[u]);
        }
    }

    public static void main(String[] args) {
        System.out.println("--- GREEDY GRAPH COLORING DEMO ---");
        
        int[][] graph = {
            {0, 1, 1, 1},
            {1, 0, 1, 0},
            {1, 1, 0, 1},
            {1, 0, 1, 0}
        };

        greedyColoring(graph);
        // Expected: Vertex 0=Color 0, Vertex 1=Color 1, Vertex 2=Color 2, Vertex 3=Color 1
    }
}
