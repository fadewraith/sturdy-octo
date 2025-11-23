package onejavathreaddemo;

public class ThreadExample {

    /**
     * threads in java are managed by underlying OS that the JVM is running on, and such are referred to as OS level threads
     * OS level threads are more heavy and unnecessary
     * java has project LOOM that is trying to provide light weight threads and are managed not by OS, but managed by JVM itself
     * */

    public static void main(String[] args) {
        Thread thread = new Thread();
        thread.start();
    }
}
