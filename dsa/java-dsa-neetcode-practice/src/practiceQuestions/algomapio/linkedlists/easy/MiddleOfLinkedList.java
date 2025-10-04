package practiceQuestions.algomapio.linkedLists.easy;

import practiceQuestions.algomapio.linkedLists.ListNode;

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
