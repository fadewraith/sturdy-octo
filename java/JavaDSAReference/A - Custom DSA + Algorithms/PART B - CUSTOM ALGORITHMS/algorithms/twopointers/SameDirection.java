package algorithms.twopointers;

import java.util.Arrays;

/**
 * TWO POINTERS: SAME-DIRECTION (Fast/Slow Pointers)
 * 
 * WHAT IT IS:
 * An algorithmic pattern where two pointers start at the same end of a linear 
 * data structure (index 0). They either move at different speeds (e.g., slow moves 
 * 1 step, fast moves 2 steps) or one advances conditionally.
 * 
 * WHEN TO USE THIS:
 * - Removing duplicates from a sorted array in-place.
 * - Partitioning arrays (e.g., moving all zeroes to the end).
 * - Cycle detection in Linked Lists (Floyd's Tortoise and Hare algorithm).
 * - Finding the middle of a Linked List.
 * 
 * DATA STRUCTURE:
 * Arrays or Linked Lists.
 * 
 * CROSS-REFERENCE:
 * - See `PART A/linkedlist/sll/SinglyLinkedList.java` for the actual implementation 
 *   of cycle detection on Linked Lists using this exact pattern!
 * 
 * COMPLEXITY:
 * Time: O(N) as both pointers traverse the structure in a single forward pass.
 * Space: O(1) strictly in-place.
 */
public class SameDirection {

    /**
     * Problem 1: Remove Duplicates from Sorted Array In-Place
     * Returns the length of the new "clean" subarray.
     * 
     * Strategy: 
     * `slow` tracks the index of where the next unique element should be written.
     * `fast` scans forward to find the next unique element.
     */
    public static int removeDuplicates(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        
        int slow = 0; // Tracks the "write" position
        
        for (int fast = 1; fast < arr.length; fast++) {
            if (arr[fast] != arr[slow]) {
                slow++;
                arr[slow] = arr[fast];
            }
        }
        
        return slow + 1; // Length is index + 1
    }

    /**
     * Problem 2: Move Zeroes to End In-Place
     * Maintains the relative order of non-zero elements.
     * 
     * Strategy:
     * `slow` points to the first available zero slot.
     * `fast` scans for non-zero numbers to throw backward into the `slow` slot.
     */
    public static void moveZeroes(int[] arr) {
        int slow = 0; 
        
        for (int fast = 0; fast < arr.length; fast++) {
            if (arr[fast] != 0) {
                // Swap fast and slow
                int temp = arr[slow];
                arr[slow] = arr[fast];
                arr[fast] = temp;
                
                slow++; // Advance the zero pointer
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- SAME-DIRECTION TWO POINTERS DEMO ---");
        
        int[] duplicates = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        System.out.println("Array with duplicates: " + Arrays.toString(duplicates));
        
        int uniqueCount = removeDuplicates(duplicates);
        System.out.println("Unique count: " + uniqueCount); // 5
        System.out.print("Cleaned array prefix: [");
        for (int i = 0; i < uniqueCount; i++) {
            System.out.print(duplicates[i] + (i == uniqueCount - 1 ? "" : ", "));
        }
        System.out.println("]\n");
        
        int[] zeroes = {0, 1, 0, 3, 12};
        System.out.println("Array with zeroes: " + Arrays.toString(zeroes));
        moveZeroes(zeroes);
        System.out.println("Zeroes moved to end: " + Arrays.toString(zeroes)); // [1, 3, 12, 0, 0]
    }
}
