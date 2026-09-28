package algorithms.slidingwindow;

/**
 * SLIDING WINDOW (Variable-Size / Dynamic)
 * 
 * WHAT IT IS:
 * A Sliding Window where the size of the window shrinks and expands dynamically 
 * based on a specific condition.
 * 
 * WHEN TO USE THIS:
 * - When a problem asks for the LONGEST or SHORTEST contiguous subarray/substring 
 *   that meets a certain dynamic condition (e.g., target sum, unique characters).
 * 
 * DATA STRUCTURE:
 * Arrays or Strings.
 * 
 * STRATEGY:
 * - Expand the `right` pointer to make the window larger, bringing in new elements.
 * - When the window VIOLATES the target condition (e.g., sum becomes too large, 
 *   or we encounter a duplicate character), we shrink the window by moving the 
 *   `left` pointer inward until the condition is valid again.
 * 
 * COMBINATION USAGE:
 * - HashMaps/Sets are often used inside the window to track character frequencies 
 *   for String-based sliding window problems.
 * - Monotonic Deque: To find the Maximum/Minimum element inside a sliding window 
 *   instantly, we combine a Sliding Window with a Monotonic Deque (built in Part A!).
 * 
 * COMPLEXITY:
 * Time: O(N). Even though there is a while loop inside a for loop, both `left` 
 *       and `right` pointers only ever move forward. Every element is visited at most twice.
 * Space: O(1) (or O(K) if storing distinct characters in a Map/Set).
 */
public class VariableWindow {

    /**
     * Problem 1: Smallest Subarray with a given sum
     * Finds the length of the SHORTEST contiguous subarray whose sum is >= target.
     */
    public static int minSubArrayLen(int target, int[] arr) {
        int minLength = Integer.MAX_VALUE;
        int windowSum = 0;
        int left = 0;

        for (int right = 0; right < arr.length; right++) {
            windowSum += arr[right]; // Expand window

            // Shrink window from the left as long as the condition is met!
            while (windowSum >= target) {
                // Record the valid window length
                int currentLength = right - left + 1;
                if (currentLength < minLength) {
                    minLength = currentLength;
                }
                
                windowSum -= arr[left]; // Remove left element from sum
                left++; // Shrink window
            }
        }

        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }

    /**
     * Problem 2: Longest Substring Without Repeating Characters
     * Uses a boolean array as a lightweight frequency map.
     */
    public static int lengthOfLongestSubstring(String s) {
        boolean[] seen = new boolean[128]; // Assuming ASCII
        int maxLength = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            char incomingChar = s.charAt(right);

            // If we've seen this character before in our current window, 
            // the window is INVALID. Shrink from the left until it's valid again.
            while (seen[incomingChar]) {
                char outgoingChar = s.charAt(left);
                seen[outgoingChar] = false;
                left++;
            }

            // Now the window is valid. Record it.
            seen[incomingChar] = true;
            int currentLength = right - left + 1;
            if (currentLength > maxLength) {
                maxLength = currentLength;
            }
        }

        return maxLength;
    }

    public static void main(String[] args) {
        System.out.println("--- VARIABLE-SIZE SLIDING WINDOW DEMO ---");
        
        int[] arr = {2, 1, 5, 2, 3, 2};
        int targetSum = 7;
        System.out.println("Target Sum >= " + targetSum + " in Array: [2, 1, 5, 2, 3, 2]");
        System.out.println("Min Subarray Length: " + minSubArrayLen(targetSum, arr)); 
        // Expected: 2 (from [5, 2])
        
        String str = "abcabcbb";
        System.out.println("\nString: " + str);
        System.out.println("Longest substring without repeating chars: " + lengthOfLongestSubstring(str)); 
        // Expected: 3 ("abc")
    }
}
