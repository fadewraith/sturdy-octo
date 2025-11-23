package seventhreadlocaldemo;

public class ThreadLocalRemoveExample {

    public static void main(String[] args) {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();

        Thread t1 = new Thread(() -> {
            threadLocal.set("Thread 1");

            String value = threadLocal.get();
            System.out.println(value);

            threadLocal.remove();
            value = threadLocal.get();
            System.out.println(value);
        });

        Thread t2 = new Thread(() -> {
            threadLocal.set("Thread 2");

            String value = threadLocal.get();
            System.out.println(value);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            value = threadLocal.get();
            System.out.println(value);

            threadLocal.remove();
            value = threadLocal.get();
            System.out.println(value);
        });

        t1.start();
        t2.start();
    }
}
