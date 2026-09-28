package os.pagereplacement;

import java.util.LinkedList;
import java.util.Queue;

/**
 * FIFO PAGE REPLACEMENT
 * 
 * WHAT IT IS:
 * The simplest page replacement algorithm. The OS maintains a queue of pages in memory.
 * When a page fault occurs and memory is full, the oldest page (front of queue) is evicted.
 * 
 * TIME COMPLEXITY:
 * O(1) for adding/removing. O(N) to check if page exists (in this array/queue simulation).
 */
public class FIFO {
    
    public static int simulateFIFO(int[] pages, int capacity) {
        Queue<Integer> memory = new LinkedList<>();
        int pageFaults = 0;

        for (int page : pages) {
            if (!memory.contains(page)) {
                pageFaults++;
                if (memory.size() == capacity) {
                    memory.poll(); // Evict oldest
                }
                memory.add(page); // Add new page
            }
        }
        return pageFaults;
    }

    public static void main(String[] args) {
        int[] referenceString = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1};
        int capacity = 3;
        System.out.println("FIFO Page Faults: " + simulateFIFO(referenceString, capacity));
    }
}
