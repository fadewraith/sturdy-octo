package algorithms.strings.similarity;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LEVENSHTEIN AUTOMATON
 *
 * WHAT IT IS:
 * A Finite State Automaton that recognizes all strings within a maximum edit distance
 * `k` of a target string.
 * Used internally by Lucene/Elasticsearch for blazing fast fuzzy queries.
 *
 * KEY IDEA (state = one row of the edit-distance DP table):
 * When we compute edit distance between `target` and an input word letter by letter,
 * each new input letter only needs the PREVIOUS DP row. So a row is a "state", and
 * reading a letter is a "transition" to the next row. Two tricks make it an automaton:
 *   1. Cap every value at k + 1 (anything bigger is just "too far").
 *   2. Then there are only finitely many distinct rows -> a finite automaton (DFA).
 *
 * WHAT YOU CAN ASK A STATE:
 *   - isMatch(state):  is the word read so far within distance k?   (last cell <= k)
 *   - canMatch(state): could ANY continuation still match?          (some cell <= k)
 * canMatch is the magic: while walking a sorted dictionary / trie, a dead state lets
 * you skip a whole branch of words at once. That's why Lucene's fuzzy search is fast.
 *
 * TWO WAYS TO USE IT (both implemented below):
 *   - Lazy:     step the DP row on the fly (simple, no build cost).
 *   - Explicit: build the full DFA up front (buildDfa) - each step becomes an O(1) table lookup.
 *
 * COMPLEXITY:
 *   Lazy step: O(n) per input letter (n = target length).
 *   DFA states: bounded by a function of n and k (small for k = 1 or 2, as used in practice).
 */
public class LevenshteinAutomaton {

    private final String target;
    private final int maxEdits;

    public LevenshteinAutomaton(String target, int maxEdits) {
        if (maxEdits < 0) throw new IllegalArgumentException("maxEdits must be >= 0");
        this.target = target;
        this.maxEdits = maxEdits;
    }

    // --------------------------------------------------------
    // 1. LAZY AUTOMATON (state = capped DP row)
    // --------------------------------------------------------

    /** Start state: distance from "" to each prefix of target = its length. */
    public int[] start() {
        int[] row = new int[target.length() + 1];
        for (int i = 0; i < row.length; i++) row[i] = Math.min(i, maxEdits + 1);
        return row;
    }

    /** Reads one input character: computes the next DP row. */
    public int[] step(int[] row, char c) {
        int[] next = new int[row.length];
        next[0] = Math.min(row[0] + 1, maxEdits + 1); // deleting all input so far
        for (int i = 1; i < row.length; i++) {
            int cost = (target.charAt(i - 1) == c) ? 0 : 1;
            int best = Math.min(row[i - 1] + cost,        // match / substitute
                       Math.min(row[i] + 1,               // insert c
                                next[i - 1] + 1));        // delete target[i-1]
            next[i] = Math.min(best, maxEdits + 1);       // cap -> finite states
        }
        return next;
    }

    public boolean isMatch(int[] row) {
        return row[row.length - 1] <= maxEdits;
    }

    public boolean canMatch(int[] row) {
        for (int v : row) if (v <= maxEdits) return true;
        return false;
    }

    /** True if edit distance(word, target) <= maxEdits. Stops early on a dead state. */
    public boolean matches(String word) {
        int[] state = start();
        for (int i = 0; i < word.length(); i++) {
            state = step(state, word.charAt(i));
            if (!canMatch(state)) return false; // no continuation can recover
        }
        return isMatch(state);
    }

    // --------------------------------------------------------
    // 2. EXPLICIT DFA
    // --------------------------------------------------------

    /**
     * A built DFA. Letters that do not appear in the target all behave the same,
     * so they share one "other" column. transitions[state][symbol] = next state, -1 = dead.
     */
    public static class Dfa {
        final Map<Character, Integer> symbolOf = new HashMap<>();
        int otherSymbol;
        List<int[]> transitions = new ArrayList<>();
        List<Boolean> accepting = new ArrayList<>();

        public int stateCount() {
            return transitions.size();
        }

        public boolean accepts(String word) {
            int s = 0;
            for (int i = 0; i < word.length() && s != -1; i++) {
                Integer sym = symbolOf.get(word.charAt(i));
                s = transitions.get(s)[sym == null ? otherSymbol : sym];
            }
            return s != -1 && accepting.get(s);
        }
    }

    /** Breadth-first exploration of every reachable (capped) DP row. */
    public Dfa buildDfa() {
        Dfa dfa = new Dfa();
        List<Character> alphabet = new ArrayList<>();
        for (char c : target.toCharArray()) {
            if (!dfa.symbolOf.containsKey(c)) {
                dfa.symbolOf.put(c, alphabet.size());
                alphabet.add(c);
            }
        }
        dfa.otherSymbol = alphabet.size();
        char other = '\u0000'; // stands for "any letter not in target"
        while (target.indexOf(other) >= 0) other++;

        Map<String, Integer> idOf = new HashMap<>();
        List<int[]> rows = new ArrayList<>();
        ArrayDeque<Integer> queue = new ArrayDeque<>();

        int[] startRow = start();
        idOf.put(Arrays.toString(startRow), 0);
        rows.add(startRow);
        dfa.transitions.add(null);
        dfa.accepting.add(isMatch(startRow));
        queue.add(0);

        while (!queue.isEmpty()) {
            int id = queue.poll();
            int[] row = rows.get(id);
            int[] out = new int[alphabet.size() + 1];
            for (int sym = 0; sym <= alphabet.size(); sym++) {
                char c = (sym < alphabet.size()) ? alphabet.get(sym) : other;
                int[] next = step(row, c);
                if (!canMatch(next)) {
                    out[sym] = -1; // dead state
                    continue;
                }
                String keyStr = Arrays.toString(next);
                Integer nextId = idOf.get(keyStr);
                if (nextId == null) {
                    nextId = rows.size();
                    idOf.put(keyStr, nextId);
                    rows.add(next);
                    dfa.transitions.add(null);
                    dfa.accepting.add(isMatch(next));
                    queue.add(nextId);
                }
                out[sym] = nextId;
            }
            dfa.transitions.set(id, out);
        }
        return dfa;
    }

    // Plain DP edit distance, used only to double-check the demo
    private static int editDistance(String a, String b) {
        int[] prev = new int[b.length() + 1], cur = new int[b.length() + 1];
        for (int j = 0; j <= b.length(); j++) prev[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            cur[0] = i;
            for (int j = 1; j <= b.length(); j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                cur[j] = Math.min(prev[j - 1] + cost, Math.min(prev[j] + 1, cur[j - 1] + 1));
            }
            int[] t = prev; prev = cur; cur = t;
        }
        return prev[b.length()];
    }

    public static void main(String[] args) {
        System.out.println("--- LEVENSHTEIN AUTOMATON DEMO ---");
        LevenshteinAutomaton la = new LevenshteinAutomaton("woof", 1);
        Dfa dfa = la.buildDfa();
        System.out.println("Target 'woof', max 1 edit. DFA has " + dfa.stateCount() + " states.");

        String[] dictionary = {"wolf", "woof", "wof", "woofs", "goof", "wood", "word", "cat", "wooof", "wf"};
        for (String w : dictionary) {
            boolean lazy = la.matches(w), built = dfa.accepts(w);
            boolean truth = editDistance(w, "woof") <= 1;
            System.out.println(String.format("%-6s lazy=%-5b dfa=%-5b (edit distance %d)%s",
                    w, lazy, built, editDistance(w, "woof"), (lazy == truth && built == truth) ? "" : "  <-- MISMATCH"));
        }
    }
}
