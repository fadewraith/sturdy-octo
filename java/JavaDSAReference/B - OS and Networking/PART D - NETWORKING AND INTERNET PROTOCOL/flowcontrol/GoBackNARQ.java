package net.flowcontrol;

import java.util.Random;

/**
 * Go-Back-N ARQ Protocol Simulation
 * Uses a sliding window for sending multiple packets. If a packet is lost, 
 * all packets starting from the lost one are retransmitted.
 */
public class GoBackNARQ {

    public static final int WINDOW_SIZE = 4;
    public static final int TOTAL_PACKETS = 10;

    public static void simulate() {
        Random rand = new Random();
        int nextSeqNum = 0;
        int base = 0;

        System.out.println("Starting Go-Back-N Simulation (Window Size: " + WINDOW_SIZE + ")");

        while (base < TOTAL_PACKETS) {
            // Send packets in the window
            while (nextSeqNum < base + WINDOW_SIZE && nextSeqNum < TOTAL_PACKETS) {
                System.out.println("[Sender] Sending packet " + nextSeqNum);
                nextSeqNum++;
            }

            // Simulate acknowledgment with 30% chance of a cumulative ACK failure (loss/timeout)
            if (rand.nextDouble() < 0.3) {
                System.out.println("\n[Network] Packet or ACK lost for packet " + base + "!");
                System.out.println("[Sender] Timeout for packet " + base + ".");
                System.out.println("[Sender] Go-Back-N to packet " + base + ".\n");
                nextSeqNum = base; // Retransmit all from base
            } else {
                // Successful ACK for some number of packets in the window
                int ackedCount = rand.nextInt(Math.min(WINDOW_SIZE, TOTAL_PACKETS - base)) + 1;
                System.out.println("\n[Receiver] Cumulative ACK up to packet " + (base + ackedCount - 1) + " received.");
                base += ackedCount;
                System.out.println("[Sender] Window slided. New base is " + base + "\n");
            }
        }

        System.out.println("All packets delivered successfully.");
    }
}
