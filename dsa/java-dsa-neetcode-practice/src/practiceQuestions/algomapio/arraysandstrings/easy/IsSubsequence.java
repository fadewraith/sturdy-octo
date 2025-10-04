package practiceQuestions.algomapio.arraysAndStrings.easy;

public class IsSubsequence {

    private static boolean solution(String s, String t) {
        if(s.length() == 0) return true;
        if(s.length() > t.length()) return false;
        int sp = 0, tp = 0;
        while(sp < s.length() && tp < t.length()) {
            if(s.charAt(sp) == t.charAt(tp)) {
                sp++;
            }
            tp++;
        }

        return sp == s.length();
    }
}
