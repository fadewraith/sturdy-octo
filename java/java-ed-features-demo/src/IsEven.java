public class IsEven {

    private IsEven() {

    }

    public static boolean isEven(int num) {
        System.out.println(num & 1);
        return (num & 1) == 0;
    }

    public static void main(String[] args) {
        System.out.println("isEven -> " + isEven(6));
    }
}
