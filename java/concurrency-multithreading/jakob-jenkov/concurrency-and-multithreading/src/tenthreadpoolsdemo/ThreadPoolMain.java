package tenthreadpoolsdemo;

public class ThreadPoolMain {

    public static void main(String[] args) throws Exception {

        ThreadPool threadPool = new ThreadPool(3, 10);

        for (int i = 0; i < 10; i++) {
            int taskNo = i;
            threadPool.execute(() -> {
                String msg = Thread.currentThread().getName() + ": Task " + taskNo;
                System.out.println(msg);
            });
        }

        threadPool.waitUntilAllTasksFinished();
        threadPool.stop();

    }
}
