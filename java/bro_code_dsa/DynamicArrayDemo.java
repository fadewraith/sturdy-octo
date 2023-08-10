public class DynamicArrayDemo {

    public static void main(String[] args) {
        DynamicArray dynamicArray = new DynamicArray();

//        System.out.println(dynamicArray.capacity);
        dynamicArray.add("a");
        dynamicArray.add("b");
        dynamicArray.add("c");
        dynamicArray.insert(0, "y");
        dynamicArray.delete("a");
        System.out.println(dynamicArray.search("c"));
        System.out.println(dynamicArray);
        System.out.println("size: " + dynamicArray.size);
        System.out.println("capacity: " + dynamicArray.capacity);
        System.out.println(dynamicArray.isEmpty());
    }

}
