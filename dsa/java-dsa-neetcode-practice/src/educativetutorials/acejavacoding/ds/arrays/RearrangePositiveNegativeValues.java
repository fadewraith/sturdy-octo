package educativetutorials.acejavacoding.ds.arrays;

import educativetutorials.codingpatterns.commonutilities.CommonUtils;

import java.util.Arrays;

public class RearrangePositiveNegativeValues {

    // Time: O(n)
    // Space: O(n)

    private static int[] bruteForce(int[] arr) {
        if(arr.length == 0) return new int[]{};
        int len = arr.length;
        int[] res = new int[len];
        int i = 0;

        for(int item : arr) {
            if(item < 0) res[i++] = item;
        }

        for(int item : arr) {
            if(item >= 0) res[i++] = item;
        }

        return res;
    }

    private static void optimal(int[] arr) {
        if(arr.length == 0) return;
        int len = arr.length;
        int leftIndex = 0;
        for(int i = 0; i < len; i++) {
            if(arr[i] < 0) {
                CommonUtils.swap(arr, i, leftIndex++);
            }
        }

    }

    public static void main(String[] args) {
        int[][] inputs = {
                {10, 4, 6, 23, 7},
                {-3, 20, -1, 8},
                {2, -5, -4, 43, 2},
                {-3, -10, -19, 21, -17},
                {25, 50, 75, 100, 400}
        };

        for (int i = 0; i < inputs.length; i++) {
            System.out.println((i + 1) + ".\tArray: " + Arrays.toString(inputs[i]));
//            int[] res = bruteForce(inputs[i]);
//            System.out.println("\tResult: " + Arrays.toString(res));
            optimal(inputs[i]);
            System.out.println("\tResult: " + Arrays.toString(inputs[i]));
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}
