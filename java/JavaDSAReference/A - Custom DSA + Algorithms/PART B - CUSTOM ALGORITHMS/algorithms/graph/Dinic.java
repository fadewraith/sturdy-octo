package algorithms.graph;

import java.util.Arrays;

/**
 * DINIC'S ALGORITHM (Maximum Flow) + MINIMUM CUT
 *
 * WHAT IT IS:
 * Computes the maximum flow from a source to a sink, like Ford-Fulkerson /
 * Edmonds-Karp (FordFulkerson.java), but much faster in practice:
 * O(V^2 * E) in general, O(E * sqrt(V)) on unit-capacity graphs (e.g. matching).
 *
 * WHEN TO USE THIS:
 * - Any max-flow problem bigger than a toy: network throughput, project selection,
 *   image segmentation, bipartite matching, edge-disjoint paths.
 * - MIN CUT: the cheapest set of edges whose removal separates source from sink.
 *   Max-flow min-cut theorem: its total capacity EQUALS the maximum flow.
 *
 * STRATEGY:
 * Repeat until the sink is unreachable:
 * 1. BFS on the residual graph -> level[v] = distance from the source.
 * 2. "Blocking flow": DFS from the source using ONLY edges that go one level deeper,
 *    pushing flow until no such path is left. The iter[] pointer remembers, per
 *    vertex, which edges were already exhausted, so they are never rescanned
 *    in the same phase - this is what makes Dinic fast.
 * Each edge is stored with its REVERSE edge right next to it (index e and e ^ 1),
 * so pushing f units is: cap[e] -= f, cap[e ^ 1] += f.
 *
 * MIN CUT AFTER MAX FLOW:
 * The source side S = every vertex still reachable from the source in the residual
 * graph. The cut edges are the ORIGINAL edges going from S to outside S - they are
 * all saturated.
 *
 * COMPLEXITY:
 * Time: O(V^2 * E) worst case.
 * Space: O(V + E) - adjacency stored as arrays, not a V x V matrix.
 */
public class Dinic {

    private final int n;
    private int edgeCount = 0;
    // Edge list in arrays: edge e goes to to[e] with remaining capacity cap[e];
    // next[e] links the edges leaving the same vertex; head[v] is v's first edge.
    private int[] to = new int[16], cap = new int[16], next = new int[16], originalCap = new int[16];
    private final int[] head;
    private int[] level, iter;

    public Dinic(int n) {
        this.n = n;
        this.head = new int[n];
        Arrays.fill(head, -1);
    }

    /** Builds a network from a capacity matrix (capacity[u][v] > 0 means an edge u -> v). */
    public static Dinic fromMatrix(int[][] capacity) {
        Dinic d = new Dinic(capacity.length);
        for (int u = 0; u < capacity.length; u++)
            for (int v = 0; v < capacity.length; v++)
                if (capacity[u][v] > 0) d.addEdge(u, v, capacity[u][v]);
        return d;
    }

    public void addEdge(int u, int v, int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("negative capacity");
        if (edgeCount + 2 > to.length) {
            int size = to.length * 2;
            to = Arrays.copyOf(to, size);
            cap = Arrays.copyOf(cap, size);
            next = Arrays.copyOf(next, size);
            originalCap = Arrays.copyOf(originalCap, size);
        }
        // forward edge
        to[edgeCount] = v; cap[edgeCount] = capacity; originalCap[edgeCount] = capacity;
        next[edgeCount] = head[u]; head[u] = edgeCount++;
        // reverse edge (starts with 0 capacity)
        to[edgeCount] = u; cap[edgeCount] = 0; originalCap[edgeCount] = 0;
        next[edgeCount] = head[v]; head[v] = edgeCount++;
    }

    public long maxFlow(int source, int sink) {
        if (source == sink) throw new IllegalArgumentException("source and sink must differ");
        long flow = 0;
        level = new int[n];
        iter = new int[n];
        while (bfs(source, sink)) {
            for (int v = 0; v < n; v++) iter[v] = head[v];
            int pushed;
            while ((pushed = dfs(source, sink, Integer.MAX_VALUE)) > 0) flow += pushed;
        }
        return flow;
    }

    private boolean bfs(int source, int sink) {
        Arrays.fill(level, -1);
        int[] queue = new int[n];
        int qh = 0, qt = 0;
        level[source] = 0;
        queue[qt++] = source;
        while (qh < qt) {
            int u = queue[qh++];
            for (int e = head[u]; e != -1; e = next[e]) {
                if (cap[e] > 0 && level[to[e]] == -1) {
                    level[to[e]] = level[u] + 1;
                    queue[qt++] = to[e];
                }
            }
        }
        return level[sink] != -1;
    }

    private int dfs(int u, int sink, int limit) {
        if (u == sink) return limit;
        for (; iter[u] != -1; iter[u] = next[iter[u]]) {
            int e = iter[u], v = to[e];
            if (cap[e] > 0 && level[v] == level[u] + 1) {
                int pushed = dfs(v, sink, Math.min(limit, cap[e]));
                if (pushed > 0) {
                    cap[e] -= pushed;
                    cap[e ^ 1] += pushed; // the paired reverse edge
                    return pushed;
                }
            }
        }
        return 0;
    }

    /** After maxFlow: which vertices are on the source side of the minimum cut. */
    public boolean[] minCutSourceSide(int source) {
        boolean[] reach = new boolean[n];
        int[] queue = new int[n];
        int qh = 0, qt = 0;
        reach[source] = true;
        queue[qt++] = source;
        while (qh < qt) {
            int u = queue[qh++];
            for (int e = head[u]; e != -1; e = next[e]) {
                if (cap[e] > 0 && !reach[to[e]]) {
                    reach[to[e]] = true;
                    queue[qt++] = to[e];
                }
            }
        }
        return reach;
    }

    /** After maxFlow: the original edges {u, v, capacity} that cross the minimum cut. */
    public int[][] minCutEdges(int source) {
        boolean[] s = minCutSourceSide(source);
        int[][] result = new int[edgeCount / 2][];
        int count = 0;
        for (int u = 0; u < n; u++) {
            for (int e = head[u]; e != -1; e = next[e]) {
                if ((e & 1) == 0 && s[u] && !s[to[e]] && originalCap[e] > 0) {
                    result[count++] = new int[]{u, to[e], originalCap[e]};
                }
            }
        }
        return Arrays.copyOf(result, count);
    }

    public static void main(String[] args) {
        System.out.println("--- DINIC MAX FLOW / MIN CUT DEMO ---");

        // Same network as FordFulkerson.java's demo (CLRS example), max flow 23
        int[][] graph = {
            {0, 16, 13, 0, 0, 0},
            {0, 0, 10, 12, 0, 0},
            {0, 4, 0, 0, 14, 0},
            {0, 0, 9, 0, 0, 20},
            {0, 0, 0, 7, 0, 4},
            {0, 0, 0, 0, 0, 0}
        };
        Dinic dinic = fromMatrix(graph);
        System.out.println("Max flow 0 -> 5: " + dinic.maxFlow(0, 5)); // 23

        int[][] cut = dinic.minCutEdges(0);
        int total = 0;
        System.out.print("Min cut edges:  ");
        for (int[] e : cut) { System.out.print(e[0] + "->" + e[1] + " (" + e[2] + ")  "); total += e[2]; }
        System.out.println("\nMin cut capacity: " + total + " (equals the max flow)"); // 23
    }
}
