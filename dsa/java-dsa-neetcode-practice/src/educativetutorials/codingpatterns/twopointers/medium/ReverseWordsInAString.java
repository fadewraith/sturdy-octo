package educativetutorials.codingpatterns.twopointers.medium;

import educativetutorials.codingpatterns.commonutilities.CommonUtils;

import java.util.Arrays;
import java.util.List;

public class ReverseWordsInAString {

    private static String reverseWords(String sentence) {

        String result = sentence.trim().replaceAll("\\s+", " ");
        String[] arr = result.split(" ");
        int i = 0, j = arr.length - 1;

        while(i <= j) {
//            String temp = arr[i];
//            arr[i++] = arr[j];
//            arr[j--] = temp;
            CommonUtils.genericSwap(arr, i++, j--);
        }

        result = String.join(" ", arr);

        return result;
    }

    public static void main(String[] args) {
        List<String> stringsToReverse = Arrays.asList(
                "Hello World",
                "a   string   with   multiple   spaces",
                "Case Sensitive Test 1234",
                "a 1 b 2 c 3 d 4 e 5",
                "     trailing spaces",
                "case test interesting an is this"
        );

        for (int i = 0; i < stringsToReverse.size(); i++) {
            System.out.println((i + 1) + ".\tOriginal string: '" + stringsToReverse.get(i) + "'");
            System.out.println("\tReversed string: '" + reverseWords(stringsToReverse.get(i)) + "'");
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}
