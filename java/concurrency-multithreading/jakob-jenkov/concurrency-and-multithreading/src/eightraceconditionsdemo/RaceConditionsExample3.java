package eightraceconditionsdemo;

public class RaceConditionsExample3 {

    public static void main(String[] args) {
//        CounterSynchronized counter = new CounterSynchronized();
        Counter counter = new Counter();

        Thread t1 = new Thread(getIncrementingRunnable(counter));
        Thread t2 = new Thread(getReadingRunnable(counter));

        t1.start();
        t2.start();
    }

    private static Runnable getIncrementingRunnable(Counter c) {
        return () -> {
            for (int i = 0; i < 1_000_000; i++) {
                c.incAndGet();
            }
            System.out.println("Thread1 final count: " + c.get());
        };
    }

    private static Runnable getReadingRunnable(Counter counter) {

        return () -> {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread2 count: " + counter.get());
            }
        };
    }

}
