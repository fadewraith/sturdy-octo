package offthecollege.l1;

public class RemoveDuplicatesLC26 {

    private static int solution(int[] nums) {
        int j = 0, n = nums.length;

        for(int i = 1; i < n; i++) {
            if(nums[i] != nums[j]) {
                j++;
                nums[j] = nums[i];
            }
        }

        return j + 1;
    }

    public static void main(String[] args) {

        int[] nums = {1, 1, 2, 2, 3, 4, 4};

        int k = solution(nums);

        System.out.println("Unique count: " + k);
        System.out.print("Modified array: { ");
        for (int i = 0; i < k; i++) {
            System.out.print(nums[i]);
            if(i < k - 1) System.out.print(", ");
        }
        System.out.println(" }");

    }
}
