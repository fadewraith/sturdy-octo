package os.synchronization;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * DINING PHILOSOPHERS PROBLEM (Deadlock-Free Version)
 * 
 * THE PROBLEM:
 * 5 Philosophers sit around a table. There are 5 forks. A philosopher needs TWO forks 
 * (left and right) to eat. If everyone picks up their left fork simultaneously, they deadlock.
 * 
 * THE FIX (Resource Hierarchy Solution):
 * We number the forks 0 to 4.
 * Rule: A philosopher must ALWAYS pick up the lower-numbered fork first!
 * This breaks the symmetry and prevents the circular wait condition of deadlock!
 */
public class DiningPhilosophers {

    static class Philosopher extends Thread {
        int id;
        Lock leftFork;
        Lock rightFork;

        Philosopher(int id, Lock left, Lock right) {
            this.id = id;
            // The magic fix: order the locks!
            if (System.identityHashCode(left) < System.identityHashCode(right)) {
                this.leftFork = left;
                this.rightFork = right;
            } else {
                this.leftFork = right;
                this.rightFork = left;
            }
        }

        public void run() {
            try {
                // Think
                System.out.println("Philosopher " + id + " is thinking.");
                
                // Pick up lower-numbered fork
                leftFork.lock();
                System.out.println("Philosopher " + id + " picked up first fork.");
                
                // Pick up higher-numbered fork
                rightFork.lock();
                System.out.println("Philosopher " + id + " picked up second fork and is EATING.");
                
                // Eat
                Thread.sleep(100);
                
                // Put down
                rightFork.unlock();
                leftFork.unlock();
                System.out.println("Philosopher " + id + " finished eating and put down forks.");
                
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- DINING PHILOSOPHERS (DEADLOCK-FREE) ---");
        int num = 5;
        Lock[] forks = new ReentrantLock[num];
        for (int i = 0; i < num; i++) forks[i] = new ReentrantLock();

        Philosopher[] philosophers = new Philosopher[num];
        for (int i = 0; i < num; i++) {
            philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % num]);
            philosophers[i].start();
        }
    }
}
