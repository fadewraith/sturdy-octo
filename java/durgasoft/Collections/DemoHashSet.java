import java.util.*;

public class DemoHashSet {

	public static void main(String[] args) {
		HashSet h = new HashSet();
		h.add("b");
		h.add("c");
		h.add("d");
		h.add("z");
		h.add(null);
		h.add("add");
		System.out.println(h.add("z"));
		System.out.println(h);

	}

}
