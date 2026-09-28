package algorithms.intervals;

import java.util.Arrays;
import java.util.Comparator;

/**
 * INTERVAL PROBLEMS (Merge & Insert)
 * 
 * WHAT IT IS:
 * Dealing with 2D arrays where each sub-array represents an interval `[start, end]`.
 * 
 * WHEN TO USE THIS:
 * - Calendar scheduling, merging overlapping meeting times.
 * 
 * STRATEGY:
 * Almost every interval problem requires SORTING BY START TIME first!
 * 
 * 1. Merge Intervals: Sort by start time. Iterate through. If the current interval's 
 *    start is <= the previous interval's end, they overlap! We merge them by updating 
 *    the previous interval's end to `max(prev.end, current.end)`.
 * 
 * 2. Insert Interval: We have a sorted list of non-overlapping intervals, and we 
 *    want to insert a new one. We can do this in O(N) time without re-sorting:
 *    a) Add all intervals that end BEFORE the new one starts.
 *    b) Merge all overlapping intervals into the new one `newStart = min(starts)`, `newEnd = max(ends)`.
 *    c) Add the merged new interval.
 *    d) Add all remaining intervals.
 * 
 * COMPLEXITY:
 * Time: O(N log N) for Merge (due to sorting), O(N) for Insert.
 * Space: O(N) for the result arrays.
 */
public class IntervalProblems {

    static class Interval {
        int start, end;
        Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
        @Override
        public String toString() {
            return "[" + start + ", " + end + "]";
        }
    }

    /**
     * 1. MERGE INTERVALS
     */
    public static Interval[] merge(Interval[] intervals) {
        if (intervals.length <= 1) return intervals;

        // Sort by starting time
        Arrays.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval i1, Interval i2) {
                return i1.start - i2.start;
            }
        });

        Interval[] merged = new Interval[intervals.length];
        int count = 0;
        
        merged[0] = intervals[0];
        
        for (int i = 1; i < intervals.length; i++) {
            Interval current = intervals[i];
            Interval lastMerged = merged[count];

            // If they overlap, merge them by updating the end of the last merged interval
            if (current.start <= lastMerged.end) {
                lastMerged.end = Math.max(lastMerged.end, current.end);
            } else {
                // No overlap, simply add to the array
                count++;
                merged[count] = current;
            }
        }

        // Return a perfectly sized array
        return Arrays.copyOf(merged, count + 1);
    }

    public static void main(String[] args) {
        System.out.println("--- INTERVAL PROBLEMS DEMO ---");
        
        Interval[] intervals = {
            new Interval(1, 3),
            new Interval(8, 10),
            new Interval(2, 6),
            new Interval(15, 18)
        };
        
        System.out.println("Original Intervals: " + Arrays.toString(intervals));
        System.out.println("Merged Intervals:   " + Arrays.toString(merge(intervals)));
        // Expected: [[1, 6], [8, 10], [15, 18]]
    }
}
