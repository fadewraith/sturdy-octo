package genericsdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

//    In Java Generics, wildcards (?) are a special kind of type argument that can be used in method arguments or class definitions to represent an unknown type. They allow for more flexible and dynamic code by letting the type be specified later or be more loosely defined.
//
//Wildcards are useful when you don’t know the exact type at the time of defining a class or method, or when you want to allow a range of types rather than a single specific one.
    public void printArrayList(ArrayList<?> list) {
        for(Object o : list) {
            System.out.println(o);
        }
    }

//    public static int sum(ArrayList<?> numbers) {
    public static double sum(List<? extends Number> numbers) {
//        upper boundation
        double sum = 0;
        for(Number o: numbers) {
            sum += o.doubleValue();
        }

        return sum;
    }

    public static void printNumbers(List<? super Integer> list) {
//        lower boundation bcoz of super k/w
        for(Object o : list) {
            System.out.println(o);
        }
    }

    public static void main(String[] args) {

//        genericsdemo.Box<Integer> box = new genericsdemo.Box<>();
//        box.setValue(1);
//        int i = box.getValue();
//        System.out.println(i);

//        GenericClassPair<String, Integer> genericClassPair = new GenericClassPair<>("Age", 30);
//        System.out.println(genericClassPair.getKey());
//        System.out.println(genericClassPair.getValue());

//        int result1 = genericsdemo.ArithmeticOperation.ADD.calculate(10, 5);
//        int result2 = genericsdemo.ArithmeticOperation.SUBTRACT.calculate(10, 5);
//        System.out.println(result1);  // Output: 15
//        System.out.println(result2);  // Output: 5

        System.out.println(sum(Arrays.asList(
                1, 2, 3.6, 7
        )));

//        List<? extends Number> numbers = Arrays.asList(1, 2, 3);
//        numbers.add(20);

        List<? super Integer> numbers = Arrays.asList(1, 2, 3);
        numbers.add(20);


    }
}


//naming convention for generics type -
//T: Type
//E: Element (used in collections)
//K: Key (used in maps)
//V: Value (used in maps)
//N: Number