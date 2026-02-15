Sliding Window Algorithm in Python: Fixed and Variable Length
The Sliding Window Algorithm is an efficient pattern used in Data Structures and Algorithms (DSA), particularly for solving problems involving arrays or strings that require examining contiguous subarrays or substrings. It allows for optimized solutions with O(N) time complexity and is commonly implemented in Python.

Types of Sliding Window Algorithms
Fixed Length Sliding Window
Variable Length Sliding Window
1. Variable Length Sliding Window
   The Variable Length Sliding Window technique is used when the size of the window can change dynamically depending on certain conditions. The goal is often to find the longest or shortest subarray or substring that satisfies a particular requirement.

Algorithm Details
Initialize two pointers: L (left) and R (right) to define the window boundaries.
Use a set or hash map to keep track of elements inside the window for constant-time lookups.
Expand R to grow the window while the condition (e.g., no duplicates) remains valid.
If the condition becomes invalid, contract the window from the left by incrementing L and removing elements from the set until the condition is valid again.
Track the window length using the formula R - L + 1.
Time and Space Complexity
Time Complexity: O(N) — Both L and R only move forward across the array.
Space Complexity: O(N) — A set or map may store up to N elements depending on the input.
2. Fixed Length Sliding Window
   The Fixed Length Sliding Window technique applies when the size of the window is predetermined and constant throughout the algorithm. It is typically used when evaluating every subarray or substring of a specific length.

Algorithm Details
Calculate the sum (or relevant metric) of the first K elements to initialize the window.
Slide the window one position to the right in each step:
Add the new element entering the window.
Subtract the element leaving the window.
Update the running result (e.g., maximum or minimum) based on the updated window metric.
Time and Space Complexity
Time Complexity: O(N) — One complete pass through the array.
Space Complexity: O(1) — Only a few variables are used to track window metrics.

Comparison: Fixed vs Variable Length Sliding Window

Feature	-> Fixed Length,	Variable Length
Window Size	-> Constant (Predefined),	Dynamic (Adjusts Based on Condition)
Use Case -> Evaluate all windows of a fixed size,	Find the optimal window size that meets a condition
Space Complexity -> O(1),	O(N) (may use a set or map)
Time Complexity	-> O(N),	O(N)

Key Takeaways
The Sliding Window Algorithm is essential for optimizing problems involving contiguous data in arrays or strings.
Fixed length windows are simpler and use constant space.
Variable length windows are more flexible and powerful when dynamic conditions are involved.
Both types provide linear time solutions with minimal overhead.
Mastering this technique improves efficiency in a wide range of DSA problems.
