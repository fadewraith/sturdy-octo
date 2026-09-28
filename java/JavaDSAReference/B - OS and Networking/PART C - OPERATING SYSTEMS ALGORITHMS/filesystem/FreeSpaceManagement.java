package os.filesystem;

import java.util.LinkedList;

/**
 * FREE SPACE MANAGEMENT
 */
public class FreeSpaceManagement {

    public static void simulateBitmap() {
        System.out.println("--- BITMAP-BASED FREE SPACE ---");
        // 1 = Free, 0 = Used
        int[] bitmap = {1, 0, 1, 1, 0, 0, 1, 1}; 
        System.out.println("Searching for 2 contiguous free blocks...");
        
        for (int i = 0; i < bitmap.length - 1; i++) {
            if (bitmap[i] == 1 && bitmap[i+1] == 1) {
                System.out.println("Found free space at blocks " + i + " and " + (i+1));
                break;
            }
        }
    }

    public static void simulateLinkedList() {
        System.out.println("\n--- LINKED-LIST-BASED FREE SPACE ---");
        LinkedList<Integer> freeList = new LinkedList<>();
        freeList.add(0);
        freeList.add(2);
        freeList.add(3);
        freeList.add(6);
        freeList.add(7);
        
        System.out.println("Free blocks list: " + freeList);
        int blockAllocated = freeList.poll();
        System.out.println("Allocated block " + blockAllocated);
        System.out.println("Updated free list: " + freeList);
    }

    public static void main(String[] args) {
        simulateBitmap();
        simulateLinkedList();
    }
}
