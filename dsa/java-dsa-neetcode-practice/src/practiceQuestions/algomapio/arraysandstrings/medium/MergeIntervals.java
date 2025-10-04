package practiceQuestions.algomapio.arraysAndStrings.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MergeIntervals {

    private static int[][] solution(int[][] intervals) {
        if(intervals.length == 0) return new int[0][0];

        // sort intervals based on the starting time
        // Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        List<int[]> merged = new ArrayList<>();
        for(int[] interval: intervals) {
           // if the merged list is empty or there is no overlap
           if(merged.isEmpty() || merged.get(merged.size() - 1)[1] < interval[0]) {
               merged.add(interval);
           } else {
               // there is no overlap, merge the intervals
               merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size() - 1)[1], interval[1]);
           }
        }

        return merged.toArray(new int[merged.size()][]);
    }

    public static void main(String[] args) {
        int[][] arr = { {1, 3}, {2, 6}, {8, 10}, {15, 18} };
        int[][] result = solution(arr);
        
        System.out.print("Merged Intervals: ");
        for (int i = 0; i < result.length; i++) {
            System.out.print(Arrays.toString(result[i]));
            if (i < result.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Merge Intervals
 * The “Merge Intervals” problem is a classic algorithmic challenge that involves taking a list of intervals, each defined by a start and end time, and combining any that overlap. The goal is to return a list of mutually exclusive (non-overlapping) intervals that covers the same ranges.
 *
 * For example, given [[1, 3], [2, 6], [8, 10], [15, 18]], the output should be [[1, 6], [8, 10], [15, 18]]. This is because [1, 3] and [2, 6] overlap and can be merged into [1, 6], while the others remain untouched.
 *
 * Why This Problem Is Important
 * This problem is essential in many real-world applications, such as calendar scheduling, CPU task scheduling, genomic range queries, and spatial data indexing. Understanding how to efficiently merge intervals teaches you about sorting, greedy techniques, and handling ranges in ordered data structures.
 *
 * Core Insight: Sort Before You Merge
 * The key insight is that intervals must be processed in a sorted order based on their start times. Sorting allows us to linearly scan and detect overlaps without checking every possible pair. Once sorted, we can use a greedy strategy to decide whether to extend an existing interval or start a new one.
 *
 * Step-by-Step Algorithm
 * Sort the intervals in ascending order based on their start values. This guarantees that we handle earlier intervals first.
 * Initialize an empty list called merged that will contain the final merged intervals.
 * Iterate through the sorted intervals one by one:
 * If merged is empty, or the current interval does not overlap with the last interval in merged, simply append the current interval.
 * Otherwise, there is an overlap. Update the end of the last interval in merged to be the maximum of its current end and the end of the current interval.
 * Return the merged list after processing all intervals.
 * Example Walkthrough
 * Input: [[1, 4], [4, 5]]
 *
 * First, sort: already sorted in this case.
 * Start with [1, 4], add to merged.
 * Next is [4, 5]. Since 4 ≤ 4, they overlap — merge them into [1, 5].
 * Final result: [[1, 5]]
 * Another input: [[1, 3], [2, 6], [8, 10], [15, 18]]
 *
 * Sort: already sorted.
 * Merge [1, 3] and [2, 6] → [1, 6]
 * [8, 10] does not overlap with [1, 6] → add as-is
 * [15, 18] is also non-overlapping → add
 * Final result: [[1, 6], [8, 10], [15, 18]]
 * Alternative Approaches
 * Though the greedy approach is optimal and elegant, there are other less efficient or less clean ways:
 *
 * Brute force pairwise comparison: Check all pairs of intervals for overlap and merge accordingly. This requires complex bookkeeping and results in O(n²) time.
 * Using a separate stack: Some variations use a stack to track merges, but the logic is equivalent to using a result list and more memory-heavy.
 * Time and Space Complexity
 * Time Complexity: O(n log n) — This comes from sorting the intervals. The merge process afterward is linear.
 * Space Complexity: O(n) — Required for storing the output list.
 *
 * Edge Cases to Consider
 * An empty list of intervals → return an empty list.
 * Only one interval → return it as-is.
 * All intervals are disjoint → output is the same as input.
 * All intervals overlap into one big range → output is a single interval.
 * Conclusion
 * The “Merge Intervals” problem is an elegant example of how sorting followed by a single pass can simplify what would otherwise be a complex task. It's a perfect introduction to greedy algorithms and interval processing, with applications across scheduling, memory allocation, and range simplification in real-world systems.
 * */