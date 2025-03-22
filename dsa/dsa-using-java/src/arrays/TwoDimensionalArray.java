package arrays;

public class TwoDimensionalArray {

    // 2D array declaration
    int arr[][] = null;

    // Constructor to initialize the array
    public TwoDimensionalArray(int numberOfRows, int numberOfColumns) {
        this.arr = new int[numberOfRows][numberOfColumns];

        // Initializing all elements of the array to Integer.MIN_VALUE
        for (int row = 0; row < arr.length; row++) {
            for (int col = 0; col < arr[0].length; col++) {
                arr[row][col] = Integer.MIN_VALUE;
            }
        }
    }

    // Inserting value in the Array
    public void insertValueInTheArray(int row, int col, int value) {
        try {
            // Check if the cell is already occupied
            if (arr[row][col] == Integer.MIN_VALUE) {
                arr[row][col] = value;
                System.out.println("The value is successfully inserted.");
            } else {
                System.out.println("This cell is already occupied.");
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid index for 2D array.");
        }
    }

    // Accessing cell value from given array
    public void accessCell(int row, int col) {
        System.out.println("\nAccessing Row#" + row + ", Col#" + col);
        try {
            System.out.println("Cell value is: " + arr[row][col]);

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid index for 2D array.");
        }
    }

    // Traverse the 2D array and print all elements
    public void traverse2DArray() {
        for (int row = 0; row < arr.length; row++) {
            for (int col = 0; col < arr[0].length; col++) {
                // Print the value of each cell, followed by a space
                System.out.print(arr[row][col] + "  ");
            }
            System.out.println();
        }
    }

    // Searching a value in the Array
    public void searchingValue(int value) {
        for (int row = 0; row < arr.length; row++) {
            for (int col = 0; col < arr[0].length; col++) {
                // Check if the value matches
                if (arr[row][col] == value) {
                    System.out.println("Value is found at row: " + row + " Col: " + col);
                    return;
                }
            }
        }
        System.out.println("Value is not found.");
    }

    // Deleting a value from the Array
    public void deleteValueFromArray(int row, int col) {
        try {
            // Delete the value by setting it to Integer.MIN_VALUE
            System.out.println("Successfully deleted: " + arr[row][col]);
            arr[row][col] = Integer.MIN_VALUE;

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("This index is not valid for array.");
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        // Test case: Create a 2D array of size 3x3
        TwoDimensionalArray array = new TwoDimensionalArray(3, 3);

        // Test case: Insert values in the array
        array.insertValueInTheArray(0, 0, 10);
        array.insertValueInTheArray(0, 1, 20);
        array.insertValueInTheArray(1, 1, 30);

        // Test case: Access a cell
        array.accessCell(0, 0); // Valid
        array.accessCell(3, 3); // Invalid index

        // Test case: Traverse the array
        System.out.println("\nTraverse the 2D array:");
        array.traverse2DArray();

        // Test case: Search for a value
        array.searchingValue(20); // Value found
        array.searchingValue(40); // Value not found

        // Test case: Delete a value from the array
        array.deleteValueFromArray(1, 1); // Valid
        array.deleteValueFromArray(3, 3); // Invalid index

        // Test case: Traverse again after deletion
        System.out.println("\nTraverse the 2D array after deletion:");
        array.traverse2DArray();

        // Test case: Insert value into an already occupied cell
        array.insertValueInTheArray(0, 0, 50); // Cell already occupied
    }
}
