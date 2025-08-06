package gfgjavacourse.listdemo;

import java.util.Iterator;
import java.util.LinkedList;

public class JosephusProblemUsingLinkedList {
    // n = no of people, k = remove each kth person from the list
    private static int solution(int n, int k) {
        LinkedList<Integer> list = new LinkedList<>();

        for(int i = 0; i < n; i++) {
            list.add(i);
        }

        Iterator<Integer> it = list.iterator();

        while(list.size() > 1) {
            int count = 0;
            while(count < k) {
                while(it.hasNext() && count < k) {
                    it.next();
                    count++;
                }
                if(count < k) {
                    it = list.iterator();
                    it.next();
                    count++;
                }
            }
            it.remove();
        }
        return list.getFirst();
    }

    public static void main(String[] args) {
        // Test case 1: Basic test case with n=5, k=2
        int n1 = 5, k1 = 2;
        System.out.println("Test Case 1: n=" + n1 + ", k=" + k1);
        System.out.println("Expected Output: 2");
        System.out.println("Actual Output: " + solution(n1, k1));
        System.out.println();

        // Test case 2: n=7, k=3 (classic Josephus problem)
        int n2 = 7, k2 = 3;
        System.out.println("Test Case 2: n=" + n2 + ", k=" + k2);
        System.out.println("Expected Output: 3");
        System.out.println("Actual Output: " + solution(n2, k2));
        System.out.println();

        // Test case 3: n=1, k=1 (edge case - single person)
        int n3 = 1, k3 = 1;
        System.out.println("Test Case 3: n=" + n3 + ", k=" + k3);
        System.out.println("Expected Output: 0"); // Since we're using 0-based indexing
        System.out.println("Actual Output: " + solution(n3, k3));
        System.out.println();

        // Test case 4: n=14, k=2
        int n4 = 14, k4 = 2;
        System.out.println("Test Case 4: n=" + n4 + ", k=" + k4);
        System.out.println("Expected Output: 12");
        System.out.println("Actual Output: " + solution(n4, k4));
    }
}
