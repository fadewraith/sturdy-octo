package compilers.expressionparsing;

import java.util.Stack;
import java.util.Map;
import java.util.HashMap;

/**
 * WHAT IT IS: The Shunting Yard algorithm by Edsger Dijkstra is a method for parsing mathematical expressions specified in infix notation and producing a postfix notation string (Reverse Polish Notation) or an abstract syntax tree.
 * STRATEGY: Use a stack to hold operators and a string builder (or queue) for the output. Iterate through tokens. Output operands immediately. Push operators onto the stack based on precedence and associativity rules, popping and outputting operators of higher or equal precedence. Parentheses have special handling (push '(', pop until '(' on ')').
 * TIME/SPACE COMPLEXITY: Time: O(N) where N is the length of the expression. Space: O(N) for the stack and output.
 * REAL-WORLD ANALOGY / USE CASE: Train switching yard (hence the name), where cars (operators) are shunted to side tracks (stack) until their correct order in the train (output) is reached. Used in calculators, compilers, and interpreters.
 * WHEN TO USE / COMBINATION: Use when you need to evaluate mathematical expressions with respect to operator precedence and parentheses. Often combined with a Postfix Evaluator.
 * 
 * PSEUDOCODE:
 * while there are tokens to be read:
 *   read a token
 *   if token is a number: add it to output queue
 *   if token is a function: push to stack
 *   if token is an operator:
 *     while (there is an operator o2 at top of stack and (o1 is left-associative and its precedence is <= o2's precedence) or (o1 is right-associative and its precedence < o2's precedence)):
 *       pop o2 off stack, onto output queue
 *     push o1 onto stack
 *   if token is '(': push to stack
 *   if token is ')':
 *     while operator at top of stack is not '(':
 *       pop operator from stack onto output queue
 *     pop '(' from stack
 * while stack is not empty:
 *   pop operator onto output queue
 */
public class ShuntingYard {

    private static final Map<Character, Integer> PRECEDENCE = new HashMap<>();
    
    static {
        PRECEDENCE.put('+', 1);
        PRECEDENCE.put('-', 1);
        PRECEDENCE.put('*', 2);
        PRECEDENCE.put('/', 2);
        PRECEDENCE.put('^', 3);
    }

    private static boolean isOperator(char c) {
        return PRECEDENCE.containsKey(c);
    }

    private static boolean isLeftAssociative(char c) {
        return c != '^'; // Only power is right-associative in this basic set
    }

    public static String infixToPostfix(String infix) {
        StringBuilder output = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();
        
        // Remove spaces for easier processing
        infix = infix.replaceAll("\\s+", "");

        for (int i = 0; i < infix.length(); i++) {
            char token = infix.charAt(i);

            if (Character.isDigit(token) || Character.isLetter(token)) {
                // If it's a number or variable (single character for simplicity), add to output
                output.append(token);
            } else if (isOperator(token)) {
                while (!operatorStack.isEmpty() && isOperator(operatorStack.peek())) {
                    char top = operatorStack.peek();
                    if ((isLeftAssociative(token) && PRECEDENCE.get(token) <= PRECEDENCE.get(top)) ||
                        (!isLeftAssociative(token) && PRECEDENCE.get(token) < PRECEDENCE.get(top))) {
                        output.append(operatorStack.pop());
                    } else {
                        break;
                    }
                }
                operatorStack.push(token);
            } else if (token == '(') {
                operatorStack.push(token);
            } else if (token == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    output.append(operatorStack.pop());
                }
                if (!operatorStack.isEmpty() && operatorStack.peek() == '(') {
                    operatorStack.pop();
                } else {
                    throw new IllegalArgumentException("Mismatched parentheses");
                }
            } else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }

        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek() == '(') {
                throw new IllegalArgumentException("Mismatched parentheses");
            }
            output.append(operatorStack.pop());
        }

        return output.toString();
    }

    public static void main(String[] args) {
        System.out.println("--- Shunting Yard Algorithm (Infix to Postfix) ---");
        
        String[] testCases = {
            "A+B*C",
            "(A+B)*C",
            "A+B*C-D/E",
            "3+4*2/(1-5)^2^3",
            "A^B^C",
            "((A+B)" // Invalid
        };

        for (String test : testCases) {
            System.out.print("Infix: " + test + " -> Postfix: ");
            try {
                System.out.println(infixToPostfix(test));
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }
}
