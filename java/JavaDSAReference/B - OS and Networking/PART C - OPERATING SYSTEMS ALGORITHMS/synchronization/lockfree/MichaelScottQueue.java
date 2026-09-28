package os.synchronization.lockfree;

import java.util.concurrent.atomic.AtomicReference;

/**
 * MICHAEL-SCOTT LOCK-FREE QUEUE
 * 
 * WHAT IT IS:
 * A non-blocking, lock-free queue algorithm.
 * Uses atomic Compare-And-Swap (CAS) instead of traditional locks (like synchronized).
 * Highly scalable for multi-threaded environments.
 */
public class MichaelScottQueue<T> {

    private static class Node<T> {
        final T value;
        final AtomicReference<Node<T>> next;

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = new AtomicReference<>(next);
        }
    }

    private final AtomicReference<Node<T>> head;
    private final AtomicReference<Node<T>> tail;

    public MichaelScottQueue() {
        Node<T> dummy = new Node<>(null, null);
        head = new AtomicReference<>(dummy);
        tail = new AtomicReference<>(dummy);
    }

    public void enqueue(T value) {
        Node<T> newNode = new Node<>(value, null);
        while (true) {
            Node<T> currentTail = tail.get();
            Node<T> tailNext = currentTail.next.get();
            
            if (currentTail == tail.get()) { // Are we still at the tail?
                if (tailNext != null) {
                    // Queue is in intermediate state, advance tail
                    tail.compareAndSet(currentTail, tailNext);
                } else {
                    // Try to link the new node
                    if (currentTail.next.compareAndSet(null, newNode)) {
                        // Success! Now advance the tail
                        tail.compareAndSet(currentTail, newNode);
                        return;
                    }
                }
            }
        }
    }

    public T dequeue() {
        while (true) {
            Node<T> currentHead = head.get();
            Node<T> currentTail = tail.get();
            Node<T> headNext = currentHead.next.get();
            
            if (currentHead == head.get()) {
                if (currentHead == currentTail) {
                    if (headNext == null) return null; // Queue is empty
                    // Intermediate state, advance tail
                    tail.compareAndSet(currentTail, headNext);
                } else {
                    T value = headNext.value;
                    // Try to advance head
                    if (head.compareAndSet(currentHead, headNext)) {
                        return value;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- MICHAEL-SCOTT LOCK-FREE QUEUE ---");
        MichaelScottQueue<Integer> queue = new MichaelScottQueue<>();
        
        Thread t1 = new Thread(() -> queue.enqueue(10));
        Thread t2 = new Thread(() -> queue.enqueue(20));
        
        t1.start(); t2.start();
        try { t1.join(); t2.join(); } catch (Exception e) {}
        
        System.out.println("Dequeued: " + queue.dequeue());
        System.out.println("Dequeued: " + queue.dequeue());
    }
}
