package net.arp;

import java.util.HashMap;
import java.util.Map;

public class ARPSimulation {
    private Map<String, String> network;
    private Map<String, String> arpCache;

    public ARPSimulation() {
        this.network = new HashMap<>();
        this.arpCache = new HashMap<>();
    }

    public void addDevice(String ip, String mac) {
        network.put(ip, mac);
    }

    public String resolve(String ip) {
        if (arpCache.containsKey(ip)) {
            System.out.println("ARP Cache Hit for IP: " + ip + " -> MAC: " + arpCache.get(ip));
            return arpCache.get(ip);
        }

        System.out.println("ARP Cache Miss for IP: " + ip + ". Broadcasting ARP Request...");
        String mac = network.get(ip);

        if (mac != null) {
            System.out.println("ARP Reply received. IP: " + ip + " is at MAC: " + mac);
            arpCache.put(ip, mac);
            return mac;
        } else {
            System.out.println("ARP Request failed. Host unreachable.");
            return null;
        }
    }

    public void displayCache() {
        System.out.println("Current ARP Cache: " + arpCache);
    }
    
    public static void main(String[] args) {
        ARPSimulation arp = new ARPSimulation();
        arp.addDevice("192.168.1.1", "00:1A:2B:3C:4D:5E");
        arp.addDevice("192.168.1.2", "00:1A:2B:3C:4D:5F");
        
        arp.resolve("192.168.1.1");
        arp.resolve("192.168.1.1");
        arp.resolve("192.168.1.3");
        arp.displayCache();
    }
}
