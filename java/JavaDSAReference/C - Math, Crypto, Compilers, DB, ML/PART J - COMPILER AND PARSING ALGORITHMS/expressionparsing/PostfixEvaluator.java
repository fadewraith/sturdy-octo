package compilers.expressionparsing;

import java.util.Stack;

/**
 * WHAT IT IS: An algorithm to evaluate mathematical expressions in Postfix notation (Reverse Polish Notation), where operators follow their operands.
 * STRATEGY: Iterate through tokens from left to right. If the token is a number, push it onto a stack. If it's an operator, pop the required number of operands from the stack (typically two for binary operators), apply the operator to them, and push the result back onto the stack. The final result is the only item left on the stack.
 * TIME/SPACE COMPLEXITY: Time: O(N) where N is the number of tokens. Space: O(N) worst case if the expression is all numbers.
 * REAL-WORLD ANALOGY / USE CASE: Assembly line assembly. You accumulate raw materials (numbers) in a bin (stack). When a machine (operator) arrives, it takes the top materials, processes them, and puts the finished product back in the bin. Used in stack machines, calculators (HP calculators), and bytecode execution.
 * WHEN TO USE / COMBINATION: Used to execute expressions after they've been parsed and converted to postfix (e.g., via Shunting Yard).
 * 
 * PSEUDOCODE:
 * create empty stack
 * for each token in postfix expression:
 *   if token is a number:
 *     push onto stack
 *   else if token is an operator:
 *     pop right operand from stack
 *     pop left operand from stack
 *     evaluate operator on left and right operands
 *     push result onto stack
 * return top of stack
 */
public class PostfixEvaluator {

    public static double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();

        // Assuming space-separated tokens for multi-digit numbers, or single-digit without spaces
        // We will process character by character assuming single digit numbers for this basic example.
        // Or we can support spaces. Let's support space-separated tokens.
        
        String[] tokens = postfix.trim().split("\\s+");
        
        // If it's a single string with no spaces but multiple chars, we might treat each char as token
        if (tokens.length == 1 && postfix.length() > 1 && !postfix.matches("\\d+(\\.\\d+)?")) {
            tokens = postfix.split("");
        }

        for (String token : tokens) {
            if (token.isEmpty()) continue;
            
            if (isOperator(token)) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid postfix expression: not enough operands");
                }
                double val2 = stack.pop();
                double val1 = stack.pop();
                double result = applyOperator(token.charAt(0), val1, val2);
                stack.push(result);
            } else {
                try {
                    stack.push(Double.parseDouble(token));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid token found: " + token);
                }
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid postfix expression: too many operands");
        }

        return stack.pop();
    }

    private static boolean isOperator(String s) {
        return s.length() == 1 && "+-*/^".contains(s);
    }

    private static double applyOperator(char operator, double b, double a) {
        switch (operator) {
            case '+': return b + a;
            case '-': return b - a;
            case '*': return b * a;
            case '/': 
                if (a == 0) throw new ArithmeticException("Division by zero");
                return b / a;
            case '^': return Math.pow(b, a);
            default: throw new UnsupportedOperationException("Unknown operator: " + operator);
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Postfix Evaluator ---");
        
        // Using space separated for multi-digit support testing
        String[] testCases = {
            "3 4 +",           // 3 + 4 = 7
            "5 1 2 + 4 * + 3 -", // 5 + ((1 + 2) * 4) - 3 = 14
            "2 3 ^ 4 5 + *",   // (2^3) * (4+5) = 72
            "10 2 /",          // 10 / 2 = 5
            "3 +",             // Error: not enough operands
            "3 4 5 +"          // Error: too many operands
        };

        for (String test : testCases) {
            System.out.print("Postfix: '" + test + "' -> Result: ");
            try {
                System.out.println(evaluatePostfix(test));
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }
}
