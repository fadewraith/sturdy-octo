package com.multithreading.sectiononethreadingcreation.partone;

public class ThreadCreation {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("We are now in thread: " + Thread.currentThread().getName());
                System.out.println("Current thread priority is " + Thread.currentThread().getPriority());
            }
        });

//        Thread t1 = new Thread(() -> {
//
//        });

        // set thread name
        thread.setName("New Worker Thread");

        // set priority -
        // MIN_PRIORITY = 1
        // MAX_PRIORITY = 10
        // NORM_PRIORITY = 5
        thread.setPriority(Thread.MAX_PRIORITY);

        System.out.println("We are in thread: " + Thread.currentThread().getName() + " - before starting a new thread");
        thread.start();
        System.out.println("We are in thread: " + Thread.currentThread().getName() + " - after starting a new thread");

        // instructs the operating system to not schedule the current thread until that time passes, during that time this thread not consuming any CPU
        // Thread.sleep(10000);
    }
}
