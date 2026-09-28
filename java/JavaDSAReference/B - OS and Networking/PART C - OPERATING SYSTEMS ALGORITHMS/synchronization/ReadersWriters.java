package os.synchronization;

import java.util.concurrent.Semaphore;

/**
 * READERS-WRITERS PROBLEM (Readers-Preference)
 * 
 * WHAT IT IS:
 * Multiple threads read, some write.
 * - Readers can read simultaneously.
 * - A Writer requires exclusive access (no other writers, no other readers).
 * 
 * READERS-PREFERENCE:
 * If a reader is reading, new readers can join in immediately. Writers must wait 
 * until ALL readers are done. This can starve writers!
 */
public class ReadersWriters {

    private static int readCount = 0;
    private static Semaphore mutex = new Semaphore(1); // Protects readCount
    private static Semaphore rwMutex = new Semaphore(1); // Protects the actual data

    static class Reader extends Thread {
        int id;
        Reader(int id) { this.id = id; }
        
        public void run() {
            try {
                mutex.acquire();
                readCount++;
                if (readCount == 1) {
                    rwMutex.acquire(); // First reader locks out writers
                }
                mutex.release();

                System.out.println("Reader " + id + " is reading.");
                Thread.sleep(100);

                mutex.acquire();
                readCount--;
                if (readCount == 0) {
                    rwMutex.release(); // Last reader lets writers in
                }
                mutex.release();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    static class Writer extends Thread {
        int id;
        Writer(int id) { this.id = id; }
        
        public void run() {
            try {
                rwMutex.acquire(); // Needs exclusive access
                System.out.println("Writer " + id + " is WRITING.");
                Thread.sleep(100);
                rwMutex.release();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- READERS-WRITERS (Readers Preference) ---");
        for (int i = 1; i <= 3; i++) new Reader(i).start();
        new Writer(1).start();
        for (int i = 4; i <= 6; i++) new Reader(i).start();
    }
}
