package educativetutorials.algoforjava.notes;

import educativetutorials.codingpatterns.commonutilities.CommonUtils;

import java.util.Random;

public class QuickSort {

    /**
     * Returns the median index of three random numbers within the range of the array.
     *
     * @param left The starting index of the range.
     * @param right The ending index of the range.
     * @return The median index of the three random numbers.
     */
    public static int choosePivot(int left, int right) {
        Random rand = new Random();
        // Pick 3 random numbers within the range of the array
        int i1 = left + (rand.nextInt(right - left + 1));
        int i2 = left + (rand.nextInt(right - left + 1));
        int i3 = left + (rand.nextInt(right - left + 1));

        // Return their median
        return Math.max(Math.min(i1, i2), Math.min(Math.max(i1, i2), i3));
    }

    /**
     * Partitions the array using the Quick Sort algorithm.
     *
     * <p>This function takes the array, and two indices left and right, and partitions the array such that all elements less than or equal to the pivot (the element at index right) are placed before or at the index i, and all elements greater than the pivot are placed after i. The function returns the index i.</p>
     *
     * @param arr The array to be partitioned.
     * @param left The starting index of the range.
     * @param right The ending index of the range.
     * @return The index i.
     */
    public static int partition(int arr[], int left, int right) {
        int pivotInd = choosePivot(left, right); // Index of pivot
        CommonUtils.swap(arr, right, pivotInd); // self created function to swap two indices of an array
        int pivot = arr[right]; // Pivot
        int i = (left - 1); // All the elements less than or equal to the
        // pivot go before or at i

        for (int j = left; j <= right - 1; j++) {
            if (arr[j] <= pivot) {
                i++; // increment the index
                CommonUtils.swap(arr, i, j);
            }
        }
        CommonUtils.swap(arr, i + 1, right); // Putting the pivot back in place
        return (i + 1);
    }

    /**
     * How to pick the pivot?
     * Choose randomly
     * The pivot can be picked randomly from the given elements. Introducing randomization in this manner has been proven to yield good expected running time.
     *
     * Median-of-three strategy
     * Another way to randomize is to pick three random elements from the array and find their median. This strategy ensures that the smallest or largest element is not picked.
     * */
}
