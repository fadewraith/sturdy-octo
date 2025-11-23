package sixteenproducerconsumerdemo;

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
                String text= Thread.currentThread().getName() + " consumed " + element;
                System.out.println(text);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
