package os.raid;

/**
 * RAID 6 (DOUBLE DISTRIBUTED PARITY)
 * 
 * WHAT IT IS:
 * RAID 6 extends RAID 5 by adding another parity block. 
 * It can survive TWO simultaneous disk failures.
 * 
 * THE MATH:
 * In a real RAID 6 system, Parity P is calculated via standard XOR (like RAID 5).
 * Parity Q is calculated using complex Galois Field (GF) multiplication polynomials.
 * 
 * EDUCATIONAL SIMULATION:
 * To avoid importing an entire GF(2^8) math library, we simulate the logic:
 * P = D1 XOR D2 XOR D3
 * Q = (D1*1) + (D2*2) + (D3*3) (Mock arithmetic representation of the secondary equation)
 */
public class Raid6Simulation {

    public static void simulateRaid6() {
        System.out.println("--- RAID 6 (DOUBLE PARITY) SIMULATION ---");
        
        // 3 Data Disks
        int d1 = 10;
        int d2 = 20;
        int d3 = 30;
        
        // Parity P (Standard XOR)
        int p = d1 ^ d2 ^ d3;
        
        // Parity Q (Mock Secondary Equation: Weighted Sum to ensure mathematical independence)
        int q = (d1 * 1) + (d2 * 2) + (d3 * 3);
        
        System.out.printf("Data Disks: [%d, %d, %d] | Parity P: %d | Parity Q: %d\n", d1, d2, d3, p, q);
        
        System.out.println("\n[!] CRASH! Disk 2 AND Disk 3 have failed simultaneously!");
        System.out.println("RAID 6 can mathematically recover both using a system of two equations!");
        
        // We know: 
        // 1) p = d1 ^ d2 ^ d3
        // 2) q = (d1 * 1) + (d2 * 2) + (d3 * 3)
        // Since D2 and D3 are missing, we solve the algebraic system:
        // From (2): (D2*2) + (D3*3) = q - (d1 * 1)
        
        int missingSum = q - (d1 * 1); // Represents 2*d2 + 3*d3
        int missingXor = p ^ d1;       // Represents d2 ^ d3
        
        System.out.println("We isolate the missing variables using P and Q...");
        System.out.println("D2 ^ D3 = " + missingXor);
        System.out.println("2*D2 + 3*D3 = " + missingSum);
        
        // Brute-force solver for the mock simulation
        int recoveredD2 = -1;
        int recoveredD3 = -1;
        
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if ((i ^ j) == missingXor && ((i * 2) + (j * 3)) == missingSum) {
                    recoveredD2 = i;
                    recoveredD3 = j;
                    break;
                }
            }
        }
        
        System.out.println("\nRecovered Data -> Disk 2: " + recoveredD2 + ", Disk 3: " + recoveredD3);
        System.out.println("RAID 6 successfully survived 2 simultaneous hardware failures!");
    }

    public static void main(String[] args) {
        simulateRaid6();
    }
}
