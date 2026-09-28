package cache.lfu;

import hashmap.chaining.MyHashMap;

/**
 * LFU (Least Frequently Used) CACHE
 * 
 * What it is:
 * A fixed-size cache that evicts the least FREQUENTLY used item. If there is a tie 
 * (multiple items have the same lowest frequency), it evicts the least RECENTLY used 
 * among them.
 * 
 * Approach/Strategy (O(1) approach):
 * To achieve strict O(1) time complexity, we use an advanced multi-map architecture:
 * 1. A Key-to-Node HashMap: Just like LRU, mapping the key to the specific Node.
 * 2. A Freq-to-DLL HashMap: Maps an integer frequency (e.g., "accessed 3 times") to 
 *    a Doubly Linked List containing all nodes currently at that exact frequency.
 * 3. A `minFreq` variable: Keeps track of the absolute lowest frequency currently in 
 *    the cache.
 * 
 * How they wire together:
 * - When you `get(key)`: Find the Node in O(1). We must increase its frequency. 
 *   We remove it from its current DLL in `freqMap`, increment its freq count, and 
 *   add it to the HEAD of the DLL for the new frequency. If the old DLL becomes empty 
 *   and its frequency was the `minFreq`, we increment `minFreq`.
 * - When you `put(key, val)`: If it's a new key and capacity is full, we look up the 
 *   DLL at `minFreq`. We pop the TAIL of that DLL (the LRU element of the LFU tier), 
 *   delete it from both maps, and insert the new node at frequency 1 (resetting `minFreq` to 1).
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Get            | O(1) avg        | O(1)             | Map lookups + DLL shifts
 * Put            | O(1) avg        | O(1)             | Map lookups + DLL shifts
 * 
 * Real-world analogy:
 * Your music playlist. You only keep 10 songs on your phone. You delete songs you 
 * rarely listen to (LFU). If there are 3 songs you've only listened to once, you delete 
 * the one you haven't played in the longest time (LRU tie-breaker).
 */
public class LFUCache<K, V> {

    // Internal Node holding frequency
    private class Node {
        K key;
        V value;
        int freq;
        Node prev;
        Node next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.freq = 1; // Starts at frequency 1 when first added
        }
    }

    // Internal DLL for a specific frequency bucket
    private class DoublyLinkedList {
        Node head;
        Node tail;
        int size;

        DoublyLinkedList() {
            head = new Node(null, null);
            tail = new Node(null, null);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        void addNodeToHead(Node node) {
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
            size++;
        }

        void removeNode(Node node) {
            Node prevNode = node.prev;
            Node nextNode = node.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
            size--;
        }

        Node popTail() {
            if (size == 0) return null;
            Node res = tail.prev;
            removeNode(res);
            return res;
        }
    }

    private final int capacity;
    private int minFreq;
    
    // Key -> Node
    private final MyHashMap<K, Node> cache;
    // Frequency -> DLL of all nodes with that frequency
    private final MyHashMap<Integer, DoublyLinkedList> freqMap;

    public LFUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        this.capacity = capacity;
        this.minFreq = 0;
        this.cache = new MyHashMap<>();
        this.freqMap = new MyHashMap<>();
    }

    public V get(K key) {
        Node node = cache.get(key);
        if (node == null) {
            return null; // Cache miss
        }
        
        // Cache hit! Update its frequency.
        updateNodeFrequency(node);
        return node.value;
    }

    public void put(K key, V value) {
        if (capacity == 0) return;

        Node node = cache.get(key);
        if (node != null) {
            // Key exists. Update value and frequency.
            node.value = value;
            updateNodeFrequency(node);
        } else {
            // New key. Check eviction first.
            if (cache.size() == capacity) {
                // Get the DLL containing the least frequently used nodes
                DoublyLinkedList minFreqList = freqMap.get(minFreq);
                // The tail of this list is the Least Recently Used among the LFU
                Node evictNode = minFreqList.popTail();
                cache.remove(evictNode.key);
            }
            
            // Create new node at frequency 1
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            minFreq = 1; // Reset minFreq to 1 because we just added a new item
            
            DoublyLinkedList list = freqMap.get(1);
            if (list == null) {
                list = new DoublyLinkedList();
                freqMap.put(1, list);
            }
            list.addNodeToHead(newNode);
        }
    }

    /**
     * Core helper: Moves a node from its current frequency DLL to the (freq + 1) DLL.
     */
    private void updateNodeFrequency(Node node) {
        int currentFreq = node.freq;
        DoublyLinkedList currentList = freqMap.get(currentFreq);
        currentList.removeNode(node);
        
        // If we just emptied the DLL that was representing our absolute minimum frequency,
        // we must bump minFreq up!
        if (currentFreq == minFreq && currentList.size == 0) {
            minFreq++;
        }
        
        // Increment node's frequency
        node.freq++;
        
        // Add to the new frequency list (creating it if it doesn't exist)
        DoublyLinkedList nextList = freqMap.get(node.freq);
        if (nextList == null) {
            nextList = new DoublyLinkedList();
            freqMap.put(node.freq, nextList);
        }
        nextList.addNodeToHead(node);
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- LFU CACHE DEMO ---");
        
        // Cache with capacity of 2
        LFUCache<Integer, String> lfu = new LFUCache<>(2);
        
        System.out.println("Putting (1, 'A')");
        lfu.put(1, "A"); // minFreq=1. Freqs: [1:1]
        
        System.out.println("Putting (2, 'B')");
        lfu.put(2, "B"); // minFreq=1. Freqs: [1:2, 1:1]
        
        System.out.println("\nGet 1: " + lfu.get(1)); // Expected: A
        // Key 1's freq becomes 2. 
        // minFreq=1. Freqs: [2:1, 1:2]
        
        System.out.println("Putting (3, 'C') - Cache Full! Evicting LFU...");
        lfu.put(3, "C"); 
        // Lowest freq is 1. Key 2 is at freq 1. Evict 2!
        // minFreq=1. Freqs: [2:1, 1:3]
        
        System.out.println("\nGet 2: " + lfu.get(2)); // Expected: null (Evicted)
        
        System.out.println("Get 3: " + lfu.get(3)); // Expected: C
        // Key 3's freq becomes 2.
        // minFreq=2. Freqs: [2:3, 2:1]
        
        System.out.println("Putting (4, 'D') - Cache Full! Evicting LFU (Tie-breaker)...");
        lfu.put(4, "D");
        // Both 1 and 3 are at freq 2. 
        // 1 was accessed older than 3 (LRU tie-breaker). Evict 1!
        
        System.out.println("\nGet 1: " + lfu.get(1)); // Expected: null (Evicted)
        System.out.println("Get 3: " + lfu.get(3)); // Expected: C
        System.out.println("Get 4: " + lfu.get(4)); // Expected: D
    }
}
