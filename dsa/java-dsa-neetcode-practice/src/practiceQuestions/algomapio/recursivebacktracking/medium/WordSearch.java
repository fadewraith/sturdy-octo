package practiceQuestions.algomapio.recursivebacktracking.medium;

public class WordSearch {

    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        int W = word.length();

        if (m == 1 && n == 1) {
            return board[0][0] == word.charAt(0);
        }

        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (backtrack(board, word, i, j, 0, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean backtrack(char[][] board, String word, int i, int j, int index, boolean[][] visited) {
        if (index == word.length()) return true;

        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || visited[i][j] || board[i][j] != word.charAt(index)) {
            return false;
        }

        visited[i][j] = true;

        boolean found = backtrack(board, word, i + 1, j, index + 1, visited) ||
                backtrack(board, word, i - 1, j, index + 1, visited) ||
                backtrack(board, word, i, j + 1, index + 1, visited) ||
                backtrack(board, word, i, j - 1, index + 1, visited);

        visited[i][j] = false;
        return found;
    }

}


/**
 * Step-by-Step Thought Process
 * Understand the problem: Determine if a given word exists in a 2D grid of characters, where the word must be formed by adjacent cells (horizontally or vertically) without reusing cells.
 * Get the grid dimensions: m (rows) and n (columns), and the word length W.
 * Handle the edge case: If the grid is 1x1, check if the single cell matches the word and return the result.
 * Define a backtrack function that takes the current position (i, j) and the current index in the word. It returns True if the word is found, False otherwise.
 * In backtrack: If the index equals W, the entire word is found, return True. If the current cell (i, j) does not match the word’s character at index, return False.
 * Mark the current cell as visited (e.g., replace with '#'), then recursively try the four adjacent cells (right, down, left, up) if they are within bounds, passing index+1.
 * If any recursive call returns True, return True. Restore the cell’s original character and return False if no path works.
 * Iterate through each cell (i, j) in the grid as a starting point, calling backtrack with index 0. If backtrack returns True, return True.
 * Return False if no starting point yields a valid path.
 * */


/**
 * Understanding the Problem
 * The "Word Search" problem asks whether a given word can be formed by sequentially adjacent letters on a 2D grid of characters. A move is valid if it steps horizontally or vertically to a neighboring cell, and each cell can only be used once during a single search path. For example, if the word is "ABCCED" and the grid is arranged in such a way that this path exists by moving from cell to cell, then the result is true.
 *
 * Recursive Backtracking Strategy
 * Since the word can start at any cell and proceed in four directions, we must consider every position in the grid as a potential starting point. From each cell, we perform a depth-first search (DFS) to try to build the word, one character at a time.
 *
 * At every recursive call, we check whether the character at the current grid position matches the corresponding character in the word. If it doesn't match or if the cell has already been used in the current path, we backtrack. If it does match, we mark the cell as visited (often by temporarily changing its value to a placeholder like '#'), and recursively attempt to complete the rest of the word using adjacent cells.
 *
 * The recursion proceeds until we've either matched the full word or exhausted all valid options from the current path. After exploring all directions from a given cell, we must revert the cell to its original value so that it can be used in other paths.
 *
 * Handling Edge Cases
 * Before beginning the main logic, we handle edge cases such as an empty grid or a grid with only one character. These special cases help prevent unnecessary computation and ensure correctness when the input is minimal.
 *
 * Time and Space Complexity
 * In the worst-case scenario, we visit each cell and recursively try up to 4 directions for each character in the word. The time complexity is therefore O(m * n * 3^W), where m and n are the grid dimensions, and W is the length of the word. The 3 arises because after the first move, we can at most go in three remaining directions (we avoid reversing direction).
 *
 * The space complexity is O(W) for the recursion call stack, where W is the length of the word. We may also use O(m * n) auxiliary space if we track visited cells with a separate matrix, though overwriting characters is a common in-place approach.
 * */