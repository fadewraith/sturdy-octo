package os.loadbalancing;

import java.util.Arrays;
import java.util.List;

/**
 * LEAST CONNECTIONS LOAD BALANCER
 * 
 * WHAT IT IS:
 * Routes the new request to the server that currently has the FEWEST active connections.
 * Perfect for environments where requests take highly variable amounts of time to process.
 */
public class LeastConnections {

    static class Server {
        String name;
        int activeConnections;
        
        Server(String n, int active) { 
            name = n; 
            activeConnections = active; 
        }
    }

    private List<Server> servers;

    public LeastConnections(List<Server> servers) {
        this.servers = servers;
    }

    public String getServer() {
        Server leastLoaded = servers.get(0);
        
        for (Server s : servers) {
            if (s.activeConnections < leastLoaded.activeConnections) {
                leastLoaded = s;
            }
        }
        
        // Simulate adding a connection
        leastLoaded.activeConnections++;
        return leastLoaded.name;
    }

    public static void main(String[] args) {
        System.out.println("--- LEAST CONNECTIONS LOAD BALANCER ---");
        
        Server sA = new Server("ServerA", 50); // Heavily loaded
        Server sB = new Server("ServerB", 10); // Lightly loaded
        Server sC = new Server("ServerC", 12);
        
        LeastConnections lb = new LeastConnections(Arrays.asList(sA, sB, sC));
        
        System.out.println("Request 1 routed to: " + lb.getServer()); // Should go to B (10->11)
        System.out.println("Request 2 routed to: " + lb.getServer()); // Should go to B (11->12)
        System.out.println("Request 3 routed to: " + lb.getServer()); // Should go to C (12->13, or B depending on tie-break)
    }
}
