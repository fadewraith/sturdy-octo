package net.flowcontrol;

import java.util.Random;

/**
 * Stop-and-Wait ARQ Protocol Simulation
 * Sender sends a packet and waits for an ACK before sending the next one.
 * Injects random packet loss to demonstrate timeouts and retransmissions.
 */
public class StopAndWaitARQ {

    static class Packet {
        int seqNum;
        String data;
        Packet(int seq, String data) { this.seqNum = seq; this.data = data; }
    }

    static class Receiver {
        int expectedSeq = 0;
        Random rand = new Random();

        public boolean receive(Packet p) {
            // Simulate 20% packet loss
            if (rand.nextDouble() < 0.2) {
                System.out.println("[Receiver] Packet " + p.seqNum + " LOST in transit!");
                return false; 
            }
            if (p.seqNum == expectedSeq) {
                System.out.println("[Receiver] Received successfully: " + p.data + " (Seq: " + p.seqNum + ")");
                expectedSeq = 1 - expectedSeq; 
            } else {
                System.out.println("[Receiver] Duplicate Packet received (Seq: " + p.seqNum + "). Resending ACK.");
            }
            return true;
        }

        public boolean sendAck() {
            // Simulate 10% ACK loss
            if (rand.nextDouble() < 0.1) {
                System.out.println("[Receiver] ACK sent but LOST in transit!");
                return false;
            }
            return true;
        }
    }

    public static void simulate() {
        System.out.println("--- STOP-AND-WAIT ARQ WITH PACKET LOSS INJECTION ---");
        Receiver receiver = new Receiver();
        int seq = 0;
        for (int i = 1; i <= 5; i++) {
            Packet p = new Packet(seq, "DataChunk_" + i);
            boolean ackReceived = false;

            while (!ackReceived) {
                System.out.println("[Sender] Sending Packet " + seq + " (" + p.data + ")");
                boolean received = receiver.receive(p);
                
                if (received) {
                    boolean ack = receiver.sendAck();
                    if (ack) {
                        System.out.println("[Sender] ACK received for Packet " + seq);
                        ackReceived = true;
                    } else {
                        System.out.println("[Sender] Timeout! ACK not received. Retransmitting...");
                    }
                } else {
                    System.out.println("[Sender] Timeout! Packet not received by destination. Retransmitting...");
                }
            }
            seq = 1 - seq; 
            System.out.println();
        }
    }

    public static void main(String[] args) {
        simulate();
    }
}
