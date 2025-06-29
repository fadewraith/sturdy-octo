package neetcode.algorithms.arrays;

public class BinarySearch {

    public static int binarySearch(int[] arr,  int target) {
        int L = 0, R = arr.length - 1;
        int mid;

        while (L <= R) {
            mid = (L + R) / 2;
            if (target > arr[mid]) {
                L = mid + 1;
            } else if (target < arr[mid]) {
                R = mid - 1;
            } else {
                return mid;
            }
        }
        return - 1;
    }

    public static int recursiveBinarySearch(int[] arr, int target, int left, int right) {
        if (left > right) {
            return -1;
        }

        int mid = (left + right) / 2;

        if (arr[mid] == target) {
            return mid;
        } else if (arr[mid] < target) {
            return recursiveBinarySearch(arr, target, mid + 1, right);
        } else {
            return recursiveBinarySearch(arr, target, left, mid - 1);
        }
    }

}
