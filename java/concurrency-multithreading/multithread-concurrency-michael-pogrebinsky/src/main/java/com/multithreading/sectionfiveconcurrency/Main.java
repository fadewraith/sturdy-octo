package com.multithreading.sectionfivecopncurrency;

public class Main {

    public static void main(String [] args) {
        SharedClass sharedObject = new SharedClass(); // q1 & q2


         // q1
         Thread thread1 = new Thread(() -> {
             while (true) {
                 sharedObject.increment();
             }
         });

         Thread thread2 = new Thread(() -> {
             while (true) {
                 sharedObject.increment();
             }
         });

        /**
         * q2
         * Thread thread1 = new Thread(() -> {
         *             while (true) {
         *                 sharedObject.increment();
         *             }
         *         });
         *
         *         Thread thread2 = new Thread(() -> {
         *             while (true) {
         *                 sharedObject.decrement();
         *             }
         *         });
         * */

        /**
         * q3
         * SharedClass sharedObject1 = new SharedClass();
         *         SharedClass sharedObject2 = new SharedClass();
         *
         *         Thread thread1 = new Thread(() -> {
         *             while (true) {
         *                 sharedObject1.increment();
         *             }
         *         });
         *
         *         Thread thread2 = new Thread(() -> {
         *             while (true) {
         *                 sharedObject2.increment();
         *             }
         *         });
         * */

        /**
         * q4
         * Thread thread1 = new Thread(() -> {
         *             while (true) {
         *                 sharedObject.incrementCounter1();
         *             }
         *         });
         *
         *         Thread thread2 = new Thread(() -> {
         *             while (true) {
         *                 sharedObject.incrementCounter2();
         *             }
         *         });
         * */

        thread1.start();
        thread2.start();
    }

    static class SharedClass {
        private int counter = 0;

        public synchronized void increment() {
            this.counter++;
        }

        public synchronized void decrement() { // q2
            this.counter--;
        }

        /**
         * q4 -
         *
         * private int counter1 = 0;
         *         private int counter2 = 0;
         *
         *         private Object lock1 = new Object();
         *         private Object lock2 = new Object();
         *
         *         public void incrementCounter1() {
         *             synchronized (lock1) {
         *                 this.counter1++;
         *             }
         *         }
         *
         *         public void incrementCounter2() {
         *             synchronized (lock2) {
         *                 this.counter2++;
         *             }
         *         }
         *
         */
        
    }
}
