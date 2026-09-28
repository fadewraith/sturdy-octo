package algorithms.randomization;

import java.util.Arrays;
import java.util.Random;

/**
 * RESERVOIR SAMPLING
 * 
 * WHAT IT IS:
 * A family of randomized algorithms for choosing a simple random sample, without 
 * replacement, of `k` items from a population of unknown size `N` in a single pass 
 * over the items.
 * 
 * WHEN TO USE THIS:
 * - Sampling a random subset from an infinite stream of data (e.g., picking 100 
 *   random tweets from the live Twitter firehose).
 * - Since the stream is infinite, you CANNOT store it all in an array and pick 
 *   random indices.
 * 
 * STRATEGY:
 * 1. Create a "reservoir" array of size `k`.
 * 2. Put the first `k` items of the stream directly into the reservoir.
 * 3. For the `i-th` item (where i > k), pick a random number `j` from 0 to `i`.
 * 4. If `j < k`, replace the item at `reservoir[j]` with the new `i-th` item!
 * 
 * PROOF OF FAIRNESS:
 * Every single item from the stream has an exact `k/N` probability of remaining 
 * in the reservoir by the end of the stream. It perfectly balances out!
 * 
 * COMPLEXITY:
 * Time: O(N)
 * Space: O(k) for the reservoir.
 */
public class ReservoirSampling {

    public static int[] selectKItems(int[] stream, int n, int k) {
        int[] reservoir = new int[k];
        int i;

        // 1. Fill the reservoir with the first k elements
        for (i = 0; i < k; i++) {
            reservoir[i] = stream[i];
        }

        Random rand = new Random();

        // 2. Process from the (k+1)-th element to the end of the stream
        for (; i < n; i++) {
            // Pick a random index from 0 to i (inclusive!)
            int j = rand.nextInt(i + 1);

            // If the randomly picked index is smaller than k, replace the element 
            // at that index in the reservoir with the new stream element!
            if (j < k) {
                reservoir[j] = stream[i];
            }
        }
        return reservoir;
    }

    public static void main(String[] args) {
        System.out.println("--- RESERVOIR SAMPLING DEMO ---");
        
        // Simulating a stream of unknown size
        int[] stream = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        int n = stream.length;
        int k = 3; // We want to sample exactly 3 items
        
        System.out.println("Stream: " + Arrays.toString(stream));
        System.out.println("Sample of " + k + " items: " + Arrays.toString(selectKItems(stream, n, k)));
    }
}
