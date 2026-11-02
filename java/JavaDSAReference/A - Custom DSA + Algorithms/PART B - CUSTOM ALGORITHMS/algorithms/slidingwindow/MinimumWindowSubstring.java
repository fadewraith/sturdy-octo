package algorithms.slidingwindow;

/**
 * MINIMUM WINDOW SUBSTRING
 *
 * WHAT IT IS:
 * Given strings s and t, find the SHORTEST substring of s that contains every
 * character of t (including duplicates). "" if there is none.
 *   s = "ADOBECODEBANC", t = "ABC" -> "BANC"
 *
 * WHEN TO USE THIS:
 * The hardest of the standard variable-size sliding window problems
 * (VariableWindow.java has the easier ones). The same "expand until valid,
 * then shrink while still valid" loop solves:
 * - smallest subarray with sum >= K, longest substring with at most K distinct chars,
 * - find all anagrams of t in s (fixed window version).
 *
 * STRATEGY:
 * need[c]  = how many more copies of c the window still needs (starts as t's counts).
 * missing  = total characters still needed (starts as t.length()).
 * 1. EXPAND: move `right` forward. If need[s[right]] > 0, this char was useful ->
 *    missing--. Always do need[s[right]]-- (it may go negative = surplus copies).
 * 2. When missing == 0 the window is valid. SHRINK from the left as long as it stays
 *    valid, recording the smallest window. Removing s[left] does need[s[left]]++;
 *    if that makes it > 0, the window just lost a required char -> missing++,
 *    and we go back to expanding.
 * Characters that are not in t simply go negative and never count as "missing".
 *
 * COMPLEXITY:
 * Time: O(|s| + |t|) - each index enters and leaves the window once.
 * Space: O(alphabet) - one counter per possible char (65,536 for Java chars).
 */
public class MinimumWindowSubstring {

    public static String minWindow(String s, String t) {
        if (t.isEmpty()) return "";
        int[] need = new int[Character.MAX_VALUE + 1];
        for (int i = 0; i < t.length(); i++) need[t.charAt(i)]++;
        int missing = t.length();

        int bestStart = 0, bestLen = Integer.MAX_VALUE;
        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (need[c] > 0) missing--;   // a required char we didn't have enough of
            need[c]--;

            while (missing == 0) {        // valid window -> try to shrink it
                if (right - left + 1 < bestLen) {
                    bestLen = right - left + 1;
                    bestStart = left;
                }
                char out = s.charAt(left++);
                need[out]++;
                if (need[out] > 0) missing++; // we just dropped a required char
            }
        }
        return bestLen == Integer.MAX_VALUE ? "" : s.substring(bestStart, bestStart + bestLen);
    }

    public static void main(String[] args) {
        System.out.println("--- MINIMUM WINDOW SUBSTRING DEMO ---");
        System.out.println("s=ADOBECODEBANC, t=ABC -> \"" + minWindow("ADOBECODEBANC", "ABC") + "\""); // BANC
        System.out.println("s=a,             t=aa  -> \"" + minWindow("a", "aa") + "\"");               // "" (needs two a's)
        System.out.println("s=aaflslflsldkalskaaa, t=aaa -> \"" + minWindow("aaflslflsldkalskaaa", "aaa") + "\""); // aaa
    }
}
