package educativetutorials.acejavacoding.ds.arrays;

import java.util.Arrays;

public class RearrangeSortedArrayMaxMinForm {

//    TimeO(n)
//    Space(O(n))

    private static int[] solution1(int[] arr) {
        int n = arr.length;

        if(n == 0) return new int[0];

        int res[] = new int[n];
        int index = 0;
        int mid = n / 2;

        // Iterate through half of the array
        for(int i = 0; i < mid; i++) {
            // Append the largest remaining element (from the end of the array)
            res[index++] = arr[n - (i + 1)];
            // Append the smallest remaining element (from the start of the array)
            res[index++] = arr[i];
        }

        // Handle middle element for odd-length arrays
        if(n % 2 != 0) {
            res[index] = arr[mid];
        }

        return res;
    }

    // O(n), s - O(1)
    private static int[] solution2(int[] arr) {
        int n = arr.length;
        if(n == 0) return arr;

        int maxIndex = n - 1, minIndex = 0, maxElement = arr[n - 1] + 1;

        // encode
        for(int i = 0; i < n; i++) {
            if(i % 2 == 0) {
                arr[i] += (arr[maxIndex] % maxElement) * maxElement;
                maxIndex--;
            } else {
                arr[i] += (arr[minIndex] % maxElement) * maxElement;
                minIndex++;
            }
        }

        // decode
        for(int i = 0; i < n; i++) {
            arr[i] /= maxElement;
        }

        return arr;
    }



    public static void main(String[] args) {
        int[][] inputArray = {
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {11, 22, 33, 44, 55, 66, 77, 88},
                {1, 2, 4, 8, 16, 32, 64, 128, 512, 1024},
                {3, 3, 5, 5, 7, 7, 9, 9, 11, 11, 13, 13},
                {100, 233, 544, 753, 864, 935, 1933, 2342}
        };

        for (int i = 0; i < inputArray.length; ++i) {
            System.out.print((i + 1) + ".\tOriginal array: ");
            System.out.print(Arrays.toString(inputArray[i]));
            System.out.print("\n\tRearranged array: ");
            int[] rearranged = solution2(inputArray[i]);
            System.out.print(Arrays.toString(rearranged));
            System.out.println("\n" + new String(new char[100]).replace('\0', '-'));
        }
    }
}
