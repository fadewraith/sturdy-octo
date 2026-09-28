package algorithms.strings.ahocorasick;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * AHO-CORASICK ALGORITHM
 * 
 * WHAT IT IS:
 * A multi-pattern string search algorithm. It locates all occurrences of any of a 
 * finite number of keywords in a string of text.
 * 
 * STRATEGY:
 * Constructs a finite state machine (a Trie with extra links) that resembles a Trie 
 * but has additional "failure links" (similar to the failure function in KMP). 
 * These links allow fast transitions between failed string matches to other branches 
 * of the Trie that share a common prefix.
 * 
 * COMPLEXITY:
 * Time: O(N + M + Z) where N is text length, M is total length of all keywords, 
 *       and Z is the number of matches found.
 * Space: O(M * |Alphabet|)
 * 
 * WHEN TO USE THIS / APPLIES TO:
 * - Scanning a document for hundreds/thousands of keywords in ONE linear pass!
 * - Spam filters, intrusion detection systems, dictionary matching.
 * - Vastly superior to running KMP or Rabin-Karp individually for every pattern.
 * 
 * COMBINATION:
 * Trie + KMP Failure Links + BFS (to build the links level-by-level).
 * 
 * PSEUDOCODE:
 * 1. Build a Trie for all patterns.
 * 2. Use BFS to set 'fail' links:
 *    - For root's children, fail link is root.
 *    - For others, follow parent's fail link until a matching child is found.
 * 3. Search:
 *    - For each char in text, transition state in Trie.
 *    - If no transition, follow fail links.
 *    - If state has output, report match.
 */
public class AhoCorasick {

    static class Node {
        Map<Character, Node> children = new HashMap<>();
        Node fail;      // Failure link
        Node output;    // Dictionary suffix link
        String word;    // Stores the word if this node is the end of a pattern
    }

    private Node root = new Node();

    public void addPattern(String pattern) {
        Node curr = root;
        for (char c : pattern.toCharArray()) {
            curr.children.putIfAbsent(c, new Node());
            curr = curr.children.get(c);
        }
        curr.word = pattern;
    }

    public void buildAutomaton() {
        Queue<Node> queue = new LinkedList<>();

        root.fail = root;
        for (Node child : root.children.values()) {
            child.fail = root;
            queue.add(child);
        }

        while (!queue.isEmpty()) {
            Node curr = queue.poll();

            for (Map.Entry<Character, Node> entry : curr.children.entrySet()) {
                char c = entry.getKey();
                Node child = entry.getValue();

                Node failState = curr.fail;
                while (failState != root && !failState.children.containsKey(c)) {
                    failState = failState.fail;
                }
                
                if (failState.children.containsKey(c)) {
                    child.fail = failState.children.get(c);
                } else {
                    child.fail = root;
                }

                if (child.fail.word != null) {
                    child.output = child.fail;
                } else {
                    child.output = child.fail.output;
                }

                queue.add(child);
            }
        }
    }

    public void search(String text) {
        Node curr = root;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            while (curr != root && !curr.children.containsKey(c)) {
                curr = curr.fail;
            }

            if (curr.children.containsKey(c)) {
                curr = curr.children.get(c);
            } else {
                curr = root;
            }

            if (curr.word != null) {
                System.out.println("Found '" + curr.word + "' ending at index " + i);
            }

            Node temp = curr.output;
            while (temp != null) {
                System.out.println("Found '" + temp.word + "' ending at index " + i);
                temp = temp.output;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- AHO-CORASICK DEMO ---");
        AhoCorasick ac = new AhoCorasick();
        
        String[] patterns = {"he", "she", "his", "hers"};
        for (String p : patterns) {
            ac.addPattern(p);
        }
        
        ac.buildAutomaton();
        String text = "ushers";
        System.out.println("Text: " + text + "\nResults:");
        ac.search(text);
    }
}
