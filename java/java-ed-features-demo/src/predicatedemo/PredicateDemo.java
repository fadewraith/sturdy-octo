package predicatedemo;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class PredicateDemo {
//    predicate is a boolean valued function

    public static void main(String[] args) {
//        Predicate<Integer> salaryGreaterThanOneLac =x -> x > 1_00_000;
//        System.out.println(salaryGreaterThanOneLac.test(10_00_000));
//        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//        int sum = integerList.stream().filter(n -> n % 2 == 0).mapToInt(n -> n).sum();

//        Predicate<Integer> isEven = x -> x % 2 == 0;
//        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//        Predicate<String> startsWithLetterV = x -> x.toLowerCase().charAt(0) == 'h';
//        Predicate<String> endsWithLetterO = x -> x.toLowerCase().charAt(x.length() - 1) == 'o';
//        Predicate<String> and = startsWithLetterV.and(endsWithLetterO);
//        Predicate<String> or = startsWithLetterV.or(endsWithLetterO);
//        System.out.println(or.negate().test("hello"));

        Student s1 = new Student("John", 1);
        Student s2 = new Student("Doe", 2);
        Predicate<Student> studentPredicate = x -> x.getId() > 1;

        Predicate<Object> equal = Predicate.isEqual("John");
        System.out.println(equal.test("Johns"));


    }

    private static class Student {
        String name;
        int id;

        public Student(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }


}
