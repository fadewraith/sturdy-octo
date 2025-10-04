package neetcodepracticesets.arraysandhashing.medium;

import java.util.*;

public class GroupAnagrams {

    public static List<List<String>> groupAnagrams(String[] s) {
        // O(m * n log n)
        // O(m * n)
        Map<String, List<String>> res = new HashMap<>();
        for(String item: s) {
            char[] charArr = item.toCharArray();
            Arrays.sort(charArr);
            String sorted = new String(charArr);
            res.putIfAbsent(sorted, new ArrayList<>());
            res.get(sorted).add(item);
        }
        return new ArrayList<>(res.values());
    }

    public List<List<String>> optimzed(String[] strs) {
        // O(m * n)
        // O(m) - extra space, O(m * n) - space for output list
        Map<String, List<String>> res = new HashMap<>();
        for (String s : strs) {
            int[] count = new int[26];
            for (char c : s.toCharArray()) {
                count[c - 'a']++;
            }
            String key = Arrays.toString(count);
            res.putIfAbsent(key, new ArrayList<>());
            res.get(key).add(s);
        }
        return new ArrayList<>(res.values());
    }

}
