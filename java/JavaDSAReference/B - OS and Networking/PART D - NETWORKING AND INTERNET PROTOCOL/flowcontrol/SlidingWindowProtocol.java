package net.flowcontrol;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Sliding Window Protocol (Generalized Version)
 * Demonstrates the basic mechanics of how a sliding window restricts the number 
 * of unacknowledged packets in flight to control flow between sender and receiver.
 */
public class SlidingWindowProtocol {

    static class GeneralizedSender {
        int windowSize;
        int nextToSend = 0;
        int unackedCount = 0;
        Queue<Integer> unackedPackets = new LinkedList<>();

        public GeneralizedSender(int windowSize) {
            this.windowSize = windowSize;
        }

        public boolean canSend() {
            return unackedCount < windowSize;
        }

        public void sendNext() {
            if (canSend()) {
                System.out.println("[Sender] Sending Frame " + nextToSend);
                unackedPackets.offer(nextToSend);
                nextToSend++;
                unackedCount++;
            } else {
                System.out.println("[Sender] Window full. Cannot send. Waiting for ACKs...");
            }
        }

        public void receiveAck(int frame) {
            System.out.println("[Sender] Received ACK for Frame " + frame);
            if (unackedPackets.contains(frame)) {
                unackedPackets.remove(frame);
                unackedCount--;
            }
        }
    }

    public static void simulate() {
        int windowSize = 3;
        GeneralizedSender sender = new GeneralizedSender(windowSize);

        System.out.println("--- Sliding Window Mechanics ---");
        
        // Attempt to send beyond window size
        for (int i = 0; i < 5; i++) {
            sender.sendNext();
        }

        // Acknowledge one frame to open window
        sender.receiveAck(0);
        
        // Send next frame
        sender.sendNext();

        // Acknowledge all
        sender.receiveAck(1);
        sender.receiveAck(2);
        sender.receiveAck(3);

        sender.sendNext();
    }
}
