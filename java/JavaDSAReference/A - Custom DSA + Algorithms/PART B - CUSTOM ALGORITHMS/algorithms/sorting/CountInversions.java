package algorithms.sorting;

/**
 * COUNT INVERSIONS (Merge Sort based)
 *
 * WHAT IT IS:
 * An inversion is a pair of positions i < j with a[i] > a[j] - two elements in the
 * "wrong" order. Count them all.
 *   [2, 4, 1, 3, 5] -> (2,1), (4,1), (4,3) = 3
 *   Sorted array: 0. Reverse-sorted array: n(n-1)/2 (the maximum).
 *
 * WHEN TO USE THIS:
 * - Measuring how "unsorted" data is; = number of swaps Bubble/Insertion sort makes.
 * - Comparing two rankings (Kendall tau distance): how many pairs do two people
 *   order differently?
 * - The standard example of "piggy-backing" extra work on Merge Sort.
 *
 * STRATEGY:
 * Inversions = (inside the left half) + (inside the right half) + (crossing pairs).
 * The first two come from recursion. For crossing pairs, use the MERGE step: both
 * halves are sorted, so when the right half's element R is placed BEFORE the
 * remaining left elements, R is smaller than ALL of them -> add (mid - i + 1) in one
 * go, instead of comparing pairs one by one.
 * Equal elements are NOT inversions: take from the left on ties (`<=`).
 *
 * COMPLEXITY:
 * Time: O(N log N) (the brute force is O(N^2)).
 * Space: O(N) for the temporary array. Result is a long: up to n(n-1)/2.
 */
public class CountInversions {

    /** Number of inversions. The input array is not modified. */
    public static long countInversions(int[] input) {
        int[] a = input.clone();
        int[] temp = new int[a.length];
        return sortAndCount(a, temp, 0, a.length - 1);
    }

    private static long sortAndCount(int[] a, int[] temp, int left, int right) {
        if (left >= right) return 0;
        int mid = left + (right - left) / 2;
        long count = sortAndCount(a, temp, left, mid) + sortAndCount(a, temp, mid + 1, right);
        return count + mergeAndCount(a, temp, left, mid, right);
    }

    private static long mergeAndCount(int[] a, int[] temp, int left, int mid, int right) {
        for (int k = left; k <= right; k++) temp[k] = a[k];
        int i = left, j = mid + 1;
        long crossing = 0;
        for (int k = left; k <= right; k++) {
            if (i > mid) a[k] = temp[j++];
            else if (j > right) a[k] = temp[i++];
            else if (temp[i] <= temp[j]) a[k] = temp[i++]; // tie: not an inversion
            else {
                a[k] = temp[j++];
                crossing += mid - i + 1; // temp[j] is smaller than every remaining left element
            }
        }
        return crossing;
    }

    public static void main(String[] args) {
        System.out.println("--- COUNT INVERSIONS DEMO ---");
        System.out.println("[2, 4, 1, 3, 5]  -> " + countInversions(new int[]{2, 4, 1, 3, 5}));  // 3
        System.out.println("[1, 2, 3, 4]     -> " + countInversions(new int[]{1, 2, 3, 4}));     // 0
        System.out.println("[5, 4, 3, 2, 1]  -> " + countInversions(new int[]{5, 4, 3, 2, 1}));  // 10
        System.out.println("[3, 3, 1]        -> " + countInversions(new int[]{3, 3, 1}));        // 2

        int n = 100_000;
        int[] reversed = new int[n];
        for (int i = 0; i < n; i++) reversed[i] = n - i;
        System.out.println("Reverse-sorted 100,000 -> " + countInversions(reversed) + " (= n(n-1)/2, more than an int holds)");
    }
}
