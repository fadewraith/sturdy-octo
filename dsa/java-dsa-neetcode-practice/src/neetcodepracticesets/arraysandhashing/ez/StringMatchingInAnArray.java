package arraysandhashing.ez;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class StringMatchingInAnArray {

//    Where n is the number of words, and m is the length of the longest word.

    public List<String> bruteForce(String[] words) {

        // t - O(n^2 * m^2)
        // s - O(1) extra space, O(n * m) space for the output list
        List<String> res = new ArrayList<>();
        for(int i = 0; i < words.length; i++) {
            for(int j = 0; j < words.length; j++) {
                if(i == j) continue;

                if(words[j].contains(words[i])) {
                    res.add(words[i]);
                    break;
                }
            }
        }

        return res;
    }

    public List<String> stringMathcing(String[] words) {

        // t - O(n^2 * m^2)
        // s - O(1) extra space, O(n * m) space for the output list

        List<String> res = new ArrayList<>();
        Arrays.sort(words, Comparator.comparingInt(String::length));

        for(int i = 0; i < words.length; i++) {
            for(int j = i + 1; j < words.length; j++) {
                if(words[j].contains(words[i])) {
                    res.add(words[i]);
                    break;
                }
            }
        }

        return res;
    }


    public List<String> knuthMorrisPattern(String[] words) {
        List<String> res = new ArrayList<>();
        Arrays.sort(words, Comparator.comparingInt(String::length));

        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if (kmp(words[j], words[i]) != -1) {
                    res.add(words[i]);
                    break;
                }
            }
        }

        return res;
    }

    private int kmp(String word1, String word2) {
        // t- O(n^2 * m)
        // s - O(m) extra space, O(1) or O(n) space depending on the soring algos, O(n * m) space for o/p list
        int[] lps = new int[word2.length()];
        int prevLPS = 0, i = 1;

        while (i < word2.length()) {
            if (word2.charAt(i) == word2.charAt(prevLPS)) {
                lps[i] = prevLPS + 1;
                prevLPS++;
                i++;
            } else if (prevLPS == 0) {
                lps[i] = 0;
                i++;
            } else {
                prevLPS = lps[prevLPS - 1];
            }
        }

        i = 0;
        int j = 0;
        while (i < word1.length()) {
            if (word1.charAt(i) == word2.charAt(j)) {
                i++;
                j++;
            } else {
                if (j == 0) {
                    i++;
                } else {
                    j = lps[j - 1];
                }
            }

            if (j == word2.length()) {
                return i - word2.length();
            }
        }

        return -1;
    }


    public List<String> rabinKarpAlgo(String[] words) {
        List<String> res = new ArrayList<>();
        Arrays.sort(words, Comparator.comparingInt(String::length));

        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if (rabinKarp(words[j], words[i]) != -1) {
                    res.add(words[i]);
                    break;
                }
            }
        }

        return res;
    }

    private int rabinKarp(String word1, String word2) {
        // t- O(n^2 * m)
        // s - O(1) or O(n) space depending on the soring algos, O(n * m) space for o/p list
        int base1 = 31, mod1 = 768258391;
        int base2 = 37, mod2 = 685683731;
        int n = word1.length(), m = word2.length();

        long power1 = 1, power2 = 1;
        for (int k = 0; k < m; k++) {
            power1 = (power1 * base1) % mod1;
            power2 = (power2 * base2) % mod2;
        }

        long word1Hash1 = 0, word1Hash2 = 0;
        long word2Hash1 = 0, word2Hash2 = 0;

        for (int i = 0; i < m; i++) {
            word1Hash1 = (word1Hash1 * base1 + word2.charAt(i)) % mod1;
            word1Hash2 = (word1Hash2 * base2 + word2.charAt(i)) % mod2;
            word2Hash1 = (word2Hash1 * base1 + word1.charAt(i)) % mod1;
            word2Hash2 = (word2Hash2 * base2 + word1.charAt(i)) % mod2;
        }

        for (int i = 0; i <= n - m; i++) {
            if (word2Hash1 == word1Hash1 && word2Hash2 == word1Hash2) {
                return i;
            }

            if (i + m < n) {
                word2Hash1 = (word2Hash1 * base1 - word1.charAt(i) * power1 + word1.charAt(i + m)) % mod1;
                word2Hash2 = (word2Hash2 * base2 - word1.charAt(i) * power2 + word1.charAt(i + m)) % mod2;

                if (word2Hash1 < 0) word2Hash1 += mod1;
                if (word2Hash2 < 0) word2Hash2 += mod2;
            }
        }

        return -1;
    }

    public List<String> zAlgo(String[] words) {
        List<String> res = new ArrayList<>();
        Arrays.sort(words, Comparator.comparingInt(String::length));

        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if (zAlgorithm(words[j], words[i]) != -1) {
                    res.add(words[i]);
                    break;
                }
            }
        }

        return res;
    }

    private int zAlgorithm(String word1, String word2) {
        // t- O(n^2 * m)
        // s - O(1) or O(n) space depending on the soring algos, O(n * m) space for o/p list
        String s = word2 + "$" + word1;
        int n = s.length();
        int[] z = new int[n];
        int l = 0, r = 0;

        for (int i = 1; i < n; i++) {
            if (i <= r) {
                z[i] = Math.min(r - i + 1, z[i - l]);
            }
            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) {
                z[i]++;
            }
            if (i + z[i] - 1 > r) {
                l = i;
                r = i + z[i] - 1;
            }
        }

        for (int i = word2.length() + 1; i < n; i++) {
            if (z[i] == word2.length()) {
                return i - word2.length() - 1;
            }
        }

        return -1;
    }

    public List<String> stringMatchingUsingTrie(String[] words) {
        // t - O(n * m^2)
        // s - O(n * m^2) extra space,O(n * m) space for o/p list
        List<String> res = new ArrayList<>();
        Trie trie = new Trie();

        for (String word : words) {
            trie.insertSuffixes(word);
        }

        for (String word : words) {
            if (trie.search(word)) {
                res.add(word);
            }
        }

        return res;
    }



}

class TrieNodeStringMatching {
    TrieNodeStringMatching[] children;
    int cnt;

    TrieNodeStringMatching() {
        children = new TrieNodeStringMatching[26];
        cnt = 0;
    }
}

class Trie {
    TrieNodeStringMatching root;

    Trie() {
        root = new TrieNodeStringMatching();
    }

    void insertSuffixes(String word) {
        for (int i = 0; i < word.length(); i++) {
            TrieNodeStringMatching node = root;
            for (int j = i; j < word.length(); j++) {
                int idx = word.charAt(j) - 'a';
                if (node.children[idx] == null) {
                    node.children[idx] = new TrieNodeStringMatching();
                }

                node = node.children[idx];
                node.cnt++;
            }
        }
    }

    boolean search(String word) {
        TrieNodeStringMatching node = root;
        for (int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - 'a';
            node = node.children[idx];
        }
        return node.cnt > 1;
    }
}
