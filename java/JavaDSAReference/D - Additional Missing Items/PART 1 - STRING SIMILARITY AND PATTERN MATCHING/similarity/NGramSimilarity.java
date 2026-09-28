package algorithms.strings.similarity;

import java.util.HashSet;
import java.util.Set;

/**
 * N-GRAM SIMILARITY
 * 
 * WHAT IT IS:
 * Breaks strings into overlapping N-character chunks (n-grams), then compares 
 * the set overlap using Jaccard or Sørensen-Dice.
 * 
 * APPLIES TO:
 * - Autocomplete, "Did you mean", fast fuzzy search filtering.
 * - Much more typo-resistant than checking whole words.
 * 
 * Example (Bigrams for "apple"): "ap", "pp", "pl", "le"
 * 
 * COMPLEXITY:
 * Time: O(N) to build n-grams.
 */
public class NGramSimilarity {

    public static Set<String> getNGrams(String str, int n) {
        Set<String> nGrams = new HashSet<>();
        if (str == null || str.length() < n) return nGrams;
        
        for (int i = 0; i <= str.length() - n; i++) {
            nGrams.add(str.substring(i, i + n));
        }
        return nGrams;
    }

    public static double compare(String s1, String s2, int n) {
        Set<String> set1 = getNGrams(s1, n);
        Set<String> set2 = getNGrams(s2, n);
        
        return JaccardAndDice.jaccard(set1, set2); // Reuse our Jaccard logic
    }

    public static void main(String[] args) {
        System.out.println("--- N-GRAM SIMILARITY DEMO ---");
        String s1 = "night";
        String s2 = "nacht";
        
        System.out.println("String 1: " + s1);
        System.out.println("String 2: " + s2);
        
        System.out.println("Bigrams for S1: " + getNGrams(s1, 2));
        System.out.println("Bigrams for S2: " + getNGrams(s2, 2));
        
        System.out.printf("2-gram Jaccard Similarity: %.4f\n", compare(s1, s2, 2));
    }
}
