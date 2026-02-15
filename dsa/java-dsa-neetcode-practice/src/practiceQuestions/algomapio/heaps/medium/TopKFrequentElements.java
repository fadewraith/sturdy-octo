package practiceQuestions.algomapio.heaps.medium;

import java.util.*;

public class TopKFrequentElements {

    /**
     * Step-by-Step Thought Process
     * Brute Force
     * Understand the problem: Find the k most frequent elements in an array of integers.
     * Use a Counter to count the frequency of each number in nums.
     * Initialize an empty min-heap heap to store at most k (frequency, number) pairs.
     * Iterate through each key (number) and val (frequency) in the Counter.
     * If the heap has fewer than k elements, push (val, key) to the heap.
     * Otherwise, push (val, key) to the heap and pop the smallest element to maintain k elements.
     * Extract the second element (number) from each tuple in the heap and return them as the result.
     *
     * Why the Brute-Force Solution is Inefficient
     * The brute-force solution uses a heap to maintain the k most frequent elements, leading to:
     *
     * Time Complexity: O(n log k), where n is the length of the array, due to heap operations for each of the n elements (or fewer distinct elements).
     * Space Complexity: O(k), for storing the heap of k elements.
     * Performance Issue: While efficient for small k, the heap operations introduce a logarithmic factor that can be avoided by leveraging bucket sort, which is more suitable given the frequency range is bounded by n.
     * */

    public int[] bruteForce(int[] nums, int k) {
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<Map.Entry<Integer, Integer>> heap = new PriorityQueue<>(
                (a, b) -> Integer.compare(a.getValue(), b.getValue())
        );

        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if (heap.size() < k) {
                heap.offer(entry);
            } else {
                heap.offer(entry);
                heap.poll();
            }
        }

        int[] topK = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            topK[i] = heap.poll().getKey();
        }

        return topK;
    }

    /**
     * Optimal Solution
     * The optimal solution uses bucket sort to achieve O(n) time complexity:
     *
     * Get the length n of the input array nums.
     * Use a Counter to count the frequency of each number in nums.
     * Initialize a buckets array of size n+1, where buckets[i] stores a list of numbers with frequency i.
     * Iterate through the Counter, placing each number in buckets[freq]; if buckets[freq] is empty, initialize it as a list with the number, otherwise append the number.
     * Initialize an empty list ret to store the result.
     * Iterate through buckets from index n down to 0; if buckets[i] is not empty, extend ret with buckets[i].
     * Stop when ret contains k elements and return ret.
     * */


    public int[] optimal(int[] nums, int k) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int num : nums) {
            count.put(num, count.getOrDefault(num, 0) + 1);
        }

        List<Integer>[] buckets = new ArrayList[nums.length + 1];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }

        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            int num = entry.getKey();
            int freq = entry.getValue();
            buckets[freq].add(num);
        }

        List<Integer> result = new ArrayList<>();
        for (int i = buckets.length - 1; i >= 0; --i) {
            if (!buckets[i].isEmpty()) {
                result.addAll(buckets[i]);
                if (result.size() >= k) {
                    break;
                }
            }
        }

        // Convert result to int[]
        int[] resultArray = new int[k];
        for (int i = 0; i < k; i++) {
            resultArray[i] = result.get(i);
        }
        return resultArray;
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Top K Frequent Elements
 * In this problem, we're given an unsorted array of integers and asked to return the k elements that appear most frequently. This problem is a classic example of frequency analysis and appears frequently in scenarios such as log data analysis, recommendation systems, and natural language processing where you need to extract high-occurrence entities.
 *
 * Initial Approach: Min Heap
 * A common brute-force approach involves counting the frequency of each element and using a min heap to keep track of the top k most frequent numbers. We build a frequency map using a hash table (or Python’s Counter) and then push each (frequency, number) pair into the heap. When the heap size exceeds k, we remove the smallest frequency element. This ensures the heap always contains the k most frequent elements seen so far.
 *
 * Once all elements are processed, the numbers in the heap are our result. The heap allows for efficient retrieval of the most frequent items in O(n log k) time.
 *
 * Optimized Strategy: Bucket Sort
 * While the heap-based approach is efficient, we can do better. The maximum frequency any number can have is equal to the length of the input array. So instead of sorting or using a heap, we can use a bucket sort idea. We create an array of n + 1 buckets where index i represents all numbers that appear exactly i times.
 *
 * After populating the buckets, we iterate from the highest possible frequency down to 1, collecting numbers until we have gathered k of them. This approach skips sorting altogether and guarantees a linear time solution in O(n).
 *
 * Time and Space Complexity
 * The heap solution has a time complexity of O(n log k), where n is the number of unique elements and k is the number of top elements to retrieve. It uses O(k) space for the heap and O(n) for the frequency map.
 *
 * The bucket sort solution improves the time complexity to O(n) in the average case by eliminating the log factor introduced by the heap. The space complexity remains O(n) due to the use of the frequency map and buckets.
 *
 * Conclusion
 * The “Top K Frequent Elements” problem showcases the trade-offs between general-purpose data structures like heaps and specialized techniques like bucket sort. The heap approach is versatile and works well in many scenarios, but if the data’s frequency bounds are known or small, bucket sort offers superior performance. Understanding both strategies enhances your problem-solving toolkit for real-world frequency analysis problems.
 * */