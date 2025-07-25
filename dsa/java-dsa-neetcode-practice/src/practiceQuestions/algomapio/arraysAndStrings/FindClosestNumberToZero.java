package practiceQuestions.algomapio.arraysAndStrings;

public class FindClosestNumberToZero {

   private static int solution(int[] nums) {

       if (nums == null || nums.length == 0) {
           throw new IllegalArgumentException("Input array cannot be null or empty");
       }

       int closest = nums[0];
       for(int x: nums) {
           if(Math.abs(x) < Math.abs(closest)) {
               closest = x;
           }
       }

       if(closest < 0 && contains(nums, Math.abs(closest))) {
           return Math.abs(closest);
       } else {
           return closest;
       }
   }

   private static boolean contains(int[] nums, int value) {
       for(int x: nums) {
           if(x == value) {
               return true;
           }
       }
       return false;
   }

    public static void main(String[] args) {
        int[] nums = { -4, -2, 5, 4, 2 };
        System.out.println(solution(nums));;
    }
}


/**
 * Given an array of integers, return the number closest to zero. If there are two equally close values (one negative and one positive), return the positive one. For example, given the input [-4, -2, 1, 4, 2], both -2 and 2 are at the same distance from zero, but the correct answer is 2.
 *
 * Algorithmic Approach
 * This problem is typically solved using a linear scan algorithm. We initialize a variable, commonly called closest, with the first value of the array. As we iterate through the array, we compare each element using absolute value comparison:
 *
 * If the absolute value of the current number is less than that of closest, update closest.
 * If the absolute values are the same, choose the positive number.
 * This logic ensures that we return the number closest to zero and correctly handle tie-breakers in favor of positive values.
 *
 *Edge Cases
 * This problem requires careful attention to detail. Common edge cases include:
 *
 * An array containing only one element
 * Presence of both x and -x
 * All elements being negative or all being positive
 * Correct handling of these edge cases ensures robustness and correctness of the algorithm.
 *
 * Use Cases and Relevance
 * This type of problem teaches key programming concepts such as comparison operations, absolute value logic, and array traversal. It is commonly used to assess fundamental algorithmic thinking in entry-level coding interviews and is relevant in real-world scenarios such as:
 *
 * Sensor data normalization
 * Financial deviation analysis
 * Error minimization logic
 * */