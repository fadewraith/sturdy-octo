package twentyonethreadsignallingdemo;

public class SpuriosWakeupSignalGuard {

    Object myMonitorObject = new Object();
    boolean wasSignalled = false;

    public void doNotify() {
        synchronized (myMonitorObject) {
            wasSignalled = true;
            myMonitorObject.notify();
        }
    }

    public void doWait() throws InterruptedException {
        synchronized (myMonitorObject) {
            // common constructs used to guard against spurious wakeups
            while(!wasSignalled) {
                myMonitorObject.wait();
            }
            // clear signal and continue running.
            wasSignalled = false;
        }
    }
}
