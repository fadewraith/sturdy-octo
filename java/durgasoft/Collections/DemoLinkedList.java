import java.util.*;

public class DemoLinkedList {

	public static void main(String[] args) {
		LinkedList l = new LinkedList();
		l.add("Hello");
		l.add(30);
		System.out.println(l);
		l.add(null);
		l.add("hello");
		System.out.println(l);
		l.set(0, "Software");
		System.out.println(l);
		l.add(1, "Doe");
		System.out.println(l);
		l.removeLast();
		l.addFirst("John");
		System.out.println(l);

	}

}
