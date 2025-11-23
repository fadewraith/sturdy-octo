package twentyonethreadsignallingdemo;

public class SignalHolder {

    // this is the one way to solve the signal problems, missed signal soln
    // this soln still has 1 big prob, if thread calls doNotify more than once before thread calls doWait, then there was no recording of how many times doNotify was called
    // soln to this prob is change signal holder to signal counter class

    private boolean signalRaised = false;
    private boolean isThreadWaiting = false;

    public void doNotify() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + " calling notify()");
            if(!this.isThreadWaiting) {
                this.signalRaised = true;
            }
            this.notify();
            System.out.println(Thread.currentThread().getName() + " exited notify()");
        }
    }

    public void doWait() throws InterruptedException {
        synchronized (this) {
            if(this.signalRaised) {
                System.out.println(Thread.currentThread().getName() + " signal was already raised");
                this.signalRaised = false;
                return;
            }
            System.out.println(Thread.currentThread().getName() + " calling wait()");
            this.isThreadWaiting = true;
            this.wait();
            this.isThreadWaiting = false;
            System.out.println(Thread.currentThread().getName() + " exited wait()");
        }
    }
}
