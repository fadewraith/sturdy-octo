package offthecollege.l2;

public class MaxAvgSubarrayILC643 {

    /**
     * Time → O(n·k)
     *
     * Space → O(1)
     * */

    private static double bruteForce(int[] nums, int k) {
        int n = nums.length;
        double maxAvg = Double.NEGATIVE_INFINITY;

        for (int i = 0; i <= n - k; i++) {
            int sum = 0;

            for (int j = i; j < i + k; j++) {
                sum += nums[j];
            }

            maxAvg = Math.max(maxAvg, (double) sum / k);
        }

        return maxAvg;
    }

    /**
     * Sliding Window (Optimal) — O(n)
     * */

    private static double optimal(int[] nums, int k) {
        int n = nums.length;
        int sum = 0;

        for(int i = 0; i < k; i++) {
            sum += nums[i];
        }

        int maxSum = sum;

        for(int i = k; i < n; i++) {
            sum += nums[i] - nums[i - k];
            maxSum = Math.max(maxSum, sum);
        }

        return (double) maxSum / k;
    }

    public static void main(String[] args) {
        System.out.println(bruteForce(new int[]{1,2,3,4,5,6,7,8,9,10}, 2));
        System.out.println(bruteForce(new int[] {1,12,-5,-6,50,3}, 4));
        System.out.println(bruteForce(new int[] {5}, 1));
        System.out.println("====================================================");
        System.out.println(optimal(new int[]{1,2,3,4,5,6,7,8,9,10}, 2));
        System.out.println(optimal(new int[] {1,12,-5,-6,50,3}, 4));
        System.out.println(optimal(new int[] {5}, 1));
    }



}
