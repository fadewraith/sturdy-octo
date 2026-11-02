package algorithms.dp;

/**
 * EGG DROP
 *
 * WHAT IT IS:
 * You have K identical eggs and a building with N floors. There is a critical floor
 * F (0 <= F <= N): an egg dropped above F breaks, at or below F it survives (and can
 * be reused). Find the minimum number of drops that GUARANTEES finding F in the
 * worst case.
 *   1 egg:     you must go floor by floor -> N drops.
 *   2 eggs, 100 floors -> 14 drops.
 *
 * WHEN TO USE THIS:
 * - The classic "worst-case strategy" DP (minimise the maximum).
 * - Shows how flipping the question ("how many floors can I handle with m drops?")
 *   turns a slow DP into a fast one.
 *
 * STRATEGY 1 - DIRECT (minDropsClassic), O(K * N^2):
 * drops(k, n) = 1 + min over floor x of max(
 *                   drops(k-1, x-1),   // egg breaks: search the x-1 floors below
 *                   drops(k,   n-x))   // egg survives: search the n-x floors above
 *
 * STRATEGY 2 - FLIP THE QUESTION (minDrops), O(K * answer) <= O(K * N):
 * floors(m, k) = the most floors you can fully solve with m drops and k eggs.
 * Drop once from the right floor:
 *   - it breaks   -> m-1 drops, k-1 eggs handle floors(m-1, k-1) floors below
 *   - it survives -> m-1 drops, k   eggs handle floors(m-1, k)   floors above
 *   floors(m, k) = floors(m-1, k-1) + 1 + floors(m-1, k)
 * The answer is the smallest m with floors(m, K) >= N.
 * Keep one array over k and update k from high to low so floors[k-1] is still the
 * (m-1) value.
 *
 * COMPLEXITY:
 * Classic:  Time O(K * N^2), Space O(K * N).
 * Flipped:  Time O(K * answer) (answer <= N), Space O(K).
 */
public class EggDrop {

    /** Fast version: smallest m with floors(m, eggs) >= floors. */
    public static int minDrops(int eggs, int floors) {
        if (eggs < 1) throw new IllegalArgumentException("need at least one egg");
        long[] reach = new long[eggs + 1]; // reach[k] = floors solvable with current m drops, k eggs
        int m = 0;
        while (reach[eggs] < floors) {
            m++;
            for (int k = eggs; k >= 1; k--) {
                reach[k] = reach[k - 1] + 1 + reach[k]; // right side is still the (m-1) value
            }
        }
        return m;
    }

    /** Direct DP over (eggs, floors) - slower, closest to the problem statement. */
    public static int minDropsClassic(int eggs, int floors) {
        if (eggs < 1) throw new IllegalArgumentException("need at least one egg");
        int[][] dp = new int[eggs + 1][floors + 1];
        for (int n = 1; n <= floors; n++) dp[1][n] = n;    // one egg: linear scan
        for (int k = 2; k <= eggs; k++) {
            for (int n = 1; n <= floors; n++) {
                int best = Integer.MAX_VALUE;
                for (int x = 1; x <= n; x++) {
                    int worst = 1 + Math.max(dp[k - 1][x - 1], dp[k][n - x]);
                    best = Math.min(best, worst);
                }
                dp[k][n] = best;
            }
        }
        return dp[eggs][floors];
    }

    public static void main(String[] args) {
        System.out.println("--- EGG DROP DEMO ---");
        System.out.println("1 egg,  10 floors:  " + minDrops(1, 10));    // 10
        System.out.println("2 eggs, 100 floors: " + minDrops(2, 100)
                + " (classic DP: " + minDropsClassic(2, 100) + ")");      // 14
        System.out.println("3 eggs, 14 floors:  " + minDrops(3, 14));    // 4
        System.out.println("2 eggs, 1,000,000,000 floors: " + minDrops(2, 1_000_000_000)); // 44721
    }
}
