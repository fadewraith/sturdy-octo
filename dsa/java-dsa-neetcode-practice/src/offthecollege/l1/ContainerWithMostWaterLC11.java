package offthecollege.l1;

public class ContainerWithMostWaterLC11 {

    private int bruteForce(int[] height) {
        int maxWater = 0;

        for(int i = 0; i < height.length; i++) {
            for(int j = i + 1; j < height.length; j++) {
                int width = j - i;
                int curHeight = Math.min(height[i], height[j]);
                int area = width * curHeight;

                maxWater = Math.max(maxWater, area);
            }
        }

        return maxWater;
    }

    private static int optimal(int[] height) {
        int i = 0, j = height.length - 1, maxWater = 0;

        while(i < j) {
            int breadth = j - i;
            int length = Math.min(height[i], height[j]);
            int area = length * breadth;

            maxWater = Math.max(maxWater, area);

            if(height[i] < height[j]) {
                i++;
            } else {
                j--;
            }

        }

        return maxWater;
    }

    public static void main(String[] args) {
        System.out.println(optimal(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
    }
}
