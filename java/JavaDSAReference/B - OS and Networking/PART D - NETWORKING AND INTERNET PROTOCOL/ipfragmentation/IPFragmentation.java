package net.ipfragmentation;

import java.util.ArrayList;
import java.util.List;

public class IPFragmentation {
    
    public static class Fragment {
        int id;
        int offset;
        boolean moreFragments;
        String data;

        public Fragment(int id, int offset, boolean moreFragments, String data) {
            this.id = id;
            this.offset = offset;
            this.moreFragments = moreFragments;
            this.data = data;
        }

        @Override
        public String toString() {
            return "Fragment[id=" + id + ", offset=" + offset + ", MF=" + moreFragments + ", data='" + data + "']";
        }
    }

    public List<Fragment> fragmentPacket(int packetId, String data, int mtu) {
        List<Fragment> fragments = new ArrayList<>();
        int headerSize = 20;
        int maxPayload = mtu - headerSize;
        maxPayload = (maxPayload / 8) * 8; // ensure multiple of 8
        
        int offset = 0;
        while (offset < data.length()) {
            int end = Math.min(offset + maxPayload, data.length());
            String chunk = data.substring(offset, end);
            boolean mf = end < data.length();
            fragments.add(new Fragment(packetId, offset, mf, chunk));
            offset += maxPayload;
        }
        
        return fragments;
    }

    public String reassemble(List<Fragment> fragments) {
        fragments.sort((f1, f2) -> Integer.compare(f1.offset, f2.offset));
        StringBuilder sb = new StringBuilder();
        for (Fragment f : fragments) {
            sb.append(f.data);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        IPFragmentation ip = new IPFragmentation();
        String data = "This is a very large payload that needs to be fragmented across the network.";
        int mtu = 36;
        
        System.out.println("Original data: " + data);
        List<Fragment> fragments = ip.fragmentPacket(1234, data, mtu);
        
        System.out.println("Fragments:");
        for (Fragment f : fragments) {
            System.out.println(f);
        }
        
        String reassembled = ip.reassemble(fragments);
        System.out.println("Reassembled: " + reassembled);
    }
}
