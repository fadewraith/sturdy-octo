package algorithms.strings.suffixtree;

import java.util.HashMap;
import java.util.Map;

/**
 * SUFFIX TREE
 * 
 * WHAT IT IS:
 * A compressed Trie containing all suffixes of a string as paths from root to leaf.
 * 
 * SUFFIX ARRAY VS SUFFIX TREE (TRADE-OFFS):
 * // Suffix Array = simpler to build and more memory-efficient, but needs the LCP 
 * // array for some queries a suffix tree answers directly. 
 * // Suffix Tree = faster queries (O(m) for a pattern of length m) and more powerful 
 * // (multi-string generalized suffix trees exist), but is significantly harder to 
 * // construct efficiently (e.g., Ukkonen's algorithm) and uses more memory.
 * 
 * COMPLEXITY:
 * - This is a basic O(N^2) construction for teaching purposes. 
 * - *NOTE*: Ukkonen's Algorithm is the real linear-time O(N) approach used in production, 
 *   but it is notoriously complex to implement.
 */
public class SuffixTree {

    static class Node {
        Map<Character, Node> children = new HashMap<>();
        int index = -1; // -1 means it's an internal node. >= 0 means it's a leaf.
    }

    private Node root = new Node();

    // Naive O(N^2) construction
    public void buildTree(String text) {
        for (int i = 0; i < text.length(); i++) {
            insertSuffix(text.substring(i), i);
        }
    }

    private void insertSuffix(String suffix, int index) {
        Node curr = root;
        for (char c : suffix.toCharArray()) {
            curr.children.putIfAbsent(c, new Node());
            curr = curr.children.get(c);
        }
        curr.index = index;
    }

    public boolean search(String pattern) {
        Node curr = root;
        for (char c : pattern.toCharArray()) {
            if (!curr.children.containsKey(c)) {
                return false;
            }
            curr = curr.children.get(c);
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("--- SUFFIX TREE DEMO ---");
        SuffixTree st = new SuffixTree();
        
        // Append '$' as a terminal character (standard practice for suffix trees)
        String text = "banana$"; 
        st.buildTree(text);
        
        System.out.println("Tree built for text: " + text);
        System.out.println("Search 'nana': " + st.search("nana")); // true
        System.out.println("Search 'band': " + st.search("band")); // false
    }
}
