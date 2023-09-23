import java.util.*;
public class DemoTreeSet4 {

	public static void main(String[] args) {
//		by default it will call compareTo method internally and will sort in ascending order, i.e. I1.compareTo(I2)
//		TreeSet t = new TreeSet(new MyComparator());
		TreeSet t = new TreeSet(new MyComparator()); 
		t.add(10);
		t.add(0);
		t.add(15);
		t.add(20);
		t.add(20);
		System.out.println(t);
	}

}


class MyComparator implements Comparator {
	public int compare(Object obj1, Object obj2) {
		Integer I1 = (Integer)obj1;
		Integer I2 = (Integer)obj2;
		if(I1 < I2)
			return 1;
		else if(I1 > I2)
			return -1;
		else
			return 0;
	}
}

class MyComparatorNumber implements Comparator {
	public int compare(Object obj1, Object obj2) {
		Integer I1 = (Integer)obj1;
		Integer I2 = (Integer)obj2;
//		return I1.compareTo(I2); // [0, 10, 15, 20] ascending order
		return -I1.compareTo(I2); // if its negative, then it will invert it, [20, 15, 10, 0] descending order
//		return I2.compareTo(I1); // [20, 15, 10, 0] descending order
//		return -I2.compareTo(I1); // [0, 10, 15, 20] asc order
//		return +1; // [10, 0, 15, 20, 20] always after, insertion order, allowing duplicates, bcoz logic is not telling it duplicate
//		return -1; // [20, 20, 15, 0, 10] , reverser of insertion order
//		return 0; // only first element will be inserted and all other elements are considered as duplicates
		
	}
}