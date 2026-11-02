package algorithms.graph.advanced;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 2-SAT (satisfiability of 2-CNF formulas) via the implication graph + SCCs
 *
 * WHAT IT IS:
 * Boolean variables x0..x(n-1) and clauses of the form (a OR b), where a and b are
 * literals (a variable or its negation). Is there an assignment making every clause
 * true? General SAT (3 literals per clause) is NP-complete; with 2 literals it is
 * solvable in LINEAR time.
 *
 * WHEN TO USE THIS:
 * - Constraints that are all "at least one of these two" / "not both" / "same" /
 *   "different": scheduling with two time-slot options per meeting, placing labels
 *   on a map (each label left or right of its point, overlaps forbidden), choosing
 *   one of two versions per package, team assignment with pairwise rules.
 *
 * STRATEGY:
 * 1. IMPLICATION GRAPH: a clause (a OR b) is equivalent to
 *      (NOT a -> b)  and  (NOT b -> a)
 *    so add those two edges. Literal x is node 2x, NOT x is node 2x + 1.
 * 2. Find STRONGLY CONNECTED COMPONENTS (Tarjan; see TarjansSCC.java in Part B).
 *    Literals in one SCC must all have the same value.
 * 3. UNSATISFIABLE iff some x and NOT x are in the same SCC (x -> ... -> NOT x -> ... -> x).
 * 4. Otherwise an assignment: Tarjan numbers SCCs in REVERSE topological order
 *    (sinks first). Set x = true if comp[x] < comp[NOT x], i.e. x comes LATER in
 *    topological order - so x never implies its own negation.
 *
 * COMPLEXITY:
 * Time: O(n + m) for n variables and m clauses. Space: O(n + m).
 */
public class TwoSAT {

    private final int n;
    private final List<List<Integer>> graph = new ArrayList<>();
    private int clauses = 0;

    public TwoSAT(int variables) {
        this.n = variables;
        for (int i = 0; i < 2 * n; i++) graph.add(new ArrayList<>());
    }

    private static int lit(int var, boolean value) { return 2 * var + (value ? 0 : 1); }

    /** Clause (x == xValue) OR (y == yValue). */
    public void addClause(int x, boolean xValue, int y, boolean yValue) {
        int a = lit(x, xValue), b = lit(y, yValue);
        graph.get(a ^ 1).add(b); // NOT a -> b
        graph.get(b ^ 1).add(a); // NOT b -> a
        clauses++;
    }

    // Convenient shapes built from clauses:
    public void mustBe(int x, boolean value)              { addClause(x, value, x, value); }
    public void notBoth(int x, boolean xv, int y, boolean yv) { addClause(x, !xv, y, !yv); }
    public void implies(int x, boolean xv, int y, boolean yv) { addClause(x, !xv, y, yv); }
    public void equal(int x, int y)                       { addClause(x, true, y, false); addClause(x, false, y, true); }
    public void different(int x, int y)                   { addClause(x, true, y, true); addClause(x, false, y, false); }

    public int clauseCount() { return clauses; }

    /** A satisfying assignment, or null if none exists. */
    public boolean[] solve() {
        int[] comp = tarjan();
        boolean[] value = new boolean[n];
        for (int x = 0; x < n; x++) {
            if (comp[2 * x] == comp[2 * x + 1]) return null; // x and NOT x forced equal
            value[x] = comp[2 * x] < comp[2 * x + 1];
        }
        return value;
    }

    // Iterative Tarjan (no recursion limits on big instances). comp ids are in reverse topological order.
    private int[] tarjan() {
        int nodes = 2 * n;
        int[] index = new int[nodes], low = new int[nodes], comp = new int[nodes], edgePos = new int[nodes];
        Arrays.fill(index, -1);
        boolean[] onStack = new boolean[nodes];
        int[] stack = new int[nodes], callStack = new int[nodes];
        int sp = 0, counter = 0, comps = 0;
        for (int start = 0; start < nodes; start++) {
            if (index[start] != -1) continue;
            int csp = 0;
            callStack[csp++] = start;
            index[start] = low[start] = counter++;
            stack[sp++] = start;
            onStack[start] = true;
            while (csp > 0) {
                int u = callStack[csp - 1];
                List<Integer> out = graph.get(u);
                if (edgePos[u] < out.size()) {
                    int v = out.get(edgePos[u]++);
                    if (index[v] == -1) {                 // "recurse" into v
                        index[v] = low[v] = counter++;
                        stack[sp++] = v;
                        onStack[v] = true;
                        callStack[csp++] = v;
                    } else if (onStack[v]) {
                        low[u] = Math.min(low[u], index[v]);
                    }
                } else {                                  // u finished
                    if (low[u] == index[u]) {             // u is the root of an SCC: pop it
                        int w;
                        do { w = stack[--sp]; onStack[w] = false; comp[w] = comps; } while (w != u);
                        comps++;
                    }
                    csp--;
                    if (csp > 0) { int parent = callStack[csp - 1]; low[parent] = Math.min(low[parent], low[u]); }
                }
            }
        }
        return comp;
    }

    public static void main(String[] args) {
        System.out.println("--- 2-SAT DEMO ---");
        // Party planning: x0 = Alice comes, x1 = Bob comes, x2 = Carol comes
        TwoSAT party = new TwoSAT(3);
        party.addClause(0, true, 1, true);       // Alice or Bob must come
        party.notBoth(0, true, 2, true);         // Alice and Carol can't both come
        party.implies(1, true, 2, true);         // if Bob comes, Carol comes
        party.mustBe(2, false);                  // Carol is away
        System.out.println("Party: " + Arrays.toString(party.solve()) + " (Alice, Bob, Carol)");
        // Carol away -> Bob can't come -> Alice must come: [true, false, false]

        TwoSAT impossible = new TwoSAT(2);
        impossible.different(0, 1);
        impossible.equal(0, 1);
        System.out.println("x0 != x1 and x0 == x1: " + (impossible.solve() == null ? "unsatisfiable" : "sat?!"));
    }
}
