package offthecollege.l1;

import java.util.Arrays;

public class SortColorsLC75 {

    private static void solution(int[] nums) {
        int count0 = 0, count1 = 0, count2 = 0,  index = 0;

        for(int item : nums) {
            if(item == 0) count0++;
            if(item == 1) count1++;
            if(item == 2) count2++;
        }

        while(count0-- > 0) nums[index++] = 0;
        while(count1-- > 0) nums[index++] = 1;
        while(count2-- > 0) nums[index++] = 2;

        System.out.println(Arrays.toString(nums));
    }

    private static void sortColors(int[] nums) {
        int low = 0, mid = 0;
        int high = nums.length - 1;

        while (mid <= high) {
            if (nums[mid] == 0) {
                swap(nums, low, mid);
                low++;
                mid++;
            }
            else if (nums[mid] == 1) {
                mid++;
            }
            else { // nums[mid] == 2
                swap(nums, mid, high);
                high--;
            }
        }

        System.out.println(Arrays.toString(nums));
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }


    public static void main(String[] args) {
        solution(new int[] {2,0,2,1,1,0});
        solution(new int[] {2,0,1});
        System.out.println();
        sortColors(new int[] {2,0,2,1,1,0});
        sortColors(new int[] {2,0,1});

    }
}
