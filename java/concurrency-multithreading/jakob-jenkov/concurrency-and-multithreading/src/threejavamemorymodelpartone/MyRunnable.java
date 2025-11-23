package threejavamemorymodelpartone;

public class MyRunnable implements Runnable {

    private int count = 0;
//    // this is stored in heap
//    private MyObject myObject = null;
//
//    public MyRunnable() {}
//    public MyRunnable(MyObject myObject) {
//        this.myObject = myObject;
//    }

    // local variables are not shared between the threads

    @Override
    public void run() {

        // local demo
        // MyObject myObject = new MyObject();
//        System.out.println(myObject);

        for (int i = 0; i < 1_000_000; i++) {
            synchronized (this) {
                this.count++;
            }
//            this.count++;
        }

        System.out.println(
                Thread.currentThread().getName() + " : " + this.count
        );
    }
}
