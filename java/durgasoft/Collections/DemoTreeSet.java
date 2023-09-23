import java.util.*;

public class DemoTreeSet {

	public static void main(String[] args) {
		TreeSet t = new TreeSet();
//		t.add(null); // here also we will get error
		t.add("A");
		t.add("a");
		t.add("B");
		t.add("Z");
		t.add("L");
//		t.add(new Integer(10)); // catched cast exception
//		t.add(null); // null pointer exception
		
		System.out.println(t);
	}

}
