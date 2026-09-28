package os.pagereplacement;

/**
 * BELADY'S ANOMALY DEMONSTRATION
 * 
 * WHAT IT IS:
 * In most algorithms, increasing the number of memory frames (capacity) decreases 
 * the number of page faults. 
 * Belady's Anomaly is a phenomenon where increasing the number of page frames 
 * actually INCREASES the number of page faults!
 * 
 * WHY IT HAPPENS:
 * FIFO suffers from this because it purely evicts based on age, completely 
 * ignoring usage patterns. Adding a frame can shift the eviction cycle so poorly 
 * that highly requested pages get evicted right before they are needed.
 * (LRU and Optimal do NOT suffer from this because they are stack-based algorithms).
 */
public class BeladysAnomaly {

    public static void main(String[] args) {
        System.out.println("--- BELADY'S ANOMALY DEMONSTRATION ---");
        
        // Classic reference string that causes the anomaly
        int[] referenceString = {1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5};
        
        int faultsWith3Frames = FIFO.simulateFIFO(referenceString, 3);
        int faultsWith4Frames = FIFO.simulateFIFO(referenceString, 4);
        
        System.out.println("Page Faults with 3 Frames: " + faultsWith3Frames);
        System.out.println("Page Faults with 4 Frames: " + faultsWith4Frames);
        System.out.println();
        
        if (faultsWith4Frames > faultsWith3Frames) {
            System.out.println("ANOMALY DETECTED: More memory frames caused MORE page faults!");
        }
    }
}
