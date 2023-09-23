import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ForEachDemo {

	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("hello");
		list.add("world");
		list.add("john");
		list.add("marek");
		list.add("mac");
//		for(String s:list) {
//			if(s.startsWith("m"))
//				System.out.println(s+", s");
//		}
		
		// forEach is internally using the consumer functional interface
//		list.stream().forEach(t -> System.out.println(t));
		// filter method internally uses predicate functional interface
		list.stream().filter(t -> t.startsWith("m")).forEach(t -> System.out.println(t+ ", s filter"));
		
		Map<Integer, String> map = new HashMap<>();
		map.put(1,  "a");
		map.put(2,  "b");
		map.put(3,  "c");
		map.put(4,  "d");
		
//		map.forEach((key, value) -> System.out.println(key+": "+value));
//		map.entrySet().stream().forEach(obj -> System.out.println(obj));
		
		map.entrySet().stream().filter(k -> k.getKey() % 2 == 0).forEach(obj -> System.out.println(obj));
		
		Consumer<String> consumer = (t) -> System.out.println(t);
//		for(String s: list) {
//			consumer.accept(s);;
//		}
		
	}

}
