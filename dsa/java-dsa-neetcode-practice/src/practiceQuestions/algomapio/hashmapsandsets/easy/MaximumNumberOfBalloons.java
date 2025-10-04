package practiceQuestions.algomapio.hashmapsandsets.easy;

import java.util.HashMap;
import java.util.Map;

public class MaximumNumberOfBalloons {

    private static int solution(String s) {
        String balloon = "balloon";
        Map<Character, Integer> map = new HashMap<>();
        for(char item : s.toCharArray()) {
            if(balloon.indexOf(item) != -1) {
                map.put(item, map.getOrDefault(item, 0) + 1);
            }
        }

        if(!map.containsKey('b') ||
                !map.containsKey('a') ||
                !map.containsKey('l') ||
                !map.containsKey('o') ||
                !map.containsKey('n')) {
            return 0;
        } else {
            return Math.min(Math.min(map.get('b'), map.get('a')), Math.min(map.get('l') / 2, Math.min(map.get('o') / 2, map.get('n'))));
        }
    }

    public static void main(String[] args) {
        String s = "loonbalxballpoon";
        System.out.println(solution(s));
    }
}

/**
 * Detailed Explanation
 * Understanding the Problem: Maximum Number of Balloons
 * The “Maximum Number of Balloons” problem asks us to determine how many times the word "balloon" can be formed from the letters in a given input string text. Each instance of the word "balloon" requires the following characters: one 'b', one 'a', two 'l''s, two 'o''s, and one 'n'.
 *
 * For example, given the input "loonbalxballpoon", we can form the word "balloon" twice.
 *
 * Why This Problem Matters
 * This problem tests your ability to perform character frequency analysis and enforce resource constraints—an important pattern in string manipulation problems. It's directly applicable to scenarios such as resource allocation, recipe management, and string construction problems.
 *
 * Approach: Frequency Counting
 * The key to solving this problem efficiently is to count how many times each required letter appears in the input and determine how many full sets of "balloon" we can extract from it.
 *
 * Steps:
 * Initialize a hash map (or dictionary) to keep track of the frequency of each character in text.
 * Loop through each character in text. If the character is one of the required ones (i.e., in "balloon"), increment its count in the dictionary.
 * After populating the frequency map, check that all required characters exist. If any are missing, return 0 immediately.
 * Since the word "balloon" uses 'l' and 'o' twice, divide their frequencies by 2 (using integer division).
 * Take the minimum value among the adjusted counts for 'b', 'a', 'l' // 2, 'o' // 2, and 'n'. This minimum represents how many full "balloon" words we can construct.
 * Return that minimum count.
 * Example Walkthrough
 * Input: "balonbalonbal"
 * Frequencies:
 *
 * 'b' → 2
 * 'a' → 2
 * 'l' → 2
 * 'o' → 2
 * 'n' → 2
 * Adjusted frequencies:
 *
 * 'l' → 2 // 2 = 1
 * 'o' → 2 // 2 = 1
 * Minimum of [2, 2, 1, 1, 2] = 1 → return 1.
 * Time and Space Complexity
 * Time Complexity: O(n), where n is the length of the input string. We scan the string once to count character frequencies.
 * Space Complexity: O(1), because the frequency dictionary has a fixed size, limited to at most 26 lowercase English letters.
 *
 * Edge Cases to Consider
 * If text is empty → return 0
 * If text lacks even one of the required characters → return 0
 * Extra characters in text that aren't part of "balloon" → ignore them
 * Conclusion
 * The “Maximum Number of Balloons” problem is an elegant example of frequency-based character matching. It teaches how to count occurrences and apply minimum constraints across multiple requirements. This is a valuable pattern in a wide range of string construction and inventory-based problems.
 * */
