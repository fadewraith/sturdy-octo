package practiceQuestions.algomapio.linkedlists.easy;

import practiceQuestions.algomapio.linkedlists.ListNode;

public class ReverseLinkedList {

    private static ListNode solution(ListNode head) {
        ListNode prev = null;
        ListNode current = head;

        while(current != null) {
            ListNode temp = current.next;
            current.next = prev;
            prev = current;
            current = temp;
        }

        return prev;
    }
}
