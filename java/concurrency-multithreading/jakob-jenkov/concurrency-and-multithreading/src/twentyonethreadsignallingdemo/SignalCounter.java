package twentyonethreadsignallingdemo;

public class SignalCounter {

    private int signals = 0;
    private int threadsWaiting = 0;

/*    public void doNotify() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + " calling notify()");
            if(this.threadsWaiting == 0) {
                this.signals++;
            }
            this.notify();
            System.out.println(Thread.currentThread().getName() + " exited notify()");
        }
    }

    public void doWait() throws InterruptedException {
        synchronized (this) {
            if(this.signals > 0) {
                System.out.println(Thread.currentThread().getName() + " " + this.signals + " signals was already raised - decrementing signals and return");
                this.signals--;
                return;
            }
            System.out.println(Thread.currentThread().getName() + " calling wait()");
            this.threadsWaiting++;
            this.wait();
            this.threadsWaiting--;
            System.out.println(Thread.currentThread().getName() + " exited wait()");
        }
    }*/

    public void doNotify() {
        synchronized (this) {
            this.signals++;
            System.out.println("Signals stored: " + this.signals);
            this.notify();
        }
    }

    public void doWait() throws InterruptedException {
        synchronized (this) {
            this.signals--;
            if(this.signals >= 0) {
                System.out.println(Thread.currentThread().getName() + " - " + (this.signals + 1) + " signal(s) were stored. Exiting without wait().");
                return;
            }
            System.out.println(Thread.currentThread().getName() + " calling wait()");
            this.wait();
            System.out.println(Thread.currentThread().getName() + " exited wait()");
        }
    }
}
