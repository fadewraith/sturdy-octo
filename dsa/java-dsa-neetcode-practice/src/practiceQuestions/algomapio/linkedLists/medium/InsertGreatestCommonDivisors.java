package practiceQuestions.algomapio.linkedLists.medium;

import practiceQuestions.algomapio.linkedLists.ListNode;

import java.math.BigInteger;

public class InsertGreatestCommonDivisors {

    /**
     * Step-by-Step Thought Process
     * The problem requires inserting a node with the greatest common divisor (GCD) of the values of two adjacent nodes in a linked list between them, for every pair of adjacent nodes, and returning the modified list.
     *
     * The solution iterates through the linked list, computing GCDs and inserting nodes in-place. Here’s how it works:
     *
     * Pointer Setup: We use two pointers: prev pointing to the current node and cur pointing to the next node, starting with prev = head and cur = head.next.
     * Iterating and Inserting: While cur is not null, for each pair of nodes:
     * Compute the GCD of prev.val and cur.val using the math.gcd function.
     * Create a new node g with the GCD value.
     * Insert g between prev and cur by setting prev.next = g and g.next = cur.
     * Advance pointers: set prev = cur and cur = cur.next to process the next pair.
     * Result: Return the head of the modified list, which now includes GCD nodes between each original pair.
     * Why This Works: The solution processes each pair of adjacent nodes exactly once, inserting the GCD node in-place without disrupting the list’s structure. The GCD computation ensures the correct value is inserted, and pointer updates maintain the linked list’s integrity.
     * Time and Space Complexity: The solution visits each node once, and for each pair, computes the GCD, which takes O(log min(a, b)) time (where a and b are the node values). For n nodes, there are n-1 pairs, so the time complexity is O(n * log min(a, b)). The space complexity is O(1) for pointer operations, excluding the new nodes created (which are part of the output).
     * */

    private static int gcd(int a, int b) {
        return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
    }

    private static ListNode solution(ListNode head) {
        ListNode prev = head;
        ListNode current = head.next;

        while(current != null) {
            int gcd = gcd(current.data, prev.data);
            ListNode g = new ListNode(gcd);
            prev.next = g;
            g.next = current;
            prev = current;
            current = current.next;
        }
        return head;
    }

    public static void main(String[] args) {
        // Example: 18 -> 6 -> 12
        ListNode head = new ListNode(18, new ListNode(6, new ListNode(12)));
        ListNode result = solution(head);

        // Print the modified list
        ListNode curr = result;
        while (curr != null) {
            System.out.print(curr.data);
            if (curr.next != null) System.out.print(" -> ");
            curr = curr.next;
        }
        // Expected output: 18 -> 6 -> 6 -> 6 -> 12
    }

}



/**
 * Detailed Explanation
 * Understanding the Problem
 * The goal of this problem is to traverse a singly linked list and insert a new node between every pair of adjacent nodes. The new node should contain the Greatest Common Divisor (GCD) of the values of the two adjacent nodes. This modification must be done in-place, and the updated list should be returned.
 *
 * GCD Insertion Strategy
 * To solve this problem efficiently, we use two pointers to traverse the linked list:
 *
 * prev – initially pointing to the current node.
 * cur – pointing to the next node in the list.
 * For each pair of nodes (prev, cur), we compute the GCD of their values. We then create a new node with this GCD value and insert it between prev and cur by adjusting the pointers accordingly. After inserting the GCD node, we move prev to cur and cur to cur.next, repeating the process until the end of the list is reached.
 *
 * Step-by-Step Example
 * Consider the input linked list: 18 → 6 → 12.
 *
 * We compute:
 *
 * GCD(18, 6) = 6 → insert 6 between 18 and 6.
 * GCD(6, 12) = 6 → insert 6 between 6 and 12.
 * Final list becomes: 18 → 6 → 6 → 6 → 12.
 *
 * Why This Works
 * This approach works effectively because we preserve the original node order and insert new nodes without disrupting existing links. By always moving two steps ahead (past the inserted node), we ensure we process each original pair exactly once. The GCD function provides accurate values for insertion.
 *
 * Time and Space Complexity
 * Time Complexity: O(n × log k), where n is the number of nodes and k is the average magnitude of the node values. The math.gcd function runs in O(log k) time, and we compute it once per adjacent pair (which is n-1 pairs).
 *
 * Space Complexity: O(1) auxiliary space. We only use pointers and modify the linked list in-place. The newly created nodes are part of the final answer and not considered extra space.
 *
 * Conclusion
 * The problem of inserting GCDs between adjacent nodes in a linked list is efficiently solved using a simple traversal with pointer manipulation. By leveraging the sorted structure and using the math.gcd function, we can insert new values in a single pass, keeping the time and space complexity minimal. This is a common pattern in linked list problems, especially when in-place modifications are required.
 * */