package trie;

/**
 * TRIE (Prefix Tree)
 * 
 * What it is:
 * A specialized tree data structure used to store an associative array or a dynamic 
 * set where the keys are usually strings. Unlike a BST, nodes in a Trie do not store 
 * their associated key; instead, a node's position in the tree defines the key with 
 * which it is associated.
 * 
 * Approach/Strategy:
 * We use an array of 26 child pointers (assuming lowercase English letters `a-z`) 
 * for fast O(1) lookups per character. 
 * - Each node holds a boolean `isEndOfWord` to signify if a valid word ends there.
 * - Each node also holds an integer `prefixCount` to track how many words share 
 *   that specific path, which makes `countWordsWithPrefix` incredibly fast (O(L)).
 * 
 * Time/Space Complexity:
 * Operation            | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Insert               | O(L)            | O(L)             | L = length of word
 * Search               | O(L)            | O(1)             | L = length of word
 * StartsWith           | O(L)            | O(1)             | L = length of prefix
 * CountWithPrefix      | O(L)            | O(1)             | Uses precalculated prefixCount
 * Delete               | O(L)            | O(1)             | Decrements prefixCount
 * 
 * Real-world analogy:
 * A smartphone keyboard's autocomplete feature. As you type 'c', 'a', 't', the system 
 * is traversing down a Trie. At the 't' node, it can instantly tell you if "cat" is 
 * a valid word, and it can look at the children of 't' to suggest words like "catch" 
 * or "caterpillar".
 */
public class Trie {

    private static class TrieNode {
        TrieNode[] children;
        boolean isEndOfWord;
        int prefixCount; // Number of words sharing this specific prefix path

        public TrieNode() {
            // Assuming strictly lowercase English letters a-z
            // If supporting all characters, a HashMap<Character, TrieNode> is preferred
            this.children = new TrieNode[26];
            this.isEndOfWord = false;
            this.prefixCount = 0;
        }
    }

    private final TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    /**
     * Inserts a word into the trie.
     */
    public void insert(String word) {
        if (word == null || word.isEmpty()) return;
        
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            int index = ch - 'a';
            
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            
            current = current.children[index];
            current.prefixCount++; // A word passes through this node
        }
        current.isEndOfWord = true;
    }

    /**
     * Returns true if the word is fully in the trie.
     */
    public boolean search(String word) {
        TrieNode node = getNode(word);
        return node != null && node.isEndOfWord;
    }

    /**
     * Returns true if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        return getNode(prefix) != null;
    }

    /**
     * Returns the exact count of words that start with the given prefix.
     */
    public int countWordsWithPrefix(String prefix) {
        TrieNode node = getNode(prefix);
        return node == null ? 0 : node.prefixCount;
    }

    /**
     * Deletes a word from the trie if it exists.
     * Iteratively traces the word, decrementing prefixCounts to maintain the integrity 
     * of the countWordsWithPrefix method.
     */
    public void delete(String word) {
        if (!search(word)) {
            return; // Word doesn't exist, do nothing
        }

        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            int index = ch - 'a';
            
            TrieNode nextNode = current.children[index];
            nextNode.prefixCount--;
            
            // Optimization: If a node's prefix count drops to 0, no words use this branch anymore.
            // We can completely sever the link to save memory and avoid further traversal.
            if (nextNode.prefixCount == 0) {
                current.children[index] = null;
                return; 
            }
            
            current = nextNode;
        }
        
        // If we reach the end and didn't sever a link (meaning other words share this exact path), 
        // we just unmark the end-of-word flag.
        current.isEndOfWord = false;
    }

    /**
     * Private helper to traverse down to the node representing the end of a string.
     */
    private TrieNode getNode(String str) {
        if (str == null) return null;
        
        TrieNode current = root;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            int index = ch - 'a';
            
            if (current.children[index] == null) {
                return null;
            }
            current = current.children[index];
        }
        return current;
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- TRIE (PREFIX TREE) DEMO ---");
        
        Trie trie = new Trie();
        
        trie.insert("apple");
        trie.insert("app");
        trie.insert("apricot");
        trie.insert("bat");
        trie.insert("batch");
        
        System.out.println("Inserted: apple, app, apricot, bat, batch\n");
        
        // 1. Search (Exact Match)
        System.out.println("Search 'app': " + trie.search("app")); // true
        System.out.println("Search 'apple': " + trie.search("apple")); // true
        System.out.println("Search 'apri': " + trie.search("apri")); // false (It's a prefix, not a full word)
        
        // 2. StartsWith
        System.out.println("\nStartsWith 'ap': " + trie.startsWith("ap")); // true
        System.out.println("StartsWith 'ba': " + trie.startsWith("ba")); // true
        System.out.println("StartsWith 'cat': " + trie.startsWith("cat")); // false
        
        // 3. Prefix Counts
        System.out.println("\nCount words starting with 'ap': " + trie.countWordsWithPrefix("ap")); // 3 (app, apple, apricot)
        System.out.println("Count words starting with 'bat': " + trie.countWordsWithPrefix("bat")); // 2 (bat, batch)
        
        // 4. Deletion
        System.out.println("\nDeleting 'app'...");
        trie.delete("app");
        
        System.out.println("Search 'app': " + trie.search("app")); // false
        System.out.println("Search 'apple': " + trie.search("apple")); // true (should remain completely unaffected!)
        System.out.println("Count words starting with 'ap': " + trie.countWordsWithPrefix("ap")); // 2 (apple, apricot)
        
        // 5. Memory cleanup optimization check
        System.out.println("\nDeleting 'apricot' (which has a unique branch 'ricot')...");
        trie.delete("apricot");
        System.out.println("Count words starting with 'ap': " + trie.countWordsWithPrefix("ap")); // 1 (apple)
    }
}
