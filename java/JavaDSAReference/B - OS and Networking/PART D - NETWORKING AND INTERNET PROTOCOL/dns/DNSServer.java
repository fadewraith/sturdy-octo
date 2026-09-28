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
}
