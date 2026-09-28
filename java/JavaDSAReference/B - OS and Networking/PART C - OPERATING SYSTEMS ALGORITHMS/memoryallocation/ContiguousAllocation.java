package os.memoryallocation;

import java.util.ArrayList;
import java.util.List;

/**
 * CONTIGUOUS MEMORY ALLOCATION
 * 
 * WHAT IT IS:
 * Assigning a continuous block of physical memory to a process.
 * 
 * STRATEGIES:
 * 1. First-Fit: Allocate the FIRST hole that is big enough. (Fastest)
 * 2. Best-Fit: Allocate the SMALLEST hole that is big enough. (Leaves tiny useless holes - external fragmentation)
 * 3. Worst-Fit: Allocate the LARGEST hole. (Leaves large holes that might be usable later)
 */
public class ContiguousAllocation {

    static class Block {
        int id;
        int size;
        boolean isAllocated;

        Block(int id, int size) {
            this.id = id;
            this.size = size;
            this.isAllocated = false;
        }
        
        public Block clone() {
            return new Block(id, size);
        }
    }

    private static List<Block> copyBlocks(List<Block> original) {
        List<Block> copy = new ArrayList<>();
        for (Block b : original) copy.add(b.clone());
        return copy;
    }

    public static void firstFit(List<Block> memory, int[] processes) {
        System.out.println("--- FIRST-FIT ALLOCATION ---");
        List<Block> mem = copyBlocks(memory);
        
        for (int pSize : processes) {
            boolean allocated = false;
            for (Block b : mem) {
                if (!b.isAllocated && b.size >= pSize) {
                    b.isAllocated = true;
                    allocated = true;
                    System.out.println("Process size " + pSize + " allocated to Block " + b.id + " (Size " + b.size + ")");
                    break;
                }
            }
            if (!allocated) System.out.println("Process size " + pSize + " COULD NOT BE ALLOCATED.");
        }
        System.out.println();
    }

    public static void bestFit(List<Block> memory, int[] processes) {
        System.out.println("--- BEST-FIT ALLOCATION ---");
        List<Block> mem = copyBlocks(memory);
        
        for (int pSize : processes) {
            int bestIdx = -1;
            for (int i = 0; i < mem.size(); i++) {
                Block b = mem.get(i);
                if (!b.isAllocated && b.size >= pSize) {
                    if (bestIdx == -1 || b.size < mem.get(bestIdx).size) {
                        bestIdx = i;
                    }
                }
            }
            if (bestIdx != -1) {
                mem.get(bestIdx).isAllocated = true;
                System.out.println("Process size " + pSize + " allocated to Block " + mem.get(bestIdx).id + " (Size " + mem.get(bestIdx).size + ")");
            } else {
                System.out.println("Process size " + pSize + " COULD NOT BE ALLOCATED.");
            }
        }
        System.out.println();
    }

    public static void worstFit(List<Block> memory, int[] processes) {
        System.out.println("--- WORST-FIT ALLOCATION ---");
        List<Block> mem = copyBlocks(memory);
        
        for (int pSize : processes) {
            int worstIdx = -1;
            for (int i = 0; i < mem.size(); i++) {
                Block b = mem.get(i);
                if (!b.isAllocated && b.size >= pSize) {
                    if (worstIdx == -1 || b.size > mem.get(worstIdx).size) {
                        worstIdx = i;
                    }
                }
            }
            if (worstIdx != -1) {
                mem.get(worstIdx).isAllocated = true;
                System.out.println("Process size " + pSize + " allocated to Block " + mem.get(worstIdx).id + " (Size " + mem.get(worstIdx).size + ")");
            } else {
                System.out.println("Process size " + pSize + " COULD NOT BE ALLOCATED.");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        List<Block> memory = new ArrayList<>();
        memory.add(new Block(1, 100));
        memory.add(new Block(2, 500));
        memory.add(new Block(3, 200));
        memory.add(new Block(4, 300));
        memory.add(new Block(5, 600));

        int[] processes = {212, 417, 112, 426};

        firstFit(memory, processes);
        bestFit(memory, processes);
        worstFit(memory, processes);
    }
}
