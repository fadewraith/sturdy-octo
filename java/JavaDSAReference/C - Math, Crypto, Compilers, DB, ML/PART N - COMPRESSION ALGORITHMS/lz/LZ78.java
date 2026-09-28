package compression.lz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WHAT IT IS: A dictionary-based compression algorithm where the dictionary is built dynamically.
 * STRATEGY: Read characters. If the current sequence is in dictionary, keep reading. If not, output (dictionary index of prefix, last char) and add new sequence to dictionary.
 * TIME/SPACE COMPLEXITY: Time O(N), Space O(N) where N is length of data (space for dictionary).
 * REAL-WORLD ANALOGY / USE CASE: Building a glossary as you read a book. Used as a basis for LZW.
 * WHEN TO USE / COMBINATION: When sliding window overhead is undesirable.
 * 
 * PSEUDOCODE:
 * dict = empty
 * w = empty
 * for char in text:
 *   if w + char in dict: w = w + char
 *   else:
 *     output (index(w), char)
 *     dict.add(w + char)
 *     w = empty
 */
public class LZ78 {
    public static class Token {
        int index;
        char nextChar;
        Token(int index, char nextChar) { this.index = index; this.nextChar = nextChar; }
        @Override
        public String toString() { return String.format("<%d,%c>", index, nextChar); }
    }

    public static List<Token> encode(String text) {
        List<Token> tokens = new ArrayList<>();
        Map<String, Integer> dict = new HashMap<>();
        int dictIndex = 1;
        String w = "";

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (dict.containsKey(w + c)) {
                w = w + c;
            } else {
                tokens.add(new Token(w.isEmpty() ? 0 : dict.get(w), c));
                dict.put(w + c, dictIndex++);
                w = "";
            }
        }
        if (!w.isEmpty()) {
            tokens.add(new Token(dict.get(w), '\0'));
        }
        return tokens;
    }

    public static String decode(List<Token> tokens) {
        Map<Integer, String> dict = new HashMap<>();
        int dictIndex = 1;
        StringBuilder sb = new StringBuilder();

        for (Token t : tokens) {
            String w = t.index == 0 ? "" : dict.get(t.index);
            sb.append(w);
            if (t.nextChar != '\0') {
                sb.append(t.nextChar);
                dict.put(dictIndex++, w + t.nextChar);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String text = "abracadabra";
        List<Token> encoded = encode(text);
        System.out.println("Original: " + text);
        System.out.println("Encoded: " + encoded);
        System.out.println("Decoded: " + decode(encoded));

        String test2 = "ABABABA";
        List<Token> enc2 = encode(test2);
        System.out.println("Original: " + test2);
        System.out.println("Encoded: " + enc2);
        System.out.println("Decoded: " + decode(enc2));
    }
}
