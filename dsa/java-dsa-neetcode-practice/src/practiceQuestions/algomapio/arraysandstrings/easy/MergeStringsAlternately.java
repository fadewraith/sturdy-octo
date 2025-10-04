package practiceQuestions.algomapio.arraysandstrings.easy;

public class MergeStringsAlternately {

    private static String solution(String s1, String s2) {
        StringBuilder s = new StringBuilder();
        int word = 1;
        int p1 = 0, p2 = 0;
        int lenOfS1 = s1.length(), lenOfS2 = s2.length();

        while(p1 < lenOfS1 && p2 < lenOfS2) {
            if(word == 1) {
                s.append(s1.charAt(p1++));
                word = 2;
            } else {
                s.append(s2.charAt(p2++));
                word = 1;
            }
        }

        while(p1 < lenOfS1) {
            s.append(s1.charAt(p1++));
        }

        while(p2 < lenOfS2) {
            s.append(s2.charAt(p2++));
        }

        return s.toString();
    }

    public static void main(String[] args) {
        String s1 = "abc";
        String s2 = "xyz";
        System.out.println(solution(s1, s2));
    }
}


/**
 * Step-by-Step Thought Process
 * Start with two input strings, for example: "abc" and "xyz".
 * Use a pointer for each string to keep track of where you are in the merging process.
 * Alternate characters between the two strings: take one from the first string, then one from the second.
 * Continue alternating until you reach the end of one or both strings.
 * If one string is longer than the other, append the remaining characters from that string to the result.
 * Return the final merged string.
 *
 *
 * The time complexity of this approach is O(max(m, n)), where m and n are the lengths of word1 and word2. This is because each character from both strings is processed once. The space complexity is also O(m + n) since the final merged string contains all characters from both inputs.
 * */