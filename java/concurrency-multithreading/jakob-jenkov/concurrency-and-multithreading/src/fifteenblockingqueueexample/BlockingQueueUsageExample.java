package fifteenblockingqueueexample;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueUsageExample {

    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);

        queue.put("Task 1");
        queue.put("Task 2");

        String element1 = queue.take();
        String element2 = queue.take();

        System.out.println(element1);
        System.out.println(element2);

    }
}
