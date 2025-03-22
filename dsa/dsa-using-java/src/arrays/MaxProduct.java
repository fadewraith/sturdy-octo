package arrays;

public class MaxProduct {

    public static void main(String[] args) {
        int[] intArray = { 10, 60, 30, 40, 50 };
        String pairs = maxProduct(intArray);
        System.out.println(pairs);
    }

    public static String maxProduct(int[] intArray) {
        int maxProduct = 0;
        String pairs = "";
        for (int i = 0; i < intArray.length; i++) {
            for (int j = i + 1; j < intArray.length; j++) {
                if (intArray[i] * intArray[j] > maxProduct) {
                    maxProduct = intArray[i] * intArray[j];
                    pairs = intArray[i] + ", " + intArray[j];
                }
            }
        }
        return pairs;
    }
}
