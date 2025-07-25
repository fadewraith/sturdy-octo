package practiceQuestions.algomapio.linkedLists;

import practiceQuestions.algomapio.linkedLists.ListNode;

public class RemoveDuplicatesFromSortedList {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Given a sorted array nums, remove duplicates in-place and return the length of the array with unique elements.
     * Initialize a pointer j to 1, representing the position to place the next unique element.
     * Iterate through the array with index i from 1 to n-1 (where n is the length of nums):
     * If nums[i] is not equal to nums[i-1], it’s a new unique element.
     * Assign nums[i] to nums[j] and increment j.
     * Return j as the length of the array with unique elements.
     * */

    private static ListNode solution(ListNode head) {
        ListNode current = head;
        while(current != null && current.next != null) {
            if(current.data == current.next.data) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }

        return head;
    }
}
