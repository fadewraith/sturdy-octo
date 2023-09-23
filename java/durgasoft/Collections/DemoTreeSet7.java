import java.util.*;

public class DemoTreeSet7 {

	public static void main(String[] args) {
		// doing sorting where the sorting order is increasing length order
		// if same length, the sort in alphabetical order
//		TreeSet t = new TreeSet();
		TreeSet t = new TreeSet(new MyComparatorHeterogeneousObjects());
		t.add("A");
		t.add(new StringBuilder("ABC"));
		t.add(new StringBuilder("AA"));
		t.add("XX");
		t.add("ABCD");
		t.add("A");
		System.out.println(t);

	}

}


class MyComparatorHeterogeneousObjects implements Comparator {
	public int compare(Object obj1, Object obj2) {
		String s1 = obj1.toString();
		String s2 = obj2.toString();
		int l1 = s1.length();
		int l2 = s2.length();
		if(l1 < l2) return -1;
		else if(l1 > l2) return 1;
		else return s1.compareTo(s2);
	}
}