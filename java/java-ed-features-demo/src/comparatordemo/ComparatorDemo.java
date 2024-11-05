package comparatordemo;

import threaddemo.MyClass;

import java.util.*;

public class ComparatorDemo {

    public static void main(String[] args) {
//        listDemo();
//        setDemo();
//        mapDemo();

        StudentComparatorDemo s1 = new StudentComparatorDemo(2, "John");
        StudentComparatorDemo s2 = new StudentComparatorDemo(3, "Doe");
        StudentComparatorDemo s3 = new StudentComparatorDemo(33, "Demo");
        List<StudentComparatorDemo> list = new ArrayList<>();
        list.add(s1);
        list.add(s2);
        list.add(s3);
//        Collections.sort(list, (a, b) -> b.id - a.id);
//        Collections.sort(list, Comparator.comparing(student -> student.name));
//        Collections.sort(list, Comparator.comparing(student -> student.name).reversed());
//        Collections.sort(list, (a, b) -> b.name.compareTo(a.name));
        System.out.println(list);

    }

    public static void mapDemo() {
        Map<Integer, String> m = new TreeMap<>();
        m.put(2, "Z");
        m.put(3, "f");
        m.put(1, "y");
        System.out.println("before manual sorting: " + m);
        Map<Integer, String> mm = new TreeMap<>((a, b) -> b - a);
        mm.put(2, "Z");
        mm.put(3, "f");
        mm.put(1, "y");
        System.out.println("after manual sorting: " + mm);
    }

    public static void setDemo() {
        //        set is interface, TreeSet for order, HashSet no orders
        Set<Integer> s = new TreeSet<>();
        s.add(22);
        s.add(1);
        s.add(13);
        System.out.println("before manual sorting: " + s);
        Set<Integer> ss = new TreeSet<>((a, b) -> b - a);
        ss.add(22);
        ss.add(1);
        ss.add(13);
        System.out.println("after manual sorting: " + ss);
    }

    public static void listDemo() {
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(4);
        list.add(6);
        list.add(99);
        list.add(0);
//        Collections.sort(list);
//        Collections.sort(list, new CustomComparator());
//        Collections.sort(list, (a, b) -> b - a);
        list.sort((a, b) -> b - a);
        System.out.println(list);
    }



}
