package algorithms.strings.similarity;

import java.util.HashMap;
import java.util.Map;

/**
 * COSINE SIMILARITY
 * 
 * WHAT IT IS:
 * Treats each string as a mathematical vector (a "bag of words" or character counts) 
 * and measures the cosine of the angle between the two vectors.
 * 
 * Formula: (A dot B) / (||A|| * ||B||)
 * 
 * APPLIES TO:
 * - Document similarity, search relevance ranking.
 * - Excellent for comparing long texts because it is independent of document length 
 *   (the angle is the same whether a document is 1 page or 100 pages, as long as the 
 *   word proportions match!).
 * - Not ideal for short strings/typos.
 * 
 * COMPLEXITY:
 * Time: O(N) to build vectors, O(V) to calculate dot product.
 */
public class CosineSimilarity {

    // Helper to convert string to term-frequency vector
    private static Map<String, Integer> getTermFrequencies(String text) {
        Map<String, Integer> freqs = new HashMap<>();
        String[] words = text.toLowerCase().replaceAll("[^a-z ]", "").split("\\s+");
        for (String word : words) {
            if (!word.isEmpty()) {
                freqs.put(word, freqs.getOrDefault(word, 0) + 1);
            }
        }
        return freqs;
    }

    public static double cosineSimilarity(String doc1, String doc2) {
        Map<String, Integer> vec1 = getTermFrequencies(doc1);
        Map<String, Integer> vec2 = getTermFrequencies(doc2);

        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (Map.Entry<String, Integer> entry : vec1.entrySet()) {
            String term = entry.getKey();
            int v1 = entry.getValue();
            norm1 += v1 * v1;
            
            if (vec2.containsKey(term)) {
                dotProduct += v1 * vec2.get(term);
            }
        }

        for (int v2 : vec2.values()) {
            norm2 += v2 * v2;
        }

        if (norm1 == 0.0 || norm2 == 0.0) return 0.0;
        
        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    public static void main(String[] args) {
        System.out.println("--- COSINE SIMILARITY DEMO ---");
        
        String doc1 = "The quick brown fox jumps over the lazy dog";
        String doc2 = "The quick brown fox"; // Shorter, but exact same angle!
        String doc3 = "Artificial intelligence machine learning";
        
        System.out.printf("Doc1 vs Doc2: %.4f\n", cosineSimilarity(doc1, doc2));
        System.out.printf("Doc1 vs Doc3: %.4f\n", cosineSimilarity(doc1, doc3));
    }
}
