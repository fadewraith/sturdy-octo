package practiceQuestions.gfgMostAskedDsaSheet.searching;

public class SearchInUnsortedArray {

    private static int findElement(int[] arr, int n, int key) {
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] == key) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String args[]) {
        int arr[] = { 12, 34, 10, 6, 40 };
        int n = arr.length;

        // Using a last element as search element
        int key = 40;

        // Function call
        int position = findElement(arr, n, key);

        if (position == -1)
            System.out.println("Element not found");
        else
            System.out.println("Element Found at Position: "
                    + (position + 1));
    }
}
