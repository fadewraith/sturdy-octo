package skiplist;

/**
 * SKIP LIST
 * 
 * What it is:
 * A probabilistic (randomized) data structure that allows O(log N) search, insertion, 
 * and deletion within an ordered sequence of elements. It achieves the performance of a 
 * balanced tree (like AVL or Red-Black), but uses linked lists instead of trees.
 * 
 * Approach/Strategy:
 * It consists of a base Linked List where all elements are sorted.
 * We then stack multiple layers of "express lane" linked lists on top of it.
 * - When an element is inserted, we flip a virtual coin. If Heads, it gets promoted 
 *   to the next layer up. We keep flipping until we hit Tails or the max level.
 * - To SEARCH, we start at the absolute highest level. We move right as long as the 
 *   next node is less than our target. If it's greater, we "drop down" one level and 
 *   continue moving right. This allows us to skip massive sections of the list!
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity (Avg) | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Search         | O(log N)              | O(1) iterative   | Express lane skipping
 * Insert         | O(log N)              | O(1)             | Requires search + coin flips
 * Delete         | O(log N)              | O(1)             | Requires search
 * Space          | -                     | O(N log N) avg   | Extra pointers per node
 * 
 * Real-world analogy:
 * A subway system. The lowest level is the local train that stops at every single station. 
 * The next level up is an express train that stops every 5 stations. The top level is a 
 * super-express that stops every 20 stations. To get to station 43, you take the 
 * super-express to 40, drop down to the local train, and go 41, 42, 43.
 */
public class SkipList {

    // Maximum level for this skip list
    private static final int MAX_LEVEL = 16;
    
    // Probability for a node to be promoted to the next level
    private static final double PROBABILITY = 0.5;

    private static class Node {
        int value;
        // forward[i] holds a pointer to the next node at level `i`
        Node[] forward;

        public Node(int value, int level) {
            this.value = value;
            this.forward = new Node[level + 1];
        }
    }

    private final Node head;
    private int currentMaxLevel; // Current highest level that actually has nodes

    public SkipList() {
        this.currentMaxLevel = 0;
        // Head is a dummy node that spans all possible levels. Value doesn't matter.
        this.head = new Node(-1, MAX_LEVEL);
    }

    /**
     * Randomly generates a level for a new node using a coin flip.
     */
    private int randomLevel() {
        int lvl = 0;
        // Keep flipping "Heads" (random < 0.5) and stop if "Tails" or MAX_LEVEL hit
        while (Math.random() < PROBABILITY && lvl < MAX_LEVEL) {
            lvl++;
        }
        return lvl;
    }

    /**
     * Searches for a value in O(log N) average time.
     */
    public boolean search(int target) {
        Node current = head;
        
        // Start from the highest active level and work downwards
        for (int i = currentMaxLevel; i >= 0; i--) {
            // Keep moving right as long as the next node's value is strictly less than target
            while (current.forward[i] != null && current.forward[i].value < target) {
                current = current.forward[i];
            }
        }
        
        // Drop to the base level (0). The target must be the immediate next node.
        current = current.forward[0];
        
        return current != null && current.value == target;
    }

    /**
     * Inserts a value in O(log N) average time.
     */
    public void insert(int value) {
        // update[] keeps track of the rightmost node at each level before we drop down.
        // These are the nodes whose forward pointers we might need to update!
        Node[] update = new Node[MAX_LEVEL + 1];
        Node current = head;

        for (int i = currentMaxLevel; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value < value) {
                current = current.forward[i];
            }
            update[i] = current; // Save the path
        }

        // Move to the actual spot in level 0
        current = current.forward[0];

        // If the value doesn't already exist
        if (current == null || current.value != value) {
            int newLvl = randomLevel();
            
            // If the new node randomly rolled a level higher than any existing node,
            // we must update the `update` array to track the head for these new high levels
            if (newLvl > currentMaxLevel) {
                for (int i = currentMaxLevel + 1; i <= newLvl; i++) {
                    update[i] = head;
                }
                currentMaxLevel = newLvl;
            }

            // Create the new node and splice it in at all required levels
            Node newNode = new Node(value, newLvl);
            for (int i = 0; i <= newLvl; i++) {
                newNode.forward[i] = update[i].forward[i];
                update[i].forward[i] = newNode;
            }
        }
    }

    /**
     * Deletes a value in O(log N) average time.
     */
    public void delete(int value) {
        Node[] update = new Node[MAX_LEVEL + 1];
        Node current = head;

        // Trace the path to the node
        for (int i = currentMaxLevel; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value < value) {
                current = current.forward[i];
            }
            update[i] = current;
        }

        current = current.forward[0];

        // If the node exists, remove it
        if (current != null && current.value == value) {
            // Splice the node out of all levels where it exists
            for (int i = 0; i <= currentMaxLevel; i++) {
                // If the update node doesn't point to current, it means `current` 
                // didn't reach this high a level, so we can stop.
                if (update[i].forward[i] != current) {
                    break;
                }
                update[i].forward[i] = current.forward[i];
            }

            // Optimization: If removing this node caused the highest levels to become empty,
            // we should decrement our max level counter.
            while (currentMaxLevel > 0 && head.forward[currentMaxLevel] == null) {
                currentMaxLevel--;
            }
        }
    }

    /**
     * Prints the visual structure of the Skip List.
     */
    public void printList() {
        System.out.println("Skip List Structure:");
        for (int i = currentMaxLevel; i >= 0; i--) {
            Node current = head.forward[i];
            System.out.print("Level " + i + ": ");
            while (current != null) {
                System.out.print(current.value + " -> ");
                current = current.forward[i];
            }
            System.out.println("null");
        }
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- SKIP LIST DEMO ---");
        SkipList skipList = new SkipList();
        
        System.out.println("Inserting elements: 3, 6, 7, 9, 12, 19, 17, 26, 21, 25");
        int[] elements = {3, 6, 7, 9, 12, 19, 17, 26, 21, 25};
        for (int e : elements) {
            skipList.insert(e);
        }
        
        skipList.printList();
        
        System.out.println("\nSearching for 19: " + skipList.search(19)); // true
        System.out.println("Searching for 15: " + skipList.search(15)); // false
        
        System.out.println("\nDeleting 19 and 7...");
        skipList.delete(19);
        skipList.delete(7);
        
        System.out.println("Searching for 19: " + skipList.search(19)); // false
        skipList.printList();
    }
}
