package algorithms.strings.similarity;

/**
 * JARO & JARO-WINKLER DISTANCE
 * 
 * WHAT IT IS:
 * A similarity score between 0.0 (no similarity) and 1.0 (exact match).
 * Based on matching characters within a sliding proximity window, and penalizing transpositions.
 * Winkler's extension adds a bonus if the strings share a common prefix.
 * 
 * APPLIES TO:
 * - Short strings. Classically used for name matching in databases (e.g., "Martha" vs "Marhta").
 * - NOT meant for long documents!
 * 
 * COMPLEXITY:
 * Time: O(M * N)
 * Space: O(M + N) to track matched characters.
 */
public class JaroWinkler {

    public static double jaroDistance(String s1, String s2) {
        if (s1.equals(s2)) return 1.0;

        int len1 = s1.length(), len2 = s2.length();
        if (len1 == 0 || len2 == 0) return 0.0;

        // Max distance characters can be apart to be considered a match
        int matchDistance = (Math.max(len1, len2) / 2) - 1;

        boolean[] s1Matches = new boolean[len1];
        boolean[] s2Matches = new boolean[len2];
        int matches = 0;

        // 1. Find Matches
        for (int i = 0; i < len1; i++) {
            int start = Math.max(0, i - matchDistance);
            int end = Math.min(i + matchDistance + 1, len2);
            for (int j = start; j < end; j++) {
                if (!s2Matches[j] && s1.charAt(i) == s2.charAt(j)) {
                    s1Matches[i] = true;
                    s2Matches[j] = true;
                    matches++;
                    break;
                }
            }
        }

        if (matches == 0) return 0.0;

        // 2. Count Transpositions
        int t = 0;
        int point = 0;
        for (int i = 0; i < len1; i++) {
            if (s1Matches[i]) {
                while (!s2Matches[point]) point++;
                if (s1.charAt(i) != s2.charAt(point)) t++;
                point++;
            }
        }
        t /= 2;

        // 3. Jaro Formula
        return (((double) matches / len1) + ((double) matches / len2) + ((double) (matches - t) / matches)) / 3.0;
    }

    public static double jaroWinklerDistance(String s1, String s2) {
        double jaro = jaroDistance(s1, s2);
        
        // Winkler Extension: Boost score if they share a prefix (max 4 chars)
        int prefix = 0;
        for (int i = 0; i < Math.min(Math.min(s1.length(), s2.length()), 4); i++) {
            if (s1.charAt(i) == s2.charAt(i)) prefix++;
            else break;
        }

        double scalingFactor = 0.1;
        return jaro + (prefix * scalingFactor * (1.0 - jaro));
    }

    public static void main(String[] args) {
        System.out.println("--- JARO-WINKLER DEMO ---");
        String s1 = "MARTHA";
        String s2 = "MARHTA"; // Transposition of H and T
        
        System.out.printf("Jaro Score: %.4f\n", jaroDistance(s1, s2));
        System.out.printf("Jaro-Winkler Score: %.4f\n", jaroWinklerDistance(s1, s2));
        // Expected: Jaro is high, Jaro-Winkler is even higher due to the matching "MAR" prefix
    }
}
