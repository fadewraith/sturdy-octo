package educativetutorials.codingpatterns.twopointers.medium;

import educativetutorials.codingpatterns.commonutilities.LinkedList;
import educativetutorials.codingpatterns.commonutilities.ListNode;

import java.util.Arrays;
import java.util.List;

public class RemoveNthNodeFromEndOfList {

    /**
     * This approach requires two traversals of the linked list. In the first traversal, count the total nodes in the linked list. Let’s say the total number of nodes is N.
     * Then, we can identify the position of the node to be removed using the formula (N - n + 1). because we are counting it from the end. Traverse the list again, but this time stop at the
     * (N - n)th node, and remove its next node. For this, make the next pointer of the (N - n)th node point to the (N - n + 1)th node to skip the target node.
     * */

    private static ListNode solution(ListNode head, int n) {
        // Point two pointers, right and left, at head.
        ListNode left = head;
        ListNode right = head;

        // Move right pointer n elements away from the left pointer.
        for(int i = 0; i < n; i++) {
            right = right.next;
        }

        // Removal of the head node
        if(right == null) {
            return head.next;
        }

        // Move both pointers until right pointer reaches the last node.
        while(right.next != null) {
            right = right.next;
            left = left.next;
        }

        // At this point, left pointer points to (n-1)th element.
        // So link it to next to next element of left.
        left.next = left.next.next;

        return head;


    }


    public static void main(String[] args) {
        List<List<Integer>> inputs = Arrays.asList(
                Arrays.asList(23, 89, 10, 5, 67, 39, 70, 28),
                Arrays.asList(34, 53, 6, 95, 38, 28, 17, 63, 16, 76),
                Arrays.asList(288, 224, 275, 390, 4, 383, 330, 60, 193),
                Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9),
                Arrays.asList(69, 8, 49, 106, 116, 112, 104, 129, 39, 14, 27, 12)
        );

        int[] n = {4, 1, 6, 9, 11};

        for (int i = 0; i < inputs.size(); i++) {
            LinkedList inputLinkedList = new LinkedList(inputs.get(i));
            System.out.print((i + 1) + ".\tLinked List:\t\t");
            LinkedList.PrintList.display(inputLinkedList.head);
            System.out.print("\n\tn = " + n[i]);
            System.out.print("\n\tUpdated Linked List:\t");
            LinkedList.PrintList.display(solution(inputLinkedList.head, n[i]));
            System.out.println();
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}
