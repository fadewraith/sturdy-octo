import java.util.LinkedList;
import java.util.Queue;

public class QueueDemo {

    public static void main(String[] args) {
//        to utilise the queue class, we need that thing to sue which implements the Queue
        Queue<String> queue = new LinkedList<String>();
        queue.offer("Hello");
        queue.offer("John");
        queue.offer("World");
        queue.offer("Doe");

//        poll wont throw exception, but element will throw exception
        System.out.println(queue.peek());
        System.out.println(queue);
        System.out.println(queue.poll());
        System.out.println(queue);
        System.out.println(queue.element());
        System.out.println(queue.isEmpty());
        System.out.println(queue.size());
        System.out.println(queue.contains("Doe"));

//        uses of queue
//        keyboard buffer(letters should appear on the screen in the order they're pressed)
//        printer queue (print jobs should be completed in order)
//        used in linkedlists, priorityqueues, BFS
    }
}
