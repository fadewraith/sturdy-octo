package os.memorymanagement;

import java.util.HashMap;
import java.util.Map;

/**
 * SEGMENTATION
 * 
 * WHAT IT IS:
 * Instead of fixed-size pages, memory is divided into variable-sized "Segments" 
 * that correspond to logical units of a program (e.g., Code, Data, Stack).
 * 
 * STRATEGY:
 * A Segment Table maps a Segment ID to a Base Physical Address and a Limit (Size).
 * If the requested offset exceeds the Limit, a Segmentation Fault is thrown!
 */
public class Segmentation {

    static class SegmentEntry {
        int base;
        int limit;

        SegmentEntry(int base, int limit) {
            this.base = base;
            this.limit = limit;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- SEGMENTATION TRANSLATION ---");
        
        Map<Integer, SegmentEntry> segmentTable = new HashMap<>();
        // Segment 0 (Code): Starts at 1000, Size is 500
        segmentTable.put(0, new SegmentEntry(1000, 500));
        // Segment 1 (Data): Starts at 3000, Size is 200
        segmentTable.put(1, new SegmentEntry(3000, 200));

        // Test Cases: [Segment ID, Offset]
        int[][] requests = {
            {0, 250}, // Valid Code access
            {1, 50},  // Valid Data access
            {1, 300}  // INVALID! Exceeds limit of 200
        };

        for (int[] req : requests) {
            int segId = req[0];
            int offset = req[1];
            
            System.out.printf("Requesting Segment %d, Offset %d -> ", segId, offset);
            
            if (segmentTable.containsKey(segId)) {
                SegmentEntry entry = segmentTable.get(segId);
                
                if (offset < entry.limit) {
                    int physicalAddress = entry.base + offset;
                    System.out.println("SUCCESS. Physical Address: " + physicalAddress);
                } else {
                    System.out.println("SEGMENTATION FAULT! (Offset exceeds limit)");
                }
            } else {
                System.out.println("SEGMENTATION FAULT! (Invalid Segment)");
            }
        }
    }
}
