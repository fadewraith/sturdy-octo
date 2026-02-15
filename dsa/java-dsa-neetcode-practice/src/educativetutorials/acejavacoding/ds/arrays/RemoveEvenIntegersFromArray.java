package educativetutorials.acejavacoding.ds.arrays;

import java.util.Arrays;

public class RemoveEvenIntegersFromArray {

    public static int[] removeEven(int[] arr) {
        int m = 0;
        //Find number of odd elements in arr
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] % 2 != 0) m++;
        }

        //Create odds array with the size equal to the number of odd elements in arr
        int[] odds = new int[m];
        int result_index = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] % 2 != 0)// Check if the item in the array is not even
                odds[result_index++] = arr[i]; // If it isn't even, append it to the odds array
        }

        return odds;
    }

    public static int[] optimizedRemoveEven(int[] arr) {
        if (arr == null) {
            return new int[0];
        }

        return Arrays.stream(arr)
                .filter(n -> n % 2 != 0)
                .toArray();
    }

    public static void main(String args[]) {

        int[][] inputs = {
                {3, 2, 41, 3, 34},
                {-5, -4, -3, -2, -1},
                {-1, 2, 3, -4, -10},
                {1, 2, 3, 7},
                {2, 4, 6, 8, 10},

        };

        for (int i = 0; i < inputs.length; ++i) {
            System.out.print((i + 1) + ".\tInput list: ");
            System.out.println(Arrays.toString(inputs[i]));

//            int[] result = removeEven(inputs[i]);
            int[] result = optimizedRemoveEven(inputs[i]);

            System.out.print("\n\tFinal list: ");
            System.out.println(Arrays.toString(result));
            System.out.println(new String(new char[100]).replace('\0', '-'));

        }
    }
}
