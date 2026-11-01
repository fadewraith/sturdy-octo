/**
 * WHAT IT IS: Contraction Hierarchies (CH) is a preprocessing technique to speed up shortest-path routing in large graphs.
 * STRATEGY: Order nodes by "importance" and contract them (remove them and add shortcut edges between their neighbors if they formed the shortest path). Query using bidirectional Dijkstra where you only relax edges going to "more important" nodes.
 * TIME/SPACE COMPLEXITY: Query Time: O(log V) or O(sqrt V) depending on structure. Space: O(V + E) with added shortcuts. Precomputation takes significant time.
 * REAL-WORLD ANALOGY / USE CASE: Google Maps routing across the country, where local roads are ignored in favor of highways (shortcuts) for the bulk of the journey.
 * WHEN TO USE / COMBINATION: When dealing with massive static road networks where you need millions of queries per second and can afford upfront precomputation.
 *
 * THE TWO PIECES THAT MAKE IT CORRECT AND FAST:
 * 1. WITNESS SEARCH: before adding shortcut u-w (through v), run a small Dijkstra from u
 *    that avoids v. If it finds a path to w that is no longer than u-v-w, a "witness"
 *    exists and the shortcut is unnecessary. Without this, CH adds far too many shortcuts.
 * 2. NODE ORDERING: contract "unimportant" nodes first. Importance here is the classic
 *    EDGE DIFFERENCE (shortcuts that would be added - edges removed) plus the number of
 *    already-contracted neighbors (spreads contraction evenly). Priorities change as the
 *    graph changes, so we use LAZY updates: re-check a node's priority when it is popped.
 *
 * WHY THE QUERY IS CORRECT: every shortest path, after contraction, can be rewritten as
 * a path that only goes UP in rank and then only DOWN. So a forward search going up from
 * the source and a backward search going up from the target must meet at the path's
 * highest node.
 *
 * PSEUDOCODE:
 * Preprocessing:
 * while nodes remain:
 *   v = uncontracted node with the lowest priority (lazy re-check)
 *   for each pair (u, w) of uncontracted neighbors of v:
 *     if no witness path u->w avoiding v with cost <= c(u,v) + c(v,w):
 *       add shortcut edge (u, w) with weight c(u,v) + c(v,w)
 *   remove v from the graph, rank[v] = next rank
 * Query:
 * Bidirectional Dijkstra only following upward edges (to higher-rank nodes);
 * answer = min over nodes x of distForward[x] + distBackward[x].
 */
package algorithms.graph.advanced;
// These aren't separate algorithms so much as OPTIMIZATIONS/ADAPTATIONS of Dijkstra's and A* for specific real-world constraints (memory, dynamic changes, precomputation budget, grid structure).

import java.util.*;

public class ContractionHierarchies {

    static class Edge {
        int to; long weight;
        Edge(int to, long weight) { this.to = to; this.weight = weight; }
    }

    private static final long INF = Long.MAX_VALUE / 4;
    private static final int WITNESS_SETTLE_LIMIT = 200; // keeps preprocessing fast; a missed witness only adds an extra (still correct) shortcut

    private final int n;
    private final List<Map<Integer, Long>> remaining; // graph of not-yet-contracted nodes
    private final List<List<Edge>> up = new ArrayList<>(); // final search graph: edges to higher rank
    private final int[] rank;
    private final boolean[] contracted;
    private final int[] contractedNeighbors;
    private int shortcutsAdded = 0;

    /** Builds the hierarchy for an UNDIRECTED graph with non-negative weights. */
    public ContractionHierarchies(int n, int[][] edges) {
        this.n = n;
        this.rank = new int[n];
        this.contracted = new boolean[n];
        this.contractedNeighbors = new int[n];
        remaining = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            remaining.add(new HashMap<>());
            up.add(new ArrayList<>());
        }
        // Store every edge (original + later shortcuts) to orient it once ranks are known
        List<long[]> allEdges = new ArrayList<>();
        for (int[] e : edges) {
            if (e[0] == e[1]) continue;
            addRemainingEdge(e[0], e[1], e[2]);
            allEdges.add(new long[]{e[0], e[1], e[2]});
        }
        preprocess(allEdges);
    }

    private void addRemainingEdge(int u, int v, long w) {
        remaining.get(u).merge(v, w, Math::min); // keep the cheaper of parallel edges
        remaining.get(v).merge(u, w, Math::min);
    }

    // ---------- preprocessing ----------

    private void preprocess(List<long[]> allEdges) {
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        for (int v = 0; v < n; v++) pq.add(new long[]{priority(v), v});

        int nextRank = 0;
        while (!pq.isEmpty()) {
            long[] top = pq.poll();
            int v = (int) top[1];
            if (contracted[v]) continue;
            // Lazy update: the priority may be outdated. If it got worse than the next
            // candidate, put it back and try the next one instead.
            long fresh = priority(v);
            if (!pq.isEmpty() && fresh > pq.peek()[0]) {
                pq.add(new long[]{fresh, v});
                continue;
            }
            for (long[] sc : contract(v, false)) allEdges.add(sc);
            rank[v] = nextRank++;
        }

        // Orient every edge upward (from lower rank to higher rank) for the query
        for (long[] e : allEdges) {
            int a = (int) e[0], b = (int) e[1];
            if (rank[a] < rank[b]) up.get(a).add(new Edge(b, e[2]));
            else up.get(b).add(new Edge(a, e[2]));
        }
    }

    private long priority(int v) {
        int shortcuts = contract(v, true).size();
        int edgeDifference = shortcuts - remaining.get(v).size();
        return edgeDifference + contractedNeighbors[v];
    }

    /**
     * Contracts v (or just counts the needed shortcuts when simulate = true).
     * Returns the shortcuts as {u, w, weight}.
     */
    private List<long[]> contract(int v, boolean simulate) {
        List<long[]> shortcuts = new ArrayList<>();
        List<Integer> nbrs = new ArrayList<>(remaining.get(v).keySet());
        for (int i = 0; i + 1 < nbrs.size(); i++) { // the last neighbor has no pair partner left
            int u = nbrs.get(i);
            long cu = remaining.get(v).get(u);
            long maxNeeded = 0;
            for (int j = i + 1; j < nbrs.size(); j++) {
                maxNeeded = Math.max(maxNeeded, cu + remaining.get(v).get(nbrs.get(j)));
            }
            Map<Integer, Long> dist = witnessSearch(u, v, maxNeeded);
            for (int j = i + 1; j < nbrs.size(); j++) {
                int w = nbrs.get(j);
                long via = cu + remaining.get(v).get(w);
                if (dist.getOrDefault(w, INF) > via) shortcuts.add(new long[]{u, w, via}); // no witness
            }
        }
        if (!simulate) {
            for (long[] sc : shortcuts) {
                addRemainingEdge((int) sc[0], (int) sc[1], sc[2]);
                shortcutsAdded++;
            }
            for (int u : nbrs) {
                remaining.get(u).remove(v);
                contractedNeighbors[u]++;
            }
            remaining.get(v).clear();
            contracted[v] = true;
        }
        return shortcuts;
    }

    // Dijkstra from 'source' in the remaining graph, never entering 'avoid', stopping at maxDist
    private Map<Integer, Long> witnessSearch(int source, int avoid, long maxDist) {
        Map<Integer, Long> dist = new HashMap<>();
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        dist.put(source, 0L);
        pq.add(new long[]{0, source});
        int settled = 0;
        while (!pq.isEmpty() && settled < WITNESS_SETTLE_LIMIT) {
            long[] cur = pq.poll();
            int x = (int) cur[1];
            if (cur[0] > dist.get(x)) continue;
            if (cur[0] > maxDist) break;
            settled++;
            for (Map.Entry<Integer, Long> e : remaining.get(x).entrySet()) {
                int y = e.getKey();
                if (y == avoid) continue;
                long nd = cur[0] + e.getValue();
                if (nd < dist.getOrDefault(y, INF)) {
                    dist.put(y, nd);
                    pq.add(new long[]{nd, y});
                }
            }
        }
        return dist;
    }

    // ---------- query ----------

    /** Shortest distance between src and dest, or -1 if unreachable. */
    public long query(int src, int dest) {
        if (src == dest) return 0;
        long[] distF = new long[n], distB = new long[n];
        Arrays.fill(distF, INF);
        Arrays.fill(distB, INF);
        PriorityQueue<long[]> pqF = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        PriorityQueue<long[]> pqB = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        distF[src] = 0; pqF.add(new long[]{0, src});
        distB[dest] = 0; pqB.add(new long[]{0, dest});
        long best = INF;

        // Alternate between the searches. A search can stop once its smallest
        // tentative distance is >= best: it cannot improve the answer any more.
        while (!pqF.isEmpty() || !pqB.isEmpty()) {
            if (!pqF.isEmpty()) best = step(pqF, distF, distB, best);
            if (!pqB.isEmpty()) best = step(pqB, distB, distF, best);
        }
        return best >= INF ? -1 : best;
    }

    private long step(PriorityQueue<long[]> pq, long[] dist, long[] otherDist, long best) {
        long[] cur = pq.poll();
        int u = (int) cur[1];
        if (cur[0] > dist[u]) return best;               // stale
        if (cur[0] >= best) { pq.clear(); return best; } // this side is done
        if (otherDist[u] < INF) best = Math.min(best, dist[u] + otherDist[u]); // searches meet
        for (Edge e : up.get(u)) {                       // only UPWARD edges
            long nd = dist[u] + e.weight;
            if (nd < dist[e.to]) {
                dist[e.to] = nd;
                pq.add(new long[]{nd, e.to});
            }
        }
        return best;
    }

    public int getShortcutsAdded() {
        return shortcutsAdded;
    }

    // Plain Dijkstra, used by the demo to double-check answers
    static long dijkstra(int n, int[][] edges, int src, int dest) {
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] e : edges) {
            adj.get(e[0]).add(new int[]{e[1], e[2]});
            adj.get(e[1]).add(new int[]{e[0], e[2]});
        }
        long[] dist = new long[n];
        Arrays.fill(dist, INF);
        dist[src] = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        pq.add(new long[]{0, src});
        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            int u = (int) cur[1];
            if (cur[0] > dist[u]) continue;
            for (int[] e : adj.get(u)) {
                if (dist[u] + e[1] < dist[e[0]]) {
                    dist[e[0]] = dist[u] + e[1];
                    pq.add(new long[]{dist[e[0]], e[0]});
                }
            }
        }
        return dist[dest] >= INF ? -1 : dist[dest];
    }

    public static void main(String[] args) {
        System.out.println("--- Contraction Hierarchies Tests ---");
        // 0-1-2-3-4 line graph with unit weights
        int[][] line = {{0, 1, 1}, {1, 2, 1}, {2, 3, 1}, {3, 4, 1}};
        ContractionHierarchies ch = new ContractionHierarchies(5, line);
        System.out.println("Test 1 (0 to 4): " + ch.query(0, 4)); // Expected: 4
        System.out.println("Test 2 (1 to 3): " + ch.query(1, 3)); // Expected: 2
        System.out.println("Test 3 (Start == Goal): " + ch.query(2, 2)); // Expected: 0

        // A "road grid" 20 x 20 with random weights
        int side = 20, n = side * side;
        Random rand = new Random(1);
        List<int[]> roads = new ArrayList<>();
        for (int r = 0; r < side; r++) {
            for (int c = 0; c < side; c++) {
                int id = r * side + c;
                if (c + 1 < side) roads.add(new int[]{id, id + 1, 1 + rand.nextInt(9)});
                if (r + 1 < side) roads.add(new int[]{id, id + side, 1 + rand.nextInt(9)});
            }
        }
        int[][] roadArr = roads.toArray(new int[0][]);
        ContractionHierarchies grid = new ContractionHierarchies(n, roadArr);
        int checks = 0, agree = 0;
        for (int i = 0; i < 200; i++) {
            int s = rand.nextInt(n), t = rand.nextInt(n);
            checks++;
            if (grid.query(s, t) == dijkstra(n, roadArr, s, t)) agree++;
        }
        System.out.println("Test 4 (20x20 road grid, " + grid.getShortcutsAdded() + " shortcuts): "
                + agree + "/" + checks + " queries match plain Dijkstra");
    }
}
