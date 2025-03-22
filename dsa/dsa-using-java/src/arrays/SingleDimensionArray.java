package arrays;

public class SingleDimensionArray {
    private int[] arr;

    // Constructor to initialize array with the specified size
    public SingleDimensionArray (int sizeOfArray) {
        arr = new int[sizeOfArray];
        // Initialize the array with a default value (Integer.MIN_VALUE)
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.MIN_VALUE;
        }
    }

    // Insert a value at a specific location
    public void insert (int location, int valueToBeInserted) {
        try {
            if (location < 0 || location >= arr.length) {
                System.out.println("Invalid index to access array!");
                return;
            }

            if (arr[location] == Integer.MIN_VALUE) {
                arr[location] = valueToBeInserted;
                System.out.println("Successfully inserted at index " + location);
            } else {
                System.out.println("This cell is already occupied");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid index to access array!");
        }
    }

    // Traverse and print all the elements in the array
    public void traverseArray () {
        boolean isArrayEmpty = true;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != Integer.MIN_VALUE) {
                isArrayEmpty = false;
                System.out.print(arr[i] + " ");
            }
        }

        if (isArrayEmpty) {
            System.out.println("Array is empty!");
        } else {
            System.out.println(); // Print a new line after traversing the array
        }
    }

    // Search for a value in the array and return its index
    public void searchInArray (int valueToSearch) {
        boolean found = false;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == valueToSearch) {
                System.out.println("Value is found at index " + i);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println(valueToSearch + " is not found in the array");
        }
    }

    // Delete a value at a specified index
    public void deleteValue (int valueToDeleteIndex) {
        try {
            if (valueToDeleteIndex < 0 || valueToDeleteIndex >= arr.length) {
                System.out.println("The provided index is out of range of the array");
                return;
            }

            arr[valueToDeleteIndex] = Integer.MIN_VALUE;
            System.out.println("The value at index " + valueToDeleteIndex + " has been deleted successfully");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("The provided index is not in the range of the array");
        }
    }

    public static void main(String[] args) {
        // Test Case 1: Initialize an array of size 5
        System.out.println("Test Case 1: Initialize Array with size 5");
        SingleDimensionArray arr = new SingleDimensionArray(5);
        arr.traverseArray(); // Expecting "Array is empty!"

        // Test Case 2: Insert values into the array
        System.out.println("\nTest Case 2: Insert values into the array");
        arr.insert(0, 10); // Should insert at index 0
        arr.insert(1, 20); // Should insert at index 1
        arr.insert(2, 30); // Should insert at index 2
        arr.insert(5, 50); // Invalid index (out of bounds)
        arr.traverseArray(); // Should print 10, 20, 30 with empty spaces for other indexes

        // Test Case 3: Insert at an already occupied index
        System.out.println("\nTest Case 3: Insert at an already occupied index");
        arr.insert(1, 25); // Should print "This cell is already occupied"
        arr.traverseArray();

        // Test Case 4: Search for a value that exists
        System.out.println("\nTest Case 4: Search for a value that exists");
        arr.searchInArray(20); // Should print "Value is found at index 1"

        // Test Case 5: Search for a value that does not exist
        System.out.println("\nTest Case 5: Search for a value that does not exist");
        arr.searchInArray(100); // Should print "100 is not found in the array"

        // Test Case 6: Delete a value
        System.out.println("\nTest Case 6: Delete a value");
        arr.deleteValue(1); // Should delete the value at index 1
        arr.traverseArray(); // Should print updated array with index 1 deleted

        // Test Case 7: Delete value at an invalid index
        System.out.println("\nTest Case 7: Delete value at an invalid index");
        arr.deleteValue(5); // Should print "The provided index is out of range of the array"

        // Test Case 8: Attempt to insert at an invalid index
        System.out.println("\nTest Case 8: Attempt to insert at an invalid index");
        arr.insert(5, 60); // Should print "Invalid index to access array!"

        // Test Case 9: Empty array condition
        System.out.println("\nTest Case 9: Traverse Empty Array");
        SingleDimensionArray emptyArr = new SingleDimensionArray(0); // Array with size 0
        emptyArr.traverseArray(); // Should print "Array is empty!"
    }
}
