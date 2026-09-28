package algorithms.realworld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * RESERVOIR SAMPLING (Using Java Built-ins)
 * 
 * WHAT IT IS:
 * Randomly sample `k` items from a stream of unknown length in a single pass.
 * 
 * (Note: A raw array version was built in Part B. This version uses Java Collections 
 * for demonstration in the 02D context).
 */
public class ReservoirSampling {

    public static List<Integer> sampleStream(Iterable<Integer> stream, int k) {
        List<Integer> reservoir = new ArrayList<>(k);
        Random rand = new Random();
        int count = 0;

        for (Integer item : stream) {
            count++;
            if (reservoir.size() < k) {
                reservoir.add(item);
            } else {
                int j = rand.nextInt(count);
                if (j < k) {
                    reservoir.set(j, item);
                }
            }
        }
        return reservoir;
    }

    public static void main(String[] args) {
        System.out.println("--- RESERVOIR SAMPLING DEMO ---");
        
        // Creating a massive mock stream
        List<Integer> stream = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            stream.add(i);
        }
        
        System.out.println("Stream size: 10,000");
        System.out.println("Sampling 5 random elements...");
        
        List<Integer> sample = sampleStream(stream, 5);
        System.out.println("Sample: " + sample);
    }
}
