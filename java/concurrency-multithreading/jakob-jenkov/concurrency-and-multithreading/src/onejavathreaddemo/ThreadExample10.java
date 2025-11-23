package onejavathreaddemo;

public class ThreadExample10 {

    public static void main(String[] args) {
        Runnable runnable = () -> {
            for (int i = 0; i < 5; i++) {
                sleep(1000);
                System.out.println("Running");
            }
        };

        Thread thread = new Thread(runnable);
        // if dont want to keep virtual machine alive, if its the only thread running
        // daemon threads are stopped in an undefined state, make sure they are resting in a state
        thread.setDaemon(true); // as long as the main thread stops, it terminates all the daemon threads are running
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
