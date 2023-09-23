import java.util.*;

public class DemoListIterator {

	public static void main(String[] args) {
		
		LinkedList l = new LinkedList();
		l.add("hello");
		l.add("world");
		l.add("john");
		l.add("doe");
		System.out.println(l);
		ListIterator ltr = l.listIterator();
		while(ltr.hasNext()) {
			String s = (String)ltr.next();
			if(s.equals("world")) {
				ltr.remove();
			} else if(s.equals("john")) {
				ltr.add("random");
			} else if(s.equals("doe")) {
				ltr.set("something");
			}
		}
		System.out.println(l);
		
		

	}

}
 