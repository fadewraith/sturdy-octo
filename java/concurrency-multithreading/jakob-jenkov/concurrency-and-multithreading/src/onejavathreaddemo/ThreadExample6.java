package onejavathreaddemo;

public class ThreadExample6 {

    public static void main(String[] args) {
        Runnable runnable = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " running");
        };

        Thread thread = new Thread(runnable, "The thread");
        thread.start();

        Thread thread1 = new Thread(runnable, "The thread 1");
        thread1.start();
    }
}
