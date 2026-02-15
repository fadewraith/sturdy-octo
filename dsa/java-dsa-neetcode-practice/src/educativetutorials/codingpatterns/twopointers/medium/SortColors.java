package educativetutorials.codingpatterns.twopointers.medium;

import educativetutorials.codingpatterns.commonutilities.CommonUtils;

import java.util.Arrays;

public class SortColors {

    /**
     * Naive approach
     * The most basic naive approach is to sort the array using any conventional sorting algorithm that sorts the array in place. This will rearrange the elements in the required order: all 0s (red), followed by 1s (white), and then 2s (blue). The time complexity will depend on the sorting algorithm used. For example, using merge sort would result in a time complexity of
     * O(nlog(n))
     * */

    private static int[] sortColors(int[] colors) {
        // Initialize the start, current and end pointers
        int start = 0;
        int current = 0;
        int end = colors.length - 1;

        // iterate through the list until the current pointer exceeds the end pointer
        while (current <= end) {
            if (colors[current] == 0) {
                // if the current element is 0 (red), swap it with the element at the start pointer
                // this ensures the red element is placed at the beginning of the array
                // move both start and current pointers forward
                CommonUtils.swap(colors, start++, current++);
            } else if (colors[current] == 1) {
                // if the current element is 1 white, just move the current pointer one position forward
                current++;
            } else {
                // if the current element is 2 blue, swap it with the element at the end pointer
                // this pushes the blue element to the end of the array
                CommonUtils.swap(colors, current, end--);
            }
        }
        return colors;
    }



    public static void main(String[] args) {
        int[][] inputs = {
                {0, 1, 0},
                {1, 1, 0, 2},
                {2, 1, 1, 0, 0},
                {2, 2, 2, 0, 1, 0},
                {2, 1, 1, 0, 1, 0, 2}
        };

        for (int i = 0; i < inputs.length; i++) {
            System.out.println((i + 1) + ".\tcolors: " + Arrays.toString(inputs[i]));
            int[] sortedColors = sortColors(inputs[i].clone());
            System.out.println("\n\tThe sorted array is: " + Arrays.toString(sortedColors));
            System.out.println("-".repeat(100));
        }
    }
}
