package algorithms.strings.similarity;

/**
 * METAPHONE ALGORITHM
 * 
 * WHAT IT IS:
 * An improved phonetic algorithm over Soundex. It uses a much larger set of rules 
 * to handle inconsistencies in English spelling and pronunciation more accurately.
 * 
 * *NOTE:* A full production Metaphone implementation (or Double Metaphone) has 
 * hundreds of lines of complex prefix/suffix rules. This is a condensed conceptual 
 * demonstration showing the core mapping logic.
 */
public class Metaphone {

    public static String getCode(String s) {
        if (s == null || s.length() == 0) return "";
        s = s.toUpperCase().replaceAll("[^A-Z]", "");
        
        // Handle common initial combinations
        if (s.startsWith("KN") || s.startsWith("GN") || s.startsWith("PN") || s.startsWith("WR") || s.startsWith("AE")) {
            s = s.substring(1);
        }
        
        StringBuilder out = new StringBuilder();
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            // Basic mapping rules (Conceptual subset)
            if (c == 'B') {
                if (i == s.length() - 1 && i > 0 && s.charAt(i - 1) == 'M') continue; // Dumb -> DUM
                out.append('B');
            } else if (c == 'C') {
                if (i < s.length() - 1 && (s.charAt(i + 1) == 'I' || s.charAt(i + 1) == 'E' || s.charAt(i + 1) == 'Y')) {
                    out.append('S'); // City -> SITY
                } else {
                    out.append('K'); // Cat -> KAT
                }
            } else if (c == 'F' || c == 'J' || c == 'L' || c == 'M' || c == 'N' || c == 'R') {
                out.append(c);
            } // ... many more rules would be here for a full implementation
        }
        
        return out.toString();
    }
}
