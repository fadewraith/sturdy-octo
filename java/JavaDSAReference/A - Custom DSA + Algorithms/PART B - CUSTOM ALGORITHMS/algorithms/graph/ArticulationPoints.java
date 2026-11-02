package algorithms.graph;

/**
 * ARTICULATION POINTS (Cut Vertices) - Tarjan's low-link algorithm
 *
 * WHAT IT IS:
 * In an UNDIRECTED graph, an articulation point is a VERTEX whose removal (together
 * with its edges) splits its connected component into two or more pieces.
 * (The edge version of this question is "bridges" - see TarjansBridges.java.)
 *
 * WHEN TO USE THIS:
 * - Finding single points of failure: a router, a server, an airport hub whose
 *   failure disconnects part of the network.
 * - Splitting a graph into biconnected components.
 *
 * STRATEGY (one DFS, same disc/low idea as bridges):
 * disc[u] = the time DFS first reached u.
 * low[u]  = the smallest disc reachable from u's DFS subtree using tree edges down
 *           and at most ONE back edge up.
 * For a DFS child v of u:
 *   - NON-ROOT u is a cut vertex if some child has low[v] >= disc[u]
 *     (v's subtree cannot climb above u without going through u).
 *     Compare with bridges, which use low[v] > disc[u] - strict, because there we
 *     remove an edge, while here removing u itself already blocks the way back to u.
 *   - The ROOT is special: it has no ancestors, so it is a cut vertex only if it has
 *     two or more DFS children (they can only connect through the root).
 *
 * INPUT FORMAT: adjacency matrix, graph[u][v] != 0 means an undirected edge.
 *
 * COMPLEXITY:
 * Time: O(V + E) with adjacency lists, O(V^2) with the matrix used here.
 * Space: O(V) for disc/low arrays and recursion.
 */
public class ArticulationPoints {

    private static int timer;

    /** isCut[v] == true if removing v disconnects its component. */
    public static boolean[] findCutVertices(int[][] graph) {
        int V = graph.length;
        int[] disc = new int[V]; // 0 = not visited yet (times start at 1)
        int[] low = new int[V];
        boolean[] isCut = new boolean[V];
        timer = 0;
        for (int v = 0; v < V; v++) {
            if (disc[v] == 0) dfs(graph, v, -1, disc, low, isCut);
        }
        return isCut;
    }

    /** The cut vertices in increasing order. */
    public static int[] articulationPoints(int[][] graph) {
        boolean[] isCut = findCutVertices(graph);
        int count = 0;
        for (boolean b : isCut) if (b) count++;
        int[] result = new int[count];
        int k = 0;
        for (int v = 0; v < isCut.length; v++) if (isCut[v]) result[k++] = v;
        return result;
    }

    private static void dfs(int[][] graph, int u, int parent, int[] disc, int[] low, boolean[] isCut) {
        disc[u] = low[u] = ++timer;
        int children = 0;

        for (int v = 0; v < graph.length; v++) {
            if (graph[u][v] == 0 || v == u) continue;
            if (disc[v] == 0) {
                // tree edge
                children++;
                dfs(graph, v, u, disc, low, isCut);
                low[u] = Math.min(low[u], low[v]);
                if (parent != -1 && low[v] >= disc[u]) isCut[u] = true;
            } else if (v != parent) {
                // back edge to an ancestor (or an already-finished vertex)
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        if (parent == -1 && children > 1) isCut[u] = true;
    }

    public static void main(String[] args) {
        System.out.println("--- ARTICULATION POINTS DEMO ---");

        //   0 --- 1 --- 3 --- 4
        //    \   /      |
        //      2        5
        int[][] graph = new int[6][6];
        int[][] edges = {{0, 1}, {1, 2}, {2, 0}, {1, 3}, {3, 4}, {3, 5}};
        for (int[] e : edges) { graph[e[0]][e[1]] = 1; graph[e[1]][e[0]] = 1; }

        System.out.println("Articulation points: " + java.util.Arrays.toString(articulationPoints(graph)));
        // Expected: [1, 3] (removing 1 cuts off {3,4,5}; removing 3 isolates 4 and 5)

        // A plain cycle has no single point of failure
        int[][] cycle = new int[4][4];
        for (int i = 0; i < 4; i++) { cycle[i][(i + 1) % 4] = 1; cycle[(i + 1) % 4][i] = 1; }
        System.out.println("Cycle of 4:          " + java.util.Arrays.toString(articulationPoints(cycle))); // []
    }
}
