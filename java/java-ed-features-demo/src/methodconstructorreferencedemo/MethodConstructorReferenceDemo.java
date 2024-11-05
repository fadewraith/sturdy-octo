package methodconstructorreferencedemo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MethodConstructorReferenceDemo {

    public static void print(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
//        method references allow to refer a method without invoking it. they can be used in place of a lambda expression when it only calls an existing method
//        ex 1
        List<String> students = Arrays.asList("John", "Doe", "Hello");
//        if method is not static, instantiate it and then use it as a reference
        students.forEach(MethodConstructorReferenceDemo::print);

//        ex 2
//        List<Student> studentList = students.stream().map(x -> new Student(x)).collect(Collectors.toList());
        List<Student> studentList = students.stream().map(Student::new).collect(Collectors.toList());


    }
}
