package os.memorymanagement;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * TRANSLATION LOOKASIDE BUFFER (TLB)
 * 
 * WHAT IT IS:
 * A hardware cache inside the MMU (Memory Management Unit).
 * It caches recent Virtual Page -> Physical Frame translations.
 */
public class TLBSimulation {

    static class TLB {
        int capacity;
        LinkedHashMap<Integer, Integer> cache;

        TLB(int capacity) {
            this.capacity = capacity;
            this.cache = new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                    return size() > capacity;
                }
            };
        }

        public Integer translate(int pageNumber) {
            if (cache.containsKey(pageNumber)) {
                System.out.println("TLB HIT for Page " + pageNumber + " -> Frame " + cache.get(pageNumber));
                return cache.get(pageNumber);
            } else {
                System.out.println("TLB MISS for Page " + pageNumber);
                return null;
            }
        }

        public void addEntry(int pageNumber, int frameNumber) {
            cache.put(pageNumber, frameNumber);
        }
    }

    public static void main(String[] args) {
        System.out.println("--- TLB SIMULATION ---");
        
        TLB tlb = new TLB(3); 
        
        Map<Integer, Integer> pageTable = new HashMap<>();
        pageTable.put(10, 100);
        pageTable.put(20, 200);
        pageTable.put(30, 300);
        pageTable.put(40, 400);

        int[] memoryRequests = {10, 20, 10, 30, 40, 10}; 

        for (int reqPage : memoryRequests) {
            Integer frame = tlb.translate(reqPage);
            
            if (frame == null) {
                frame = pageTable.get(reqPage);
                System.out.println("  Fetched from Page Table: Page " + reqPage + " -> Frame " + frame);
                tlb.addEntry(reqPage, frame);
            }
        }
    }
}
