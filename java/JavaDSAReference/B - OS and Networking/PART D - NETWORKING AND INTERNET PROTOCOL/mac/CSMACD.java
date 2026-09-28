package net.mac;

import java.util.Random;

public class CSMACD {
    private static final int MAX_ATTEMPTS = 15;
    private final Random random = new Random();

    public boolean transmit(int nodeId) {
        int attempt = 0;
        while (attempt <= MAX_ATTEMPTS) {
            System.out.println("Node " + nodeId + " attempting to transmit (Attempt " + (attempt + 1) + ")");
            
            boolean collision = random.nextDouble() < 0.3; // 30% chance of collision
            
            if (!collision) {
                System.out.println("Node " + nodeId + " successfully transmitted without collision.");
                return true;
            }
            
            System.out.println("Collision detected by Node " + nodeId + "!");
            attempt++;
            
            if (attempt > MAX_ATTEMPTS) {
                System.out.println("Node " + nodeId + " reached max attempts. Transmission failed.");
                return false;
            }
            
            // Exponential backoff
            int maxWait = (1 << Math.min(attempt, 10)) - 1; 
            int waitTime = random.nextInt(maxWait + 1);
            System.out.println("Node " + nodeId + " backing off for " + waitTime + " slot times.");
            try {
                Thread.sleep(waitTime * 10L); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return false;
    }

    public static void main(String[] args) {
        CSMACD mac = new CSMACD();
        mac.transmit(1);
    }
}
