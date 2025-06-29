package arraysandhashing.ez;

public class NumberOfSeniorCitizens {

    public static int countSeniors(String[] details) {
        int k = 0;
        for(int i = 0; i < details.length; i++) {
            String detail = details[i];
            for(int j = 0; j < detail.length(); j++) {
                char c = detail.charAt(j);
                if(isAlphabet(c)) {
                    String[] split = detail.split(String.valueOf(c));
                    Integer value = Integer.valueOf(split[1].substring(0, 2));
                    if(value > 60) {
                        k += 1;
                    }
                }
            }
        }
        return k;
    }

    public static boolean isAlphabet(char ch) {
        return (ch >= 'A' && ch <= 'Z');
    }

    public static int countSeniors1(String[] details) {
        int count = 0;
        for (String s : details) {
            for (int i = 0; i < s.length(); i++) {
                if (Character.isLetter(s.charAt(i)) && i + 2 < s.length()) {
                    int age = (s.charAt(i + 1) - '0') * 10 + (s.charAt(i + 2) - '0');
                    if (age > 60) count++;
                    break;
                }
            }
        }
        return count;
    }

    public static int countSeniors2(String[] details) {
        // O(n), O(1)
        int res = 0;
        for (String d : details) {
            if (Integer.parseInt(d.substring(11, 13)) > 60) {
                res++;
            }
        }
        return res;
    }

    public static int countSeniors3(String[] details) {
        int res = 0;
        for (String d : details) {
            int ten = d.charAt(11) - '0';
            int one = d.charAt(12) - '0';
            int age = one + 10 * ten;
            if (age > 60) {
                res++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        String[] details = {"7868190130M7522","5303914400F9211","9273338290F4010"};
//        System.out.println(countSeniors(details));
        System.out.println(countSeniors3(details));
    }
}
