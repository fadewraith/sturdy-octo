package foursynchronizeddemo;

public class SyncCounter {

    private long count = 0;

//    public void incCount() {
//        this.count++;
//    }
//
//    public long getCount() {
//        return this.count;
//    }

    public synchronized void incCount() {
        this.count++;
    }

    public synchronized long getCount() {
        return this.count;
    }
}
