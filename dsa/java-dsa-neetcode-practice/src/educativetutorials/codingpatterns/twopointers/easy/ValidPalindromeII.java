package educativetutorials.codingpatterns.twopointers.easy;

public class ValidPalindromeII {

    /**
     * The algorithm consists of two functions:
     *
     * isSubstringPalindrome(string, left, right): This helper function checks if a substring of the input string, defined by left and right indexes, is a palindrome. It uses a two-pointer approach, comparing characters from both ends inward. If any mismatch is found, it returns FALSE; otherwise, it returns TRUE.
     *
     * isPalindrome(string): This function checks if the entire string is a palindrome or can become one by removing one character. It initializes two pointers, leftIndex at the start and rightIndex at end of the string:
     *
     * If the characters at the leftIndex and rightIndex are the same, it moves both pointers inward.
     *
     * If the characters differ, it checks two cases by calling isSubstringPalindrome:
     *
     * The substring from leftIndex + 1 to rightIndex
     *
     * The substring from leftIndex to rightIndex - 1
     *
     * If either case returns TRUE, the function returns TRUE; otherwise, it returns FALSE.
     * */

    private static boolean isSubstringPalindrome(String s, int left, int right) {
        while(left < right) {
            // If the characters at left and right indexes are not equal, the substring is not a palindrome
            if(s.charAt(left) != s.charAt(right)) return false;

            // Move the left index forward and right index backward
            left++;
            right--;
        }
        // If the substring is a palindrome, return true
        return true;
    }

    private static boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;

        while(left < right) {
            // If the characters at start and end indexes are not equal, the string is not a palindrome
            if(s.charAt(left) != s.charAt(right)) {
                // Check if string s excluding character at start index or end index is a
                // palindrome by calling isSubstringPalindrome function
                return (isSubstringPalindrome(s, left + 1, right) || (isSubstringPalindrome(s, left, right - 1)));
            }
            // Move the start index forward and the end index backward
            left++;
            right--;
        }

        // If the string is a palindrome, return True
        return true;

    }


    public static void main(String[] args) {
        String[] inputs = {"madame", "dead", "abca", "tebbem", "eeccccbebaeeabebccceea"};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println((i + 1) + ".\tString: " + inputs[i]);
            System.out.println("\n\tValid Palindrome: " + isPalindrome(inputs[i]));
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }


}
