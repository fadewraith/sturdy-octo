package algorithms.searching;

/**
 * BINARY SEARCH (Iterative, Recursive, and the classic variants)
 *
 * WHEN TO USE THIS:
 * - When you need to find a target element (or its insertion point) in O(log N) time.
 * - The primary prerequisite is that the data MUST BE SORTED - or, more generally,
 *   that some yes/no question flips from "no" to "yes" exactly once along the array
 *   ("monotonic predicate"). Rotated arrays and peaks work because of that idea.
 *
 * DATA STRUCTURE:
 * - Operates almost exclusively on Arrays (or contiguous memory blocks).
 * - Does NOT work well on Linked Lists because O(1) random access is required to jump
 *   to the `mid` index. (Binary searching a Linked List takes O(N) time due to traversal).
 *
 * VARIATIONS:
 * - Iterative: Preferred in production code because it uses O(1) space and won't
 *   cause StackOverflow errors on massive arrays.
 * - Recursive: Easier to conceptually grasp, but uses O(log N) stack space.
 *
 * THE TWO TEMPLATES:
 * - "Find exact" uses `while (left <= right)` on a closed range [left, right].
 * - "Find boundary" (lower/upper bound) uses `while (left < right)` on a half-open
 *   range [left, right) and never returns early. It ends with left == right == the
 *   first index where the predicate is true. Almost every variant is a boundary search:
 *     lowerBound(x)  = first index with arr[i] >= x
 *     upperBound(x)  = first index with arr[i] >  x
 *     firstOccurrence = lowerBound, if that slot holds x
 *     lastOccurrence  = upperBound - 1, if that slot holds x
 *     count(x)        = upperBound(x) - lowerBound(x)
 *     searchInsert    = lowerBound (same as Java's Arrays.binarySearch insertion point)
 *
 * ROTATED SORTED ARRAY ([4,5,6,7,0,1,2]):
 * One half around `mid` is always sorted. Check whether the target lies inside the
 * sorted half's range; if yes go there, otherwise go to the other half.
 * With DUPLICATES ([1,0,1,1,1]) you sometimes can't tell which half is sorted
 * (arr[left] == arr[mid] == arr[right]); then just shrink both ends by one.
 * That makes the worst case O(N), which is unavoidable.
 *
 * PEAK ELEMENT: if arr[mid] < arr[mid + 1] we are on an uphill slope, so a peak
 * must exist to the right; otherwise one exists at mid or to the left.
 *
 * COMBINATION USAGE:
 * - Often used after a Sorting algorithm (Sort first -> then Binary Search multiple queries).
 * - "Binary search on the answer" (BinarySearchOnAnswer.java) is the same boundary
 *   template applied to a range of candidate answers instead of array indices.
 *
 * PSEUDOCODE (Iterative):
 * left = 0, right = length - 1
 * while left <= right:
 *     mid = left + (right - left) / 2
 *     if arr[mid] == target: return mid
 *     if arr[mid] < target: left = mid + 1
 *     else: right = mid - 1
 *
 * COMPLEXITY:
 * Time: O(log N) worst/avg, O(1) best (found at mid immediately).
 *       Rotated search WITH duplicates: O(N) worst case.
 *       2D matrix: O(log(R * C)). Integer square root: O(log N).
 * Space: O(1) for iterative, O(log N) for recursive.
 */
public class BinarySearch {

    /**
     * ITERATIVE Binary Search (Preferred)
     */
    public static int searchIterative(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            // NOTE: We use `left + (right - left) / 2` instead of `(left + right) / 2`
            // to prevent Integer Overflow if left and right are massive numbers!
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                return mid; // Target found
            }

            if (arr[mid] < target) {
                left = mid + 1; // Discard left half
            } else {
                right = mid - 1; // Discard right half
            }
        }
        return -1; // Target not found
    }

    /**
     * RECURSIVE Binary Search
     */
    public static int searchRecursive(int[] arr, int target) {
        return searchRecHelper(arr, target, 0, arr.length - 1);
    }

    private static int searchRecHelper(int[] arr, int target, int left, int right) {
        if (left > right) {
            return -1; // Base case: not found
        }

        int mid = left + (right - left) / 2;

        if (arr[mid] == target) {
            return mid;
        } else if (arr[mid] < target) {
            return searchRecHelper(arr, target, mid + 1, right);
        } else {
            return searchRecHelper(arr, target, left, mid - 1);
        }
    }

    // --------------------------------------------------------
    // BOUNDARY SEARCHES (array sorted ascending, duplicates allowed)
    // --------------------------------------------------------

    /** First index i with arr[i] >= target, or arr.length if none. (C++ lower_bound) */
    public static int lowerBound(int[] arr, int target) {
        int left = 0, right = arr.length; // half-open [left, right)
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] < target) left = mid + 1; // mid is too small, answer is to the right
            else right = mid;                      // mid might be the answer, keep it
        }
        return left;
    }

    /** First index i with arr[i] > target, or arr.length if none. (C++ upper_bound) */
    public static int upperBound(int[] arr, int target) {
        int left = 0, right = arr.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] <= target) left = mid + 1;
            else right = mid;
        }
        return left;
    }

    /** Index of the first copy of target, or -1. */
    public static int firstOccurrence(int[] arr, int target) {
        int i = lowerBound(arr, target);
        return (i < arr.length && arr[i] == target) ? i : -1;
    }

    /** Index of the last copy of target, or -1. */
    public static int lastOccurrence(int[] arr, int target) {
        int i = upperBound(arr, target) - 1;
        return (i >= 0 && arr[i] == target) ? i : -1;
    }

    /** How many times target appears - O(log N) even if it appears a million times. */
    public static int countOccurrences(int[] arr, int target) {
        return upperBound(arr, target) - lowerBound(arr, target);
    }

    /** Index of target if present, otherwise the index where it would be inserted. */
    public static int searchInsert(int[] arr, int target) {
        return lowerBound(arr, target);
    }

    // --------------------------------------------------------
    // ROTATED SORTED ARRAYS
    // --------------------------------------------------------

    /** Search in a rotated sorted array of DISTINCT values. Returns index or -1. O(log N) */
    public static int searchRotated(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) return mid;

            if (arr[left] <= arr[mid]) {
                // left half [left..mid] is sorted
                if (arr[left] <= target && target < arr[mid]) right = mid - 1;
                else left = mid + 1;
            } else {
                // right half [mid..right] is sorted
                if (arr[mid] < target && target <= arr[right]) left = mid + 1;
                else right = mid - 1;
            }
        }
        return -1;
    }

    /** Search in a rotated sorted array that may contain DUPLICATES. O(log N) avg, O(N) worst */
    public static boolean searchRotatedWithDuplicates(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) return true;

            if (arr[left] == arr[mid] && arr[mid] == arr[right]) {
                // can't tell which half is sorted - drop both ends (neither is the target)
                left++;
                right--;
            } else if (arr[left] <= arr[mid]) {
                if (arr[left] <= target && target < arr[mid]) right = mid - 1;
                else left = mid + 1;
            } else {
                if (arr[mid] < target && target <= arr[right]) left = mid + 1;
                else right = mid - 1;
            }
        }
        return false;
    }

    // --------------------------------------------------------
    // OTHER CLASSIC VARIANTS
    // --------------------------------------------------------

    /**
     * Index of ANY peak (an element strictly greater than its neighbours; outside
     * the array counts as -infinity). Adjacent elements must differ. O(log N)
     */
    public static int findPeakElement(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] < arr[mid + 1]) left = mid + 1; // going uphill -> peak on the right
            else right = mid;                            // going downhill -> peak at mid or left
        }
        return left;
    }

    /**
     * Search a matrix where each row is sorted and each row's first value is greater
     * than the previous row's last value. Treat it as one flat sorted array:
     * flat index k -> row k / cols, column k % cols. Returns {row, col} or null.
     */
    public static int[] searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0) return null;
        int rows = matrix.length, cols = matrix[0].length;
        long left = 0, right = (long) rows * cols - 1;
        while (left <= right) {
            long mid = left + (right - left) / 2;
            int value = matrix[(int) (mid / cols)][(int) (mid % cols)];
            if (value == target) return new int[]{(int) (mid / cols), (int) (mid % cols)};
            if (value < target) left = mid + 1;
            else right = mid - 1;
        }
        return null;
    }

    /** floor(sqrt(n)) for n >= 0 without floating point. Uses long to avoid mid*mid overflow. */
    public static int integerSqrt(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");
        long left = 0, right = n, answer = 0;
        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (mid * mid <= n) {
                answer = mid;      // mid works, try bigger
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return (int) answer;
    }

    public static void main(String[] args) {
        System.out.println("--- BINARY SEARCH DEMO ---");
        int[] sortedArr = {2, 5, 8, 12, 16, 23, 38, 56, 72, 91};
        System.out.println("Array: [2, 5, 8, 12, 16, 23, 38, 56, 72, 91]");

        System.out.println("\nSearching for 23 (Iterative): Index " + searchIterative(sortedArr, 23)); // Expected: 5
        System.out.println("Searching for 72 (Recursive): Index " + searchRecursive(sortedArr, 72)); // Expected: 8
        System.out.println("Searching for 100 (Iterative): Index " + searchIterative(sortedArr, 100)); // Expected: -1

        int[] dups = {1, 2, 2, 2, 3, 5, 5, 8};
        System.out.println("\nWith duplicates: [1, 2, 2, 2, 3, 5, 5, 8]");
        System.out.println("lowerBound(2) = " + lowerBound(dups, 2));           // 1
        System.out.println("upperBound(2) = " + upperBound(dups, 2));           // 4
        System.out.println("first/last 5  = " + firstOccurrence(dups, 5) + ", " + lastOccurrence(dups, 5)); // 5, 6
        System.out.println("count(2)      = " + countOccurrences(dups, 2));     // 3
        System.out.println("searchInsert(4) = " + searchInsert(dups, 4));       // 5

        int[] rotated = {4, 5, 6, 7, 0, 1, 2};
        System.out.println("\nRotated: [4, 5, 6, 7, 0, 1, 2]");
        System.out.println("searchRotated(0) = " + searchRotated(rotated, 0)); // 4
        System.out.println("searchRotated(3) = " + searchRotated(rotated, 3)); // -1
        int[] rotatedDups = {1, 0, 1, 1, 1};
        System.out.println("Rotated with duplicates [1, 0, 1, 1, 1] contains 0? "
                + searchRotatedWithDuplicates(rotatedDups, 0)); // true

        int[] mountain = {1, 3, 20, 4, 1, 0};
        System.out.println("\nPeak of [1, 3, 20, 4, 1, 0] at index " + findPeakElement(mountain)); // 2

        int[][] matrix = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
        int[] pos = searchMatrix(matrix, 16);
        System.out.println("16 in matrix at row " + pos[0] + ", col " + pos[1]); // row 1, col 2

        System.out.println("integerSqrt(2147395600) = " + integerSqrt(2147395600)); // 46340
    }
}
