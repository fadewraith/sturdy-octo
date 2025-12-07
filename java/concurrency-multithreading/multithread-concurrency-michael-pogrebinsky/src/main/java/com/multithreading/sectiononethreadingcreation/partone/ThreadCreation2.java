package com.multithreading.sectiononethreadingcreation.partone;

public class ThreadCreation2 {

    // creation using runnable class

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            throw new RuntimeException("Intentional exception");
        });

        thread.setName("Misbehaving thread");

        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("A critical error happened in thread '" + t.getName() + "' and the error is " + e.getMessage());
            }
        });

        thread.start();
    }
}
