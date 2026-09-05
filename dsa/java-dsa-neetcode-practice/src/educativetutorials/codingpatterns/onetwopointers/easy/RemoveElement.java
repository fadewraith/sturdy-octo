package educativetutorials.codingpatterns.onetwopointers.easy;

public class RemoveElement {

    public int removeElement(int[] nums, int val) {
        // Initialize k to track the position for non-val elements
        int k = 0;

        // Iterate through all elements in the array
        for (int j = 0; j < nums.length; j++) {
            // If the current element is not equal to val, keep it
            if (nums[j] != val) {
                nums[k] = nums[j];
                k++;
            }
        }

        // Return the count of elements not equal to val
        return k;
    }

    // Driver code
    public static void main(String[] args) {
        int[][] numsArr = {
                {5, 8, 8, 5, 3},
                {50, 49, 48, 47, 46, 45},
                {0, 0, 0, 0, 1, 0, 0, 0, 0},
                {10, 20, 30, 40, 50},
                {0, 50}
        };

        int[] valArr = {5, 48, 0, 25, 0};

        RemoveElement sol = new RemoveElement();
        for (int i = 0; i < numsArr.length; i++) {
            System.out.println((i + 1) + ".\tnums: " + java.util.Arrays.toString(numsArr[i]));
            System.out.println("\tval: " + valArr[i]);
            System.out.println("\tk: " + sol.removeElement(numsArr[i], valArr[i]));
            System.out.println("-".repeat(100));
        }
    }
}
