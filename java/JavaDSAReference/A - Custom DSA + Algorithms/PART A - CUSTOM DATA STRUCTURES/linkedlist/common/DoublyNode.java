package linkedlist.common;

/**
 * DOUBLY LINKED LIST NODE
 * 
 * What it is:
 * The foundational building block for Doubly Linked Lists (DLL).
 * It holds a piece of data and references to both the next and previous nodes in the sequence.
 */
public class DoublyNode<T> {
    public T data;
    public DoublyNode<T> next;
    public DoublyNode<T> prev;

    /**
     * Constructs a new DoublyNode with the given data.
     * @param data the data to store in this node
     */
    public DoublyNode(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
