package arraysandhashing.ez;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LongestCommonPrefix {

    public static String horizontalScanning(String[] s) {
        // t - O(n * m)
        // s - O(1)
        String prefix = s[0];
        for(int i = 1; i < s.length; i++) {
            int j = 0;
            while(j < Math.min(prefix.length(), s[i].length())) {
                if(prefix.charAt(j) != s[i].charAt(j)) break;
                j++;
            }
            prefix = prefix.substring(0, j);
        }
        return prefix;
    }

    public static String verticalScanning(String[] s) {
        // t - O(n * m)
        // s - O(1)
        for(int i = 0; i < s[0].length(); i++) {
            for(String item: s) {
                if(i == item.length() || item.charAt(i) != s[0].charAt(i)) return item.substring(0, i);
            }
        }
        return s[0];
    }

    public String sortingMethod(String[] strs) {
        // n = len(str), m = len(arr)
        // t - O(n * m log n)
        // s - O(1) or O(m) depends on sorting algo
        if (strs.length == 1) {
            return strs[0];
        }

        Arrays.sort(strs);
        int N = Math.min(strs[0].length(), strs[strs.length - 1].length());
        for (int i = 0; i < N; i++) {
            if (strs[0].charAt(i) != strs[strs.length - 1].charAt(i)) {
                return strs[0].substring(0, i);
            }
        }
        return strs[0];
    }

    TrieNode root = new TrieNode();
    void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }
    }

    int lcp(String word, int prefixLen) {
        TrieNode node = root;
        int i = 0;
        while (i < Math.min(word.length(), prefixLen)) {
            if (!node.children.containsKey(word.charAt(i))) {
                return i;
            }
            node = node.children.get(word.charAt(i));
            i++;
        }
        return Math.min(word.length(), prefixLen);
    }

    public String usingTrie(String[] strs) {
        // t - O(n * m)
        // s - O(n)
        if (strs.length == 1) {
            return strs[0];
        }

        int mini = 0;
        for (int i = 1; i < strs.length; i++) {
            if (strs[mini].length() > strs[i].length()) {
                mini = i;
            }
        }

        LongestCommonPrefix trie = new LongestCommonPrefix();
        trie.insert(strs[mini]);
        int prefixLen = strs[mini].length();

        for (int i = 0; i < strs.length; i++) {
            prefixLen = trie.lcp(strs[i], prefixLen);
        }

        return strs[0].substring(0, prefixLen);
    }


}

class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
}