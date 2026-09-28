package os.loadbalancing;

import java.util.Arrays;
import java.util.List;

/**
 * IP HASH-BASED LOAD BALANCER
 * 
 * WHAT IT IS:
 * Hashes the Client's IP Address to determine which server gets the request.
 * 
 * WHY IT MATTERS:
 * Guarantees "Session Persistence" (Sticky Sessions). A specific user will ALWAYS 
 * hit the exact same server as long as the server pool doesn't change, meaning 
 * their in-memory session cache (like a shopping cart) remains valid!
 */
public class IPHashLoadBalancer {

    private List<String> servers;

    public IPHashLoadBalancer(List<String> servers) {
        this.servers = servers;
    }

    public String getServer(String clientIp) {
        // Simple hash calculation
        int hash = clientIp.hashCode();
        
        // Ensure positive index
        int index = Math.abs(hash) % servers.size();
        return servers.get(index);
    }

    public static void main(String[] args) {
        System.out.println("--- IP HASH LOAD BALANCER (STICKY SESSIONS) ---");
        IPHashLoadBalancer lb = new IPHashLoadBalancer(Arrays.asList("ServerA", "ServerB", "ServerC"));
        
        String ip1 = "192.168.1.55";
        String ip2 = "10.0.0.9";
        
        System.out.println("User 1 (IP " + ip1 + ") routed to: " + lb.getServer(ip1));
        System.out.println("User 2 (IP " + ip2 + ") routed to: " + lb.getServer(ip2));
        
        System.out.println("User 1 refreshes the page...");
        System.out.println("User 1 (IP " + ip1 + ") STILL routed to: " + lb.getServer(ip1) + " (Sticky Session!)");
    }
}
