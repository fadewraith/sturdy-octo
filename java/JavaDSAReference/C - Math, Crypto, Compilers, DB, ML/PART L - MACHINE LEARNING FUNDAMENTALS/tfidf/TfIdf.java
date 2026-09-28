// These are simplified, from-scratch educational implementations to understand the underlying math. Real-world ML work uses libraries (scikit-learn, TensorFlow, etc.) — these exist here purely for conceptual completeness, not as production advice.
/**
 * WHAT IT IS: Term Frequency-Inverse Document Frequency weighting scheme for text representation.
 * STRATEGY: Evaluate how relevant a word is to a document in a collection. Relevance increases proportionally to times word appears in document but is offset by frequency of word in corpus.
 * TIME/SPACE COMPLEXITY: Time: O(N * L) where N=documents, L=avg document length. Space: O(V) where V=vocabulary size.
 * REAL-WORLD ANALOGY / USE CASE: Search engine scoring, keyword extraction.
 * WHEN TO USE / COMBINATION: TF-IDF vectors + Cosine Similarity = standard document-similarity pipeline.
 * 
 * PSEUDOCODE:
 * For each document:
 *   TF(word) = (count of word in doc) / (total words in doc)
 * For each word in corpus:
 *   IDF(word) = log_e(Total number of documents / Number of documents with word in it)
 * TF-IDF(word, doc) = TF(word) * IDF(word)
 */
package ml.tfidf;

import java.util.*;

public class TfIdf {

    public static void main(String[] args) {
        System.out.println("--- TF-IDF ---");
        List<List<String>> docs = Arrays.asList(
            Arrays.asList("this", "is", "a", "sample", "document"),
            Arrays.asList("this", "document", "is", "another", "document"),
            Arrays.asList("and", "this", "is", "a", "third", "one")
        );
        
        System.out.println("Documents:");
        for (int i = 0; i < docs.size(); i++) {
            System.out.println("Doc " + i + ": " + docs.get(i));
        }
        
        System.out.println("\nTF-IDF scores for 'document':");
        for (int i = 0; i < docs.size(); i++) {
            double score = tfIdf(docs.get(i), docs, "document");
            System.out.println("Doc " + i + ": " + score);
        }
        
        System.out.println("\nTF-IDF scores for 'sample':");
        for (int i = 0; i < docs.size(); i++) {
            double score = tfIdf(docs.get(i), docs, "sample");
            System.out.println("Doc " + i + ": " + score);
        }
    }

    public static double tf(List<String> document, String term) {
        if (document.isEmpty()) return 0;
        int count = 0;
        for (String word : document) {
            if (word.equalsIgnoreCase(term)) {
                count++;
            }
        }
        return (double) count / document.size();
    }
    
    public static double idf(List<List<String>> corpus, String term) {
        int numDocsWithTerm = 0;
        for (List<String> doc : corpus) {
            for (String word : doc) {
                if (word.equalsIgnoreCase(term)) {
                    numDocsWithTerm++;
                    break; // count doc only once
                }
            }
        }
        if (numDocsWithTerm == 0) return 0; // handle division by zero
        return Math.log((double) corpus.size() / numDocsWithTerm);
    }
    
    public static double tfIdf(List<String> document, List<List<String>> corpus, String term) {
        return tf(document, term) * idf(corpus, term);
    }
}
