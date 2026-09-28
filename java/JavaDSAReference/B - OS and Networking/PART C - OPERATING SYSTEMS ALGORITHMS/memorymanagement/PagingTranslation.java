package os.memorymanagement;

import java.util.HashMap;
import java.util.Map;

/**
 * PAGING & MULTI-LEVEL PAGE TABLE TRANSLATION
 * 
 * WHAT IT IS:
 * Paging divides virtual memory into fixed-size "Pages" and physical memory into 
 * fixed-size "Frames".
 * A Page Table maps Page Numbers to Frame Numbers.
 * 
 * MULTI-LEVEL PAGING:
 * Instead of one massive page table (which wastes memory), the page table is 
 * broken into a hierarchy (e.g., Page Directory -> Page Table -> Physical Frame).
 * 
 * SIMULATION:
 * Virtual Address is split into: [Directory Index] [Table Index] [Offset]
 */
public class PagingTranslation {

    // Simulating a 2-Level Page Table
    // Level 1: Page Directory (maps Directory Index -> Page Table)
    // Level 2: Page Table (maps Table Index -> Physical Frame Number)
    
    static class PageTable {
        Map<Integer, Integer> entries = new HashMap<>(); // TableIndex -> FrameNumber
    }

    static class PageDirectory {
        Map<Integer, PageTable> entries = new HashMap<>(); // DirIndex -> PageTable
    }

    public static void main(String[] args) {
        System.out.println("--- MULTI-LEVEL PAGING TRANSLATION ---");
        
        PageDirectory directory = new PageDirectory();
        
        // Setup mock physical memory mapping
        PageTable pt0 = new PageTable();
        pt0.entries.put(5, 12); // Virtual Table Index 5 maps to Physical Frame 12
        directory.entries.put(2, pt0); // Directory Index 2 points to pt0

        // Example Virtual Address: 0x02050A (Hex)
        // Let's say:
        // Directory Index = 0x02
        // Table Index = 0x05
        // Offset = 0x0A
        
        int dirIndex = 0x02;
        int tableIndex = 0x05;
        int offset = 0x0A;
        
        System.out.printf("Virtual Address Parts -> Dir: %d, Table: %d, Offset: %d\n", dirIndex, tableIndex, offset);

        // TRANSLATION LOGIC
        if (directory.entries.containsKey(dirIndex)) {
            PageTable pt = directory.entries.get(dirIndex);
            
            if (pt.entries.containsKey(tableIndex)) {
                int frameNumber = pt.entries.get(tableIndex);
                System.out.printf("SUCCESS! Translated to Physical Frame: %d\n", frameNumber);
                System.out.printf("Final Physical Address: [Frame %d][Offset %d]\n", frameNumber, offset);
            } else {
                System.out.println("PAGE FAULT! Table index not found in memory.");
            }
        } else {
            System.out.println("SEGMENTATION FAULT! Directory index invalid.");
        }
    }
}
