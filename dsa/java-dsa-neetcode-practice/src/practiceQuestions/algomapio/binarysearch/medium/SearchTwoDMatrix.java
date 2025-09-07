package practiceQuestions.algomapio.binarysearch.medium;

public class SearchTwoDMatrix {

    /**
     * Brute Force
     * Understand the problem: Search for a target value in an m x n matrix where each row is sorted and the first element of each row is greater than the last element of the previous row.
     * Iterate through each row in the matrix.
     * For each row, check if the target exists in that row using a linear search or Python's 'in' operator.
     * If the target is found in any row, return True.
     * If the target is not found after checking all rows, return False.
     * */

    private static boolean bruteForce(int[][] matrix, int target) {
        for(int[] row : matrix) {
            for(int num : row) {
                if(num == target) {
                    return true;
                }
            }
        }
        return false;
        // Time: O(m * n)
        // Space: O(1)
    }

    /**
     * Optimal Solution
     * The optimal solution treats the matrix as a flattened sorted array and uses binary search, achieving O(log(m * n)) time complexity:
     *
     * Get the dimensions m (rows) and n (columns) of the matrix, and compute the total elements t as m * n.
     * Initialize two pointers, l to 0 and r to t-1, for binary search.
     * While l is less than or equal to r, compute the middle index mid as (l + r) // 2.
     * Convert mid to matrix coordinates: row (mid_i) as mid // n and column (mid_j) as mid % n.
     * Retrieve the middle element mid_num as matrix[mid_i][mid_j].
     * If mid_num equals the target, return True.
     * If mid_num is greater than the target, adjust r to mid - 1 to search the left half.
     * If mid_num is less than the target, adjust l to mid + 1 to search the right half.
     * Return False if the target is not found.
     * */

    private static boolean optimal(int[][] matrix, int target) {
        int m = matrix.length; // row
        int n = matrix[0].length; // col
        int left = 0;
        int right = m * n - 1;

        while(left <= right) {
            int mid = (left + right) / 2;
            int i = mid / n;
            int j = mid % n;
            int midNum = matrix[i][j];

            if(target == midNum) {
                return true;
            } else if(target < midNum) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return false;

        // Time Complexity: O(log(m * n))
        // Space Complexity: O(1)
    }

}


/**
 * Detailed Explanation
 * Understanding the Problem: Search a 2D Matrix
 * The “Search a 2D Matrix” problem asks us to efficiently search for a target value in a 2D matrix that has the following sorted properties:
 *
 * Each row is sorted in ascending order.
 * The first integer of each row is greater than the last integer of the previous row.
 * This structure allows us to treat the 2D matrix almost like a 1D sorted array.
 *
 * Example:
 *
 * Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
 * Output: true
 * Why This Problem Matters
 * This problem is a hybrid of matrix traversal and binary search, testing your ability to convert between 2D and 1D indexing. It's frequently asked in coding interviews and serves as a foundation for more complex matrix and search problems.
 *
 * Optimal Approach: Binary Search Over Flattened Matrix
 * Because of the matrix’s sorted structure across rows and columns, we can apply a binary search as if the matrix were a single flat array. The total number of elements is m * n, and we can use math to translate flat indices to 2D coordinates.
 *
 * Steps:
 * Let m be the number of rows and n be the number of columns.
 * Initialize binary search with left = 0 and right = m * n - 1.
 * While left <= right:
 * Compute the middle index: mid = Math.floor((left + right) / 2).
 * Map mid to 2D coordinates:
 * row = Math.floor(mid / n)
 * col = mid % n
 * Get the element: matrix[row][col].
 * If the element equals the target, return true.
 * If it's less than the target, search the right half: left = mid + 1.
 * If it's greater than the target, search the left half: right = mid - 1.
 * If the loop ends, return false — the target does not exist in the matrix.
 * Example Walkthrough
 * Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 16
 * m = 3, n = 4 → total = 12 elements
 * Initial range: left = 0, right = 11
 *
 * mid = 5 → matrix[1][1] = 11 → 11 < 16 → move left to 6
 * mid = 8 → matrix[2][0] = 23 → 23 > 16 → move right to 7
 * mid = 6 → matrix[1][2] = 16 → match → return true
 * Time and Space Complexity
 * Time Complexity: O(log(m * n)), where m is rows and n is columns. This is due to binary search over all elements.
 * Space Complexity: O(1), as the algorithm uses only constant extra memory.
 *
 * Edge Cases to Consider
 * Empty matrix → return false
 * Single row or column → should still apply binary search properly
 * Target smaller than the smallest element or larger than the largest → return false early
 * Conclusion
 * The “Search a 2D Matrix” problem teaches how binary search can be applied beyond 1D arrays by mapping indices across dimensions. This approach is both time-efficient and elegant, making it ideal for large datasets structured in tabular formats.
 * */