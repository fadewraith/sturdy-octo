package practiceQuestions;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Test {

   private static int[] solution(int[] colors) {
       return null;
   }


   private static void swap(int[] arr, int i, int j) {
       int temp = arr[i];
       arr[i] = arr[j];
       arr[j] = temp;
   }

    public static void main(String[] args) {
        String input = "   Java    is   very    fun   ";
        System.out.println(input);

        String result = input
                .trim()
                .replaceAll("\\s+", " ");

        System.out.println(result);
// Output: "Java is very fun"


    }

}