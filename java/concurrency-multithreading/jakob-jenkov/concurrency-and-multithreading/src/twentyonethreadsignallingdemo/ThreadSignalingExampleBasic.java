package twentyonethreadsignallingdemo;

public class ThreadSignalingExampleBasic {

    // wait and notify cant be called on any object unless there is a synchronization lock on that object
    // synchronized needs to be used

    public static void main(String[] args) {

        Object signalObject = new Object();

        Thread waitingThread = new Thread(() -> {
            synchronized (signalObject) {
                try {
                    signalObject.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread notifyingThread = new Thread(() -> {
            synchronized (signalObject) {
                signalObject.notify();
            }
        });

        waitingThread.start();
        notifyingThread.start();
    }
}
