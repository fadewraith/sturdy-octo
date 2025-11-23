package practiceQuestions;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Test {

    private static int[] solution(int[] arr, int d) {
        int n = arr.length;

        for(int i = 0; i < d; i++) {
            int first = arr[0];

            for(int j = 0; j < n - 1; j++) {
                arr[j] = arr[j + 1];
            }

            arr[n - 1] = first;
        }

        return arr;

    }

    public static void main(String[] args) {
        int[] arr = {4, 5, 2};



    }

}