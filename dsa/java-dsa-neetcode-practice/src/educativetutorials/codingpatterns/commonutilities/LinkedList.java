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

    public static ListNode getNode(ListNode head, int pos){
        ListNode ptr = head;
        if (pos != -1){
            int p = 0;

            while (p < pos){
                ptr = ptr.next;
                p += 1;
            }

            return ptr;
        }
        return ptr;
    }

    public static int getLength(ListNode head)
    {
        ListNode temp = head;
        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
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

        public static void printListWithForwardArrow(ListNode head) {
            ListNode temp = head;

            while (temp != null) {
                System.out.print(temp.val); // print node value
                temp = temp.next;
                if (temp != null) {
                    System.out.print(" → ");
                } else{
                    // if this is the last node, print null at the end
                    System.out.print(" → null \n ");
                }
            }
        }
        public static void printListWithForwardArrowLoop(ListNode head) {
            ListNode temp = head;

            while (temp != null) {
                System.out.print(temp.val); // print node value
                temp = temp.next;
                if (temp != null) {
                    System.out.print(" → ");
                }
            }
        }
    }

}

