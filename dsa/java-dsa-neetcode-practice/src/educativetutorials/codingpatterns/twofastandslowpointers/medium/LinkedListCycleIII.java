package educativetutorials.codingpatterns.twofastandslowpointers.medium;

import educativetutorials.codingpatterns.commonutilities.LinkedList;
import educativetutorials.codingpatterns.commonutilities.ListNode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinkedListCycleIII {

    private static int bruteForce(ListNode head) {
        if(head == null) return 0;

        Map<ListNode, Integer> visited = new HashMap<>();
        ListNode current = head;

        int index = 0;

        while(current != null) {
            // if node already seen -> cycle found
            if(visited.containsKey(current)) {
                return index - visited.get(current);
            }

            visited.put(current, index);
            current = current.next;
            index++;
        }

        return 0;
    }

    private static int countCycleLength(ListNode head) {
        // Initialize the slow & fast pointer
        ListNode slow = head, fast = head;

        // Traverse the linked list to detect a cycle
        while(fast != null && fast.next != null) {
            slow = slow.next;          // Move slow pointer by 1 step
            fast = fast.next.next;     // Move fast pointer by 2 steps

            // If slow and fast pointers meet, a cycle exists
            if (slow == fast) {
                int length = 1;        // Initialize cycle length counter
                slow = slow.next;      // Move slow pointer by one step to start counting

                // Continue moving the slow pointer until it meets the fast pointer again
                while (slow != fast) {
                    length++;          // Increment the length counter
                    slow = slow.next;  // Move to the next node in the cycle
                }

                return length;         // Return the total length of the cycle
            }
        }

        return 0;
    }


    public static void main(String args[]) {
        List<List<Integer>> inputList = Arrays.asList(
                Arrays.asList(2, 4, 6, 8, 10, 12),
                Arrays.asList(1, 3, 5, 7, 9, 11),
                Arrays.asList(0, 1, 2, 3, 4, 6),
                Arrays.asList(3, 4, 7, 9, 11, 17),
                Arrays.asList(5, 1, 4, 9, 2, 3)
        );
        int[] pos = { 0, -1, 1, -1, 2 };
        for (int i = 0; i < inputList.size(); i++) {
            LinkedList list = new LinkedList(inputList.get(i));
            System.out.print(i + 1 + ".\tInput:");
            System.out.print("\t");
            if (pos[i] == -1) {
                LinkedList.PrintList.printListWithForwardArrow(list.head);
            } else {
                LinkedList.PrintList.printListWithForwardArrowLoop(list.head);
            }
            System.out.println("\n\tpos: " + pos[i]);

            if (pos[i] != -1) {
                int length = list.getLength(list.head);
                ListNode lastNode = list.getNode(list.head, length - 1);
                lastNode.next = list.getNode(list.head, pos[i]);
            }
            System.out.println("\n\tCycle length = " + countCycleLength(list.head));
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}
