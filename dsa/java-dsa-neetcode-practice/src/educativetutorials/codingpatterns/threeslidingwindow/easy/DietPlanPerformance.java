package educativetutorials.codingpatterns.threeslidingwindow.easy;

import java.util.Arrays;
import java.util.List;

public class DietPlanPerformance {

    private static int dietPlanPerformance(List<Integer> calories, int k, int lower, int upper) {
        // Initialize the points counter
        int points = 0;

        // Calculate the sum of the first k days (initial window)
        int currentSum = 0;
        for(int i = 0; i < k; i++) {
            currentSum += calories.get(i);
        }

        // Evaluate the initial window
        if(currentSum < lower) {
            points -= 1;
        } else if(currentSum > upper) {
            points += 1;
        }

        // Slide the window across the rest of the days
        for(int i = k; i < calories.size(); i++) {
            // Update the window sum: subtract the element that is sliding out and add the new element
            currentSum = currentSum - calories.get(i - k) + calories.get(i);

            // Evaluate the current window sum
            if(currentSum < lower) {
                points -= 1;
            } else if(currentSum > upper) {
                points += 1;
            }
        }


        // Return the total points after evaluating all windows
        return points;
    }

    // Driver code
    public static void main(String[] args) {
        // List of test cases: each test case is [calories, k, lower, upper]
        List<List<Integer>> testCases = Arrays.asList(
                Arrays.asList(3, 5, 8, 2, 6),     // Test Case 1: Mixed performance
                Arrays.asList(1, 1, 1, 1, 1),     // Test Case 2: All sums below lower limit
                Arrays.asList(10, 12, 15, 20, 25), // Test Case 3: All sums above upper limit
                Arrays.asList(5, 10, 15, 20, 25, 30), // Test Case 4: Mix of poor, normal, and good performances
                Arrays.asList(3, 8, 7, 4, 5, 6)   // Test Case 5: Sliding window with variable performance
        );

        int[] ks = {2, 2, 3, 3, 2};
        int[] lowers = {7, 5, 10, 20, 7};
        int[] uppers = {10, 10, 30, 40, 10};

        // Run each test case
        for (int i = 0; i < testCases.size(); i++) {
            System.out.println("Test Case " + (i + 1) + ":");
            System.out.print("\tcalories = [");
            String s = "";
            for (int j = 0; j < testCases.get(i).size(); j++) {
                s += testCases.get(i).get(j);
                if (j != testCases.get(i).size() - 1) {
                    s += ", ";
                }
            }
            System.out.println(s + "]");
            System.out.println("\tk = " + ks[i]);
            System.out.println("\tlower = " + lowers[i]);
            System.out.println("\tupper = " + uppers[i]);
            int result = dietPlanPerformance(testCases.get(i), ks[i], lowers[i], uppers[i]);
            System.out.println("\n\tpoints = " + result);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}

/**
 * Diet Plan Performance
 *
 * Statement:
 * A dieter consumes calories[i] calories on the i-th day.
 *
 * Given an integer k, the dieter reviews their calorie intake over every sequence
 * of k consecutive days (from calories[i] to calories[i+k-1] for all 0 <= i <= n-k).
 * For each sequence, they calculate T, the total calories consumed over those k days:
 *
 * - If T is less than lower, the dieter performs poorly and loses 1 point.
 * - If T is greater than upper, the dieter performs better and gains 1 point.
 * - If T is between lower and upper (inclusive), the dieter’s performance is normal,
 *   and their points remain the same.
 *
 * The dieter starts with zero points. Return the total points after the dieter
 * follows this routine for all calories.length days. The total points can be negative.
 *
 * Constraints:
 * 1 <= k <= calories.length <= 10^5
 * 0 <= calories[i] <= 20000
 * 0 <= lower <= upper
 *
 * Solution:
 * This problem uses a sliding window approach to efficiently compute the sum
 * of every k consecutive days.
 *
 * Steps:
 * 1. Initialize points = 0 and calculate the sum of the first k days.
 * 2. Compare the sum with lower and upper:
 *    - If sum < lower, decrement points by 1.
 *    - If sum > upper, increment points by 1.
 *    - Otherwise, no change.
 *
 * 3. Slide the window across the array:
 *    - Subtract the element going out of the window.
 *    - Add the new element coming into the window.
 *    - Update points based on the new sum using the same rules.
 *
 * 4. Continue until all windows are processed.
 *
 * 5. Return the final points.
 *
 * Time Complexity:
 * O(n), where n is the length of the calories array.
 *
 * Space Complexity:
 * O(1), constant extra space.
 */