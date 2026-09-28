package compression.lz;

import java.util.ArrayList;
import java.util.List;

/**
 * WHAT IT IS: A dictionary-based lossless compression algorithm using a sliding window.
 * STRATEGY: Sliding window is divided into a search buffer and a lookahead buffer. Finds longest match in search buffer. Output is (offset, length, next_char).
 * TIME/SPACE COMPLEXITY: Time O(N * S * L). Space O(N) for output tokens.
 * REAL-WORLD ANALOGY / USE CASE: Pointing back to a phrase you already said instead of saying it again. Used in DEFLATE (ZIP, PNG).
 * WHEN TO USE / COMBINATION: Good for general text or data with repeating substrings.
 * 
 * PSEUDOCODE:
 * while lookahead not empty:
 *   find longest match in search buffer
 *   output (distance_back, match_length, next_char)
 *   advance window by match_length + 1
 */
public class LZ77 {
    public static class Token {
        int offset;
        int length;
        char nextChar;
        Token(int offset, int length, char nextChar) {
            this.offset = offset; this.length = length; this.nextChar = nextChar;
        }
        @Override
        public String toString() { return String.format("<%d,%d,%c>", offset, length, nextChar); }
    }

    public static List<Token> encode(String text, int searchBufferSize, int lookaheadBufferSize) {
        List<Token> tokens = new ArrayList<>();
        int i = 0;
        while (i < text.length()) {
            int matchOffset = 0;
            int matchLength = 0;
            char nextChar = text.charAt(i);

            int searchStart = Math.max(0, i - searchBufferSize);
            for (int j = searchStart; j < i; j++) {
                int len = 0;
                while (len < lookaheadBufferSize && i + len < text.length() && text.charAt(j + len) == text.charAt(i + len)) {
                    len++;
                }
                if (len > matchLength) {
                    matchLength = len;
                    matchOffset = i - j;
                    if (i + len < text.length()) {
                        nextChar = text.charAt(i + len);
                    } else {
                        nextChar = '\0';
                    }
                }
            }

            tokens.add(new Token(matchOffset, matchLength, nextChar));
            i += matchLength + 1;
        }
        return tokens;
    }

    public static String decode(List<Token> tokens) {
        StringBuilder sb = new StringBuilder();
        for (Token t : tokens) {
            int start = sb.length() - t.offset;
            for (int i = 0; i < t.length; i++) {
                sb.append(sb.charAt(start + i));
            }
            if (t.nextChar != '\0') {
                sb.append(t.nextChar);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String text = "abracadabra";
        List<Token> encoded = encode(text, 5, 5);
        System.out.println("Original: " + text);
        System.out.println("Encoded: " + encoded);
        System.out.println("Decoded: " + decode(encoded));

        String test2 = "aacaacabcabaaac";
        List<Token> enc2 = encode(test2, 10, 5);
        System.out.println("Original: " + test2);
        System.out.println("Encoded: " + enc2);
        System.out.println("Decoded: " + decode(enc2));
    }
}
