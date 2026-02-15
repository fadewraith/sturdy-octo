package educativetutorials.acejavacoding.ds.arrays;

import java.util.HashMap;
import java.util.Map;

public class FirstNonRepeatingIntegerInArray {

    /**
     * This function takes an array of integers and returns the first non-repeating integer in the array.
     * The function uses a brute force approach to find the first non-repeating integer.
     * It works by iterating through the array and checking if each element is repeated anywhere else in the array.
     * If an element is not repeated, it is returned. If no such element is found, -1 is returned.
     *
     * @param arr The input array of integers
     * @return The first non-repeating integer in the array, or -1 if no such element is found
     * O(n^2) time and O(1) space
     */
    private static int bruteForce(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            int j = 0;

            while(j < n) {
                if(i != j && arr[i] == arr[j]) {
                    break;
                }
                j++;
            }

            if(j == n) return arr[i];
        }
        return -1;
    }

    /**
     * This function takes an array of integers and returns the first non-repeating integer in the array.
     * The function uses an optimized approach to find the first non-repeating integer.
     * It works by first creating a HashMap to store the frequency of each element in the array.
     * Then it iterates through the array again and checks if the frequency of each element is equal to 1.
     * If such an element is found, it is returned. If no such element is found, -1 is returned.
     *
     * @param arr The input array of integers
     * @return The first non-repeating integer in the array, or -1 if no such element is found
     * O(n) time and O(n) space
     */
    private static int optimized(int[] arr) {
        Map<Integer, Integer> frequency = new HashMap<>();

        for(int num : arr) {
            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
        }

        for(int x : arr) {
            if(frequency.get(x) == 1) return x;
        }

        return -1;
    }

    /**
     * This function takes an array of integers and returns the first non-repeating integer in the array using XOR.
     * The function uses an optimized approach to find the first non-repeating integer.
     * It works by iterating through the array and XORing each element with a result variable.
     * At the end of the iteration, the result variable will contain the first non-repeating integer.
     * If no such element is found, -1 is returned.
     *
     * @param arr The input array of integers
     * @return The first non-repeating integer in the array, or -1 if no such element is found
     * O(n) time and O(1) space
     */
    private static int optimizedXor(int[] arr) {
        int res = 0;
        for (int x : arr) {
            res ^= x;
        }
        return res;
    }


    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 2, 1};
        System.out.println(bruteForce(arr));
    }
}
