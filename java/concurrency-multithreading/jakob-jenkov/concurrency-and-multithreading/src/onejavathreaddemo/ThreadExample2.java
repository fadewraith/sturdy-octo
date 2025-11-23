package onejavathreaddemo;

public class ThreadExample2 {

    public static class MyThread extends Thread {
        /**
         * The entry point for the thread. This method is invoked when the thread
         * is started. It prints a message when the thread starts and finishes.
         */
        public void run() {
            System.out.println("MyThread running");
            System.out.println("MyThread finished");
        }
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
    }
}
