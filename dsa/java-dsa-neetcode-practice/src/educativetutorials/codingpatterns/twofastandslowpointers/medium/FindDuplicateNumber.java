package educativetutorials.codingpatterns.twofastandslowpointers.medium;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FindDuplicateNumber {

    private static int optimal(int[] nums) {

        // Initialize the fast and slow pointers and make them point the first
        // element of the array
        int fast = nums[0];
        int slow = nums[0];

        // PART #1
        // Traverse in array until the intersection point is found
        while(true) {
            // Move the slow pointer using the nums[slow] flow
            slow = nums[slow];
            // Move the fast pointer two times fast as the slow pointer using the
            // nums[nums[fast]] flow
            fast = nums[nums[fast]];
            // Break the loop when slow pointer becomes equal to the fast pointer, i.e.,
            // if the intersection is found
            if(slow == fast) {
                break;
            }
        }

        // PART #2
        // Make the slow pointer point the starting position of an array again, i.e.,
        // start the slow pointer from starting position
        slow = nums[0];
        // Traverse the array until the slow pointer becomes equal to the
        // fast pointer
        while(slow != fast) {
            // Move the slow pointer using the nums[slow] flow
            slow = nums[slow];
            // Move the fast pointer slower than before, i.e., move the fast pointer
            // using the nums[fast] flow
            fast = nums[fast];
        }

        // Return the fast pointer as it points the duplicate number of the array
        return fast;
    }

    public static int bruteForce2(int[] nums) {
        // Edge case
        if (nums == null || nums.length <= 1) {
            throw new IllegalArgumentException("Invalid input array");
        }

        Set<Integer> seen = new HashSet<>();

        for (int num : nums) {
            if (seen.contains(num)) {
                return num;
            }
            seen.add(num);
        }

        // Problem guarantees one duplicate
        throw new IllegalStateException("No duplicate found");
    }

    public static int bruteForce1(int[] nums) {
        if (nums == null || nums.length <= 1) {
            throw new IllegalArgumentException("Invalid input array");
        }

        int n = nums.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {

                if (nums[i] == nums[j]) {
                    return nums[i];   // duplicate found
                }
            }
        }

        // Guaranteed one duplicate
        throw new IllegalStateException("No duplicate found");
    }


    public static void main(String[] args) {
        int[][] nums = {
                {1, 3, 2, 3, 5, 4},
                {2, 4, 5, 4, 1, 3},
                {1, 6, 3, 5, 1, 2, 7, 4},
                {1, 2, 2, 4, 3},
                {3, 1, 3, 5, 6, 4, 2}
        };
        for (int i = 0; i < nums.length; i++) {
            System.out.print(i + 1);
            System.out.println(".\tnums = "+ Arrays.toString(nums[i]));
            System.out.println("\tDuplicate number = "+ optimal(nums[i]));
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}
