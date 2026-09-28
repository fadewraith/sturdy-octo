package net.congestioncontrol;

public class AIMD {
    private int cwnd = 1;

    public void simulate(boolean[] ackLossSequence) {
        System.out.println("Starting AIMD Simulation. Initial cwnd: " + cwnd);
        for (int i = 0; i < ackLossSequence.length; i++) {
            boolean packetLoss = ackLossSequence[i];
            System.out.print("Round " + (i + 1) + " | cwnd: " + cwnd + " | Event: ");
            if (packetLoss) {
                System.out.println("Packet Loss (Multiplicative Decrease)");
                cwnd = Math.max(1, cwnd / 2);
            } else {
                System.out.println("ACK Received (Additive Increase)");
                cwnd += 1;
            }
        }
    }

    public static void main(String[] args) {
        boolean[] sequence = {false, false, false, true, false, false, true, false};
        new AIMD().simulate(sequence);
    }
}
