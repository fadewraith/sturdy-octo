package educativetutorials.codingpatterns.threeslidingwindow.easy;

public class MaxAvgSubarrayI {

    private static double findMaxAverage(int[] nums, int k) {
        int currSum = 0;

        // Calculate the sum of the first 'k' elements in the array
        for(int i = 0; i < k; i++) {
            currSum += nums[i];
        }

        // Initialize 'maxSum' with 'currentSum'
        int maxSum = currSum;

        // Iterate through the array starting from the 'kth' element
        for(int i = k; i < nums.length; i++) {
            // Subtract the element that just left the window,
            // and add the new element that just entered in the window
            currSum += nums[i] - nums[i - k];

            // Update 'maxSum' if the new 'currentSum' is greater
            maxSum = Math.max(maxSum, currSum);

        }

        // Return the maximum average, which is 'maxSum' divided by 'k'
        return (double) maxSum / k;
    }

    public static void main(String[] args) {
        int[][] inputData = {
                {10, 5, 2, -1, 6, 3, -2, -4, 4, 1, -3, -6, -1, -2, -5, -7},
                {7, 3, 1, -2, 6, 2, -1, -3, 4, 1, -2, -5, 2, 0, -4, -6},
                {12, 9, 5, 2, 8, 6, 4, 1, 7, 5, 3, 0, 4, 2, 0, -3},
                {-10, -11, -12, -13, -20, -21, -22, -23, -30, -31, -32, -33, -40, -41, -42, -43},
                {5, 3, -2, -3, 4, 2, -3, -4, 3, 1, -4, -5, 2, 0, -5, -6}
        };

        int k = 4;

        for (int i = 0; i < inputData.length; i++) {
            int[] nums = inputData[i];
            double result = findMaxAverage(nums, k);
            System.out.print((i + 1) + ".\tInput: nums = {");
            for (int num : nums) {
                System.out.print(num + " ");
            }
            System.out.println("}, k = " + k);
            System.out.printf("\tMaximum Average: %.2f%n", result);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}
