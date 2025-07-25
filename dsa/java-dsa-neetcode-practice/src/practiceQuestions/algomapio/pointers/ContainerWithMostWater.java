package practiceQuestions.algomapio.pointers;

public class ContainerWithMostWater {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Find two lines that form a container with the maximum water area, given an array of heights.
     * Initialize left pointer to 0 and right pointer to the array length minus 1.
     * Initialize max_area to 0 to track the maximum area found.
     * While left is less than right, calculate the width as right minus left.
     * Calculate the height as the minimum of height[left] and height[right].
     * Compute the area as width times height and update max_area if the computed area is larger.
     * If height[left] is less than height[right], increment left; otherwise, decrement right.
     * Return max_area after the loop.
     * */

    private static int solution(int[] height) {
        int n = height.length;
        int left = 0;
        int right = n - 1;
        int maxArea = 0;

        while(left < right) {
            int width = right - left;
            int ht = Math.min(height[left], height[right]);
            int area = width * ht;
            maxArea = Math.max(maxArea, area);

            if(height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return maxArea;
    }

}
