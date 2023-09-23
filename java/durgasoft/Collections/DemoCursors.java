import java.util.*;

public class DemoCursors {

	public static void main(String[] args) {
		//Vector$1 output is the implementation class, where Vector$1 is anonymous inner class present inside vector
		//Vector$Itr, where Itr is inner class present inside vector which implements Iterator interface
		//Vector$ListItr is the internal implementation className for the list iterator interface, we are getting corresponding implemented class objects
		Vector v = new Vector();
		Enumeration e = v.elements();
		Iterator itr = v.iterator();
		ListIterator litr = v.listIterator();
		System.out.println(e.getClass().getName());
		System.out.println(itr.getClass().getName());
		System.out.println(litr.getClass().getName());

	}

}
