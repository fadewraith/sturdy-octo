package os.scheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * CPU SCHEDULING ALGORITHMS
 * 
 * WHAT IT IS:
 * OS level algorithms to decide which process in the ready queue gets the CPU next.
 * 
 * STRATEGY:
 * - FCFS: Simple FIFO queue.
 * - SJF (Shortest Job First): Pick the process with the shortest burst time. Can be 
 *   preemptive (SRTF - Shortest Remaining Time First) or non-preemptive.
 * - Priority: Pick process with highest priority (lower number often means higher priority).
 * - Round Robin: Time quantum. Process runs for quantum Q, then goes to back of queue.
 * 
 * WHEN TO USE THIS:
 * - SJF minimizes average waiting time but causes starvation for long processes.
 * - Round Robin provides fairness and excellent response time for time-sharing systems.
 * - Priority scheduling requires "aging" (gradually increasing priority of old processes) 
 *   to prevent starvation.
 * 
 * PSEUDOCODE (Round Robin):
 * queue = all arrived processes
 * while (queue not empty):
 *    p = queue.pop()
 *    time_to_run = min(p.remaining_time, quantum)
 *    current_time += time_to_run
 *    p.remaining_time -= time_to_run
 *    add any newly arrived processes to queue
 *    if p.remaining_time > 0: queue.push(p)
 */
public class BasicCPUScheduling {

    static class Process {
        String id;
        int arrivalTime;
        int burstTime;
        int priority;
        
        int remainingTime;
        int completionTime;
        int waitingTime;
        int turnaroundTime;

        public Process(String id, int arrivalTime, int burstTime, int priority) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.remainingTime = burstTime;
            this.priority = priority;
        }

        // Copy constructor for resetting simulations
        public Process(Process p) {
            this(p.id, p.arrivalTime, p.burstTime, p.priority);
        }

        @Override
        public String toString() {
            return String.format("%s (AT:%d, BT:%d, Prio:%d) -> CT:%d, TAT:%d, WT:%d", 
                                 id, arrivalTime, burstTime, priority, completionTime, turnaroundTime, waitingTime);
        }
    }

    private static List<Process> copyAndSortByArrival(List<Process> processes) {
        List<Process> copy = new ArrayList<>();
        for (Process p : processes) copy.add(new Process(p));
        copy.sort(Comparator.comparingInt(p -> p.arrivalTime));
        return copy;
    }

    private static void calculateMetrics(Process p, int currentTime) {
        p.completionTime = currentTime;
        p.turnaroundTime = p.completionTime - p.arrivalTime;
        p.waitingTime = p.turnaroundTime - p.burstTime;
    }

    private static void printAverages(List<Process> processes, String algoName) {
        double avgWT = 0, avgTAT = 0;
        System.out.println("--- " + algoName + " ---");
        for (Process p : processes) {
            System.out.println(p);
            avgWT += p.waitingTime;
            avgTAT += p.turnaroundTime;
        }
        System.out.printf("Avg WT: %.2f | Avg TAT: %.2f\n\n", avgWT / processes.size(), avgTAT / processes.size());
    }

    // 1. FCFS (First Come First Serve)
    public static void simulateFCFS(List<Process> processes) {
        List<Process> list = copyAndSortByArrival(processes);
        int currentTime = 0;

        for (Process p : list) {
            if (currentTime < p.arrivalTime) {
                currentTime = p.arrivalTime; // CPU idle time
            }
            currentTime += p.burstTime;
            calculateMetrics(p, currentTime);
        }
        printAverages(list, "FCFS");
    }

    // 2. SJF (Non-Preemptive)
    public static void simulateSJF_NonPreemptive(List<Process> processes) {
        List<Process> list = copyAndSortByArrival(processes);
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.burstTime));
        List<Process> completed = new ArrayList<>();
        
        int currentTime = 0;
        int completedCount = 0;
        int n = list.size();
        
        while (completedCount < n) {
            // Add arrived processes to ready queue
            for (Process p : list) {
                if (p.arrivalTime <= currentTime && p.remainingTime == p.burstTime && !readyQueue.contains(p) && !completed.contains(p)) {
                    readyQueue.add(p);
                }
            }

            if (readyQueue.isEmpty()) {
                currentTime++;
                continue;
            }

            Process current = readyQueue.poll();
            currentTime += current.burstTime;
            current.remainingTime = 0;
            calculateMetrics(current, currentTime);
            completed.add(current);
            completedCount++;
        }
        printAverages(completed, "SJF (Non-Preemptive)");
    }

    // 3. Round Robin
    public static void simulateRoundRobin(List<Process> processes, int quantum) {
        List<Process> list = copyAndSortByArrival(processes);
        Queue<Process> readyQueue = new LinkedList<>();
        List<Process> completed = new ArrayList<>();
        
        int currentTime = list.get(0).arrivalTime;
        readyQueue.add(list.get(0));
        
        int index = 1;
        int n = list.size();

        while (!readyQueue.isEmpty()) {
            Process current = readyQueue.poll();
            
            int runTime = Math.min(current.remainingTime, quantum);
            currentTime += runTime;
            current.remainingTime -= runTime;

            // Check for new arrivals while this process was running
            while (index < n && list.get(index).arrivalTime <= currentTime) {
                readyQueue.add(list.get(index));
                index++;
            }

            if (current.remainingTime > 0) {
                readyQueue.add(current);
            } else {
                calculateMetrics(current, currentTime);
                completed.add(current);
            }

            // If queue is empty but there are more processes coming, fast forward time
            if (readyQueue.isEmpty() && index < n) {
                currentTime = list.get(index).arrivalTime;
                readyQueue.add(list.get(index));
                index++;
            }
        }
        printAverages(completed, "Round Robin (Quantum=" + quantum + ")");
    }

    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        processes.add(new Process("P1", 0, 5, 2)); // ID, Arrival, Burst, Priority
        processes.add(new Process("P2", 1, 3, 1));
        processes.add(new Process("P3", 2, 8, 3));
        processes.add(new Process("P4", 3, 6, 4));

        simulateFCFS(processes);
        simulateSJF_NonPreemptive(processes);
        simulateRoundRobin(processes, 2);
    }
}
