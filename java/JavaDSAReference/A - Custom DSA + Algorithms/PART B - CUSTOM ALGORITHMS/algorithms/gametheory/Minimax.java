package algorithms.gametheory;

/**
 * MINIMAX WITH ALPHA-BETA PRUNING
 * 
 * WHAT IT IS:
 * A recursive algorithm used for decision-making in Game Theory (e.g., Chess, Tic-Tac-Toe).
 * It provides an optimal move for the player assuming the opponent also plays optimally.
 * 
 * ALPHA-BETA PRUNING:
 * An optimization that cuts off branches of the game tree that can't possibly affect 
 * the final decision, drastically reducing the search space!
 * 
 * WHY IT BELONGS IN 02A (FROM SCRATCH):
 * This is a classic recursive tree-traversal whiteboard question.
 * 
 * COMPLEXITY:
 * Standard Minimax Time: O(b^d) where b is branching factor, d is depth.
 * Alpha-Beta Time: O(b^(d/2)) in the best case (perfect move ordering).
 */
public class Minimax {

    // A mock representation of a game tree (leaf nodes contain the final score)
    // Positive score favors the Maximizer, negative favors the Minimizer
    private static int[] scores = {3, 5, 2, 9, 12, 5, 23, 23};

    /**
     * @param depth Current depth in game tree
     * @param nodeIndex Current node index in the array
     * @param isMax is it the Maximizer's turn?
     * @param alpha Best already explored option along path to root for Maximizer
     * @param beta Best already explored option along path to root for Minimizer
     * @param h Maximum depth of the tree
     */
    public static int minimax(int depth, int nodeIndex, boolean isMax, int alpha, int beta, int h) {
        
        // Terminating condition (leaf node reached)
        if (depth == h) {
            return scores[nodeIndex];
        }

        if (isMax) {
            int best = Integer.MIN_VALUE;
            // Go down left and right children
            for (int i = 0; i < 2; i++) {
                int val = minimax(depth + 1, nodeIndex * 2 + i, false, alpha, beta, h);
                best = Math.max(best, val);
                alpha = Math.max(alpha, best);
                
                // Alpha Beta Pruning!
                if (beta <= alpha) {
                    System.out.println("Pruned at depth " + depth + ", node " + nodeIndex);
                    break;
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 2; i++) {
                int val = minimax(depth + 1, nodeIndex * 2 + i, true, alpha, beta, h);
                best = Math.min(best, val);
                beta = Math.min(beta, best);
                
                // Alpha Beta Pruning!
                if (beta <= alpha) {
                    System.out.println("Pruned at depth " + depth + ", node " + nodeIndex);
                    break;
                }
            }
            return best;
        }
    }

    // Helper to log base 2
    private static int log2(int n) {
        return (n == 1) ? 0 : 1 + log2(n / 2);
    }

    public static void main(String[] args) {
        System.out.println("--- MINIMAX WITH ALPHA-BETA PRUNING DEMO ---");
        
        int n = scores.length;
        int height = log2(n);
        
        int optimalValue = minimax(0, 0, true, Integer.MIN_VALUE, Integer.MAX_VALUE, height);
        
        System.out.println("The optimal value for the Maximizer is: " + optimalValue);
        // Expected: 12
    }
}
