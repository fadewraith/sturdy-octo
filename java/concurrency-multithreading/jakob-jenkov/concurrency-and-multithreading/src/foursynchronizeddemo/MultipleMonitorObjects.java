package foursynchronizeddemo;

public class MultipleMonitorObjects {

    private Object monitor1 = new Object();
    private Object monitor2 = new Object();

    private int counter1 = 0;
    private int counter2 = 0;

    public void incrementCounter1() {
        synchronized (this.monitor1) {
            this.counter1++;
        }
    }

    public void incrementCounter2() {
        synchronized (this.monitor2) {
            this.counter2++;
        }
    }
}
