package linkedlist.cdll;

import linkedlist.common.DoublyNode;

/**
 * CIRCULAR DOUBLY LINKED LIST
 *
 * What it is:
 * A doubly linked list whose ends are joined: the last node's next is the head, and the
 * head's prev is the last node. There is no null anywhere in a non-empty list.
 *
 * Why it's useful:
 * - We only store 'head'. The tail is simply head.prev, so BOTH ends are O(1) to reach,
 *   insert at and delete from - with no separate tail pointer to keep in sync.
 * - Rotating the list is just moving the head pointer (no relinking).
 * - Walking backwards is as easy as forwards.
 * Used for: round-robin schedulers, music playlists on repeat, the "recently used" list
 * inside some caches, the doubly linked list inside java.util.LinkedList's ancestors.
 *
 * The ONE thing to be careful about: loops must stop after 'size' steps (or when they
 * get back to head) - there is no null to stop at.
 *
 * Time/Space Complexity:
 * Operation                         | Time
 * --------------------------------------------------------
 * insert/delete at head or tail     | O(1)
 * get/set/insert/delete at index    | O(min(i, N - i)) - walks from the closer end
 * indexOf / deleteByValue           | O(N)
 * rotate(k)                         | O(min(k, N - k))
 * reverse                           | O(N)
 * Space                             | O(N)
 */
public class CircularDoublyLinkedList<T> implements Iterable<T> {

    private DoublyNode<T> head;
    private int size;

    private static boolean same(Object a, Object b) {
        return a == null ? b == null : a.equals(b);
    }

    // --------------------------------------------------------
    // 1. INSERTION
    // --------------------------------------------------------

    /** Links newNode in between prev and next (which are neighbors). */
    private void linkBetween(DoublyNode<T> newNode, DoublyNode<T> prev, DoublyNode<T> next) {
        newNode.prev = prev;
        newNode.next = next;
        prev.next = newNode;
        next.prev = newNode;
        size++;
    }

    public void insertAtTail(T data) {
        DoublyNode<T> node = new DoublyNode<>(data);
        if (head == null) {
            node.next = node; // a single node points to itself both ways
            node.prev = node;
            head = node;
            size = 1;
        } else {
            linkBetween(node, head.prev, head); // between the old tail and head
        }
    }

    public void insertAtHead(T data) {
        insertAtTail(data);   // goes between tail and head...
        head = head.prev;     // ...so moving head back one step makes it the new head
    }

    public void insertAtPosition(int index, T data) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        if (index == size) { insertAtTail(data); return; }
        if (index == 0) { insertAtHead(data); return; }
        DoublyNode<T> at = nodeAt(index);
        linkBetween(new DoublyNode<>(data), at.prev, at);
    }

    // --------------------------------------------------------
    // 2. DELETION
    // --------------------------------------------------------

    private T unlink(DoublyNode<T> node) {
        if (size == 1) {
            head = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            if (node == head) head = node.next;
        }
        node.next = null;
        node.prev = null;
        size--;
        return node.data;
    }

    public T deleteAtHead() {
        if (head == null) throw new IllegalStateException("List is empty");
        return unlink(head);
    }

    public T deleteAtTail() {
        if (head == null) throw new IllegalStateException("List is empty");
        return unlink(head.prev);
    }

    public T deleteAtPosition(int index) {
        return unlink(nodeAt(index));
    }

    /** Removes the first occurrence. Returns true if something was removed. */
    public boolean deleteByValue(T value) {
        DoublyNode<T> cur = head;
        for (int i = 0; i < size; i++, cur = cur.next) {
            if (same(cur.data, value)) {
                unlink(cur);
                return true;
            }
        }
        return false;
    }

    // --------------------------------------------------------
    // 3. ACCESS & SEARCH
    // --------------------------------------------------------

    /** Walks forward from head or backward from tail - whichever is closer. */
    private DoublyNode<T> nodeAt(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        DoublyNode<T> cur = head;
        if (index <= size / 2) {
            for (int i = 0; i < index; i++) cur = cur.next;
        } else {
            for (int i = size; i > index; i--) cur = cur.prev;
        }
        return cur;
    }

    public T get(int index) {
        return nodeAt(index).data;
    }

    public T set(int index, T data) {
        DoublyNode<T> node = nodeAt(index);
        T old = node.data;
        node.data = data;
        return old;
    }

    public T getFirst() {
        if (head == null) throw new IllegalStateException("List is empty");
        return head.data;
    }

    public T getLast() {
        if (head == null) throw new IllegalStateException("List is empty");
        return head.prev.data;
    }

    public int indexOf(T value) {
        DoublyNode<T> cur = head;
        for (int i = 0; i < size; i++, cur = cur.next) {
            if (same(cur.data, value)) return i;
        }
        return -1;
    }

    public boolean contains(T value) {
        return indexOf(value) != -1;
    }

    // --------------------------------------------------------
    // 4. ROTATE & REVERSE
    // --------------------------------------------------------

    /**
     * Rotate LEFT by k (element at index k becomes the head). Negative k rotates right.
     * Only the head pointer moves - in whichever direction is shorter.
     */
    public void rotate(int k) {
        if (size == 0) return;
        k = ((k % size) + size) % size;
        if (k <= size / 2) {
            for (int i = 0; i < k; i++) head = head.next;
        } else {
            for (int i = 0; i < size - k; i++) head = head.prev;
        }
    }

    /** Swap next/prev in every node; the old tail becomes the head. */
    public void reverse() {
        if (size < 2) return;
        DoublyNode<T> cur = head;
        for (int i = 0; i < size; i++) {
            DoublyNode<T> tmp = cur.next;
            cur.next = cur.prev;
            cur.prev = tmp;
            cur = tmp; // the original next
        }
        head = head.next; // after swapping, head.next is the old tail
    }

    // --------------------------------------------------------
    // 5. UTILITIES & ITERATION
    // --------------------------------------------------------

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        if (head != null) {
            head.prev.next = null; // break the circle so nodes can be collected
            head.prev = null;
        }
        head = null;
        size = 0;
    }

    /** Head to tail, exactly once around. */
    @Override
    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            private DoublyNode<T> current = head;
            private int remaining = size;

            @Override
            public boolean hasNext() {
                return remaining > 0;
            }

            @Override
            public T next() {
                if (remaining == 0) throw new java.util.NoSuchElementException();
                T data = current.data;
                current = current.next;
                remaining--;
                return data;
            }
        };
    }

    /** Tail to head, using the prev links. */
    public java.util.Iterator<T> descendingIterator() {
        return new java.util.Iterator<T>() {
            private DoublyNode<T> current = head == null ? null : head.prev;
            private int remaining = size;

            @Override
            public boolean hasNext() {
                return remaining > 0;
            }

            @Override
            public T next() {
                if (remaining == 0) throw new java.util.NoSuchElementException();
                T data = current.data;
                current = current.prev;
                remaining--;
                return data;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        DoublyNode<T> cur = head;
        for (int i = 0; i < size; i++, cur = cur.next) {
            sb.append(cur.data);
            if (i < size - 1) sb.append(", ");
        }
        return sb.append("]").toString();
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- CIRCULAR DOUBLY LINKED LIST DEMO ---");
        CircularDoublyLinkedList<String> playlist = new CircularDoublyLinkedList<>();
        playlist.insertAtTail("Song B");
        playlist.insertAtTail("Song C");
        playlist.insertAtHead("Song A");
        playlist.insertAtTail("Song D");
        System.out.println("Playlist: " + playlist);                 // [Song A, Song B, Song C, Song D]
        System.out.println("First: " + playlist.getFirst() + ", last: " + playlist.getLast());

        playlist.rotate(1);
        System.out.println("After skipping one song (rotate 1): " + playlist); // starts at Song B
        playlist.rotate(-1);
        System.out.println("Back one (rotate -1): " + playlist);

        playlist.reverse();
        System.out.println("Reversed: " + playlist);

        StringBuilder backward = new StringBuilder();
        for (java.util.Iterator<String> it = playlist.descendingIterator(); it.hasNext(); ) backward.append(it.next()).append(' ');
        System.out.println("Walking backward with prev links: " + backward);

        playlist.deleteByValue("Song C");
        playlist.deleteAtTail();
        System.out.println("After removing Song C and the tail: " + playlist + " (size " + playlist.size() + ")");
    }
}
