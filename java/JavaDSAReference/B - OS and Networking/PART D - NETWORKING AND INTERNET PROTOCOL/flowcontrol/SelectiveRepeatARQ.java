package net.flowcontrol;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Selective Repeat ARQ Protocol Simulation
 * Uses a window, but unlike Go-Back-N, the receiver buffers out-of-order packets.
 * Only the specific lost packets are retransmitted by the sender upon timeout.
 */
public class SelectiveRepeatARQ {

    public static final int WINDOW_SIZE = 4;
    public static final int TOTAL_PACKETS = 8;

    public static void simulate() {
        Random rand = new Random();
        Set<Integer> ackedPackets = new HashSet<>();
        Set<Integer> sentPackets = new HashSet<>();
        int base = 0;

        System.out.println("Starting Selective Repeat Simulation (Window Size: " + WINDOW_SIZE + ")");

        while (base < TOTAL_PACKETS) {
            // Send unacknowledged packets in current window
            for (int i = base; i < base + WINDOW_SIZE && i < TOTAL_PACKETS; i++) {
                if (!ackedPackets.contains(i) && !sentPackets.contains(i)) {
                    System.out.println("[Sender] Sending packet " + i);
                    sentPackets.add(i);
                }
            }

            System.out.println("--- Wait for ACKs / Timeout ---");

            // Evaluate ACKs for currently un-acked packets in the window
            for (int i = base; i < base + WINDOW_SIZE && i < TOTAL_PACKETS; i++) {
                if (!ackedPackets.contains(i)) {
                    // 25% chance of packet loss
                    if (rand.nextDouble() < 0.25) {
                        System.out.println("[Network] Packet " + i + " lost!");
                        System.out.println("[Sender] Timeout for packet " + i + ". Marking for retransmission.");
                        sentPackets.remove(i); // Will be resent next loop
                    } else {
                        System.out.println("[Receiver] ACK received for packet " + i);
                        ackedPackets.add(i);
                    }
                }
            }

            // Slide window if base is acknowledged
            while (ackedPackets.contains(base)) {
                System.out.println("[Sender] Packet " + base + " acknowledged. Sliding window.");
                base++;
            }
            System.out.println();
        }

        System.out.println("All packets delivered successfully with Selective Repeat.");
    }
}
