package onejavathreaddemo;

public class ThreadExample9 {

    public static void main(String[] args) {
        Runnable runnable = () -> {
            while(true) {
                sleep(1000);
                System.out.println("Running");
            }
        };

        Thread thread = new Thread(runnable);
        // if dont want to keep virtual machine alive, if its the only thread running
        // daemon threads are stopped in an undefined state, make sure they are resting in a state
        thread.setDaemon(true); // as long as the main thread stops, it terminates all the daemon threads are running
        thread.start();
        sleep(3100);
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
