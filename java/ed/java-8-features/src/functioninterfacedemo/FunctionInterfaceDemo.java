package functioninterfacedemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class FunctionInterfaceDemo {

    public static void main(String[] args) {
//        predicate will check the condition, function will give the value
// //       ex 1
//        Function<String, Integer> getLength = String::length;
//        Function<String, Integer> getLength = x -> x.length();
//        System.out.println(getLength.apply("Hello World!"));
// //        ex 2
//        Function<String, String> f2 = x -> x.substring(0, 3);
//        Function<List<Student>, List<Student>> studentsWithPrefix = li -> {
//            List<Student> res = new ArrayList<>();
//            for(Student s : li) {
//                if(f2.apply(s.getName()).equalsIgnoreCase("vip")) {
//                    res.add(s);
//                }
//            }
//            return res;
//        };
//
//        Student s1 = new Student(1, "John");
//        Student s2 = new Student(2, "Doe");
//        Student s3 = new Student(1, "Vipendra");
//        List<Student> studentList = Arrays.asList(s1, s2, s3);
//        List<Student> filteredList = studentsWithPrefix.apply(studentList);
//        System.out.println(filteredList);

////        ex 3
//        Function<String, String> f1 = x -> x.toUpperCase();
//        Function<String, String> f2 = x -> x.substring(0, 3);
//
//        Function<String, String> stringStringFunction = f1.andThen(f2);
//        stringStringFunction.apply("Hello");
////        ex 4
//        String applied = f1.andThen(f2).apply("John Doe");
//        System.out.println(applied);

////        ex 5
//        Function<Integer, Integer> f1 = x -> 2 * x;
//        Function<Integer, Integer> f2 = x -> x * x * x;
//        System.out.println(f1.andThen(f2).apply(3));
//        System.out.println(f2.andThen(f1).apply(3));
//        System.out.println(f1.compose(f2).apply(3));

//        ex 6
        Function<String, String> identityFunction = Function.identity();
        System.out.println(identityFunction.apply("Hello"));
    }

    private static class Student {
        String name;
        int id;

        public Student(int id, String name) {
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

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", id=" + id +
                    '}';
        }
    }
}
