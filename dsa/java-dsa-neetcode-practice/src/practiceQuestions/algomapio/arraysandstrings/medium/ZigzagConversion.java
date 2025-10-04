package practiceQuestions.algomapio.arraysAndStrings.medium;

import java.util.ArrayList;
import java.util.List;

public class ZigzagConversion {

    private static String solution(String s, int numRows) {

        if(numRows == 1) return s;

        // Initialize a list of lists (rows) with numRows empty lists to store characters in each row.
        List<StringBuilder> rows = new ArrayList<>();
        for(int i = 0; i < Math.min(numRows, s.length()); i++) {
            rows.add(new StringBuilder());
        }

        int i = 0;
        boolean goingDown = false;

        for(char c : s.toCharArray()) {
            rows.get(i).append(c);
            if(i == 0 || i == numRows - 1) goingDown = !goingDown;
            i += goingDown ? 1 : -1;
        }

        StringBuilder res = new StringBuilder();
        for(StringBuilder row : rows) {
            res.append(row);
        }

        return res.toString();
    }
}

/**
 * Detailed Explanation
 * Understanding the Zigzag Conversion Problem
 * The Zigzag Conversion problem asks you to format a given string into a zigzag pattern across a specified number of rows, and then read the characters row-by-row to form the final output string. For example, if the input string is "PAYPALISHIRING" and the number of rows is 3, the zigzag pattern would visually look like this:
 *
 * P     A     H     N
 * A   P   L   S   I   I   G
 * Y     I     R
 *
 * Reading the characters line by line produces the output: "PAHNAPLSIIGYIR".
 *
 * When to Use Zigzag Formatting
 * This problem appears frequently in coding interviews because it tests string manipulation, control flow, and indexing logic. It's particularly useful for showcasing how you handle direction changes and how you organize character placement when working with visual patterns.
 *
 * Optimal Strategy to Build the Zigzag Pattern
 * The most efficient way to solve this problem is to simulate the zigzag traversal by maintaining a list of strings—one for each row. As you iterate over the input string character by character, append each character to the appropriate row and change the direction of traversal when you reach the top or bottom row.
 *
 * Begin by initializing a list with numRows empty strings. Then, use two variables: one for tracking the current row and one for determining the direction of traversal (down or up). If you're on the first row, start moving downward; if you're on the last row, reverse direction and begin moving upward. For every character in the input string, add it to the corresponding row, update the current row based on the direction, and continue this process until you've placed every character.
 *
 * Time and Space Complexity
 * Time Complexity: O(n), where n is the length of the input string. Each character is processed exactly once.
 * Space Complexity: O(n), since we're storing each character in one of the row lists before combining them.
 * Why This Problem Is Important
 * Zigzag Conversion is a great exercise in simulating state-based traversal and implementing a visual pattern with indexing logic. It’s also a problem that highlights your ability to recognize when extra structure (like direction flags or row containers) is needed to control data flow.
 *
 * Conclusion
 * By using a simple row tracking mechanism and toggling direction when needed, this problem can be solved efficiently and elegantly. Understanding this approach lays the foundation for more advanced pattern-based string manipulation problems that you may encounter in technical interviews.
 * */