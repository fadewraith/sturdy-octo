package net.mac;

import java.util.Random;

public class CSMACA {
    private final Random random = new Random();
    private static final int DIFS = 50;

    public boolean transmit(int nodeId) {
        System.out.println("Node " + nodeId + " wants to transmit.");
        
        boolean channelBusy = random.nextDouble() < 0.5; // 50% chance busy
        if (channelBusy) {
            System.out.println("Channel is busy. Node " + nodeId + " defers transmission.");
        }
        
        System.out.println("Node " + nodeId + " waiting DIFS period.");
        sleep(DIFS);
        
        // Random backoff
        int contentionWindow = 15;
        int backoffSlots = random.nextInt(contentionWindow);
        System.out.println("Node " + nodeId + " backing off for " + backoffSlots + " slots.");
        sleep(backoffSlots * 10);
        
        System.out.println("Node " + nodeId + " sends RTS. Receives CTS.");
        System.out.println("Node " + nodeId + " transmitting data...");
        
        boolean ackReceived = random.nextDouble() < 0.9; // 90% chance of ACK
        if (ackReceived) {
            System.out.println("Node " + nodeId + " received ACK. Transmission successful.");
            return true;
        } else {
            System.out.println("Node " + nodeId + " did not receive ACK. Collision assumed. Will retry.");
            return false;
        }
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        CSMACA mac = new CSMACA();
        mac.transmit(2);
    }
}
