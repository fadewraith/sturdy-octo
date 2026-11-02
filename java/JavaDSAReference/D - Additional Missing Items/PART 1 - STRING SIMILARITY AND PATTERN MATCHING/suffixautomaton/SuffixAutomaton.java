package algorithms.strings.suffixautomaton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SUFFIX AUTOMATON (DAWG - the smallest automaton accepting all substrings)
 *
 * WHAT IT IS:
 * A DFA that accepts exactly the SUBSTRINGS of a text s (reading from its start
 * state), built ONLINE in O(n), with at most 2n - 1 states and 3n - 4 transitions.
 * It answers many string questions in linear time, often more simply than a suffix
 * tree (SuffixTree.java) or suffix array (SuffixArray.java).
 *
 * WHEN TO USE THIS:
 * - Is p a substring of s?  O(|p|).
 * - How many DISTINCT substrings does s have?  (sum over states of len - link.len)
 * - How many times does p occur in s?  (endpos sizes)
 * - Longest common substring of two strings in O(|a| + |b|).
 * - Plagiarism detection, DNA analysis, compression (it underlies some LZ variants).
 *
 * KEY IDEAS:
 * - Each state = a set of substrings with the same ENDPOS set (the positions where
 *   they end in s). len(state) = the longest of them.
 * - SUFFIX LINK of a state points to the state of its longest suffix that has a
 *   DIFFERENT (larger) endpos set. Links form a tree.
 * - ADDING character c (Blumer et al.): create state cur with len = last.len + 1;
 *   follow suffix links from `last`, adding transitions c -> cur, until a state p
 *   already has a c-transition to q.
 *     - none found: link(cur) = start state
 *     - len(q) == len(p) + 1: link(cur) = q
 *     - otherwise CLONE q (same transitions and link, len = len(p) + 1), redirect
 *       p's chain of c-transitions from q to the clone, and link q and cur to it.
 *   Amortized O(1) per character.
 * - OCCURRENCE COUNTS: every non-clone state contributes one end position; push the
 *   counts up the suffix-link tree in decreasing order of len.
 *
 * COMPLEXITY:
 * Build: O(n) (times the cost of the transition map). Queries: O(|pattern|).
 * Space: O(n) states.
 */
public class SuffixAutomaton {

    private final List<Map<Character, Integer>> next = new ArrayList<>();
    private final List<Integer> link = new ArrayList<>(), len = new ArrayList<>();
    private final List<Long> occ = new ArrayList<>();
    private int last = 0;
    private boolean occComputed = false;

    public SuffixAutomaton(String s) {
        newState(0, -1, 0);
        for (int i = 0; i < s.length(); i++) extend(s.charAt(i));
    }

    private int newState(int length, int suffixLink, long occurrences) {
        next.add(new HashMap<>());
        len.add(length);
        link.add(suffixLink);
        occ.add(occurrences);
        return next.size() - 1;
    }

    private void extend(char c) {
        int cur = newState(len.get(last) + 1, -1, 1);          // a new end position
        int p = last;
        while (p != -1 && !next.get(p).containsKey(c)) {
            next.get(p).put(c, cur);
            p = link.get(p);
        }
        if (p == -1) {
            link.set(cur, 0);
        } else {
            int q = next.get(p).get(c);
            if (len.get(p) + 1 == len.get(q)) {
                link.set(cur, q);
            } else {
                int clone = newState(len.get(p) + 1, link.get(q), 0); // clones add no end position
                next.get(clone).putAll(next.get(q));
                while (p != -1 && next.get(p).get(c) == q) {
                    next.get(p).put(c, clone);
                    p = link.get(p);
                }
                link.set(q, clone);
                link.set(cur, clone);
            }
        }
        last = cur;
        occComputed = false;
    }

    public int stateCount() { return next.size(); }

    private int walk(String p) {
        int st = 0;
        for (int i = 0; i < p.length() && st != -1; i++) {
            Integer nx = next.get(st).get(p.charAt(i));
            st = nx == null ? -1 : nx;
        }
        return st;
    }

    public boolean contains(String p) { return walk(p) != -1; }

    /** Number of distinct non-empty substrings. */
    public long distinctSubstrings() {
        long total = 0;
        for (int v = 1; v < next.size(); v++) total += len.get(v) - len.get(link.get(v));
        return total;
    }

    /** Number of (possibly overlapping) occurrences of p in the text. */
    public long occurrences(String p) {
        if (p.isEmpty()) throw new IllegalArgumentException("empty pattern");
        if (!occComputed) computeOccurrences();
        int st = walk(p);
        return st == -1 ? 0 : occ.get(st);
    }

    private void computeOccurrences() {
        int n = next.size(), maxLen = 0;
        for (int v = 0; v < n; v++) maxLen = Math.max(maxLen, len.get(v));
        int[] bucket = new int[maxLen + 1];                     // counting sort states by len
        for (int v = 0; v < n; v++) bucket[len.get(v)]++;
        for (int i = 1; i <= maxLen; i++) bucket[i] += bucket[i - 1];
        int[] order = new int[n];
        for (int v = n - 1; v >= 0; v--) order[--bucket[len.get(v)]] = v;
        long[] cnt = new long[n];
        for (int v = 0; v < n; v++) cnt[v] = occ.get(v);
        for (int i = n - 1; i > 0; i--) {                       // longest first: pass counts to the suffix link
            int v = order[i];
            if (link.get(v) >= 0) cnt[link.get(v)] += cnt[v];
        }
        for (int v = 0; v < n; v++) occ.set(v, cnt[v]);
        occComputed = true;
    }

    /** Longest common substring of the automaton's text and t. */
    public String longestCommonSubstring(String t) {
        int st = 0, l = 0, best = 0, bestEnd = 0;
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            while (st != 0 && !next.get(st).containsKey(c)) { st = link.get(st); l = len.get(st); } // shorten the match
            Integer nx = next.get(st).get(c);
            if (nx != null) { st = nx; l++; }
            if (l > best) { best = l; bestEnd = i + 1; }
        }
        return t.substring(bestEnd - best, bestEnd);
    }

    public static void main(String[] args) {
        System.out.println("--- SUFFIX AUTOMATON DEMO ---");
        String s = "abracadabra";
        SuffixAutomaton sa = new SuffixAutomaton(s);
        System.out.println("Text \"" + s + "\": " + sa.stateCount() + " states (<= 2n - 1 = " + (2 * s.length() - 1) + ")");
        System.out.println("contains \"cada\"? " + sa.contains("cada") + ", contains \"abc\"? " + sa.contains("abc"));
        System.out.println("occurrences of \"abra\": " + sa.occurrences("abra") + ", of \"a\": " + sa.occurrences("a")); // 2, 5
        System.out.println("distinct substrings: " + sa.distinctSubstrings());                                       // 54
        System.out.println("longest common substring with \"cadabra rules\": \"" + sa.longestCommonSubstring("cadabra rules") + "\"");
    }
}
