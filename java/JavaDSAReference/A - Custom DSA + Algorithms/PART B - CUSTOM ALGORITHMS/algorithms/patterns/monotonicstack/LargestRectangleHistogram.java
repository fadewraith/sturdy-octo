package algorithms.patterns.monotonicstack;

/**
 * LARGEST RECTANGLE IN A HISTOGRAM (+ Maximal Rectangle in a binary matrix)
 *
 * WHAT IT IS:
 * Bars of width 1 with heights h[0..n-1]. Find the largest rectangle that fits
 * entirely under the bars.  [2, 1, 5, 6, 2, 3] -> 10 (height 5 across bars 2..3).
 *
 * WHEN TO USE THIS:
 * - The flagship MONOTONIC STACK problem (harder sibling of Next Greater Element).
 * - Maximal Rectangle of 1s in a binary matrix: treat every row as the floor of a
 *   histogram whose bar heights are the counts of consecutive 1s above.
 *
 * KEY OBSERVATION:
 * The best rectangle uses some bar i as its SHORTEST bar. It then stretches left
 * and right until it meets a bar shorter than h[i]. So for every bar we need:
 *   left  = index of the previous smaller bar,  right = index of the next smaller bar
 *   area  = h[i] * (right - left - 1)
 *
 * STRATEGY (one pass, increasing stack of indices):
 * Keep bar indices on a stack with INCREASING heights. When bar i is lower than the
 * top, the top bar has just found its "next smaller" (i), and the bar below it on
 * the stack is its "previous smaller". Pop it and compute its area. A sentinel bar of
 * height 0 at the end flushes everything left on the stack.
 *
 * COMPLEXITY:
 * Time: O(N) - every index is pushed and popped once. Maximal rectangle: O(R * C).
 * Space: O(N) for the stack.
 */
public class LargestRectangleHistogram {

    /** Area of the largest rectangle (long, since height * width can exceed int). */
    public static long largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] stack = new int[n + 1];
        int top = -1;
        long best = 0;
        for (int i = 0; i <= n; i++) {
            int h = (i == n) ? 0 : heights[i]; // sentinel 0 empties the stack at the end
            while (top >= 0 && heights[stack[top]] >= h) {
                int height = heights[stack[top--]];
                int left = (top >= 0) ? stack[top] : -1; // previous smaller bar
                long width = i - left - 1;               // i is the next smaller bar
                best = Math.max(best, height * width);
            }
            stack[++top] = i;
        }
        return best;
    }

    /** Largest rectangle containing only 1s in a 0/1 matrix. */
    public static long maximalRectangle(int[][] matrix) {
        if (matrix.length == 0) return 0;
        int cols = matrix[0].length;
        int[] heights = new int[cols];
        long best = 0;
        for (int[] row : matrix) {
            for (int c = 0; c < cols; c++) {
                heights[c] = (row[c] == 1) ? heights[c] + 1 : 0; // consecutive 1s ending at this row
            }
            best = Math.max(best, largestRectangleArea(heights));
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println("--- LARGEST RECTANGLE IN HISTOGRAM DEMO ---");
        System.out.println("[2, 1, 5, 6, 2, 3] -> " + largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3})); // 10
        System.out.println("[2, 4]             -> " + largestRectangleArea(new int[]{2, 4}));             // 4
        System.out.println("[3, 3, 3]          -> " + largestRectangleArea(new int[]{3, 3, 3}));          // 9

        int[][] matrix = {
            {1, 0, 1, 0, 0},
            {1, 0, 1, 1, 1},
            {1, 1, 1, 1, 1},
            {1, 0, 0, 1, 0}
        };
        System.out.println("Maximal rectangle of 1s: " + maximalRectangle(matrix)); // 6
    }
}
