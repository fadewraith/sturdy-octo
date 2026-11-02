package algorithms.graph.advanced;

import java.util.ArrayList;
import java.util.List;

/**
 * HEAVY-LIGHT DECOMPOSITION (path queries and updates on a tree)
 *
 * WHAT IT IS:
 * Answers "sum / max of the values on the path from u to v" and "change the value
 * of node x" on a tree, each in O(log^2 n), by cutting the tree into chains that
 * are laid out contiguously in a segment tree.
 *
 * WHEN TO USE THIS:
 * - Path queries on trees with updates: network links with changing load (max
 *   bandwidth on the route), "toll on the path between two cities", competitive
 *   programming classics (QTREE). Also gives LCA for free.
 * - Without updates, prefix sums from the root + LCA (LCABinaryLifting.java) suffice.
 *
 * KEY IDEA:
 * For every node, its HEAVY child is the child with the largest subtree; the edge
 * to it is heavy, all other child edges are light. Heavy edges form CHAINS.
 * Going down a LIGHT edge at least halves the subtree size, so any root-to-node
 * path crosses at most log2(n) light edges = touches at most log n chains.
 * Number the nodes in a DFS that always visits the heavy child FIRST: each chain
 * then occupies a CONTIGUOUS range of positions -> one segment-tree query per chain.
 *
 * PATH QUERY (u, v): while u and v are on different chains, take the one whose
 * chain HEAD is deeper, query [pos(head), pos(node)], and jump to head's parent.
 * When both are on one chain, query the range between them. The node where they
 * meet is the LCA.
 *
 * COMPLEXITY:
 * Build: O(n). Path query / LCA: O(log^2 n) (log n chains x log n segment tree).
 * Point update: O(log n). Space: O(n).
 */
public class HeavyLightDecomposition {

    private final int n;
    private final int[] parent, depth, heavy, head, pos, size;
    private final long[] sumTree, maxTree;
    private final int segSize;

    /** Tree with n nodes, n-1 undirected edges, rooted at `root`, initial node values. */
    public HeavyLightDecomposition(int n, int[][] edges, int root, long[] values) {
        this.n = n;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] e : edges) { adj.get(e[0]).add(e[1]); adj.get(e[1]).add(e[0]); }
        parent = new int[n]; depth = new int[n]; heavy = new int[n]; head = new int[n]; pos = new int[n]; size = new int[n];

        // 1. iterative DFS order, parents and depths
        int[] order = new int[n];
        int[] stack = new int[n];
        int sp = 0, cnt = 0;
        boolean[] seen = new boolean[n];
        stack[sp++] = root;
        seen[root] = true;
        parent[root] = -1;
        while (sp > 0) {
            int u = stack[--sp];
            order[cnt++] = u;
            for (int v : adj.get(u)) if (!seen[v]) { seen[v] = true; parent[v] = u; depth[v] = depth[u] + 1; stack[sp++] = v; }
        }
        if (cnt != n) throw new IllegalArgumentException("edges do not form a connected tree");

        // 2. subtree sizes (children before parents = reverse DFS order) and heavy children
        java.util.Arrays.fill(heavy, -1);
        for (int i = n - 1; i >= 0; i--) {
            int u = order[i];
            size[u]++;
            if (parent[u] >= 0) {
                int p = parent[u];
                size[p] += size[u];
                if (heavy[p] == -1 || size[u] > size[heavy[p]]) heavy[p] = u;
            }
        }
        // (sizes of p are complete only after all children, which reverse DFS order guarantees)

        // 3. decompose: walk each chain from its head, heavy child first, numbering positions
        int curPos = 0;
        int[] heads = new int[n];
        int hs = 0;
        heads[hs++] = root;
        head[root] = root;
        while (hs > 0) {
            int h = heads[--hs];
            for (int u = h; u != -1; u = heavy[u]) {
                head[u] = h;
                pos[u] = curPos++;
                for (int v : adj.get(u)) {
                    if (v != parent[u] && v != heavy[u]) heads[hs++] = v; // light child starts a new chain
                }
            }
        }

        // 4. segment tree (iterative, sum + max) over positions
        int s = 1;
        while (s < n) s <<= 1;
        segSize = s;
        sumTree = new long[2 * s];
        maxTree = new long[2 * s];
        java.util.Arrays.fill(maxTree, Long.MIN_VALUE);
        for (int v = 0; v < n; v++) { sumTree[s + pos[v]] = values[v]; maxTree[s + pos[v]] = values[v]; }
        for (int i = s - 1; i >= 1; i--) pull(i);
    }

    private void pull(int i) {
        sumTree[i] = sumTree[2 * i] + sumTree[2 * i + 1];
        maxTree[i] = Math.max(maxTree[2 * i], maxTree[2 * i + 1]);
    }

    public void update(int node, long value) {
        int i = segSize + pos[node];
        sumTree[i] = value;
        maxTree[i] = value;
        for (i >>= 1; i >= 1; i >>= 1) pull(i);
    }

    // {sum, max} over positions [l, r]
    private long[] segQuery(int l, int r) {
        long sum = 0, max = Long.MIN_VALUE;
        for (l += segSize, r += segSize + 1; l < r; l >>= 1, r >>= 1) {
            if ((l & 1) == 1) { sum += sumTree[l]; max = Math.max(max, maxTree[l]); l++; }
            if ((r & 1) == 1) { --r; sum += sumTree[r]; max = Math.max(max, maxTree[r]); }
        }
        return new long[]{sum, max};
    }

    /** {sum, max, lca} of the node values on the path u .. v (both included). */
    private long[] path(int u, int v) {
        long sum = 0, max = Long.MIN_VALUE;
        while (head[u] != head[v]) {
            if (depth[head[u]] < depth[head[v]]) { int t = u; u = v; v = t; } // move the deeper chain up
            long[] q = segQuery(pos[head[u]], pos[u]);
            sum += q[0];
            max = Math.max(max, q[1]);
            u = parent[head[u]];
        }
        if (depth[u] > depth[v]) { int t = u; u = v; v = t; }   // same chain: u is the LCA
        long[] q = segQuery(pos[u], pos[v]);
        return new long[]{sum + q[0], Math.max(max, q[1]), u};
    }

    public long pathSum(int u, int v) { return path(u, v)[0]; }
    public long pathMax(int u, int v) { return path(u, v)[1]; }
    public int lca(int u, int v) { return (int) path(u, v)[2]; }

    /** Sum over the whole subtree of v: a subtree is also one contiguous range! */
    public long subtreeSum(int v) { return segQuery(pos[v], pos[v] + size[v] - 1)[0]; }

    public static void main(String[] args) {
        System.out.println("--- HEAVY-LIGHT DECOMPOSITION DEMO ---");
        //          0(5)
        //        /     \
        //      1(3)    2(8)
        //     /  \       \
        //   3(1) 4(7)    5(2)
        //        /
        //      6(9)
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5}, {4, 6}};
        long[] values = {5, 3, 8, 1, 7, 2, 9};
        HeavyLightDecomposition hld = new HeavyLightDecomposition(7, edges, 0, values);
        System.out.println("path 6 -> 5: sum " + hld.pathSum(6, 5) + ", max " + hld.pathMax(6, 5) + ", lca " + hld.lca(6, 5));
        // 9 + 7 + 3 + 5 + 8 + 2 = 34, max 9, lca 0
        System.out.println("path 3 -> 6: sum " + hld.pathSum(3, 6) + ", lca " + hld.lca(3, 6)); // 1 + 3 + 7 + 9 = 20, lca 1
        hld.update(4, 100);
        System.out.println("after node 4 := 100, path 6 -> 5 max " + hld.pathMax(6, 5) + ", subtree(1) sum " + hld.subtreeSum(1)); // 100, 113
    }
}
