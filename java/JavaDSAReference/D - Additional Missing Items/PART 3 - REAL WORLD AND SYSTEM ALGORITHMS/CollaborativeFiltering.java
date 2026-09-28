package algorithms.realworld;

import java.util.HashMap;
import java.util.Map;

/**
 * COLLABORATIVE FILTERING (User-Based Recommendation)
 * 
 * WHAT IT IS:
 * A method of making automatic predictions (filtering) about the interests of a user 
 * by collecting preferences from many users (collaborating).
 * "People who liked X also liked Y."
 * 
 * COMBINATION:
 * Builds heavily on Cosine Similarity applied to rating vectors.
 * 
 * STRATEGY:
 * 1. Represent each user as a vector of their item ratings.
 * 2. To recommend items for User A:
 *    a) Calculate the Cosine Similarity between User A and all other users.
 *    b) Find the most similar user (the "Nearest Neighbor").
 *    c) Recommend items that the similar user rated highly, but User A hasn't seen yet!
 * 
 * COMPLEXITY:
 * Time: O(U * I) where U is number of users and I is number of items.
 * Space: O(U * I) to store the ratings matrix.
 */
public class CollaborativeFiltering {

    // Calculates cosine similarity between two rating maps
    private static double cosineSimilarity(Map<String, Double> user1, Map<String, Double> user2) {
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (String item : user1.keySet()) {
            double rating1 = user1.get(item);
            norm1 += rating1 * rating1;
            
            if (user2.containsKey(item)) {
                dotProduct += rating1 * user2.get(item);
            }
        }

        for (double rating2 : user2.values()) {
            norm2 += rating2 * rating2;
        }

        if (norm1 == 0 || norm2 == 0) return 0;
        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    public static void recommend(String targetUser, Map<String, Map<String, Double>> database) {
        Map<String, Double> targetRatings = database.get(targetUser);
        
        String bestMatchUser = null;
        double highestSimilarity = -1;

        // 1. Find the most similar user
        for (String otherUser : database.keySet()) {
            if (otherUser.equals(targetUser)) continue;
            
            double sim = cosineSimilarity(targetRatings, database.get(otherUser));
            System.out.printf("Similarity with %s: %.2f\n", otherUser, sim);
            
            if (sim > highestSimilarity) {
                highestSimilarity = sim;
                bestMatchUser = otherUser;
            }
        }

        // 2. Recommend items they liked that we haven't seen
        System.out.println("\nMost similar user is: " + bestMatchUser);
        System.out.println("Recommendations for " + targetUser + ":");
        
        Map<String, Double> bestMatchRatings = database.get(bestMatchUser);
        boolean foundRecommendation = false;
        
        for (Map.Entry<String, Double> entry : bestMatchRatings.entrySet()) {
            String item = entry.getKey();
            double rating = entry.getValue();
            
            if (!targetRatings.containsKey(item) && rating >= 4.0) { // Only recommend highly rated items
                System.out.println("- " + item + " (They rated it " + rating + ")");
                foundRecommendation = true;
            }
        }
        
        if (!foundRecommendation) {
            System.out.println("No new recommendations found.");
        }
    }

    public static void main(String[] args) {
        System.out.println("--- COLLABORATIVE FILTERING DEMO ---");
        
        Map<String, Map<String, Double>> db = new HashMap<>();
        
        // Alice likes Sci-Fi and Action
        Map<String, Double> alice = new HashMap<>();
        alice.put("The Matrix", 5.0);
        alice.put("Inception", 4.5);
        alice.put("Star Wars", 5.0);
        db.put("Alice", alice);
        
        // Bob has very similar tastes to Alice!
        Map<String, Double> bob = new HashMap<>();
        bob.put("The Matrix", 4.5);
        bob.put("Inception", 5.0);
        bob.put("Interstellar", 4.5); // Alice hasn't seen this!
        db.put("Bob", bob);
        
        // Charlie likes Rom-Coms
        Map<String, Double> charlie = new HashMap<>();
        charlie.put("The Notebook", 5.0);
        charlie.put("Titanic", 4.0);
        charlie.put("The Matrix", 1.0); // Hated it
        db.put("Charlie", charlie);

        recommend("Alice", db);
    }
}
