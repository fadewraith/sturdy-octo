package educativetutorials.codingpatterns.twofastandslowpointers.easy;

import educativetutorials.codingpatterns.commonutilities.LinkedList;
import educativetutorials.codingpatterns.commonutilities.LinkedListReversal;
import educativetutorials.codingpatterns.commonutilities.ListNode;

import java.util.Arrays;
import java.util.List;

public class PalindromeLL {

    public static boolean palindrome(ListNode head) {
        // Initialize slow and fast pointers to the head of the linked list
        ListNode slow = head;
        ListNode fast = head;

        // Find the middle of the linked list using the slow and fast pointers
        while (fast != null && fast.next != null) {
            // move slow one step forward
            slow = slow.next;
            // move fast two steps forward
            fast = fast.next.next;
        }
        // Reverse the second half of the linked list starting from the middle node
        ListNode revertData = LinkedListReversal.reverseLinkedList(slow);
        // Compare the first half of the linked list with the reversed second half of the linked list
        boolean check = compareTwoHalves(head, revertData);
        // Re-reverse the second half of the linked list to restore the original linked list
        LinkedListReversal.reverseLinkedList(revertData);
        // Return True if the linked list is a palindrome, else False
        if (check) {
            return true;
        }

        return false;

    }

    public static boolean compareTwoHalves(ListNode firstHalf, ListNode secondHalf) {
        // Compare the corresponding nodes of the first and second halves of the linked list
        while (firstHalf != null && secondHalf != null) {
            if (firstHalf.val != secondHalf.val) {
                return false;
            } else {
                firstHalf = firstHalf.next;
                secondHalf = secondHalf.next;
            }


        }
        return true;
    }

    // Driver code
    public static void main( String args[] ) {

        List<List<Integer>> inputList = Arrays.asList(
                Arrays.asList(2, 4, 6, 4, 2),
                Arrays.asList(0, 3, 5, 5, 0),
                Arrays.asList(9, 27, 4, 4, 27, 9),
                Arrays.asList(5, 4, 7, 9, 4, 5),
                Arrays.asList(5, 10, 15, 20, 15, 10, 5)
        );

        for(int i=0; i<inputList.size(); i++){
            System.out.print(i+1);
            LinkedList list = new LinkedList(inputList.get(i));
            System.out.print(".\tLinked list:  ");
            LinkedList.PrintList.display(list.head);
            System.out.print("\tIs it a palindrome?  ");
            boolean result = palindrome(list.head);
            if (result){
                System.out.println("Yes");
            }
            else {
                System.out.println("No");
            }
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }

    }
}
