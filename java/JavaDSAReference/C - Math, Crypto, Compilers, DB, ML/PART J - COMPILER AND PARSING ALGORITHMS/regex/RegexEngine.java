package compilers.regex;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REGEX ENGINE: Thompson's NFA construction + NFA simulation + lazy DFA (subset construction)
 *
 * WHAT IT IS:
 * Decides whether a whole string matches a regular expression with:
 *   literals, . (any char), concatenation, | (or), * (0+), + (1+), ? (0 or 1),
 *   ( ) grouping, and \ to escape a special character.
 *
 * WHEN TO USE THIS:
 * - This is how grep, RE2 (Google), Rust's regex and Go's regexp work: guaranteed
 *   LINEAR time in the input. Backtracking engines (Python's re, PCRE, older Java)
 *   can take EXPONENTIAL time on patterns like (a|aa)*b or (a+)+b against a long
 *   run of a's that fails at the end ("ReDoS" - a backtracking regex took down
 *   Cloudflare in 2019). Recent JDKs add optimisations that defuse many such
 *   cases, but only the automaton approach guarantees it.
 * - Lexers (lex/flex) compile token regexes into one DFA the same way.
 *
 * STEP 1 - THOMPSON'S CONSTRUCTION (regex -> NFA):
 * Parse with recursive descent (alternation > concatenation > repetition > atom)
 * and build an NFA FRAGMENT (start state, accept state) for each piece:
 *   char c:   s --c--> a
 *   AB:       A.accept --eps--> B.start
 *   A|B:      new s --eps--> A.start, B.start;  A.accept, B.accept --eps--> new a
 *   A*:       new s --eps--> A.start and a;  A.accept --eps--> A.start and a
 *   A+ = A A*-style loop without the skip;  A? = skip edge without the loop
 * Each regex symbol adds at most 2 states -> NFA size O(m).
 *
 * STEP 2 - NFA SIMULATION (Thompson / Pike):
 * Track the SET of states we could be in. Start with the epsilon-closure of the
 * start state; for each input char, move every state on that char and take the
 * closure again. Accept if the final set contains the accept state.
 * O(n * m) time, never exponential.
 *
 * STEP 3 - LAZY DFA (subset construction on demand):
 * Each distinct SET of NFA states becomes one DFA state; transitions are computed
 * the first time they're needed and cached. After warm-up every char is one
 * table lookup - O(n). Building the full DFA up front could need 2^m states, so
 * RE2 builds it lazily exactly like this.
 *
 * COMPLEXITY:
 * Compile: O(m). NFA match: O(n * m). Lazy DFA match: O(n) amortized after
 * caching (at most min(2^m, n * ...) states are ever built).
 */
public class RegexEngine {

    private static final int EPS = -1, ANY = -2;

    // NFA: per state, a list of {label, target}; label = char, ANY or EPS
    private final List<List<int[]>> edges = new ArrayList<>();
    private final int start, accept;

    private String pattern;
    private int pos;

    public RegexEngine(String pattern) {
        this.pattern = pattern;
        this.pos = 0;
        int[] frag = parseAlternation();
        if (pos != pattern.length()) throw new IllegalArgumentException("unexpected '" + pattern.charAt(pos) + "' at " + pos);
        this.start = frag[0];
        this.accept = frag[1];
        this.pattern = null;
    }

    private int newState() { edges.add(new ArrayList<>()); return edges.size() - 1; }
    private void edge(int from, int label, int to) { edges.get(from).add(new int[]{label, to}); }

    // ---------------- parser building fragments {start, accept} ----------------
    private int[] parseAlternation() {
        int[] left = parseConcat();
        while (pos < pattern.length() && pattern.charAt(pos) == '|') {
            pos++;
            int[] right = parseConcat();
            int s = newState(), a = newState();
            edge(s, EPS, left[0]); edge(s, EPS, right[0]);
            edge(left[1], EPS, a); edge(right[1], EPS, a);
            left = new int[]{s, a};
        }
        return left;
    }

    private int[] parseConcat() {
        int[] result = null;
        while (pos < pattern.length() && pattern.charAt(pos) != '|' && pattern.charAt(pos) != ')') {
            int[] next = parseRepeat();
            if (result == null) result = next;
            else { edge(result[1], EPS, next[0]); result = new int[]{result[0], next[1]}; }
        }
        if (result == null) { // empty: matches the empty string
            int s = newState(), a = newState();
            edge(s, EPS, a);
            result = new int[]{s, a};
        }
        return result;
    }

    private int[] parseRepeat() {
        int[] f = parseAtom();
        while (pos < pattern.length() && "*+?".indexOf(pattern.charAt(pos)) >= 0) {
            char q = pattern.charAt(pos++);
            int s = newState(), a = newState();
            edge(s, EPS, f[0]);
            if (q != '+') edge(s, EPS, a);           // * and ? may skip
            if (q != '?') edge(f[1], EPS, f[0]);     // * and + may loop
            edge(f[1], EPS, a);
            f = new int[]{s, a};
        }
        return f;
    }

    private int[] parseAtom() {
        char c = pattern.charAt(pos);
        if (c == '(') {
            pos++;
            int[] inner = parseAlternation();
            if (pos >= pattern.length() || pattern.charAt(pos) != ')') throw new IllegalArgumentException("missing ')'");
            pos++;
            return inner;
        }
        if ("*+?".indexOf(c) >= 0) throw new IllegalArgumentException("nothing to repeat at " + pos);
        int label;
        if (c == '\\') {
            if (pos + 1 >= pattern.length()) throw new IllegalArgumentException("trailing backslash");
            label = pattern.charAt(pos + 1);
            pos += 2;
        } else {
            label = (c == '.') ? ANY : c;
            pos++;
        }
        int s = newState(), a = newState();
        edge(s, label, a);
        return new int[]{s, a};
    }

    // ---------------- NFA simulation ----------------
    private void closure(BitSet set) {
        int[] stack = new int[edges.size()];
        int top = 0;
        for (int s = set.nextSetBit(0); s >= 0; s = set.nextSetBit(s + 1)) stack[top++] = s;
        while (top > 0) {
            int s = stack[--top];
            for (int[] e : edges.get(s)) {
                if (e[0] == EPS && !set.get(e[1])) { set.set(e[1]); stack[top++] = e[1]; }
            }
        }
    }

    private BitSet step(BitSet current, char c) {
        BitSet next = new BitSet(edges.size());
        for (int s = current.nextSetBit(0); s >= 0; s = current.nextSetBit(s + 1)) {
            for (int[] e : edges.get(s)) if (e[0] == c || e[0] == ANY) next.set(e[1]);
        }
        closure(next);
        return next;
    }

    /** Full match by simulating the NFA directly. */
    public boolean matchesNFA(String input) {
        BitSet current = new BitSet(edges.size());
        current.set(start);
        closure(current);
        for (int i = 0; i < input.length() && !current.isEmpty(); i++) current = step(current, input.charAt(i));
        return current.get(accept);
    }

    // ---------------- lazy DFA ----------------
    private final Map<BitSet, Integer> dfaIds = new HashMap<>();
    private final List<BitSet> dfaSets = new ArrayList<>();
    private final List<Map<Character, Integer>> dfaNext = new ArrayList<>();

    private int dfaState(BitSet set) {
        Integer id = dfaIds.get(set);
        if (id != null) return id;
        dfaIds.put(set, dfaSets.size());
        dfaSets.add(set);
        dfaNext.add(new HashMap<>());
        return dfaSets.size() - 1;
    }

    /** Full match using the cached DFA (states built on first use). */
    public boolean matches(String input) {
        BitSet init = new BitSet(edges.size());
        init.set(start);
        closure(init);
        int d = dfaState(init);
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            Integer nxt = dfaNext.get(d).get(c);
            if (nxt == null) {                                   // not built yet: subset construction step
                nxt = dfaState(step(dfaSets.get(d), c));
                dfaNext.get(d).put(c, nxt);
            }
            d = nxt;
            if (dfaSets.get(d).isEmpty()) return false;          // dead state
        }
        return dfaSets.get(d).get(accept);
    }

    public int nfaStates() { return edges.size(); }
    public int dfaStatesBuilt() { return dfaSets.size(); }

    public static void main(String[] args) {
        System.out.println("--- REGEX ENGINE (THOMPSON NFA + LAZY DFA) DEMO ---");
        String[][] cases = {
            {"(a|b)*abb", "babb", "abab"},
            {"colou?r", "color", "colr"},
            {"h.llo+", "hellooo", "hllo"},
            {"\\(\\d\\)", "(d)", "d"},
            {"", "", "x"},
        };
        for (String[] c : cases) {
            RegexEngine re = new RegexEngine(c[0]);
            System.out.printf("/%s/  \"%s\" -> %b,  \"%s\" -> %b   (%d NFA states)%n",
                    c[0], c[1], re.matches(c[1]), c[2], re.matches(c[2]), re.nfaStates());
        }

        // A classic catastrophic-backtracking pattern. A naive backtracking engine tries
        // exponentially many ways to split the a's; the automaton just tracks a state set.
        RegexEngine re = new RegexEngine("(a|aa)*b");
        System.out.println();
        for (int n : new int[]{1_000, 10_000, 100_000}) {
            String input = "a".repeat(n) + "bc";
            long t0 = System.nanoTime();
            boolean m = re.matches(input);
            System.out.printf("(a|aa)*b vs %,7d a's + \"bc\": %b in %6.2f ms  (time grows linearly)%n", n, m, (System.nanoTime() - t0) / 1e6);
        }
        System.out.println("DFA states built: " + re.dfaStatesBuilt() + " (the cache makes each character one lookup)");
    }
}
