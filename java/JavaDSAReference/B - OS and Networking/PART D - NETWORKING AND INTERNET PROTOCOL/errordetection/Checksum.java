package net.errordetection;

public class Checksum {
    // Checksum catches simple single-bit errors or odd numbers of errors.
    // It misses errors that cancel each other out (e.g., bit 1 in word A flips 0->1 and bit 1 in word B flips 1->0).
    
    public static int calculateChecksum(int[] data, int numBits) {
        int sum = 0;
        int wrapLimit = (1 << numBits) - 1;
        
        for (int val : data) {
            sum += val;
            if (sum > wrapLimit) {
                sum = (sum & wrapLimit) + (sum >> numBits);
            }
        }
        return (~sum) & wrapLimit;
    }
    
    public static boolean verifyChecksum(int[] data, int numBits, int checksum) {
        int sum = 0;
        int wrapLimit = (1 << numBits) - 1;
        
        for (int val : data) {
            sum += val;
            if (sum > wrapLimit) {
                sum = (sum & wrapLimit) + (sum >> numBits);
            }
        }
        sum += checksum;
        if (sum > wrapLimit) {
            sum = (sum & wrapLimit) + (sum >> numBits);
        }
        return ((~sum) & wrapLimit) == 0;
    }

    public static void main(String[] args) {
        System.out.println("--- Internet Checksum (16-bit one's complement) ---");
        int[] words = {0x4500, 0x0073, 0x0000, 0x4000, 0x4011, 0xc0a8, 0x0001, 0xc0a8, 0x00c7};
        int checksum = calculateChecksum(words, 16);
        System.out.println("IPv4 header checksum: 0x" + Integer.toHexString(checksum)); // Expected: 0xb861
        System.out.println("Receiver check (no error): " + verifyChecksum(words, 16, checksum)); // true
        words[2] = 0x0001; // corrupt one word
        System.out.println("Receiver check (corrupted word): " + verifyChecksum(words, 16, checksum)); // false
    }
}
