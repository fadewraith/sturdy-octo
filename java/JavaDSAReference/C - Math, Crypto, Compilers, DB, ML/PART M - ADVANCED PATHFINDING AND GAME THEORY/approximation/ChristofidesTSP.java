package algorithms.approximation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * CHRISTOFIDES' ALGORITHM (1.5-approximation for metric TSP)
 *
 * WHAT IT IS:
 * A tour visiting every city once, guaranteed at most 1.5x the optimal length, when
 * distances obey the triangle inequality (e.g. straight-line distances). The
 * simpler MST-doubling method (TSPApproximation.java) only guarantees 2x.
 * For almost 50 years this was the best known guarantee for metric TSP.
 *
 * WHEN TO USE THIS:
 * - Routing (delivery, drilling holes in circuit boards, warehouse picking) when a
 *   provable bound matters; in practice it's often followed by 2-opt local search
 *   (Metaheuristics.java) to polish the tour.
 *
 * STRATEGY:
 * 1. MINIMUM SPANNING TREE T (Prim). cost(T) <= OPT (remove one edge from the
 *    optimal tour and you get a spanning tree).
 * 2. O = vertices with ODD degree in T (always an even number of them).
 * 3. MINIMUM-WEIGHT PERFECT MATCHING M on O. cost(M) <= OPT / 2 (the optimal tour
 *    restricted to O splits into two perfect matchings; the cheaper is <= half).
 * 4. T + M has all degrees even -> it has an EULERIAN CIRCUIT (Hierholzer).
 * 5. SHORTCUT: walk the circuit and skip already-visited cities. By the triangle
 *    inequality shortcuts never make the tour longer.
 * Total <= cost(T) + cost(M) <= 1.5 * OPT.
 *
 * MATCHING: an exact minimum-weight perfect matching needs Edmonds' blossom
 * algorithm (long and intricate). Here it is computed EXACTLY with a bitmask DP
 * when there are <= 20 odd vertices (O(2^k * k)), and greedily (closest pairs first)
 * otherwise - the 1.5 guarantee only holds for the exact version.
 *
 * COMPLEXITY:
 * MST O(n^2), matching O(2^k k) exact or O(k^2 log k) greedy, Euler + shortcut O(n).
 */
public class ChristofidesTSP {

    public static final class Result {
        public final int[] tour;
        public final double length;
        public final boolean exactMatching;
        Result(int[] tour, double length, boolean exactMatching) { this.tour = tour; this.length = length; this.exactMatching = exactMatching; }
    }

    public static double dist(double[][] p, int a, int b) { return Math.hypot(p[a][0] - p[b][0], p[a][1] - p[b][1]); }

    public static double tourLength(double[][] p, int[] tour) {
        double s = 0;
        for (int i = 0; i < tour.length; i++) s += dist(p, tour[i], tour[(i + 1) % tour.length]);
        return s;
    }

    public static Result solve(double[][] p) {
        int n = p.length;
        if (n <= 2) { int[] t = new int[n]; for (int i = 0; i < n; i++) t[i] = i; return new Result(t, tourLength(p, t), true); }

        // 1. Prim's MST (dense graph: O(n^2) with arrays)
        List<List<Integer>> multigraph = new ArrayList<>();
        for (int i = 0; i < n; i++) multigraph.add(new ArrayList<>());
        double[] best = new double[n];
        int[] from = new int[n];
        boolean[] inTree = new boolean[n];
        Arrays.fill(best, Double.MAX_VALUE);
        best[0] = 0;
        from[0] = -1;
        for (int it = 0; it < n; it++) {
            int u = -1;
            for (int v = 0; v < n; v++) if (!inTree[v] && (u == -1 || best[v] < best[u])) u = v;
            inTree[u] = true;
            if (from[u] >= 0) { multigraph.get(u).add(from[u]); multigraph.get(from[u]).add(u); }
            for (int v = 0; v < n; v++) {
                double d = dist(p, u, v);
                if (!inTree[v] && d < best[v]) { best[v] = d; from[v] = u; }
            }
        }

        // 2. odd-degree vertices
        List<Integer> odd = new ArrayList<>();
        for (int v = 0; v < n; v++) if (multigraph.get(v).size() % 2 == 1) odd.add(v);

        // 3. minimum-weight perfect matching on them, added to the multigraph
        boolean exact = odd.size() <= 20;
        int[][] pairs = exact ? exactMatching(p, odd) : greedyMatching(p, odd);
        for (int[] pr : pairs) { multigraph.get(pr[0]).add(pr[1]); multigraph.get(pr[1]).add(pr[0]); }

        // 4. Eulerian circuit (Hierholzer) on the multigraph, 5. shortcut repeats
        int[] tour = new int[n];
        boolean[] visited = new boolean[n];
        int k = 0;
        for (int v : eulerCircuit(multigraph, 0)) {
            if (!visited[v]) { visited[v] = true; tour[k++] = v; }
        }
        return new Result(tour, tourLength(p, tour), exact);
    }

    /** Exact min-weight perfect matching by DP over subsets: f(mask) = best matching of the vertices in mask. */
    static int[][] exactMatching(double[][] p, List<Integer> odd) {
        int k = odd.size();
        double[] f = new double[1 << k];
        int[] choice = new int[1 << k];
        Arrays.fill(f, Double.MAX_VALUE);
        f[0] = 0;
        for (int mask = 1; mask < (1 << k); mask++) {
            if (Integer.bitCount(mask) % 2 == 1) continue;
            int i = Integer.numberOfTrailingZeros(mask);      // always match the lowest vertex first
            for (int j = i + 1; j < k; j++) {
                if ((mask >> j & 1) == 0) continue;
                int rest = mask & ~(1 << i) & ~(1 << j);
                double c = f[rest] + dist(p, odd.get(i), odd.get(j));
                if (c < f[mask]) { f[mask] = c; choice[mask] = j; }
            }
        }
        int[][] pairs = new int[k / 2][];
        int mask = (1 << k) - 1, idx = 0;
        while (mask != 0) {
            int i = Integer.numberOfTrailingZeros(mask), j = choice[mask];
            pairs[idx++] = new int[]{odd.get(i), odd.get(j)};
            mask &= ~(1 << i) & ~(1 << j);
        }
        return pairs;
    }

    static int[][] greedyMatching(double[][] p, List<Integer> odd) {
        List<int[]> cand = new ArrayList<>();
        for (int a = 0; a < odd.size(); a++) for (int b = a + 1; b < odd.size(); b++) cand.add(new int[]{odd.get(a), odd.get(b)});
        cand.sort((x, y) -> Double.compare(dist(p, x[0], x[1]), dist(p, y[0], y[1])));
        boolean[] used = new boolean[p.length];
        List<int[]> out = new ArrayList<>();
        for (int[] c : cand) if (!used[c[0]] && !used[c[1]]) { used[c[0]] = used[c[1]] = true; out.add(c); }
        return out.toArray(new int[0][]);
    }

    /** Hierholzer on an undirected multigraph given as adjacency lists (edges consumed). */
    static List<Integer> eulerCircuit(List<List<Integer>> g, int start) {
        List<Deque<Integer>> adj = new ArrayList<>();
        for (List<Integer> l : g) adj.add(new ArrayDeque<>(l));
        List<Integer> circuit = new ArrayList<>();
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            int u = stack.peek();
            if (adj.get(u).isEmpty()) { circuit.add(stack.pop()); continue; }
            int v = adj.get(u).poll();
            adj.get(v).removeFirstOccurrence(u);              // remove the reverse copy of the edge
            stack.push(v);
        }
        return circuit;
    }

    public static void main(String[] args) {
        System.out.println("--- CHRISTOFIDES TSP DEMO ---");
        java.util.Random rng = new java.util.Random(12);
        double[][] cities = new double[9][];
        for (int i = 0; i < cities.length; i++) cities[i] = new double[]{rng.nextInt(100), rng.nextInt(100)};
        Result r = solve(cities);
        double opt = optimal(cities);
        System.out.printf("Tour %s%nChristofides length %.2f, optimal %.2f, ratio %.3f (guarantee <= 1.5)%n",
                Arrays.toString(r.tour), r.length, opt, r.length / opt);
    }

    /** Exact optimum by Held-Karp DP (only for small n). */
    public static double optimal(double[][] p) {
        int n = p.length;
        if (n <= 2) return tourLength(p, n == 2 ? new int[]{0, 1} : new int[n]);
        double[][] dp = new double[1 << n][n];
        for (double[] row : dp) Arrays.fill(row, Double.MAX_VALUE);
        dp[1][0] = 0;
        for (int mask = 1; mask < (1 << n); mask++) {
            if ((mask & 1) == 0) continue;
            for (int last = 0; last < n; last++) {
                if (dp[mask][last] == Double.MAX_VALUE) continue;
                for (int nx = 0; nx < n; nx++) {
                    if ((mask >> nx & 1) == 1) continue;
                    int nm = mask | (1 << nx);
                    dp[nm][nx] = Math.min(dp[nm][nx], dp[mask][last] + dist(p, last, nx));
                }
            }
        }
        double best = Double.MAX_VALUE;
        for (int last = 1; last < n; last++) best = Math.min(best, dp[(1 << n) - 1][last] + dist(p, last, 0));
        return best;
    }
}
