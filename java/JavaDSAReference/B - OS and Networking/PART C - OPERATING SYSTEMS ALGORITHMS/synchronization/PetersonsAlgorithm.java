package os.synchronization;

import java.util.concurrent.atomic.AtomicIntegerArray;

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
 * standard Java unless every shared variable has volatile semantics (see below).
 */
public class PetersonsAlgorithm {

    // IMPORTANT: "volatile boolean[] flag" would NOT work. volatile applies only to
    // the array REFERENCE, not to its elements, so a spinning thread may never see
    // the other thread's flag[i] = true. AtomicIntegerArray gives every element
    // volatile read/write semantics (1 = wants to enter, 0 = does not).
    private static final AtomicIntegerArray flag = new AtomicIntegerArray(2);
    private static volatile int turn = 0;
    
    private static int sharedResource = 0;

    static class Process0 extends Thread {
        public void run() {
            for (int i = 0; i < 5; i++) {
                flag.set(0, 1);
                turn = 1; // Give turn to process 1
                
                // Busy wait! (Spinlock)
                while (flag.get(1) == 1 && turn == 1) { 
                    // Spin...
                }

                // Critical Section
                sharedResource++;
                System.out.println("Process 0 incremented resource to: " + sharedResource);

                // Exit Section
                flag.set(0, 0);
            }
        }
    }

    static class Process1 extends Thread {
        public void run() {
            for (int i = 0; i < 5; i++) {
                flag.set(1, 1);
                turn = 0; // Give turn to process 0
                
                while (flag.get(0) == 1 && turn == 0) { 
                    // Spin...
                }

                // Critical Section
                sharedResource++;
                System.out.println("Process 1 incremented resource to: " + sharedResource);

                // Exit Section
                flag.set(1, 0);
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
