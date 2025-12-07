package com.multithreading.sectiontenvirtualthreads;

import java.util.ArrayList;
import java.util.List;

public class VirtualThreadsDemo {

    private static final int NUMBER_OF_VIRTUAL_THREADS = 1000;

    public static void main(String[] args) throws InterruptedException {

        Runnable runnable = () -> System.out.println("Inside thread: " + Thread.currentThread());

        /**
         * O/P of below statement -
         * Inside thread: Thread[#22,Thread-0,5,main]
         * where -
         * #22 = Thread ID
         * Thread-0 = Thread name
         * 5 = Thread priority (default priority of any thread)
         * main (parent of this thread is main thread)
         * */
        // Thread platformThread = new Thread(runnable);
        // Thread platformThread = Thread.ofPlatform().unstarted(runnable);

        // platformThread.start();
        // platformThread.join();

        /**
         * O/P -
         * Inside thread: VirtualThread[#22]/runnable@ForkJoinPool-1-worker-1
         * where -
         * #22 = Thread ID
         * runnable@ForkJoinPool-1-worker-1 = Thread name
         * JVM created an internal thread pool of platform threads which is called -> ForkJoinPool-1
         * then JVM mounted virtual thread to one of those worker threads which is called -> worker-1
         * */
//        Thread virtualThread = Thread.ofVirtual().unstarted(runnable);
//
//        virtualThread.start();
//        virtualThread.join();

        List<Thread> virtualThreads = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_VIRTUAL_THREADS; i++) {
            Thread virtualThread = Thread.ofVirtual().unstarted(runnable);
            virtualThreads.add(virtualThread);
        }

        for (Thread virtualThread : virtualThreads) {
            virtualThread.start();
        }

        for (Thread virtualThread : virtualThreads) {
            virtualThread.join();
        }
    }
}
