package compilers.pratt;

import java.util.ArrayList;
import java.util.List;

/**
 * PRATT PARSER (Top-Down Operator Precedence)
 *
 * WHAT IT IS:
 * An expression parser where each operator has a BINDING POWER (precedence)
 * instead of one grammar rule per precedence level. Adding an operator = adding one
 * table entry. Handles prefix, infix, postfix and right-associative operators in
 * one small loop.
 *
 * WHEN TO USE THIS:
 * - Expression parsing in real compilers/tools: rustc (rust-analyzer), V8's
 *   precursor JSLint, Clang's operator-precedence parsing, many hand-written parsers.
 * - Compare: RecursiveDescentParser.java (one method per level - fine for 3 levels,
 *   tedious for 15), ShuntingYard.java (same power, but an explicit stack and
 *   harder to extend with prefix/postfix/ternary operators).
 *
 * OPERATORS HERE (higher binds tighter):
 *    + -   10  left-assoc        * / %  20  left-assoc
 *    - (prefix negation)  30     ^      40  RIGHT-assoc: 2^3^2 = 2^(3^2)
 *    ! (postfix factorial) 50    ( )   grouping
 * So  -2^2 = -(2^2) = -4  (as in mathematics and Python), and  3! ^ 2 = (3!)^2.
 *
 * THE CORE LOOP - parseExpression(minBp):
 *   left = parse a PREFIX thing (number, '(' expr ')', or '-' operand)
 *   loop:
 *     op = next token
 *     if op is POSTFIX with bp >= minBp: left = postfix(left); continue
 *     if op is INFIX with left-bp > minBp:  consume it, right = parseExpression(right-bp),
 *                                           left = (op left right); continue
 *     otherwise stop - op belongs to an outer call
 * Associativity is just the right-bp: left-assoc uses right-bp = bp (an equal
 * operator stops the inner call), right-assoc uses bp - 1 (the inner call takes it).
 *
 * OUTPUT: an S-expression string showing the tree, e.g. "(+ 1 (* 2 3))", and its value.
 *
 * COMPLEXITY:
 * O(N) for N tokens - each token is consumed once, recursion depth = nesting depth.
 */
public class PrattParser {

    private final List<String> tokens;
    private int pos = 0;

    private PrattParser(String input) {
        tokens = new ArrayList<>();
        int i = 0;
        while (i < input.length()) {
            char c = input.charAt(i);
            if (Character.isWhitespace(c)) { i++; continue; }
            if (Character.isDigit(c)) {
                int s = i;
                while (i < input.length() && Character.isDigit(input.charAt(i))) i++;
                tokens.add(input.substring(s, i));
            } else if ("+-*/%^!()".indexOf(c) >= 0) {
                tokens.add(String.valueOf(c));
                i++;
            } else {
                throw new IllegalArgumentException("unexpected character '" + c + "'");
            }
        }
    }

    private String peek() { return pos < tokens.size() ? tokens.get(pos) : null; }
    private String next() { return tokens.get(pos++); }

    // binding powers: {left bp, right bp}
    private static int[] infixBp(String op) {
        return switch (op) {
            case "+", "-" -> new int[]{10, 10};
            case "*", "/", "%" -> new int[]{20, 20};
            case "^" -> new int[]{40, 39};           // right-assoc: right bp one lower
            default -> null;
        };
    }

    private static final int PREFIX_MINUS_BP = 30;
    private static final int POSTFIX_BANG_BP = 50;

    /** Parses and returns the S-expression of the whole input. */
    public static String toSExpression(String input) {
        PrattParser p = new PrattParser(input);
        String tree = p.parseExpression(0);
        if (p.peek() != null) throw new IllegalArgumentException("unexpected '" + p.peek() + "'");
        return tree;
    }

    private String parseExpression(int minBp) {
        if (peek() == null) throw new IllegalArgumentException("unexpected end of input");
        String tok = next();
        String left;
        if (Character.isDigit(tok.charAt(0))) {
            left = tok;
        } else if (tok.equals("(")) {
            left = parseExpression(0);
            if (!")".equals(peek())) throw new IllegalArgumentException("missing ')'");
            next();
        } else if (tok.equals("-")) {
            left = "(neg " + parseExpression(PREFIX_MINUS_BP) + ")";
        } else {
            throw new IllegalArgumentException("unexpected '" + tok + "'");
        }

        while (true) {
            String op = peek();
            if (op == null || op.equals(")")) break;
            if (op.equals("!")) {
                if (POSTFIX_BANG_BP < minBp) break;
                next();
                left = "(! " + left + ")";
                continue;
            }
            int[] bp = infixBp(op);
            if (bp == null) throw new IllegalArgumentException("unexpected '" + op + "'");
            if (bp[0] <= minBp) break;              // weaker than our context: let the caller take it
            next();
            String right = parseExpression(bp[1]);
            left = "(" + op + " " + left + " " + right + ")";
        }
        return left;
    }

    // ---------------- evaluating the S-expression ----------------
    public static long evaluate(String input) {
        String sexp = toSExpression(input);
        int[] idx = {0};
        return evalSexp(sexp, idx);
    }

    private static long evalSexp(String s, int[] i) {
        if (s.charAt(i[0]) != '(') {                 // a number
            int start = i[0];
            while (i[0] < s.length() && Character.isDigit(s.charAt(i[0]))) i[0]++;
            return Long.parseLong(s.substring(start, i[0]));
        }
        i[0]++;                                      // '('
        int opEnd = s.indexOf(' ', i[0]);
        String op = s.substring(i[0], opEnd);
        i[0] = opEnd + 1;
        long a = evalSexp(s, i);
        long result;
        if (op.equals("neg")) result = -a;
        else if (op.equals("!")) {
            if (a < 0 || a > 20) throw new ArithmeticException("factorial only for 0..20");
            result = 1;
            for (int k = 2; k <= a; k++) result *= k;
        } else {
            i[0]++;                                  // space
            long b = evalSexp(s, i);
            result = switch (op) {
                case "+" -> a + b; case "-" -> a - b; case "*" -> a * b;
                case "/" -> { if (b == 0) throw new ArithmeticException("division by zero"); yield a / b; }
                case "%" -> { if (b == 0) throw new ArithmeticException("division by zero"); yield a % b; }
                case "^" -> { if (b < 0) throw new ArithmeticException("negative exponent"); long r = 1; for (long k = 0; k < b; k++) r *= a; yield r; }
                default -> throw new IllegalStateException(op);
            };
        }
        i[0]++;                                      // ')'
        return result;
    }

    public static void main(String[] args) {
        System.out.println("--- PRATT PARSER DEMO ---");
        String[] exprs = {"1 + 2 * 3", "(1 + 2) * 3", "10 - 4 - 3", "2 ^ 3 ^ 2", "-2 ^ 2", "3! ^ 2", "-3!", "7 % 4 * -2", "--5"};
        for (String e : exprs) {
            System.out.printf("%-12s -> %-22s = %d%n", e, toSExpression(e), evaluate(e));
        }
        // 1 + 2 * 3 -> (+ 1 (* 2 3)) = 7;  10-4-3 = 3 (left-assoc);  2^3^2 = 512 (right-assoc);
        // -2^2 = -4;  3!^2 = 36;  -3! = -6;  --5 = 5
    }
}
