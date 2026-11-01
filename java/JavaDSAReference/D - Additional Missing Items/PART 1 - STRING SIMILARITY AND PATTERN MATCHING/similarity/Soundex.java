package algorithms.strings.similarity;

/**
 * SOUNDEX ALGORITHM
 * 
 * WHAT IT IS:
 * A phonetic algorithm for indexing names by sound, as pronounced in English.
 * It encodes homophones to the same representation.
 * 
 * APPLIES TO:
 * - Genealogy databases, old-school search systems, name matching.
 * 
 * RULES:
 * 1. Retain the first letter.
 * 2. Drop all occurrences of a, e, i, o, u, y, h, w.
 * 3. Replace consonants with digits:
 *    b, f, p, v -> 1
 *    c, g, j, k, q, s, x, z -> 2
 *    d, t -> 3
 *    l -> 4
 *    m, n -> 5
 *    r -> 6
 * 4. Replace adjacent same digits with one digit. Letters separated only by
 *    H or W count as adjacent; letters separated by a vowel do not.
 * 5. Pad with zeros to return a 4-character code.
 */
public class Soundex {

    public static String getCode(String s) {
        if (s == null || s.length() == 0) return "";
        s = s.toUpperCase();
        
        StringBuilder result = new StringBuilder();
        result.append(s.charAt(0));
        
        char prevCode = getMapping(s.charAt(0));
        
        for (int i = 1; i < s.length() && result.length() < 4; i++) {
            char c = s.charAt(i);
            // H and W are "transparent": letters with the same code on both sides of
            // them still merge (Ashcraft -> A261, not A226). Vowels DO separate them.
            // Non-letters (spaces, hyphens, apostrophes) are skipped too.
            if (c == 'H' || c == 'W' || !Character.isLetter(c)) continue;
            char code = getMapping(c);

            if (code != '0' && code != prevCode) {
                result.append(code);
            }
            prevCode = code;
        }
        
        while (result.length() < 4) {
            result.append('0');
        }
        
        return result.toString();
    }

    private static char getMapping(char c) {
        switch (c) {
            case 'B': case 'F': case 'P': case 'V': return '1';
            case 'C': case 'G': case 'J': case 'K': case 'Q': case 'S': case 'X': case 'Z': return '2';
            case 'D': case 'T': return '3';
            case 'L': return '4';
            case 'M': case 'N': return '5';
            case 'R': return '6';
            default: return '0'; // a,e,i,o,u,y,h,w
        }
    }

    public static void main(String[] args) {
        System.out.println("--- SOUNDEX DEMO ---");
        String name1 = "Rupert";
        String name2 = "Robert";
        
        System.out.println(name1 + " -> " + getCode(name1));
        System.out.println(name2 + " -> " + getCode(name2)); // Expected: both R163
        System.out.println("Ashcraft -> " + getCode("Ashcraft")); // Expected: A261 (H rule)
        System.out.println("Tymczak -> " + getCode("Tymczak"));   // Expected: T522
    }
}
