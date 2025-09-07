package practiceQuestions.algomapio.bitmanipulation.easy;

public class SingleNumber {

    // Time: O(n)
    // Space: O(1)

    private static int solution(int[] nums) {
        int a = 0;
        for(int x : nums) {
            a ^= x;
        }
        return a;
    }

    public static void main(String[] args) {
        int[] nums = {2, 3, 2, 4, 4};
        System.out.println(solution(nums));
        // write test cases
        int[] nums2 = {1};
        System.out.println(solution(nums2));
        int[] nums3 = {1, 2, 2};
        System.out.println(solution(nums3));
        int[] nums4 = {1, 2, 2, 3, 3};
        System.out.println(solution(nums4));
        int[] nums5 = {1, 2, 2, 3, 3, 4, 4};
        System.out.println(solution(nums5));
        int[] nums6 = {1, 2, 2, 3, 3, 4, 4, 5, 5};
        System.out.println(solution(nums6));
    }
}


/**
 * Detailed Explanation
 * Understanding the Single Number Problem
 * The "Single Number" problem is a popular bit manipulation challenge. You are given an array of integers where every element appears exactly twice except for one. Your task is to find the element that appears only once. The goal is to accomplish this in linear time and constant space, making the solution highly efficient and scalable.
 *
 * Key Insight: Using Bitwise XOR
 * The problem can be solved elegantly using the properties of the XOR (exclusive OR) operation:
 *
 * a ^ a = 0 – Any number XORed with itself is 0.
 * a ^ 0 = a – Any number XORed with 0 remains unchanged.
 * XOR is commutative and associative, meaning the order of operations does not matter.
 * When we XOR all elements of the array together, all numbers that appear in pairs cancel each other out, and we are left with the single number that appears only once.
 *
 * Example Walkthrough
 * Consider the array [2, 3, 2, 4, 4]. Applying XOR step-by-step:
 *
 * a = 0
 * a = a ^ 2 = 2
 * a = a ^ 3 = 1
 * a = a ^ 2 = 3
 * a = a ^ 4 = 7
 * a = a ^ 4 = 3
 *
 * The final result is 3, which is the number that appears only once.
 *
 * Time and Space Complexity
 * Time Complexity: O(n) – The array is traversed once.
 * Space Complexity: O(1) – No extra space is used aside from a single integer variable.
 * Why This Solution Is Optimal
 * Traditional approaches such as using a hash map or a set require extra memory to track occurrences. In contrast, the XOR approach is optimal in terms of both speed and memory usage. It's a prime example of how understanding bitwise operations can lead to elegant and efficient solutions.
 *
 * Conclusion
 * The Single Number problem showcases the power of bitwise operations in algorithm design. It's an essential pattern for technical interviews and is foundational for solving a variety of other XOR-based problems, such as finding missing numbers or duplicate detection.
 * */