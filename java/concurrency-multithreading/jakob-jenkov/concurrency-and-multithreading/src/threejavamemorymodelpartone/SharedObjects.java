package threejavamemorymodelpartone;

public class SharedObjects {

    public static void main(String[] args) {

//        MyObject myObject = new MyObject();

        Runnable runnable = new MyRunnable();
//        Runnable runnable = new MyRunnable(myObject);

        Thread t1 = new Thread(runnable, "Thread 1");
        Thread t2 = new Thread(runnable, "Thread 2");

        t1.start();
        t2.start();
    }
}
