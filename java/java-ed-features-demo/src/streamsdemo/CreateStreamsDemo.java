package streamsdemo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class CreateStreamsDemo {

    public static void main(String[] args) {
//        ex1
        int[] array = { 1, 2, 3, 4, 5 };
        int sum = Arrays.stream(array).filter(n -> n % 2 == 0).sum();
        System.out.println(sum);

//        ex2
        List<String> list = Arrays.asList("apple", "banana", "cherry");
        Stream<String> stream = list.stream();

        String[] stringArray = { "apple", "banana", "cherry" };
        Stream<String> stringStream = Arrays.stream(stringArray);

//        ex3
        Stream<Integer> intStream = Stream.of(1, 2, 3);

//        ex5
        Stream<Integer> limited = Stream.iterate(0, n -> n + 1).limit(100);

//        ex6
        Stream<String> limited1 = Stream.generate(() -> "hello").limit(5);
        Stream<Integer> limited2 = Stream.generate(() -> (int) (Math.random() * 100)).limit(5);
    }
}
