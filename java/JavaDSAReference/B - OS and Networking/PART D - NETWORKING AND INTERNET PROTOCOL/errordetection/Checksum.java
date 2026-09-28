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
}
