package os.pagereplacement;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * LFU (LEAST FREQUENTLY USED) PAGE REPLACEMENT
 * 
 * WHAT IT IS:
 * Evicts the page that has been accessed the LEAST number of times.
 * If there's a tie, it evicts the oldest among them (LRU tie-breaker).
 * 
 * NOTE:
 * Like LRU, the hardcore O(1) from-scratch version was built in Prompt 02A.
 * Here we use Java's `HashMap` and `PriorityQueue` for the OS-level simulation.
 */
public class LFU {

    static class Page {
        int id;
        int frequency;
        int lastAccessTime;

        Page(int id, int time) {
            this.id = id;
            this.frequency = 1;
            this.lastAccessTime = time;
        }
    }

    public static int simulateLFU(int[] pages, int capacity) {
        Map<Integer, Page> memory = new HashMap<>();
        
        // Sort by frequency ascending. If tie, sort by lastAccessTime ascending (oldest first).
        PriorityQueue<Page> pq = new PriorityQueue<>((p1, p2) -> {
            if (p1.frequency == p2.frequency) {
                return Integer.compare(p1.lastAccessTime, p2.lastAccessTime);
            }
            return Integer.compare(p1.frequency, p2.frequency);
        });

        int pageFaults = 0;
        int time = 0;

        for (int pageId : pages) {
            time++;
            if (memory.containsKey(pageId)) {
                // Page Hit
                Page p = memory.get(pageId);
                pq.remove(p); // Remove to update
                p.frequency++;
                p.lastAccessTime = time;
                pq.offer(p);
            } else {
                // Page Fault
                pageFaults++;
                if (memory.size() == capacity) {
                    Page evicted = pq.poll(); // Evict LFU
                    memory.remove(evicted.id);
                }
                Page newPage = new Page(pageId, time);
                memory.put(pageId, newPage);
                pq.offer(newPage);
            }
        }
        return pageFaults;
    }

    public static void main(String[] args) {
        int[] referenceString = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1};
        int capacity = 3;
        System.out.println("LFU Page Faults: " + simulateLFU(referenceString, capacity));
    }
}
