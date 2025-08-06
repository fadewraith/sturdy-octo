package practiceQuestions.algomapio.hashmapsAndSets.easy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JewelsAndStones {

    private static int bruteForce(String jewels, String stones) {

        if(jewels.length() == 0 || stones.length() == 0) return 0;

        int count = 0;

        for(int i = 0; i < jewels.length(); i++) {
            for(int j = 0; j < stones.length(); j++) {
                if(jewels.charAt(i) == stones.charAt(j)) {
                    count += 1;
                }
            }
        }

        return count;
    }

    private static int optimal(String jewels, String stones) {

        if(jewels.length() == 0 || stones.length() == 0) return 0;

        int count = 0;

        Set<Character> set = new HashSet<>();
        for(char item: jewels.toCharArray()) set.add(item);

        for(char item: stones.toCharArray()) if(set.contains(item)) count++;

        return count;
    }

    public static void main(String[] args) {
        String jewels = "aA";
        String stones = "aAAbbbb";
        System.out.println(bruteForce(jewels, stones));
        System.out.println(optimal(jewels, stones));
    }
}
