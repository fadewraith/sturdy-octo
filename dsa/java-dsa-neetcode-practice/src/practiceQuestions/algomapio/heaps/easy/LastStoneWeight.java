package practiceQuestions.algomapio.heaps.easy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class LastStoneWeight {

    /**
     * Step-by-Step Thought Process
     * Brute Force
     * Understand the problem: Given a list of stone weights, repeatedly smash the two heaviest stones together until at most one stone remains, returning its weight or 0 if none remain.
     * Define a helper function remove_largest that finds the index of the maximum stone weight using stones.index(max(stones)) and removes it from the list, returning the weight.
     * While there are at least two stones in the list, perform the following steps:
     * Call remove_largest to get the weight of the heaviest stone (stone_1).
     * Call remove_largest again to get the weight of the second heaviest stone (stone_2).
     * If stone_1 is not equal to stone_2, compute their difference (stone_1 - stone_2) and append it to the stones list.
     * After the loop, return the remaining stone’s weight if the list is not empty, otherwise return 0.
     *
     * Why the Brute-Force Solution is Inefficient
     * The brute-force solution repeatedly searches for the maximum element, leading to:
     *
     * Time Complexity: O(n²), where n is the number of stones, as finding the maximum stone takes O(n) and this is done up to n times.
     * Space Complexity: O(1), as only a constant amount of extra space is used beyond the input list.
     * Performance Issue: Repeatedly scanning the list to find the maximum element is inefficient, especially for large inputs, when a heap can maintain the largest elements in logarithmic time.
     * */
    public int bruteForce(int[] stones) {
        List<Integer> list = new ArrayList<>();
        for (int stone : stones) {
            list.add(stone);
        }

        while (list.size() > 1) {
            int max1 = Collections.max(list);
            list.remove((Integer) max1);

            int max2 = Collections.max(list);
            list.remove((Integer) max2);

            if (max1 != max2) {
                list.add(max1 - max2);
            }
        }

        return list.isEmpty() ? 0 : list.get(0);
    }


    /**
     * Optimal Solution
     * The optimal solution uses a max heap to efficiently retrieve the heaviest stones, achieving O(n log n) time complexity:
     *
     * Negate all stone weights to simulate a max heap using Python’s min heap (heapq).
     * Heapify the stones list to convert it into a heap in O(n) time.
     * While there are at least two stones in the heap, pop the two largest stones (negated, so use heappop).
     * If the stones are not equal, compute their difference and push the negated difference back to the heap.
     * After the loop, return the negative of the remaining stone (if any) or 0 if the heap is empty.
     * */

    public int optimal(int[] stones) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);

        for (int stone : stones) {
            maxHeap.offer(stone);
        }

        while (maxHeap.size() > 1) {
            int largest = maxHeap.poll();
            int nextLargest = maxHeap.poll();

            if (largest != nextLargest) {
                maxHeap.offer(largest - nextLargest);
            }
        }

        return maxHeap.isEmpty() ? 0 : maxHeap.peek();
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Last Stone Weight
 * In the “Last Stone Weight” problem, we are given a list of positive integers where each number represents the weight of a stone. The task is to simulate a process where we repeatedly select the two heaviest stones and smash them together. If the two stones have equal weights, they are both destroyed. If they differ, the stone with smaller weight is destroyed, and the heavier one is replaced with the difference of their weights. This continues until one or no stones remain. The goal is to return the weight of the last remaining stone or 0 if all stones are destroyed.
 *
 * Key Insight and Real-World Analogy
 * This problem resembles a kind of greedy strategy where, at every step, we aim to eliminate the largest weights first. The critical insight is that we must always compare the two heaviest stones, which suggests the use of a priority queue or max-heap to efficiently access these elements at each step.
 *
 * Consider an example: [2, 7, 4, 1, 8, 1]. The two heaviest stones are 8 and 7. Smashing them gives a new stone of weight 1. We then repeat this process with the updated set until one stone remains. Doing this efficiently is crucial for performance.
 *
 * Why a Heap is the Right Tool
 * A heap allows us to repeatedly retrieve and remove the largest elements in logarithmic time. Since Python’s heapq module implements a min-heap by default, we simulate a max-heap by storing the negative values of the stones. This way, the largest original values become the smallest in the heap structure.
 *
 * We heapify the entire list initially in O(n) time, then repeatedly pop the top two elements (heaviest stones) and compute the result of smashing. If the result is non-zero, we push it back into the heap. This continues until the heap has one or no elements left.
 *
 * Time and Space Complexity
 * The time complexity is O(n log n), where n is the number of stones. We heapify the list in O(n) time and then perform up to n log n operations for pushing and popping elements from the heap. The space complexity is O(n) due to the heap storage.
 *
 * Edge Cases and Practical Considerations
 * The input list might already contain just one stone—in that case, we return its weight immediately. If all stones cancel out through equal-weight collisions, we return 0. This solution is robust for any size input and performs well on larger datasets thanks to its use of heap operations rather than linear scans.
 * */