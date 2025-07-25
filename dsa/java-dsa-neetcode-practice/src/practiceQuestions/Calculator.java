package practiceQuestions;

@FunctionalInterface
public interface Calculator {

    int calculate(int a, int b);

    // Default method: Can be called on an instance
    default int square(int a) {
        return a * a;
    }

    // Static method: Can be called on the interface itself
    static boolean isPositive(int a) {
        return a > 0;
    }

}
