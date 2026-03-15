package educativetutorials.codingpatterns.commonutilities;

public class LinkedListReversal {

    public static ListNode reverseLinkedList(ListNode slowPtr){
        ListNode prev = null;
        ListNode next = null;
        ListNode curr = slowPtr;

        while (curr != null)
        {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}
