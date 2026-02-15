package educativetutorials.acejavacoding.ds.arrays;

public class Merge2SortedArrays {

    // T = S = O(m + n)

    public static int[] bruteForce(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int[] result = new int[m + n];  // Create result array

        int p1 = 0;
        int p2 = 0;
        int writePos = 0;  // Position in result array

        // Traverse both arrays until the end of either array is reached
        while (p1 < m && p2 < n) {
            // If the value at p1 is smaller than the value at p2, store it and increment p1
            if (nums1[p1] < nums2[p2]) {
                result[writePos] = nums1[p1];
                p1++;
            }
            // Otherwise, store the value at p2 and increment p2
            else {
                result[writePos] = nums2[p2];
                p2++;
            }
            writePos++;
        }

        // If elements remain in nums1, add them to the result
        while (p1 < m) {
            result[writePos] = nums1[p1];
            p1++;
            writePos++;
        }

        // If elements remain in nums2, add them to the result
        while (p2 < n) {
            result[writePos] = nums2[p2];
            p2++;
            writePos++;
        }

        return result;
    }


    // in place
    // T = O(m + n)
    // S = O(1)
    public static int[] optimized(int[] nums1, int[] nums2) {
        // ASSUMPTION: nums1 already has space for m+n elements
        // First m elements are valid data, rest are placeholders (usually 0s)

        int m = nums1.length;
        int n = nums2.length;
        int p1 = m - 1;              // Last valid element in nums1
        int p2 = n - 1;              // Last element in nums2
        int writePos = m + n - 1;    // Last position in nums1 (total space)

        // Merge from back to front
        while (p2 >= 0) {
            if (p1 < 0 || nums2[p2] > nums1[p1]) {
                nums1[writePos] = nums2[p2];
                p2--;
            } else {
                nums1[writePos] = nums1[p1];
                p1--;
            }
            writePos--;
        }

        return nums1;  // Return the modified nums1
    }

    public static int[] stableMerge(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int[] result = new int[m + n];

        int p1 = 0, p2 = 0, writePos = 0;

        while (p1 < m && p2 < n) {
            // ✅ Use <= to prefer nums1 when equal
            if (nums1[p1] <= nums2[p2]) {  // Changed from
                result[writePos++] = nums1[p1++];
            } else {
                result[writePos++] = nums2[p2++];
            }
        }

        while (p1 < m) {
            result[writePos++] = nums1[p1++];
        }

        while (p2 < n) {
            result[writePos++] = nums2[p2++];
        }

        return result;
    }



    // Driver code
    public static void main(String[] args) {
        // Convert ArrayList<ArrayList<Integer>> to int[][]
        int[][] nums1 = {
                {23, 33, 35, 41, 44, 47, 56, 91, 105},
                {1, 2},
                {1, 1, 1},
                {6},
                {12, 34, 45, 56, 67, 78, 89, 99}
        };

        int[][] nums2 = {
                {32, 49, 50, 51, 61, 99},
                {7},
                {1, 2, 3, 4},
                {-99, -45},
                {100}
        };

        // Test loop
        for (int i = 0; i < nums1.length; i++) {
            System.out.println((i + 1) + ".\tFirst array: " + arrayToString(nums1[i]));
            System.out.println("\tSecond array: " + arrayToString(nums2[i]));
            System.out.println("\tMerged array: " + arrayToString(bruteForce(nums1[i], nums2[i])));
            System.out.println("-".repeat(100));
        }
    }

    // Helper method to print arrays (since Arrays.toString() is a built-in)
    public static String arrayToString(int[] arr) {
        if (arr.length == 0) return "[]";

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
