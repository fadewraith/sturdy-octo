package algorithms.strings.similarity;

import java.util.HashSet;
import java.util.Set;

/**
 * JACCARD SIMILARITY & SØRENSEN-DICE COEFFICIENT
 * 
 * WHAT THEY ARE:
 * Statistical metrics to compare the similarity and diversity of sample sets.
 * Often used in NLP to compare the set of words (or N-Grams) in two documents.
 * 
 * JACCARD:
 * Formula: |Intersection| / |Union|
 * Range: 0 to 1
 * 
 * SØRENSEN-DICE:
 * Formula: 2 * |Intersection| / (|A| + |B|)
 * Range: 0 to 1
 * Difference: Dice weights the intersection twice as heavily, often yielding a 
 * higher apparent similarity score than Jaccard.
 * 
 * COMPLEXITY:
 * Time: O(A + B) where A and B are the sizes of the sets.
 * Space: O(A + B)
 */
public class JaccardAndDice {

    public static double jaccard(Set<String> setA, Set<String> setB) {
        if (setA.isEmpty() && setB.isEmpty()) return 1.0;
        
        Set<String> intersection = new HashSet<>(setA);
        intersection.retainAll(setB); // Keep only elements present in both
        
        Set<String> union = new HashSet<>(setA);
        union.addAll(setB);
        
        return (double) intersection.size() / union.size();
    }

    public static double dice(Set<String> setA, Set<String> setB) {
        if (setA.isEmpty() && setB.isEmpty()) return 1.0;
        
        Set<String> intersection = new HashSet<>(setA);
        intersection.retainAll(setB);
        
        return (2.0 * intersection.size()) / (setA.size() + setB.size());
    }

    public static void main(String[] args) {
        System.out.println("--- JACCARD vs SØRENSEN-DICE DEMO ---");
        
        Set<String> setA = new HashSet<>(java.util.Arrays.asList("apple", "banana", "cherry"));
        Set<String> setB = new HashSet<>(java.util.Arrays.asList("banana", "cherry", "date", "fig"));
        
        System.out.println("Set A: " + setA);
        System.out.println("Set B: " + setB);
        
        System.out.printf("Jaccard Similarity: %.4f\n", jaccard(setA, setB)); 
        // Expected: 2 (intersection) / 5 (union) = 0.4000
        
        System.out.printf("Sørensen-Dice Coefficient: %.4f\n", dice(setA, setB)); 
        // Expected: 2*2 / (3 + 4) = 4 / 7 = 0.5714
    }
}
