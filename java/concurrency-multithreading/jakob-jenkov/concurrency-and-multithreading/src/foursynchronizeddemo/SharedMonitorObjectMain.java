package foursynchronizeddemo;

public class SharedMonitorObjectMain {

    /**
     * Dont use string constant objects as monitor objects
     * */

    public static void main(String[] args) {
        Object monitor1 = new Object();

        SharedMonitorObject smo1 = new SharedMonitorObject(monitor1);
        SharedMonitorObject smo2 = new SharedMonitorObject(monitor1);

        smo1.incrementCounter();
        smo2.incrementCounter();

        Object monitor2 = new Object();

        SharedMonitorObject smo3 = new SharedMonitorObject(monitor2);
        smo3.incrementCounter();
    }
}
