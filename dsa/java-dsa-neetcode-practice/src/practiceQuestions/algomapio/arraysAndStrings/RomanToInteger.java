package practiceQuestions.algomapio.arraysAndStrings;

import java.util.HashMap;
import java.util.Map;

public class RomanToInteger {

    public static int solution(String s) {
        Map<Character, Integer> dict = new HashMap<>();
        dict.put('I', 1);
        dict.put('V', 5);
        dict.put('X', 10);
        dict.put('L', 50);
        dict.put('C', 100);
        dict.put('D', 500);
        dict.put('M', 1000);

        int sum = 0;
        int n = s.length();
        int i = 0;
        while(i < n) {
            if(i < n - 1 && dict.get(s.charAt(i)) < dict.get(s.charAt(i + 1))) {
                sum += dict.get(s.charAt(i + 1)) - dict.get(s.charAt(i));
                i += 2;
            } else {
                sum += dict.get(s.charAt(i));
                i++;
            }
        }

        return sum;
    }

    public static void main(String[] args) {
        String s = "MCMXCIV";
        System.out.println(solution(s));
    }
}


/**
 * Step-by-Step Thought Process
 * Understand the problem: Convert a Roman numeral string s to its integer value.
 * Create a dictionary d mapping Roman numerals to their values: I=1, V=5, X=10, L=50, C=100, D=500, M=1000.
 * Initialize a variable summ to 0 to store the total value and an index i to 0.
 * While i is less than the length n of s:
 * If i < n-1 and the value of s[i] is less than s[i+1], subtract s[i]’s value from s[i+1]’s value, add the result to summ, and increment i by 2.
 * Otherwise, add s[i]’s value to summ and increment i by 1.
 * Return summ as the integer value.
 *
 *
 * */