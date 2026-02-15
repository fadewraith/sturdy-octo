package offthecollege.l1;

import java.util.Arrays;

public class TwoSumLC167 {

    private static int[] optimal(int[] numbers, int target) {
        int n = numbers.length;

        int i = 0, j = n - 1;
        while(i <= j) {
            int sum = numbers[i] + numbers[j];
            if(sum == target) {
                return new int[] {i + 1, j + 1};
            } else if(sum < target) {
                i++;
            } else {
                j--;
            }
        }

        return new int[]{};
    }


    public static void main(String[] args) {
        System.out.println(Arrays.toString(optimal(new int[]{2,7,11,15}, 9)));
        System.out.println(Arrays.toString(optimal(new int[]{2,3,4}, 6)));
        System.out.println(Arrays.toString(optimal(new int[]{1,2}, -1)));

    }
}
