package ml.naivebayes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * MULTINOMIAL NAIVE BAYES (text classification)
 *
 * WHAT IT IS:
 * A probabilistic classifier: pick the class c that maximises P(c | document).
 * By Bayes' rule P(c | doc) is proportional to P(c) * P(doc | c), and the "naive"
 * assumption treats words as independent given the class:
 *   P(doc | c) = product over words w of P(w | c)
 *
 * WHEN TO USE THIS:
 * - Spam filtering (the classic: Paul Graham's "A Plan for Spam"), sentiment,
 *   topic/language detection, a fast baseline before trying bigger models.
 * - Trains in one pass, works with very little data, handles huge vocabularies.
 *
 * STRATEGY:
 * Training (just counting):
 *   P(c)     = documents of class c / all documents
 *   P(w | c) = (count of w in class c + alpha) / (total words in c + alpha * |V|)
 *   alpha = 1 is LAPLACE SMOOTHING: a word never seen with class c would otherwise
 *   give probability 0 and wipe out the whole product.
 * Prediction: score(c) = log P(c) + sum over words of log P(w | c).
 *   LOGS turn the product into a sum and avoid floating-point UNDERFLOW (a product of
 *   500 small probabilities rounds to 0.0). Words outside the vocabulary are skipped.
 * Probabilities: softmax of the scores (subtract the max first for stability).
 *
 * COMPLEXITY:
 * Training: O(total words). Prediction: O(words in doc * classes).
 * Space: O(|V| * classes).
 */
public class NaiveBayes {

    private final double alpha;
    private final Map<String, Integer> docsPerClass = new LinkedHashMap<>();
    private final Map<String, Map<String, Integer>> wordCounts = new HashMap<>();
    private final Map<String, Integer> totalWords = new HashMap<>();
    private final Set<String> vocabulary = new HashSet<>();
    private int totalDocs = 0;

    public NaiveBayes(double alpha) { this.alpha = alpha; }

    public static String[] tokenize(String text) {
        String t = text.toLowerCase().replaceAll("[^a-z0-9' ]", " ").trim();
        return t.isEmpty() ? new String[0] : t.split("\\s+");
    }

    public void train(String text, String label) {
        totalDocs++;
        docsPerClass.merge(label, 1, Integer::sum);
        Map<String, Integer> counts = wordCounts.computeIfAbsent(label, k -> new HashMap<>());
        for (String w : tokenize(text)) {
            counts.merge(w, 1, Integer::sum);
            totalWords.merge(label, 1, Integer::sum);
            vocabulary.add(w);
        }
    }

    /** log P(c) + sum of log P(w | c) for every class. */
    public Map<String, Double> logScores(String text) {
        Map<String, Double> scores = new LinkedHashMap<>();
        String[] words = tokenize(text);
        for (String c : docsPerClass.keySet()) {
            double s = Math.log((double) docsPerClass.get(c) / totalDocs);
            Map<String, Integer> counts = wordCounts.get(c);
            double denom = totalWords.getOrDefault(c, 0) + alpha * vocabulary.size();
            for (String w : words) {
                if (!vocabulary.contains(w)) continue;          // unseen everywhere: no information
                s += Math.log((counts.getOrDefault(w, 0) + alpha) / denom);
            }
            scores.put(c, s);
        }
        return scores;
    }

    public String predict(String text) {
        String best = null;
        double bestScore = Double.NEGATIVE_INFINITY;
        for (Map.Entry<String, Double> e : logScores(text).entrySet()) {
            if (e.getValue() > bestScore) { bestScore = e.getValue(); best = e.getKey(); }
        }
        return best;
    }

    /** Posterior probabilities via a numerically stable softmax of the log scores. */
    public Map<String, Double> probabilities(String text) {
        Map<String, Double> scores = logScores(text);
        double max = scores.values().stream().mapToDouble(Double::doubleValue).max().orElse(0);
        double sum = 0;
        Map<String, Double> out = new LinkedHashMap<>();
        for (Map.Entry<String, Double> e : scores.entrySet()) { double v = Math.exp(e.getValue() - max); out.put(e.getKey(), v); sum += v; }
        for (Map.Entry<String, Double> e : out.entrySet()) e.setValue(e.getValue() / sum);
        return out;
    }

    public int vocabularySize() { return vocabulary.size(); }

    public static void main(String[] args) {
        System.out.println("--- NAIVE BAYES (SPAM FILTER) DEMO ---");
        NaiveBayes nb = new NaiveBayes(1.0);
        String[][] training = {
            {"Win a free iPhone now, click here", "spam"},
            {"Limited offer: free money, claim your prize", "spam"},
            {"Cheap meds, buy now, best price", "spam"},
            {"You are a winner! Claim your free gift", "spam"},
            {"Meeting moved to 3pm, see agenda attached", "ham"},
            {"Can you review my pull request today?", "ham"},
            {"Lunch tomorrow? The new place near the office", "ham"},
            {"Here are the notes from today's meeting", "ham"},
        };
        for (String[] t : training) nb.train(t[0], t[1]);

        String[] tests = {"Claim your free prize now", "Notes for the meeting tomorrow", "free lunch at the office"};
        for (String t : tests) {
            Map<String, Double> p = nb.probabilities(t);
            System.out.printf("%-34s -> %-4s  P(spam)=%.3f%n", "\"" + t + "\"", nb.predict(t), p.get("spam"));
        }
        // spam, ham, and the mixed one leans ham ("lunch", "office") despite "free"
    }
}
