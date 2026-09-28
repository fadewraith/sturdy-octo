package net.trafficshaping;

/*
 * Note: Leaky bucket enforces a strict constant output rate, regardless of the burstiness of the incoming traffic.
 * If the bucket is full, packets are dropped.
 */
public class LeakyBucket {
    private final int capacity;
    private final int leakRate;
    private int currentWater = 0;

    public LeakyBucket(int capacity, int leakRate) {
        this.capacity = capacity;
        this.leakRate = leakRate;
    }

    public void addPacket(int packetSize) {
        System.out.print("Incoming packet of size " + packetSize + ". ");
        if (currentWater + packetSize > capacity) {
            System.out.println("Bucket overflow! Packet dropped.");
        } else {
            currentWater += packetSize;
            System.out.println("Packet added. Current water level: " + currentWater);
        }
    }

    public void leak() {
        if (currentWater == 0) {
            System.out.println("Bucket empty. Nothing to leak.");
        } else {
            int leaked = Math.min(currentWater, leakRate);
            currentWater -= leaked;
            System.out.println("Leaked " + leaked + " units. Current water level: " + currentWater);
        }
    }

    public static void main(String[] args) {
        LeakyBucket bucket = new LeakyBucket(10, 3);
        bucket.addPacket(4);
        bucket.addPacket(5);
        bucket.addPacket(5); // Should drop
        bucket.leak();
        bucket.leak();
        bucket.addPacket(2);
    }
}
