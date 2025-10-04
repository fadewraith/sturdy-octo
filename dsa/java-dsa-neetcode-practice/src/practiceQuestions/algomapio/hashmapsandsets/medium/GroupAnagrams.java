package practiceQuestions.algomapio.hashmapsandsets.medium;

import java.util.*;

public class GroupAnagrams {

    private static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for(String s: strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(map.values());
        // Time: O(n * m log m), n is the number of strings, m is the length of largest string
        // Space: O(n * m)
    }

    /**
     * Optimal Approach
     * Use a frequency count of letters (array of size 26) as the key instead of sorting.
     * For each string in the input list:
     * Count the frequency of each letter (O(K) time).
     * Use the tuple of counts as a key in the dictionary.
     * Append the original string to the list corresponding to this key.
     * Return the values of the dictionary as the result.
     * Efficiency Gain Avoids the need to sort each string. The time complexity improves to O(NK), where N is the number of strings and K is the maximum length of a string.
     * */
    private static List<List<String>> optimal(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for(String s: strs) {
            int[] count = new int[26];
            for(char c : s.toCharArray()) {
                count[c - 'a']++;
            }
            StringBuilder key = new StringBuilder();
            for(int num: count) {
                key.append('#').append(num);
            }

            String keyStr = key.toString();
            map.computeIfAbsent(keyStr, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(map.values());
    }
}
