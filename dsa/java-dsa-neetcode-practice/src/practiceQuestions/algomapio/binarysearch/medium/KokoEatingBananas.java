package practiceQuestions.algomapio.binarysearch.medium;

import java.util.Arrays;

public class KokoEatingBananas {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Find the minimum eating speed k such that Koko can eat all bananas in h hours, where each pile takes ceil(pile / k) hours.
     * Define a helper function k_works that takes a speed k and returns True if all piles can be eaten in at most h hours.
     * In k_works, compute the total hours by summing ceil(p / k) for each pile p, and return whether hours is at most h.
     * Initialize left pointer l to 1 (minimum possible speed) and right pointer r to the maximum pile size.
     * While l is less than r, compute the middle speed k as the average of l and r (integer division).
     * If k_works(k) is True, set r to k to try a lower speed; otherwise, set l to k + 1 to try a higher speed.
     * Return r as the minimum eating speed.
     * */

    // Time Complexity: O(n * log(m))
    // Space Complexity: O(1)
    private static int solution(int[] piles, int h) {
        int left = 1;
        int right = Arrays.stream(piles).max().orElse(1); // max pile

        while(left < right) {
            int mid = (left + right) / 2;

            if (canFinish(piles, h, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    private static boolean canFinish(int[] piles, int h, int k) {
        int hours = 0;
        for(int pile : piles) {
            hours += (pile + k - 1) / k; // equivalent to ceil(pile / k)
        }

        return hours <= h;
    }


}


/**
 * Detailed Explanation
 * Understanding the Problem: Koko Eating Bananas
 * The “Koko Eating Bananas” problem presents a situation where Koko, the gorilla, has a set of banana piles and a limited number of hours to eat them all. She can only eat from one pile per hour, but she eats at a fixed speed of k bananas per hour. If a pile has fewer bananas than k, she finishes it in less than an hour but the full hour still counts. Your task is to find the minimum integer value of k such that Koko can finish all the piles within the given number of hours h.
 *
 * Why This Problem Matters
 * This is a classic problem where binary search is applied over the answer space rather than a sorted array. It teaches how to efficiently minimize or maximize a feasible value under constraints, and is especially useful when linear search would be too slow due to a large search range.
 *
 * Optimal Approach: Binary Search on Eating Speed
 * The key insight is that the function which determines whether Koko can eat all bananas at a given speed is monotonic. If she can do it at speed k, then she can also do it at any speed greater than k. This means we can apply binary search to find the minimum speed that works.
 *
 * We begin by defining a helper function that checks whether a given eating speed is sufficient. For a given speed k, we calculate the total number of hours Koko would need to finish all piles. This is done by summing up the ceiling of each pile size divided by k. If the total number of hours is less than or equal to h, then k is a feasible speed.
 *
 * The search range for the speed begins with 1 as the lowest possible value and the size of the largest pile as the upper bound, since Koko would never need to eat faster than the largest pile size in one hour. We use binary search within this range. In each step, we calculate the midpoint and check if it is a valid speed using our helper function. If the current speed works, we search for a lower speed by updating the upper bound. If it doesn't work, we try a higher speed by updating the lower bound. Eventually, the search converges on the minimum valid eating speed.
 *
 * Example
 * Suppose the piles are [3, 6, 7, 11] and Koko has 8 hours. If she eats at a speed of 4 bananas per hour, the time needed is 1 hour for the first pile, 2 hours for the second, 2 hours for the third, and 3 hours for the last. This totals exactly 8 hours, which is valid. But since we are looking for the smallest possible speed that allows her to finish, binary search will continue to test lower values until it finds the minimum feasible k.
 *
 * Time and Space Complexity
 * The time complexity is O(n log m), where n is the number of piles and m is the size of the largest pile. This is because for each iteration of the binary search (which takes log m steps), we iterate through all piles to check if the speed works. The space complexity is O(1), as we only use a few variables for tracking indices and computing totals.
 *
 * Conclusion
 * The “Koko Eating Bananas” problem is an excellent example of how to apply binary search to optimize numeric values instead of searching within arrays. By recognizing the structure of the solution space and using a helper function to validate each candidate speed, we arrive at a highly efficient solution that is both elegant and scalable for large inputs.
 * */