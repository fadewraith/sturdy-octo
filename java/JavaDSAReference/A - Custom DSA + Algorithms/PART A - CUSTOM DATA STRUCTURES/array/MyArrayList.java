package array;

/**
 * DYNAMIC ARRAY (MyArrayList)
 * 
 * What it is:
 * An array-based data structure that can grow and shrink dynamically as elements are added 
 * or removed. It provides the O(1) random access of a static array but abstracts away the 
 * need to know the capacity upfront.
 * 
 * Approach/Strategy:
 * It wraps a standard Object[] array. When the internal array is full, we create a new 
 * array (usually double the size) and copy the existing elements over. This is called 
 * "resizing" or "growing" the array.
 * 
 * Time/Space Complexity:
 * Operation       | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Add (end)       | O(1) amortized  | O(1)             | O(N) worst-case if resize triggers
 * Add (index)     | O(N)            | O(1)             | Shifting + potential resize
 * Remove (index)  | O(N)            | O(1)             | Requires shifting elements left
 * Remove (value)  | O(N)            | O(1)             | Search + shifting
 * Get / Set       | O(1)            | O(1)             | Direct index access
 * Resize          | O(N)            | O(N)             | New array allocation and copying
 * 
 * Real-world analogy:
 * Imagine a bookshelf that holds exactly 10 books. When you buy the 11th book, you have to 
 * buy a new bookshelf that holds 20 books, move all 10 books to the new shelf, and then 
 * place the 11th. While that one move is time-consuming, the next 9 books can be added instantly.
 * 
 * Why implement this yourself when Java's built-in exists? What's the tradeoff?
 * Java provides `java.util.ArrayList`. Implementing it from scratch teaches how amortized 
 * time complexity works (how O(N) resizes average out to O(1) insertions). The tradeoff 
 * is that resizing is computationally expensive, so if you know the exact size needed in 
 * advance, a static array or pre-sizing the dynamic array is vastly more efficient.
 */
public class MyArrayList<T> {

    // Default starting capacity if none is provided
    private static final int DEFAULT_CAPACITY = 10;
    
    // Growth factor dictates how much larger the new array will be on resize
    // 2 is common (doubling). Java's ArrayList uses 1.5.
    private static final int GROWTH_FACTOR = 2;

    private T[] data;
    private int size;

    /**
     * Default constructor initializes array with default capacity.
     */
    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Initializes array with a specified initial capacity.
     * @param initialCapacity the starting capacity
     */
    @SuppressWarnings("unchecked")
    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative.");
        }
        this.data = (T[]) new Object[initialCapacity];
        this.size = 0;
    }

    /**
     * Adds an element to the end of the list.
     * Amortized O(1) time complexity.
     * @param element the element to add
     */
    public void add(T element) {
        // If the array is full, we must grow it before adding
        if (size == data.length) {
            grow();
        }
        data[size] = element;
        size++;
    }

    /**
     * Adds an element at a specific index, shifting subsequent elements to the right.
     * O(N) time complexity.
     * @param index the position to insert at
     * @param element the element to add
     */
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        if (size == data.length) {
            grow();
        }
        
        // Shift elements to the right to make space for the new element
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        
        data[index] = element;
        size++;
    }

    /**
     * Removes the element at the specified index, shifting subsequent elements to the left.
     * @param index the position to remove
     * @return the element that was removed
     */
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        T removedElement = data[index];
        
        // Shift elements to the left to fill the gap
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        
        // Nullify the last element for garbage collection
        data[size - 1] = null;
        size--;
        
        return removedElement;
    }

    /**
     * Removes the first occurrence of the specified value.
     * @param value the value to remove
     * @return true if the list contained the specified element
     */
    public boolean remove(T value) {
        int index = indexOf(value);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    /**
     * Retrieves the element at the specified index.
     * O(1) time complexity.
     * @param index the position to read
     * @return the element at the index
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return data[index];
    }

    /**
     * Replaces the element at the specified index.
     * O(1) time complexity.
     * @param index the position to update
     * @param element the new element
     */
    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        data[index] = element;
    }

    /**
     * Returns the current number of elements.
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if the list contains no elements.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears all elements from the list, resetting size to 0.
     */
    public void clear() {
        // Nullify elements so GC can reclaim them
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    /**
     * Checks if the list contains a specific value.
     */
    public boolean contains(T value) {
        return indexOf(value) >= 0;
    }

    /**
     * Returns the index of the first occurrence of the specified element,
     * or -1 if the list does not contain the element.
     */
    public int indexOf(T value) {
        for (int i = 0; i < size; i++) {
            if (value == null ? data[i] == null : value.equals(data[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns an array containing all elements in the list in proper sequence.
     */
    public Object[] toArray() {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = data[i];
        }
        return result;
    }

    /**
     * Trims the capacity of this ArrayList instance to be the list's current size.
     * Used to minimize storage of an ArrayList instance.
     */
    @SuppressWarnings("unchecked")
    public void trimToSize() {
        if (size < data.length) {
            T[] newData = (T[]) new Object[size];
            for (int i = 0; i < size; i++) {
                newData[i] = data[i];
            }
            data = newData;
        }
    }

    /**
     * Internal method to double the capacity of the array.
     * Time Complexity: O(N) because we must copy all existing elements.
     */
    @SuppressWarnings("unchecked")
    private void grow() {
        // If current capacity is 0 (can happen if initialCapacity was 0), grow to 1
        int newCapacity = (data.length == 0) ? 1 : data.length * GROWTH_FACTOR;
        
        T[] newData = (T[]) new Object[newCapacity];
        
        // Copy elements over
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        
        // Swap the reference
        data = newData;
        
        System.out.println("[DEBUG] Array resized. New capacity: " + newCapacity);
    }

    /**
     * Returns current internal capacity for testing purposes.
     */
    public int capacity() {
        return data.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Runner method to exercise all public methods and edge cases.
     */
    public static void main(String[] args) {
        System.out.println("--- DYNAMIC ARRAY (MyArrayList) DEMO ---");
        
        // 1. Initialize with small capacity to force resizing quickly
        MyArrayList<String> list = new MyArrayList<>(2);
        System.out.println("Initial empty list: " + list + ", Capacity: " + list.capacity());
        
        // 2. Add elements (triggering resize)
        list.add("Apple");
        list.add("Banana");
        System.out.println("\nAdded 2 items: " + list + ", Capacity: " + list.capacity());
        
        list.add("Cherry"); // This triggers a resize (2 * 2 = 4)
        System.out.println("Added 3rd item: " + list + ", Capacity: " + list.capacity());
        
        // 3. Add at index
        list.add(1, "Blueberry");
        System.out.println("\nInserted 'Blueberry' at index 1: " + list);
        
        // 4. Set / Get
        list.set(2, "Blackberry");
        System.out.println("Set index 2 to 'Blackberry': " + list);
        System.out.println("Get element at index 1: " + list.get(1)); // Expected: Blueberry
        
        // 5. Contains and IndexOf
        System.out.println("\nContains 'Apple'? " + list.contains("Apple")); // Expected: true
        System.out.println("Contains 'Mango'? " + list.contains("Mango")); // Expected: false
        System.out.println("Index of 'Cherry': " + list.indexOf("Cherry")); // Expected: 3
        
        // 6. Remove (index) and Remove (value)
        System.out.println("\nRemoved element at index 0: " + list.remove(0)); // Removes Apple
        System.out.println("List after remove(0): " + list);
        
        boolean removed = list.remove("Cherry");
        System.out.println("Removed 'Cherry'? " + removed);
        System.out.println("List after remove('Cherry'): " + list);
        
        // 7. toArray
        Object[] array = list.toArray();
        System.out.print("\nExported toArray(): [");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + (i < array.length - 1 ? ", " : ""));
        }
        System.out.println("]");
        
        // 8. trimToSize
        System.out.println("\nCapacity before trim: " + list.capacity());
        list.trimToSize();
        System.out.println("Capacity after trim: " + list.capacity());
        
        // 9. Edge Cases
        System.out.println("\n--- Edge Cases ---");
        try {
            list.get(10);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Successfully caught invalid get(10): " + e.getMessage());
        }
        
        list.add(null);
        System.out.println("Added null: " + list);
        System.out.println("Contains null? " + list.contains(null)); // Expected: true
        list.remove(null);
        System.out.println("Removed null: " + list);
        
        // 10. Clear
        list.clear();
        System.out.println("\nList after clear(): " + list + ", Size: " + list.size() + ", isEmpty: " + list.isEmpty());
    }
}
