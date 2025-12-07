package com.multithreading.sectiononethreadingcreation.parttwo;

public class ThreadCreation3 {

    public static void main(String[] args) {
//        Thread thread = new Thread(() -> {
//            System.out.println("Hello from '" + Thread.currentThread().getName() + "'");
//        });

        Thread thread = new NewThread();

        thread.start();
    }

    private static class NewThread extends Thread {
        @Override
        public void run() {
//            System.out.println("Hello from '" + Thread.currentThread().getName() + "'");
            System.out.println("Hello from '" + this.getName() + "'");
        }
    }
}
