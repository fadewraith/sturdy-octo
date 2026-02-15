package offthecollege.l1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestSubstringLC3 {

    private static int bruteForce(String s) {
        int n = s.length();
        int maxLen = 0;

        for(int i = 0; i < n; i++) {
            Set<Character> set = new HashSet<>();
            for(int j = i; j < n; j++) {
                if(set.contains(s.charAt(j))) {
                    break;
                }
                set.add(s.charAt(j));
                maxLen = Math.max(maxLen, j - i + 1);
            }
        }

        return maxLen;
    }


    private static int twoPointers(String s) {
        int left = 0, maxLen = 0;
        Set<Character> set = new HashSet<>();

        for (int right = 0; right < s.length(); right++) {

            while (set.contains(s.charAt(right))) {
                set.remove(s.charAt(left));
                left++;
            }

            set.add(s.charAt(right));
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }


    private static int optimal(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int left = 0, maxLen = 0;

        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if(map.containsKey(c)) {
                left = Math.max(left, map.get(c) + 1);
            }

            map.put(c, i);
            maxLen = Math.max(maxLen, i - left + 1);
        }

        return maxLen;
    }

    public static void main(String[] args) {
        String s = "abcabcbb";
        String s1 = "bbbbb";
        String s2 = "pwwkew";
        System.out.println(bruteForce(s));
        System.out.println(bruteForce(s1));
        System.out.println(bruteForce(s2));
        System.out.println();
        System.out.println(twoPointers(s));
        System.out.println(twoPointers(s1));
        System.out.println(twoPointers(s2));
    }
}
