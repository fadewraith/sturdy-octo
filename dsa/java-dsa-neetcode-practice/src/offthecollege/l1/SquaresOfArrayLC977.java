package offthecollege.l1;

import java.util.Arrays;

public class SquaresOfArrayLC977 {

    private static int[] sortedSquares(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        // Square each element
        for (int i = 0; i < n; i++) {
            result[i] = nums[i] * nums[i];
        }

        // Sort the squared array
        Arrays.sort(result);

        return result;
    }

    private static int[] optimal(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];

        int left = 0;
        int right = n - 1;
        int pos = n - 1;

        while(left <= right) {
            int leftSquare = nums[left] * nums[left];
            int rightSquare = nums[right] * nums[right];

            if(leftSquare > rightSquare) {
                res[pos] = leftSquare;
                left++;
            } else {
                res[pos] = rightSquare;
                right--;
            }
            pos--;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(sortedSquares(new int[] {-4,-1,0,3,10})));
        System.out.println(Arrays.toString(sortedSquares(new int[] {-7,-3,2,3,11})));
        System.out.println();
        System.out.println(Arrays.toString(optimal(new int[] {-4,-1,0,3,10})));
        System.out.println(Arrays.toString(optimal(new int[] {-7,-3,2,3,11})));
    }
}
