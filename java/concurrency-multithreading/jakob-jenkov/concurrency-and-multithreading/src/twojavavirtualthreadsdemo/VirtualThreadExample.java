package twojavavirtualthreadsdemo;

public class VirtualThreadExample {

    // virtual threads are preview feature in java 19

    public static void main(String[] args) {
        // example 1: create runnable. create and start virtual thread
        Runnable runnable = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Index: " + i);
            }
        };

        Thread vThread1 = Thread.ofVirtual().start(runnable);

        // example 2: create but do not start virtual thread
        Thread vThreadUnstarted = Thread.ofVirtual().unstarted(runnable);

        vThreadUnstarted.start();

        // example 3: create and start virtual thread with lambda example


        // example 4: hwo to join a virtual thread
        try {
            vThreadUnstarted.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
