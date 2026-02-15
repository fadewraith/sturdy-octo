package educativetutorials.codingpatterns.commonutilities;

import java.util.List;

public class LinkedList {
    public ListNode head;

    // Default constructor
    public LinkedList() {
        head = null;
    }

    // Constructor to initialize from a list of values
    public LinkedList(List<Integer> values) {
        head = null;
        createLinkedList(values);
    }

    // Function to create a linked list from a list of values
    private void createLinkedList(List<Integer> values) {
        if (values.isEmpty()) {
            head = null;
            return;
        }

        head = new ListNode(values.get(0));
        ListNode current = head;
        for (int i = 1; i < values.size(); i++) {
            current.next = new ListNode(values.get(i));
            current = current.next;
        }
    }

    public static class PrintList{
        // Function to display the linked list
        public static void display(ListNode head) {
            ListNode current = head;
            while (current != null) {
                System.out.print(current.val + " -> ");
                current = current.next;
            }
            System.out.println("None");
        }
    }

}

