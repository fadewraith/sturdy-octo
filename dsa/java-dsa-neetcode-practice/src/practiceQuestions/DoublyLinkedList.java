package practiceQuestions;

class DoublyLinkedList {
    // Node class (inner)
    static class Node {
        int data;
        Node prev, next;
        Node(int data) { this.data = data; }
    }

    private Node head, tail;

    // Insert at the beginning
    public void insertAtHead(int data) {
        Node newNode = new Node(data);
        newNode.next = head;
        if (head != null) head.prev = newNode;
        head = newNode;
        if (tail == null) tail = newNode;
    }

    // Insert at the end
    public void insertAtTail(int data) {
        Node newNode = new Node(data);
        if (tail == null) {
            head = tail = newNode;
            return;
        }
        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
    }

    // Insert at a given position (0-based)
    public void insertAtPosition(int data, int pos) {
        if (pos < 0) throw new IndexOutOfBoundsException("Negative position");
        if (pos == 0) { insertAtHead(data); return; }
        Node curr = head;
        for (int i = 0; i < pos - 1 && curr != null; i++) curr = curr.next;
        if (curr == null) throw new IndexOutOfBoundsException("Position out of bounds");
        Node newNode = new Node(data);
        newNode.next = curr.next;
        newNode.prev = curr;
        if (curr.next != null) curr.next.prev = newNode;
        else tail = newNode;
        curr.next = newNode;
    }

    // Delete at the head
    public void deleteHead() {
        if (head == null) return;
        head = head.next;
        if (head != null) head.prev = null;
        else tail = null;
    }

    // Delete at the tail
    public void deleteTail() {
        if (tail == null) return;
        tail = tail.prev;
        if (tail != null) tail.next = null;
        else head = null;
    }

    // Delete at a given position (0-based)
    public void deleteAtPosition(int pos) {
        if (pos < 0 || head == null) throw new IndexOutOfBoundsException("Position out of bounds");
        if (pos == 0) { deleteHead(); return; }
        Node curr = head;
        for (int i = 0; i < pos && curr != null; i++) curr = curr.next;
        if (curr == null) throw new IndexOutOfBoundsException("Position out of bounds");
        if (curr.next != null) curr.next.prev = curr.prev;
        else tail = curr.prev;
        if (curr.prev != null) curr.prev.next = curr.next;
    }

    // Search for a value
    public boolean search(int key) {
        Node curr = head;
        while (curr != null) {
            if (curr.data == key) return true;
            curr = curr.next;
        }
        return false;
    }

    // Update value at a position
    public void update(int pos, int newValue) {
        if (pos < 0) throw new IndexOutOfBoundsException("Position out of bounds");
        Node curr = head;
        for (int i = 0; i < pos && curr != null; i++) curr = curr.next;
        if (curr == null) throw new IndexOutOfBoundsException("Position out of bounds");
        curr.data = newValue;
    }

    // Get length
    public int length() {
        int count = 0;
        Node curr = head;
        while (curr != null) {
            count++;
            curr = curr.next;
        }
        return count;
    }

    // Check if empty
    public boolean isEmpty() {
        return head == null;
    }

    // Clear the list
    public void clear() {
        head = tail = null;
    }

    // Print list (forward traversal)
    public void printList() {
        Node curr = head;
        while (curr != null) {
            System.out.print(curr.data + " <-> ");
            curr = curr.next;
        }
        System.out.println("null");
    }

    // Print list in reverse
    public void printReverse() {
        Node curr = tail;
        while (curr != null) {
            System.out.print(curr.data + " <-> ");
            curr = curr.prev;
        }
        System.out.println("null");
    }

    // Main to demonstrate usage
    public static void main(String[] args) {
        DoublyLinkedList dll = new DoublyLinkedList();
        dll.insertAtHead(10);
        dll.insertAtTail(20);
        dll.insertAtTail(30);
        dll.insertAtPosition(15, 1);   // 10 <-> 15 <-> 20 <-> 30
        dll.printList();

        dll.deleteAtPosition(1);       // Deletes 15
        dll.printList();

        System.out.println("Is 20 present? " + dll.search(20));
        System.out.println("Length: " + dll.length());

        dll.update(1, 25);             // Update position 1 (was 20) to 25
        dll.printList();

        System.out.println("Empty? " + dll.isEmpty());
        dll.clear();
        System.out.println("After clear:");
        dll.printList();
    }
}
