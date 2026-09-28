package algorithms.greedy;

import java.util.Arrays;

/**
 * ACTIVITY SELECTION (Interval Scheduling)
 * 
 * WHAT IT IS:
 * Finding the maximum number of non-overlapping activities/intervals that can 
 * be scheduled in a single resource (e.g., a meeting room).
 * 
 * STRATEGY (Greedy):
 * 1. Sort the activities strictly by their END TIME (earliest finish time first).
 * 2. Pick the first activity.
 * 3. Iterate through the rest. If the start time of the next activity is >= the 
 *    end time of the previously picked activity, pick it!
 * 
 * WHY SORT BY END TIME?
 * An activity that ends earlier frees up the resource sooner, leaving the maximum 
 * amount of continuous time available for future activities.
 * 
 * COMPLEXITY:
 * Time: O(N log N) for sorting, O(N) to iterate = O(N log N).
 * Space: O(1)
 */
public class ActivitySelection {

    static class Activity implements Comparable<Activity> {
        int start, end;
        Activity(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Activity other) {
            return this.end - other.end; // Sort by END time
        }
    }

    public static void selectMaxActivities(Activity[] activities) {
        if (activities == null || activities.length == 0) return;

        // 1. Sort by end time
        Arrays.sort(activities);

        System.out.println("Selected Activities:");
        
        // 2. The first activity is always selected
        int i = 0;
        System.out.println("[" + activities[i].start + ", " + activities[i].end + "]");

        // 3. Iterate through the rest
        for (int j = 1; j < activities.length; j++) {
            // If this activity starts AFTER or WHEN the previous one finished
            if (activities[j].start >= activities[i].end) {
                System.out.println("[" + activities[j].start + ", " + activities[j].end + "]");
                i = j; // Update the previously picked activity
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- ACTIVITY SELECTION DEMO ---");
        
        Activity[] arr = {
            new Activity(1, 4),
            new Activity(3, 5),
            new Activity(0, 6),
            new Activity(5, 7),
            new Activity(3, 9),
            new Activity(5, 9),
            new Activity(6, 10),
            new Activity(8, 11),
            new Activity(8, 12),
            new Activity(2, 14),
            new Activity(12, 16)
        };

        selectMaxActivities(arr);
        // Expected: [1, 4], [5, 7], [8, 11], [12, 16] (4 activities max)
    }
}
