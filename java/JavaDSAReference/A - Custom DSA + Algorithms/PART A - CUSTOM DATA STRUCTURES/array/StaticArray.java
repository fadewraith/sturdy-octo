package array;

/**
 * STATIC ARRAY
 * 
 * What it is:
 * A foundational, fixed-size data structure that stores elements in contiguous memory locations.
 * Once created, its capacity cannot be changed. It is the building block for dynamic arrays
 * (like ArrayList) and many other structures (hash tables, heaps, etc.).
 * 
 * Approach/Strategy:
 * Under the hood, we use a plain Java array of Objects and cast it to our generic type.
 * We keep track of the current number of elements (`size`) separately from the array's 
 * physical capacity (`capacity`).
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Insert (end)   | O(1)            | O(1)             | Assuming array is not full
 * Insert (index) | O(N)            | O(1)             | Requires shifting elements to the right
 * Delete (index) | O(N)            | O(1)             | Requires shifting elements to the left
 * Search (value) | O(N)            | O(1)             | Linear scan required
 * Update (index) | O(1)            | O(1)             | Direct access via index
 * 
 * Real-world analogy:
 * Think of a fixed row of mailboxes in an apartment building. The number of mailboxes is fixed 
 * when the building is constructed. You can instantly access mailbox #5 (Update/Get), but if 
 * you want to insert a new mailbox between #4 and #5 while keeping them in order, you'd have 
 * to physically shift all subsequent mailboxes to the right (assuming we want to preserve order).
 * 
 * Why implement this yourself when Java's built-in (T[]) exists? What's the tradeoff?
 * Java already has native arrays (e.g., int[], Object[]). This custom wrapper class demonstrates 
 * how an object-oriented wrapper manages the `size` vs `capacity` abstraction, and implements 
 * the shifting logic manually, which is typically hidden away in utility methods like 
 * System.arraycopy(). Tradeoff: slight memory and performance overhead compared to raw arrays.
 */
public class StaticArray<T> {

    private final T[] data;
    private int size;
    private final int capacity;

    /**
     * Initializes the static array with a fixed capacity.
     * @param capacity the maximum number of elements this array can hold.
     */
    @SuppressWarnings("unchecked")
    public StaticArray(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        this.capacity = capacity;
        this.size = 0;
        
        // NOTE: In Java, we cannot instantiate a generic array directly (new T[capacity]).
        // We must instantiate an Object array and cast it to T[].
        this.data = (T[]) new Object[capacity];
    }

    /**
     * Inserts an element at the end of the currently populated portion.
     * @param element the element to insert
     */
    public void insert(T element) {
        if (size >= capacity) {
            throw new IllegalStateException("Array is full. Cannot insert new elements.");
        }
        // Insert at the current size index, then increment size
        data[size] = element;
        size++;
    }

    /**
     * Inserts an element at a specific index, shifting subsequent elements to the right.
     * @param index the position to insert at
     * @param element the element to insert
     */
    public void insertAt(int index, T element) {
        if (size >= capacity) {
            throw new IllegalStateException("Array is full. Cannot insert new elements.");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }

        // Shift elements to the right starting from the end down to the target index
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }

        // Place the new element
        data[index] = element;
        size++;
    }

    /**
     * Deletes the element at the specified index, shifting subsequent elements to the left.
     * @param index the position of the element to delete
     */
    public void delete(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }

        // Shift elements to the left starting from the target index up to the end
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }

        // Nullify the last populated element to help garbage collection
        data[size - 1] = null;
        size--;
    }

    /**
     * Searches for a specific value and returns its index.
     * @param value the value to search for
     * @return the index of the value, or -1 if not found
     */
    public int search(T value) {
        // Linear scan through the populated elements
        for (int i = 0; i < size; i++) {
            // Use .equals for object comparison, handle null values safely
            if (value == null ? data[i] == null : value.equals(data[i])) {
                return i;
            }
        }
        return -1; // Not found
    }

    /**
     * Updates the element at the specified index.
     * @param index the position to update
     * @param value the new value
     */
    public void update(int index, T value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        // Direct access, O(1) update
        data[index] = value;
    }
    
    /**
     * Helper method to get an element at an index.
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return data[index];
    }

    /**
     * Returns the current number of elements in the array.
     */
    public int size() {
        return size;
    }

    /**
     * Prints the current state of the array.
     */
    public void printArray() {
        System.out.print("[");
        for (int i = 0; i < size; i++) {
            System.out.print(data[i]);
            if (i < size - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    /**
     * Runner method to exercise all public methods and edge cases.
     */
    public static void main(String[] args) {
        System.out.println("--- STATIC ARRAY DEMO ---");
        
        // 1. Initialize
        StaticArray<String> array = new StaticArray<>(5);
        System.out.println("Initial empty array:");
        array.printArray();
        
        // 2. Insert (end)
        System.out.println("\nInserting 'A', 'B', 'C' at the end:");
        array.insert("A");
        array.insert("B");
        array.insert("C");
        array.printArray(); // Expected: [A, B, C]
        
        // 3. Insert at position (shifting right)
        System.out.println("\nInserting 'X' at index 1:");
        array.insertAt(1, "X");
        array.printArray(); // Expected: [A, X, B, C]
        
        // 4. Update
        System.out.println("\nUpdating index 2 ('B') to 'Y':");
        array.update(2, "Y");
        array.printArray(); // Expected: [A, X, Y, C]
        
        // 5. Search
        System.out.println("\nSearching for 'Y': Index = " + array.search("Y")); // Expected: 2
        System.out.println("Searching for 'Z' (not present): Index = " + array.search("Z")); // Expected: -1
        
        // 6. Delete (shifting left)
        System.out.println("\nDeleting element at index 0 ('A'):");
        array.delete(0);
        array.printArray(); // Expected: [X, Y, C]
        
        // 7. Edge Case: Full array
        System.out.println("\nTesting capacity limit (adding elements until full):");
        array.insert("D");
        array.insert("E");
        array.printArray(); // Expected: [X, Y, C, D, E]
        
        try {
            System.out.println("Attempting to insert into a full array...");
            array.insert("F");
        } catch (IllegalStateException e) {
            System.out.println("Caught exception successfully: " + e.getMessage());
        }
        
        // 8. Edge Case: Invalid index
        try {
            System.out.println("\nAttempting to delete at invalid index (10)...");
            array.delete(10);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught exception successfully: " + e.getMessage());
        }
        
        System.out.println("\nFinal array state:");
        array.printArray();
    }
}
