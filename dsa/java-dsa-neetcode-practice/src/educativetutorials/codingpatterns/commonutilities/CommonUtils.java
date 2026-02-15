package educativetutorials.codingpatterns.commonutilities;

public class CommonUtils {

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static <T> void genericSwap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void reverse(int[] arr, int start, int end) {
        while(start < end) {
            swap(arr, start++, end--);
        }
    }

    public static <T> void genericReverse(T[] arr, int start, int end) {
        while(start < end) {
            genericSwap(arr, start++, end--);
        }
    }

}
