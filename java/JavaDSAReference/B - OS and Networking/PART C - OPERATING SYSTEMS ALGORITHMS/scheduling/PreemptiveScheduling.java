package os.scheduling;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * PREEMPTIVE CPU SCHEDULING
 * 
 * 1. SRTF (Shortest Remaining Time First - Preemptive SJF):
 * At every single time unit, the OS checks the ready queue. If a newly arrived 
 * process has a shorter remaining burst time than the currently running process, 
 * the current process is pre-empted (kicked off the CPU)!
 * 
 * 2. PREEMPTIVE PRIORITY:
 * Same concept, but we preempt based on strictly higher priority numbers.
 */
public class PreemptiveScheduling {

    static class Process {
        String id;
        int arrivalTime;
        int burstTime;
        int remainingTime;
        int priority;
        int completionTime;

        Process(String id, int arrivalTime, int burstTime, int priority) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.remainingTime = burstTime;
            this.priority = priority;
        }

        public Process(Process p) {
            this(p.id, p.arrivalTime, p.burstTime, p.priority);
        }
    }

    public static void simulateSRTF(List<Process> processes) {
        System.out.println("--- SRTF (Preemptive SJF) ---");
        List<Process> list = new ArrayList<>();
        for (Process p : processes) list.add(new Process(p));

        int currentTime = 0;
        int completed = 0;
        int n = list.size();
        
        while (completed < n) {
            // Find process with shortest remaining time that has arrived
            Process shortest = null;
            int minRemaining = Integer.MAX_VALUE;

            for (Process p : list) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0 && p.remainingTime < minRemaining) {
                    shortest = p;
                    minRemaining = p.remainingTime;
                }
            }

            if (shortest == null) {
                currentTime++; // CPU idle
                continue;
            }

            // Run for 1 time unit (this is the preemptive check point)
            shortest.remainingTime--;
            currentTime++;

            if (shortest.remainingTime == 0) {
                shortest.completionTime = currentTime;
                completed++;
                int tat = shortest.completionTime - shortest.arrivalTime;
                int wt = tat - shortest.burstTime;
                System.out.printf("%s finished at %d. TAT: %d, WT: %d\n", shortest.id, shortest.completionTime, tat, wt);
            }
        }
        System.out.println();
    }

    public static void simulatePreemptivePriority(List<Process> processes) {
        System.out.println("--- PREEMPTIVE PRIORITY (Lower number = Higher Priority) ---");
        List<Process> list = new ArrayList<>();
        for (Process p : processes) list.add(new Process(p));

        int currentTime = 0;
        int completed = 0;
        int n = list.size();
        
        while (completed < n) {
            Process highestPriority = null;
            int minPriority = Integer.MAX_VALUE; // Lower integer is higher priority

            for (Process p : list) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0 && p.priority < minPriority) {
                    highestPriority = p;
                    minPriority = p.priority;
                }
            }

            if (highestPriority == null) {
                currentTime++;
                continue;
            }

            highestPriority.remainingTime--;
            currentTime++;

            if (highestPriority.remainingTime == 0) {
                highestPriority.completionTime = currentTime;
                completed++;
                int tat = highestPriority.completionTime - highestPriority.arrivalTime;
                int wt = tat - highestPriority.burstTime;
                System.out.printf("%s finished at %d. TAT: %d, WT: %d\n", highestPriority.id, highestPriority.completionTime, tat, wt);
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        processes.add(new Process("P1", 0, 8, 3));
        processes.add(new Process("P2", 1, 4, 1)); // Arrives at 1, will preempt P1!
        processes.add(new Process("P3", 2, 2, 4)); // Arrives at 2, shortest remaining!
        
        simulateSRTF(processes);
        simulatePreemptivePriority(processes);
    }
}
