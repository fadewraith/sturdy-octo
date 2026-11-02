package algorithms.gametheory;

import java.util.Arrays;

/**
 * NIM and SPRAGUE-GRUNDY NUMBERS
 *
 * WHAT IT IS:
 * NIM: several piles of stones. Players alternate; a move removes any positive
 * number of stones from ONE pile. Whoever takes the last stone WINS.
 *
 * BOUTON'S THEOREM:
 * XOR all pile sizes (the "nim-sum").
 *   nim-sum != 0 -> the player to move WINS (with perfect play)
 *   nim-sum == 0 -> the player to move LOSES
 * Winning move: find a pile p with (p XOR nimSum) < p and reduce it to p XOR nimSum.
 * That makes the new nim-sum 0, handing the opponent a losing position. From a
 * nim-sum-0 position, EVERY move makes it non-zero again. That's the whole proof.
 *
 * SPRAGUE-GRUNDY - EVERY fair game is secretly Nim:
 * For any impartial game (both players have the same moves, last move wins), give
 * each position a Grundy number:
 *   grundy(position) = mex { grundy(p) : p reachable in one move }
 *   mex ("minimum excludant") = the smallest non-negative integer NOT in the set.
 *   grundy == 0 <-> losing position for the player to move.
 * A position with Grundy number g behaves exactly like a Nim pile of size g. So the
 * SUM of several independent games (you move in one of them per turn) is won iff
 * the XOR of their Grundy numbers is non-zero.
 *
 * EXAMPLE - SUBTRACTION GAME: from a pile you may remove only 1, 3 or 4 stones.
 *   grundy[n] = mex { grundy[n - m] : m in moves, m <= n }
 * Several such piles at once: XOR their Grundy numbers.
 *
 * WHEN TO USE THIS:
 * "Who wins if both play optimally?" questions about take-away games, coin games,
 * games on graphs - whenever the game splits into independent parts.
 *
 * COMPLEXITY:
 * Nim winner / winning move: O(number of piles).
 * Grundy table up to N with M allowed moves: O(N * M).
 */
public class NimGame {

    public static int nimSum(int[] piles) {
        int x = 0;
        for (int p : piles) {
            if (p < 0) throw new IllegalArgumentException("negative pile");
            x ^= p;
        }
        return x;
    }

    /** True if the player about to move wins with perfect play. */
    public static boolean firstPlayerWins(int[] piles) {
        return nimSum(piles) != 0;
    }

    /** A winning move {pileIndex, newSize}, or null if every move loses. */
    public static int[] winningMove(int[] piles) {
        int x = nimSum(piles);
        if (x == 0) return null;
        for (int i = 0; i < piles.length; i++) {
            int target = piles[i] ^ x;
            if (target < piles[i]) return new int[]{i, target};
        }
        return null; // unreachable: the pile holding x's highest bit always works
    }

    /** Smallest non-negative integer not present among values[0 .. count-1]. */
    public static int mex(int[] values, int count) {
        boolean[] present = new boolean[count + 1]; // the mex is at most count
        for (int i = 0; i < count; i++) {
            if (values[i] >= 0 && values[i] <= count) present[values[i]] = true;
        }
        int m = 0;
        while (present[m]) m++;
        return m;
    }

    /** Grundy numbers 0..maxN for the subtraction game with the given allowed moves. */
    public static int[] subtractionGameGrundy(int maxN, int[] moves) {
        int[] g = new int[maxN + 1];
        int[] reachable = new int[moves.length];
        for (int n = 1; n <= maxN; n++) {
            int count = 0;
            for (int m : moves) {
                if (m <= 0) throw new IllegalArgumentException("moves must be positive");
                if (m <= n) reachable[count++] = g[n - m];
            }
            g[n] = mex(reachable, count);
        }
        return g;
    }

    /** Several subtraction-game piles at once: first player wins iff XOR of Grundy numbers != 0. */
    public static boolean subtractionGameFirstPlayerWins(int[] piles, int[] moves) {
        int max = 0;
        for (int p : piles) max = Math.max(max, p);
        int[] g = subtractionGameGrundy(max, moves);
        int x = 0;
        for (int p : piles) x ^= g[p];
        return x != 0;
    }

    public static void main(String[] args) {
        System.out.println("--- NIM / SPRAGUE-GRUNDY DEMO ---");

        int[] piles = {3, 4, 5};
        System.out.println("Piles " + Arrays.toString(piles) + ": nim-sum " + nimSum(piles)
                + ", first player wins? " + firstPlayerWins(piles)); // 2, true
        int[] move = winningMove(piles);
        System.out.println("Winning move: pile " + move[0] + " -> " + move[1] + " stones"); // pile 0 -> 1
        piles[move[0]] = move[1];
        System.out.println("After it: " + Arrays.toString(piles) + ", nim-sum " + nimSum(piles)); // [1, 4, 5], 0

        int[] moves = {1, 3, 4};
        System.out.println("\nSubtraction game, moves {1, 3, 4}, Grundy numbers 0..14: "
                + Arrays.toString(subtractionGameGrundy(14, moves)));
        // [0, 1, 0, 1, 2, 3, 2, 0, 1, 0, 1, 2, 3, 2, 0] - repeats with period 7; 0 = losing
        System.out.println("Piles {2, 7} in that game: first player wins? "
                + subtractionGameFirstPlayerWins(new int[]{2, 7}, moves)); // grundy 0 ^ 0 = 0 -> false
    }
}
