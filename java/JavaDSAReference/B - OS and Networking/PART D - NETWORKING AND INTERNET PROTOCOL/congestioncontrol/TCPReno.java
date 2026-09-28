package net.congestioncontrol;

public class TCPReno {
    private int cwnd = 1;
    private int ssthresh = 64;

    public void simulate(String[] events) {
        System.out.println("Starting TCP Reno Simulation");
        for (int i = 0; i < events.length; i++) {
            String event = events[i];
            System.out.print("Round " + (i + 1) + " | Event: " + event + " | cwnd before: " + cwnd + " | ");
            if (event.equals("ACK")) {
                if (cwnd < ssthresh) {
                    cwnd *= 2; // Slow start
                } else {
                    cwnd += 1; // Congestion avoidance
                }
            } else if (event.equals("TIMEOUT")) {
                ssthresh = Math.max(2, cwnd / 2);
                cwnd = 1;
            } else if (event.equals("TRIPLE_DUP_ACK")) {
                // Fast recovery in Reno
                ssthresh = Math.max(2, cwnd / 2);
                cwnd = ssthresh; // Simplified fast recovery window adjustment
            }
            System.out.println("cwnd after: " + cwnd + ", ssthresh: " + ssthresh);
        }
    }

    public static void main(String[] args) {
        String[] events = {"ACK", "ACK", "ACK", "ACK", "TRIPLE_DUP_ACK", "ACK", "ACK", "TIMEOUT", "ACK"};
        new TCPReno().simulate(events);
    }
}
