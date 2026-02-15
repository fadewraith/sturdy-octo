package educativetutorials.acejavacoding.ds.arrays;

import educativetutorials.codingpatterns.commonutilities.CommonUtils;

import java.util.Arrays;

public class RightRotateArrayByK {

    public static int[] bruteForce(int[] nums, int k) {
        if(nums.length == 0) {
            k = 0;
        } else {
            k %= nums.length;
        }

        int[] rotatedArray = new int[nums.length];

        // copy elements from end
        for (int i = 0; i < k; i++) {
            rotatedArray[i] = nums[nums.length - k + i];
        }

        // copy remaining elements
        for (int i = k; i < nums.length; i++) {
            rotatedArray[i] = nums[i - k];
        }

        return rotatedArray;
    }
    //    Time: O(n) - each element touched 2 times max
//    Space: O(1) - only modifies input array, uses one temp variable

    private static void sliceShiftRotation(int[] nums, int k) {
        k = nums.length == 0 ? 0 : k % nums.length;

        if (k == 0) return;

        // reverse entire array
        reverse(nums, 0, nums.length - 1);
        // reverse first k elements
        reverse(nums, 0, k - 1);
        // reverse remaining elements
        reverse(nums, k, nums.length - 1);
    }

    public static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            CommonUtils.swap(nums, start++, end--);
        }
    }


    public static void main(String[] args) {
        int[][] inputs = {
                {10, 20, 30, 40, 50},
                {1, -2, 3, 4, 5},
                {-1, 90, -90, 4, 6},
                {3, 6, 9, -12},
                {-100, -200, -300}
        };
        int[] k = {3, 2, 6, 2, 1};

        for (int i = 0; i < inputs.length; i++) {
            System.out.println((i + 1) + ".\tnums: " + Arrays.toString(inputs[i]));
            System.out.println("\tk: " + k[i]);

//            int[] rotated = bruteForce(inputs[i], k[i]);
//            System.out.println("\n\tRotated Array: " + Arrays.toString(rotated));
            sliceShiftRotation(inputs[i], k[i]);
            System.out.println("\n\tRotated Array: " + Arrays.toString(inputs[i]));
            System.out.println(new String(new char[70]).replace('\0', '-'));

        }
    }
}
