package arraysandhashing.ez;

public class LengthOfLastWord {

    public static int iterationOne(String s) {
        int length = 0, i = 0;
        while(i < s.length()) {
            if(s.charAt(i) == ' ') {
                while(i < s.length() && s.charAt(i) == ' ') i++;
                if(i == s.length()) return length;
                length = 0;
            } else {
                length++;
                i++;
            }
        }
        return length;
    }

    public static int iterationTwo(String s) {
        int n = s.length();
        int i = n - 1, length = 0;
        while(s.charAt(i) == ' ') i--;
        while(i >= 0 && s.charAt(i) != ' ') {
            i--;
            length++;
        }
        return length;
    }

    public static int builtInFunction(String s) {
        return s.trim().length() - s.lastIndexOf(" ") - 1;
    }

    public static void main(String[] args) {
        String s = "Hello World";
        System.out.println(iterationTwo(s));
    }
}
