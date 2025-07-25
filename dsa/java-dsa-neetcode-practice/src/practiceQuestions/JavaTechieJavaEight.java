package practiceQuestions;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;

public class JavaTechieJavaEight {

    public static void main(String[] args) {
        // Using a lambda expression to implement practiceQuestions.Calculator
        Calculator add = (a, b) -> a + b;
        Calculator multiply = (a, b) -> a * b;

        System.out.println("Sum: " + add.calculate(5, 3));         // Output: 8
        System.out.println("Product: " + multiply.calculate(5, 3)); // Output: 15


        int sum = add.calculate(10, 5); // Calls the abstract method
        int squared = add.square(4);    // Calls the default method
        boolean positive = Calculator.isPositive(sum); // Calls the static method

        System.out.println("Sum: " + sum);          // Output: 15
        System.out.println("Square: " + squared);   // Output: 16
        System.out.println("Is Positive: " + positive); // Output: true

        // wap using stream to find the frequency of each character in a given string
        String intput = "Basant";
        Map<String, Long> countMap = Arrays.stream(intput.split(""))
                .collect(
                        Collectors.groupingBy(
                                Function.identity(), counting()
                        )
                );

        System.out.println(countMap);

        // assume you have list of employee in various dept, wap to find the highest paid employee from each dept
        // employees.stream().collect(Collectors.groupingBy(Employee::getDept))

    }
}
