````md
# Sliding Window Pattern

The **sliding window pattern** is a technique for efficiently processing sequential data, such as arrays and strings. It involves maintaining a **dynamic window** that slides across the data, allowing you to examine a fixed portion at a time.

Instead of repeatedly scanning the entire dataset, this approach adjusts the window’s boundaries as needed to track relevant elements.

It is especially useful for solving problems involving **contiguous subarrays or substrings**, such as:

- Finding the **maximum sum of a subarray**
- Detecting **repeated patterns in a string**

This pattern can be viewed as a **variation of the two-pointer approach**, where the pointers define the window’s **start and end**.

---

# Types of Sliding Window

Depending on the problem, the sliding window can be:

1. **Fixed Size Window**
2. **Dynamic Window**

---

## Fixed-Size Window

The **fixed-size window** is used when the window size is **given or constant**.

**Example**

Find the **largest sum of any three consecutive numbers**.

---

## Dynamic Window

The **dynamic window** is used when the window size **changes based on conditions**.

**Example**

Find the **smallest subarray whose sum exceeds a target**.

---

# How is the Sliding Window Implemented?

The sliding window technique uses **two pointers** representing the window’s boundaries.

These pointers move through an **array or a string** to examine a portion of the data at a time.

The window is then updated by:

- **Adding new elements**
- **Removing old elements**

as the pointers move.

---

# Fixed Size Sliding Window

In a **fixed-size window**, both pointers move together, keeping the window size constant.

For example, if you need to find the **largest sum of three consecutive numbers**, the window size stays at **three** while sliding across the array.

---

## Fixed Window Template

```pseudo
FUNCTION slidingWindow(arr, k, processWindow):

# Initialize the window result (sum, product, count, etc.)
windowResult = INITIAL_VALUE

# Compute the initial window's result
FOR i FROM 0 TO k - 1:
    UPDATE windowResult WITH arr[i]

# Process the first window
processWindow(windowResult)

# Slide the window across the array
FOR i FROM k TO length of arr - 1:

    UPDATE windowResult BY ADDING arr[i]      # Add a new element to the window
    UPDATE windowResult BY REMOVING arr[i-k]  # Remove outgoing element

    processWindow(windowResult)               # Operation on the updated window
````

---

# Dynamic Sliding Window

In a **dynamic window**, the window size can **grow or shrink based on a condition**.

One pointer (**usually the right one**) expands the window, while the other (**left**) contracts it when the condition is met.

Example:

Finding the **smallest subarray with a sum greater than a target**.

This **two-pointer strategy** allows the sliding window to process data in **linear time**, making it a powerful tool for problems involving **continuous data sections**.

---

## Dynamic Window Template

```pseudo
FUNCTION slidingWindow(arr, condition, processWindow):

left = 0
windowState = INITIAL_VALUE

FOR right FROM 0 TO length of arr - 1:

    UPDATE windowState WITH arr[right]   # expand window

    WHILE NOT condition(windowState):    # shrink window if needed

        UPDATE windowState BY REMOVING arr[left]
        left = left + 1

    processWindow(windowState, left, right)
```

---

# Fixed Window Processing Explanation

We start by computing the result for the **first `k` elements** and storing it in **windowResult**. This represents the **initial window** in the array.

Next, we **slide the window across `arr`** by moving one step at a time.

For each step:

1. **Process the current window’s result** using the function `processWindow(windowResult)`.
   This step can involve:

    * Updating a maximum
    * Computing an average
    * Counting occurrences
    * Any other required calculation.

2. **Add the new incoming element (`arr[i]`)** to the window, updating `windowResult` accordingly.

3. **Remove the outgoing element (`arr[i−k]`)** from the window to maintain the correct size.

4. **Repeat the process** until the entire array is covered, ensuring we efficiently compute the desired result for every possible **k-sized subarray**.

---

# How Does Sliding Window Reduce Time Complexity?

The sliding window helps process **sequential data like arrays or strings efficiently**.

Let’s see why.

---

## 1. Avoids Nested Loops

Without a sliding window, many problems require checking **all subarrays** using **two or more loops**, leading to:

```
O(n²)
```

time complexity or more.

The sliding window allows us to **update the window by adjusting pointers**, reducing the complexity of traversing and processing the entire array to:

```
O(n)
```

---

## 2. Reuses Computation

Instead of recalculating values for each window from scratch, the sliding window approach **reuses previous calculations** by:

* Adding new elements
* Removing old elements

Example:

Finding the most chocolate chips in **any 3 cookies**.

Instead of recalculating the sum for every window, we simply:

```
New Window Sum = Previous Sum + Incoming Element - Outgoing Element
```

This makes the computation **significantly more efficient**.

```
```
