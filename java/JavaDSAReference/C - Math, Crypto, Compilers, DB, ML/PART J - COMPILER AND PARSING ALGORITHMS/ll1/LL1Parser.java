package compilers.ll1;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * LL(1) PARSER GENERATOR: FIRST / FOLLOW sets, parse table, table-driven parsing
 *
 * WHAT IT IS:
 * Given a context-free GRAMMAR, automatically build a parsing TABLE, then parse any
 * input with a stack and that table - no hand-written parse functions.
 * "LL(1)" = read Left to right, build a Leftmost derivation, 1 token of lookahead.
 *
 * WHEN TO USE THIS:
 * - Understanding parser generators (ANTLR is LL(*), JavaCC is LL(k)) and why some
 *   grammars must be rewritten before a top-down parser can use them.
 * - Hand-written recursive-descent parsers ARE LL(1) parsers; FIRST/FOLLOW tell
 *   you which rule each function should pick.
 *
 * STEP 1 - FIRST(X): the terminals that can START a string derived from X
 *   (plus EPSILON if X can derive the empty string). Fixed-point iteration: for
 *   A -> Y1 Y2 ... add FIRST(Y1); if Y1 can be empty also FIRST(Y2), and so on.
 * STEP 2 - FOLLOW(A): the terminals that can appear right AFTER A in some
 *   sentence ($ = end of input follows the start symbol). For B -> ... A beta:
 *   add FIRST(beta); if beta can be empty, add FOLLOW(B).
 * STEP 3 - TABLE[A][t] = which production to use for A when the lookahead is t:
 *   for A -> alpha: for t in FIRST(alpha): TABLE[A][t] = alpha;
 *                   if alpha can be empty: for t in FOLLOW(A): TABLE[A][t] = alpha.
 *   Two productions in one cell = a CONFLICT -> the grammar is not LL(1).
 *   Classic causes: LEFT RECURSION (E -> E + T) and common prefixes; fix them by
 *   rewriting:  E -> T E',  E' -> + T E' | epsilon.
 * STEP 4 - PARSE: stack starts as [$, Start]. Top is a terminal -> must equal the
 *   lookahead (consume both). Top is a nonterminal A -> replace it with the
 *   right-hand side TABLE[A][lookahead] (pushed in reverse); empty cell = syntax error.
 *
 * COMPLEXITY:
 * FIRST/FOLLOW: O(iterations * grammar size), small in practice.
 * Parsing: O(N) for N tokens (each step consumes a token or expands a nonterminal;
 * in an LL(1) grammar without useless cycles the expansions per token are bounded).
 */
public class LL1Parser {

    public static final String EPSILON = "eps";
    public static final String END = "$";

    private final String start;
    private final Map<String, List<List<String>>> rules = new LinkedHashMap<>(); // A -> list of right-hand sides
    private final Map<String, Set<String>> first = new LinkedHashMap<>();
    private final Map<String, Set<String>> follow = new LinkedHashMap<>();
    private final Map<String, Map<String, List<String>>> table = new LinkedHashMap<>();
    private final List<String> conflicts = new ArrayList<>();

    /**
     * Grammar lines like "E -> T E'" and "E' -> + T E' | eps". The first line's
     * left side is the start symbol; symbols are separated by spaces; anything that
     * never appears on a left side is a terminal.
     */
    public LL1Parser(String... grammar) {
        String s = null;
        for (String line : grammar) {
            String[] sides = line.split("->");
            String lhs = sides[0].trim();
            if (s == null) s = lhs;
            for (String alt : sides[1].split("\\|")) {
                List<String> rhs = new ArrayList<>(Arrays.asList(alt.trim().split("\\s+")));
                if (rhs.size() == 1 && rhs.get(0).equals(EPSILON)) rhs.clear(); // empty production
                rules.computeIfAbsent(lhs, k -> new ArrayList<>()).add(rhs);
            }
        }
        this.start = s;
        computeFirst();
        computeFollow();
        buildTable();
    }

    private boolean isNonTerminal(String sym) { return rules.containsKey(sym); }

    /** FIRST of a sequence of symbols (contains EPSILON if the whole sequence can be empty). */
    public Set<String> firstOf(List<String> symbols) {
        Set<String> out = new LinkedHashSet<>();
        for (String sym : symbols) {
            if (!isNonTerminal(sym)) { out.add(sym); return out; }
            Set<String> f = first.get(sym);
            for (String t : f) if (!t.equals(EPSILON)) out.add(t);
            if (!f.contains(EPSILON)) return out;
        }
        out.add(EPSILON); // every symbol could vanish
        return out;
    }

    private void computeFirst() {
        for (String a : rules.keySet()) first.put(a, new LinkedHashSet<>());
        boolean changed = true;
        while (changed) {                                  // iterate to a fixed point
            changed = false;
            for (Map.Entry<String, List<List<String>>> e : rules.entrySet()) {
                for (List<String> rhs : e.getValue()) {
                    if (first.get(e.getKey()).addAll(firstOf(rhs))) changed = true;
                }
            }
        }
    }

    private void computeFollow() {
        for (String a : rules.keySet()) follow.put(a, new LinkedHashSet<>());
        follow.get(start).add(END);
        boolean changed = true;
        while (changed) {
            changed = false;
            for (Map.Entry<String, List<List<String>>> e : rules.entrySet()) {
                for (List<String> rhs : e.getValue()) {
                    for (int i = 0; i < rhs.size(); i++) {
                        String sym = rhs.get(i);
                        if (!isNonTerminal(sym)) continue;
                        Set<String> beta = firstOf(rhs.subList(i + 1, rhs.size()));
                        for (String t : beta) if (!t.equals(EPSILON) && follow.get(sym).add(t)) changed = true;
                        if (beta.contains(EPSILON) && follow.get(sym).addAll(follow.get(e.getKey()))) changed = true;
                    }
                }
            }
        }
    }

    private void buildTable() {
        for (Map.Entry<String, List<List<String>>> e : rules.entrySet()) {
            String a = e.getKey();
            Map<String, List<String>> row = table.computeIfAbsent(a, k -> new LinkedHashMap<>());
            for (List<String> rhs : e.getValue()) {
                Set<String> f = firstOf(rhs);
                Set<String> lookaheads = new LinkedHashSet<>();
                for (String t : f) if (!t.equals(EPSILON)) lookaheads.add(t);
                if (f.contains(EPSILON)) lookaheads.addAll(follow.get(a));
                for (String t : lookaheads) {
                    List<String> existing = row.putIfAbsent(t, rhs);
                    if (existing != null && existing != rhs) {
                        conflicts.add("TABLE[" + a + ", " + t + "]: " + show(a, existing) + "  vs  " + show(a, rhs));
                    }
                }
            }
        }
    }

    private static String show(String a, List<String> rhs) { return a + " -> " + (rhs.isEmpty() ? EPSILON : String.join(" ", rhs)); }

    public boolean isLL1() { return conflicts.isEmpty(); }
    public List<String> conflicts() { return conflicts; }
    public Set<String> first(String nonTerminal) { return first.get(nonTerminal); }
    public Set<String> follow(String nonTerminal) { return follow.get(nonTerminal); }

    /**
     * Parses a token list. Returns the productions used (a leftmost derivation),
     * or throws IllegalArgumentException with the position of the syntax error.
     */
    public List<String> parse(List<String> input) {
        if (!isLL1()) throw new IllegalStateException("grammar is not LL(1): " + conflicts);
        List<String> tokens = new ArrayList<>(input);
        tokens.add(END);
        Deque<String> stack = new ArrayDeque<>();
        stack.push(END);
        stack.push(start);
        List<String> derivation = new ArrayList<>();
        int i = 0;
        while (!stack.isEmpty()) {
            String top = stack.pop();
            String look = tokens.get(i);
            if (!isNonTerminal(top)) {                       // terminal (or $): must match
                if (!top.equals(look)) throw new IllegalArgumentException("expected '" + top + "' but found '" + look + "' at token " + i);
                i++;
                continue;
            }
            List<String> rhs = table.get(top).get(look);
            if (rhs == null) throw new IllegalArgumentException("unexpected '" + look + "' at token " + i + " while parsing " + top);
            derivation.add(show(top, rhs));
            for (int k = rhs.size() - 1; k >= 0; k--) stack.push(rhs.get(k)); // reversed, so the first symbol is on top
        }
        return derivation;
    }

    public boolean accepts(List<String> input) {
        try { parse(input); return true; } catch (IllegalArgumentException e) { return false; }
    }

    public static List<String> tokens(String s) { return s.isBlank() ? List.of() : Arrays.asList(s.trim().split("\\s+")); }

    public static void main(String[] args) {
        System.out.println("--- LL(1) PARSER DEMO ---");
        LL1Parser g = new LL1Parser(
                "E -> T E'",
                "E' -> + T E' | eps",
                "T -> F T'",
                "T' -> * F T' | eps",
                "F -> ( E ) | id");
        for (String nt : new String[]{"E", "E'", "T", "T'", "F"}) {
            System.out.printf("FIRST(%-2s) = %-16s FOLLOW(%-2s) = %s%n", nt, new TreeSet<>(g.first(nt)), nt, new TreeSet<>(g.follow(nt)));
        }
        // FIRST(E) = {(, id}, FIRST(E') = {+, eps}, FOLLOW(E) = {$, )}, FOLLOW(T) = {$, ), +}, FOLLOW(F) = {$, ), *, +}
        System.out.println("LL(1)? " + g.isLL1());
        System.out.println("Derivation of 'id + id * id':");
        for (String step : g.parse(tokens("id + id * id"))) System.out.println("  " + step);
        System.out.println("Accepts '( id + id ) * id'? " + g.accepts(tokens("( id + id ) * id")));
        System.out.println("Accepts 'id + * id'?        " + g.accepts(tokens("id + * id")));

        LL1Parser leftRecursive = new LL1Parser("E -> E + T | T", "T -> id");
        System.out.println("\nLeft-recursive E -> E + T | T is LL(1)? " + leftRecursive.isLL1());
        for (String c : leftRecursive.conflicts()) System.out.println("  conflict " + c);
    }
}
