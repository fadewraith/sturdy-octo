package fifteenblockingqueueexample;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    BlockingQueue<String> queue = null;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true) {
            try {
                String element = queue.take();
                System.out.println("consumed: " + element);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


}
