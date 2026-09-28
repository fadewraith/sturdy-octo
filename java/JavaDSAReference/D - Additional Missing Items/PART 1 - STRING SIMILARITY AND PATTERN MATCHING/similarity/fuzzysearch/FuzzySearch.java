package algorithms.strings.similarity.fuzzysearch;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import algorithms.strings.similarity.JaroWinkler;

/**
 * FUZZY SEARCH ENGINE
 * 
 * WHAT IT IS:
 * // Fuzzy search is not a single algorithm — it's the general problem of finding 
 * // approximate matches (not exact matches) between a query and a dataset. 
 * // It's typically implemented using ONE or A COMBINATION of the algorithms above: 
 * // Levenshtein (typo tolerance), N-gram (substring tolerance), Soundex (phonetic), 
 * // or Jaro-Winkler (name tolerance). 
 * // A real fuzzy search engine typically combines edit-distance thresholding with 
 * // n-gram indexing OR a BK-Tree for speed, since running edit distance against 
 * // every record in a large dataset is too slow.
 * 
 * DEMO APPROACH:
 * - We have a massive list of database candidates.
 * - We want to rank the top matches.
 * - We use Jaro-Winkler (or Levenshtein) to score, and PriorityQueue to rank.
 */
public class FuzzySearch {

    static class Match implements Comparable<Match> {
        String text;
        double score;

        Match(String text, double score) {
            this.text = text;
            this.score = score;
        }

        @Override
        public int compareTo(Match other) {
            // Sort DESCENDING by score
            return Double.compare(other.score, this.score);
        }
        
        @Override
        public String toString() {
            return String.format("%s (%.2f)", text, score);
        }
    }

    public static List<Match> search(String query, List<String> database, int topK) {
        PriorityQueue<Match> pq = new PriorityQueue<>();

        for (String record : database) {
            // Scoring mechanism (using Jaro-Winkler for short-string fuzzy matching)
            double score = JaroWinkler.jaroWinklerDistance(query.toLowerCase(), record.toLowerCase());
            
            if (score > 0.6) { // Minimum threshold to even consider
                pq.add(new Match(record, score));
            }
        }

        List<Match> results = new ArrayList<>();
        while (!pq.isEmpty() && results.size() < topK) {
            results.add(pq.poll());
        }

        return results;
    }

    public static void main(String[] args) {
        System.out.println("--- FUZZY SEARCH ENGINE DEMO ---");
        
        List<String> database = new ArrayList<>();
        database.add("Johnathon");
        database.add("Jonathan");
        database.add("John");
        database.add("Jhonatan");
        database.add("Janice");
        database.add("Jim");
        
        String query = "Jonathon";
        
        System.out.println("Database: " + database);
        System.out.println("Query: '" + query + "'");
        
        List<Match> results = search(query, database, 3);
        System.out.println("\nTop 3 Matches:");
        for (Match m : results) {
            System.out.println("  " + m);
        }
    }
}
