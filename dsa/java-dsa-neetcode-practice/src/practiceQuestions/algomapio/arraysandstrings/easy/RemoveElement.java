package practiceQuestions.algomapio.arraysAndStrings.easy;

public class RemoveElement {

    private static int solution(int[] nums, int val) {
        int i = 0, n = nums.length;
        while(i < n) {
            if(nums[i] == val) {
                nums[i] = nums[n - 1];
                n--;
            } else {
                i++;
            }
        }
        return n;
    }
}

/**
 * Optimal Approach: Two-Pointer Technique
 *
 *
 * Consider the input nums = [3, 2, 2, 3] with val = 3. We begin with i = 0 and n = 4. The value at index 0 is 3, so we swap it with nums[3], which is also 3. We reduce n to 3 and stay at index 0. Now nums[0] is again 3, so we swap with nums[2], which is 2. Now the array is [2, 2, 3, 3] and n = 2. Finally, we increment i through the remaining valid elements. The output is 2, and the first two elements of the array are [2, 2].
 * */