package educativetutorials.codingpatterns.commonutilities;

import static educativetutorials.codingpatterns.commonutilities.CommonUtils.swap;

public class QuickSortAscending {

    private static void quickSort(int[] arr, int low, int high) {
        if(low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for(int j = low; j < high; j++) {
            if(arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }
}
