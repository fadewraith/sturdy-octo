package os.synchronization.primitives;

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
    private volatile boolean[] choosing;
    private volatile int[] ticket;
    private int sharedResource = 0;

    public BakeryAlgorithm(int n) {
        this.numProcesses = n;
        choosing = new boolean[n];
        ticket = new int[n];
    }

    public void lock(int id) {
        choosing[id] = true;
        
        // Find the maximum ticket currently held
        int max = 0;
        for (int i = 0; i < numProcesses; i++) {
            if (ticket[i] > max) max = ticket[i];
        }
        
        ticket[id] = max + 1; // Take the next ticket
        choosing[id] = false;
        
        // Wait for turn
        for (int j = 0; j < numProcesses; j++) {
            if (j == id) continue;
            
            // Wait while process j is choosing a ticket
            while (choosing[j]) {}
            
            // Wait while process j has a valid ticket AND 
            // (j's ticket is smaller OR (tickets are equal but j has lower ID))
            while (ticket[j] != 0 && 
                  (ticket[j] < ticket[id] || (ticket[j] == ticket[id] && j < id))) {
                // Busy wait
            }
        }
    }

    public void unlock(int id) {
        ticket[id] = 0; // Discard ticket
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
