package algorithms.graph;

/**
 * GRAPH COLORING (Greedy, Backtracking m-Coloring, Exact Chromatic Number)
 *
 * WHAT IT IS:
 * Assigns colors to vertices of a graph such that no two adjacent vertices share
 * the same color, using the minimum number of colors possible (Chromatic Number).
 *
 * WHEN TO USE THIS:
 * - Exam timetabling (Subjects are vertices, conflicts are edges. Minimum colors =
 *   minimum time slots needed).
 * - Register allocation in compiler optimization.
 * - Map coloring, frequency assignment for radio towers, Sudoku (a 9-coloring!).
 *
 * STRATEGY 1 - GREEDY:
 * Finding the EXACT minimum number of colors for any graph is NP-Complete.
 * The GREEDY approach is fast (O(V+E)) and guarantees at most (d + 1) colors,
 * where d is the maximum degree - but it is NOT always optimal (the vertex order
 * matters; a bad order on a bipartite "crown" graph can use V/2 colors instead of 2).
 * We process vertices one by one, assigning the lowest numbered color that is NOT
 * currently used by any of its adjacent vertices.
 *
 * STRATEGY 2 - BACKTRACKING m-COLORING ("can it be done with m colors?"):
 * Color vertex 0, then 1, then 2 ... For each vertex try colors 0..m-1 that no
 * already-colored neighbour uses. If no color fits, undo and go back to the
 * previous vertex to try its next color.
 * Pruning used here: color c is only tried if c <= (highest color used so far) + 1.
 * Colors are interchangeable, so using color 5 before color 4 never finds anything
 * new - this "symmetry breaking" cuts the search by up to m! times.
 *
 * STRATEGY 3 - CHROMATIC NUMBER (exact minimum):
 * Try m = 1, 2, 3 ... and return the first m that backtracking can do.
 * (Max is V: give every vertex its own color.)
 * Quick facts: 1 color iff no edges; 2 colors iff bipartite (see BipartiteCheck).
 *
 * INPUT FORMAT: undirected adjacency matrix, graph[u][v] != 0 means an edge.
 *
 * COMPLEXITY:
 * Greedy:       Time O(V^2) for adjacency matrix, O(V + E) for adjacency list. Space O(V).
 * m-Coloring:   Time O(m^V * V) worst case (exponential), Space O(V) recursion.
 * Chromatic #:  up to V runs of m-coloring - only for small graphs (~ up to 30-50 vertices).
 */
public class GraphColoring {

    /** Greedy coloring that prints the result (original demo). */
    public static void greedyColoring(int[][] graph) {
        int[] result = greedyColors(graph);

        // Print the result
        System.out.println("Vertex \t Color");
        for (int u = 0; u < result.length; u++) {
            System.out.println(u + " \t " + result[u]);
        }
    }

    /** Greedy coloring in vertex order 0..V-1. Returns color[v] for every vertex. */
    public static int[] greedyColors(int[][] graph) {
        int V = graph.length;
        int[] result = new int[V];
        if (V == 0) return result;

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
                if (graph[u][i] != 0 && result[i] != -1) {
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
                if (graph[u][i] != 0 && result[i] != -1) {
                    available[result[i]] = false;
                }
            }
        }
        return result;
    }

    /**
     * BACKTRACKING m-COLORING. Returns a valid coloring using colors 0..m-1,
     * or null if the graph cannot be colored with m colors.
     */
    public static int[] mColoring(int[][] graph, int m) {
        int V = graph.length;
        int[] color = new int[V];
        for (int i = 0; i < V; i++) color[i] = -1;
        return colorFrom(graph, m, 0, -1, color) ? color : null;
    }

    private static boolean colorFrom(int[][] graph, int m, int v, int maxUsed, int[] color) {
        if (v == graph.length) return true; // every vertex colored

        // symmetry breaking: never skip ahead to a brand-new color beyond maxUsed + 1
        int limit = Math.min(m - 1, maxUsed + 1);
        for (int c = 0; c <= limit; c++) {
            if (isSafe(graph, v, c, color)) {
                color[v] = c;
                if (colorFrom(graph, m, v + 1, Math.max(maxUsed, c), color)) return true;
                color[v] = -1; // undo (backtrack)
            }
        }
        return false;
    }

    // Only earlier vertices are colored, so checking all neighbours is enough
    private static boolean isSafe(int[][] graph, int v, int c, int[] color) {
        for (int u = 0; u < graph.length; u++) {
            if (graph[v][u] != 0 && color[u] == c) return false;
        }
        return true;
    }

    /**
     * Exact chromatic number: the fewest colors that work. A self-loop makes coloring
     * impossible (a vertex would clash with itself), so that throws.
     */
    public static int chromaticNumber(int[][] graph) {
        int V = graph.length;
        for (int v = 0; v < V; v++) {
            if (graph[v][v] != 0) throw new IllegalArgumentException("self-loop at vertex " + v);
        }
        if (V == 0) return 0;
        for (int m = 1; m <= V; m++) {
            if (mColoring(graph, m) != null) return m;
        }
        return V; // unreachable: V colors always work
    }

    /** Checks that no edge joins two vertices of the same color. */
    public static boolean isValidColoring(int[][] graph, int[] color) {
        for (int u = 0; u < graph.length; u++)
            for (int v = 0; v < graph.length; v++)
                if (graph[u][v] != 0 && color[u] == color[v]) return false;
        return true;
    }

    public static void main(String[] args) {
        System.out.println("--- GRAPH COLORING DEMO ---");

        int[][] graph = {
            {0, 1, 1, 1},
            {1, 0, 1, 0},
            {1, 1, 0, 1},
            {1, 0, 1, 0}
        };

        greedyColoring(graph);
        // Expected: Vertex 0=Color 0, Vertex 1=Color 1, Vertex 2=Color 2, Vertex 3=Color 1

        System.out.println("\n2-coloring possible? " + (mColoring(graph, 2) != null)); // false (triangle 0-1-2)
        System.out.println("3-coloring: " + java.util.Arrays.toString(mColoring(graph, 3))); // [0, 1, 2, 1]
        System.out.println("Chromatic number: " + chromaticNumber(graph)); // 3

        // Crown graph on 6 vertices (pairs 0-1, 2-3, 4-5 are NOT connected, everything
        // else between the two sides is). Ordered 0,1,2,... greedy is fooled into 3 colors.
        int[][] crown = new int[6][6];
        for (int u = 0; u < 6; u += 2)
            for (int v = 1; v < 6; v += 2)
                if (v != u + 1) { crown[u][v] = 1; crown[v][u] = 1; }
        int[] greedy = greedyColors(crown);
        int greedyCount = 0;
        for (int c : greedy) greedyCount = Math.max(greedyCount, c + 1);
        System.out.println("\nCrown graph: greedy uses " + greedyCount + " colors, optimum is "
                + chromaticNumber(crown)); // greedy 3, optimum 2
    }
}
