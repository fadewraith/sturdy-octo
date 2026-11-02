package algorithms.recursion;

import java.util.Arrays;

/**
 * GENERATE PARENTHESES (Backtracking with constraints)
 *
 * WHAT IT IS:
 * List every well-formed string of n pairs of parentheses.
 *   n = 3 -> ((())), (()()), (())(), ()(()), ()()()
 *
 * WHEN TO USE THIS:
 * - The cleanest example of backtracking where the RULES prune the tree, so we
 *   never build an invalid string and then throw it away.
 * - The count is the n-th CATALAN number: 1, 1, 2, 5, 14, 42, 132, ...
 *   (also counts binary trees with n nodes, ways to triangulate a polygon, ...).
 *
 * STRATEGY:
 * Build the string left to right, tracking `open` and `close` used so far:
 *   - add '(' if open < n            (still have opening brackets to place)
 *   - add ')' if close < open        (there is an unmatched '(' to close)
 * When the length reaches 2n, the string is valid by construction.
 * Trying '(' before ')' produces the results in lexicographic order ('(' < ')').
 *
 * COMPLEXITY:
 * Time: O(4^n / sqrt(n)) - the Catalan number of results, O(n) work each.
 * Space: O(n) recursion depth (plus the output).
 */
public class GenerateParentheses {

    private static String[] results;
    private static int count;

    public static String[] generate(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");
        results = new String[(int) Math.min(catalan(n), Integer.MAX_VALUE - 8)];
        count = 0;
        backtrack(new char[2 * n], 0, 0, n);
        return Arrays.copyOf(results, count);
    }

    private static void backtrack(char[] buf, int open, int close, int n) {
        int pos = open + close;
        if (pos == 2 * n) {
            results[count++] = new String(buf);
            return;
        }
        if (open < n) {
            buf[pos] = '(';
            backtrack(buf, open + 1, close, n);
        }
        if (close < open) {
            buf[pos] = ')';                 // overwriting buf[pos] is the "undo"
            backtrack(buf, open, close + 1, n);
        }
    }

    /** n-th Catalan number: C(n) = C(n-1) * 2(2n-1) / (n+1). Exact for n <= 35 in a long. */
    public static long catalan(int n) {
        long c = 1;
        for (int i = 1; i <= n; i++) c = c * 2 * (2 * i - 1) / (i + 1);
        return c;
    }

    /** Is s a balanced string of '(' and ')'? */
    public static boolean isValid(String s) {
        int balance = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') balance++;
            else if (ch == ')') { if (--balance < 0) return false; }
            else return false;
        }
        return balance == 0;
    }

    public static void main(String[] args) {
        System.out.println("--- GENERATE PARENTHESES DEMO ---");
        System.out.println("n = 3: " + Arrays.toString(generate(3)));
        // [((())), (()()), (())(), ()(()), ()()()]
        for (int n = 0; n <= 8; n++) {
            System.out.print(generate(n).length + (n < 8 ? ", " : "  (Catalan numbers)\n"));
        }
        // 1, 1, 2, 5, 14, 42, 132, 429, 1430
    }
}
