package net.congestioncontrol;

public class TCPSlowStart {
    private int cwnd = 1;
    private int ssthresh = 16; // Arbitrary threshold

    public void simulate(int rounds) {
        System.out.println("Starting TCP Slow Start Simulation. Initial cwnd: " + cwnd + ", ssthresh: " + ssthresh);
        for (int i = 1; i <= rounds; i++) {
            System.out.println("Round " + i + ": cwnd = " + cwnd);
            if (cwnd < ssthresh) {
                // In slow start, cwnd doubles every RTT (each ACK increases cwnd by 1)
                cwnd *= 2; 
            } else {
                System.out.println("Reached ssthresh. Switching to Congestion Avoidance.");
                break;
            }
        }
    }

    public static void main(String[] args) {
        new TCPSlowStart().simulate(6);
    }
}
