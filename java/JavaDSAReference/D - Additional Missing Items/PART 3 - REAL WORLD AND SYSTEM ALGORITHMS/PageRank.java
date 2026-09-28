package algorithms.realworld;

import java.util.HashMap;
import java.util.Map;

/**
 * PAGERANK ALGORITHM
 * 
 * WHAT IT IS:
 * An algorithm used by Google Search to rank web pages in their search engine results.
 * It measures the importance of website pages by counting the number and quality of 
 * links to a page.
 * 
 * COMBINATION:
 * Graph (Adjacency List using HashMap) + Iterative Power Iteration.
 * 
 * STRATEGY:
 * - Initialize all nodes with a PageRank of 1 / N.
 * - In each iteration, a node distributes its PageRank equally among its outgoing links.
 * - We also apply a "Damping Factor" (usually 0.85), representing the probability 
 *   that a user continues clicking links vs. typing a new URL randomly.
 * - We repeat this until the values converge (or for a fixed number of iterations).
 * 
 * COMPLEXITY:
 * Time: O(Iterations * (V + E))
 * Space: O(V + E) to store the graph.
 */
public class PageRank {

    private Map<String, String[]> graph = new HashMap<>();
    private static final double DAMPING_FACTOR = 0.85;
    private static final int ITERATIONS = 20;

    public void addPage(String page, String[] outgoingLinks) {
        graph.put(page, outgoingLinks);
    }

    public void calculatePageRank() {
        int n = graph.size();
        Map<String, Double> ranks = new HashMap<>();
        
        // 1. Initialize all pages with rank 1/N
        for (String page : graph.keySet()) {
            ranks.put(page, 1.0 / n);
        }

        // 2. Power Iteration
        for (int i = 0; i < ITERATIONS; i++) {
            Map<String, Double> newRanks = new HashMap<>();
            
            // Base rank from random jumps (1 - d) / N
            double baseRank = (1.0 - DAMPING_FACTOR) / n;
            for (String page : graph.keySet()) {
                newRanks.put(page, baseRank);
            }

            // Distribute rank from links
            for (Map.Entry<String, String[]> entry : graph.entrySet()) {
                String page = entry.getKey();
                String[] outLinks = entry.getValue();
                
                if (outLinks.length > 0) {
                    double rankToGive = ranks.get(page) / outLinks.length;
                    for (String target : outLinks) {
                        // The target page receives a portion of this page's rank
                        newRanks.put(target, newRanks.get(target) + (DAMPING_FACTOR * rankToGive));
                    }
                }
            }
            ranks = newRanks;
        }

        // 3. Print final ranks
        System.out.println("Final PageRank scores:");
        ranks.entrySet().stream()
             .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
             .forEach(e -> System.out.printf("%s: %.4f\n", e.getKey(), e.getValue()));
    }

    public static void main(String[] args) {
        System.out.println("--- PAGERANK DEMO ---");
        PageRank pr = new PageRank();
        
        // Page A links to B and C
        // Page B links to C
        // Page C links to A
        // Page D links to C (D is a dead end after C)
        pr.addPage("A", new String[]{"B", "C"});
        pr.addPage("B", new String[]{"C"});
        pr.addPage("C", new String[]{"A"});
        pr.addPage("D", new String[]{"C"});

        pr.calculatePageRank();
        // Expected: C should have the highest rank since almost everything links to it!
    }
}
