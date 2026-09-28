package os.pagereplacement;

/**
 * CLOCK / SECOND-CHANCE ALGORITHM
 * 
 * WHAT IT IS:
 * A practical approximation of LRU that is highly efficient to implement in hardware.
 * Pages are kept in a circular queue (a clock). 
 * Each page has a "reference bit".
 * 
 * HOW IT WORKS:
 * When a page is accessed, its reference bit is set to 1.
 * When a page fault occurs, the clock hand sweeps around:
 * - If it points to a page with bit=1, it resets the bit to 0 (giving it a "second chance") and moves on.
 * - If it points to a page with bit=0, that page is evicted!
 */
public class ClockSecondChance {

    public static int simulateClock(int[] pages, int capacity) {
        int[] memory = new int[capacity];
        boolean[] referenceBits = new boolean[capacity];
        
        // Initialize memory as empty (-1)
        for (int i = 0; i < capacity; i++) memory[i] = -1;
        
        int pageFaults = 0;
        int hand = 0; // Clock hand points to the oldest page

        for (int page : pages) {
            boolean found = false;
            
            // Check if page is already in memory
            for (int i = 0; i < capacity; i++) {
                if (memory[i] == page) {
                    found = true;
                    referenceBits[i] = true; // Set reference bit!
                    break;
                }
            }

            if (!found) {
                pageFaults++;
                
                // Sweep the clock hand
                while (true) {
                    if (!referenceBits[hand]) {
                        // Found a victim!
                        memory[hand] = page;
                        referenceBits[hand] = true; // Newly loaded page gets bit=1
                        hand = (hand + 1) % capacity;
                        break;
                    } else {
                        // Second chance! Reset bit and move on
                        referenceBits[hand] = false;
                        hand = (hand + 1) % capacity;
                    }
                }
            }
        }
        return pageFaults;
    }

    public static void main(String[] args) {
        int[] referenceString = {2, 5, 10, 1, 2, 2, 6, 9, 1, 2, 10, 2, 6, 1, 2, 1, 6, 9, 5, 1};
        int capacity = 3;
        System.out.println("Clock/Second-Chance Page Faults: " + simulateClock(referenceString, capacity));
    }
}
