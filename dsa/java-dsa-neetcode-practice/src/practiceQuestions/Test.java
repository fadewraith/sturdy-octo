package practiceQuestions;

import java.util.HashMap;
import java.util.Map;

public class Test {

    private static void solution(int[] nums) {
        int[] count = new int[3];
        int j = 0;
        for(int color: nums) {
            count[color]++;
        }

        int red = count[0], white = count[1], blue = count[2];
        for(int i = 0; i < red; i++) {
            nums[j++] = 0;
        }

        for(int i = red; i < red + white; i++) {
            nums[i] = 1;
        }

        for(int i = red + white; i < nums.length; i++) {
            nums[i] = 2;
        }

    }


    public static void main(String[] args) {

        outerLoop: // label for the outer loop
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i * j > 6) {
                    break outerLoop; // breaks out of the outer loop
                }
//                System.out.println("i = " + i + ", j = " + j);
            }
        }
        System.out.println("Loop complete.");

        String s1 = new String("hello");
        // 1 object -> new -> heap
        // 2 object -> literal -> SCP (String constant pool area)

        String s2 = "hello"; // 1 object
        // total object count 2 (statement1 + statement2)

        // using intern to get the object from scp
        // intern method used to get reference from scp
        System.out.println(s1.intern());
        System.out.println(s2.intern());
        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());
        System.out.println(s1.intern().hashCode());
        System.out.println(s2.intern().hashCode());
        System.out.println(s1.intern().hashCode() == s2.hashCode());






    }
}