package educativetutorials.codingpatterns.onetwopointers.medium;

import java.util.Arrays;
import java.util.List;

public class IntersectionOfTwoLinkedLists {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // Initialize two pointers, each starting at the head of one list
        ListNode ptrA = headA;
        ListNode ptrB = headB;

        // Traverse both lists until the two pointers meet
        // If the lists intersect, they will eventually point to the same node
        // If not, both will become null at the same time and the loop will stop
        while (ptrA != ptrB) {
            // When ptrA reaches the end of its list, move it to the head of list B
            // Otherwise, move it to the next node
            ptrA = (ptrA == null) ? headB : ptrA.next;

            // When ptrB reaches the end of its list, move it to the head of list A
            // Otherwise, move it to the next node
            ptrB = (ptrB == null) ? headA : ptrB.next;
        }

        // When the loop ends, ptrA and ptrB are either both null (no intersection)
        // or both point to the intersection node
        return ptrA;
    }

    // Driver code
    public static void main(String[] args) {
        List<List<Integer>> listA_values = Arrays.asList(
                Arrays.asList(4, 1, 8, 4, 5),
                Arrays.asList(1, 2, 3),
                Arrays.asList(1, 2, 3),
                Arrays.asList(2, 6, 4),
                Arrays.asList(1),
                Arrays.asList(2, 3, 6, 21, 5)
        );

        List<List<Integer>> listB_values = Arrays.asList(
                Arrays.asList(5, 6, 6, 8, 4, 5),
                Arrays.asList(4, 5, 6),
                Arrays.asList(1, 2, 3),
                Arrays.asList(1, 5, 4),
                Arrays.asList(1),
                Arrays.asList(7, 21, 5)
        );

        int[] skipA = {2, 3, 0, 2, 0, 3};
        int[] skipB = {3, 3, 0, 2, 0, 1};
        int[] intersect_val = {8, 0, 1, 4, 1, 21};

        for (int i = 0; i < listA_values.size(); i++) {
            LinkedList listA = new LinkedList(listA_values.get(i));
            LinkedList listB = new LinkedList(listB_values.get(i));

            // Create intersection if valid
            if (skipA[i] == 0 && skipB[i] == 0) {
                listB.head = listA.head;
            } else if (skipA[i] < listA_values.get(i).size() && skipB[i] < listB_values.get(i).size()) {
                ListNode nodeA = listA.head;
                for (int j = 0; j < skipA[i]; j++) nodeA = nodeA.next;

                ListNode nodeB = listB.head;
                for (int j = 0; j < skipB[i] - 1; j++) nodeB = nodeB.next;

                nodeB.next = nodeA;
            }

            IntersectionOfTwoLinkedLists sol = new IntersectionOfTwoLinkedLists();
            ListNode result = sol.getIntersectionNode(listA.head, listB.head);

            System.out.print((i + 1) + ".\tList A: ");
            PrintList.display(listA.head);

            System.out.print("\tList B: ");
            PrintList.display(listB.head);

            System.out.println("\tskipA: " + skipA[i]);
            System.out.println("\tskipB: " + skipB[i]);
            System.out.println("\tintersect_val: " + intersect_val[i]);

            if (result != null)
                System.out.println("\n\tOutput: Intersected at " + result.val);
            else
                System.out.println("\n\tOutput: No intersection");

            System.out.println("-".repeat(100));
        }
    }
}


class LinkedList {
    ListNode head;

    // Default constructor
    public LinkedList() {
        head = null;
    }

    // Constructor to initialize from a list of values
    public LinkedList(List<Integer> values) {
        head = null;
        createLinkedList(values);
    }

    // Function to create a linked list from a list of values
    private void createLinkedList(List<Integer> values) {
        if (values.isEmpty()) {
            head = null;
            return;
        }

        head = new ListNode(values.get(0));
        ListNode current = head;
        for (int i = 1; i < values.size(); i++) {
            current.next = new ListNode(values.get(i));
            current = current.next;
        }
    }

}

class PrintList{
    // Function to display the linked list
    public static void display(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val + " -> ");
            current = current.next;
        }
        System.out.println("None");
    }
}

class ListNode {
    int val;
    ListNode next;

    // Constructor
    public ListNode(int val) {
        this.val = val;
        this.next = null;
    }
}