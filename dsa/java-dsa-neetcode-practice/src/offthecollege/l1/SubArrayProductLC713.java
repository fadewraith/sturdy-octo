package offthecollege.l1;

public class SubArrayProductLC713 {

    private static int bruteForce(int[] nums, int k) {
        int n = nums.length;
        int count = 0;

        for(int i = 0; i < n; i++) {
            int product = 1;
            for(int j = i; j < n; j++) {
                product *= nums[j];

                if(product < k) {
                    count++;
                } else {
                    break; // product will only increase further
                }
            }
        }

        return count;
    }

    private static int optimal(int[] nums, int k) {
        if(k <= 1) return 0;

        int left = 0, count = 0, product = 1;

        for(int right = 0; right < nums.length; right++) {
            product *= nums[right];

            // logic -> decrease the wind from left, if product becomes large
            while(product >= k) {
                product /= nums[left];
                left++;
            }

            /**
             * [10] -> count = 1
             * [5], [10, 5] -> count = 2
             * []
             * */
            count += (right - left + 1);
        }

        return count;
    }


    public static void main(String[] args) {
        System.out.println(bruteForce(new int[] {10,5,2,6}, 100));
        System.out.println(bruteForce(new int[] {1,2,3}, 0));
        System.out.println();
        System.out.println(optimal(new int[] {10,5,2,6}, 100));
        System.out.println(optimal(new int[] {1,2,3}, 0));
    }

}
