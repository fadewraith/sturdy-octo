package compilers.lexer;

import java.util.ArrayList;
import java.util.List;

/**
 * WHAT IT IS: A basic lexical analyzer (tokenizer) that converts a sequence of characters (source code) into a sequence of tokens (meaningful chunks like identifiers, keywords, operators, numbers).
 * STRATEGY: Use a simple state machine to identify patterns in the input string. Consume characters matching a specific pattern (e.g., while character is digit, accumulate into number), emit a token of that type, and repeat until the input is exhausted, skipping whitespace.
 * TIME/SPACE COMPLEXITY: Time: O(N) where N is the length of the input string. Space: O(T) where T is the number of tokens produced.
 * REAL-WORLD ANALOGY / USE CASE: Reading words in a book. You don't read individual letters; your brain automatically groups letters separated by spaces or punctuation into distinct words with specific meanings. Used as the first stage of any compiler or interpreter.
 * WHEN TO USE / COMBINATION: Always the first step in parsing text or source code. Followed by a Parser (like Recursive Descent or a parser generator).
 * 
 * PSEUDOCODE:
 * while input is not empty:
 *   skip leading whitespace
 *   if char is letter:
 *     read until non-letter/digit -> ID_TOKEN
 *   else if char is digit:
 *     read until non-digit -> NUM_TOKEN
 *   else if char is operator symbol:
 *     read operator -> OP_TOKEN
 *   else:
 *     throw Error
 * return list of tokens
 */
public class BasicTokenizer {

    public enum TokenType {
        NUMBER, IDENTIFIER, OPERATOR, PAREN_LEFT, PAREN_RIGHT, EOF
    }

    public static class Token {
        public final TokenType type;
        public final String value;

        public Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.format("Token(%s, '%s')", type.name(), value);
        }
    }

    public static List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        int i = 0;
        int n = input.length();

        while (i < n) {
            char c = input.charAt(i);

            // Skip whitespace
            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            // Identifiers (start with letter, followed by letters/digits)
            if (Character.isLetter(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < n && Character.isLetterOrDigit(input.charAt(i))) {
                    sb.append(input.charAt(i));
                    i++;
                }
                tokens.add(new Token(TokenType.IDENTIFIER, sb.toString()));
                continue;
            }

            // Numbers
            if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < n && Character.isDigit(input.charAt(i))) {
                    sb.append(input.charAt(i));
                    i++;
                }
                tokens.add(new Token(TokenType.NUMBER, sb.toString()));
                continue;
            }

            // Operators and Parentheses
            if ("+-*/^=".indexOf(c) != -1) {
                tokens.add(new Token(TokenType.OPERATOR, String.valueOf(c)));
                i++;
            } else if (c == '(') {
                tokens.add(new Token(TokenType.PAREN_LEFT, "("));
                i++;
            } else if (c == ')') {
                tokens.add(new Token(TokenType.PAREN_RIGHT, ")"));
                i++;
            } else {
                throw new IllegalArgumentException("Unrecognized character at index " + i + ": " + c);
            }
        }
        
        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    public static void main(String[] args) {
        System.out.println("--- Basic Tokenizer ---");
        
        String[] testCases = {
            "x = 42 + y * (3 - 1)",
            "let var123 = 5^2",
            "valid_identifer = 10", // Note: our tokenizer only supports alphanumeric identifiers currently
            "100 / 0 + foo",
            "invalid $ char"
        };

        for (String test : testCases) {
            System.out.println("\nSource: " + test);
            try {
                List<Token> tokens = tokenize(test);
                for (Token t : tokens) {
                    System.out.println("  " + t);
                }
            } catch (Exception e) {
                System.out.println("  ERROR: " + e.getMessage());
            }
        }
    }
}
