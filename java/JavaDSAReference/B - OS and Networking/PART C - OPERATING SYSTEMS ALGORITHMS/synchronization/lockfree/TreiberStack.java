package os.synchronization.lockfree;

import java.util.concurrent.atomic.AtomicReference;

/**
 * TREIBER STACK
 * 
 * WHAT IT IS:
 * A scalable, lock-free concurrent stack algorithm using AtomicReference and CAS.
 */
public class TreiberStack<T> {

    private static class Node<T> {
        final T value;
        Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }

    private AtomicReference<Node<T>> head = new AtomicReference<>(null);

    public void push(T value) {
        Node<T> newNode = new Node<>(value);
        while (true) {
            Node<T> currentHead = head.get();
            newNode.next = currentHead;
            // Attempt to CAS the head. If successful, push is done. If not, retry (spin).
            if (head.compareAndSet(currentHead, newNode)) {
                return;
            }
        }
    }

    public T pop() {
        while (true) {
            Node<T> currentHead = head.get();
            if (currentHead == null) {
                return null; // Stack is empty
            }
            Node<T> newHead = currentHead.next;
            // Attempt to CAS the head to the next node.
            if (head.compareAndSet(currentHead, newHead)) {
                return currentHead.value;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- TREIBER LOCK-FREE STACK ---");
        TreiberStack<String> stack = new TreiberStack<>();
        
        Thread t1 = new Thread(() -> stack.push("A"));
        Thread t2 = new Thread(() -> stack.push("B"));
        
        t1.start(); t2.start();
        try { t1.join(); t2.join(); } catch (Exception e) {}
        
        System.out.println("Popped: " + stack.pop());
        System.out.println("Popped: " + stack.pop());
    }
}
