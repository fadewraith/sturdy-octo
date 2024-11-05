package bipredicateconsumerfunctiondemo;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class BiPredicateConsumerFunctionDemo {

    public static void main(String[] args) {
//        ex 1
        BiPredicate<Integer, Integer> biPredicate = (x, y) -> x % 2 == 0 && y % 2 == 0;
        System.out.println(biPredicate.test(2, 5));

//        ex 2
        BiPredicate<String, Integer> biPredicate1 = (str, x) -> str.length() == x;
        System.out.println(biPredicate1.test("Hello", 5));

//        ex 3
        BiFunction<String, String, Integer> biFunction = (x, y) -> x.length() + y.length();
        System.out.println(biFunction.apply("Hello", "World"));

//        ex 4
        BiConsumer<Integer, Integer> biConsumer = (x, y) -> {
            System.out.println(x + y);
        };

        biConsumer.accept(1, 2);

//        ex 5

    }
}
