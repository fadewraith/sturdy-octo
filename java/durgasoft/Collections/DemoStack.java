import java.util.*;

public class DemoStack {

	public static void main(String[] args) {
		Stack<String> s = new Stack<String>();
		s.push("A");
		s.push("B");
		s.push("C");
		System.out.println(s);
		System.out.println(s.search("A")); //offset for c = 3, b = 2, a = 1
		System.out.println(s.search("Z"));

	}

}
