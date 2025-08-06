package practiceQuestions.algomapio.arraysAndStrings.medium;

public class RotateImage {

    private static void solution(int[][] matrix) {
        int n = matrix.length;

        // transpose
        for(int i = 0; i < n; i++) {
            for(int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // reflection
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n /2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - j - 1];
                matrix[i][n - j - 1] = temp;
            }
        }
    }
}

/**
 * Detailed Explanation
 * Understanding the Problem: Rotate Image
 * The “Rotate Image” problem requires you to rotate an n × n 2D matrix 90 degrees clockwise, in-place. This means you must rearrange the values inside the matrix without using extra space for another matrix.
 *
 * For example, given:
 *
 * 1	2	3
 * 4	5	6
 * 7	8	9
 * The rotated matrix should be:
 * 7	4	1
 * 8	5	2
 * 9	6	3
 * Why This Problem Matters
 * Matrix rotation is a core concept in geometry, image processing, and computer graphics. Solving this problem builds your understanding of 2D array manipulation, in-place transformations, and symmetry properties. In interviews, it tests your ability to reason about indexes and operate efficiently with limited memory.
 *
 * Key Insight: Transpose + Reflect
 * The trick to solving this problem in-place lies in recognizing a two-step transformation:
 *
 * Transpose the matrix: Flip it across its main diagonal (top-left to bottom-right).
 * Reflect the matrix horizontally: Swap elements in each row from left to right.
 * These two operations, when combined, result in a 90-degree clockwise rotation.
 *
 * Step-by-Step Explanation
 * 1. Transpose the Matrix
 * Transposing means turning all rows into columns. For every cell (i, j) above the main diagonal, we swap it with (j, i).
 *
 * Loop through i = 0 to n - 1
 * For each i, loop j = i + 1 to n - 1
 * Swap matrix[i][j] and matrix[j][i]
 * After transposition, our example becomes:
 *
 * 1	4	7
 * 2	5	8
 * 3	6	9
 * 2. Reflect the Matrix Horizontally
 * Now we reverse each row to complete the 90° rotation. This swaps the elements on the left with those on the right.
 *
 * Loop through each row i = 0 to n - 1
 * For each row, swap elements at positions j and n - j - 1 for j = 0 to n / 2 - 1
 * Final rotated matrix:
 *
 * 7	4	1
 * 8	5	2
 * 9	6	3
 * Alternative Approach: Layer-by-Layer Rotation
 * Another solution involves rotating the matrix layer by layer (or ring by ring). For each square layer from the outside in:
 *
 * Store the top-left element
 * Move bottom-left to top-left
 * Move bottom-right to bottom-left
 * Move top-right to bottom-right
 * Move stored top-left value to top-right
 * Repeat this for each layer. Though this method is more complex, it avoids transposition logic and operates purely by index manipulation.
 *
 * Time and Space Complexity
 * Time Complexity: O(n²), since every element in the matrix is accessed and possibly swapped once.
 * Space Complexity: O(1), because all transformations are done in-place without allocating another matrix.
 *
 * Edge Cases
 * 1x1 matrix → stays the same after rotation
 * 2x2 matrix → fully rotates in just four swaps
 * Non-square matrix → not allowed; problem assumes square matrix
 * Conclusion
 * The “Rotate Image” problem elegantly demonstrates how simple geometric operations—transpose and reflect—can yield powerful transformations. It’s a prime example of solving problems using patterns and properties rather than brute-force logic. Mastering this in-place algorithm prepares you for more advanced matrix manipulation tasks, like rotating images in-place or optimizing spatial layouts.
 * */