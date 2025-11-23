package eighteenfalsesharingdemo;

public class FalseSharingExample {

    public static void main(String[] args) {
        // Counter1 c1 = new Counter1();
        // Counter1 c2 = c1;
        // Counter1 c2 = new Counter1();

        Counter2 c1 = new Counter2();
        Counter2 c2 = c1;
        // Counter2 c2 = new Counter2();

        long iterations = 1_000_000_000;

        Thread t1 = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            for (long i = 0; i < iterations; i++) {
                c1.count1++;
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Time taken by thread 1: " + (endTime - startTime));
        });

        Thread t2 = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            for (long i = 0; i < iterations; i++) {
                c2.count2++;
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Time taken by thread 2: " + (endTime - startTime));
        });

        t1.start();
        t2.start();
    }
}
