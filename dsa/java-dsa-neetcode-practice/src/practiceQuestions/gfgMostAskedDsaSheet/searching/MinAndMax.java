package practiceQuestions.gfgMostAskedDsaSheet.searching;

public class MinAndMax {

    private static int findMin(int[] arr, int n) {
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < n; i++) {
            if(arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }

    private static int findMax(int[] arr, int n) {
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < n; i++) {
            if(arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    public static void main(String[] args) {

        // can be done using sorting also
        int[] A = { 4, 9, 6, 5, 2, 3 };
        int N = A.length;
        System.out.println("Minimum element is: " + findMin(A, N));
        System.out.println("Maximum element is: " + findMax(A, N));
    }
}
