package neetcodepracticesets.arraysandhashing.ez;

public class MaxConsecutiveOnes {

    private static int solution(int[] nums) {
        int max = 0, count = 0;
        for (int num : nums) {
            if (num == 1) {
                max = Math.max(max, ++count);
            } else {
                count = 0;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[] a1 = {1, 1, 0, 1, 1, 1};
        int[] a2 = {1, 0, 1, 1, 0, 1};
        System.out.println("a1: " + solution(a1));
        System.out.println("a2: " + solution(a2));

    }
}
