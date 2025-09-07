package practiceQuestions.algomapio.stacks.easy;

import java.util.Stack;

public class BaseballGame {

    private static int solution(String[] operations) {
        Stack<Integer> stack = new Stack<>();

        for(String item : operations) {
            if(item.equals("+")) {
                int top = stack.pop();
                int newTop = top + stack.peek();
                stack.push(top);
                stack.push(newTop);
            } else if(item.equals("D")) {
                stack.push(stack.peek() * 2);
            } else if(item.equals("C")) {
                stack.pop();
            } else {
                stack.push(Integer.parseInt(item));
            }
        }

        int sum = 0;
        while(!stack.isEmpty()) {
            sum += stack.pop();
        }

        return sum;
    }



    public static void main(String[] args) {
        // Test case 1: Example from problem
        String[] ops1 = {"5","2","C","D","+"};
        System.out.println("Test 1: " + solution(ops1)); // Expected: 30
        
        // Test case 2: Another example
        String[] ops2 = {"5","-2","4","C","D","9","+","+"};
        System.out.println("Test 2: " + solution(ops2)); // Expected: 27
        
        // Test case 3: Edge case with only numbers
        String[] ops3 = {"1", "2", "3", "4", "5"};
        System.out.println("Test 3: " + solution(ops3)); // Expected: 15
        
        // Test case 4: Edge case with consecutive Cs
        String[] ops4 = {"1", "C", "2", "C", "3", "C"};
        System.out.println("Test 4: " + solution(ops4)); // Expected: 0
    }
}

/**
 * Detailed Explanation
 * Understanding the Problem: Baseball Game
 * The “Baseball Game” problem is a simulation-based question where you’re asked to calculate the total score of a baseball game from a list of operations. Each operation is one of the following:
 *
 * Integer: Add this number to the score.
 * "+": Add the sum of the previous two scores.
 * "D": Double the previous score and add it.
 * "C": Invalidate and remove the previous score.
 * Your task is to evaluate the sequence of operations and return the total score after all operations are processed.
 *
 * Why This Problem Matters
 * This problem is a great test of your ability to simulate a stack-based process, especially where operations depend on previously recorded values. It is often asked in interviews because it challenges your understanding of state maintenance and control flow.
 *
 * Optimal Approach: Use a Stack
 * Since the rules of the problem depend on previous operations, a stack is the perfect data structure to keep track of the score history. You can push scores, pop them when canceled, and peek at the top of the stack to retrieve recent values.
 *
 * Steps:
 * Initialize an empty stack scores.
 * Loop through each operation in the input list:
 * If the operation is "+", compute the sum of the last two elements and push it onto the stack.
 * If the operation is "D", double the last score and push it onto the stack.
 * If the operation is "C", remove the last score by popping from the stack.
 * If it's a numeric string, convert it to an integer and push it onto the stack.
 * Once all operations are complete, sum all values in the stack to get the total score.
 * Example Walkthrough
 * Input: ["5", "2", "C", "D", "+"]
 * Execution steps:
 *
 * "5" → push 5 → stack: [5]
 * "2" → push 2 → stack: [5, 2]
 * "C" → pop 2 → stack: [5]
 * "D" → push 10 (2×5) → stack: [5, 10]
 * "+" → push 15 (5+10) → stack: [5, 10, 15]
 * Final score = 5 + 10 + 15 = 30
 *
 * Time and Space Complexity
 * Time Complexity: O(n), where n is the number of operations. Each operation is processed once.
 * Space Complexity: O(n), for storing up to n scores on the stack.
 *
 * Edge Cases to Consider
 * Multiple consecutive "C" operations
 * Using "+" or "D" when stack has fewer than 2 or 1 elements (guaranteed safe by constraints)
 * Empty operations list → return 0
 * Only numeric values → return sum of all
 * Conclusion
 * The “Baseball Game” problem is an excellent example of stack-based simulation. It helps solidify your understanding of how to use a stack for processing dynamic operations that depend on previously recorded values. This kind of problem frequently appears in interviews for companies that value problem-solving and data structure fluency.
 * */