package os.loadbalancing;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ROUND ROBIN LOAD BALANCER
 * 
 * Requests are distributed across servers sequentially in a circular order.
 */
public class RoundRobinLoadBalancer {

    private List<String> servers;
    private AtomicInteger currentIndex = new AtomicInteger(0);

    public RoundRobinLoadBalancer(List<String> servers) {
        this.servers = servers;
    }

    public String getServer() {
        // Atomic getAndIncrement ensures thread-safety in concurrent web requests
        int index = Math.abs(currentIndex.getAndIncrement() % servers.size());
        return servers.get(index);
    }

    public static void main(String[] args) {
        System.out.println("--- ROUND ROBIN LOAD BALANCER ---");
        RoundRobinLoadBalancer lb = new RoundRobinLoadBalancer(Arrays.asList("ServerA", "ServerB", "ServerC"));
        
        for (int i = 1; i <= 5; i++) {
            System.out.println("Request " + i + " routed to: " + lb.getServer());
        }
    }
}
