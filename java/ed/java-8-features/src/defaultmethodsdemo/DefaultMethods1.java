package defaultmethodsdemo;

interface A {
    default void sayHello() {
        System.out.println("defaultmethodsdemo.A says hello");
    }
}


interface B {
    default void sayHello() {
        System.out.println("defaultmethodsdemo.B says hello");
    }
}


public class DefaultMethods1 implements A, B { // will throw error

    public static void main(String[] args) {

    }
}