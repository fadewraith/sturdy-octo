package educativetutorials.codingpatterns.commonutilities;

public class Helper {

    /**
     * Prints the elements of the array in a single line, separated by spaces.
     *
     * @param arr The input array
     * @param arrSize The size of the array
     */
    public static void printArray(int[]arr, int arrSize) {
        for(int i = 0; i < arrSize; i++)
            System.out.print(arr[i] + " ");
        System.out.println();
    }


    /**
     * Finds the index of the minimum element in a subarray
     *
     * @param arr The input array
     * @param start The starting index of the subarray
     * @param end The ending index of the subarray
     * @return The index of the minimum element in the specified range
     */
    public static int findMin(int[] arr, int start, int end) {
        if(end<=0 || start < 0)
            return 0;

        int minInd = start;
        for(int i = start+1; i <= end; i++) {
            if(arr[minInd] > arr[i])
                minInd = i;
        }
        return minInd;
    }


    /**
     * Finds the index of the maximum element in a subarray
     * @param arr The input array
     * @param start The starting index of the subarray
     * @param end The ending index of the subarray
     * @return The index of the maximum element in the specified range
     */
    public static int findMax(int[] arr, int start, int end) {
        if(end<=0 || start < 0)
            return 0;

        int maxInd = start;
        for(int i = start+1; i <= end; i++) {
            if(arr[maxInd] < arr[i])
                maxInd = i;
        }
        return maxInd;
    }
}
