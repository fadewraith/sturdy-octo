package os.memorymanagement;

import java.util.HashSet;
import java.util.Set;

/**
 * DEMAND PAGING
 * 
 * WHAT IT IS:
 * Pages are only loaded into physical memory when they are actually demanded 
 * during execution (Lazy Loading).
 * 
 * STRATEGY:
 * When a process tries to access a page that is not in memory, a "Page Fault" trap 
 * is generated. The OS catches this, pauses the process, fetches the page from disk 
 * into memory, updates the page table, and resumes the process.
 */
public class DemandPaging {

    public static void main(String[] args) {
        System.out.println("--- DEMAND PAGING (LAZY LOADING) SIMULATION ---");
        
        // Physical memory (holds loaded pages)
        Set<Integer> physicalMemory = new HashSet<>();
        
        // Mock disk containing all program pages
        Set<Integer> disk = new HashSet<>();
        for (int i = 1; i <= 5; i++) disk.add(i);

        int[] programExecutionSequence = {1, 2, 1, 3, 5, 2};

        for (int page : programExecutionSequence) {
            System.out.print("CPU requests Page " + page + ": ");
            
            if (physicalMemory.contains(page)) {
                System.out.println("Page Hit! Proceeding immediately.");
            } else {
                System.out.println("PAGE FAULT! Trapping to OS...");
                // Simulate OS fetching from disk
                if (disk.contains(page)) {
                    System.out.println("  -> OS fetching Page " + page + " from disk...");
                    physicalMemory.add(page);
                    System.out.println("  -> Page loaded into physical memory. Resuming execution.");
                } else {
                    System.out.println("  -> FATAL ERROR: Page does not exist on disk.");
                }
            }
        }
    }
}
