package os.loadbalancing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * WEIGHTED ROUND ROBIN LOAD BALANCER
 * 
 * WHAT IT IS:
 * Similar to Round Robin, but servers have "weights" based on their hardware capacity.
 * A server with weight 3 gets 3 times as many requests as a server with weight 1.
 */
public class WeightedRoundRobin {

    static class Server {
        String name;
        int weight;
        Server(String n, int w) { name = n; weight = w; }
    }

    private List<Server> serverPool = new ArrayList<>();
    private AtomicInteger currentIndex = new AtomicInteger(0);

    public WeightedRoundRobin(List<Server> servers) {
        // Flatten the pool based on weights!
        // If ServerA has weight 3, we add it to the list 3 times.
        for (Server s : servers) {
            for (int i = 0; i < s.weight; i++) {
                serverPool.add(s);
            }
        }
    }

    public String getServer() {
        int index = Math.abs(currentIndex.getAndIncrement() % serverPool.size());
        return serverPool.get(index).name;
    }

    public static void main(String[] args) {
        System.out.println("--- WEIGHTED ROUND ROBIN LOAD BALANCER ---");
        List<Server> servers = new ArrayList<>();
        servers.add(new Server("ServerA (Beefy)", 3)); 
        servers.add(new Server("ServerB (Weak)", 1));  
        
        WeightedRoundRobin lb = new WeightedRoundRobin(servers);
        
        for (int i = 1; i <= 8; i++) {
            System.out.println("Request " + i + " routed to: " + lb.getServer());
        }
    }
}
