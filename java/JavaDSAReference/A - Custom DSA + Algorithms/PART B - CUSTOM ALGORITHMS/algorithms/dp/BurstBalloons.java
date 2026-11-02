package algorithms.dp;

/**
 * BURST BALLOONS (Interval DP)
 *
 * WHAT IT IS:
 * Balloons in a row have numbers. Bursting balloon i earns left * nums[i] * right,
 * where left/right are its CURRENT neighbours (1 past either end). After a burst,
 * the neighbours close the gap. Find the maximum total coins.
 *   [3, 1, 5, 8] -> burst 1, 5, 3, 8 -> 3*1*5 + 3*5*8 + 1*3*8 + 1*8*1 = 167
 *
 * WHEN TO USE THIS:
 * The flagship example of INTERVAL DP where the trick is to think about the LAST
 * action instead of the first. Same pattern as Matrix Chain Multiplication,
 * optimal BST, and polygon triangulation.
 *
 * WHY "LAST" AND NOT "FIRST"?
 * If you choose which balloon to burst FIRST, its neighbours change and the two
 * sides become entangled. If you choose which balloon k bursts LAST inside an open
 * interval (i, j), then while k is alive it separates the two sides completely, and
 * when it finally bursts its neighbours are exactly the boundaries i and j.
 *
 * STRATEGY:
 * Pad the array: vals = [1, nums..., 1].
 * dp[i][j] = best coins from bursting every balloon strictly BETWEEN i and j.
 * dp[i][j] = max over k in (i, j) of dp[i][k] + vals[i]*vals[k]*vals[j] + dp[k][j]
 * Answer = dp[0][n+1]. Fill by increasing gap j - i.
 *
 * COMPLEXITY:
 * Time: O(N^3)
 * Space: O(N^2)
 */
public class BurstBalloons {

    public static long maxCoins(int[] nums) {
        int n = nums.length;
        long[] vals = new long[n + 2];
        vals[0] = 1;
        vals[n + 1] = 1;
        for (int i = 0; i < n; i++) vals[i + 1] = nums[i];

        long[][] dp = new long[n + 2][n + 2];
        for (int gap = 2; gap <= n + 1; gap++) {          // j - i
            for (int i = 0; i + gap <= n + 1; i++) {
                int j = i + gap;
                long best = 0;
                for (int k = i + 1; k < j; k++) {          // k bursts LAST in (i, j)
                    best = Math.max(best, dp[i][k] + vals[i] * vals[k] * vals[j] + dp[k][j]);
                }
                dp[i][j] = best;
            }
        }
        return dp[0][n + 1];
    }

    public static void main(String[] args) {
        System.out.println("--- BURST BALLOONS DEMO ---");
        System.out.println("[3, 1, 5, 8] -> " + maxCoins(new int[]{3, 1, 5, 8})); // 167
        System.out.println("[1, 5]       -> " + maxCoins(new int[]{1, 5}));       // 10
        System.out.println("[]           -> " + maxCoins(new int[]{}));           // 0
    }
}
