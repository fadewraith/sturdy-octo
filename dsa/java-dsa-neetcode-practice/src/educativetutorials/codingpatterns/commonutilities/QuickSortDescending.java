package educativetutorials.codingpatterns.commonutilities;

import static educativetutorials.codingpatterns.commonutilities.CommonUtils.swap;

public class QuickSortDescending {

    private static void quickSortDescending(int[] arr, int low, int high) {
        if(low < high) {
            int pivotIndex = partitionDescending(arr, low, high);
            quickSortDescending(arr, low, pivotIndex - 1);
            quickSortDescending(arr, pivotIndex + 1, high);
        }
    }

    private static int partitionDescending(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for(int j = low; j < high; j++) {
            if(arr[j] > pivot) {  // ✓ Changed < to >
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }
}
