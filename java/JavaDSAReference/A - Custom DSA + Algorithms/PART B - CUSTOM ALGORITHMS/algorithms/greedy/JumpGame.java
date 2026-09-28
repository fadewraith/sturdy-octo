package algorithms.greedy;

/**
 * JUMP GAME (Greedy Variant)
 * 
 * WHAT IT IS:
 * You are given an integer array where each element represents your maximum jump 
 * length at that position. Determine if you can reach the last index.
 * 
 * WHEN TO USE THIS:
 * - This problem CAN be solved with O(N^2) Dynamic Programming, but the Greedy 
 *   approach collapses it to an elegant O(N) linear scan!
 * 
 * STRATEGY:
 * We maintain a variable `furthestReachable`.
 * As we iterate through the array, if our current index `i` is greater than 
 * `furthestReachable`, it means we are stuck in a "hole" and can never reach this 
 * spot! Return false.
 * Otherwise, we update `furthestReachable` to be the max of itself or `i + nums[i]`.
 * If `furthestReachable` exceeds the last index, we win!
 * 
 * COMPLEXITY:
 * Time: O(N)
 * Space: O(1)
 */
public class JumpGame {

    public static boolean canJump(int[] nums) {
        int furthestReachable = 0;
        
        for (int i = 0; i < nums.length; i++) {
            // If our current index is beyond the furthest point we could ever reach, we failed.
            if (i > furthestReachable) {
                return false;
            }
            
            // Greedily update the furthest reachable point
            furthestReachable = Math.max(furthestReachable, i + nums[i]);
            
            // If we can reach or surpass the end, we win!
            if (furthestReachable >= nums.length - 1) {
                return true;
            }
        }
        
        return true;
    }

    public static void main(String[] args) {
        System.out.println("--- JUMP GAME (GREEDY) DEMO ---");
        
        int[] game1 = {2, 3, 1, 1, 4};
        System.out.println("Can finish Game 1? " + canJump(game1)); // Expected: true
        
        int[] game2 = {3, 2, 1, 0, 4};
        System.out.println("Can finish Game 2? " + canJump(game2)); // Expected: false (Stuck at the 0)
    }
}
