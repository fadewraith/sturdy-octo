package offthecollege.l1;

public class RemoveElementLC27 {

    private static int solution(int[] nums, int val) {
        int n = nums.length;
        int count = 0;

        for(int i = 0; i < n; i++) {
            if(nums[i] != val) {
                nums[count] = nums[i];
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[] {3,2,2,3}, 3));
        System.out.println(solution(new int[] {0,1,2,2,3,0,4,2}, 2));
    }
}
