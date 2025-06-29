package neetcode.algorithms.heaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeapDemo {

//    leftChild = heap[2 * i]
//    rightChild = heap[(2 * i) + 1]
//    parent = heap[i / 2]

    List<Integer> heap = new ArrayList<>();

    public HeapDemo() {
        heap.add(0); // empty heap initialized, not going to use index = 0
    }

    public void push(int val) {
        // Add the new value to the end of the heap
        heap.add(val);
        // Get the index of the newly added value
        int i = heap.size() - 1;

        // Percolate up: while the current node is not the root and is smaller than its parent
        while(i > 1 && heap.get(i) < heap.get(i / 2)) {
            // Swap the current node with its parent
            int temp = heap.get(i);
            heap.set(i, heap.get(i / 2));
            heap.set(i / 2, temp);
            // Move up to the parent's index
            i /= 2;
        }
    }

    public int pop() {
        // If the heap only contains the dummy element, return immediately
        if (heap.size() == 1) {
            return -1; // or throw an exception
        }

        // If the heap only contains one actual element, remove and return it
        if (heap.size() == 2) {
            return heap.remove(heap.size() - 1); // equivalent to heap.remove(1)
        }

        // Store the root value to return later
        int res = heap.get(1);
        // Move the last value to the root
        heap.set(1, heap.remove(heap.size() - 1));
        int i = 1;

        // Percolate down to maintain the heap property, run only if root has left child, otherwise its not a complete b.t
        while (2 * i < heap.size()) {
            // If the right child exists and is smaller than the left child and the current node
            if (2 * i + 1 < heap.size()
                    && heap.get(2 * i + 1) < heap.get(2 * i)
                    && heap.get(i) > heap.get(2 * i + 1)) {
                // Swap with the right child
                int temp = heap.get(i);
                heap.set(i, heap.get(2 * i + 1));
                heap.set(2 * i + 1, temp);
                i = 2 * i + 1;
            } else if (heap.get(i) > heap.get(2 * i)) {
                // Swap with the left child
                int temp = heap.get(i);
                heap.set(i, heap.get(2 * i));
                heap.set(2 * i, temp);
                i = 2 * i;
            } else {
                // If the current node is smaller than both children, stop
                break;
            }
        }

        // Return the original root value
        return res;
    }

    public static void main(String[] args) {
        HeapDemo heapDemo = new HeapDemo();
        heapDemo.push(14);
        heapDemo.push(19);
        heapDemo.push(16);
        heapDemo.push(18);
        heapDemo.push(26);
        heapDemo.push(19);
        heapDemo.push(68);
        heapDemo.push(65);
        heapDemo.push(30);
        System.out.println(Arrays.toString(heapDemo.heap.toArray()));
        heapDemo.pop();
        System.out.println(Arrays.toString(heapDemo.heap.toArray()));

    }
}


    /*

    ### Properties of a Heap

1. **Structure Property**:
   - A heap is a complete binary tree, meaning all levels are fully filled except possibly the last level, which is filled from left to right.

2. **Order Property**:
   - **Max-Heap**: The value of each node is greater than or equal to the values of its children.
   - **Min-Heap**: The value of each node is less than or equal to the values of its children.

### Limitations of a Heap

1. **Not Suitable for Searching**:
   - Heaps are not designed for searching for arbitrary elements. The time complexity for searching is O(n).

2. **Not Suitable for Sorted Order Traversal**:
   - Heaps do not support efficient sorted order traversal. Extracting all elements in sorted order requires O(n log n) time.

3. **Limited Operations**:
   - Heaps primarily support insertion, deletion, and extraction of the maximum or minimum element. Other operations like merging two heaps are not as efficient.

4. **Memory Overhead**:
   - Heaps require additional memory for maintaining the complete binary tree structure, which can be significant for large datasets.

5. **Balancing**:
   - Unlike balanced binary search trees (e.g., AVL trees, Red-Black trees), heaps do not maintain a strict balance, which can lead to inefficiencies in certain operations.
    *
    *
    * */