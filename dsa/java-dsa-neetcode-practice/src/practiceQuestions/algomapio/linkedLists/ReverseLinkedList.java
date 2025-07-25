package practiceQuestions.algomapio.linkedLists;

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
