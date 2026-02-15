package offthecollege.l1;

import java.util.Arrays;

public class MinSizeSubarraySumLC209 {

    /**
     * | Approach               | Time       | Space |
     * | ---------------------- | ---------- | ----- |
     * | Brute Force            | O(n²)      | O(1)  |
     * | 2-Pointer              | O(n)       | O(1)  |
     * | Prefix + Binary Search | O(n log n) | O(n)  |
     * */


    private static int bruteForce(int[] nums, int target) {
        int n = nums.length;
        int minLen = Integer.MAX_VALUE;

        for(int i = 0; i < n; i++) {
            int sum = 0;

            for(int j = i; j < n; j++) {
                sum += nums[j];

                if(sum >= target) {
                    minLen = Math.min(minLen, j - i + 1);
                    break;
                }
            }
        }

        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    private static int twoPointer(int[] nums, int target) {
        int left = 0;
        int sum = 0;
        int minLen = Integer.MAX_VALUE;

        for(int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while(sum >= target) {
                minLen = Math.min(minLen, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }

        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }


    private static int followUp(int[] nums, int target) {
        int n = nums.length;
        int[] prefix = new int[n + 1];

        // Build prefix sum
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        int minLen = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            int required = prefix[i] + target;

            int bound = Arrays.binarySearch(prefix, required);

            if (bound < 0) {
                bound = -bound - 1; // insertion point
            }

            if (bound <= n) {
                minLen = Math.min(minLen, bound - i);
            }
        }

        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    public static void main(String[] args) {
        long startBrute = System.nanoTime();
        System.out.println(bruteForce(new int[] {2,3,1,2,4,3}, 7));
        System.out.println(bruteForce(new int[] {1,4,4}, 4));
        System.out.println(bruteForce(new int[] {1,1,1,1,1,1,1,1}, 11));
        long endBrute = System.nanoTime();
        System.out.println(endBrute - startBrute);
        System.out.println("==========================");
        long startTwo = System.nanoTime();
        System.out.println(twoPointer(new int[] {2,3,1,2,4,3}, 7));
        System.out.println(twoPointer(new int[] {1,4,4}, 4));
        System.out.println(twoPointer(new int[] {1,1,1,1,1,1,1,1}, 11));
        long endTwo = System.nanoTime();
        System.out.println(endTwo - startTwo);
        System.out.println("==========================");
        long startFollow = System.nanoTime();
        System.out.println(followUp(new int[] {2,3,1,2,4,3}, 7));
        System.out.println(followUp(new int[] {1,4,4}, 4));
        System.out.println(followUp(new int[] {1,1,1,1,1,1,1,1}, 11));
        long endFollow = System.nanoTime();
        System.out.println(endFollow - startFollow);
    }

}
