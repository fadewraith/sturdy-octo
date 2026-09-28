package algorithms.gametheory;

/**
 * WHAT IT IS:
 * An optimization technique for the minimax algorithm that reduces the number of nodes evaluated in the search tree.
 * 
 * STRATEGY:
 * Maintains two values, alpha and beta, which represent the minimum score that the maximizing player is assured of and the maximum score that the minimizing player is assured of respectively. If beta <= alpha, the remaining branch can be pruned.
 * 
 * TIME/SPACE COMPLEXITY:
 * Time: O(b^(m/2)) in the best case, O(b^m) in worst case. Space: O(m).
 * 
 * REAL-WORLD ANALOGY / USE CASE:
 * Optimizing game AI like Deep Blue playing chess. It allows searching much deeper into the game tree in the same amount of time.
 * 
 * WHEN TO USE / COMBINATION:
 * Use whenever minimax is used, as it strictly improves performance without changing the outcome. Often used with move ordering for maximum pruning.
 * 
 * PSEUDOCODE:
 * function alphabeta(node, depth, alpha, beta, isMaximizingPlayer):
 *     if depth == 0 or node is terminal:
 *         return value of node
 *     if isMaximizingPlayer:
 *         value = -infinity
 *         for each child of node:
 *             value = max(value, alphabeta(child, depth - 1, alpha, beta, false))
 *             alpha = max(alpha, value)
 *             if alpha >= beta:
 *                 break // beta cut-off
 *         return value
 *     else:
 *         value = +infinity
 *         for each child of node:
 *             value = min(value, alphabeta(child, depth - 1, alpha, beta, true))
 *             beta = min(beta, value)
 *             if beta <= alpha:
 *                 break // alpha cut-off
 *         return value
 */
public class AlphaBetaPruning {

    static final int MIN = Integer.MIN_VALUE;
    static final int MAX = Integer.MAX_VALUE;

    public static int minimax(int depth, int nodeIndex, boolean maximizingPlayer, int[] values, int alpha, int beta) {
        // Base case: leaf node is reached
        if (depth == 3) { // Hardcoded depth for 8 elements
            return values[nodeIndex];
        }

        if (maximizingPlayer) {
            int best = MIN;
            for (int i = 0; i < 2; i++) {
                int val = minimax(depth + 1, nodeIndex * 2 + i, false, values, alpha, beta);
                best = Math.max(best, val);
                alpha = Math.max(alpha, best);
                if (beta <= alpha) {
                    break;
                }
            }
            return best;
        } else {
            int best = MAX;
            for (int i = 0; i < 2; i++) {
                int val = minimax(depth + 1, nodeIndex * 2 + i, true, values, alpha, beta);
                best = Math.min(best, val);
                beta = Math.min(beta, best);
                if (beta <= alpha) {
                    break;
                }
            }
            return best;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Alpha-Beta Pruning ---");
        int[] values = {3, 5, 6, 9, 1, 2, 0, -1};
        System.out.println("Tree leaves: [3, 5, 6, 9, 1, 2, 0, -1]");
        int optimalValue = minimax(0, 0, true, values, MIN, MAX);
        System.out.println("Optimal value for Maximizer: " + optimalValue);
    }
}
