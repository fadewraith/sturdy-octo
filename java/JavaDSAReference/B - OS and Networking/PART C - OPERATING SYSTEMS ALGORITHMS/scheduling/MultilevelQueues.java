package os.scheduling;

import java.util.LinkedList;
import java.util.Queue;

/**
 * MULTILEVEL QUEUE (MLQ) & MULTILEVEL FEEDBACK QUEUE (MLFQ)
 * 
 * 1. MULTILEVEL QUEUE:
 * Partitions the ready queue into several separate queues based on process type 
 * (e.g., System processes vs User processes).
 * Each queue has its own scheduling algorithm.
 * Processes CANNOT move between queues! Higher priority queues strictly preempt lower ones.
 * 
 * 2. MULTILEVEL FEEDBACK QUEUE (MLFQ):
 * Processes CAN move between queues!
 * If a process uses too much CPU time, it is demoted to a lower priority queue.
 * This naturally favors I/O bound and interactive processes.
 */
public class MultilevelQueues {

    static class Process {
        String id;
        int remainingTime;
        int queueLevel; // 1 = High Priority, 2 = Low Priority (Only used in MLQ)

        Process(String id, int time, int queueLevel) {
            this.id = id;
            this.remainingTime = time;
            this.queueLevel = queueLevel;
        }
    }

    /**
     * MULTILEVEL QUEUE SCHEDULING (MLQ)
     * Static queues. Processes never change queues.
     * System processes in Q1 run before User processes in Q2.
     */
    public static void simulateMLQ() {
        System.out.println("--- MULTILEVEL QUEUE SCHEDULING (MLQ) ---");
        
        Queue<Process> q1 = new LinkedList<>(); // High Priority (System)
        Queue<Process> q2 = new LinkedList<>(); // Low Priority (User)

        q1.add(new Process("P1 (System)", 4, 1));
        q2.add(new Process("P2 (User)", 6, 2));
        q2.add(new Process("P3 (User)", 2, 2));
        
        int time = 0;

        // While there is any work to do
        while (!q1.isEmpty() || !q2.isEmpty()) {
            
            // Q1 strictly preempts Q2!
            if (!q1.isEmpty()) {
                Process p = q1.poll();
                time += p.remainingTime; // Assuming FCFS for Q1
                System.out.printf("Time %2d: %s ran in Q1 to completion.\n", time, p.id);
            } 
            else if (!q2.isEmpty()) {
                Process p = q2.poll();
                // Assuming Round Robin (Quantum=2) for Q2
                int runTime = Math.min(p.remainingTime, 2);
                p.remainingTime -= runTime;
                time += runTime;
                
                System.out.printf("Time %2d: %s ran in Q2 for %d. Remaining: %d\n", time, p.id, runTime, p.remainingTime);
                
                if (p.remainingTime > 0) {
                    q2.add(p); // Put back in SAME queue
                }
            }
        }
        System.out.println();
    }

    /**
     * MULTILEVEL FEEDBACK QUEUE (MLFQ)
     * Dynamic queues. Processes DEMOTED if they take too long!
     */
    public static void simulateMLFQ() {
        System.out.println("--- MULTILEVEL FEEDBACK QUEUE (MLFQ) ---");
        
        Queue<Process> q1 = new LinkedList<>();
        Queue<Process> q2 = new LinkedList<>();
        Queue<Process> q3 = new LinkedList<>(); // FCFS

        // All processes start at highest priority queue in MLFQ
        q1.add(new Process("P1", 10, 1)); 
        q1.add(new Process("P2", 3, 1));  
        
        int time = 0;

        while (!q1.isEmpty() || !q2.isEmpty() || !q3.isEmpty()) {
            
            if (!q1.isEmpty()) {
                Process p = q1.poll();
                int quantum = 2;
                int runTime = Math.min(p.remainingTime, quantum);
                p.remainingTime -= runTime;
                time += runTime;
                System.out.printf("Time %2d: %s ran in Q1 for %d. Remaining: %d\n", time, p.id, runTime, p.remainingTime);
                
                if (p.remainingTime > 0) q2.add(p); // Demote!
            } 
            else if (!q2.isEmpty()) {
                Process p = q2.poll();
                int quantum = 4;
                int runTime = Math.min(p.remainingTime, quantum);
                p.remainingTime -= runTime;
                time += runTime;
                System.out.printf("Time %2d: %s ran in Q2 for %d. Remaining: %d\n", time, p.id, runTime, p.remainingTime);
                
                if (p.remainingTime > 0) q3.add(p); // Demote to background FCFS queue!
            }
            else if (!q3.isEmpty()) {
                Process p = q3.poll();
                time += p.remainingTime;
                System.out.printf("Time %2d: %s ran in Q3 (FCFS) for %d to COMPLETION.\n", time, p.id, p.remainingTime);
                p.remainingTime = 0;
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        simulateMLQ();
        simulateMLFQ();
    }
}
