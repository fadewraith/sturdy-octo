package educativetutorials.acejavacoding.ds.arrays;

import educativetutorials.codingpatterns.commonutilities.CommonUtils;

public class LeftRotateArrayByK {

//    Time: O(n), Space: O(n)

    public static int[] naiveLeftRotate(int[] nums, int k) {
        if(nums.length == 0) {
            k = 0;
        } else {
            k %= nums.length;  // Handle k > length
        }

        int[] rotatedArray = new int[nums.length];

        // Copy elements from index k to end
        for(int i = 0; i < nums.length - k; i++) {
            rotatedArray[i] = nums[k + i];
        }

        // Copy first k elements to end
        for(int i = 0; i < k; i++) {
            rotatedArray[nums.length - k + i] = nums[i];
        }

        return rotatedArray;
    }

    public static void optimalLeftRotate(int[] nums, int k) {
        if(nums.length == 0) return;

        k %= nums.length;

        if(k == 0) return;

        // Step 1: Reverse first k elements
        CommonUtils.reverse(nums, 0, k - 1);

        // Step 2: Reverse remaining elements
        CommonUtils.reverse(nums, k, nums.length - 1);

        // Step 3: Reverse entire array
        CommonUtils.reverse(nums, 0, nums.length - 1);
    }

    public static void main(String[] args) {
        int[][] inputs = {
                {10, 20, 30, 40, 50},
                {1, 2, 3, 4, 5},
                {-1, 90, -90, 4, 6},
                {3, 6, 9, 12},
                {100, 200, 300}
        };
        int[] k = {2, 1, 3, 2, 1};

        for(int i = 0; i < inputs.length; i++) {
            System.out.println((i + 1) + ".\tnums: ");
            printArray(inputs[i]);
            System.out.println("\tk: " + k[i]);

            // Using optimal approach
            optimalLeftRotate(inputs[i], k[i]);
            System.out.println("\tRotated Array: ");
            printArray(inputs[i]);
            System.out.println(new String(new char[70]).replace('\0', '-'));
        }
    }

    private static void printArray(int[] arr) {
        System.out.print("[");
        for(int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if(i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
}
