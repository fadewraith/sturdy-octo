package practiceQuestions.algomapio.heaps.hard;

import practiceQuestions.algomapio.linkedlists.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MergeKSortedLinkedLists {

    /**
     *  Step-by-Step Thought Process
     * Understand the problem: Merge k sorted linked lists into one sorted linked list.
     * Initialize a min-heap to store nodes from the linked lists, using each node's value and index for ordering.
     * Iterate through the input lists, pushing each non-null head node onto the heap with its value and list index.
     * Create a dummy node to build the merged list, and set a current pointer to it.
     * While the heap is not empty, pop the node with the smallest value.
     * Attach the popped node to the current pointer's next, and move the current pointer forward.
     * If the popped node has a next node, push the next node onto the heap with its value and the same list index.
     * Return the next node of the dummy node as the head of the merged list.
     * */

    public ListNode mergeKLists(ListNode[] lists) {
//        PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a.data, b.data));

        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(Comparator.comparingInt(n -> n.data));



        // Add the initial nodes of each list to the heap
        for (ListNode node : lists) {
            if (node != null) {
                minHeap.offer(node);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;

        while (!minHeap.isEmpty()) {
            ListNode node = minHeap.poll();
            cur.next = node;
            cur = node;
            if (node.next != null) {
                minHeap.offer(node.next);
            }
        }

        return dummy.next;
    }
}

/**
 * Detailed Explanation
 * Understanding the Problem: Merge K Sorted Linked Lists
 * The “Merge K Sorted Linked Lists” problem challenges us to combine multiple sorted linked lists into a single sorted list. This is a common problem in systems that deal with merging data streams, merging multiple sorted files, or implementing external sorting in databases.
 *
 * Each of the k input linked lists is already sorted in ascending order. The output should also be sorted, and the original node objects must be reused, not copied or rebuilt. This makes the problem slightly more complex than merging arrays.
 *
 * Brute-Force Strategy: Using a Min Heap
 * A widely accepted approach to this problem uses a min heap (priority queue). The idea is to always have the smallest node available at the top of the heap. Here's how it works:
 *
 * First, we push the head of each non-null linked list into the heap. Since each node contains a value and a pointer to the next node, we can extract the smallest value node efficiently using the heap's pop operation. Once popped, this node is appended to the result list, and if it has a next node, we push that next node into the heap.
 *
 * This process continues until the heap is empty. Since the heap always contains the next smallest available node, the result list will be correctly sorted.
 *
 * Efficiency Analysis
 * The time complexity of this approach is O(N log k), where:
 *
 * N is the total number of nodes across all linked lists.
 * k is the number of linked lists.
 * Each node is inserted and removed from the heap exactly once, and each operation takes O(log k) time.
 * The space complexity is O(k) for storing up to k nodes in the heap at any point in time.
 *
 * Alternative Approaches
 * Another common method is to use the Divide and Conquer technique. You recursively divide the list of linked lists into halves, merge each pair using a standard merge operation for two sorted lists, and combine the results. This approach also has a time complexity of O(N log k) and is often more memory-efficient in languages with heavier heap implementations.
 * */