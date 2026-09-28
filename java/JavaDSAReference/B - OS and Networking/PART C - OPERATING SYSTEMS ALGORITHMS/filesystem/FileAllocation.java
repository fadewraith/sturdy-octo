package os.filesystem;

import java.util.ArrayList;
import java.util.List;

/**
 * FILE ALLOCATION METHODS
 * 
 * Demonstrating how files are stored in disk blocks.
 */
public class FileAllocation {

    static class DiskBlock {
        int id;
        String data;
        int nextBlockId = -1; // Used for Linked Allocation
        List<Integer> indexBlock = null; // Used for Indexed Allocation
        
        DiskBlock(int id) {
            this.id = id;
        }
    }

    public static void simulateContiguous(List<DiskBlock> disk) {
        System.out.println("--- CONTIGUOUS ALLOCATION ---");
        // A file requires 3 blocks. We find 3 contiguous free blocks.
        System.out.println("Allocating File 'A' (size 3) starting at Block 1");
        disk.get(1).data = "FileA_Part1";
        disk.get(2).data = "FileA_Part2";
        disk.get(3).data = "FileA_Part3";
        System.out.println("Success! (Subject to external fragmentation)");
    }

    public static void simulateLinked(List<DiskBlock> disk) {
        System.out.println("--- LINKED ALLOCATION ---");
        // Files can be scattered. Blocks point to the next block.
        System.out.println("Allocating File 'B' (size 3) scattered at Blocks 0, 4, 6");
        disk.get(0).data = "FileB_Part1";
        disk.get(0).nextBlockId = 4;
        
        disk.get(4).data = "FileB_Part2";
        disk.get(4).nextBlockId = 6;
        
        disk.get(6).data = "FileB_Part3";
        disk.get(6).nextBlockId = -1; // EOF
        System.out.println("Success! No external fragmentation, but slow random access.");
    }

    public static void simulateIndexed(List<DiskBlock> disk) {
        System.out.println("--- INDEXED ALLOCATION ---");
        // One block acts as an index block containing pointers to data blocks.
        System.out.println("Allocating File 'C' (size 3). Index Block at 8. Data at 5, 7, 9.");
        disk.get(8).indexBlock = new ArrayList<>();
        disk.get(8).indexBlock.add(5);
        disk.get(8).indexBlock.add(7);
        disk.get(8).indexBlock.add(9);
        
        disk.get(5).data = "FileC_Part1";
        disk.get(7).data = "FileC_Part2";
        disk.get(9).data = "FileC_Part3";
        System.out.println("Success! Fast random access, no external fragmentation, but wastes 1 block for index.");
    }

    public static void main(String[] args) {
        List<DiskBlock> disk = new ArrayList<>();
        for (int i = 0; i < 10; i++) disk.add(new DiskBlock(i));
        
        simulateContiguous(disk);
        System.out.println();
        simulateLinked(disk);
        System.out.println();
        simulateIndexed(disk);
    }
}
