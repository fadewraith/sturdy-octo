import java.util.*;
public class DemoTreeSet2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * if we are depending on default natural sorting order, then it should be homogeneous & comparable, because
		 * string buffer objects are not comfortable.
		 * in the corresponding class, implements comparable interface (Comparable(I)), then those obejcts are - 
		 * comparable objects. string class implements comparable. 
		 * does stringbuffer implements comparable interface ? confusing bcoz, according to video lecture, output -
		 * should be throwing error, but here it is running and giving output in sorted order
		 * */
		TreeSet t = new TreeSet();
		t.add(new StringBuffer("A"));
		t.add(new StringBuffer("Z"));
		t.add(new StringBuffer("L"));
		t.add(new StringBuffer("B"));
		System.out.println(t);
	}

}
