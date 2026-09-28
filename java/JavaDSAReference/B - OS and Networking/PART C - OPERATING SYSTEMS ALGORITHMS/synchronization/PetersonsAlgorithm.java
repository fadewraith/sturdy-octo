package os.synchronization;

/**
 * PETERSON'S ALGORITHM
 * 
 * WHAT IT IS:
 * A software-based mutual exclusion algorithm that allows exactly TWO processes 
 * to share a critical section without hardware locks.
 * 
 * HOW IT WORKS:
 * Uses a boolean array `flag` (intent to enter) and a `turn` variable (yields to the other).
 * 
 * NOTE: Modern CPUs reorder memory operations, so Peterson's Algorithm fails in 
 * standard Java without the `volatile` keyword preventing reordering.
 */
public class PetersonsAlgorithm {

    private static volatile boolean[] flag = new boolean[2];
    private static volatile int turn = 0;
    
    private static int sharedResource = 0;

    static class Process0 extends Thread {
        public void run() {
            for (int i = 0; i < 5; i++) {
                flag[0] = true;
                turn = 1; // Give turn to process 1
                
                // Busy wait! (Spinlock)
                while (flag[1] && turn == 1) { 
                    // Spin...
                }

                // Critical Section
                sharedResource++;
                System.out.println("Process 0 incremented resource to: " + sharedResource);

                // Exit Section
                flag[0] = false;
            }
        }
    }

    static class Process1 extends Thread {
        public void run() {
            for (int i = 0; i < 5; i++) {
                flag[1] = true;
                turn = 0; // Give turn to process 0
                
                while (flag[0] && turn == 0) { 
                    // Spin...
                }

                // Critical Section
                sharedResource++;
                System.out.println("Process 1 incremented resource to: " + sharedResource);

                // Exit Section
                flag[1] = false;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- PETERSON'S ALGORITHM (2-Process Mutual Exclusion) ---");
        Thread t0 = new Process0();
        Thread t1 = new Process1();
        t0.start();
        t1.start();
    }
}
