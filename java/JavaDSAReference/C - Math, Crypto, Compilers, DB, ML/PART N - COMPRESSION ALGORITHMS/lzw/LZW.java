package compression.lzw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WHAT IT IS: Lempel-Ziv-Welch is a universal lossless data compression algorithm.
 * STRATEGY: Initialize dictionary with all single characters. Read input. If current + next is in dict, continue. Else, output code for current, add current + next to dict.
 * TIME/SPACE COMPLEXITY: Time O(N). Space O(Dictionary Size).
 * REAL-WORLD ANALOGY / USE CASE: Used in GIF images and compress command. Replaces repeated strings with single codes.
 * WHEN TO USE / COMBINATION: Good for compressing text and images where sequences of bytes appear repeatedly.
 * 
 * PSEUDOCODE:
 * dict = basic characters
 * p = ""
 * for c in input:
 *   pc = p + c
 *   if pc in dict: p = pc
 *   else:
 *     output dict[p]
 *     dict.add(pc)
 *     p = c
 * output dict[p]
 */
public class LZW {
    public static List<Integer> encode(String text) {
        int dictSize = 256;
        Map<String, Integer> dictionary = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dictionary.put("" + (char)i, i);
        }

        String w = "";
        List<Integer> result = new ArrayList<>();
        for (char c : text.toCharArray()) {
            String wc = w + c;
            if (dictionary.containsKey(wc)) {
                w = wc;
            } else {
                result.add(dictionary.get(w));
                dictionary.put(wc, dictSize++);
                w = "" + c;
            }
        }
        if (!w.isEmpty()) {
            result.add(dictionary.get(w));
        }
        return result;
    }

    public static String decode(List<Integer> encoded) {
        if (encoded == null || encoded.isEmpty()) return "";
        int dictSize = 256;
        Map<Integer, String> dictionary = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dictionary.put(i, "" + (char)i);
        }

        String w = "" + (char)(int)encoded.remove(0);
        StringBuilder result = new StringBuilder(w);
        for (int k : encoded) {
            String entry;
            if (dictionary.containsKey(k)) {
                entry = dictionary.get(k);
            } else if (k == dictSize) {
                entry = w + w.charAt(0);
            } else {
                throw new IllegalArgumentException("Bad compressed k: " + k);
            }

            result.append(entry);
            dictionary.put(dictSize++, w + entry.charAt(0));
            w = entry;
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String text = "TOBEORNOTTOBEORTOBEORNOT";
        List<Integer> encoded = encode(text);
        System.out.println("Original: " + text);
        System.out.println("Encoded: " + encoded);
        
        List<Integer> toDecode = new ArrayList<>(encoded);
        System.out.println("Decoded: " + decode(toDecode));

        String test2 = "ABABABA";
        List<Integer> enc2 = encode(test2);
        System.out.println("Original: " + test2);
        System.out.println("Encoded: " + enc2);
        List<Integer> toDecode2 = new ArrayList<>(enc2);
        System.out.println("Decoded: " + decode(toDecode2));
    }
}
