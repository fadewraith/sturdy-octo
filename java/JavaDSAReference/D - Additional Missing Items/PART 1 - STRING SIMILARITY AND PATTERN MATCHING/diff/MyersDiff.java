package algorithms.strings.diff;

import java.util.ArrayList;
import java.util.List;

/**
 * MYERS' DIFF ALGORITHM (the algorithm behind `git diff`)
 *
 * WHAT IT IS:
 * Given two sequences A and B (usually lines of two file versions), find the SHORTEST
 * EDIT SCRIPT - the fewest line insertions and deletions turning A into B - and
 * print it as a diff (unchanged lines kept as context).
 *
 * WHEN TO USE THIS:
 * - git diff (default algorithm), GNU diff, code review tools, merge tools,
 *   text-sync systems, test frameworks showing "expected vs actual".
 * - Same answer as LCS-based diff (fewest edits = n + m - 2 * LCS), but much faster
 *   when the files are SIMILAR, which is the common case.
 *
 * KEY IDEAS (Eugene Myers, 1986):
 * - EDIT GRAPH: a grid where moving RIGHT = delete A[x], DOWN = insert B[y], and
 *   DIAGONAL = keep a matching line (free). We want the path from (0,0) to (n,m)
 *   with the fewest non-diagonal steps, D.
 * - DIAGONALS: k = x - y. After d edits, the path can only end on diagonals
 *   k = -d, -d+2, ..., d.
 * - GREEDY "SNAKES": V[k] = the furthest x reached on diagonal k with d edits. From
 *   d-1 to d, each diagonal extends from its neighbour (k-1 via a delete, or k+1
 *   via an insert, whichever got further), then slides along free diagonal moves as
 *   far as possible. Stop at the first d whose path reaches (n, m).
 * - To print the script, keep a copy of V for every d and walk BACKWARDS.
 *
 * COMPLEXITY:
 * Time: O((n + m) * D) where D = number of edits - nearly linear for similar files
 * (LCS DP is always O(n * m)). Space: O((n + m) * D) for the saved V arrays here
 * (the linear-space "middle snake" refinement brings it to O(n + m)).
 */
public class MyersDiff {

    public enum Op { EQUAL, DELETE, INSERT }

    public record Edit(Op op, String line) {
        @Override public String toString() { return (op == Op.EQUAL ? "  " : op == Op.DELETE ? "- " : "+ ") + line; }
    }

    /** The shortest edit script turning a into b. */
    public static List<Edit> diff(List<String> a, List<String> b) {
        int n = a.size(), m = b.size(), max = n + m, offset = max;
        int[] v = new int[2 * max + 2];
        List<int[]> trace = new ArrayList<>();
        int found = -1;
        for (int d = 0; d <= max && found < 0; d++) {
            trace.add(v.clone());                              // V as it was BEFORE step d
            for (int k = -d; k <= d; k += 2) {
                int x;
                if (k == -d || (k != d && v[offset + k - 1] < v[offset + k + 1])) x = v[offset + k + 1]; // insert (down)
                else x = v[offset + k - 1] + 1;                                                          // delete (right)
                int y = x - k;
                while (x < n && y < m && a.get(x).equals(b.get(y))) { x++; y++; }                      // the snake
                v[offset + k] = x;
                if (x >= n && y >= m) { found = d; break; }
            }
        }

        // backtrack from (n, m) through the saved V arrays
        List<Edit> script = new ArrayList<>();
        int x = n, y = m;
        for (int d = found; d > 0; d--) {
            int[] vPrev = trace.get(d);                         // V after step d-1
            int k = x - y;
            int prevK = (k == -d || (k != d && vPrev[offset + k - 1] < vPrev[offset + k + 1])) ? k + 1 : k - 1;
            int prevX = vPrev[offset + prevK], prevY = prevX - prevK;
            while (x > prevX && y > prevY) { script.add(new Edit(Op.EQUAL, a.get(x - 1))); x--; y--; } // undo the snake
            if (prevK == k + 1) script.add(new Edit(Op.INSERT, b.get(y - 1)));
            else script.add(new Edit(Op.DELETE, a.get(x - 1)));
            x = prevX;
            y = prevY;
        }
        while (x > 0 && y > 0) { script.add(new Edit(Op.EQUAL, a.get(x - 1))); x--; y--; }       // leading common lines
        java.util.Collections.reverse(script);
        return script;
    }

    /** Number of inserted + deleted lines (the edit distance D). */
    public static int distance(List<Edit> script) {
        int d = 0;
        for (Edit e : script) if (e.op() != Op.EQUAL) d++;
        return d;
    }

    /** Applies a script to a to rebuild b (used to check scripts). */
    public static List<String> apply(List<String> a, List<Edit> script) {
        List<String> out = new ArrayList<>();
        int i = 0;
        for (Edit e : script) {
            switch (e.op()) {
                case EQUAL -> { if (!a.get(i).equals(e.line())) throw new IllegalStateException("script mismatch"); out.add(a.get(i++)); }
                case DELETE -> { if (!a.get(i).equals(e.line())) throw new IllegalStateException("script mismatch"); i++; }
                case INSERT -> out.add(e.line());
            }
        }
        if (i != a.size()) throw new IllegalStateException("script did not consume all of a");
        return out;
    }

    public static void main(String[] args) {
        System.out.println("--- MYERS DIFF DEMO ---");
        List<String> before = List.of("def greet(name):", "    print('Hello')", "    print(name)", "", "greet('Bob')");
        List<String> after = List.of("def greet(name, loud=False):", "    print('Hello')", "    msg = name.upper() if loud else name", "    print(msg)", "", "greet('Bob', loud=True)");
        List<Edit> script = diff(before, after);
        for (Edit e : script) System.out.println(e);
        System.out.println("edit distance " + distance(script) + "; applying the script rebuilds the new version: " + apply(before, script).equals(after));

        // the textbook example from Myers' paper: ABCABBA -> CBABAC, D = 5
        List<String> a = List.of("A", "B", "C", "A", "B", "B", "A"), b = List.of("C", "B", "A", "B", "A", "C");
        System.out.println("\nABCABBA -> CBABAC: D = " + distance(diff(a, b)));
    }
}
