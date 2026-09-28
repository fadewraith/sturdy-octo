package net.dns;

/**
 * Simulates Recursive DNS Resolution.
 * In a recursive lookup, the queried DNS server takes on the burden of querying 
 * other DNS servers on behalf of the client until it finds the IP address.
 */
public class RecursiveDNSLookup {
    
    public static String resolveRecursively(DNSServer currentServer, String domain) {
        System.out.println("[" + currentServer.name + "] received query for: " + domain);
        
        // 1. Check if the server has the record
        if (currentServer.aRecords.containsKey(domain)) {
            String ip = currentServer.aRecords.get(domain);
            System.out.println("[" + currentServer.name + "] Found A-Record: " + ip);
            return ip;
        }
        
        // 2. Check if the server has a delegation for this domain
        DNSServer nextServer = currentServer.getDelegation(domain);
        if (nextServer != null) {
            System.out.println("[" + currentServer.name + "] Delegating query down to: " + nextServer.name);
            
            // Server recurses on behalf of the client
            String resultIp = resolveRecursively(nextServer, domain);
            
            System.out.println("[" + currentServer.name + "] Passing result back up: " + resultIp);
            return resultIp;
        }
        
        // 3. Domain not found
        System.out.println("[" + currentServer.name + "] NXDOMAIN - Record not found.");
        return null;
    }

    public static void main(String[] args) {
        System.out.println("--- Recursive DNS Setup ---");
        DNSServer root = new DNSServer("RootServer");
        DNSServer tld = new DNSServer("TLDServer(.com)");
        DNSServer auth = new DNSServer("AuthServer(example.com)");
        
        root.delegate(".com", tld);
        tld.delegate("example.com", auth);
        auth.addARecord("www.example.com", "192.168.1.100");
        
        System.out.println("\n--- Starting Recursive Lookup for www.example.com ---");
        // Client only talks to Root (acting as its recursive resolver here)
        String resolvedIp = resolveRecursively(root, "www.example.com");
        
        System.out.println("\nClient received final IP: " + resolvedIp);
    }
}
