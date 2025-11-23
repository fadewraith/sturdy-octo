package fifteenblockingqueueexample;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueExample {

    BlockingQueue arrayBlockingQueue = new ArrayBlockingQueue<>(3);
    BlockingQueue linkedBlockingQueue = new LinkedBlockingQueue<>();
}
