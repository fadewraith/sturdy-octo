import java.util.*;

public class DemoVector {

	public static void main(String[] args) {
//		Vector v = new Vector(); // default initialised constructor
		Vector v = new Vector(17, 2);
		System.out.println(v.capacity());
		for(int i = 1; i <= 10; i++) {
			v.addElement(i);
		}
		System.out.println(v.capacity());
		v.addElement("A");
		System.out.println(v.capacity());
		System.out.println(v);
		System.out.println(v.capacity());

	}

}
