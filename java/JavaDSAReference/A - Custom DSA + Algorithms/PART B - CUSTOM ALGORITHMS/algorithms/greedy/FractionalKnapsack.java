package algorithms.greedy;

import java.util.Arrays;

/**
 * FRACTIONAL KNAPSACK
 * 
 * WHAT IT IS:
 * Given weights and values of N items, put these items in a knapsack of capacity W 
 * to get the maximum total value.
 * 
 * CRITICAL DISTINCTION:
 * In the *Fractional* Knapsack, you CAN break items apart (e.g., gold dust, flour).
 * In the *0/1* Knapsack, you CANNOT break items (e.g., a TV, a laptop), which forces 
 * you to use Dynamic Programming instead of Greedy!
 * 
 * COMBINATION USAGE:
 * - Sorting + Greedy Strategy.
 * 
 * STRATEGY:
 * 1. Calculate the Ratio (Value / Weight) for each item.
 * 2. Sort the items in descending order of this ratio.
 * 3. Take as much of the highest-ratio items as possible until the bag is full.
 * 
 * COMPLEXITY:
 * Time: O(N log N) to sort.
 * Space: O(1)
 */
public class FractionalKnapsack {

    static class Item implements Comparable<Item> {
        int weight;
        int value;
        double ratio;

        Item(int value, int weight) {
            this.value = value;
            this.weight = weight;
            this.ratio = (double) value / weight;
        }

        @Override
        public int compareTo(Item other) {
            // Sort in DESCENDING order of ratio
            return Double.compare(other.ratio, this.ratio);
        }
    }

    public static double getMaxValue(Item[] items, int capacity) {
        // 1. Sort items by value/weight ratio
        Arrays.sort(items);

        double totalValue = 0d;

        // 2. Greedily pick items
        for (Item item : items) {
            if (capacity - item.weight >= 0) {
                // We can take the whole item
                capacity -= item.weight;
                totalValue += item.value;
            } else {
                // We can only take a fraction of the item
                totalValue += item.ratio * capacity;
                break; // Bag is now completely full!
            }
        }
        return totalValue;
    }

    public static void main(String[] args) {
        System.out.println("--- FRACTIONAL KNAPSACK DEMO ---");
        
        Item[] items = {
            new Item(60, 10), // Ratio: 6
            new Item(100, 20),// Ratio: 5
            new Item(120, 30) // Ratio: 4
        };
        int capacity = 50;

        System.out.println("Max Value we can carry: " + getMaxValue(items, capacity));
        // Expected: Takes all of item 1 (10w) + all of item 2 (20w) + 2/3rds of item 3 (20w)
        // Value: 60 + 100 + (120 * 20/30) = 160 + 80 = 240.0
    }
}
