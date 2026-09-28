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

    public static String infixToPostfix(String infix) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        
        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                result.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop());
                }
                stack.pop(); 
            } else {
                while (!stack.isEmpty() && getPrecedence(c) <= getPrecedence(stack.peek())) {
                    result.append(stack.pop());
                }
                stack.push(c);
            }
        }
        
        while (!stack.isEmpty()) {
            if (stack.peek() == '(') return "Invalid Expression";
            result.append(stack.pop());
        }
        
        return result.toString();
    }

    public static int evaluatePostfix(String postfix) {
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < postfix.length(); i++) {
            char c = postfix.charAt(i);

            if (Character.isDigit(c)) {
                stack.push(c - '0');
            } else {
                int val1 = stack.pop();
                int val2 = stack.pop();

                switch (c) {
                    case '+': stack.push(val2 + val1); break;
                    case '-': stack.push(val2 - val1); break;
                    case '/': stack.push(val2 / val1); break;
                    case '*': stack.push(val2 * val1); break;
                }
            }
        }
        return stack.pop();
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
    }
}
