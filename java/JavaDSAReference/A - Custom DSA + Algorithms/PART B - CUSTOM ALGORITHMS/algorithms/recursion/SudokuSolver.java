package algorithms.recursion;

/**
 * SUDOKU SOLVER
 * 
 * WHAT IT IS:
 * Solves a 9x9 Sudoku grid using Backtracking.
 * 
 * STRATEGY:
 * - Scan the board for an empty cell ('.').
 * - Try placing numbers '1' through '9' in that cell.
 * - Constraint Check: Verify if placing the number violates the row, column, or 3x3 box.
 * - If valid: CHOOSE (place number), EXPLORE (recurse to solve the rest of the board).
 * - If the recursion returns true, we are done!
 * - If false, UN-CHOOSE (reset to '.') and try the next number.
 * 
 * INTERVIEW NOTE:
 * This is one of the most time-consuming backtracking problems to code live on a whiteboard. 
 * The constraint checking logic (`isValid`) is tedious. Understanding the PRUNING strategy 
 * (instantly stopping if a number violates rules rather than building a full 9x9 board 
 * and checking it at the end) matters much more than raw coding speed.
 * 
 * COMPLEXITY:
 * Time: O(9^(Empty Cells)). (Massively optimized by the tight constraints).
 * Space: O(Empty Cells) for recursion stack.
 */
public class SudokuSolver {

    public static boolean solveSudoku(char[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                
                if (board[row][col] == '.') {
                    // Try every possible number
                    for (char c = '1'; c <= '9'; c++) {
                        if (isValid(board, row, col, c)) {
                            // 1. Choose
                            board[row][col] = c;
                            
                            // 2. Explore
                            if (solveSudoku(board)) {
                                return true; // Found the solution!
                            }
                            
                            // 3. Un-Choose
                            board[row][col] = '.';
                        }
                    }
                    // If no number 1-9 works in this empty cell, this path is dead. Backtrack!
                    return false;
                }
            }
        }
        // If we loop through the entire board and find no '.', the board is fully solved.
        return true; 
    }

    private static boolean isValid(char[][] board, int row, int col, char c) {
        int boxRowStart = 3 * (row / 3);
        int boxColStart = 3 * (col / 3);

        for (int i = 0; i < 9; i++) {
            // Check row
            if (board[row][i] == c) return false;
            // Check column
            if (board[i][col] == c) return false;
            // Check 3x3 sub-box
            if (board[boxRowStart + i / 3][boxColStart + i % 3] == c) return false;
        }
        return true;
    }

    public static void printBoard(char[][] board) {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) System.out.println("------+-------+------");
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) System.out.print("| ");
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println("--- SUDOKU SOLVER DEMO ---");
        
        char[][] board = {
            {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
            {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
            {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

        System.out.println("Unsolved Board:");
        printBoard(board);
        
        if (solveSudoku(board)) {
            System.out.println("\nSolved Board:");
            printBoard(board);
        } else {
            System.out.println("No solution exists!");
        }
    }
}
