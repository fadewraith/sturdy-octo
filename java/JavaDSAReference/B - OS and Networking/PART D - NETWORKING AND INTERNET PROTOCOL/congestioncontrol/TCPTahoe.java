package net.congestioncontrol;

public class TCPTahoe {
    private int cwnd = 1;
    private int ssthresh = 64;

    public void simulate(String[] events) {
        System.out.println("Starting TCP Tahoe Simulation");
        for (int i = 0; i < events.length; i++) {
            String event = events[i];
            System.out.print("Round " + (i + 1) + " | Event: " + event + " | cwnd before: " + cwnd + " | ");
            if (event.equals("ACK")) {
                if (cwnd < ssthresh) {
                    cwnd *= 2; // Slow start
                } else {
                    cwnd += 1; // Congestion avoidance
                }
            } else if (event.equals("TIMEOUT") || event.equals("TRIPLE_DUP_ACK")) {
                // Tahoe treats both timeout and triple duplicate ACK as same
                ssthresh = Math.max(2, cwnd / 2);
                cwnd = 1;
            }
            System.out.println("cwnd after: " + cwnd + ", ssthresh: " + ssthresh);
        }
    }

    public static void main(String[] args) {
        String[] events = {"ACK", "ACK", "ACK", "ACK", "TRIPLE_DUP_ACK", "ACK", "ACK", "TIMEOUT", "ACK"};
        new TCPTahoe().simulate(events);
    }
}
