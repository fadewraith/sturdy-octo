package defaultmethodsdemo;

interface A1 {
//    default modifier is public
    static void sayHello() {
        System.out.println("hello");
    }

    default void sayBye() {
        System.out.println("defaultmethodsdemo.B says hello");
    }
}

public class StaticMethodsInterfaceDemo implements A1 {
    public static void main(String[] args) {
        A1.sayHello();
    }
}

//// main can be written inside interface also, comes from 1.8
//public interface MyInterface {
//    public static void main(String[] args) {
//        System.out.println("defaultmethodsdemo.B says hello");
//    }
//}