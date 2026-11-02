package algorithms.tree;

/**
 * LOWEST COMMON ANCESTOR with BINARY LIFTING
 *
 * WHAT IT IS:
 * Answers "what is the LCA of u and v?" for MANY queries on a fixed rooted tree,
 * in O(log N) each after O(N log N) preprocessing.
 * (LowestCommonAncestor.java is the O(N)-per-query recursive version - fine for
 * one query, too slow for 100,000 of them.)
 *
 * WHEN TO USE THIS:
 * - Many LCA queries: distance between two nodes = depth[u] + depth[v] - 2*depth[lca].
 * - "k-th ancestor of a node" queries (company hierarchy, file system paths).
 * - Path queries on trees (combined with prefix sums from the root).
 *
 * STRATEGY:
 * up[j][v] = the 2^j-th ancestor of v (or -1 past the root).
 *   up[0][v]   = parent[v]
 *   up[j][v]   = up[j-1][ up[j-1][v] ]    (2^j steps = 2^(j-1) steps, twice)
 * k-th ancestor: write k in binary and jump by every power of two that's set.
 *   e.g. k = 13 = 8 + 4 + 1 -> three jumps instead of 13 single steps.
 * LCA(u, v):
 *   1. Lift the deeper node up so both are at the same depth.
 *   2. If they are now equal, that node is the LCA.
 *   3. Otherwise, for j from the biggest power down to 0: if up[j][u] != up[j][v],
 *      jump BOTH (they are still below the LCA). In the end u and v are the two
 *      children of the LCA just below it, so the answer is parent[u].
 *
 * INPUT: n nodes 0..n-1, the n-1 undirected edges of a tree, and the root.
 * Depths and parents are found with an iterative BFS (no recursion depth problems
 * on a long chain-like tree).
 *
 * COMPLEXITY:
 * Preprocessing: Time O(N log N), Space O(N log N).
 * Query (LCA, k-th ancestor, distance): O(log N).
 */
public class LCABinaryLifting {

    private final int n, LOG;
    private final int[][] up;
    private final int[] depth;

    public LCABinaryLifting(int n, int[][] edges, int root) {
        if (edges.length != n - 1) throw new IllegalArgumentException("a tree with n nodes has n - 1 edges");
        this.n = n;
        int log = 1;
        while ((1 << log) < n) log++;
        this.LOG = log;
        this.up = new int[LOG][n];
        this.depth = new int[n];

        // Compressed adjacency lists ("CSR"): neighbours of v are adj[start[v] .. start[v+1]-1]
        int[] degree = new int[n];
        for (int[] e : edges) { degree[e[0]]++; degree[e[1]]++; }
        int[] start = new int[n + 1];
        for (int v = 0; v < n; v++) start[v + 1] = start[v] + degree[v];
        int[] fill = new int[n];
        int[] adj = new int[2 * (n - 1)];
        for (int[] e : edges) {
            adj[start[e[0]] + fill[e[0]]++] = e[1];
            adj[start[e[1]] + fill[e[1]]++] = e[0];
        }

        // BFS from the root to get parent (= up[0]) and depth
        boolean[] seen = new boolean[n];
        int[] queue = new int[n];
        int head = 0, tail = 0;
        queue[tail++] = root;
        seen[root] = true;
        up[0][root] = -1;
        while (head < tail) {
            int u = queue[head++];
            for (int i = start[u]; i < start[u + 1]; i++) {
                int v = adj[i];
                if (!seen[v]) {
                    seen[v] = true;
                    up[0][v] = u;
                    depth[v] = depth[u] + 1;
                    queue[tail++] = v;
                }
            }
        }
        if (tail != n) throw new IllegalArgumentException("edges do not form a connected tree");

        // Fill the jump table level by level
        for (int j = 1; j < LOG; j++) {
            for (int v = 0; v < n; v++) {
                int mid = up[j - 1][v];
                up[j][v] = mid == -1 ? -1 : up[j - 1][mid];
            }
        }
    }

    public int depth(int v) {
        return depth[v];
    }

    /** The k-th ancestor of v (k = 0 is v itself), or -1 if it goes past the root. */
    public int kthAncestor(int v, int k) {
        if (k > depth[v]) return -1;
        for (int j = 0; j < LOG && v != -1; j++) {
            if (((k >> j) & 1) == 1) v = up[j][v];
        }
        return v;
    }

    public int lca(int u, int v) {
        // 1. bring both to the same depth
        if (depth[u] < depth[v]) { int t = u; u = v; v = t; }
        u = kthAncestor(u, depth[u] - depth[v]);
        if (u == v) return u;

        // 3. jump both while they stay different
        for (int j = LOG - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }
        return up[0][u];
    }

    /** Number of edges on the path between u and v. */
    public int distance(int u, int v) {
        return depth[u] + depth[v] - 2 * depth[lca(u, v)];
    }

    public static void main(String[] args) {
        System.out.println("--- LCA (BINARY LIFTING) DEMO ---");
        //            0
        //          /   \
        //         1     2
        //        / \     \
        //       3   4     5
        //      /         / \
        //     6         7   8
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5}, {3, 6}, {5, 7}, {5, 8}};
        LCABinaryLifting tree = new LCABinaryLifting(9, edges, 0);

        System.out.println("LCA(6, 4) = " + tree.lca(6, 4));   // 1
        System.out.println("LCA(7, 8) = " + tree.lca(7, 8));   // 5
        System.out.println("LCA(6, 8) = " + tree.lca(6, 8));   // 0
        System.out.println("LCA(3, 6) = " + tree.lca(3, 6));   // 3 (a node is its own ancestor)
        System.out.println("2nd ancestor of 7 = " + tree.kthAncestor(7, 2)); // 2
        System.out.println("4th ancestor of 7 = " + tree.kthAncestor(7, 4)); // -1 (past the root)
        System.out.println("distance(6, 7) = " + tree.distance(6, 7));       // 6
    }
}
