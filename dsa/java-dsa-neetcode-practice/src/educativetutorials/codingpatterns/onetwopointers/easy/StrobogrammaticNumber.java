package educativetutorials.codingpatterns.onetwopointers.easy;

import java.util.HashMap;
import java.util.Map;

public class StrobogrammaticNumber {

    private static boolean isStrobogrammatic(String s) {
        // Dictionary to map digits to their corresponding rotations
        Map<Character, Character> dict = new HashMap<>();
        dict.put('0', '0');
        dict.put('1', '1');
        dict.put('6', '9');
        dict.put('8', '8');
        dict.put('9', '6');

        // Initialize pointers for the two ends of the string
        int left = 0;
        int right = s.length() - 1;

        // Iterate while the left pointer is less than or equal to the right pointer
        while(left <= right) {
            // Check if the current digit is valid and matches its corresponding rotated value
            if(!dict.containsKey(s.charAt(left)) || dict.get(s.charAt(left)) != s.charAt(right)) {
                // Return false if the number is not strobogrammatic
                return false;
            }
            left++;
            right--;
        }
        // Return true if all digit pairs are valid
        return true;
    }

    public static void main(String[] args) {
        String[] nums = {
                "609",
                "88",
                "962",
                "101",
                "123"
        };

        int i = 0;
        for(String num : nums) {
            System.out.println((i + 1) + ".\tnum: " + num);
            System.out.println("\n\tIs strobogrammatic: " + (isStrobogrammatic(num) ? "true" : "false"));
            System.out.println(new String(new char[100]).replace("\0", "-"));
            i++;
        }
    }
}
