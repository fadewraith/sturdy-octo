package twentyonethreadsignallingdemo;

public class ThreadSignalingExample {

    public static void main(String[] args) {

        SignalCarrier signalCarrier = new SignalCarrier();

        Thread waiter = new Thread(() -> {
            try {
                signalCarrier.doWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread notifier = new Thread(() -> {
            signalCarrier.doNotify();
        });

        // dont know sequence of calling here, or whether its missed or not
        waiter.start();
        notifier.start();

        // to demonstrate the prob, one signal is missed here and going indefinitely, in runner green signal means program is still running
//        notifier.start();
//        waiter.start();
    }
}
