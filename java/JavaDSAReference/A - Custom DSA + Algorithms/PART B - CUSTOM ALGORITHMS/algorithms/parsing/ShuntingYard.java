package algorithms.parsing;

import java.util.Stack; // Using standard stack structurally as placeholder for custom stack

/**
 * SHUNTING YARD ALGORITHM & EXPRESSION EVALUATION
 * 
 * WHAT IT IS:
 * Dijkstra's Shunting Yard algorithm converts Infix expressions (e.g., 3 + 4 * 2) 
 * into Postfix expressions (Reverse Polish Notation: 3 4 2 * +).
 * Once in Postfix, the expression can be trivially evaluated using a Stack.
 * 
 * WHY IT BELONGS IN 02A (FROM SCRATCH):
 * This is the ultimate classic Stack interview question.
 * 
 * COMPLEXITY:
 * Time: O(N) to parse and O(N) to evaluate.
 * Space: O(N) for the stack.
 */
public class ShuntingYard {

    // Simple precedence rule
    private static int getPrecedence(char ch) {
        if (ch == '+' || ch == '-') return 1;
        if (ch == '*' || ch == '/') return 2;
        if (ch == '^') return 3;
        return -1;
    }

    // '^' is RIGHT-associative: 2^3^2 means 2^(3^2) = 512, not (2^3)^2 = 64.
    // Everything else is left-associative: 8-3-2 means (8-3)-2.
    private static boolean isRightAssociative(char ch) {
        return ch == '^';
    }

    /**
     * Converts infix to postfix. Output tokens are separated by single spaces
     * so multi-digit numbers stay intact: "12+3" -> "12 3 +".
     * Supports non-negative integers, variable names (letters), + - * / ^ and parentheses.
     */
    public static String infixToPostfix(String infix) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        int i = 0;
        while (i < infix.length()) {
            char c = infix.charAt(i);

            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            if (Character.isLetterOrDigit(c)) {
                // Read the WHOLE number / name, not just one character
                int start = i;
                while (i < infix.length() && Character.isLetterOrDigit(infix.charAt(i))) i++;
                result.append(infix, start, i).append(' ');
                continue;
            }

            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop()).append(' ');
                }
                if (stack.isEmpty()) return "Invalid Expression"; // unmatched ')'
                stack.pop();
            } else if (getPrecedence(c) > 0) {
                // Pop operators that must be applied first:
                // - higher precedence always
                // - equal precedence only if c is left-associative
                while (!stack.isEmpty() && stack.peek() != '(' &&
                       (getPrecedence(stack.peek()) > getPrecedence(c) ||
                        (getPrecedence(stack.peek()) == getPrecedence(c) && !isRightAssociative(c)))) {
                    result.append(stack.pop()).append(' ');
                }
                stack.push(c);
            } else {
                return "Invalid Expression"; // unknown character
            }
            i++;
        }

        while (!stack.isEmpty()) {
            if (stack.peek() == '(') return "Invalid Expression";
            result.append(stack.pop()).append(' ');
        }

        return result.toString().trim();
    }

    /** Evaluates a space-separated postfix expression, e.g. "12 3 + 2 ^". */
    public static int evaluatePostfix(String postfix) {
        Stack<Integer> stack = new Stack<>();

        for (String token : postfix.trim().split("\\s+")) {
            if (token.isEmpty()) continue;

            if (Character.isDigit(token.charAt(0))) {
                stack.push(Integer.parseInt(token));
            } else {
                if (token.length() != 1 || stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid postfix expression: " + postfix);
                }
                int val1 = stack.pop();
                int val2 = stack.pop();

                switch (token.charAt(0)) {
                    case '+': stack.push(val2 + val1); break;
                    case '-': stack.push(val2 - val1); break;
                    case '/': stack.push(val2 / val1); break;
                    case '*': stack.push(val2 * val1); break;
                    case '^': stack.push(intPow(val2, val1)); break;
                    default: throw new IllegalArgumentException("Unknown operator: " + token);
                }
            }
        }
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid postfix expression: " + postfix);
        }
        return stack.pop();
    }

    // Integer power by repeated squaring (exponent must be >= 0)
    private static int intPow(int base, int exp) {
        if (exp < 0) throw new ArithmeticException("Negative exponent: " + exp);
        int result = 1;
        while (exp > 0) {
            if ((exp & 1) == 1) result *= base;
            base *= base;
            exp >>= 1;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("--- SHUNTING YARD (FROM SCRATCH) DEMO ---");
        String infix = "3+4*2/(1-5)^2"; 
        System.out.println("Infix: " + infix);
        
        String postfix = infixToPostfix(infix);
        System.out.println("Postfix: " + postfix);
        
        String simpleInfix = "3+4*2";
        String simplePostfix = infixToPostfix(simpleInfix);
        System.out.println("\nSimple Infix: " + simpleInfix);
        System.out.println("Simple Postfix: " + simplePostfix);
        System.out.println("Evaluation Result: " + evaluatePostfix(simplePostfix)); // Expected: 11

        String multiDigit = "12 + 30 * (4 - 2)";
        String mdPostfix = infixToPostfix(multiDigit);
        System.out.println("\nMulti-digit: " + multiDigit + " -> " + mdPostfix + " = " + evaluatePostfix(mdPostfix)); // 72

        String power = "2^3^2";
        String powPostfix = infixToPostfix(power);
        System.out.println("Right-assoc: " + power + " -> " + powPostfix + " = " + evaluatePostfix(powPostfix)); // 512
    }
}
