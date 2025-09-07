package practiceQuestions.algomapio.twopointers.hard;

public class TrappingRainWater {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Compute the total water trapped between bars of given heights in an array.
     * Initialize two variables, l_wall and r_wall, to 0 to track the maximum heights from the left and right.
     * Get the length n of the height array.
     * Initialize two arrays, max_left and max_right, of size n to store the maximum height to the left and right of each index.
     * Iterate through each index i from 0 to n-1:
     * Set max_left[i] to l_wall.
     * Compute the corresponding right index j as -i-1.
     * Set max_right[j] to r_wall.
     * Update l_wall to the maximum of l_wall and height[i].
     * Update r_wall to the maximum of r_wall and height[j].
     * Initialize summ to 0 to accumulate trapped water.
     * For each index i from 0 to n-1, compute the potential height pot as the minimum of max_left[i] and max_right[i].
     * Add max(0, pot - height[i]) to summ as the water trapped at index i.
     * Return summ as the total trapped water.
     * */

    private static int solution(int[] height) {
        int n = height.length;
        int[] maxLeft = new int[n];
        int[] maxRight = new int[n];

        int leftWall = 0, rightWall = 0;

        for(int i = 0; i < n; i++) {
            int j = n - i - 1;
            maxLeft[i] = leftWall;
            maxRight[j] = rightWall;
            leftWall = Math.max(leftWall, height[i]);
            rightWall = Math.max(rightWall, height[j]);
        }

        int sum = 0;
        for(int i = 0; i < n; i++) {
            int pot = Math.min(maxLeft[i], maxRight[i]);
            sum += Math.max(0, pot - height[i]);
        }

        return sum;
    }

    public static void main(String[] args) {
        // Test case 1: Example from problem statement
        int[] height1 = {0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println("Test case 1: " + solution(height1) + " (Expected: 6)");
        
        // Test case 2: Simple case with no water trapped
        int[] height2 = {1, 2, 3, 4, 5};
        System.out.println("Test case 2: " + solution(height2) + " (Expected: 0)");
        
        // Test case 3: All same height
        int[] height3 = {3, 3, 3, 3, 3};
        System.out.println("Test case 3: " + solution(height3) + " (Expected: 0)");
        
        // Test case 4: Valley shape
        int[] height4 = {5, 1, 5};
        System.out.println("Test case 4: " + solution(height4) + " (Expected: 4)");
        
        // Test case 5: Edge case with empty array
        int[] height5 = {};
        System.out.println("Test case 5: " + solution(height5) + " (Expected: 0)");
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Trapping Rain Water
 * The “Trapping Rain Water” problem asks us to compute how much water can be trapped after raining, given an elevation map where the width of each bar is 1. The bars are represented as an array of non-negative integers.
 *
 * Example:
 *
 * Input: [0,1,0,2,1,0,1,3,2,1,2,1]
 * Output: 6
 * Water is trapped between the elevation bars where dips occur. The goal is to calculate the total amount of trapped water.
 * Why This Problem Matters
 * This is a classic problem in array manipulation and precomputation. It introduces the concept of prefix maximums and suffix maximums, and also has an elegant two-pointer solution. It's a common technical interview question that tests both brute-force intuition and optimization with space/time trade-offs.
 *
 * Brute Force Concept with Precomputed Max Arrays
 * To calculate how much water can be trapped at each index, we must know the tallest bar to the left and to the right of it. The amount of water at index i is the minimum of those two heights minus the height at i itself.
 *
 * Steps:
 * Initialize arrays max_left and max_right of the same length as the input array height.
 * Traverse from left to right, filling max_left[i] as the highest bar to the left of i.
 * Traverse from right to left, filling max_right[i] as the highest bar to the right of i.
 * For each index i, compute the trapped water as min(max_left[i], max_right[i]) - height[i].
 * Add this amount to a running sum if it is positive.
 * Return the total sum.
 * Example Walkthrough
 * Input: [0,1,0,2,1,0,1,3,2,1,2,1]
 *
 * max_left = [0,1,1,2,2,2,2,3,3,3,3,3]
 * max_right = [3,3,3,3,3,3,3,3,2,2,2,1]
 * Water[i] = min(max_left[i], max_right[i]) - height[i]
 * Total trapped = 6
 * Time and Space Complexity
 * Time Complexity: O(n), where n is the length of the height array. We traverse the array three times (left max, right max, compute water).
 * Space Complexity: O(n) for storing two auxiliary arrays max_left and max_right.
 *
 * Optimal Solution (Two Pointers)
 * Instead of storing two full arrays, we can optimize space using two pointers and variables to track the current left and right maximum heights dynamically.
 *
 * Optimized Steps:
 * Initialize two pointers: left = 0 and right = height.length - 1.
 * Track left_max and right_max as the maximum seen so far from each end.
 * While left < right:
 * If height[left] < height[right]:
 * If height[left] >= left_max, update left_max.
 * Else, add left_max - height[left] to total.
 * Move left forward.
 * Else:
 * If height[right] >= right_max, update right_max.
 * Else, add right_max - height[right] to total.
 * Move right backward.
 * This results in O(n) time and O(1) space.
 *
 * Edge Cases to Consider
 * Empty array → no water can be trapped
 * All bars of same height → no dips, so no water trapped
 * Only one or two bars → not enough to trap water
 * Conclusion
 * The “Trapping Rain Water” problem is an excellent demonstration of how precomputation or two-pointer strategies can optimize time and space complexity. It’s one of the most iconic examples of converting a seemingly brute-force problem into an elegant linear-time solution.
 * */