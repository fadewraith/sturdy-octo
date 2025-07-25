package practiceQuestions;

import java.util.concurrent.CompletableFuture;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class EvenOddPrinter {

    // using 2 thread

    private static Object object = new Object();

    private static IntPredicate even = e -> e % 2 == 0;
    private static IntPredicate odd = e -> e % 2 != 0;

    public static void main(String[] args) throws InterruptedException {

        CompletableFuture.runAsync(() -> EvenOddPrinter.printNumber(odd));
        CompletableFuture.runAsync(() -> EvenOddPrinter.printNumber(even));
        Thread.sleep(1000);
    }

    public static void printNumber(IntPredicate condition) {
        IntStream.rangeClosed(1, 10).filter(condition).forEach(EvenOddPrinter::execute);
    }

    public static void execute(int n) {
        synchronized(object) {
            try {
                System.out.println(Thread.currentThread().getName() + " " + n);
                object.notify();
                object.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
