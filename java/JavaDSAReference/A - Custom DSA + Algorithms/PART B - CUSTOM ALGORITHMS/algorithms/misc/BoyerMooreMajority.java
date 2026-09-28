package algorithms.misc;

/**
 * BOYER-MOORE MAJORITY VOTE ALGORITHM
 * 
 * WHAT IT IS:
 * Finds the "Majority Element" in an array (an element that appears strictly MORE 
 * than floor(N/2) times). 
 * 
 * IMPORTANT NAME COLLISION:
 * Do not confuse this with the "Boyer-Moore String Search" algorithm! (Part B, item 5). 
 * Same authors, completely different algorithm.
 * 
 * STRATEGY:
 * We maintain a `candidate` and a `count`.
 * - If `count` is 0, we set the current element as the `candidate` and set count to 1.
 * - If the current element == `candidate`, we increment `count`.
 * - If the current element != `candidate`, we decrement `count`.
 * 
 * The logic works because if an element appears more than N/2 times, it will 
 * inevitably outlast all other elements combined in this voting war!
 * 
 * COMPLEXITY:
 * Time: O(N) single pass.
 * Space: O(1) space. (Brute force uses a HashMap which takes O(N) space).
 */
public class BoyerMooreMajority {

    public static Integer findMajority(int[] nums) {
        int candidate = -1;
        int count = 0;
        
        // 1. Finding the Candidate
        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            if (num == candidate) {
                count++;
            } else {
                count--;
            }
        }
        
        // 2. Verification (Crucial if a majority element isn't GUARANTEED to exist!)
        count = 0;
        for (int num : nums) {
            if (num == candidate) {
                count++;
            }
        }
        
        if (count > nums.length / 2) {
            return candidate;
        }
        return null; // No majority element exists
    }

    public static void main(String[] args) {
        System.out.println("--- BOYER-MOORE MAJORITY VOTE DEMO ---");
        int[] arr = {2, 2, 1, 1, 1, 2, 2};
        System.out.println("Majority element is: " + findMajority(arr)); // Expected: 2
    }
}
