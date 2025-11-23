package seventhreadlocaldemo;

import threejavamemorymodelpartone.MyObject;

public class ThreadLocalInitialValueExample2 {

    public static void main(String[] args) {
        ThreadLocal<MyObject> threadLocal1 = new ThreadLocal<>() {
            @Override
            protected MyObject initialValue() {
                return new MyObject();
            }
        };

        ThreadLocal<MyObject> threadLocal2 = ThreadLocal.withInitial(() -> new MyObject());

        Thread t1 = new Thread(() -> {
            System.out.println("threadLocal1: " + threadLocal1.get());
            System.out.println("threadLocal2: " + threadLocal2.get());
        });

        Thread t2 = new Thread(() -> {
            System.out.println("threadlocal1: " + threadLocal1.get());
            System.out.println("threadlocal2: " + threadLocal2.get());
        });

        t1.start();
        t2.start();
    }
}
