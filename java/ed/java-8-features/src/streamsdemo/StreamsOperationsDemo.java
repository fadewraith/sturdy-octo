package streamsdemo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamsOperationsDemo {

    public static void main(String[] args) {
//        ex1
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 4322, 9, 0, 5, 44, 23, 433);
//        Stream<Integer> integerStream = list.stream().filter(x -> x % 2 == 0);
//        List<Integer> collect = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
//        ex2
        List<Integer> collect = list.stream().filter(x -> x % 2 == 0).toList();
//        ex3
        List<Integer> list1 = collect.stream().map(x -> x / 2).toList();
//        ex4/5/6/7
//        sort in asc order
        List<Integer> filteredMappedList = list.stream().filter(x -> x % 2 == 0).map(x -> x / 2).distinct().sorted().toList();
        //        sort in asc order
        List<Integer> filteredMappedListSortedDesc = list.stream()
                .filter(x -> x % 2 == 0)
                .map(x -> x / 2)
                .distinct()
                .sorted((a, b) -> b - a)
                .toList();

//        ex8
        List<Integer> filteredMappedListSortedDescLimit = list.stream()
                .filter(x -> x % 2 == 0)
                .map(x -> x / 2)
                .distinct()
                .sorted((a, b) -> b - a)
                .limit(3)
                .toList();

//        ex9
        List<Integer> filteredMappedListSortedDescLimitSkip = list.stream()
                .filter(x -> x % 2 == 0)
                .map(x -> x / 2)
                .distinct()
                .sorted((a, b) -> b - a)
                .limit(3)
                .skip(1)
                .toList();

//        ex10
        List<Integer> list2 = Stream.iterate(0, x -> x + 1)
                .limit(31)
                .skip(1)
                .filter(x -> x % 2 == 0)
                .map(x -> x / 10)
                .distinct()
                .sorted()
//                .peek(x -> System.out.println(x)) // peek can be used anywhere
                .toList();

//        ex11
        Integer integer = Stream.iterate(0, x -> x + 1)
                .limit(101)
                .map(x -> x / 20)
                .distinct()
//                .peek(System.out::println)
                .max((a, b) -> a - b) // .max((a, b) -> b - a)
                .get();

//        ex12
        long count = Stream.iterate(0, x -> x + 1)
                .limit(101)
                .map(x -> x / 20)
                .distinct()
                .count();
//        ex13
        List<Integer> list3 = Arrays.asList(1, 2, 3, 4, 5);
        list3.parallelStream();

        System.out.println(count);
    }
}
