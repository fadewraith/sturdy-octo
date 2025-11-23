package eightraceconditionsdemo;

public class RaceConditionsExample4 {

    public static void main(String[] args) {
        Counter c1 = new Counter();
        Counter c2 = new Counter();

        Thread t1 = new Thread(getRunnable(c1, c2, "Thread 1"));
        Thread t2 = new Thread(getRunnable(c2, c1, "Thread 2"));

        t1.start();
        t2.start();
    }

    private static Runnable getRunnable(Counter c1, Counter c2, String runnableName) {
        return () -> {
            for (int i = 0; i < 1_000_000; i++) {
                c1.incAndGet();
            }
            System.out.println(runnableName + " final count - counterA: " + c1.get());
            System.out.println(runnableName + " final count - counterB: " + c2.get());
        };
    }
}
