package unarybinaryinterfacedemo;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class UnaryBinaryInterfaceDemo {

    public static void main(String[] args) {
//        when in Function, i/p & o/p have same return type, then we use unary
//        ex 1
        UnaryOperator<Integer> unaryOperator = x -> x * x;
        System.out.println(unaryOperator.apply(10));
//        ex 2
        UnaryOperator<String> stringUnaryOperator = str -> str.toLowerCase();

//        ex 3
//        same is the case with BiFunction, we use Binary Operator
        BinaryOperator<String> binaryOperator = (s1, s2) -> s1 + s2;
        System.out.println(binaryOperator.apply("John", "Doe"));
    }
}
