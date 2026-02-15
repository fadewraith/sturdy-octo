# Sliding Window Technique

## 1. Definition

The **Sliding Window** is a technique used to solve problems that involve contiguous segments (subarrays or substrings) of a given array or string.

It involves maintaining a **window** (a range or segment) defined by two pointers (usually `start` and `end`) that slide over the input data.

The window can either be:

* **Fixed size**
* **Variable size**

Instead of recalculating everything for every window, it efficiently updates results as the window moves forward by:

* Adding the new element
* Removing the old element

---

## 2. Where It Can Be Applied

The sliding window pattern applies mainly when:

* You need to find something about **subarrays or substrings of contiguous elements**
* You want to calculate or track a property over a range that moves forward step-by-step
* Problems involve:

   * Sums
   * Counts
   * Maximum/Minimum
   * Frequency of elements inside a window

### Common Use Cases

* **Fixed-size windows**

   * Example: "Find maximum sum of subarray of size `k`"

* **Variable-size windows**

   * Example: "Smallest subarray with sum ≥ target"

---

## 3. How to Apply the Sliding Window Pattern

There are generally **two types** of sliding windows:

---

### a) Fixed-Size Sliding Window

* Window size is fixed (say `k`)
* Move the window from the start to the end
* Slide one element at a time
* For each move:

   * Remove the leftmost element
   * Add the new rightmost element
   * Update the result

#### Steps

1. Initialize sum, frequency map, or required tracking variable for the first `k` elements.
2. Slide the window forward:

   * Remove the leftmost element.
   * Add the new rightmost element.
3. Update your result for the current window.

---

### b) Variable-Size Sliding Window

* Window size changes dynamically based on a condition
* Use two pointers: `start` and `end`

#### Steps

1. Initialize `start = 0` and `end = 0`
2. Expand `end` to include new elements and update tracking variables
3. When condition is met (e.g., `windowSum >= target`):

   * Try shrinking the window by moving `start` forward
   * Maintain the condition while optimizing result
4. Keep track of the best window found so far (minimum, maximum, count, etc.)

---

## 4. How to Identify Sliding Window Problems

Look for these clues:

* The problem deals with **subarrays or substrings**
* You need to find:

   * Maximum/minimum sum of subarray of size `k`
   * Longest/shortest substring/subarray meeting criteria
   * Count of distinct elements or frequencies
* Keywords like:

   * "continuous"
   * "contiguous"
   * "substring"
   * "subarray"
* Naive solution would involve nested loops over all subarrays
* Constraints are large (e.g., `10^5` elements) where `O(n^2)` is not feasible

---

## 5. Benefits of Sliding Window

* **Efficiency**: Reduces time complexity from `O(n²)` to `O(n)`
* **Simplicity**: Easy to implement once understood
* **Memory Efficient**: Uses constant or linear extra space
* **Versatile**: Works for fixed and variable size problems
* **Streaming Friendly**: Ideal for real-time or sequential data processing