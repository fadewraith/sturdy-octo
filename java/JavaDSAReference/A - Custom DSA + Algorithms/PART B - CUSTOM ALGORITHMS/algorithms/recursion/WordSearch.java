package algorithms.recursion;

/**
 * WORD SEARCH (Grid Backtracking)
 *
 * WHAT IT IS:
 * Does a word exist in a letter grid, spelled by moving between 4-directionally
 * adjacent cells, using each cell at most ONCE?
 *
 * WHEN TO USE THIS:
 * - Boggle-style games, the template for "find a path in a grid under constraints".
 * - Many words at once (Word Search II): put the words in a Trie and walk the trie
 *   alongside the grid DFS, so shared prefixes are explored only once.
 *
 * STRATEGY (DFS + backtracking):
 * For every cell matching word[0], DFS:
 *   dfs(r, c, i): cell (r, c) must equal word[i].
 *     - if i is the last index -> found
 *     - CHOOSE: mark the cell used (overwrite it with a sentinel '#')
 *     - EXPLORE: try the 4 neighbours with i + 1
 *     - UN-CHOOSE: restore the original letter (so other paths can use it)
 * Overwriting the board instead of keeping a separate visited[][] saves memory;
 * the board is always restored before returning, even on success.
 *
 * PRUNING (cheap and very effective):
 * - word longer than the number of cells -> impossible.
 * - some letter appears more often in the word than on the board -> impossible.
 * - If the last letter is rarer on the board than the first, search the REVERSED
 *   word: fewer starting points, smaller search tree.
 *
 * COMPLEXITY:
 * Time: O(R * C * 3^L) worst case (L = word length; 3 because we never step back
 *       onto the cell we came from).
 * Space: O(L) recursion depth.
 */
public class WordSearch {

    private static final int[] DR = {-1, 1, 0, 0};
    private static final int[] DC = {0, 0, -1, 1};

    public static boolean exist(char[][] board, String word) {
        if (word.isEmpty()) return true;
        if (board.length == 0) return false;
        int rows = board.length, cols = board[0].length;
        if (word.length() > rows * cols) return false;

        // letter-count pruning
        int[] boardCount = new int[Character.MAX_VALUE + 1];
        for (char[] row : board) for (char ch : row) boardCount[ch]++;
        int[] wordCount = new int[Character.MAX_VALUE + 1];
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (++wordCount[ch] > boardCount[ch]) return false;
        }
        // start from the rarer end
        if (boardCount[word.charAt(word.length() - 1)] < boardCount[word.charAt(0)]) {
            word = new StringBuilder(word).reverse().toString();
        }

        char[] w = word.toCharArray();
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (dfs(board, w, r, c, 0)) return true;
        return false;
    }

    private static boolean dfs(char[][] board, char[] w, int r, int c, int i) {
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length || board[r][c] != w[i]) return false;
        if (i == w.length - 1) return true;

        char saved = board[r][c];
        board[r][c] = '#';                         // choose: this cell is in use
        boolean found = false;
        for (int d = 0; d < 4 && !found; d++) {
            found = dfs(board, w, r + DR[d], c + DC[d], i + 1);
        }
        board[r][c] = saved;                       // un-choose: always restore
        return found;
    }

    public static void main(String[] args) {
        System.out.println("--- WORD SEARCH DEMO ---");
        char[][] board = {
            {'A', 'B', 'C', 'E'},
            {'S', 'F', 'C', 'S'},
            {'A', 'D', 'E', 'E'}
        };
        System.out.println("ABCCED: " + exist(board, "ABCCED")); // true
        System.out.println("SEE:    " + exist(board, "SEE"));    // true
        System.out.println("ABCB:   " + exist(board, "ABCB"));   // false (would reuse the B)
        System.out.println("Board restored: " + new String(board[0]) + " " + new String(board[1]) + " " + new String(board[2]));
    }
}
