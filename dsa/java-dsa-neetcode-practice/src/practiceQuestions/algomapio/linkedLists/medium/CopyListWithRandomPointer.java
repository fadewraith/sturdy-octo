package practiceQuestions.algomapio.linkedLists.medium;



import practiceQuestions.algomapio.linkedLists.Node;

import java.util.HashMap;
import java.util.Map;

public class CopyListWithRandomPointer {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Create a deep copy of a linked list where each node has a value, a next pointer, and a random pointer.
     * If the head is None, return None.
     * Initialize a dictionary to map original nodes to their clones.
     * Traverse the original list, creating a new node for each with the same value and storing the mapping.
     * Traverse the original list again, setting the next and random pointers of each new node using the dictionary mappings.
     * Return the cloned head node from the dictionary.
     * */

    private static Node solution(Node head) {
        if(head == null) return null;

        Map<Node, Node> oldToNew = new HashMap<>();
        Node current = head;

        // first pass: create all nodes and put them in the map
        while(current != null) {
            oldToNew.put(current, new Node(current.val));
            current = current.next;
        }

        // second pass: set next and random pointers
        current = head;
        while(current != null) {
            Node newNode = oldToNew.get(current);
            newNode.next = oldToNew.get(current.next);
            newNode.random = oldToNew.get(current.random);
            current = current.next;
        }

        return oldToNew.get(head);
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Copy List with Random Pointer
 * The “Copy List with Random Pointer” problem asks you to create a deep copy of a special linked list. Each node in the list contains:
 *
 * val – the integer value of the node
 * next – a pointer to the next node in the list
 * random – a pointer to any node in the list (or null)
 * A deep copy means all the nodes should be newly created, and none of them should point to any node from the original list. All connections, including both next and random pointers, must be preserved.
 *
 * Why This Problem Matters
 * This problem is a classic example of advanced linked list manipulation. It requires understanding how to duplicate both direct and arbitrary references between nodes. It builds important intuition for working with complex data structures like graphs or object graphs.
 *
 * Approach: Hash Map to Maintain Original-to-Copy Mapping
 * The simplest and most reliable way to solve this problem is to use a hash map (dictionary) that maps each original node to its corresponding copied node.
 *
 * Steps:
 * If the head is null, return null.
 * Create an empty dictionary oldToNew to store mappings from original nodes to copied nodes.
 * First pass: traverse the original list and create a copy of each node with the same value. Store these in the hash map.
 * Second pass: traverse the list again to assign next and random pointers for each copied node using the hash map:
 * copy.next = oldToNew[original.next]
 * copy.random = oldToNew[original.random]
 * Return oldToNew[head] as the new head of the deep-copied list.
 * Example Walkthrough
 * Consider the list:
 *
 * Node A (val = 1) → next: B, random: C
 * Node B (val = 2) → next: C, random: A
 * Node C (val = 3) → next: null, random: B
 * First pass creates new nodes: A′, B′, C′.
 * Second pass wires:
 *
 * A′.next = B′, A′.random = C′
 * B′.next = C′, B′.random = A′
 * C′.next = null, C′.random = B′
 * Time and Space Complexity
 * Time Complexity: O(n), where n is the number of nodes in the list. We perform two passes through the list.
 * Space Complexity: O(n) for the hash map that stores original-to-copy mappings.
 *
 * Edge Cases to Consider
 * Empty list (head is null) → return null
 * All random pointers are null → solution still works
 * Random pointers form cycles or self-references → handled by the mapping
 * Conclusion
 * The “Copy List with Random Pointer” problem is a great test of your understanding of deep copying, hashing, and reference manipulation. While the use of a hash map is straightforward, it lays the groundwork for more space-efficient solutions and shows how auxiliary data structures can simplify complex linked list operations.
 * */