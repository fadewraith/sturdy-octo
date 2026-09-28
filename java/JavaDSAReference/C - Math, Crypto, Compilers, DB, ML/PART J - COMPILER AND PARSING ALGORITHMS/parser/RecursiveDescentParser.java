package compilers.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * WHAT IT IS: A top-down parser built from a set of mutually recursive procedures, where each procedure usually implements one of the non-terminals of the grammar. It constructs an abstract syntax tree (AST) which can then be evaluated. Connects directly to Tree Data Structures.
 * STRATEGY: Define grammar rules (e.g., Expr -> Term (+|- Term)*, Term -> Factor (*|/ Factor)*, Factor -> Number | (Expr)). Write a function for each rule. Each function consumes tokens that match its rule and calls other rule functions.
 * TIME/SPACE COMPLEXITY: Time: O(N) where N is the number of tokens, assuming no backtracking. Space: O(D) where D is the maximum depth of the parse tree.
 * REAL-WORLD ANALOGY / USE CASE: Following a recipe with sub-recipes. "To make a cake (Expr), first make the batter (Term), then bake it. To make batter (Term), mix flour and eggs (Factor)." Used in hand-written parsers for modern languages.
 * WHEN TO USE / COMBINATION: Use when you need to parse a custom language or expression and want a straightforward, easily debuggable, hand-written parser. Combines with a Tokenizer to get input, and AST evaluation or code generation to process the result.
 * 
 * PSEUDOCODE:
 * function parseExpr():
 *   left = parseTerm()
 *   while peek() is '+' or '-':
 *     op = consume()
 *     right = parseTerm()
 *     left = new BinaryNode(op, left, right)
 *   return left
 * 
 * function parseTerm():
 *   left = parseFactor()
 *   while peek() is '*' or '/':
 *     op = consume()
 *     right = parseFactor()
 *     left = new BinaryNode(op, left, right)
 *   return left
 * 
 * function parseFactor():
 *   if peek() is Number:
 *     return new NumberNode(consume())
 *   if peek() is '(':
 *     consume('(')
 *     expr = parseExpr()
 *     consume(')')
 *     return expr
 *   throw Error
 */
public class RecursiveDescentParser {

    // Simple Token definition for the parser
    static class Token {
        enum Type { NUMBER, PLUS, MINUS, MUL, DIV, LPAREN, RPAREN, EOF }
        Type type;
        String value;
        Token(Type type, String value) { this.type = type; this.value = value; }
    }

    // AST Nodes
    static abstract class ASTNode {
        abstract double evaluate();
        // Illustrating tree structure connection by allowing tree stringification
        abstract String toTreeString(String indent);
    }

    static class NumberNode extends ASTNode {
        double value;
        NumberNode(double value) { this.value = value; }
        
        @Override double evaluate() { return value; }
        @Override String toTreeString(String indent) { return indent + "Number: " + value + "\n"; }
    }

    static class BinaryOpNode extends ASTNode {
        Token.Type op;
        ASTNode left, right;
        BinaryOpNode(Token.Type op, ASTNode left, ASTNode right) {
            this.op = op; this.left = left; this.right = right;
        }

        @Override
        double evaluate() {
            double lVal = left.evaluate();
            double rVal = right.evaluate();
            switch (op) {
                case PLUS: return lVal + rVal;
                case MINUS: return lVal - rVal;
                case MUL: return lVal * rVal;
                case DIV: 
                    if(rVal == 0) throw new ArithmeticException("Division by zero");
                    return lVal / rVal;
                default: throw new UnsupportedOperationException();
            }
        }
        
        @Override 
        String toTreeString(String indent) {
            String res = indent + "BinaryOp: " + op + "\n";
            res += left.toTreeString(indent + "  ");
            res += right.toTreeString(indent + "  ");
            return res;
        }
    }

    private List<Token> tokens;
    private int current;

    public RecursiveDescentParser(List<Token> tokens) {
        this.tokens = tokens;
        this.current = 0;
    }

    private Token peek() {
        if (current >= tokens.size()) return tokens.get(tokens.size()-1); // return EOF
        return tokens.get(current);
    }

    private Token advance() {
        if (current < tokens.size()) current++;
        return tokens.get(current - 1);
    }

    private boolean match(Token.Type type) {
        if (peek().type == type) {
            advance();
            return true;
        }
        return false;
    }
    
    private Token consume(Token.Type type, String message) {
        if (peek().type == type) return advance();
        throw new RuntimeException(message + " found " + peek().type);
    }

    // Expr -> Term ( (PLUS | MINUS) Term )*
    public ASTNode parseExpr() {
        ASTNode node = parseTerm();

        while (peek().type == Token.Type.PLUS || peek().type == Token.Type.MINUS) {
            Token op = advance();
            ASTNode right = parseTerm();
            node = new BinaryOpNode(op.type, node, right);
        }

        return node;
    }

    // Term -> Factor ( (MUL | DIV) Factor )*
    private ASTNode parseTerm() {
        ASTNode node = parseFactor();

        while (peek().type == Token.Type.MUL || peek().type == Token.Type.DIV) {
            Token op = advance();
            ASTNode right = parseFactor();
            node = new BinaryOpNode(op.type, node, right);
        }

        return node;
    }

    // Factor -> NUMBER | LPAREN Expr RPAREN
    private ASTNode parseFactor() {
        if (match(Token.Type.NUMBER)) {
            return new NumberNode(Double.parseDouble(tokens.get(current - 1).value));
        }

        if (match(Token.Type.LPAREN)) {
            ASTNode node = parseExpr();
            consume(Token.Type.RPAREN, "Expect ')' after expression.");
            return node;
        }

        throw new RuntimeException("Expect expression, found: " + peek().type);
    }

    // Quick and dirty tokenizer for testing
    private static List<Token> lex(String source) {
        List<Token> tokens = new ArrayList<>();
        int i = 0;
        while(i < source.length()) {
            char c = source.charAt(i);
            if (Character.isWhitespace(c)) { i++; }
            else if (c == '+') { tokens.add(new Token(Token.Type.PLUS, "+")); i++; }
            else if (c == '-') { tokens.add(new Token(Token.Type.MINUS, "-")); i++; }
            else if (c == '*') { tokens.add(new Token(Token.Type.MUL, "*")); i++; }
            else if (c == '/') { tokens.add(new Token(Token.Type.DIV, "/")); i++; }
            else if (c == '(') { tokens.add(new Token(Token.Type.LPAREN, "(")); i++; }
            else if (c == ')') { tokens.add(new Token(Token.Type.RPAREN, ")")); i++; }
            else if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while(i < source.length() && Character.isDigit(source.charAt(i))) {
                    sb.append(source.charAt(i));
                    i++;
                }
                tokens.add(new Token(Token.Type.NUMBER, sb.toString()));
            } else {
                throw new RuntimeException("Unexpected character: " + c);
            }
        }
        tokens.add(new Token(Token.Type.EOF, ""));
        return tokens;
    }

    public static void main(String[] args) {
        System.out.println("--- Recursive Descent Parser ---");
        String[] testCases = {
            "3 + 5 * 2",
            "(3 + 5) * 2",
            "10 - 2 * 3 + 4",
            "8 / 2 / 2",
            "2 * (4 + 6 * (1 + 1))",
            "(3 + 4" // Error
        };

        for (String test : testCases) {
            System.out.println("\nExpression: " + test);
            try {
                List<Token> tokens = lex(test);
                RecursiveDescentParser parser = new RecursiveDescentParser(tokens);
                ASTNode ast = parser.parseExpr();
                
                System.out.println("AST:");
                System.out.print(ast.toTreeString("  "));
                System.out.println("Result: " + ast.evaluate());
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }
}
