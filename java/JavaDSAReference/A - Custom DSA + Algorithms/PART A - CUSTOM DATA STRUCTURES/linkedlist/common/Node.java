package linkedlist.common;

/**
 * SINGLY LINKED LIST NODE
 * 
 * What it is:
 * The foundational building block for Singly Linked Lists (SLL) and Circular Linked Lists (CLL).
 * It holds a piece of data and a reference to the next node in the sequence.
 */
public class Node<T> {
    public T data;
    public Node<T> next;

    /**
     * Constructs a new Node with the given data.
     * @param data the data to store in this node
     */
    public Node(T data) {
        this.data = data;
        this.next = null;
    }
}
