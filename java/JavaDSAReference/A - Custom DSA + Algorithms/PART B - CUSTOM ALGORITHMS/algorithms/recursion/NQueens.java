package algorithms.recursion;

/**
 * N-QUEENS PROBLEM
 * 
 * WHAT IT IS:
 * Place N chess queens on an N×N chessboard so that no two queens threaten each other.
 * 
 * STRATEGY (Backtracking with Pruning):
 * - We place queens one row at a time.
 * - For a given row, we try placing a queen in every column.
 * - Before placing, we check constraints (is this column safe? are the diagonals safe?).
 * - If safe: CHOOSE (place queen), EXPLORE (recurse to next row), UN-CHOOSE (remove queen).
 * - If we reach row == N, we have successfully placed all N queens!
 * 
 * COMPLEXITY:
 * Time: O(N!) (Extremely slow for large N, but pruning makes it viable for small N).
 * Space: O(N^2) for the board + O(N) for recursion stack.
 */
public class NQueens {

    public static void solveNQueens(int n) {
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }
        
        System.out.println("Solutions for " + n + "-Queens:");
        if (!backtrack(board, 0, n)) {
            System.out.println("No solution exists.");
        }
    }

    private static boolean backtrack(char[][] board, int row, int n) {
        // Base case: All queens have been placed successfully!
        if (row == n) {
            printBoard(board);
            System.out.println();
            return true; // Return false here instead if you want to find ALL solutions
        }

        boolean foundSolution = false;

        for (int col = 0; col < n; col++) {
            if (isSafe(board, row, col, n)) {
                // 1. Choose
                board[row][col] = 'Q';
                
                // 2. Explore
                foundSolution = backtrack(board, row + 1, n) || foundSolution;
                
                // 3. Un-Choose
                board[row][col] = '.';
            }
        }
        
        return foundSolution;
    }

    private static boolean isSafe(char[][] board, int row, int col, int n) {
        // Check column (above current row)
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q') return false;
        }
        
        // Check upper-left diagonal
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') return false;
        }
        
        // Check upper-right diagonal
        for (int i = row, j = col; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 'Q') return false;
        }
        
        return true;
    }

    private static void printBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println("--- N-QUEENS DEMO ---");
        solveNQueens(4); 
    }
}
