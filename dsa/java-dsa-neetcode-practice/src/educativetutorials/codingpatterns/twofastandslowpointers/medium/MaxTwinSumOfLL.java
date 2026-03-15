package educativetutorials.codingpatterns.twofastandslowpointers.medium;

import educativetutorials.codingpatterns.commonutilities.LinkedList;
import educativetutorials.codingpatterns.commonutilities.ListNode;

import java.util.Arrays;
import java.util.List;

public class MaxTwinSumOfLL {

    public static int twinSum(ListNode head) {
        // Initialize fast and slow pointers at the head of the linked list
        ListNode slow = head;
        ListNode fast = head;

        // Find the middle node of the linked list using fast and slow pointers
        while (fast != null && fast.next != null) {
            // Move the slow pointer one step forward
            slow = slow.next;
            // Move the fast pointer two steps forward
            fast = fast.next.next;
        }

        // Set curr at the middle node (slow) to reverse the second half of the linked list
        ListNode curr = slow;
        ListNode prev = null;

        // Iterate through the list until curr reaches null
        while (curr != null) {
            // Save curr.next for later use
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }

        // Initialize maxSum with 0 to keep track of the maximum twin sum found so far
        int maxSum = 0;

        // Set curr at the head of the linked list
        curr = head;

        // Iterate through the list until prev reaches null
        while (prev != null) {
            // Update maxSum if the current twin sum is greater than maxSum
            maxSum = Math.max(maxSum, curr.val + prev.val);

            // Move both prev and curr pointers forward
            prev = prev.next;
            curr = curr.next;
        }

        // Return maxSum as the maximum twin sum of the given linked list
        return maxSum;
    }

    // Driver code
    public static void main(String[] args) {
        List<List<Integer>> lists = Arrays.asList(
                Arrays.asList(2, 3, 5, 7),
                Arrays.asList(81, 144, 64, 121, 25, 49),
                Arrays.asList(4, 5, 6, 7),
                Arrays.asList(1, 1000),
                Arrays.asList(11, 77, 44, 99, 22, 66, 55, 88)
        );

        for (int i = 0; i < lists.size(); ++i) {
            LinkedList inputLinkedList = new LinkedList(lists.get(i));
            System.out.print((i + 1) + ".\tLinked list: ");
            LinkedList.PrintList.display(inputLinkedList.head);
            System.out.println("\tMaximum twin sum: " + twinSum(inputLinkedList.head));
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}
