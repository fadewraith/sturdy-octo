package os.pagereplacement;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU (LEAST RECENTLY USED) PAGE REPLACEMENT
 * 
 * WHAT IT IS:
 * Evicts the page that has not been accessed for the longest time.
 * Assumes that pages used recently will likely be used again soon.
 * 
 * NOTE ON BUILT-INS:
 * In Prompt 02A, we built a production-grade O(1) LRU Cache entirely from scratch 
 * using a custom Doubly Linked List and custom HashMap. 
 * Since 02B allows built-ins for OS simulations, we use Java's `LinkedHashMap` 
 * configured with access-order (true) to natively simulate the LRU behavior here.
 */
public class LRU {

    public static int simulateLRU(int[] pages, int capacity) {
        // LinkedHashMap with accessOrder = true automatically moves accessed elements to the back
        LinkedHashMap<Integer, Boolean> memory = new LinkedHashMap<Integer, Boolean>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Boolean> eldest) {
                return size() > capacity; // Automatically evict eldest (front of list) if capacity exceeded
            }
        };

        int pageFaults = 0;

        for (int page : pages) {
            if (!memory.containsKey(page)) {
                pageFaults++;
                memory.put(page, true);
            } else {
                memory.get(page); // Triggers accessOrder to move page to the back (most recently used)
            }
        }
        return pageFaults;
    }

    public static void main(String[] args) {
        int[] referenceString = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1};
        int capacity = 3;
        System.out.println("LRU Page Faults: " + simulateLRU(referenceString, capacity));
    }
}
