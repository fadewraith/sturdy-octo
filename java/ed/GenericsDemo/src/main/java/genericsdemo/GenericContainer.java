package genericsdemo;//public class genericsdemo.GenericContainer implements genericsdemo.GenericInterfaceContainer<String> {
//
//    private String item;
//    @Override
//    public void add(String item) {
//        this.item = item;
//    }
//
//    @Override
//    public String get() {
//        return item;
//    }
//}


public class GenericContainer<T> implements GenericInterfaceContainer<T> {

    private T item;
    @Override
    public void add(T item) {
        this.item = item;
    }

    @Override
    public T get() {
        return item;
    }
}