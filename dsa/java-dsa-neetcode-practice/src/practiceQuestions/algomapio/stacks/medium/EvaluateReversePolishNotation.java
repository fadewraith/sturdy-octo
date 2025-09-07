package practiceQuestions.algomapio.stacks.medium;

import java.util.Stack;

public class EvaluateReversePolishNotation {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Evaluate an expression given in Reverse Polish Notation using a list of tokens (numbers or operators).
     * Initialize an empty stack to store operands.
     * Iterate through each token in the input list.
     * If the token is an operator (+, -, *, /), pop the last two numbers (b and a) from the stack.
     * Perform the operation (a + b, a - b, a * b, or a / b with proper rounding for division).
     * Push the result back onto the stack.
     * If the token is a number, convert it to an integer and push it onto the stack.
     * Return the single value left on the stack as the result.
     * */

    private static int solution(String[] tokens) {
        Stack<Integer> stack = new Stack<>();

        for(String token : tokens) {
            if(token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
                int b = stack.pop();
                int a = stack.pop();

                switch(token) {
                    case "+":
                        stack.push(a + b);
                        break;
                    case "-":
                        stack.push(a - b);
                        break;
                    case "*":
                        stack.push(a * b);
                        break;
                    case "/":
                        double div = (double) a / b;
                        stack.push(div < 0 ? (int) Math.ceil(div) : (int) Math.floor(div));
                        break;
                }
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        // Test case 1: Basic operations
        String[] test1 = {"2", "1", "+", "3", "*"};
        System.out.println("Test 1 - Expected: 9, Actual: " + solution(test1)); // ((2 + 1) * 3) = 9
        
        // Test case 2: Division and negative numbers
        String[] test2 = {"4", "13", "5", "/", "+"};
        System.out.println("Test 2 - Expected: 6, Actual: " + solution(test2)); // 4 + (13 / 5) = 6
        
        // Test case 3: Multiple operations
        String[] test3 = {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
        System.out.println("Test 3 - Expected: 22, Actual: " + solution(test3)); // ((10 * (6 / ((9 + 3) * -11))) + 17) + 5 = 22
        
        // Test case 4: Single number
        String[] test4 = {"42"};
        System.out.println("Test 4 - Expected: 42, Actual: " + solution(test4)); // 42
        
        // Test case 5: Negative division
        String[] test5 = {"5", "-2", "/"};
        System.out.println("Test 5 - Expected: -2, Actual: " + solution(test5)); // 5 / -2 = -2 (truncated towards zero)
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Evaluate Reverse Polish Notation (RPN)
 * The “Evaluate Reverse Polish Notation” problem asks you to compute the result of a mathematical expression written in postfix notation (also known as Reverse Polish Notation or RPN). In this notation, every operator follows its operands, eliminating the need for parentheses to define precedence.
 *
 * For example:
 *
 * Input: ["2", "1", "+", "3", "*"]
 * Output: 9
 * Explanation: ((2 + 1) * 3) = 9
 * Why This Problem Matters
 * Evaluating expressions in postfix form is a classic stack-based problem that helps reinforce how expression parsing works. This technique is commonly used in compiler design, calculator implementations, and parsing tools where performance and operator precedence are essential.
 *
 * Stack-Based Solution
 * Since RPN eliminates parentheses and always places the operator after its operands, we can process the expression left to right using a stack. When we encounter a number, we push it onto the stack. When we see an operator, we pop the last two numbers from the stack, apply the operation, and push the result back.
 *
 * Steps:
 * Initialize an empty stack.
 * Iterate through each token in the input list.
 * If the token is an operator:
 * Pop the top two elements from the stack: first b, then a.
 * Evaluate a op b, where op is one of +, -, *, /.
 * Push the result back onto the stack.
 * If the token is a number, convert it to an integer and push it onto the stack.
 * At the end of the iteration, the stack will contain one element — the result of the expression.
 * Example Walkthrough
 * Input: ["4", "13", "5", "/", "+"]
 * Steps:
 *
 * Push 4 → stack: [4]
 * Push 13 → stack: [4, 13]
 * Push 5 → stack: [4, 13, 5]
 * Encounter "/" → pop 5 and 13 → 13 / 5 = 2 → push 2 → stack: [4, 2]
 * Encounter "+" → pop 2 and 4 → 4 + 2 = 6 → push 6 → stack: [6]
 * Final result = 6
 * Operator Edge Case: Integer Division
 * In the case of division, the result should truncate toward zero (e.g., int(5 / -2) = -2). Most programming languages handle this automatically when using integer division (e.g., int(a / b) in Python or Math.trunc(a / b) in JavaScript).
 *
 * Time and Space Complexity
 * Time Complexity: O(n), where n is the number of tokens. Each token is processed exactly once.
 * Space Complexity: O(n), in the worst case where all tokens are numbers and pushed onto the stack.
 *
 * Edge Cases to Consider
 * Only one token → return it directly
 * Expression with negative numbers → handle sign correctly
 * Integer division rounding toward zero
 * Division by zero → input constraints typically prevent this
 * Conclusion
 * The “Evaluate Reverse Polish Notation” problem is a cornerstone of stack-based algorithm practice. It illustrates how expression evaluation can be made simple and efficient using a linear scan and stack data structure. Understanding this approach is not only helpful for coding interviews but also for systems involving custom scripting, expression parsing, or calculator logic.
 * */