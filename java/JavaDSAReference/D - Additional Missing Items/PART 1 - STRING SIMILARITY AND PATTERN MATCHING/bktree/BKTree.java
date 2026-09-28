package bktree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BK-TREE (Burkhard-Keller Tree)
 * 
 * WHAT IT IS:
 * A tree data structure specifically designed for fast approximate string matching 
 * (fuzzy searching) in a metric space. 
 * 
 * WHY IT BEATS BRUTE FORCE:
 * A brute force fuzzy search compares the query string against EVERY string in a 
 * dataset (O(N) edit distance checks). 
 * A BK-Tree uses the Triangle Inequality property of Levenshtein Distance to prune 
 * massive sections of the tree! If I am searching for a string with tolerance `T`, 
 * and I am currently at a node with edit distance `D` from my query, I ONLY need 
 * to search children whose edge weights are between `D - T` and `D + T`.
 * 
 * STRATEGY:
 * - Each node represents a string.
 * - Edges are labeled with the Levenshtein distance between the parent and child strings.
 * 
 * COMPLEXITY:
 * Time: O(log N) average query time.
 * Space: O(N)
 */
public class BKTree {

    static class Node {
        String word;
        // Map from Edit Distance -> Child Node
        Map<Integer, Node> children = new HashMap<>();

        Node(String word) {
            this.word = word;
        }
    }

    private Node root;

    // Standard Levenshtein DP Matrix
    public static int editDistance(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0) dp[i][j] = j;
                else if (j == 0) dp[i][j] = i;
                else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i][j - 1], Math.min(dp[i - 1][j], dp[i - 1][j - 1]));
                }
            }
        }
        return dp[m][n];
    }

    public void add(String word) {
        if (root == null) {
            root = new Node(word);
            return;
        }

        Node curr = root;
        while (true) {
            int dist = editDistance(curr.word, word);
            if (dist == 0) return; // Word already exists

            if (!curr.children.containsKey(dist)) {
                curr.children.put(dist, new Node(word));
                break;
            } else {
                curr = curr.children.get(dist);
            }
        }
    }

    public List<String> search(String query, int tolerance) {
        List<String> results = new ArrayList<>();
        if (root != null) {
            searchRecursive(root, query, tolerance, results);
        }
        return results;
    }

    private void searchRecursive(Node node, String query, int tolerance, List<String> results) {
        int dist = editDistance(node.word, query);
        
        // If this node is within the tolerance threshold, add to results
        if (dist <= tolerance) {
            results.add(node.word);
        }

        // Triangle Inequality Optimization:
        // We only check children whose edge weights fall in the range [dist - tolerance, dist + tolerance]
        int minBound = dist - tolerance;
        int maxBound = dist + tolerance;

        for (Map.Entry<Integer, Node> entry : node.children.entrySet()) {
            int childDist = entry.getKey();
            if (childDist >= minBound && childDist <= maxBound) {
                searchRecursive(entry.getValue(), query, tolerance, results);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- BK-TREE DEMO ---");
        
        BKTree tree = new BKTree();
        String[] dictionary = {"some", "soft", "same", "mole", "soda", "salmon"};
        
        System.out.println("Adding dictionary words...");
        for (String word : dictionary) {
            tree.add(word);
        }

        String query = "sort";
        int tolerance = 2; // Allow up to 2 typos/edits
        
        System.out.println("\nQuery: '" + query + "' with max edit distance: " + tolerance);
        List<String> matches = tree.search(query, tolerance);
        
        System.out.println("Matches found: " + matches);
        // Expected: "soft" (1 edit), "some" (2 edits)
    }
}
