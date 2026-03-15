package educativetutorials.codingpatterns.twofastandslowpointers.medium;

import educativetutorials.codingpatterns.commonutilities.CircularLinkedList;
import educativetutorials.codingpatterns.commonutilities.ListNode;

public class SplitACLL {

    // Method to split the circular linked list
    // Method to split a circular linked list into two halves
    private static ListNode[] splitCircularLinkedList(ListNode head) {
        // Initialize slow and fast pointers to the head of the list
        ListNode slow = head;
        ListNode fast = head;

        // Move slow by one step and fast by two steps to find the middle of the list
        while(fast.next != head && fast.next.next != head) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Set head1 to the start of the first half
        ListNode head1 = head;
        // Set head2 to the start of the second half, which is the node after slow
        ListNode head2 = slow.next;
        // Make the first half circular by pointing slow's next to head1
        slow.next = head1;

        // Reuse the fast pointer to find the end of the second half
        fast = head2;
        while (fast.next != head) {
            fast = fast.next; // Move to the end of the second half
        }
        // Make the second half circular by linking the last node to head2
        fast.next = head2;

        // Return the heads of the two split circular linked lists
        return new ListNode[]{head1, head2};

    }

    public static void main(String[] args) {
        int[][] lists = {
                {1, 5, 7},
                {2, 6, 1, 5},
                {3, 1, 4, 2, 5},
                {8, 10, 12, 14, 16, 18},
                {9, 10}
        };

        for (int i = 0; i < lists.length; i++) {
            CircularLinkedList inputLinkedList = new CircularLinkedList();
            inputLinkedList.createLinkedList(lists[i]);
            System.out.print((i + 1) + ". Linked list: ");
            CircularLinkedList.printCircularLinkedList(inputLinkedList.head);

            // Get the split lists
            ListNode[] splitLists = splitCircularLinkedList(inputLinkedList.head);
            int[] splitList1 = CircularLinkedList.linkedListToArray(splitLists[0]);
            int[] splitList2 = CircularLinkedList.linkedListToArray(splitLists[1]);
            System.out.println("\n   Split Lists: [" + java.util.Arrays.toString(splitList1) + ", " + java.util.Arrays.toString(splitList2) + "]");
            System.out.println(new String(new char[100]).replace('\0', '-')); // Printing 100 dashes
        }
    }

}

/**
 * The core idea is to use the slow and fast pointer approach, a well-known technique for finding the middle of a linked list in an optimized manner. The slow pointer moves one step at a time, while the fast pointer moves two steps, ensuring that when the fast pointer completes its traversal, the slow pointer will be at the midpoint. Once the middle is found, we break the circular connection at this midpoint, forming two separate lists. The first half starts from the head and extends up to the slow pointer, with its last node pointing back to the head to maintain the circular structure. The second half begins from the next node after the slow pointer and extends to the original last node, which is then linked back to this second half’s new head, ensuring both resulting lists remain circular. Finally, the two newly formed circular linked lists are returned as separate head pointers representing the start of each half.
 *
 * Now, let’s walk through the steps of the solution:
 *
 * We initialize the slow and fast pointers to the head of the list. The slow pointer moves one node at a time while the fast pointer moves two nodes at a time.
 *
 * We iterate through the list using the fast and slow pointers until the fast pointer has reached back to the head, ensured by the conditions fast.next != head and fast.next.next != head.
 *
 * After iterating, the slow pointer will be at the middle point of the list, while the fast pointer will be pointing back to the head. This middle point node serves as the point where we will split the list into two halves.
 *
 * The first circular linked list will start from the original head (head1 = head). Before modifying slow.next, we store slow.next in head2 to retain the starting node of the second half. Then, we update slow.next to point back to head1, effectively closing the first circular half.
 *
 * The second half of the list begins from the node immediately following the middle point, which we stored in head2 in the previous step. This prevents losing the reference to the second half’s start after updating slow.next for the first half.
 *
 * Next, we need to ensure that the second half is also circular. To do this, we traverse the second half starting from head2 using the fast pointer. The fast moves throughout the list until it points back to the head.
 *
 * Once the fast pointer reaches the head, we update fast.next=head2, closing the second circular list.
 * */
