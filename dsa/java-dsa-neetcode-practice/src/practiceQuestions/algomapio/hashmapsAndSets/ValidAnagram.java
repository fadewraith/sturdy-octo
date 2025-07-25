package practiceQuestions.algomapio.hashmapsAndSets;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ValidAnagram {

    private static boolean isAnagram(String s, String t) {
        if(s.length() != t.length()) return false;

        Map<Character, Integer> sMap = new HashMap<>();
        Map<Character, Integer> tMap = new HashMap<>();

        for(char c: s.toCharArray()) {
            sMap.put(c, sMap.getOrDefault(c, 0) + 1);
        }

        for(char c: t.toCharArray()) {
            tMap.put(c, tMap.getOrDefault(c, 0) + 1);
        }

        return sMap.equals(tMap);
    }
}



/**
 * Time and Space Complexity
 * Using Counters:
 * Time Complexity: O(n), where n is the length of the strings.
 * Space Complexity: O(1), because we use at most 26 letters (if constrained to lowercase English).
 *
 * Using Sorting:
 * Time Complexity: O(n log n)
 * Space Complexity: O(n) due to the creation of sorted copies.
 * */