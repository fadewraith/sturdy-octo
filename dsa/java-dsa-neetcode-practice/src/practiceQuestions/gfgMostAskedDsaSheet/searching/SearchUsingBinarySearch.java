package practiceQuestions.gfgMostAskedDsaSheet.searching;

public class SearchUsingBinarySearch {

    public static void main(String[] args) {
        int arr[] = { 5, 6, 7, 8, 9, 10 };
        int n, key;
        n = arr.length - 1;
        key = 10;

        // Function call
        System.out.println("Index: " + binarySearch(arr, 0, n, key));
        System.out.println("Index: " + rec(arr, 0, n, key));
    }

    private static int binarySearch(int[] arr, int i, int n, int key) {
        int low = i;
        int high = n;
        while(low <= high) {
            int mid = low + ((high - low) / 2);
            if(arr[mid] == key) {
                return mid;
            } else if(key > arr[mid]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    private static int rec(int arr[], int low, int high, int key) {
        if(high < low) return -1;
        int mid = (low + high) / 2;
        if(arr[mid] == key) {
            return mid;
        }
        if(key > arr[mid]) {
            return rec(arr, mid + 1, high, key);
        }
        return rec(arr, low, mid - 1, key);
    }
}
