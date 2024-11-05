package consumerinterfacedemo;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerInterfaceDemo {

    public static void main(String[] args) {
//        ex 1
        Consumer<String> consumer = s -> System.out.println(s);
//        consumer.accept("John");
//        ex 2
        Consumer<List<Integer>> listConsumer = x -> {
            for(Integer i: x) {
                System.out.println(i + 100);
            }
        };
//        listConsumer.accept(Arrays.asList(1, 2, 3, 4, 5));
//        ex 3
        Consumer<List<Integer>> listConsumer1 = x -> {
            for(Integer i : x) {
                System.out.println(i);
            }
        };
        listConsumer1.andThen(listConsumer).accept(Arrays.asList(1, 2, 3, 4));
    }
}
