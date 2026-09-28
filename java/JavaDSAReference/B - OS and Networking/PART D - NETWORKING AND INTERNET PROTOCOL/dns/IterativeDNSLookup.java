package net.dns;

/**
 * Simulates Iterative DNS Resolution.
 * In an iterative lookup, the client queries a server, and the server either 
 * returns the IP or a referral to the next server. The client must do the follow-up queries.
 */
public class IterativeDNSLookup {
    
    public static String resolveIteratively(DNSServer rootServer, String domain) {
        DNSServer currentServer = rootServer;
        
        while (currentServer != null) {
            System.out.println("[Client] Querying " + currentServer.name + " for " + domain);
            
            // 1. Check if the server has the exact answer
            if (currentServer.aRecords.containsKey(domain)) {
                String ip = currentServer.aRecords.get(domain);
                System.out.println("[" + currentServer.name + "] Replies with IP Address: " + ip);
                return ip;
            }
            
            // 2. Check if the server gives a referral
            DNSServer nextServer = currentServer.getDelegation(domain);
            if (nextServer != null) {
                System.out.println("[" + currentServer.name + "] Replies with Referral to: " + nextServer.name);
                // Client updates its target for the next loop iteration
                currentServer = nextServer;
            } else {
                // 3. No record and no referral
                System.out.println("[" + currentServer.name + "] Replies NXDOMAIN (Not Found)");
                return null;
            }
        }
        
        return null;
    }

    public static void main(String[] args) {
        System.out.println("--- Iterative DNS Setup ---");
        DNSServer root = new DNSServer("RootServer");
        DNSServer tld = new DNSServer("TLDServer(.com)");
        DNSServer auth = new DNSServer("AuthServer(example.com)");
        
        root.delegate(".com", tld);
        tld.delegate("example.com", auth);
        auth.addARecord("www.example.com", "192.168.1.100");
        
        System.out.println("\n--- Starting Iterative Lookup for www.example.com ---");
        // Client orchestrates the traversal
        String resolvedIp = resolveIteratively(root, "www.example.com");
        
        System.out.println("\nClient finalized lookup, IP: " + resolvedIp);
    }
}
