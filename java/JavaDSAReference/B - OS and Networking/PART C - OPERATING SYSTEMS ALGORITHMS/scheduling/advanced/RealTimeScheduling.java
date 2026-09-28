package os.scheduling.advanced;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * REAL-TIME SCHEDULING (EDF & RMS)
 * 
 * 1. RATE MONOTONIC SCHEDULING (RMS):
 * A static priority scheduling algorithm for periodic tasks.
 * Rule: The SHORTER the period of the task, the HIGHER its static priority.
 * 
 * 2. EARLIEST DEADLINE FIRST (EDF):
 * A dynamic priority scheduling algorithm.
 * Rule: The task with the deadline closest to the current time gets the CPU.
 * EDF can achieve 100% CPU utilization, whereas RMS is bounded (~69%).
 */
public class RealTimeScheduling {

    static class Task {
        String id;
        int executionTime;
        int period;    // Time between arrivals
        int deadline;  // For EDF: Absolute deadline for the current job
        
        int remainingTime;
        int nextArrival;

        Task(String id, int executionTime, int period) {
            this.id = id;
            this.executionTime = executionTime;
            this.period = period;
            
            this.remainingTime = 0;
            this.nextArrival = 0;
            this.deadline = 0;
        }
    }

    public static void simulateEDF(List<Task> tasks, int totalTimeToSimulate) {
        System.out.println("--- EARLIEST DEADLINE FIRST (EDF) ---");
        
        for (int time = 0; time < totalTimeToSimulate; time++) {
            
            // 1. Release new jobs if they reached their period
            for (Task t : tasks) {
                if (time == t.nextArrival) {
                    t.remainingTime = t.executionTime;
                    t.deadline = time + t.period; // Absolute deadline
                    t.nextArrival += t.period;
                }
            }
            
            // 2. Find the ready task with the EARLIEST absolute deadline
            Task active = null;
            int earliest = Integer.MAX_VALUE;
            
            for (Task t : tasks) {
                if (t.remainingTime > 0 && t.deadline < earliest) {
                    earliest = t.deadline;
                    active = t;
                }
            }
            
            // 3. Execute for 1 tick
            if (active != null) {
                active.remainingTime--;
                System.out.println("Time " + time + ": Running " + active.id + " (Deadline: " + active.deadline + ")");
                
                // Missed deadline check
                if (active.remainingTime > 0 && time + 1 >= active.deadline) {
                    System.out.println("  [!] DEADLINE MISSED FOR " + active.id);
                }
            } else {
                System.out.println("Time " + time + ": CPU Idle");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        List<Task> tasks = new ArrayList<>();
        // Task 1: needs 1 unit of CPU every 4 units of time
        tasks.add(new Task("T1", 1, 4));
        // Task 2: needs 2 units of CPU every 5 units of time
        tasks.add(new Task("T2", 2, 5));
        
        simulateEDF(tasks, 12);
    }
}
