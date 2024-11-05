package supplierinterfacedemo;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SupplierInterfaceDemo {
    public static void main(String[] args) {

//        //        ex 1
//        Supplier<Integer> s = () -> 1;
//        System.out.println(s.get());


//        ex 2
        Predicate<Integer> p = x -> x % 2 == 0;
        Function<Integer, Integer> f = x -> x * x;
        Consumer<Integer> c = System.out::println;
        Supplier<Integer> s = () -> 100;

        if(p.test(s.get())) {
            c.accept(f.apply(s.get()));
        }

    }
}
