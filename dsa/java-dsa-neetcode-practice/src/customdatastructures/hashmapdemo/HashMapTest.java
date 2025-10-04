package customdatastructures.hashmapdemo;

public class HashMapTest {
    public static void main(String[] args) {
        CustomHashMap<Integer, String> map = new CustomHashMap<>();

        // Test put operations
        map.put(1, "One");
        map.put(2, "Two");
        map.put(17, "Seventeen"); // Will cause collision with key 1
        map.put(null, "Null Value");
        map.put(null, "Null Value 1");
        System.out.println("===========================");
        map.display();
        System.out.println("===========================");

        // Test get operations
        System.out.println("Get key 1: " + map.get(1));
        System.out.println("Get key 17: " + map.get(17));
        System.out.println("Get null key: " + map.get(null));

        // Test remove
        map.remove(2);
        System.out.println("After removing key 2:");
        map.display();

        // Test size and other methods
        System.out.println("Size: " + map.size());
        System.out.println("Contains key 1: " + map.containsKey(1));
        System.out.println("Contains value 'One': " + map.containsValue("One"));
    }
}
