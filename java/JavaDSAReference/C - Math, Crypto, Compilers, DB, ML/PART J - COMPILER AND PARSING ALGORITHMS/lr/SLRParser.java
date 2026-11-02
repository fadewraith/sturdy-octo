package compilers.lr;

import compilers.ll1.LL1Parser;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * SLR(1) PARSER GENERATOR (bottom-up LR parsing: LR(0) items, ACTION/GOTO tables)
 *
 * WHAT IT IS:
 * A BOTTOM-UP parser: it reads tokens, SHIFTS them onto a stack, and REDUCES the top
 * of the stack to a nonterminal whenever it matches a rule's right-hand side - building
 * the parse tree from the leaves up. The decisions come from tables generated from
 * the grammar. "SLR" = Simple LR, the easiest table construction.
 *
 * WHEN TO USE THIS:
 * - yacc/bison (LALR(1)), most production parser generators, and the reason
 *   LEFT-RECURSIVE grammars like  E -> E + T | T  are fine for LR (natural
 *   left-associativity) while LL(1) (LL1Parser.java) must rewrite them.
 *
 * BUILDING THE TABLES:
 * 1. Augment the grammar with S' -> S (accepting = reducing this rule).
 * 2. LR(0) ITEM = a rule with a dot showing how much has been seen: E -> E . + T
 * 3. CLOSURE(items): if the dot is before nonterminal B, add every B -> . gamma.
 * 4. GOTO(I, X): move the dot over X in every item of I that allows it, then close.
 * 5. CANONICAL COLLECTION: all item sets reachable from closure({S' -> . S}) via GOTO.
 *    Each set is a parser STATE (a DFA over grammar symbols).
 * 6. ACTION[state][terminal]:
 *      SHIFT j   if GOTO(state, terminal) = j
 *      REDUCE r  for a completed item A -> alpha . , on every terminal in FOLLOW(A)
 *      ACCEPT    for S' -> S . on $
 *    GOTO[state][nonterminal] = j   (used right after a reduce)
 *    Two entries in one cell = CONFLICT (shift/reduce or reduce/reduce): the grammar
 *    is not SLR(1). The classic one is the "dangling else".
 *
 * PARSING: stack of states. Look at ACTION[top][lookahead]:
 *   shift -> push j, consume the token;  reduce A -> beta -> pop |beta| states, push
 *   GOTO[new top][A];  accept -> done;  empty -> syntax error.
 * The sequence of reductions is a RIGHTMOST derivation in reverse.
 *
 * COMPLEXITY:
 * Table construction: polynomial in the grammar size (number of states can be large
 * for big grammars). Parsing: O(n) for n tokens.
 */
public class SLRParser {

    private static final String END = "$";
    private static final String AUG = "S'";

    private final List<String> lhs = new ArrayList<>();
    private final List<List<String>> rhs = new ArrayList<>();
    private final Set<String> nonTerminals = new LinkedHashSet<>();
    private final List<Set<Long>> states = new ArrayList<>();
    private final List<Map<String, String>> action = new ArrayList<>();
    private final List<Map<String, Integer>> gotoTable = new ArrayList<>();
    private final List<String> conflicts = new ArrayList<>();

    /** Same grammar format as LL1Parser: "E -> E + T | T", first line = start symbol. */
    public SLRParser(String... grammar) {
        String start = null;
        lhs.add(AUG);
        rhs.add(new ArrayList<>());
        for (String line : grammar) {
            String[] sides = line.split("->");
            String a = sides[0].trim();
            if (start == null) start = a;
            nonTerminals.add(a);
            for (String alt : sides[1].split("\\|")) {
                List<String> r = new ArrayList<>(Arrays.asList(alt.trim().split("\\s+")));
                if (r.size() == 1 && r.get(0).equals(LL1Parser.EPSILON)) r.clear();
                lhs.add(a);
                rhs.add(r);
            }
        }
        rhs.get(0).add(start);                         // S' -> S
        LL1Parser firstFollow = new LL1Parser(grammar); // reused only for FOLLOW sets
        build(firstFollow);
    }

    // an item (production p, dot position d) packed into one long
    private static long item(int p, int dot) { return ((long) p << 32) | dot; }
    private static int prod(long it) { return (int) (it >>> 32); }
    private static int dot(long it) { return (int) it; }

    private Set<Long> closure(Set<Long> items) {
        Set<Long> result = new TreeSet<>(items);
        Deque<Long> work = new ArrayDeque<>(items);
        while (!work.isEmpty()) {
            long it = work.poll();
            List<String> r = rhs.get(prod(it));
            if (dot(it) < r.size() && nonTerminals.contains(r.get(dot(it)))) {
                String b = r.get(dot(it));
                for (int p = 1; p < lhs.size(); p++) {
                    if (lhs.get(p).equals(b) && result.add(item(p, 0))) work.add(item(p, 0));
                }
            }
        }
        return result;
    }

    private Set<Long> goTo(Set<Long> items, String symbol) {
        Set<Long> moved = new TreeSet<>();
        for (long it : items) {
            List<String> r = rhs.get(prod(it));
            if (dot(it) < r.size() && r.get(dot(it)).equals(symbol)) moved.add(item(prod(it), dot(it) + 1));
        }
        return moved.isEmpty() ? moved : closure(moved);
    }

    private void build(LL1Parser ff) {
        Map<Set<Long>, Integer> index = new HashMap<>();
        Set<Long> start = closure(Set.of(item(0, 0)));
        states.add(start);
        index.put(start, 0);
        for (int s = 0; s < states.size(); s++) {          // the list grows while we iterate
            action.add(new HashMap<>());
            gotoTable.add(new HashMap<>());
            Set<String> symbols = new LinkedHashSet<>();
            for (long it : states.get(s)) {
                List<String> r = rhs.get(prod(it));
                if (dot(it) < r.size()) symbols.add(r.get(dot(it)));
            }
            for (String x : symbols) {
                Set<Long> target = goTo(states.get(s), x);
                Integer j = index.get(target);
                if (j == null) { j = states.size(); states.add(target); index.put(target, j); }
                if (nonTerminals.contains(x)) gotoTable.get(s).put(x, j);
                else setAction(s, x, "s" + j);
            }
        }
        for (int s = 0; s < states.size(); s++) {          // reduce / accept entries
            for (long it : states.get(s)) {
                int p = prod(it);
                if (dot(it) != rhs.get(p).size()) continue;
                if (p == 0) { setAction(s, END, "acc"); continue; }
                for (String t : ff.follow(lhs.get(p))) setAction(s, t, "r" + p);
            }
        }
    }

    private void setAction(int state, String terminal, String act) {
        String old = action.get(state).putIfAbsent(terminal, act);
        if (old != null && !old.equals(act)) {
            String kind = (old.charAt(0) == 's' || act.charAt(0) == 's') ? "shift/reduce" : "reduce/reduce";
            conflicts.add(kind + " conflict in state " + state + " on '" + terminal + "': " + describe(old) + " vs " + describe(act));
        }
    }

    private String describe(String act) {
        if (act.charAt(0) == 'r') { int p = Integer.parseInt(act.substring(1)); return "reduce " + lhs.get(p) + " -> " + String.join(" ", rhs.get(p)); }
        return act.equals("acc") ? "accept" : "shift";
    }

    public boolean isSLR1() { return conflicts.isEmpty(); }
    public List<String> conflicts() { return conflicts; }
    public int stateCount() { return states.size(); }

    /** Returns the reductions performed (a rightmost derivation in reverse) or throws on a syntax error. */
    public List<String> parse(List<String> input) {
        if (!isSLR1()) throw new IllegalStateException("grammar is not SLR(1): " + conflicts);
        List<String> tokens = new ArrayList<>(input);
        tokens.add(END);
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0);
        List<String> reductions = new ArrayList<>();
        int i = 0;
        while (true) {
            String act = action.get(stack.peek()).get(tokens.get(i));
            if (act == null) throw new IllegalArgumentException("syntax error at token " + i + " ('" + tokens.get(i) + "')");
            if (act.equals("acc")) return reductions;
            if (act.charAt(0) == 's') {
                stack.push(Integer.parseInt(act.substring(1)));
                i++;
            } else {
                int p = Integer.parseInt(act.substring(1));
                for (int k = 0; k < rhs.get(p).size(); k++) stack.pop();
                stack.push(gotoTable.get(stack.peek()).get(lhs.get(p)));
                reductions.add(lhs.get(p) + " -> " + (rhs.get(p).isEmpty() ? LL1Parser.EPSILON : String.join(" ", rhs.get(p))));
            }
        }
    }

    public boolean accepts(List<String> input) {
        try { parse(input); return true; } catch (IllegalArgumentException e) { return false; }
    }

    public static void main(String[] args) {
        System.out.println("--- SLR(1) PARSER DEMO ---");
        // Left-recursive expression grammar: rejected by LL(1), natural for LR
        SLRParser g = new SLRParser("E -> E + T | T", "T -> T * F | F", "F -> ( E ) | id");
        System.out.println("States: " + g.stateCount() + ", SLR(1)? " + g.isSLR1()); // 12 states, true
        System.out.println("Reductions for 'id + id * id' (rightmost derivation, reversed):");
        for (String r : g.parse(LL1Parser.tokens("id + id * id"))) System.out.println("  " + r);
        System.out.println("Accepts '( id + id ) * id'? " + g.accepts(LL1Parser.tokens("( id + id ) * id")));
        System.out.println("Accepts 'id + + id'?        " + g.accepts(LL1Parser.tokens("id + + id")));

        SLRParser danglingElse = new SLRParser("S -> if S | if S else S | other");
        System.out.println("\nDangling else grammar SLR(1)? " + danglingElse.isSLR1());
        for (String c : danglingElse.conflicts()) System.out.println("  " + c);
        // (yacc resolves this one by preferring SHIFT: else binds to the nearest if)
    }
}
