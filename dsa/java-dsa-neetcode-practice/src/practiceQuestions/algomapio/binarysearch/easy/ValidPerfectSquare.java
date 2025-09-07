package practiceQuestions.algomapio.binarysearch.easy;

public class ValidPerfectSquare {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Determine if a given number is a perfect square (i.e., the square of an integer).
     * Initialize two pointers: left to 1 and right to the input number num.
     * While left is less than or equal to right, compute the middle point m as the integer average of left and right.
     * Calculate m_squared as m times m.
     * If m_squared equals num, return True.
     * If m_squared is less than num, adjust left to m + 1 to search the right half.
     * If m_squared is greater than num, adjust right to m - 1 to search the left half.
     * Return False if no perfect square is found.
     * */

    private static boolean solution(int num) {
        int left = 1;
        int right = num;

        while(left <= right) {
            int mid = (left + right) / 2;
            long midSquared = (long) mid * mid; // use long to prevent overflow

            if(num == midSquared) {
                return true;
            } else if(midSquared < num) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        System.out.println(solution(626));
    }


}


/**
 * Detailed Explanation
 * Understanding the Problem: Valid Perfect Square
 * The “Valid Perfect Square” problem asks whether a given positive integer num is a perfect square. In other words, we need to determine if there exists an integer x such that x * x == num.
 *
 * Example:
 *
 * num = 16 → Output: true (since 4 * 4 = 16)
 * num = 14 → Output: false
 * Why This Problem Matters
 * This problem helps you practice binary search in a mathematical context, reinforcing how to apply divide-and-conquer techniques outside of traditional array-based problems. It also encourages thinking about precision, overflow, and integer arithmetic.
 *
 * Optimal Approach: Binary Search Over the Range of Integers
 * Since the square root of num must lie between 1 and num (inclusive), we can use binary search to check if there exists an integer whose square equals num. Instead of checking every integer sequentially, we divide the range in half with each step.
 *
 * Steps:
 * Initialize two pointers: left = 1 and right = num.
 * While left <= right:
 * Compute the midpoint: mid = Math.floor((left + right) / 2).
 * Calculate mid * mid.
 * If mid * mid == num, return true.
 * If mid * mid < num, the square is too small → move left = mid + 1.
 * If mid * mid > num, the square is too large → move right = mid - 1.
 * If the loop ends with no match, return false.
 * Example Walkthrough
 * Input: num = 25
 *
 * left = 1, right = 25 → mid = 13 → 13² = 169 → too big → right = 12
 * mid = 6 → 6² = 36 → too big → right = 5
 * mid = 3 → 3² = 9 → too small → left = 4
 * mid = 4 → 4² = 16 → too small → left = 5
 * mid = 5 → 5² = 25 → match found → return true
 * Time and Space Complexity
 * Time Complexity: O(log n), because we divide the range of possible answers by 2 in each step.
 * Space Complexity: O(1), using only constant extra memory.
 *
 * Edge Cases to Consider
 * num = 1 → should return true
 * num = 0 → problem typically restricts to positive integers, but should handle gracefully
 * Large numbers → avoid integer overflow by using long integers or safe arithmetic in your language
 * Conclusion
 * The “Valid Perfect Square” problem is a great demonstration of how binary search can be applied to numerical properties rather than sorted data structures. This technique is both time-efficient and elegant, making it ideal for problems where brute-force iteration is too slow.
 * */