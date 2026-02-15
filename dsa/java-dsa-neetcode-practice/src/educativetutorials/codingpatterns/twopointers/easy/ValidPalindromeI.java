package educativetutorials.codingpatterns.twopointers.easy;

public class ValidPalindromeI {

    /**
     * A naive approach to checking if a string is a palindrome would involve first removing all non-alphanumeric characters and converting the string to lowercase for case-insensitive comparison. This can be done by iterating the string once and appending only letters and digits to a new cleaned string. Once the cleaned version of the string is obtained, we can reverse it and compare it to the original cleaned string. If both versions are identical, the string is a palindrome; otherwise, it is not.
     *
     * While simple to implement, this method requires extra space to store the cleaned string and its reversed copy, leading to a space complexity of O(n).
     * This extra space usage and unnecessary reversal make the approach less efficient than the two pointer method.
     * */

    private static boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;

        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }

            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }

            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }

    public static void main(String[] args) {
        String[] testCases = {
                "A man, a plan, a canal: Panama",
                "race a car",
                "1A@2!3 23!2@a1",
                "No 'x' in Nixon",
                "12321"
        };

        for (String test : testCases) {
            System.out.println("\tString: " + test);
            boolean result = isPalindrome(test);
            System.out.println("\n\tResult: " + result);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}
