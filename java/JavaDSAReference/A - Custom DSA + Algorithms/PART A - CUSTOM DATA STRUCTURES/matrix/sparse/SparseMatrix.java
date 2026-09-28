package matrix.sparse;

/**
 * SPARSE MATRIX
 * 
 * What it is:
 * A matrix in which most of the elements are zero. Storing such a matrix in a 
 * standard 2D array (`int[][]`) wastes an enormous amount of memory. A sparse 
 * matrix data structure explicitly stores only the NON-ZERO elements.
 * 
 * Approach/Strategy (Array of Linked Lists / Adjacency-like):
 * Instead of an M x N 2D array, we use an array of size M (representing the rows). 
 * Each index in this array points to a Singly Linked List of `Cell` nodes. 
 * A `Cell` stores the column index and the actual non-zero value.
 * (Note: Other common formats include CSR - Compressed Sparse Row, and COO - Coordinate List).
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Insert/Update  | O(K)            | O(1)             | K = non-zero elements in that row
 * Get            | O(K)            | O(1)             | K = non-zero elements in that row
 * Storage        | -               | O(R + Z)         | R = rows, Z = total non-zero elements
 * 
 * Real-world analogy:
 * An Excel spreadsheet with 10,000 rows and 10,000 columns, but only 50 cells actually 
 * have data typed into them. Instead of saving 100,000,000 empty boxes to the hard drive, 
 * you just save a list of the 50 boxes that have data ("Row 5, Col 2 = 'Hello'").
 */
public class SparseMatrix {

    /**
     * Internal node representing a non-zero cell in a specific row.
     */
    private static class Cell {
        int col;
        int value;
        Cell next;

        Cell(int col, int value) {
            this.col = col;
            this.value = value;
            this.next = null;
        }
    }

    private final Cell[] rows;
    private final int numRows;
    private final int numCols;

    public SparseMatrix(int numRows, int numCols) {
        if (numRows <= 0 || numCols <= 0) {
            throw new IllegalArgumentException("Dimensions must be positive.");
        }
        this.numRows = numRows;
        this.numCols = numCols;
        this.rows = new Cell[numRows];
    }

    /**
     * Sets a value in the sparse matrix. If the value is 0, it removes the cell if it exists.
     */
    public void set(int row, int col, int value) {
        checkBounds(row, col);

        Cell current = rows[row];
        Cell prev = null;

        // Traverse the linked list for this row to see if the column already exists
        while (current != null && current.col < col) {
            prev = current;
            current = current.next;
        }

        // If the column exists
        if (current != null && current.col == col) {
            if (value == 0) {
                // We are setting to 0, which means we should DELETE the node to save memory
                if (prev == null) {
                    rows[row] = current.next; // Deleting head
                } else {
                    prev.next = current.next; // Deleting middle/tail
                }
            } else {
                // Just update the value
                current.value = value;
            }
        } 
        // If column doesn't exist and value is NOT 0, insert a new node
        else if (value != 0) {
            Cell newCell = new Cell(col, value);
            if (prev == null) {
                // Insert at head
                newCell.next = rows[row];
                rows[row] = newCell;
            } else {
                // Insert in the middle/tail
                newCell.next = prev.next;
                prev.next = newCell;
            }
        }
    }

    /**
     * Retrieves the value at the given row and column.
     * Returns 0 if the cell is not explicitly stored.
     */
    public int get(int row, int col) {
        checkBounds(row, col);

        Cell current = rows[row];
        while (current != null) {
            if (current.col == col) {
                return current.value;
            } else if (current.col > col) {
                // Because we keep columns sorted, if we pass the target column, it doesn't exist
                break;
            }
            current = current.next;
        }
        return 0; // Default zero value
    }

    private void checkBounds(int row, int col) {
        if (row < 0 || row >= numRows || col < 0 || col >= numCols) {
            throw new IndexOutOfBoundsException("Matrix index out of bounds.");
        }
    }

    /**
     * Prints a visual representation of the dense matrix.
     */
    public void printDense() {
        for (int r = 0; r < numRows; r++) {
            Cell current = rows[r];
            for (int c = 0; c < numCols; c++) {
                if (current != null && current.col == c) {
                    System.out.print(String.format("%3d ", current.value));
                    current = current.next;
                } else {
                    System.out.print("  0 ");
                }
            }
            System.out.println();
        }
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- SPARSE MATRIX DEMO ---");
        
        // A 5x5 matrix
        SparseMatrix matrix = new SparseMatrix(5, 5);
        
        // Setting a few non-zero elements
        matrix.set(0, 0, 9);
        matrix.set(0, 4, 3);
        matrix.set(2, 2, 7);
        matrix.set(4, 1, 5);
        
        System.out.println("Sparse Matrix represented as dense:");
        matrix.printDense();
        
        System.out.println("\nQuerying specific cells:");
        System.out.println("Value at (2,2): " + matrix.get(2, 2)); // 7
        System.out.println("Value at (3,3): " + matrix.get(3, 3)); // 0 (Implicitly zero)
        
        System.out.println("\nUpdating (2,2) to 0 (This physically deletes the node!):");
        matrix.set(2, 2, 0);
        matrix.printDense();
    }
}
