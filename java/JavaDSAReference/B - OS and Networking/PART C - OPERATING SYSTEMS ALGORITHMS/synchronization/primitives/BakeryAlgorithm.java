package os.synchronization.primitives;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * LAMPORT'S BAKERY ALGORITHM
 * 
 * WHAT IT IS:
 * Extends Peterson's Algorithm from 2 processes to N processes.
 * It uses a ticketing system, exactly like a bakery!
 * 
 * HOW IT WORKS:
 * 1. A process takes a ticket (a number strictly greater than all other current numbers).
 * 2. It waits until its ticket number is the lowest among all processes that want to enter.
 * 3. Ties are broken by process ID (lower ID wins).
 */
public class BakeryAlgorithm {

    private int numProcesses;
    // "volatile int[]" would only make the array REFERENCE volatile, not its elements,
    // so threads could spin on stale values. AtomicIntegerArray makes each element
    // read/write volatile. choosing: 1 = picking a ticket, 0 = not.
    private final AtomicIntegerArray choosing;
    private final AtomicIntegerArray ticket;
    private int sharedResource = 0;

    public BakeryAlgorithm(int n) {
        this.numProcesses = n;
        choosing = new AtomicIntegerArray(n);
        ticket = new AtomicIntegerArray(n);
    }

    public void lock(int id) {
        choosing.set(id, 1);
        
        // Find the maximum ticket currently held
        int max = 0;
        for (int i = 0; i < numProcesses; i++) {
            if (ticket.get(i) > max) max = ticket.get(i);
        }
        
        ticket.set(id, max + 1); // Take the next ticket
        choosing.set(id, 0);
        
        // Wait for turn
        for (int j = 0; j < numProcesses; j++) {
            if (j == id) continue;
            
            // Wait while process j is choosing a ticket
            while (choosing.get(j) == 1) {}
            
            // Wait while process j has a valid ticket AND 
            // (j's ticket is smaller OR (tickets are equal but j has lower ID))
            while (ticket.get(j) != 0 && 
                  (ticket.get(j) < ticket.get(id) || (ticket.get(j) == ticket.get(id) && j < id))) {
                // Busy wait
            }
        }
    }

    public void unlock(int id) {
        ticket.set(id, 0); // Discard ticket
    }

    class BakeryThread extends Thread {
        int id;
        BakeryThread(int id) { this.id = id; }
        
        public void run() {
            for (int i = 0; i < 3; i++) {
                lock(id);
                // Critical Section
                sharedResource++;
                System.out.println("Thread " + id + " in CS. Resource: " + sharedResource);
                unlock(id);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- LAMPORT'S BAKERY ALGORITHM (N-Process Mutual Exclusion) ---");
        BakeryAlgorithm bakery = new BakeryAlgorithm(3);
        
        bakery.new BakeryThread(0).start();
        bakery.new BakeryThread(1).start();
        bakery.new BakeryThread(2).start();
    }
}
