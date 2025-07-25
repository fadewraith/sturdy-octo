package practiceQuestions;

import neetcode.algorithms.linkedlists.SinglyLinkedList;

public class SLL {
    // Node class (inner)
    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    private Node head;

    // Insert at the beginning
    public void insertAtHead(int data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
    }

    // Insert at the end
    public void insertAtTail(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            return;
        }
        Node curr = head;
        while (curr.next != null) curr = curr.next;
        curr.next = newNode;
    }

    // Insert at a given position (0-based indexing)
    public void insertAtPosition(int data, int pos) {
        if (pos < 0) throw new IndexOutOfBoundsException("Negative position");
        if (pos == 0) { insertAtHead(data); return; }
        Node curr = head;
        for (int i = 0; i < pos - 1 && curr != null; i++) curr = curr.next;
        if (curr == null) throw new IndexOutOfBoundsException("Position out of bounds");
        Node newNode = new Node(data);
        newNode.next = curr.next;
        curr.next = newNode;
    }

    // Delete at the head
    public void deleteHead() {
        if (head != null) head = head.next;
    }

    // Delete at the tail
    public void deleteTail() {
        if (head == null) return;
        if (head.next == null) { head = null; return; }
        Node curr = head;
        while (curr.next.next != null) curr = curr.next;
        curr.next = null;
    }

    // Delete at a given position (0-based)
    public void deleteAtPosition(int pos) {
        if (pos < 0 || head == null) throw new IndexOutOfBoundsException("Position out of bounds");
        if (pos == 0) { deleteHead(); return; }
        Node curr = head;
        for (int i = 0; i < pos - 1 && curr != null; i++) curr = curr.next;
        if (curr == null || curr.next == null) throw new IndexOutOfBoundsException("Position out of bounds");
        curr.next = curr.next.next;
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

    // Update node value at a position
    public void update(int pos, int newValue) {
        if (pos < 0) throw new IndexOutOfBoundsException("Position out of bounds");
        Node curr = head;
        for (int i = 0; i < pos && curr != null; i++) curr = curr.next;
        if (curr == null) throw new IndexOutOfBoundsException("Position out of bounds");
        curr.data = newValue;
    }

    // Get size (length)
    public int length() {
        int count = 0;
        Node curr = head;
        while (curr != null) {
            count++;
            curr = curr.next;
        }
        return count;
    }

    // Check if list is empty
    public boolean isEmpty() {
        return head == null;
    }

    // Clear the list
    public void clear() {
        head = null;
    }

    // Print list (traversal)
    public void printList() {
        Node curr = head;
        while (curr != null) {
            System.out.print(curr.data + " -> ");
            curr = curr.next;
        }
        System.out.println("null");
    }

    // Main to demonstrate usage
    public static void main(String[] args) {
        SLL list = new SLL();
        list.insertAtHead(3);
        list.insertAtTail(5);
        list.insertAtTail(9);
        list.insertAtPosition(7, 2);
        list.printList(); // 3 -> 5 -> 7 -> 9 -> null

        list.deleteAtPosition(2); // Remove 7
        list.printList(); // 3 -> 5 -> 9 -> null

        System.out.println("Is 5 present? " + list.search(5)); // true
        System.out.println("Length: " + list.length());        // 3

        list.update(1, 8); // Update position 1 to 8
        list.printList(); // 3 -> 8 -> 9 -> null

        System.out.println("Empty? " + list.isEmpty()); // false
        list.clear();
        System.out.println("After clear:");
        list.printList(); // null
    }
}
