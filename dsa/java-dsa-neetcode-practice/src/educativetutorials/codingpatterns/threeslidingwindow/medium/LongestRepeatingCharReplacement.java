package educativetutorials.codingpatterns.threeslidingwindow.medium;

import java.util.Arrays;
import java.util.List;

public class LongestRepeatingCharReplacement {

    private static int longestRepeatingCharacterReplacement(String s, int k) {
        
    }


    public static void main(String[] args) {
        List<String> inputStrings = Arrays.asList("AABCCBB", "ABBCB", "ABCCDE", "ABBCAB", "BBBBBBBBB");
        List<Integer> k = Arrays.asList(2, 1, 1, 2, 4);

        for (int i = 0; i < inputStrings.size(); ++i) {
            System.out.println((i + 1) + ".\tInput String: '" + inputStrings.get(i) + "'");
            System.out.println("\tk: " + k.get(i));
            System.out.println("\tLength of the longest substring with repeating characters: "
                    + longestRepeatingCharacterReplacement(inputStrings.get(i), k.get(i)));
            System.out.println(new String(new char[100]).replace("\0", "-"));
        }
    }
}
