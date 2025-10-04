package practiceQuestions.algomapio.arraysandstrings.medium;

public class RemoveDuplicatesFromSortedArrayII {

    private static int solution(int[] nums) {
        int j = 1, count = 1, n = nums.length;

        for(int i = 1; i < n; i++) {
            if(nums[i] == nums[i - 1]) {
                count++;
            } else {
                count = 1;
            }

            if(count <= 2) {
                nums[j] = nums[i];
                j++;
            }
        }

        int i = 0;
        while(i < j) {
            System.out.print(nums[i++]);
            if(i < j) System.out.print(", ");
        }
        System.out.println();

        return j;
    }

    public static void main(String[] args) {
        int[] nums = { 1,1,1,2,2,3 };
        System.out.println(solution(nums));
    }
}


/**
 * Implementation Details
 * We start by initializing j = 1 since the first element is always valid, and count = 1 for the first occurrence. Then, we iterate over the array from index 1 to the end:
 *
 * If nums[i] == nums[i - 1], we increment count since it's a duplicate of the previous number.
 * If nums[i] != nums[i - 1], we reset count to 1 because we've encountered a new unique value.
 * If count <= 2, the current number is allowed to stay in the array (no more than twice). So, we place it at nums[j] and increment j.
 * This logic ensures that each unique number appears at most twice in the result. The elements are placed in the first j positions of the array, forming the correct output in-place.
 * */