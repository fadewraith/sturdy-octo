package practiceQuestions.algomapio.heaps.medium;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class KthLargestElementArray {

    /**
     * Brute Force Method
     * Understand the problem: Find the kth largest element in an unsorted array.
     * Sort the array nums in ascending order.
     * Return the element at index len(nums) - k, which is the kth largest element.
     *
     * Why the Brute-Force Solution is Inefficient
     * The brute-force solution sorts the entire array, leading to:
     *
     * Time Complexity: O(n log n), where n is the length of the array, due to the sorting operation.
     * Space Complexity: O(1), as sorting is typically in-place.
     * Performance Issue: Sorting the entire array is unnecessary when only the kth largest element is needed, especially for large arrays or small k.
     * */

    public int bruteForce(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    /**
     * Optimal Solution: Max Heap
     * Convert to Negative Values: Since heapq provides a min heap (where the smallest element is at the root), we negate all elements in nums by iterating through the array and setting nums[i] = -nums[i]. This effectively simulates a max heap, where the largest original value corresponds to the smallest (most negative) value in the heap.
     * Heapify the Array: We call heapq.heapify(nums) to rearrange the array into a min heap based on the negated values. This operation ensures that the smallest negated value (corresponding to the largest original value) is at the root.
     * Pop k-1 Elements: To find the kth largest element, we remove the smallest negated value (largest original value) from the heap k-1 times using heapq.heappop(nums). Each pop operation removes the root and re-heapifies the remaining elements, bringing the next smallest negated value to the root.
     * Extract the Kth Largest: After k-1 pops, the root of the heap is the kth smallest negated value (kth largest original value). We pop this element with heapq.heappop(nums) and return its negation (i.e., -heapq.heappop(nums)) to obtain the original positive value.
     * */

    public int optimalMaxHeap(int[] nums, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int num : nums) {
            maxHeap.add(num);
        }

        int result = 0;
        for (int i = 0; i < k; i++) {
            result = maxHeap.poll();
        }
        return result;
    }

    /**
     * Optimal Solution: Min Heap
     * Initialize Min Heap: We create an empty list min_heap to serve as a min heap, which will store at most k elements. The heap will maintain the smallest element at the root.
     * Process Each Element: We iterate through each number num in nums:
     * If the heap has fewer than k elements, we push num onto the heap using heapq.heappush(min_heap, num). This builds the initial set of k candidates.
     * If the heap already has k elements, we use heapq.heappushpop(min_heap, num). This operation pushes num onto the heap and immediately pops the smallest element, ensuring the heap maintains exactly k elements. If num is larger than the smallest element in the heap, it replaces it; otherwise, num is discarded.
     * Extract Result: After processing all elements, the heap contains the k largest elements, with the smallest of these (the kth largest) at the root. We return min_heap[0], the root of the heap.
     * */

    public int optimalMinHeap(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int num : nums) {
            if (minHeap.size() < k) {
                minHeap.add(num);
            } else {
                if (num > minHeap.peek()) {
                    minHeap.poll();
                    minHeap.add(num);
                }
            }
        }
        return minHeap.peek();
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Kth Largest Element in an Array
 * The “Kth Largest Element in an Array” problem challenges us to find the element that ranks kth when the array is sorted in descending order. For example, the 1st largest is the maximum, the 2nd largest is the second biggest, and so on. Importantly, we are only interested in identifying this element, not necessarily sorting the entire array.
 *
 * This problem appears in real-world scenarios like leaderboard rankings, top-k analytics, and streaming data summaries, where full sorting is expensive and unnecessary.
 *
 * Brute Force Approach: Sorting the Entire Array
 * The most intuitive solution is to sort the array in ascending order and return the element at position len(nums) - k. This works because sorting places the largest elements at the end, so the kth largest will be k steps from the last.
 *
 * However, this strategy is inefficient because it performs more work than necessary—sorting all elements—even though we only need to know one specific value.
 *
 * Optimal Strategy: Use a Min Heap
 * To solve this problem more efficiently, we can maintain a min heap of the top k elements seen so far. A min heap is ideal because it keeps the smallest element at the top. That way, we can always discard the smallest of the k largest candidates.
 *
 * Here's how it works: we iterate through the array and insert each number into the min heap. If the heap grows beyond size k, we remove the smallest element. At the end, the heap will contain the k largest elements, and the smallest among them—at the top of the heap—is the kth largest in the full array.
 *
 * Time and Space Complexity
 * The min heap solution has a time complexity of O(n log k), where n is the number of elements in the array. Each insertion and deletion operation in the heap takes O(log k) time, and we do this for each of the n elements. The space complexity is O(k), as the heap never grows beyond size k.
 *
 * Alternative: Quickselect
 * For advanced users, an even more efficient average-case solution uses the Quickselect algorithm, a variation of Quicksort. It has an average time complexity of O(n) and works by recursively partitioning the array around a pivot to isolate the kth largest element. However, in the worst case, its time complexity is O(n²), and it’s more complex to implement correctly.
 * */