package arrays;

public class GenericSingleDimensionArray<T> {
    private T[] arr;

    // Constructor to initialize array with the specified size
    public GenericSingleDimensionArray(int sizeOfArray) {
        // Use reflection to create a generic array
        arr = (T[]) new Object[sizeOfArray]; // casting to T[] since we cannot directly instantiate generic arrays
        // Initialize the array with null values (default value for objects)
        for (int i = 0; i < arr.length; i++) {
            arr[i] = null; // Default value for objects is null
        }
    }

    // Insert a value at a specific location
    public void insert(int location, T valueToBeInserted) {
        try {
            if (location < 0 || location >= arr.length) {
                System.out.println("Invalid index to access array!");
                return;
            }

            if (arr[location] == null) {
                arr[location] = valueToBeInserted;  // Insert the value at the specified index
                System.out.println("Successfully inserted at index " + location);
            } else {
                System.out.println("This cell is already occupied");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid index to access array!");
        }
    }

    // Traverse and print all the elements in the array
    public void traverseArray() {
        boolean isArrayEmpty = true;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {  // Check if the element is not null (i.e., it has been assigned a value)
                isArrayEmpty = false;
                System.out.print(arr[i] + " ");
            }
        }

        if (isArrayEmpty) {
            System.out.println("Array is empty!");
        } else {
            System.out.println();  // Print a new line after traversing the array
        }
    }

    // Search for a value in the array and return its index
    public void searchInArray(T valueToSearch) {
        boolean found = false;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null && arr[i].equals(valueToSearch)) {  // Use .equals() for comparison
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
    public void deleteValue(int valueToDeleteIndex) {
        try {
            if (valueToDeleteIndex < 0 || valueToDeleteIndex >= arr.length) {
                System.out.println("The provided index is out of range of the array");
                return;
            }

            arr[valueToDeleteIndex] = null;  // Set the index to null (effectively deleting the value)
            System.out.println("The value at index " + valueToDeleteIndex + " has been deleted successfully");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("The provided index is not in the range of the array");
        }
    }

    // Main method for testing the generic class
    public static void main(String[] args) {
        // Test with Integer type
        GenericSingleDimensionArray<Integer> intArray = new GenericSingleDimensionArray<>(5);
        intArray.insert(0, 10);
        intArray.insert(1, 20);
        intArray.traverseArray(); // Should print 10 20
        intArray.searchInArray(20); // Should find 20 at index 1
        intArray.deleteValue(0); // Should delete value at index 0
        intArray.traverseArray(); // Should print 20

        // Test with String type
        GenericSingleDimensionArray<String> stringArray = new GenericSingleDimensionArray<>(3);
        stringArray.insert(0, "Hello");
        stringArray.insert(1, "World");
        stringArray.traverseArray(); // Should print Hello World
        stringArray.searchInArray("World"); // Should find World at index 1
        stringArray.deleteValue(1); // Should delete value at index 1
        stringArray.traverseArray(); // Should print Hello
    }
}
