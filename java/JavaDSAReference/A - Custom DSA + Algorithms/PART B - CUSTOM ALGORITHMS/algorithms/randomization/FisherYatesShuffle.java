package algorithms.randomization;

import java.util.Arrays;
import java.util.Random;

/**
 * FISHER-YATES SHUFFLE
 * 
 * WHAT IT IS:
 * An algorithm for generating a perfectly random permutation of a finite sequence 
 * (in plain English: it shuffles an array).
 * 
 * WHEN TO USE THIS:
 * - Shuffling a deck of cards.
 * - Randomizing playlist tracks.
 * - Crucial advantage over naive shuffling: It guarantees an unbiased permutation 
 *   (every possible arrangement is equally likely).
 * 
 * STRATEGY:
 * We iterate from the end of the array down to the first element.
 * For the element at index `i`, we pick a random index `j` between 0 and `i` (inclusive).
 * We swap the element at `i` with the element at `j`.
 * 
 * COMPLEXITY:
 * Time: O(N) single pass.
 * Space: O(1) in-place.
 */
public class FisherYatesShuffle {

    public static void shuffle(int[] arr) {
        Random rand = new Random();
        
        // Start from the last element and swap one by one
        for (int i = arr.length - 1; i > 0; i--) {
            // Pick a random index from 0 to i
            int j = rand.nextInt(i + 1);
            
            // Swap arr[i] with the element at random index
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- FISHER-YATES SHUFFLE DEMO ---");
        
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println("Original: " + Arrays.toString(arr));
        
        shuffle(arr);
        System.out.println("Shuffled: " + Arrays.toString(arr));
    }
}
