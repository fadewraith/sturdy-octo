package practiceQuestions.algomapio.linkedlists.medium;

import practiceQuestions.algomapio.linkedlists.ListNode;

public class RemoveNthNodeFromEndOfList {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Remove the nth node from the end of a linked list and return the head.
     * Create a dummy node pointing to head to handle edge cases (e.g., removing the first node).
     * Initialize two pointers, behind and ahead, both starting at dummy.
     * Move ahead n+1 steps forward to create a gap of n nodes between ahead and behind.
     * While ahead is not None, move both behind and ahead one step forward.
     * When ahead reaches the end, behind.next points to the node to be removed; set behind.next to behind.next.next to skip it.
     * Return dummy.next as the modified list’s head.
     * */

    private static ListNode solution(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        // Initialize two pointers: ahead and behind, both pointing to dummy
        ListNode behind = dummy, ahead = dummy;

        // Move ahead n + 1 steps forward to create a gap of n between ahead and behind
        for(int i = 0; i <= n; i++) {
            ahead = ahead.next;
        }

        // While ahead is not null: Move both pointers one step at a time.
        // Now, behind points to the node just before the one we want to remove.
        while(ahead != null) {
            behind = behind.next;
            ahead = ahead.next;
        }

        // Remove the node by setting behind.next = behind.next.next.
        behind.next = behind.next.next;
        // Return dummy.next as the new head of the list.
        return dummy.next;
    }


}


/**
 * Detailed Explanation
 * Understanding the Problem: Remove Nth Node From End of List
 * The “Remove Nth Node From End of List” problem asks you to remove the nth node from the end of a singly linked list, and return the modified list’s head.
 *
 * Example:
 *
 * Input: 1 → 2 → 3 → 4 → 5, n = 2
 * Output: 1 → 2 → 3 → 5
 * Explanation: The second node from the end is 4; after removing it, the list becomes 1 → 2 → 3 → 5.
 * Why This Problem Matters
 * This is a classic linked list problem that highlights the use of the two-pointer (fast and slow) technique. It also teaches how to modify list structure without explicitly counting all elements — a useful skill in memory-constrained and real-time environments.
 *
 * Optimal Approach: Two Pointers with a Dummy Node
 * The idea is to maintain a fixed gap of n between two pointers so that when the fast pointer reaches the end, the slow pointer is at the node just before the one to be removed. Using a dummy node simplifies edge cases like removing the head.
 *
 * Steps:
 * Create a dummy node that points to the head of the list.
 * Initialize two pointers: ahead and behind, both pointing to dummy.
 * Move ahead n + 1 steps forward to create a gap of n between ahead and behind.
 * While ahead is not null:
 * Move both pointers one step at a time.
 * Now, behind points to the node just before the one we want to remove.
 * Remove the node by setting behind.next = behind.next.next.
 * Return dummy.next as the new head of the list.
 * Example Walkthrough
 * Input: 1 → 2 → 3 → 4 → 5, n = 2
 * Steps:
 *
 * dummy → 1 → 2 → 3 → 4 → 5
 * ahead moves to node 3 (n+1 = 3 steps), behind is still at dummy
 * Move ahead and behind together until ahead reaches null
 * behind is at node 3 → behind.next = node 4 → skip node 4
 * Final list: 1 → 2 → 3 → 5
 * Time and Space Complexity
 * Time Complexity: O(n), where n is the number of nodes in the list. Each node is visited at most once.
 * Space Complexity: O(1), since only pointers and no additional memory are used.
 *
 * Edge Cases to Consider
 * Removing the head (n equals the list’s length) → dummy node helps simplify this
 * Single-node list, n = 1 → return null
 * Empty list → handle safely, possibly return null
 * Conclusion
 * The “Remove Nth Node From End of List” problem elegantly demonstrates how to use the two-pointer technique to solve linked list problems in one pass. It also emphasizes how dummy nodes help handle edge cases cleanly. Mastering this pattern will help you solve a wide range of similar problems efficiently.
 * */