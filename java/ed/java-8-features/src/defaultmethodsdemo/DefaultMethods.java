package defaultmethodsdemo;

interface Parent1 {
    default void sayHello() {
        System.out.println("hello");
    }
}

class Child1 implements Parent1 {
    @Override
    public void sayHello() {
        System.out.println("defaultmethodsdemo.Child says hello");
    }

}

public class DefaultMethods {
    public static void main(String[] args) {
//        will work
        Parent1 c = new Child1();
//        defaultmethodsdemo.Child1 c = new defaultmethodsdemo.Child1();
        c.sayHello();
    }
}