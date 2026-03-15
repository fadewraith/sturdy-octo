package educativetutorials.codingpatterns.twofastandslowpointers.medium;

import educativetutorials.codingpatterns.commonutilities.LinkedList;
import educativetutorials.codingpatterns.commonutilities.ListNode;

import java.util.Arrays;
import java.util.List;

public class LinkedListCycleIV {

    private static ListNode bruteForce(ListNode head) {

        // if list is empty or has one node, nothing to remove
        if(head == null || head.next == null) {
            return head;
        }

        ListNode current = head;

        // Outer loop: move current pointer one node at a time
        while (current != null) {

            ListNode runner = head;

            // Inner loop: check all nodes before current
            while(runner != current) {
                // If current.next points to any previous node,
                // that means a cycle exists
                if(current.next == runner) {
                    current.next = null;

                    return head;
                }
                runner = runner.next;
            }
            current = current.next;
        }

        // If we reach here, no cycle found
        return head;
    }

    private static ListNode removeCycle(ListNode head) {
        if (head == null) {
            return null; // No cycle in an empty list
        }

        ListNode slow = head, fast = head;

        // Detect cycle using Floyd’s Cycle Detection Algorithm
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) { // Cycle detected
                break;
            }
        }

        // If no cycle is found, return the list unchanged
        if (fast == null || fast.next == null) {
            return head;
        }

        // Find the starting node of the cycle
        slow = head; // Reset slow to head
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        // Find the last node in the cycle and remove it
        while (fast.next != slow) {
            fast = fast.next;
        }
        fast.next = null; // Break the cycle

        return head; // Return the modified linked list with the cycle removed
    }
    // Driver code
    public static void main(String args[]) {
        List<List<Integer>> inputList = Arrays.asList(
                Arrays.asList(2, 4, 6, 8, 10, 12),
                Arrays.asList(1, 3, 5, 7, 9, 11),
                Arrays.asList(0, 1, 2, 3, 4, 6),
                Arrays.asList(3, 4, 7, 9, 11, 17),
                Arrays.asList(5, 1, 4, 9, 2, 3)
        );
        int[] pos = { 0, -1, 1, -1, 2 };
        for (int i = 0; i < inputList.size(); i++) {
            LinkedList list = new LinkedList(inputList.get(i));
            System.out.print(i + 1 + ".\tInput:");
            System.out.print("\t");
            if (pos[i] == -1) {
                LinkedList.PrintList.printListWithForwardArrow(list.head);
            } else {
                LinkedList.PrintList.printListWithForwardArrowLoop(list.head);
            }
            System.out.println("\n\tpos: " + pos[i]);

            if (pos[i] != -1) {
                int length = list.getLength(list.head);
                ListNode lastNode = list.getNode(list.head, length - 1);
                lastNode.next = list.getNode(list.head, pos[i]);
            }
            System.out.print("\n\tAfter Cycle Removed = ");
            LinkedList.PrintList.printListWithForwardArrow(removeCycle(list.head));
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}
