package educativetutorials.codingpatterns.threeslidingwindow.medium;

import java.util.HashMap;
import java.util.Map;

public class FruitIntoBaskets {

    private static int totalFruit(int[] fruits) {
        // Map to count the frequency of fruit types in the current window
        Map<Integer, Integer> baskets = new HashMap<>();

        // Maximum number of fruits collected so far
        int collected = 0;

        // Left boundary of the sliding window
        int left = 0;

        // Iterate over each tree (right boundary of the sliding window)
        for (int right = 0; right < fruits.length; right++) {
            // Add the current fruit to the baskets and increment its count
            baskets.put(fruits[right], baskets.getOrDefault(fruits[right], 0) + 1);

            // If there are more than two types of fruits in the baskets
            while (baskets.size() > 2) {
                // Decrease the count of the fruit at the left boundary
                baskets.put(fruits[left], baskets.get(fruits[left]) - 1);

                // Remove the fruit type from the baskets if its count becomes zero
                if (baskets.get(fruits[left]) == 0) {
                    baskets.remove(fruits[left]);
                }

                // Move the left boundary to the right
                left++;
            }

            // Update the maximum number of fruits collected
            collected = Math.max(collected, right - left + 1);
        }

        // Return the maximum number of fruits that can be collected
        return collected;
    }

    public static void main(String[] args) {
        int[][] fruits = {{3,4,2,1,3,2},
                {2,2,2,3,1,2,4,4,4,4},
                {1,1,1,1,1,1,1,1,1,1},
                {2,3,4,1,3,3,1,2,3,4,1,5,2,5,7,7},
                {5,4,3,2,1,1}};

        for (int i = 0; i < fruits.length; i++) {
            System.out.print((i + 1) + ".\tFruits: [");
            for (int j = 0; j < fruits[i].length; j++) {
                if (j > 0) System.out.print(", ");
                System.out.print(fruits[i][j]);
            }
            System.out.println("]");
            System.out.println("\n\tMaximum number of fruit(s) collected: " + totalFruit(fruits[i]));
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}


/**
Problem: Fruit Into Baskets

You are given an integer array fruits, where fruits[i] represents the type of fruit
produced by the i-th tree in a row.

Rules:
1. You have exactly two baskets.
2. Each basket can hold only one type of fruit, but unlimited quantity.
3. You can start from any index in the array.
4. Once you start, you must move to the right, collecting exactly one fruit from each tree.
5. You must stop when you encounter a fruit type that cannot fit into either of the two baskets.

Goal:
Return the maximum number of fruits you can collect.

Approach (Sliding Window):
- Use two pointers (left and right) to maintain a window.
- Use a HashMap to store fruit types and their counts in the current window.
- Expand the window by moving right.
- If the map contains more than 2 fruit types, shrink the window from the left.
- Track the maximum window size.


*/



/**
Solution: Fruit Into Baskets

Statement:
While visiting a farm of fruits, you are given a row of fruits represented by
an integer array fruits, where fruits[i] is the type of fruit the i-th tree produces.

Rules:
1. You have only two baskets.
2. Each basket can hold only one type of fruit, but unlimited quantity.
3. You can start collecting from any tree.
4. Once you start, you must move to the right and pick exactly one fruit from each tree.
5. You must stop when you encounter a fruit type that cannot fit into your baskets.

Goal:
Return the maximum number of fruits you can collect.

Constraints:
1 <= fruits.length <= 10^3
0 <= fruits[i] < fruits.length

Solution Explanation:
The challenge is to collect the maximum number of fruits while only carrying
at most two distinct types of fruits at any time.

This problem is solved using the Sliding Window technique:

- We maintain a window using two pointers (left and right).
- A HashMap (baskets) keeps track of fruit types and their counts in the current window.
- We expand the window by moving the right pointer.
- If the number of distinct fruit types exceeds 2, we shrink the window from the left.
- We keep track of the maximum size of a valid window.

Step-by-Step:
1. Initialize:
   - A HashMap 'baskets' to store fruit counts.
   - An integer 'left' to mark the start of the window.
   - An integer 'collected' to store the maximum fruits collected.

2. Traverse the array using 'right':
   - Add fruits[right] to the map and increase its count.

3. If baskets size exceeds 2:
   - Remove fruits from the left side of the window.
   - Decrease their count in the map.
   - If count becomes 0, remove that fruit type from the map.
   - Move 'left' forward.

4. Update the result:
   - collected = max(collected, right - left + 1)

5. Return collected.

This ensures we always maintain a valid window with at most 2 fruit types
and compute the maximum possible length.
*/