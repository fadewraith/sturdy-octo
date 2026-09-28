package cache.lru;

import hashmap.chaining.MyHashMap;

/**
 * LRU (Least Recently Used) CACHE
 * 
 * What it is:
 * A fixed-size cache that evicts the least recently used item when it reaches capacity.
 * 
 * Approach/Strategy:
 * To achieve strict O(1) time complexity for BOTH `get` and `put` operations, we must 
 * combine two data structures:
 * 1. A HashMap: Provides O(1) lookup to find if a key exists in the cache.
 * 2. A Doubly Linked List (DLL): Provides O(1) insertions, deletions, and moving elements.
 * 
 * How they wire together:
 * The HashMap stores the Key mapping directly to the internal DLL `Node`. 
 * - When you `get()` an item, we use the HashMap to find the Node in O(1), physically 
 *   detach it from its current position in the DLL, and move it to the HEAD of the DLL.
 * - When you `put()` an item, we add a new Node to the HEAD. If we exceed capacity, 
 *   we simply remove the Node at the TAIL of the DLL (which is the Least Recently Used) 
 *   and also delete its key from the HashMap.
 * 
 * We use a "dummy head" and "dummy tail" node to eliminate null-pointer checks 
 * when removing or inserting.
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Get            | O(1) avg        | O(1)             | Map lookup + DLL pointer swaps
 * Put            | O(1) avg        | O(1)             | Map insert + DLL pointer swaps
 * 
 * Real-world analogy:
 * Your physical desk. You only have space for 5 books (capacity). When you need a book, 
 * you pull it out and put it right in front of you (most recently used). If you need a 
 * 6th book, you take the book that has been sitting untouched at the very back of the 
 * desk (least recently used) and put it back in the shelf to make room.
 */
public class LRUCache<K, V> {

    /**
     * Internal Doubly Linked List Node.
     * Crucially, it must store both Key and Value so that when we evict from the tail, 
     * we know which Key to remove from the HashMap!
     */
    private class Node {
        K key;
        V value;
        Node prev;
        Node next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final MyHashMap<K, Node> map;
    
    // Dummy nodes to make pointer manipulation seamless
    private final Node head;
    private final Node tail;

    public LRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        this.capacity = capacity;
        this.map = new MyHashMap<>(capacity * 2, 0.75f); // Using our custom HashMap!
        
        // Setup dummy head and tail
        this.head = new Node(null, null);
        this.tail = new Node(null, null);
        head.next = tail;
        tail.prev = head;
    }

    /**
     * Retrieves the value and marks it as most recently used.
     */
    public V get(K key) {
        Node node = map.get(key);
        if (node == null) {
            return null; // Cache miss
        }
        
        // Cache hit! Move to the front (Most Recently Used)
        moveToHead(node);
        return node.value;
    }

    /**
     * Inserts or updates the value, marking it as most recently used.
     * Evicts the LRU item if capacity is exceeded.
     */
    public void put(K key, V value) {
        Node node = map.get(key);
        
        if (node != null) {
            // Update existing node
            node.value = value;
            moveToHead(node);
        } else {
            // New item
            Node newNode = new Node(key, value);
            map.put(key, newNode);
            addNodeToHead(newNode);
            
            // Check capacity eviction
            if (map.size() > capacity) {
                Node tailNode = popTail();
                map.remove(tailNode.key); // Remove from HashMap
            }
        }
    }

    // --------------------------------------------------------
    // DLL HELPER METHODS (Strictly O(1))
    // --------------------------------------------------------

    /**
     * Always add the new node right after the dummy head.
     */
    private void addNodeToHead(Node node) {
        node.prev = head;
        node.next = head.next;
        
        head.next.prev = node;
        head.next = node;
    }

    /**
     * Physically detach an existing node from the linked list.
     */
    private void removeNode(Node node) {
        Node prevNode = node.prev;
        Node nextNode = node.next;
        
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
    }

    /**
     * Move an existing node to the head.
     */
    private void moveToHead(Node node) {
        removeNode(node);
        addNodeToHead(node);
    }

    /**
     * Pop the current tail (the node right before the dummy tail).
     */
    private Node popTail() {
        Node res = tail.prev;
        removeNode(res);
        return res;
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- LRU CACHE DEMO ---");
        
        // Cache with capacity of 3
        LRUCache<Integer, String> lru = new LRUCache<>(3);
        
        System.out.println("Putting (1, 'A')");
        lru.put(1, "A");
        System.out.println("Putting (2, 'B')");
        lru.put(2, "B");
        System.out.println("Putting (3, 'C')");
        lru.put(3, "C");
        
        // Cache is now full: [3(MRU) -> 2 -> 1(LRU)]
        
        System.out.println("\nGet 1: " + lru.get(1)); 
        // 1 is accessed, so it moves to MRU. 
        // Cache: [1(MRU) -> 3 -> 2(LRU)]
        
        System.out.println("Putting (4, 'D') - This should evict 2!");
        lru.put(4, "D"); 
        // Cache: [4(MRU) -> 1 -> 3(LRU)]
        
        System.out.println("\nGet 2: " + lru.get(2)); // Expected: null (Evicted)
        
        System.out.println("Putting (5, 'E') - This should evict 3!");
        lru.put(5, "E");
        // Cache: [5(MRU) -> 4 -> 1(LRU)]
        
        System.out.println("\nGet 1: " + lru.get(1)); // Expected: A
        System.out.println("Get 3: " + lru.get(3)); // Expected: null (Evicted)
        System.out.println("Get 4: " + lru.get(4)); // Expected: D
        System.out.println("Get 5: " + lru.get(5)); // Expected: E
    }
}
