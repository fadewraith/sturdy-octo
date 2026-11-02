package algorithms.intervals;

import java.util.Arrays;
import java.util.Comparator;

/**
 * INTERVAL PROBLEMS
 *
 * WHAT IT IS:
 * Dealing with arrays where each element represents an interval `[start, end]`
 * (closed intervals: [1, 3] and [3, 5] touch at 3, so they OVERLAP).
 *
 * WHEN TO USE THIS:
 * - Calendar scheduling, merging overlapping meeting times, booking rooms,
 *   reserving resources, finding free time between busy blocks.
 *
 * STRATEGY:
 * Almost every interval problem starts by SORTING - usually by START time,
 * but the greedy "keep as many as possible" problems sort by END time.
 *
 * 1. Merge Intervals (sort by start): if the current interval's start is <= the
 *    previous interval's end, they overlap -> extend the previous end to
 *    `max(prev.end, current.end)`.
 *
 * 2. Insert Interval (input already sorted, non-overlapping) in O(N), no re-sort:
 *    a) Copy all intervals that end BEFORE the new one starts.
 *    b) Merge all overlapping intervals into the new one (min start, max end).
 *    c) Copy all remaining intervals.
 *
 * 3. Non-overlapping Intervals (fewest removals) - sort by END time:
 *    Greedy = activity selection. Always keep the interval that finishes first,
 *    because it leaves the most room for the rest. Removals = N - kept.
 *    (Here touching intervals [1,2] and [2,3] are allowed to stay together.)
 *
 * 4. Meeting Rooms I: can one person attend all meetings? Sort by start and check
 *    that no meeting starts before the previous one ends.
 *
 * 5. Meeting Rooms II (minimum rooms) - "sweep line" / two sorted arrays:
 *    Sort all starts and all ends separately. Walk the starts; every start needs a
 *    room, but if the earliest unfinished end is <= this start, that room is free
 *    again. The max number of rooms in use at once is the answer.
 *    (A meeting ending at 10 and one starting at 10 can share a room.)
 *
 * 6. Interval List Intersections: two sorted, disjoint lists. Two pointers:
 *    overlap = [max(starts), min(ends)] if non-empty; advance whichever list's
 *    interval ends first (it can't overlap anything further).
 *
 * 7. Minimum Arrows to Burst Balloons - sort by END:
 *    Shoot an arrow at the first end; it bursts every balloon that starts at or
 *    before that point. Only shoot again when a balloon starts after the last arrow.
 *
 * COMPLEXITY:
 * Time: O(N log N) for anything that sorts, O(N) for Insert, O(N + M) for Intersection.
 * Space: O(N) for the result / sorted copies.
 */
public class IntervalProblems {

    public static class Interval {
        public int start, end;
        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
        @Override
        public String toString() {
            return "[" + start + ", " + end + "]";
        }
    }

    // Integer.compare instead of `a.start - b.start`: subtraction overflows for huge values
    private static final Comparator<Interval> BY_START = (a, b) -> Integer.compare(a.start, b.start);
    // Ties on end: earlier start first, so a zero-length [12, 12] comes after [8, 12]
    // and both can be kept (the other order would wrongly drop [8, 12]).
    private static final Comparator<Interval> BY_END = (a, b) ->
            a.end != b.end ? Integer.compare(a.end, b.end) : Integer.compare(a.start, b.start);

    // Works on copies so the caller's array and Interval objects are never modified
    private static Interval[] sortedCopy(Interval[] intervals, Comparator<Interval> order) {
        Interval[] copy = new Interval[intervals.length];
        for (int i = 0; i < intervals.length; i++) copy[i] = new Interval(intervals[i].start, intervals[i].end);
        Arrays.sort(copy, order);
        return copy;
    }

    /**
     * 1. MERGE INTERVALS - O(N log N)
     */
    public static Interval[] merge(Interval[] intervals) {
        if (intervals.length == 0) return new Interval[0];
        Interval[] sorted = sortedCopy(intervals, BY_START);

        Interval[] merged = new Interval[sorted.length];
        int count = 0;
        merged[0] = sorted[0];

        for (int i = 1; i < sorted.length; i++) {
            Interval current = sorted[i];
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

    /**
     * 2. INSERT INTERVAL - O(N)
     * `intervals` must be sorted by start and non-overlapping.
     */
    public static Interval[] insert(Interval[] intervals, Interval newInterval) {
        Interval[] result = new Interval[intervals.length + 1];
        int count = 0, i = 0, n = intervals.length;

        // a) everything that ends before the new interval starts
        while (i < n && intervals[i].end < newInterval.start) {
            result[count++] = new Interval(intervals[i].start, intervals[i].end);
            i++;
        }
        // b) everything that overlaps gets swallowed into the new interval
        int start = newInterval.start, end = newInterval.end;
        while (i < n && intervals[i].start <= end) {
            start = Math.min(start, intervals[i].start);
            end = Math.max(end, intervals[i].end);
            i++;
        }
        result[count++] = new Interval(start, end);
        // c) everything after
        while (i < n) {
            result[count++] = new Interval(intervals[i].start, intervals[i].end);
            i++;
        }
        return Arrays.copyOf(result, count);
    }

    /**
     * 3. NON-OVERLAPPING INTERVALS - minimum number to remove so the rest don't overlap.
     * Touching intervals ([1,2] and [2,3]) count as non-overlapping here. O(N log N)
     */
    public static int eraseOverlapIntervals(Interval[] intervals) {
        if (intervals.length == 0) return 0;
        Interval[] sorted = sortedCopy(intervals, BY_END);

        int kept = 1;
        int lastEnd = sorted[0].end;
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i].start >= lastEnd) { // fits after the last kept interval
                kept++;
                lastEnd = sorted[i].end;
            }
        }
        return sorted.length - kept;
    }

    /**
     * 4. MEETING ROOMS I - can one person attend every meeting?
     * A meeting ending at 10 and one starting at 10 do not clash. O(N log N)
     */
    public static boolean canAttendAll(Interval[] meetings) {
        Interval[] sorted = sortedCopy(meetings, BY_START);
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i].start < sorted[i - 1].end) return false;
        }
        return true;
    }

    /**
     * 5. MEETING ROOMS II - minimum number of rooms. O(N log N)
     */
    public static int minMeetingRooms(Interval[] meetings) {
        int n = meetings.length;
        int[] starts = new int[n], ends = new int[n];
        for (int i = 0; i < n; i++) {
            starts[i] = meetings[i].start;
            ends[i] = meetings[i].end;
        }
        Arrays.sort(starts);
        Arrays.sort(ends);

        int rooms = 0, maxRooms = 0, e = 0;
        for (int s = 0; s < n; s++) {
            // free every room whose meeting ended by now
            while (ends[e] <= starts[s]) {
                rooms--;
                e++;
            }
            rooms++;
            maxRooms = Math.max(maxRooms, rooms);
        }
        return maxRooms;
    }

    /**
     * 6. INTERVAL LIST INTERSECTIONS - both lists sorted and disjoint. O(N + M)
     */
    public static Interval[] intersection(Interval[] a, Interval[] b) {
        Interval[] result = new Interval[a.length + b.length];
        int count = 0, i = 0, j = 0;
        while (i < a.length && j < b.length) {
            int lo = Math.max(a[i].start, b[j].start);
            int hi = Math.min(a[i].end, b[j].end);
            if (lo <= hi) result[count++] = new Interval(lo, hi);
            // the one that ends first can't overlap anything else in the other list
            if (a[i].end < b[j].end) i++;
            else j++;
        }
        return Arrays.copyOf(result, count);
    }

    /**
     * 7. MINIMUM ARROWS TO BURST BALLOONS - each balloon is [xStart, xEnd];
     * an arrow at x bursts every balloon with start <= x <= end. O(N log N)
     */
    public static int minArrows(Interval[] balloons) {
        if (balloons.length == 0) return 0;
        Interval[] sorted = sortedCopy(balloons, BY_END);

        int arrows = 1;
        int arrowAt = sorted[0].end;
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i].start > arrowAt) { // the last arrow misses this balloon
                arrows++;
                arrowAt = sorted[i].end;
            }
        }
        return arrows;
    }

    private static Interval[] of(int[][] pairs) {
        Interval[] res = new Interval[pairs.length];
        for (int i = 0; i < pairs.length; i++) res[i] = new Interval(pairs[i][0], pairs[i][1]);
        return res;
    }

    public static void main(String[] args) {
        System.out.println("--- INTERVAL PROBLEMS DEMO ---");

        Interval[] intervals = of(new int[][]{{1, 3}, {8, 10}, {2, 6}, {15, 18}});
        System.out.println("Original Intervals: " + Arrays.toString(intervals));
        System.out.println("Merged Intervals:   " + Arrays.toString(merge(intervals)));
        // Expected: [[1, 6], [8, 10], [15, 18]]
        System.out.println("Original unchanged: " + Arrays.toString(intervals));

        Interval[] sorted = of(new int[][]{{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}});
        System.out.println("\nInsert [4, 8] into " + Arrays.toString(sorted) + ":");
        System.out.println("  " + Arrays.toString(insert(sorted, new Interval(4, 8))));
        // Expected: [[1, 2], [3, 10], [12, 16]]

        Interval[] overlapping = of(new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 3}});
        System.out.println("\nRemovals to make " + Arrays.toString(overlapping) + " non-overlapping: "
                + eraseOverlapIntervals(overlapping)); // Expected: 1 (remove [1, 3])

        Interval[] meetings = of(new int[][]{{0, 30}, {5, 10}, {15, 20}});
        System.out.println("\nMeetings " + Arrays.toString(meetings));
        System.out.println("  Can attend all? " + canAttendAll(meetings));  // false
        System.out.println("  Rooms needed:   " + minMeetingRooms(meetings)); // 2

        Interval[] a = of(new int[][]{{0, 2}, {5, 10}, {13, 23}, {24, 25}});
        Interval[] b = of(new int[][]{{1, 5}, {8, 12}, {15, 24}, {25, 26}});
        System.out.println("\nIntersection of two lists: " + Arrays.toString(intersection(a, b)));
        // Expected: [[1, 2], [5, 5], [8, 10], [15, 23], [24, 24], [25, 25]]

        Interval[] balloons = of(new int[][]{{10, 16}, {2, 8}, {1, 6}, {7, 12}});
        System.out.println("\nArrows for balloons " + Arrays.toString(balloons) + ": " + minArrows(balloons)); // 2
    }
}
