package os.pagereplacement;

import java.util.ArrayList;
import java.util.List;

/**
 * OPTIMAL PAGE REPLACEMENT (BELADY'S ALGORITHM)
 * 
 * WHAT IT IS:
 * Evicts the page that will not be used for the LONGEST period of time in the future.
 * 
 * WHY USE IT:
 * It is impossible to implement in a real OS because it requires predicting the future!
 * It is used purely as a theoretical benchmark to measure how close to "perfect" 
 * other algorithms (like LRU) get.
 */
public class Optimal {
    
    public static int simulateOptimal(int[] pages, int capacity) {
        List<Integer> memory = new ArrayList<>();
        int pageFaults = 0;

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];
            
            if (!memory.contains(page)) {
                pageFaults++;
                
                if (memory.size() == capacity) {
                    // Find page to evict (the one used furthest in the future)
                    int furthestUse = -1;
                    int pageToEvict = -1;
                    
                    for (int memPage : memory) {
                        int nextUse = Integer.MAX_VALUE;
                        // Look into the future!
                        for (int j = i + 1; j < pages.length; j++) {
                            if (pages[j] == memPage) {
                                nextUse = j;
                                break;
                            }
                        }
                        if (nextUse > furthestUse) {
                            furthestUse = nextUse;
                            pageToEvict = memPage;
                        }
                    }
                    memory.remove((Integer) pageToEvict);
                }
                memory.add(page);
            }
        }
        return pageFaults;
    }

    public static void main(String[] args) {
        int[] referenceString = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1};
        int capacity = 3;
        System.out.println("Optimal Page Faults: " + simulateOptimal(referenceString, capacity));
    }
}
