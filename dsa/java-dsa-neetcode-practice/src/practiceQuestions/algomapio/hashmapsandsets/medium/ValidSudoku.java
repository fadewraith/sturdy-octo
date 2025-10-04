package practiceQuestions.algomapio.hashmapsandsets.medium;

import java.util.HashSet;
import java.util.Set;

public class ValidSudoku {

    private static boolean solution(char[][] board) {

        // validate rows
        for(int i = 0; i < 9; i++) {
            Set<Character> set = new HashSet<>();
            for(int j = 0; j < 9; j++) {
                char item = board[i][j];
                if(item != '.' && !set.add(item)) {
                    return false;
                }
            }
        }

        // validate columns
        for(int i = 0; i < 9; i++) {
            Set<Character> set = new HashSet<>();
            for(int j = 0; j < 9; j++) {
                char item = board[j][i];
                if(item != '.' && !set.add(item)) {
                    return false;
                }
            }
        }

        // validate 3x3 sub-grids
        int[][] starts = {{0, 0}, {0, 3}, {0, 6},
                {3, 0}, {3, 3}, {3, 6},
                {6, 0}, {6, 3}, {6, 6}};

        for(int[] start: starts) {
            Set<Character> set = new HashSet<>();
            for(int row = start[0]; row < start[0] + 2; row++) {
                for(int col = start[1]; col < start[1] + 3; col++) {
                    char item = board[row][col];
                    if(item != '.' && !set.add(item)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        char[][] board = {
                {'5','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}
        };
        System.out.println(solution(board));
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Valid Sudoku
 * The “Valid Sudoku” problem asks us to determine whether a partially filled 9×9 Sudoku board is valid. A board is valid if:
 *
 * Each row contains the digits 1–9 with no duplicates
 * Each column contains the digits 1–9 with no duplicates
 * Each of the nine 3×3 sub-boxes contains the digits 1–9 with no duplicates
 * Empty cells are represented by the character '.' and should be ignored during validation.
 *
 * Why This Problem Matters
 * Validating a Sudoku board is a useful exercise in applying set logic, matrix traversal, and multi-dimensional constraints. It frequently appears in coding interviews because it combines pattern checking with efficient scanning of 2D structures.
 *
 * Brute Force Approach: Rule-by-Rule Validation
 * The problem can be broken down into three separate validations:
 *
 * Check each row: Iterate across each row using a set to track seen digits. If a digit reappears, return false.
 * Check each column: For every column index, scan from top to bottom. Use a set to detect duplicate digits.
 * Check each 3x3 sub-box: There are 9 sub-boxes starting at positions (0,0), (0,3), (0,6), (3,0), (3,3), etc. For each sub-box:
 * Use a set to track seen digits
 * Iterate through the 3 rows and 3 columns within the sub-box
 * If a digit is already in the set, return false
 * If no violations are found in any row, column, or sub-box, the board is valid.
 *
 * Example Walkthrough
 * Here's a sample valid board layout:
 *
 * [
 *   ["5","3",".",".","7",".",".",".","."],
 *   ["6",".",".","1","9","5",".",".","."],
 *   [".","9","8",".",".",".",".","6","."],
 *   ["8",".",".",".","6",".",".",".","3"],
 *   ["4",".",".","8",".","3",".",".","1"],
 *   ["7",".",".",".","2",".",".",".","6"],
 *   [".","6",".",".",".",".","2","8","."],
 *   [".",".",".","4","1","9",".",".","5"],
 *   [".",".",".",".","8",".",".","7","9"]
 * ]
 * Each row, column, and 3×3 box in this board has no duplicates (ignoring '.'), so it is valid.
 *
 * Time and Space Complexity
 * Time Complexity: O(1) – The board is always 9×9, so we iterate over a constant number of cells.
 * Space Complexity: O(1) – We use fixed-size sets for rows, columns, and boxes, each handling at most 9 digits.
 *
 * Edge Cases to Consider
 * An entirely empty board → valid
 * A full board with valid entries → valid
 * A board with duplicate digits in any row, column, or box → invalid
 * A board with non-digit, non-dot characters → assume invalid unless input constraints guarantee otherwise
 * Conclusion
 * The “Valid Sudoku” problem teaches how to apply multiple constraints across different dimensions of a matrix. By using simple data structures like sets and scanning in a structured way, you can implement an elegant and efficient solution that checks all rules without overcomplication.
 * */