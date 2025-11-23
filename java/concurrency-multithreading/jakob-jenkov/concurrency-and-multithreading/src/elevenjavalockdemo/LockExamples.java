package elevenjavalockdemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExamples {

    public static void main(String[] args) {
//        lockBasics();
//        lockInterruptibly();
//        tryLock();

        ReentrantLock reentrantLock = new ReentrantLock();
        int holdCount = reentrantLock.getHoldCount();
        int queueLength = reentrantLock.getQueueLength();
        boolean hasQueuedThisThread = reentrantLock.hasQueuedThread(Thread.currentThread());
        boolean hasQueuedThreads = reentrantLock.hasQueuedThreads();
        boolean isFair = reentrantLock.isFair();
        boolean isHeldByCurrentThread = reentrantLock.isHeldByCurrentThread();
        boolean isLocked = reentrantLock.isLocked();
    }

//    public static void tryLock() {
//        Lock lock = new ReentrantLock();
//        try {
//            boolean lockSuccessful = lock.tryLock();
//            System.out.println("Lock successful: " + lockSuccessful);
//        } finally {
//            lock.unlock();
//        }
//    }

    public static void tryLock() {
        Lock lock = new ReentrantLock(true);
        try {
            boolean lockSuccessful = lock.tryLock(1000, TimeUnit.MILLISECONDS);
            System.out.println("Lock successful: " + lockSuccessful);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static void lockBasics() {
        Lock lock = new ReentrantLock(false);
//        Lock lock = new ReentrantLock(true);

        Runnable runnable = () -> { lockSleepUnlock(lock, 1000); };

        Thread t1 = new Thread(runnable, "Thread 1");
        Thread t2 = new Thread(runnable, "Thread 2");
        Thread t3 = new Thread(runnable, "Thread 3");

        t1.start();
        t2.start();
        t3.start();
    }

    private static void lockInterruptibly() {
        Lock lock = new ReentrantLock();
        // Thread.currentThread().interrupt();
        try {
            lock.lockInterruptibly();
            lock.unlock();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted. ");
        }
    }

    private static void lockSleepUnlock(Lock lock, long timeMillis) {
        try {
            lock.lock();
            printThreadMsg(" holds the lock. ");
            sleep(timeMillis);
        } finally {
            lock.unlock();
        }
    }

    private static void printThreadMsg(String text) {
        System.out.println(Thread.currentThread().getName() + text);
    }

    private static void sleep(long timeMillis) {
        try {
            Thread.sleep(timeMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
