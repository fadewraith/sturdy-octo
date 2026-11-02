package algorithms.graph;

import java.util.Arrays;

/**
 * HOPCROFT-KARP (Maximum Bipartite Matching)
 *
 * WHAT IT IS:
 * A bipartite graph has a LEFT side and a RIGHT side, with edges only between the
 * sides. A MATCHING picks edges so that no vertex is used twice. Hopcroft-Karp finds
 * a matching of maximum size in O(E * sqrt(V)).
 *
 * WHEN TO USE THIS:
 * - Assigning workers to jobs, students to projects, doctors to shifts
 *   (each person gets at most one, each slot takes at most one).
 * - Minimum vertex cover in bipartite graphs (Konig's theorem: same size as the
 *   maximum matching), minimum path cover in DAGs.
 *
 * KEY IDEA - AUGMENTING PATHS:
 * An augmenting path starts at a FREE left vertex, ends at a FREE right vertex, and
 * alternates "edge not in matching", "edge in matching", ... Flipping every edge on
 * it (in <-> out) grows the matching by exactly one. No augmenting path left means
 * the matching is maximum (Berge's theorem).
 * The simple approach (Kuhn's algorithm) finds one path per DFS: O(V * E).
 *
 * STRATEGY (find MANY shortest augmenting paths per round):
 * 1. BFS from ALL free left vertices at once, building layers by distance
 *    (left -> right via any edge, right -> left via its matched edge).
 *    Stop the layering at the first level that reaches a free right vertex.
 * 2. DFS from each free left vertex, only stepping to the NEXT layer, to find a
 *    maximal set of vertex-disjoint shortest augmenting paths. Flip them all.
 *    A vertex whose DFS fails is marked dead (dist = INF) so it's never retried.
 * 3. Repeat. Only O(sqrt(V)) rounds are ever needed.
 *
 * INPUT: nLeft, nRight, and adj[u] = the right vertices that left vertex u can match.
 *
 * COMPLEXITY:
 * Time: O(E * sqrt(V))
 * Space: O(V) (plus O(V) recursion depth in the DFS).
 */
public class HopcroftKarp {

    private static final int INF = Integer.MAX_VALUE;

    private final int nLeft;
    private final int[][] adj;
    private final int[] matchLeft;  // matchLeft[u] = right vertex matched to u, or -1
    private final int[] matchRight; // matchRight[v] = left vertex matched to v, or -1
    private final int[] dist;
    private int matching;

    public HopcroftKarp(int nLeft, int nRight, int[][] adj) {
        this.nLeft = nLeft;
        this.adj = adj;
        this.matchLeft = new int[nLeft];
        this.matchRight = new int[nRight];
        this.dist = new int[nLeft];
        Arrays.fill(matchLeft, -1);
        Arrays.fill(matchRight, -1);
        this.matching = run();
    }

    public int maxMatching() { return matching; }

    /** Copy of the matching from the left side: result[u] = right partner or -1. */
    public int[] leftMatches() { return matchLeft.clone(); }

    private int run() {
        int size = 0;
        while (bfs()) {
            for (int u = 0; u < nLeft; u++) {
                if (matchLeft[u] == -1 && dfs(u)) size++;
            }
        }
        return size;
    }

    // Builds the layers; returns true if some free right vertex is reachable
    private boolean bfs() {
        int[] queue = new int[nLeft];
        int head = 0, tail = 0;
        for (int u = 0; u < nLeft; u++) {
            if (matchLeft[u] == -1) { dist[u] = 0; queue[tail++] = u; }
            else dist[u] = INF;
        }
        boolean foundFree = false;
        while (head < tail) {
            int u = queue[head++];
            for (int v : adj[u]) {
                int w = matchRight[v];
                if (w == -1) foundFree = true;            // augmenting path ends here
                else if (dist[w] == INF) {                // walk back along the matched edge
                    dist[w] = dist[u] + 1;
                    queue[tail++] = w;
                }
            }
        }
        return foundFree;
    }

    // Tries to find an augmenting path from left vertex u along the layers
    private boolean dfs(int u) {
        for (int v : adj[u]) {
            int w = matchRight[v];
            if (w == -1 || (dist[w] == dist[u] + 1 && dfs(w))) {
                matchLeft[u] = v;   // flip: u-v joins the matching
                matchRight[v] = u;
                return true;
            }
        }
        dist[u] = INF; // dead end - don't try u again this round
        return false;
    }

    public static void main(String[] args) {
        System.out.println("--- HOPCROFT-KARP DEMO ---");

        // Workers 0..3 and the jobs (0..3) each one can do
        int[][] canDo = {
            {0, 1},    // worker 0
            {0},       // worker 1
            {1, 2},    // worker 2
            {2}        // worker 3 (competes with worker 2 for job 2)
        };
        HopcroftKarp hk = new HopcroftKarp(4, 4, canDo);
        System.out.println("Maximum matching: " + hk.maxMatching());              // 3
        System.out.println("Worker -> job:    " + Arrays.toString(hk.leftMatches())); // [0, -1, 1, 2] (one of several maximum matchings)
        // One worker must stay idle: jobs 0, 1 and 2 are the only jobs anyone can do.

        int[][] perfect = {{0, 1}, {0, 2}, {1, 2}};
        System.out.println("Perfect matching size: " + new HopcroftKarp(3, 3, perfect).maxMatching()); // 3
    }
}
