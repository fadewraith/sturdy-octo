package practiceQuestions.algomapio.linkedLists;

public class LinkedListCycle {
    
    /**
     * Detects if a cycle exists in a linked list using Floyd's Cycle-Finding Algorithm.
     * Also known as the "tortoise and hare" algorithm.
     * 
     * Time Complexity: O(n) - where n is the number of nodes in the list
     * Space Complexity: O(1) - uses only two pointers regardless of input size
     * 
     * @param head The head node of the linked list
     * @return true if a cycle is detected, false otherwise
     */
    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        
        ListNode slow = head;  // moves one step at a time
        ListNode fast = head;  // moves two steps at a time
        
        while (fast != null && fast.next != null) {
            slow = slow.next;          // move slow by 1
            fast = fast.next.next;     // move fast by 2
            
            if (slow == fast) {        // if they meet, there's a cycle
                return true;
            }
        }
        
        return false;  // reached end of list, no cycle
    }
    
    /**
     * Returns the node where the cycle begins, or null if there is no cycle.
     * 
     * @param head The head node of the linked list
     * @return The node where the cycle begins, or null if no cycle exists
     */
    public static ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        boolean hasCycle = false;
        
        // Find if cycle exists
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                hasCycle = true;
                break;
            }
        }
        
        // If no cycle found
        if (!hasCycle) {
            return null;
        }
        
        // Find the start of the cycle
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        
        return slow;  // or fast, they meet at the cycle start
    }
}
