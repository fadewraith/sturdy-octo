package algorithms.graph.advanced;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * JOHNSON'S ALGORITHM (all-pairs shortest paths with negative edges)
 *
 * WHAT IT IS:
 * Shortest distances between EVERY pair of vertices in a directed graph that may
 * have NEGATIVE edge weights (but no negative cycles).
 *
 * WHEN TO USE THIS:
 * - All-pairs distances on SPARSE graphs with negative weights. Floyd-Warshall is
 *   O(V^3) regardless of edges; Johnson is O(V E log V), much faster when E << V^2.
 * - Arbitrage-style graphs, routing with credits/discounts, as a subroutine in
 *   min-cost flow (the same "potentials" trick).
 *
 * KEY IDEA - REWEIGHTING WITH POTENTIALS:
 * Dijkstra is fast but breaks on negative edges. Give every vertex a potential h(v)
 * and use  w'(u, v) = w(u, v) + h(u) - h(v).
 * Along any path s -> ... -> t the h terms TELESCOPE: w'(path) = w(path) + h(s) - h(t),
 * so every s-t path changes by the same amount and shortest paths stay shortest.
 * Choosing h(v) = shortest distance from a new vertex q (with 0-weight edges to all
 * vertices) makes every w' >= 0, by the triangle inequality h(v) <= h(u) + w(u, v).
 *
 * STRATEGY:
 * 1. Add vertex q with edges q -> v of weight 0; run BELLMAN-FORD from q to get h
 *    (and detect negative cycles - then there is no answer).
 * 2. Reweight all edges (now all >= 0).
 * 3. Run DIJKSTRA from every vertex on the reweighted graph.
 * 4. Undo the reweighting: dist(u, v) = dist'(u, v) - h(u) + h(v).
 *
 * COMPLEXITY:
 * Time: O(V E) for Bellman-Ford + V * O(E log V) for the Dijkstras = O(V E log V).
 * Space: O(V^2) for the output matrix, O(V + E) for the graph.
 */
public class JohnsonsAlgorithm {

    public static final long INF = Long.MAX_VALUE;

    /**
     * edges[i] = {from, to, weight}. Returns dist[u][v] (INF = unreachable), or null
     * if the graph contains a negative cycle.
     */
    public static long[][] allPairs(int n, int[][] edges) {
        // 1. Bellman-Ford from the virtual source q (h starts at 0 for every vertex
        //    because q -> v has weight 0)
        long[] h = new long[n];
        for (int round = 0; round < n; round++) {
            boolean changed = false;
            for (int[] e : edges) {
                if (h[e[0]] + e[2] < h[e[1]]) { h[e[1]] = h[e[0]] + e[2]; changed = true; }
            }
            if (!changed) break;
            if (round == n - 1) return null;               // still improving after V rounds: negative cycle
        }

        // 2. reweighted adjacency lists
        List<List<long[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] e : edges) {
            long w = e[2] + h[e[0]] - h[e[1]];             // >= 0 guaranteed
            adj.get(e[0]).add(new long[]{e[1], w});
        }

        // 3. Dijkstra from every source, 4. undo the reweighting
        long[][] dist = new long[n][];
        for (int s = 0; s < n; s++) {
            long[] d = dijkstra(adj, s);
            for (int v = 0; v < n; v++) if (d[v] != INF) d[v] = d[v] - h[s] + h[v];
            dist[s] = d;
        }
        return dist;
    }

    private static long[] dijkstra(List<List<long[]>> adj, int s) {
        long[] d = new long[adj.size()];
        Arrays.fill(d, INF);
        d[s] = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
        pq.add(new long[]{s, 0});
        while (!pq.isEmpty()) {
            long[] top = pq.poll();
            int u = (int) top[0];
            if (top[1] > d[u]) continue;                   // stale entry
            for (long[] e : adj.get(u)) {
                int v = (int) e[0];
                if (d[u] + e[1] < d[v]) { d[v] = d[u] + e[1]; pq.add(new long[]{v, d[v]}); }
            }
        }
        return d;
    }

    public static void main(String[] args) {
        System.out.println("--- JOHNSON'S ALGORITHM DEMO ---");
        int[][] edges = {{0, 1, -5}, {0, 2, 2}, {0, 3, 3}, {1, 2, 4}, {2, 3, 1}};
        long[][] d = allPairs(4, edges);
        System.out.println("Edges: 0->1 (-5), 0->2 (2), 0->3 (3), 1->2 (4), 2->3 (1)");
        for (int u = 0; u < 4; u++) {
            StringBuilder sb = new StringBuilder("  from " + u + ": ");
            for (int v = 0; v < 4; v++) sb.append(d[u][v] == INF ? "inf" : d[u][v]).append(v < 3 ? ", " : "");
            System.out.println(sb);
        }
        // from 0: 0, -5, -1, 0  (0 -> 1 -> 2 -> 3 beats the direct edges)
        System.out.println("Negative cycle 0 -> 1 -> 0 (total -1): " + (allPairs(2, new int[][]{{0, 1, 2}, {1, 0, -3}}) == null ? "detected" : "missed"));
    }
}
