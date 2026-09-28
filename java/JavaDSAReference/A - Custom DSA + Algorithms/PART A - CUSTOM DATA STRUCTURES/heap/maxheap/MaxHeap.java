package heap.maxheap;

import java.util.Arrays;

/**
 * MAX-HEAP
 * 
 * What it is:
 * A complete binary tree where every parent node is STRICTLY GREATER THAN OR EQUAL TO 
 * its child nodes. The absolute maximum element is always at the root.
 * 
 * Approach/Strategy:
 * Uses the same flat-array indexing as Min-Heap:
 * - Left child  = 2*i + 1
 * - Right child = 2*i + 2
 * - Parent      = (i - 1) / 2
 * 
 * The only difference from Min-Heap is the comparison operator. 
 * We "heapify UP" if a child is LARGER than its parent.
 * We "heapify DOWN" by swapping a parent with its LARGEST child.
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Insert         | O(log N)        | O(1) amortized   | Bubble up
 * Extract Max    | O(log N)        | O(1)             | Sink down
 * Peek (Get Max) | O(1)            | O(1)             | Direct array access
 */
public class MaxHeap<T extends Comparable<T>> {

    private Object[] heap;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public MaxHeap() {
        this.heap = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public void insert(T element) {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
        heap[size] = element;
        size++;
        heapifyUp(size - 1);
    }

    @SuppressWarnings("unchecked")
    public T extractMax() {
        if (size == 0) throw new IllegalStateException("Heap is empty");
        
        T max = (T) heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        
        heapifyDown(0);
        return max;
    }

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
        
        // Changed to > 0 (Current node is GREATER than parent)
        while (index > 0 && ((T) heap[index]).compareTo((T) heap[parentIndex]) > 0) {
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
            int largest = index;
            
            // Changed to > 0 (Left child is GREATER than current largest)
            if (leftChild < size && ((T) heap[leftChild]).compareTo((T) heap[largest]) > 0) {
                largest = leftChild;
            }
            
            // Changed to > 0 (Right child is GREATER than current largest)
            if (rightChild < size && ((T) heap[rightChild]).compareTo((T) heap[largest]) > 0) {
                largest = rightChild;
            }
            
            if (largest == index) {
                break;
            }
            
            swap(index, largest);
            index = largest;
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
        System.out.println("--- MAX-HEAP DEMO ---");
        
        MaxHeap<Integer> maxHeap = new MaxHeap<>();
        
        maxHeap.insert(10);
        maxHeap.insert(4);
        maxHeap.insert(15);
        System.out.println("Inserted 10, 4, 15.");
        
        System.out.println("Peek Max: " + maxHeap.peek()); // 15
        
        maxHeap.insert(20);
        System.out.println("Inserted 20.");
        System.out.println("Peek Max: " + maxHeap.peek()); // 20
        
        System.out.println("Extract Max: " + maxHeap.extractMax()); // 20
        System.out.println("Extract Max: " + maxHeap.extractMax()); // 15
        System.out.println("Peek Max now: " + maxHeap.peek()); // 10
    }
}
