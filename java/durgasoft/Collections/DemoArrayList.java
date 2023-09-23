import java.util.*;

public class DemoArrayList {

	public static void main(String[] args) {
		// whenever we try to print any object reference, internally toString method will be called
		ArrayList l = new ArrayList();
		l.add("A");
		l.add(10);
		l.add("A");
		l.add(null);
		System.out.println(l);
		l.remove(2);
		System.out.println(l);
		l.add(2, "M");
		l.add("N");
		System.out.println(l);
		
		ArrayList l1 = new ArrayList();
		LinkedList l2 = new LinkedList();
//		System.out.println(l1 instanceof Serializable); // not working
		System.out.println(l2 instanceof Cloneable);
		System.out.println(l1 instanceof RandomAccess);
		System.out.println(l2 instanceof RandomAccess);
		System.out.println(l1 instanceof Cloneable);

	}

}
