package practiceQuestions.algomapio.binarysearch.easy;

public class BinarySearch {

    private static int bruteForce(int[] nums, int target) {
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    private static int optimal(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while(left <= right) {
            int mid = left + (right - left) / 2;

            if(nums[mid] == target) {
                return mid;
            } else if(nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return -1;
    }

    private static int rec(int[] nums, int target) {
        return binarySearchRecursive(nums, target, 0, nums.length - 1);
    }
    
    private static int binarySearchRecursive(int[] nums, int target, int left, int right) {
        if (left > right) {
            return -1; // Base case: element not found
        }
        
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            return mid; // Base case: element found
        } else if (nums[mid] > target) {
            return binarySearchRecursive(nums, target, left, mid - 1); // Search left half
        } else {
            return binarySearchRecursive(nums, target, mid + 1, right); // Search right half
        }
    }
}
