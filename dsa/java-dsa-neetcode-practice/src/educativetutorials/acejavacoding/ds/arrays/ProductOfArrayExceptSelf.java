package educativetutorials.acejavacoding.ds.arrays;

import java.util.Arrays;
import java.util.Collections;

public class ProductOfArrayExceptSelf {

    /**
     * Given an array of integers, this function returns an array of the same length
     * where each element at index i is the product of all elements in the array except for the element at index i.
     * The function uses a brute force approach to calculate the products.
     *
     * @param arr The input array of integers
     * @return An array where each element is the product of all elements in the array except for the element at the same index
     */
    private static int[] bruteForce(int[] arr) {
        int[] product = new int[arr.length];
        int left = 1;

        for (int i = 0; i < arr.length; i++) {
            int currentProduct = 1;
            for (int j = i + 1; j < arr.length; j++) {
                currentProduct *= arr[j];
            }
            product[i] = currentProduct * left;
            left *= arr[i];
        }

        return product;
    }

    /**
     * Given an array of integers, this function returns an array of the same length
     * where each element at index i is the product of all elements in the array except for the element at index i.
     * The function uses an optimized approach to calculate the products.
     * It works by first calculating the product of all elements to the left of each index i, and then multiplying the product with the product of all elements to the right of index i.
     *
     * @param arr The input array of integers
     * @return An array where each element is the product of all elements in the array except for the element at the same index
     */
    private static int[] optimized(int[] arr) {
        int n = arr.length;
        int[] product = new int[n];

        // Pass 1: Calculate left products
        // product[i] will contain product of all elements to the left of i
        int leftProduct = 1;
        for (int i = 0; i < n; i++) {
            product[i] = leftProduct;
            leftProduct *= arr[i];
        }

        // Pass 2: Multiply with right products
        // Traverse from right, multiply product[i] with product of all elements to the right
        int rightProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            product[i] *= rightProduct;
            rightProduct *= arr[i];
        }

        return product;
    }

    public static String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i != arr.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        int[][] inputs = {
                {1, 2, 3, 4},
                {5, -3, 1, 2},
                {2, 2, 2, 0},
                {0, 0, 0, 0},
                {-1, -2, -4}
        };

        for (int i = 0; i < inputs.length; i++) {
            System.out.printf("%d.\t Array: %s%n", i + 1, Arrays.toString(inputs[i]));
//            System.out.printf("%n\t List of products: %s%n", Arrays.toString(bruteForce(inputs[i])));
            System.out.printf("%n\t List of products: %s%n", Arrays.toString(optimized(inputs[i])));
            System.out.println('-' + String.join("", Collections.nCopies(70, "-")) + '\n');
        }
    }
}
