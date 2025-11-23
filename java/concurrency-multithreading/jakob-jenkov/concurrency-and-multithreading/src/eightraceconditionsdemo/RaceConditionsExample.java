package eightraceconditionsdemo;

public class RaceConditionsExample {

    public static void main(String[] args) {
        Counter counter = new Counter();
        
        Thread t1 = new Thread(getRunnable(counter, "Thread 1 final count: "));
        Thread t2 = new Thread(getRunnable(counter, "Thread 2 final count: "));
        
        t1.start();
        t2.start();
    }

    private static Runnable getRunnable(Counter c, String msg) {
        return () -> {
            for (int i = 0; i < 1_000_000; i++) {
                c.incAndGet();
            }
            System.out.println(msg + " " + c.get());
        };
    }
}
