import java.util.*;

public class DemoTreeSet5 {

	public static void main(String[] args) {
//		TreeSet t = new TreeSet();
		TreeSet t = new TreeSet(new MyComparatorString());
		t.add("Raja");
		t.add("Ganga");
		t.add("Ram");
		t.add("Shubh");
		t.add("Ramanuj");
		System.out.println(t);

	}

}


class MyComparatorString implements Comparator {
	public int compare(Object obj1, Object obj2) {
		String s1 = (String)obj1;
//		String s1 = (String)obj1;
		String s2 = obj2.toString();
//		return -s1.compareTo(s2); // also descending order
		return s2.compareTo(s1); // descending order
	}
}

