package algorithms.greedy;

import java.util.Arrays;

/**
 * JOB SEQUENCING WITH DEADLINES
 * 
 * WHAT IT IS:
 * Given an array of jobs where every job has a deadline and an associated profit if 
 * the job is finished before the deadline. It takes exactly 1 unit of time to 
 * complete a job. Maximize total profit.
 * 
 * WHEN TO USE THIS:
 * - A classic Greedy problem where sorting drastically simplifies the solution.
 * 
 * STRATEGY (Greedy):
 * 1. Sort all jobs in DESCENDING order of profit.
 * 2. We want to do the most profitable jobs first, but we want to do them AS LATE 
 *    AS POSSIBLE (right before their deadline!) to leave the earlier time slots 
 *    open for other jobs that have tighter deadlines!
 * 3. We maintain an array of "time slots". For each job, we look at its deadline `d`.
 *    If slot `d` is empty, we schedule the job there. If it's taken, we look at `d-1`, 
 *    then `d-2`, etc., until we find an empty slot.
 * 
 * COMPLEXITY:
 * Time: O(N^2) (due to scanning the time slot array backwards). 
 *       Can be optimized to O(N log N) using a Disjoint Set (Union-Find) to jump over filled slots!
 * Space: O(N) for the time slot array.
 */
public class JobSequencing {

    static class Job implements Comparable<Job> {
        char id;
        int deadline;
        int profit;

        Job(char id, int deadline, int profit) {
            this.id = id;
            this.deadline = deadline;
            this.profit = profit;
        }

        @Override
        public int compareTo(Job other) {
            // Sort in DESCENDING order of profit
            return other.profit - this.profit;
        }
    }

    public static void printJobScheduling(Job[] jobs, int maxDeadline) {
        Arrays.sort(jobs);

        char[] result = new char[maxDeadline]; // To store result (Sequence of jobs)
        boolean[] slot = new boolean[maxDeadline]; // To keep track of free time slots

        for (int i = 0; i < maxDeadline; i++) {
            slot[i] = false;
        }

        int totalProfit = 0;

        for (int i = 0; i < jobs.length; i++) {
            // Find a free slot for this job (starting from its deadline, moving backwards)
            // Note: max capacity of result array is maxDeadline, so we cap the start index
            int deadlineIndex = Math.min(maxDeadline - 1, jobs[i].deadline - 1);
            
            for (int j = deadlineIndex; j >= 0; j--) {
                if (!slot[j]) {
                    result[j] = jobs[i].id;
                    slot[j] = true;
                    totalProfit += jobs[i].profit;
                    break; // Job scheduled! Move to next job.
                }
            }
        }

        System.out.println("Job Sequence to maximize profit: ");
        for (int i = 0; i < maxDeadline; i++) {
            if (slot[i]) {
                System.out.print(result[i] + " ");
            }
        }
        System.out.println("\nTotal Profit: " + totalProfit);
    }

    public static void main(String[] args) {
        System.out.println("--- JOB SEQUENCING WITH DEADLINES DEMO ---");
        
        Job[] jobs = {
            new Job('a', 2, 100),
            new Job('b', 1, 19),
            new Job('c', 2, 27),
            new Job('d', 1, 25),
            new Job('e', 3, 15)
        };
        
        printJobScheduling(jobs, 3);
        // Expected: c, a, e (Profit: 27 + 100 + 15 = 142)
    }
}
