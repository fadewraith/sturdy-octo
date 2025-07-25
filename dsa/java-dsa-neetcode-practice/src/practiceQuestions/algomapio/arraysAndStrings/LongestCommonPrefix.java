package practiceQuestions.algomapio.arraysAndStrings;

public class LongestCommonPrefix {

    private static String solution(String[] strs) {
        if(strs == null || strs.length == 0) {
            return "";
        }

        int min = Integer.MAX_VALUE;
        for(String s: strs) {
            if(s.length() < min) {
                min = s.length();
            }
        }

        int i = 0;
        while(i < min) {
            for(String s: strs) {
                if(s.charAt(i) != strs[0].charAt(i)) {
                    return strs[0].substring(0, i);
                }
            }
            i++;
        }

        return strs[0].substring(0, i);
    }
}


/**
 *
 * Detailed Explanation
 * Understanding the Problem
 * The “Longest Common Prefix” problem is a classic string processing task that asks you to find the longest starting substring that is shared among all strings in a given array. In simpler terms, given an array of strings, you are asked to return the common portion that all strings begin with. For example, given ["flower", "flow", "flight"], the longest common prefix is "fl", because it is the shared start of all the strings. If no common prefix exists, the correct result is an empty string "".
 *
 * Why This Problem Is Important
 * This problem is not only a popular interview question but also has many practical applications. In fields like natural language processing, text editors, file path matching, and search autocompletion, identifying common prefixes is a useful operation. Mastering this problem strengthens your skills in string manipulation, algorithm optimization, and pattern recognition.
 *
 * Solution 1: Vertical Scanning (Brute-Force, Column-Wise)
 * The most intuitive way to solve this problem is by comparing characters vertically. That means, for each character index i, you check whether all strings have the same character at that position. You keep going until a mismatch is found or you reach the end of one of the strings. At the point of mismatch, you return the substring from the beginning up to i.
 *
 * This method works well for small input sizes and is easy to implement.
 *
 * Time Complexity: O(n * m), where n is the number of strings and m is the length of the shortest string.
 *
 * Space Complexity: O(1)
 *
 * Solution 2: Horizontal Scanning
 * Instead of scanning column by column, horizontal scanning compares entire strings with each other. You start with the first string as the assumed prefix. Then, for each subsequent string, you reduce the prefix by comparing characters until the current string also starts with the same prefix.
 *
 * For example, starting with "flower", comparing with "flow" gives "flow", then comparing with "flight" reduces it to "fl". If at any point the prefix becomes empty, you can immediately return "".
 *
 * Time Complexity: O(n * m), same as vertical scanning.
 *
 * Space Complexity: O(1)
 *
 * Solution 3: Divide and Conquer
 * This approach divides the array into two halves, recursively finds the longest common prefix in each half, and then merges the two results. This is inspired by the divide and conquer paradigm used in merge sort.
 *
 * The idea is that if you know the longest common prefix of the left half and the right half, then the answer must be the prefix they share. This process continues until you're comparing two strings at a time, eventually merging all results upward.
 *
 * This method is more efficient for very large arrays because it reduces the number of character comparisons.
 *
 * Time Complexity: O(n * m), in the worst case.
 *
 * Space Complexity: O(log n), due to recursion stack.
 *
 * Solution 4: Binary Search on Prefix Length
 * A clever optimization is to use binary search to find the maximum length L such that all strings have the same prefix of length L. You perform binary search in the range [0, minLength], where minLength is the length of the shortest string.
 *
 * In each step, you test whether all strings share the same prefix of length mid. If they do, you know the answer lies in the upper half; if not, in the lower half. This reduces the number of character comparisons, especially when the strings are long.
 *
 * Time Complexity: O(n * log m), where n is the number of strings and m is the length of the shortest string.
 *
 * Space Complexity: O(1)
 *
 * Worked Example
 * Consider the input array ["interspecies", "interstellar", "interstate"]:
 *
 * All strings start with "i" → continue
 * All have "n", "t", "e", "r" → continue
 * Next is "s" in all strings → continue
 * Next is "p", "t", and "t" → mismatch found
 * So, the longest common prefix is "inters".
 *
 * Edge Cases
 * If the input array is empty, return "".
 * If any string in the array is empty, return "".
 * If all strings are identical, return that string.
 * If no characters are shared at the start, return "".
 * Conclusion
 * The “Longest Common Prefix” problem offers a valuable lesson in understanding multiple ways to approach string comparison tasks. While the brute-force and horizontal methods are straightforward and easy to code, divide and conquer and binary search provide powerful optimizations for large datasets. Depending on the input size and context, each of these solutions has its own advantages.
 *
 * Mastering this problem deepens your understanding of strings, recursion, and optimization — all crucial topics for technical interviews and real-world programming alike.
 * */