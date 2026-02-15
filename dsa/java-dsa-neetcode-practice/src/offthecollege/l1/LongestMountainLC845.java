package offthecollege.l1;

public class LongestMountainLC845 {

    private static int optimal(int[] nums) {
        int n  = nums.length;
        int up = 0, down = 0;
        int maxLen = 0;

        for(int i = 1; i < n; i++) {
            if((down > 0 && nums[i] > nums[i - 1]) || nums[i] == nums[i - 1]) {
                up = 0; down = 0;
            }

            if (nums[i] > nums[i - 1]) {
                up++;
            }
            else if (nums[i] < nums[i - 1]) {
                down++;
            }

            if (up > 0 && down > 0) {
                maxLen = Math.max(maxLen, up + down + 1);
            }

        }

        return maxLen;
    }

    /**
     * Traverse array.
     *
     * When you find a peak:
     *
     * arr[i-1] < arr[i] > arr[i+1]
     *
     *
     * Expand:
     *
     * left backward while increasing
     *
     * right forward while decreasing
     *
     * Update max length.
     *
     * Move i to right (skip processed area)
     * */

    private static int twoPointers(int[] nums) {
        int n = nums.length, maxLen = 0, i = 1;

        while(i < n - 1) {
            if(nums[i - 1] < nums[i] && nums[i] > nums[i + 1]) {
                int left = i - 1;
                int right = i + 1;

//                means the increasing slope started at index 1.
                while(left > 0 && nums[left - 1] < nums[left]) {
                    left--;
                }

                while(right < n - 1 && nums[right] > nums[right + 1]) {
                    right++;
                }

                maxLen = Math.max(maxLen, right - left + 1);

                i = right; // skip processed part
            } else {
                i++;
            }
        }

        return maxLen;
    }

    public static void main(String[] args) {

    }
}
