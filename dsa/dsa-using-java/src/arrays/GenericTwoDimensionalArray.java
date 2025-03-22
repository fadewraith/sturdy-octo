package arrays;

public class GenericTwoDimensionalArray<T> {
    private T[][] arr;

    // Constructor
    public GenericTwoDimensionalArray(int numberOfRows, int numberOfColumns) {
        // Use reflection to create a generic array
        arr = (T[][]) new Object[numberOfRows][numberOfColumns]; // casting to T[][] since we can't directly instantiate generic arrays
        // Initialize the array with null values (default value for objects)
        for (int row = 0; row < arr.length; row++) {
            for (int col = 0; col < arr[0].length; col++) {
                arr[row][col] = null; // Default value for objects is null
            }
        }
    }

    // Inserting value in the Array
    public void insertValueInTheArray(int row, int col, T value) {
        try {
            if (arr[row][col] == null) {  // Only insert if the cell is null
                arr[row][col] = value;
                System.out.println("The value is successfully inserted at [" + row + "][" + col + "]");
            } else {
                System.out.println("This cell is already occupied");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid index for 2D array");
        }
    }

    // Accessing cell value from given array
    public void accessCell(int row, int col) {
        System.out.println("\nAccessing Row#" + row + ", Col#" + col);
        try {
            System.out.println("Cell value is: " + arr[row][col]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid index for 2D array");
        }
    }

    // Traverse 2D array
    public void traverse2DArray() {
        boolean isArrayEmpty = true;
        for (int row = 0; row < arr.length; row++) {
            for (int col = 0; col < arr[0].length; col++) {
                if (arr[row][col] != null) {
                    isArrayEmpty = false;
                }
                System.out.print((arr[row][col] != null ? arr[row][col] : "null") + "  ");
            }
            System.out.println();
        }
        if (isArrayEmpty) {
            System.out.println("Array is empty!");
        }
    }

    // Searching a single value from the Array
    public void searchingValue(T value) {
        for (int row = 0; row < arr.length; row++) {
            for (int col = 0; col < arr[0].length; col++) {
                if (arr[row][col] != null && arr[row][col].equals(value)) {  // Use equals() for generic comparison
                    System.out.println("Value is found at row: " + row + " Col: " + col);
                    return;
                }
            }
        }
        System.out.println("Value is not found");
    }

    // Deleting a value from Array
    public void deleteValuefromArray(int row, int col) {
        try {
            if (arr[row][col] != null) {
                System.out.println("Successfully deleted: " + arr[row][col]);
                arr[row][col] = null;  // Set the value to null to "delete" it
            } else {
                System.out.println("This cell is already empty");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("This index is not valid for array");
        }
    }

    // Main method to test the generic TwoDimensionalArray class
    public static void main(String[] args) {
        // Test with Integer type
        System.out.println("Testing with Integer type:");
        GenericTwoDimensionalArray<Integer> intArray = new GenericTwoDimensionalArray<>(3, 3);
        intArray.insertValueInTheArray(0, 0, 10);
        intArray.insertValueInTheArray(1, 1, 20);
        intArray.traverse2DArray();  // Should print the inserted values and "null" for others
        intArray.searchingValue(20);  // Should find 20 at [1][1]
        intArray.deleteValuefromArray(0, 0);  // Should delete the value 10 at [0][0]
        intArray.traverse2DArray();  // Should print null at [0][0] and 20 at [1][1]

        // Test with String type
        System.out.println("\nTesting with String type:");
        GenericTwoDimensionalArray<String> stringArray = new GenericTwoDimensionalArray<>(2, 2);
        stringArray.insertValueInTheArray(0, 0, "Hello");
        stringArray.insertValueInTheArray(1, 1, "World");
        stringArray.traverse2DArray();  // Should print "Hello" and "World"
        stringArray.searchingValue("World");  // Should find "World" at [1][1]
        stringArray.deleteValuefromArray(1, 1);  // Should delete "World" at [1][1]
        stringArray.traverse2DArray();  // Should print null at [1][1]
    }
}
