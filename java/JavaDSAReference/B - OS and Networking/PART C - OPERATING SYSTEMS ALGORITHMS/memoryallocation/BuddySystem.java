package os.memoryallocation;

import java.util.ArrayList;
import java.util.List;

/**
 * BUDDY SYSTEM ALLOCATION
 * 
 * WHAT IT IS:
 * A memory allocation algorithm that divides memory into partitions to try to 
 * satisfy a memory request as suitably as possible.
 * It repeatedly halves (splits) blocks of memory into "buddies" until a block 
 * is just large enough (always a power of 2).
 * 
 * WHY IT MATTERS:
 * When memory is freed, if its "buddy" is also free, they are immediately merged 
 * back together into a larger block! This drastically reduces External Fragmentation.
 */
public class BuddySystem {

    static class Block {
        int size;
        boolean isFree;
        int id; // Mock ID for demonstration
        
        Block(int size, boolean isFree, int id) {
            this.size = size;
            this.isFree = isFree;
            this.id = id;
        }
    }

    private List<Block> memory = new ArrayList<>();
    private int nextId = 1;

    public BuddySystem(int totalMemoryPowerOf2) {
        memory.add(new Block(totalMemoryPowerOf2, true, nextId++));
    }

    // Helper to find the next power of 2
    private int nextPowerOf2(int n) {
        int count = 0;
        if (n > 0 && (n & (n - 1)) == 0) return n;
        while (n != 0) {
            n >>= 1;
            count += 1;
        }
        return 1 << count;
    }

    public void allocate(int requestSize) {
        int requiredSize = nextPowerOf2(requestSize);
        System.out.println("Requested: " + requestSize + " -> Rounded to power of 2: " + requiredSize);
        
        for (int i = 0; i < memory.size(); i++) {
            Block b = memory.get(i);
            
            if (b.isFree && b.size >= requiredSize) {
                // Keep splitting until it's the exact required size
                while (b.size > requiredSize) {
                    System.out.println("  Splitting block of size " + b.size + " into two " + (b.size / 2) + " buddies...");
                    b.size /= 2;
                    // Add the buddy right next to it
                    memory.add(i + 1, new Block(b.size, true, nextId++));
                }
                b.isFree = false;
                System.out.println("  -> Allocated Block ID " + b.id + " (Size " + b.size + ")\n");
                return;
            }
        }
        System.out.println("  -> ALLOCATION FAILED (Not enough contiguous memory)\n");
    }

    public void printMemory() {
        System.out.print("Memory Map: [");
        for (Block b : memory) {
            System.out.print((b.isFree ? "Free:" : "Used:") + b.size + " | ");
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        System.out.println("--- BUDDY SYSTEM ALLOCATION DEMO ---");
        
        BuddySystem buddy = new BuddySystem(1024); // Start with 1024 unit block
        buddy.printMemory();
        
        buddy.allocate(100); // Should round to 128, splitting 1024 -> 512, 256, 128
        buddy.printMemory();
        
        buddy.allocate(240); // Should round to 256
        buddy.printMemory();
        
        buddy.allocate(64);  // Should round to 64
        buddy.printMemory();
    }
}
