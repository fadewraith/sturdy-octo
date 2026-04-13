import java.util.*;

public class Main {

    private static int solution(String s, int k) {
        int stringLength = s.length();
        int lengthOfMaxSubstring = 0;
        int start = 0;
        Map<Character, Integer> charFreq = new HashMap<>();
        int mostFreqChar = 0;

        for(int i = 0; i < stringLength; i++) {
            char currentChar = s.charAt(i);

            charFreq.put(currentChar, charFreq.getOrDefault(currentChar, 0) + 1);

            mostFreqChar = Math.max(mostFreqChar, charFreq.get(currentChar));

            if((i - start + 1 - mostFreqChar) > k) {
                charFreq.put(s.charAt(start), charFreq.get(s.charAt(start)) - 1);
                start += 1;
            }

            lengthOfMaxSubstring = Math.max(lengthOfMaxSubstring, i - start + 1);
        }

        return lengthOfMaxSubstring;
    }

    public static void main(String[] args) {
        List<String> inputStrings = Arrays.asList("AABCCBB", "ABBCB", "ABCCDE", "ABBCAB", "BBBBBBBBB");
        List<Integer> k = Arrays.asList(2, 1, 1, 2, 4);

        for (int i = 0; i < inputStrings.size(); ++i) {
            System.out.println((i + 1) + ".\tInput String: '" + inputStrings.get(i) + "'");
            System.out.println("\tk: " + k.get(i));
            System.out.println("\tLength of the longest substring with repeating characters: " + solution(inputStrings.get(i), k.get(i)));
            System.out.println(new String(new char[100]).replace("\0", "-"));
        }
    }
}
