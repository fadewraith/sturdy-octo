import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityQueueDemo {
    public static void main(String[] args) {
        /*
        serves elements with the highest priorities first before elements with lower priority
        * */

//        Queue<Double> queue = new LinkedList<>();
//        Queue<Double> queue = new PriorityQueue<>();
//        for descending order
        Queue<Double> queue = new PriorityQueue<>(Collections.reverseOrder());
        queue.offer(3.0);
        queue.offer(2.5);
        queue.offer(3.7);
        queue.offer(1.5);
        queue.offer(2.0);

        while(!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }
}
