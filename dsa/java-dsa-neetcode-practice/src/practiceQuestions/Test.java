package practiceQuestions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Test {

    private static int solution(int[] nums, int val) {
        int n = nums.length;
        System.out.println("Initial array: " + Arrays.toString(nums));
        System.out.println("Removing value: " + val + "\n");

        for(int i = 0; i < n; i++) {
            System.out.println("\n--- Step " + (i+1) + " ---");
            System.out.println("Checking index " + i + ": " + nums[i]);
            
            if(nums[i] == val) {
                System.out.println("Found " + val + " at index " + i + ". Shifting elements...");
                
                for(int j = i; j < n - 1; j++) {
                    nums[j] = nums[j + 1];
                    System.out.println("  Moved " + nums[j] + " from index " + (j+1) + " to " + j);
                }
                
                n--;  // Reduce the effective length
                i--;  // Recheck current position as it now has a new value
                
                System.out.println("After shift: " + Arrays.toString(nums));
                System.out.println("New effective length: " + n);
            } else {
                System.out.println("Value " + nums[i] + " at index " + i + " is not " + val + ". Moving to next.");
            }
        }

        System.out.println("\nFinal array state: " + Arrays.toString(nums));
        return n;
    }




    public static void main(String[] args) {
        int[] nums = { 1, 1, 2, 3, 4 };
        int val = 1;
        int newLength = solution(nums, val);
        System.out.println("New length: " + newLength);
        System.out.print("Modified array: ");
        for (int i = 0; i < newLength; i++) {
            System.out.print(nums[i] + " ");
        }
    }

}