package arraysandhashing.ez;

public class ReplaceElementsWithRightMax {

    public static int[] replaceElements(int[] arr) {
        int[] ans = new int[arr.length];
        for(int i = 0; i < arr.length; i++) {
            int rightMax = -1;
            for(int j = i + 1; j < arr.length; j++) rightMax = Math.max(rightMax, arr[j]);
            ans[i] = rightMax;
        }
        return ans;
    }

    public static int[] replaceElements1(int[] arr) {
        int[] ans = new int[arr.length];
        int rightMax = -1;
        for(int i = arr.length - 1; i >= 0; i--) {
            ans[i] = rightMax;
            rightMax = Math.max(rightMax, arr[i]);
        }
        return ans;
    }

}
