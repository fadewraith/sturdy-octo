package algorithms.gametheory;

/**
 * WHAT IT IS:
 * A backtracking algorithm used in decision making and game theory to find the optimal move for a player, assuming the opponent also plays optimally.
 * 
 * STRATEGY:
 * Explores all possible game states to a certain depth. It simulates the game: maximizing the score for the "maximizer" player and minimizing the score for the "minimizer" opponent.
 * 
 * TIME/SPACE COMPLEXITY:
 * Time: O(b^m) where b is branching factor and m is maximum depth.
 * Space: O(m) for the recursion stack.
 * 
 * REAL-WORLD ANALOGY / USE CASE:
 * Chess or Tic-Tac-Toe engines deciding their next best move.
 * 
 * WHEN TO USE / COMBINATION:
 * Use for zero-sum games with perfect information. Often combined with heuristic evaluation functions and alpha-beta pruning.
 * 
 * PSEUDOCODE:
 * function minimax(node, depth, isMaximizingPlayer):
 *     if depth == 0 or node is terminal:
 *         return heuristic_value_of_node
 *     if isMaximizingPlayer:
 *         value = -infinity
 *         for each child of node:
 *             value = max(value, minimax(child, depth - 1, false))
 *         return value
 *     else:
 *         value = +infinity
 *         for each child of node:
 *             value = min(value, minimax(child, depth - 1, true))
 *         return value
 */
public class Minimax {

    public static int minimax(int depth, int nodeIndex, boolean isMax, int[] scores, int h) {
        if (depth == h) {
            return scores[nodeIndex];
        }

        if (isMax) {
            return Math.max(minimax(depth + 1, nodeIndex * 2, false, scores, h),
                            minimax(depth + 1, nodeIndex * 2 + 1, false, scores, h));
        } else {
            return Math.min(minimax(depth + 1, nodeIndex * 2, true, scores, h),
                            minimax(depth + 1, nodeIndex * 2 + 1, true, scores, h));
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Minimax Algorithm ---");
        
        // Number of elements in scores must be a power of 2 for a full binary tree
        int[] scores = {3, 5, 2, 9, 12, 5, 23, 23};
        int n = scores.length;
        int h = (int) (Math.log(n) / Math.log(2));

        System.out.println("Tree leaves: [3, 5, 2, 9, 12, 5, 23, 23]");
        int optimalValue = minimax(0, 0, true, scores, h);
        System.out.println("Optimal value for Maximizer: " + optimalValue);
        
        int[] scores2 = {3, -2, 1, 9};
        int h2 = (int) (Math.log(scores2.length) / Math.log(2));
        System.out.println("\nTree leaves: [3, -2, 1, 9]");
        System.out.println("Optimal value for Maximizer: " + minimax(0, 0, true, scores2, h2));
    }
}
