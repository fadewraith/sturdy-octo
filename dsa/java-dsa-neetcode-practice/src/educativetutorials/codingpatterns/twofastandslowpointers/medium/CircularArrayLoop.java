package educativetutorials.codingpatterns.twofastandslowpointers.medium;

import java.util.Arrays;

public class CircularArrayLoop {

    public static boolean circularArrayLoop(int[] nums) {
        int size = nums.length;

        // Iterate through each index of the array 'nums'.
        for (int i = 0; i < size; i++) {
            // Set slow and fast pointer at current index value.
            int slow = i, fast = i;

            // Set true in 'forward' if element is positive, set false otherwise.
            boolean forward = nums[i] > 0;

            while (true) {
                // Move slow pointer to one step.
                slow = nextStep(slow, nums[slow], size);

                // If cycle is not possible, break the loop and start from next element.
                if (isNotCycle(nums, forward, slow))
                    break;

                // First move of fast pointer.
                fast = nextStep(fast, nums[fast], size);

                // If cycle is not possible, break the loop and start from next element.
                if (isNotCycle(nums, forward, fast))
                    break;

                // Second move of fast pointer.
                fast = nextStep(fast, nums[fast], size);

                // If cycle is not possible, break the loop and start from next element.
                if (isNotCycle(nums, forward, fast))
                    break;

                // At any point, if fast and slow pointers meet each other,
                // it indicates that loop has been found, return true.
                if (slow == fast)
                    return true;
            }
        }

        return false;
    }

    // A function to calculate the next step
    public static int nextStep(int pointer, int value, int size) {
        int result = (pointer + value) % size;
        if (result < 0)
            result += size;
        return result;
    }

    // A function to detect a cycle doesn't exist
    public static boolean isNotCycle(int[] nums, boolean prevDirection, int pointer) {
        // Set current direction to true if current element is positive, set false otherwise.
        boolean currDirection = nums[pointer] >= 0;

        // If current direction and previous direction are different or moving a pointer takes back to the same value,
        // then the cycle is not possible, we return true, otherwise return false.
        if (prevDirection != currDirection || nums[pointer] % nums.length == 0) {
            return true;
        }

        return false;
    }

    // Driver code
    public static void main(String[] args) {
        int[][] input = {
                {-2, -3, -9},
                {-5, -4, -3, -2, -1},
                {-1, -2, -3, -4, -5},
                {2, 1, -1, -2},
                {-1, -2, -3, -4, -5, 6},
                {1, 2, -3, 3, 4, 7, 1},
                {2, 2, 2, 7, 2, -1, 2, -1, -1}
        };

        for (int i = 0; i < input.length; i++) {
            System.out.println((i + 1) + ".\tCircular array = " + Arrays.toString(input[i]) + "\n");
            boolean result = circularArrayLoop(input[i]);
            System.out.println("\tFound Loop = " + result);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}
