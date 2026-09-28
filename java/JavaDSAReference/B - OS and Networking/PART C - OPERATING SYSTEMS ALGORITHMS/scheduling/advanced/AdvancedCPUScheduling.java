package os.scheduling.advanced;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

/**
 * ADVANCED CPU SCHEDULING
 * 
 * 1. LOTTERY SCHEDULING:
 * A probabilistic scheduling algorithm. Processes are given "tickets" based on priority.
 * The OS picks a random number (winning ticket). Whoever holds it gets the CPU.
 * 
 * 2. COMPLETELY FAIR SCHEDULER (CFS):
 * The actual scheduler used in modern Linux! 
 * It tracks "Virtual Runtime" (vruntime). Instead of a standard queue, it uses a 
 * Red-Black Tree ordered by vruntime.
 * The process with the SMALLEST vruntime gets the CPU next.
 * High priority processes have their vruntime grow SLOWER, so they get picked more often.
 */
public class AdvancedCPUScheduling {

    static class Process {
        String id;
        int burstTime;
        int remainingTime;
        
        int tickets;
        
        double vruntime;
        int niceValue; // -20 (highest priority) to 19 (lowest priority)

        Process(String id, int burstTime, int tickets, int niceValue) {
            this.id = id;
            this.burstTime = burstTime;
            this.remainingTime = burstTime;
            this.tickets = tickets;
            this.niceValue = niceValue;
            this.vruntime = 0;
        }
    }

    public static void simulateLottery(List<Process> processes) {
        System.out.println("--- LOTTERY SCHEDULING ---");
        int totalTickets = processes.stream().mapToInt(p -> p.tickets).sum();
        Random rand = new Random();
        
        int completed = 0;
        int quantum = 2; 

        while (completed < processes.size()) {
            int winningTicket = rand.nextInt(totalTickets);
            int sum = 0;
            Process winner = null;

            for (Process p : processes) {
                if (p.remainingTime == 0) continue;
                sum += p.tickets;
                if (sum > winningTicket) {
                    winner = p;
                    break;
                }
            }

            if (winner != null) {
                int runTime = Math.min(winner.remainingTime, quantum);
                winner.remainingTime -= runTime;
                System.out.println("Winner: " + winner.id + " (ran for " + runTime + "). Remaining: " + winner.remainingTime);
                
                if (winner.remainingTime == 0) {
                    totalTickets -= winner.tickets; 
                    completed++;
                }
            }
        }
        System.out.println();
    }

    public static void simulateCFS(List<Process> processes) {
        System.out.println("--- COMPLETELY FAIR SCHEDULER (CFS) ---");
        
        TreeMap<Double, Process> rbtree = new TreeMap<>();
        
        double offset = 0.0001; 
        for (Process p : processes) {
            rbtree.put(p.vruntime + offset++, p);
        }

        int quantum = 2; 
        
        while (!rbtree.isEmpty()) {
            Double smallestKey = rbtree.firstKey();
            Process p = rbtree.remove(smallestKey);
            
            int runTime = Math.min(p.remainingTime, quantum);
            p.remainingTime -= runTime;
            
            // In Linux, weight is roughly 1024 / (1.25 ^ nice)
            // Lower nice = higher weight. High weight = slower vruntime growth!
            double weight = 1024.0 / Math.pow(1.25, p.niceValue);
            p.vruntime += (runTime * (1024.0 / weight)); 
            
            System.out.printf("Ran %-12s for %d. New vruntime: %5.2f. Remaining: %d\n", 
                               p.id, runTime, p.vruntime, p.remainingTime);

            if (p.remainingTime > 0) {
                rbtree.put(p.vruntime + (Math.random() * 0.0001), p); 
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        List<Process> p1 = new ArrayList<>();
        p1.add(new Process("P1", 8, 100, 0)); // High Priority tickets
        p1.add(new Process("P2", 4, 10,  0)); // Low Priority tickets
        simulateLottery(p1);

        List<Process> p2 = new ArrayList<>();
        // P1 nice=0 (normal), P2 nice=-5 (high priority), P3 nice=5 (low priority)
        p2.add(new Process("P1 (Normal)", 6, 0, 0));
        p2.add(new Process("P2 (High)",   6, 0, -5)); 
        p2.add(new Process("P3 (Low)",    6, 0, 5));
        simulateCFS(p2);
    }
}
