import java.util.*;

public class DemoTreeSet8Employee implements Comparable {
	
	String name;
	int eid;
	DemoTreeSet8Employee(String name, int eid) {
		this.name = name;
		this.eid = eid;
	}
	
	public String toString() {
		return name+"--"+eid;
	}
	
	public int compareTo(Object obj) {
		int eid1 = this.eid; // this will be the current object on which we are calling compareTo method
		DemoTreeSet8Employee e = (DemoTreeSet8Employee)obj; // this will be our second object which we are passing inside compareTo method
		int eid2 = e.eid;
		if(eid1 < eid2) return -1;
		else if(eid1 > eid2) return 1;
		else return 0;
	}

	public static void main(String[] args) {
		DemoTreeSet8Employee e1 = new DemoTreeSet8Employee("nag", 100);
		DemoTreeSet8Employee e2 = new DemoTreeSet8Employee("balaiah", 200);
		DemoTreeSet8Employee e3 = new DemoTreeSet8Employee("chiru", 50);
		DemoTreeSet8Employee e4 = new DemoTreeSet8Employee("venki", 150);
		DemoTreeSet8Employee e5 = new DemoTreeSet8Employee("nag", 100);
		TreeSet t = new TreeSet();
		t.add(e1);
		t.add(e2);
		t.add(e3);
		t.add(e4);
		t.add(e5);
		System.out.println(t);
		TreeSet t1 = new TreeSet(new MyComparatorEmployee());
		t1.add(e1);
		t1.add(e2);
		t1.add(e3);
		t1.add(e4);
		t1.add(e5);
		System.out.println(t1);
	}

}

// customized sorting
class MyComparatorEmployee implements Comparator {
	public int compare(Object obj1, Object obj2) {
		DemoTreeSet8Employee e1 = (DemoTreeSet8Employee)obj1;
		DemoTreeSet8Employee e2 = (DemoTreeSet8Employee)obj2;
		String s1 = e1.name;
		String s2 = e2.name;
		return s1.compareTo(s2);
		
	}
}