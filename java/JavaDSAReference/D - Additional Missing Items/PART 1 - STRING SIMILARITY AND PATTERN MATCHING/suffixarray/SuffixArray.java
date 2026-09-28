package algorithms.strings.suffixarray;

import java.util.Arrays;
import java.util.Comparator;

/**
 * SUFFIX ARRAY
 * 
 * WHAT IT IS:
 * A sorted array of all suffixes of a string, represented by their starting indices.
 * 
 * WHEN TO USE THIS / APPLIES TO:
 * - Fast substring existence/count queries, pattern matching.
 * - Genomic search, full-text indexing.
 * 
 * COMBINATION:
 * - Usually built alongside an LCP (Longest Common Prefix) array to solve problems 
 *   like longest repeated substring.
 * 
 * COMPLEXITY:
 * - This is the Naive O(N^2 log N) construction using Arrays.sort.
 * - *BONUS NOTE*: In production, faster O(N log N) (Prefix Doubling) or O(N) (SA-IS) 
 *   algorithms are used to construct the array.
 * 
 * PSEUDOCODE:
 * 1. Create array of indices [0...N-1].
 * 2. Sort the indices based on the lexicographical comparison of the suffixes 
 *    they represent.
 * 3. Search using Binary Search over the sorted suffix array.
 */
public class SuffixArray {

    public static Integer[] buildSuffixArray(String text) {
        int n = text.length();
        Integer[] suffixArray = new Integer[n];
        
        for (int i = 0; i < n; i++) {
            suffixArray[i] = i;
        }

        Arrays.sort(suffixArray, new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                return text.substring(i1).compareTo(text.substring(i2));
            }
        });

        return suffixArray;
    }

    public static boolean search(String text, String pattern, Integer[] suffixArray) {
        int left = 0;
        int right = suffixArray.length - 1;
        int m = pattern.length();
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int suffixIndex = suffixArray[mid];
            
            String suffixPrefix = text.substring(suffixIndex, Math.min(suffixIndex + m, text.length()));
            int cmp = suffixPrefix.compareTo(pattern);
            
            if (cmp == 0) return true;
            if (cmp < 0) left = mid + 1;
            else right = mid - 1;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("--- SUFFIX ARRAY DEMO ---");
        String text = "banana";
        Integer[] sa = buildSuffixArray(text);
        
        System.out.println("Sorted Suffixes for 'banana':");
        for (int index : sa) {
            System.out.println(index + ": " + text.substring(index));
        }
        
        System.out.println("\nSearching for 'nan': " + search(text, "nan", sa));
        System.out.println("Searching for 'band': " + search(text, "band", sa));
    }
}
