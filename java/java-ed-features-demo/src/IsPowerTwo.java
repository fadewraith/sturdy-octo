public class IsPowerTwo {
    private IsPowerTwo() {
    }
    public static boolean isPowerTwo(int number) {
        if (number <= 0) {
            return false;
        }
        int ans = number & (number - 1);
        return ans == 0;
    }

    public static void main(String[] args) {
        System.out.println("isPowerTwo -> " + isPowerTwo(9));
    }
}