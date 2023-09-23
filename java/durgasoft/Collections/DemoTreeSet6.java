import java.util.*;
public class DemoTreeSet6 {

	public static void main(String[] args) {
		TreeSet t = new TreeSet();
//		TreeSet t = new TreeSet(new MyComparatorStringBuffer());
		t.add(new StringBuffer("A"));
		t.add(new StringBuffer("Z"));
		t.add(new StringBuffer("K"));
		t.add(new StringBuffer("L"));
		System.out.println(t);
	}

}

// for string buffer, default sorting order is not there
/*
 * so what we will do here is that - 
 * convert the string buffer objects into string and for the string natural sorting is there
 * in the demo, it was not working, but here in ide is working by default.
 * if we are depending on default natural sorting order, compulsory it should be homogeneous and comparable.
 * if we are defining our own comparator i.e. customized sorting, then the objects need not be comparable
 * */
class MyComparatorStringBuffer implements Comparator {
	public int compare(Object obj1, Object obj2) {
		// if internal object is string buffer, we cannot typecast, if we do so wrror will be classtype exception
		String s1 = obj1.toString();
		String s2 = obj2.toString();
		
		return s1.compareTo(s2);
		
	}
}