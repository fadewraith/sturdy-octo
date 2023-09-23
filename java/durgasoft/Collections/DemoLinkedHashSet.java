import java.util.*;

public class DemoLinkedHashSet {

	public static void main(String[] args) {
		LinkedHashSet h = new LinkedHashSet();
		h.add("b");
		h.add("c");
		h.add("d");
		h.add("z");
		h.add(null);
		h.add(10);
		System.out.println(h.add("z"));
		System.out.println(h);
		

	}

}
