package educativetutorials.codingpatterns.commonutilities;

import java.util.Arrays;
import java.util.HashSet;

public class CircularLinkedList {

    public ListNode head;

    // Constructor to create a CircularLinkedList
    public CircularLinkedList() {
        this.head = null;
    }

    // Method to insert a node at the head of the circular linked list
    public void insertNodeAtHead(ListNode node) {
        if (head == null) {
            // If list is empty, the new node will point to itself
            head = node;
            node.next = node;
        } else {
            // Insert at head and make last node point to the new head
            ListNode last = head;
            while (last.next != head) {
                last = last.next;
            }
            node.next = head; // Point new node to current head
            head = node;      // Update head to the new node
            last.next = head; // Update last node to point to new head
        }
    }

    // Method to create the linked list using an integer array
    public void createLinkedList(int[] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            ListNode newNode = new ListNode(arr[i]);
            insertNodeAtHead(newNode);
        }
    }

    // Method to convert the circular linked list to an array
    public int[] toArray() {
        if (head == null) {
            return new int[0];
        }

        HashSet<ListNode> seenNodes = new HashSet<>();
        int[] result = new int[100]; // Arbitrary size; can be adjusted or dynamically resized
        int count = 0;

        ListNode current = head;
        do {
            result[count++] = current.val;
            seenNodes.add(current);
            current = current.next;
        } while (current != head && !seenNodes.contains(current));

        // Trim the array to the actual size
        return Arrays.copyOf(result, count);
    }

    // Method to print the circular linked list
    public static void printCircularLinkedList(ListNode head) {
        if (head == null) {
            System.out.print("List is empty ");
            return;
        }

        ListNode current = head;
        HashSet<ListNode> seenNodes = new HashSet<>(); // To track nodes we've already printed

        do {
            System.out.print(current.val + " "); // Print node value
            seenNodes.add(current); // Add current node to seen nodes
            current = current.next;
            if (current == head) { // When we come back to the head node
                System.out.print("→ (head) ");
                break;
            }
            if (current != null) {
                System.out.print("→ ");
            }
        } while (current != null && !seenNodes.contains(current));
        System.out.println(); // Move to the next line after printing
    }

    // Method to convert a linked list to an array
    public static int[] linkedListToArray(ListNode head) {
        if (head == null) {
            return new int[0];
        }

        HashSet<ListNode> seenNodes = new HashSet<>();
        int[] result = new int[100]; // Arbitrary size, can be adjusted
        int count = 0;

        ListNode current = head;
        do {
            result[count++] = current.val;
            seenNodes.add(current);
            current = current.next;
        } while (current != head && !seenNodes.contains(current));

        // Trim the array to the actual size
        return java.util.Arrays.copyOf(result, count);
    }
}
