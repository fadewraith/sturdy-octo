package practiceQuestions.algomapio.linkedLists.easy;

import practiceQuestions.algomapio.linkedLists.ListNode;

public class MergeTwoSortedLists {

    private static ListNode solution(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        while(l1 != null && l2 != null) {
            if(l1.data < l2.data) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }

        current.next = l1 != null ? l1 : l2;

        return dummy.next;
    }
}
