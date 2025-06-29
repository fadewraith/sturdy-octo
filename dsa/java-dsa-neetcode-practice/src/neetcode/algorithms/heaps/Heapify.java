package neetcode.algorithms.heaps;

import java.util.ArrayList;
import java.util.List;

public class Heapify {

    // leftChild = heap[2 * i]
    // rightChild = heap[(2 * i) + 1]
    // parent = heap[i // 2]
// O(n)
//    percolating down is more efficient than percolating up
    List<Integer> heap;


    public Heapify() {
        heap = new ArrayList<>();
        heap.add(0);
    }

    public void heapify(ArrayList<Integer> arr) {
        // Move the 0-th position element to the end of the array
        arr.add(arr.get(0));
//        assigning array to heap, coz now arr satisfies the structure property
        heap = arr;
//        this part will satisfy order property
        int cur = (heap.size() - 1) / 2;
        while (cur > 0) {
            // Percolate down from the current position
            int i = cur;
            while (2 * i < heap.size()) {
                if (2 * i + 1 < heap.size() &&
                        heap.get(2 * i + 1) < heap.get(2 * i) &&
                        heap.get(i) > heap.get(2 * i + 1)) {
                    // Swap with the right child if it is smaller
                    int tmp = heap.get(i);
                    heap.set(i, heap.get(2 * i + 1));
                    heap.set(2 * i + 1, tmp);
                    i = 2 * i + 1;
                } else if (heap.get(i) > heap.get(2 * i)) {
                    // Swap with the left child if it is smaller
                    int tmp = heap.get(i);
                    heap.set(i, heap.get(2 * i));
                    heap.set(2 * i, tmp);
                    i = 2 * i;
                } else {
                    // If the current node is in the correct position, break
                    break;
                }
            }
            cur--;
        }
        return;
    }

}
