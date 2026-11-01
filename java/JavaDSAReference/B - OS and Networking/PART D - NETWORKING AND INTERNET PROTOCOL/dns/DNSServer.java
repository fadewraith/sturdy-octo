package net.dns;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a DNS Server in the hierarchy (Root, TLD, or Authoritative).
 * Contains A-records (IPs) and NS-records (Delegations).
 */
public class DNSServer {
    public String name;
    public Map<String, String> aRecords = new HashMap<>();
    public Map<String, DNSServer> delegations = new HashMap<>();

    public DNSServer(String name) {
        this.name = name;
    }

    public void addARecord(String domain, String ip) {
        aRecords.put(domain, ip);
    }

    public void delegate(String domainSuffix, DNSServer server) {
        delegations.put(domainSuffix, server);
    }

    // Returns the appropriate delegated server for a given domain, or null
    public DNSServer getDelegation(String domain) {
        for (String suffix : delegations.keySet()) {
            if (domain.endsWith(suffix)) {
                return delegations.get(suffix);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("--- DNS Server hierarchy (helper used by the DNS lookup demos) ---");
        DNSServer root = new DNSServer("root");
        DNSServer com = new DNSServer(".com TLD");
        DNSServer example = new DNSServer("example.com authoritative");
        root.delegate(".com", com);
        com.delegate("example.com", example);
        example.addARecord("www.example.com", "93.184.216.34");

        String domain = "www.example.com";
        DNSServer current = root;
        while (!current.aRecords.containsKey(domain)) {
            DNSServer next = current.getDelegation(domain);
            System.out.println(current.name + " refers us to " + (next == null ? "nobody" : next.name));
            if (next == null) return;
            current = next;
        }
        System.out.println(current.name + " answers: " + domain + " -> " + current.aRecords.get(domain));
    }
}
