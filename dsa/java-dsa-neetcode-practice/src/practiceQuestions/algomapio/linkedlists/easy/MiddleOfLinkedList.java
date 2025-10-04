package practiceQuestions.algomapio.linkedlists.easy;

import practiceQuestions.algomapio.linkedlists.ListNode;

public class MiddleOfLinkedList {

    private static ListNode solution(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while(fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }
}
