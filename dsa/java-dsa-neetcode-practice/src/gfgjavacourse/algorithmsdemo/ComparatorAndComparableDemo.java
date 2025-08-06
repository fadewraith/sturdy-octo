package gfgjavacourse.algorithmsdemo;

public class ComparatorAndComparableDemo {

    /**
     * public interface Comparable<T> {
     *     public int compareTo(T o);
     * }
     * ex - class Student implements Comparable<Student> {
     *     public int compareTo(Student s) {
     *         return (this.rollNo - s.rollNo);
     *     }
     * }
     * those implements the above interface
     * Wrapper classes, String, date, time, etc
     * returns -
     * -ve -> when current obj is smaller
     * 0 -> both obj are same
     * +ve -> current obj is greater
     * */

    /**
     * Comparator Interface -
     * - useful when there is no natural order or when needed to compare in diff order
     * - a parameter in many standard libraries
     * sort(), binarySearch(), min(), max(), TreeSet(), TreeMap(), priorityQueue(), etc.
     * stream functions like sorter(), max(), etc.
     * its a functional interface
     * returns -
     * -ve -> when t1 is smaller
     * 0 -> when t1 and t2 are same
     * +ve -> when t1 is greater
     *
     * public interface Comparator<T> {
     *     public int compare(T t1, T t2);
     * }
     * */

    /**
     * Comparator methods and examples -
     * int compare(t1, t2)
     * Comparator comparing(KeyExtractor)
     * Comparator thenComparing(KeyExtractor)
     *  " naturalOrder()
     *  " reversed()
     *  " reverseOrder()
     *  " nullFirst(Comparator)
     *  " nullLast(Comparator)
     *  ... etc
     * */
}
