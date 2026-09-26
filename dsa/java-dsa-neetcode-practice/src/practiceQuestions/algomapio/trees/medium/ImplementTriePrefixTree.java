package practiceQuestions.algomapio.trees.medium;

import java.util.HashMap;
import java.util.Map;

public class ImplementTriePrefixTree {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Implement a trie to insert words, search for words, and check if any word starts with a given prefix.
     * Initialize a trie as a dictionary to store characters and their children.
     * For insert: Iterate through each character in the word, creating a new dictionary for unseen characters, and mark the end with a special character ('.').
     * For search: Traverse the trie for each character in the word; if a character is missing, return False; at the end, check for the end marker ('.').
     * For startsWith: Traverse the trie for each character in the prefix; if a character is missing, return False; return True if all characters are found.
     * */

    private class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEndOfWord = false;
    }

    private TrieNode root;

    /** Initialize your data structure here. */
    public ImplementTriePrefixTree() {
        root = new TrieNode();
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }
        node.isEndOfWord = true;
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return false;
            }
            node = node.children.get(c);
        }
        return node.isEndOfWord;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return false;
            }
            node = node.children.get(c);
        }
        return true;
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Implement Trie (Prefix Tree)
 * The problem asks us to design and implement a trie, also known as a prefix tree. A trie is a tree-like data structure that is particularly efficient for string-related operations, such as storing a dictionary of words, checking whether a word exists, or determining if any words share a common prefix. Unlike hash tables or arrays, tries can take advantage of shared prefixes among strings, which makes them both space- and time-efficient in many real-world applications like autocomplete or spell check.
 *
 * Core Operations of a Trie
 * A functional trie typically supports three primary operations:
 *
 * Insert: Adds a word to the trie. This is done by traversing each character in the word. If a character doesn't exist at the current level of the trie, we create a new node (or dictionary entry). Once all characters are processed, we mark the end of the word using a special marker—commonly a boolean flag or a special character like '#' or '.'.
 *
 * Search: Checks whether a full word exists in the trie. We traverse each character in the word. If any character is not found, we return false. If all characters are found and the end-of-word marker is also present, we return true.
 *
 * startsWith: Verifies whether any word in the trie starts with the given prefix. This is similar to search, except we do not require the end-of-word marker to be present—only that all characters in the prefix are found.
 *
 * Efficient Implementation Strategy
 * A typical and efficient implementation uses nested dictionaries to represent nodes. Each key in a dictionary is a character, and its value is another dictionary representing the child node. The root of the trie is simply an empty dictionary. Alternatively, you can implement this using a class-based node structure where each node contains a hashmap of children and a flag to indicate if the node is the end of a word.
 *
 * For example, inserting the word "apple" will create nodes for 'a', 'p', 'p', 'l', and 'e', with the last node marked to signify the end of the word.
 *
 * Time and Space Complexity
 * All three operations—insert, search, and startsWith—have a time complexity of O(m), where m is the length of the input word or prefix. This is because we traverse the trie one character at a time. The space complexity also depends on the number of unique characters stored across all inserted words. In the worst case, the total space is proportional to the sum of lengths of all inserted words.
 *
 * Edge Cases and Design Decisions
 * It’s important to handle duplicate insertions gracefully. If a word is inserted more than once, the trie structure should still remain valid, and the end-of-word marker should ensure correct behavior. Additionally, when designing the trie, one must decide whether to use a special end marker or a boolean flag in the node structure—either approach is valid, and each has its advantages depending on how the trie is extended in the future.
 *
 * Conclusion
 * The “Implement Trie” problem is a foundational exercise in mastering tree-based data structures for string manipulation. By leveraging the shared prefix structure of words, tries can significantly outperform brute-force string matching techniques in both speed and memory efficiency. This problem is a gateway to more advanced trie-based algorithms, including suffix tries, compressed tries, and applications in pattern matching and information retrieval.
 * */