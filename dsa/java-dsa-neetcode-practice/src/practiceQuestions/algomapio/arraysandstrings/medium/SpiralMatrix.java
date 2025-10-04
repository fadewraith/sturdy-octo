package practiceQuestions.algomapio.arraysandstrings.medium;

import java.util.ArrayList;
import java.util.List;

public class SpiralMatrix {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Traverse an m x n matrix in a spiral order (right, down, left, up) and return all elements in a list.
     * Get the dimensions of the matrix: m (rows) and n (columns).
     * Initialize an empty list ans to store the spiral order elements.
     * Initialize pointers i (row) and j (column) to 0, and a direction variable to RIGHT (1), with other directions UP (0), DOWN (2), and LEFT (3).
     * Initialize boundaries: UP_WALL to 0, RIGHT_WALL to n, DOWN_WALL to m, and LEFT_WALL to -1.
     * While the length of ans is less than m * n, process each direction:
     * For RIGHT: While j is less than RIGHT_WALL, append matrix[i][j] to ans, increment j. Then, increment i, decrement j, reduce RIGHT_WALL by 1, set direction to DOWN.
     * For DOWN: While i is less than DOWN_WALL, append matrix[i][j] to ans, increment i. Then, decrement i, decrement j, reduce DOWN_WALL by 1, set direction to LEFT.
     * For LEFT: While j is greater than LEFT_WALL, append matrix[i][j] to ans, decrement j. Then, decrement i, increment j, increment LEFT_WALL by 1, set direction to UP.
     * For UP: While i is greater than UP_WALL, append matrix[i][j] to ans, decrement i. Then, increment i, increment j, increment UP_WALL by 1, set direction to RIGHT.
     * Return ans as the spiral order of the matrix.
     * */

    private static List<Integer> solution(int[][] matrix) {

//        int[][] matrix = {
//                {1, 2, 3},
//                {4, 5, 6},
//                {7, 8, 9}
//        };
        List<Integer> res = new ArrayList<>();
        if(matrix.length == 0) return res;

        int m = matrix.length;
        int n = matrix[0].length;
        // Initialize direction variable with possible values
        int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
        int direction = RIGHT;

        // the top boundary of unvisited row
        int UP_WALL = 0;
        // the right boundary of unvisited columns
        int RIGHT_WALL = n;
        // the bottom boundary of unvisited rows
        int DOWN_WALL = m;
        // the left boundary of unvisited columns
        int LEFT_WALL = -1;

        // Use pointers i (row) and j (column) to track the current position
        int i = 0, j = 0;

        while(res.size() != m * n) {
            if(direction == RIGHT) {
                while(j < RIGHT_WALL) {
                    // [0][0],[0][1],[0][2]
                    res.add(matrix[i][j]);
                    j++; // 1, 2, 3
                }
                i++; // 1
                j--; // 2
                RIGHT_WALL--; // 3-- -> 2
                direction = DOWN;
            } else if (direction == DOWN) {
                while (i < DOWN_WALL) {
                    // [1][2], [2][2]
                    res.add(matrix[i][j]);
                    i++; // 2, 3
                }
                i--; // 3-- -> 2
                j--; // 1
                DOWN_WALL--; // 3-- -> 2
                direction = LEFT;
            } else if (direction == LEFT) {
                while (j > LEFT_WALL) {
                    // [2][1], [2][0]
                    res.add(matrix[i][j]);
                    j--; // 0, -1
                }
                i--; // 2-- -> 1
                j++; // 0
                LEFT_WALL++; // -1 -> 0
                direction = UP;
            } else {
                while (i > UP_WALL) {
                    // [1][0], [0][0]
                    res.add(matrix[i][j]);
                    i--;
                }
                i++;
                j++;
                UP_WALL++;
                direction = RIGHT;
            }

        }

        return res;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        System.out.println(solution(matrix));
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Spiral Matrix
 * The “Spiral Matrix” problem is a classic 2D array traversal question. Given an m x n matrix, your goal is to return all its elements in a spiral order—starting from the top-left corner and moving right, then down, then left, then up, and repeating this pattern inward until all elements are visited.
 *
 * For example, if the input is:
 *
 * 1	2	3
 * 4	5	6
 * 7	8	9
 * The output should be: [1, 2, 3, 6, 9, 8, 7, 4, 5].
 * Why This Problem Matters
 * This problem strengthens your understanding of matrix boundaries, direction handling, and loop control logic. It simulates real-world data processing patterns such as image filtering, robotic movement in 2D grids, or navigation algorithms in game development.
 *
 * Intuition Behind the Spiral Movement
 * The spiral pattern can be visualized as a set of layers or “walls” that shrink as you complete each full cycle (right → down → left → up). Each layer peels off one ring of the matrix. To manage this, we maintain four boundaries:
 *
 * UP_WALL: the top boundary of unvisited rows
 * RIGHT_WALL: the right boundary of unvisited columns
 * DOWN_WALL: the bottom boundary of unvisited rows
 * LEFT_WALL: the left boundary of unvisited columns
 * As we traverse in a direction, we shrink the corresponding boundary to prevent re-visiting already-traversed cells.
 *
 * Algorithm: Step-by-Step Approach
 * Determine the number of rows (m) and columns (n) in the matrix.
 * Initialize an empty list ans to store the spiral order result.
 * Initialize direction variable with possible values: RIGHT, DOWN, LEFT, UP.
 * Use pointers i (row) and j (column) to track the current position.
 * Set up wall boundaries:
 * UP_WALL = 0
 * DOWN_WALL = m
 * LEFT_WALL = -1
 * RIGHT_WALL = n
 * Loop until ans.length === m * n:
 * Right: Move while j < RIGHT_WALL. Append matrix[i][j] to result and increment j. After the loop, adjust pointers and decrement RIGHT_WALL.
 * Down: Move while i < DOWN_WALL. Append matrix[i][j] and increment i. Then decrement DOWN_WALL.
 * Left: Move while j > LEFT_WALL. Append matrix[i][j] and decrement j. Then increment LEFT_WALL.
 * Up: Move while i > UP_WALL. Append matrix[i][j] and decrement i. Then increment UP_WALL.
 * Example Walkthrough
 * Input:
 *
 * 1	2	3
 * 4	5	6
 * 7	8	9
 * Spiral steps:
 * Right → 1, 2, 3
 * Down → 6, 9
 * Left → 8, 7
 * Up → 4
 * Right → 5 (center element)
 * Final result: [1, 2, 3, 6, 9, 8, 7, 4, 5]
 *
 * Time and Space Complexity
 * Time Complexity: O(m × n), since we visit each element of the matrix exactly once.
 * Space Complexity: O(1) extra space (not counting the output array).
 *
 * Edge Cases to Consider
 * Empty matrix → return []
 * 1x1 matrix → return the single element
 * 1-row or 1-column matrix → spiral is simply the original order
 * Non-square matrix → ensure the algorithm handles unequal dimensions
 * Conclusion
 * The “Spiral Matrix” problem helps build intuition for controlled matrix traversal and multi-directional logic. By maintaining clear boundaries and moving in controlled directions, you can elegantly extract a spiral order from a 2D array. Mastering this problem improves your confidence in handling grid-based tasks, which are foundational in both interviews and real-world systems like image processing, simulations, and game development.
 * */