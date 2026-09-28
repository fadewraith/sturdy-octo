package algorithms.dp;

/**
 * WORD BREAK
 * 
 * WHAT IT IS:
 * Given a string and a dictionary of words, determine if the string can be perfectly 
 * segmented into a space-separated sequence of one or more dictionary words.
 * 
 * COMBINATION USAGE:
 * - Dynamic Programming + Dictionary/Set lookup.
 * 
 * STRATEGY:
 * `dp[i]` is true if the first `i` characters of the string can be segmented.
 * For every `i` (end of current substring), we look back at a previous index `j`.
 * If `dp[j]` is true (meaning the string UP TO `j` is valid), AND the substring 
 * from `j` to `i` is in the dictionary, then `dp[i]` is ALSO true!
 * 
 * COMPLEXITY:
 * Time: O(N^3) standard (due to substring creation), can be O(N^2) if dictionary 
 *       lookup is O(1) and we use a Trie or optimized matching.
 * Space: O(N) for DP array.
 */
public class WordBreak {

    // Simple custom set since we can't use java.util.HashSet
    private static class StringDict {
        String[] words;
        StringDict(String[] w) { words = w; }
        boolean contains(String s) {
            for (String word : words) {
                if (word.equals(s)) return true;
            }
            return false;
        }
    }

    public static boolean wordBreak(String s, String[] wordDict) {
        StringDict dict = new StringDict(wordDict);
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        
        dp[0] = true; // Base case: Empty string is always valid

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                // If substring up to j is valid, AND the remaining part (j to i) is a word
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break; // No need to check other 'j's, we know 'i' is valid!
                }
            }
        }
        
        return dp[n];
    }

    public static void main(String[] args) {
        System.out.println("--- WORD BREAK DEMO ---");
        
        String s = "leetcode";
        String[] dict = {"leet", "code"};
        
        System.out.println("String: " + s);
        System.out.println("Can break? " + wordBreak(s, dict)); // Expected: true
        
        String s2 = "catsandog";
        String[] dict2 = {"cats", "dog", "sand", "and", "cat"};
        System.out.println("String: " + s2);
        System.out.println("Can break? " + wordBreak(s2, dict2)); // Expected: false
    }
}
