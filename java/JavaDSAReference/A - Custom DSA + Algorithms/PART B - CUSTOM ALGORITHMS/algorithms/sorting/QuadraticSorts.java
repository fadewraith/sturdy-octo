package algorithms.sorting;

/**
 * COMPARISON SORTS (O(N^2) Class)
 * Includes: Bubble Sort, Selection Sort, Insertion Sort, Shell Sort
 * 
 * WHEN TO USE THIS:
 * - Bubble Sort: Almost never used in practice. Good for teaching swaps.
 * - Selection Sort: When memory writes are extremely costly (it does exactly O(N) swaps).
 * - Insertion Sort: When the array is small (e.g., < 43 elements) or ALMOST SORTED. 
 *   (This is the secret weapon used in TimSort inside Java's Arrays.sort!).
 * - Shell Sort: When you need an in-place sort that is faster than O(N^2) but you don't 
 *   want the recursive overhead of QuickSort.
 * 
 * DATA STRUCTURE: 
 * Operates on Arrays. Insertion sort can also be easily adapted to Linked Lists.
 * 
 * COMBINATION USAGE:
 * - TimSort = Merge Sort + Insertion Sort. 
 *   Java's Arrays.sort() for Objects uses TimSort. It divides the array into small chunks, 
 *   sorts them with Insertion Sort (because it's blindingly fast for small arrays), 
 *   and merges them with Merge Sort.
 * 
 * PSEUDOCODE (Insertion Sort):
 * for i from 1 to N-1:
 *     key = arr[i]
 *     j = i - 1
 *     while j >= 0 and arr[j] > key:
 *         arr[j + 1] = arr[j]
 *         j = j - 1
 *     arr[j + 1] = key
 * 
 * COMPLEXITY TABLE:
 * Algorithm      | Best Time | Avg Time | Worst Time | Space | Stable? | In-Place?
 * --------------------------------------------------------------------------------
 * Bubble Sort    | O(N)      | O(N^2)   | O(N^2)     | O(1)  | Yes     | Yes
 * Selection Sort | O(N^2)    | O(N^2)   | O(N^2)     | O(1)  | No      | Yes
 * Insertion Sort | O(N)      | O(N^2)   | O(N^2)     | O(1)  | Yes     | Yes
 * Shell Sort     | O(N log N)| O(N^1.5) | O(N^2)     | O(1)  | No      | Yes
 */
public class QuadraticSorts {

    // --------------------------------------------------------
    // 1. BUBBLE SORT
    // --------------------------------------------------------
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            // The last i elements are already sorted
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }
            // If no two elements were swapped, array is sorted
            if (!swapped) break;
        }
    }

    // --------------------------------------------------------
    // 2. SELECTION SORT
    // --------------------------------------------------------
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            // Exactly 1 swap per outer iteration
            swap(arr, i, minIdx);
        }
    }

    // --------------------------------------------------------
    // 3. INSERTION SORT
    // --------------------------------------------------------
    public static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            
            // Shift elements of sorted segment forward to make room for key
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // --------------------------------------------------------
    // 4. SHELL SORT (Diminishing Increment Sort)
    // --------------------------------------------------------
    public static void shellSort(int[] arr) {
        int n = arr.length;
        // Start with a large gap, then reduce the gap
        for (int gap = n / 2; gap > 0; gap /= 2) {
            // Do a gapped insertion sort for this gap size
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void printArray(String name, int[] arr) {
        System.out.print(name + ": [");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + (i == arr.length - 1 ? "" : ", "));
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        System.out.println("--- QUADRATIC / IN-PLACE SORTS DEMO ---");
        
        int[] arr1 = {64, 34, 25, 12, 22, 11, 90};
        bubbleSort(arr1);
        printArray("Bubble Sort", arr1);
        
        int[] arr2 = {64, 25, 12, 22, 11};
        selectionSort(arr2);
        printArray("Selection Sort", arr2);
        
        int[] arr3 = {12, 11, 13, 5, 6};
        insertionSort(arr3);
        printArray("Insertion Sort", arr3);
        
        int[] arr4 = {12, 34, 54, 2, 3};
        shellSort(arr4);
        printArray("Shell Sort", arr4);
        
        System.out.println("\n(NOTE: Java's built-in Arrays.sort() for Objects uses Tim Sort,");
        System.out.println("which is a highly optimized hybrid of Merge Sort and Insertion Sort.)");
    }
}
