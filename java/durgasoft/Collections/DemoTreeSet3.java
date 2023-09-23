import java.util.*;

public class DemoTreeSet3 {

	public static void main(String[] args) {
		// values are not important
		System.out.println("A".compareTo("Z")); // negative
		System.out.println("Z".compareTo("B")); // positive
		System.out.println("A".compareTo("A")); // equals
//		System.out.println("A".compareTo(null)); // null pointer exception
//		if we are depending on default natural sorting order internally, JVM will call CompareTo() method
//		will inserting objects to the TreeSet. Hence the objects should be comparable.
		TreeSet t = new TreeSet();
		t.add("B");
		t.add("Z");
		t.add("A");
		System.out.println(t);
		/*
		 * 1. if we are not satisfied with default natural sorting order or if the default natural sorting order is not already available then we can define our own sutomized sorting by using comparator.
		 * 2. comparable meant for default natural sorting order where as comparator meant for sutomized sorting order.
		 * */
	}

}
