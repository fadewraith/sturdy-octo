package net.errordetection;

public class CRC {
    // CRC catches all single-bit errors, double-bit errors, odd numbers of errors, and burst errors of length <= polynomial length.
    // It is mathematically robust based on polynomial division over GF(2).
    // It only misses extremely rare specific bit flips that happen to be exact multiples of the generator polynomial.

    public static String calculateCRC(String data, String generator) {
        int dataLen = data.length();
        int genLen = generator.length();
        
        String appendedData = data + "0".repeat(genLen - 1);
        char[] remainder = appendedData.toCharArray();
        char[] genChars = generator.toCharArray();
        
        for (int i = 0; i <= appendedData.length() - genLen; i++) {
            if (remainder[i] == '1') {
                for (int j = 0; j < genLen; j++) {
                    remainder[i + j] = remainder[i + j] == genChars[j] ? '0' : '1';
                }
            }
        }
        return new String(remainder).substring(appendedData.length() - genLen + 1);
    }
    
    public static boolean verifyCRC(String data, String crc, String generator) {
        String appendedData = data + crc;
        char[] remainder = appendedData.toCharArray();
        char[] genChars = generator.toCharArray();
        
        for (int i = 0; i <= appendedData.length() - generator.length(); i++) {
            if (remainder[i] == '1') {
                for (int j = 0; j < generator.length(); j++) {
                    remainder[i + j] = remainder[i + j] == genChars[j] ? '0' : '1';
                }
            }
        }
        for (int i = appendedData.length() - generator.length() + 1; i < appendedData.length(); i++) {
            if (remainder[i] != '0') return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("--- CRC (Cyclic Redundancy Check) ---");
        String data = "11010011101100";
        String generator = "1011"; // x^3 + x + 1
        String crc = calculateCRC(data, generator);
        System.out.println("Data: " + data + ", generator: " + generator + " -> CRC bits: " + crc); // Expected: 100
        System.out.println("Receiver check (no error): " + verifyCRC(data, crc, generator)); // true

        // Flip one bit in transit
        char[] corrupted = data.toCharArray();
        corrupted[3] = corrupted[3] == '0' ? '1' : '0';
        System.out.println("Receiver check (1 bit flipped): " + verifyCRC(new String(corrupted), crc, generator)); // false
    }
}
