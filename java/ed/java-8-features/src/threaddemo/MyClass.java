package threaddemo;

public class MyClass implements Runnable {
//    Runnable is used to create threads
    @Override
    public void run() {
        for(int i = 1; i <= 10; ++i) {
            System.out.println("hello: " + i);
        }
    }

    public static void main(String[] args) {

//        MyClass myClass = new MyClass();
//        Thread childThread = new Thread(myClass);
//        childThread.run();
//
//        for(int i = 1; i <= 10; ++i) {
//            System.out.println("bye: " + i);
//        }

        Runnable runnable = () -> {
            for(int i = 1; i <= 5; ++i) {
                System.out.println("hello " + i);
            }
        };
        Thread childThread = new Thread(runnable);
        childThread.run();

    }
}
