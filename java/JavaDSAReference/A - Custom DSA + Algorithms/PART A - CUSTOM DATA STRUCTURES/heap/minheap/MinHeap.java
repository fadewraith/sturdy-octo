package heap.minheap;

import java.util.Arrays;

/**
 * MIN-HEAP
 * 
 * What it is:
 * A complete binary tree where every parent node is STRICTLY LESS THAN OR EQUAL TO 
 * its child nodes. The absolute minimum element is always at the root.
 * 
 * Approach/Strategy:
 * Because a heap is a "complete" binary tree (filled left to right), it can be 
 * perfectly represented as a flat array without any pointer gaps!
 * For a node at index `i`:
 * - Left child index  = `2*i + 1`
 * - Right child index = `2*i + 2`
 * - Parent index      = `(i - 1) / 2`
 * 
 * When we INSERT, we place the new element at the end of the array and "heapify UP" 
 * (bubble it up) until the min-heap property is restored.
 * When we EXTRACT-MIN, we remove the root, move the very last element of the array 
 * to the root, and "heapify DOWN" (sink it down) by swapping with the smallest child.
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Insert         | O(log N)        | O(1) amortized   | Bubble up
 * Extract Min    | O(log N)        | O(1)             | Sink down
 * Peek (Get Min) | O(1)            | O(1)             | Direct array access (index 0)
 * 
 * Real-world analogy:
 * A triage system in an emergency room. Patients arrive in random order, but the 
 * system constantly re-sorts them so the person with the most severe (lowest severity number) 
 * emergency is always at the very front of the line.
 */
public class MinHeap<T extends Comparable<T>> {

    private Object[] heap;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public MinHeap() {
        this.heap = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Inserts a new element into the min-heap.
     */
    public void insert(T element) {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
        
        // Place at the very end of the tree
        heap[size] = element;
        size++;
        
        // Bubble it up to its correct position
        heapifyUp(size - 1);
    }

    /**
     * Removes and returns the minimum element (the root).
     */
    @SuppressWarnings("unchecked")
    public T extractMin() {
        if (size == 0) throw new IllegalStateException("Heap is empty");
        
        T min = (T) heap[0];
        
        // Move the last element to the root
        heap[0] = heap[size - 1];
        heap[size - 1] = null; // Help GC
        size--;
        
        // Sink it down to its correct position
        heapifyDown(0);
        
        return min;
    }

    /**
     * Returns the minimum element without removing it.
     */
    @SuppressWarnings("unchecked")
    public T peek() {
        if (size == 0) throw new IllegalStateException("Heap is empty");
        return (T) heap[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // --- Helper Methods ---

    @SuppressWarnings("unchecked")
    private void heapifyUp(int index) {
        int parentIndex = (index - 1) / 2;
        
        // While we haven't reached the root, and current node is LESS than its parent
        while (index > 0 && ((T) heap[index]).compareTo((T) heap[parentIndex]) < 0) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    @SuppressWarnings("unchecked")
    private void heapifyDown(int index) {
        while (true) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = index;
            
            // Check if left child is smaller than current
            if (leftChild < size && ((T) heap[leftChild]).compareTo((T) heap[smallest]) < 0) {
                smallest = leftChild;
            }
            
            // Check if right child is smaller than the current smallest
            if (rightChild < size && ((T) heap[rightChild]).compareTo((T) heap[smallest]) < 0) {
                smallest = rightChild;
            }
            
            // If the smallest is still the current index, the heap property is satisfied
            if (smallest == index) {
                break;
            }
            
            // Otherwise, swap and continue sinking down
            swap(index, smallest);
            index = smallest;
        }
    }

    private void swap(int i, int j) {
        Object temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- MIN-HEAP DEMO ---");
        
        MinHeap<Integer> minHeap = new MinHeap<>();
        
        minHeap.insert(10);
        minHeap.insert(4);
        minHeap.insert(15);
        System.out.println("Inserted 10, 4, 15.");
        
        System.out.println("Peek Min: " + minHeap.peek()); // 4
        
        minHeap.insert(2);
        System.out.println("Inserted 2.");
        System.out.println("Peek Min: " + minHeap.peek()); // 2
        
        System.out.println("Extract Min: " + minHeap.extractMin()); // 2
        System.out.println("Extract Min: " + minHeap.extractMin()); // 4
        System.out.println("Peek Min now: " + minHeap.peek()); // 10
    }
}
