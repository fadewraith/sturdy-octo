package probabilistic.countminsketch;

import java.util.Random;

/**
 * Count-Min Sketch
 * Approximate frequency counting with bounded memory.
 * Applies to: streaming data, "top-k" frequency problems at scale.
 */
public class CountMinSketch {
    private final int width;
    private final int depth;
    private final int[][] table;
    private final long[] hashSeeds;

    public CountMinSketch(double epsilon, double delta) {
        this.width = (int) Math.ceil(Math.E / epsilon);
        this.depth = (int) Math.ceil(Math.log(1 / delta));
        this.table = new int[depth][width];
        this.hashSeeds = new long[depth];
        
        Random random = new Random();
        for (int i = 0; i < depth; i++) {
            hashSeeds[i] = random.nextLong();
        }
    }

    private int hash(String item, int i) {
        long hash = 5381;
        for (int j = 0; j < item.length(); j++) {
            hash = ((hash << 5) + hash) + item.charAt(j);
        }
        hash = (hash ^ hashSeeds[i]) * 31;
        return (int) (Math.abs(hash) % width);
    }

    public void add(String item) {
        for (int i = 0; i < depth; i++) {
            int hashVal = hash(item, i);
            table[i][hashVal]++;
        }
    }

    public int estimate(String item) {
        int minCount = Integer.MAX_VALUE;
        for (int i = 0; i < depth; i++) {
            int hashVal = hash(item, i);
            minCount = Math.min(minCount, table[i][hashVal]);
        }
        return minCount;
    }

    public static void main(String[] args) {
        System.out.println("--- Count-Min Sketch Simulation ---");
        CountMinSketch cms = new CountMinSketch(0.01, 0.01);
        
        System.out.println("Adding events...");
        for (int i = 0; i < 1000; i++) {
            cms.add("eventA");
        }
        for (int i = 0; i < 500; i++) {
            cms.add("eventB");
        }
        for (int i = 0; i < 10; i++) {
            cms.add("eventC");
        }

        System.out.println("Estimated count for eventA: " + cms.estimate("eventA") + " (Expected ~1000)");
        System.out.println("Estimated count for eventB: " + cms.estimate("eventB") + " (Expected ~500)");
        System.out.println("Estimated count for eventC: " + cms.estimate("eventC") + " (Expected ~10)");
        System.out.println("Estimated count for eventD: " + cms.estimate("eventD") + " (Expected ~0)");
    }
}
