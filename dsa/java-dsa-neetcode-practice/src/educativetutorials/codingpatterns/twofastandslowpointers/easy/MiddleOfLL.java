package educativetutorials.codingpatterns.twofastandslowpointers.easy;

import educativetutorials.codingpatterns.commonutilities.ListNode;

public class MiddleOfLL {

    public static ListNode middleNode(ListNode head) {

        // Create two pointers, slow and fast ,initially pointing to the head
        ListNode slow = head;
        ListNode fast = head;

        // Traverse the linked list until fast reaches at the last node or NULL
        while (fast != null && fast.next != null) {

            // Move the slow pointer one step ahead
            slow = slow.next;

            // Move the fast pointer two steps ahead
            fast = fast.next.next;
        }

        // Return the slow pointer
        return slow;
    }
}
