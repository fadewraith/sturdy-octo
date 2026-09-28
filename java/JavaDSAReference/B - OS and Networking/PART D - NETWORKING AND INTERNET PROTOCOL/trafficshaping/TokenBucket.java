package net.trafficshaping;

/*
 * Note: Token bucket allows bursts up to a limit while maintaining an average rate.
 * Tokens are added at a constant rate, and a packet needs enough tokens to be transmitted.
 */
public class TokenBucket {
    private final int capacity;
    private final int tokenRate;
    private int currentTokens = 0;

    public TokenBucket(int capacity, int tokenRate) {
        this.capacity = capacity;
        this.tokenRate = tokenRate;
    }

    public void addTokens() {
        int added = Math.min(capacity - currentTokens, tokenRate);
        currentTokens += added;
        System.out.println("Added " + added + " tokens. Current tokens: " + currentTokens);
    }

    public void sendPacket(int packetSize) {
        System.out.print("Trying to send packet of size " + packetSize + ". ");
        if (currentTokens >= packetSize) {
            currentTokens -= packetSize;
            System.out.println("Packet sent successfully. Remaining tokens: " + currentTokens);
        } else {
            System.out.println("Not enough tokens! Packet must wait or dropped.");
        }
    }

    public static void main(String[] args) {
        TokenBucket bucket = new TokenBucket(10, 3);
        bucket.addTokens();
        bucket.addTokens();
        bucket.sendPacket(4); // should succeed
        bucket.sendPacket(5); // should fail
        bucket.addTokens();
        bucket.sendPacket(5); // should succeed
    }
}
