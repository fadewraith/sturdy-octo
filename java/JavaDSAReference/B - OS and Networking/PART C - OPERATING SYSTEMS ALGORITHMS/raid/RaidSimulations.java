package os.raid;

/**
 * RAID ALGORITHMS (0, 1, 5, 6)
 * 
 * RAID 0 (Striping): Splits data across disks for speed. NO fault tolerance.
 * RAID 1 (Mirroring): Duplicates data. High fault tolerance, 50% capacity waste.
 * RAID 5 (Striping + Parity): Distributes parity using XOR. Can survive 1 disk loss.
 * RAID 6 (Double Parity): Can survive 2 disk losses.
 */
public class RaidSimulations {

    public static void raid0(int[] data) {
        System.out.println("--- RAID 0 (STRIPING) ---");
        System.out.print("Disk 1: ");
        for (int i = 0; i < data.length; i += 2) System.out.print(data[i] + " ");
        System.out.print("\nDisk 2: ");
        for (int i = 1; i < data.length; i += 2) System.out.print(data[i] + " ");
        System.out.println("\n(Zero fault tolerance. If Disk 1 dies, half the data is lost forever).");
    }

    public static void raid1(int[] data) {
        System.out.println("\n--- RAID 1 (MIRRORING) ---");
        System.out.print("Disk 1: ");
        for (int d : data) System.out.print(d + " ");
        System.out.print("\nDisk 2: ");
        for (int d : data) System.out.print(d + " ");
        System.out.println("\n(Perfect fault tolerance. Wasteful on storage).");
    }

    public static void raid5(int data1, int data2) {
        System.out.println("\n--- RAID 5 (DISTRIBUTED PARITY VIA XOR) ---");
        // Simulate writing to 3 disks (2 data, 1 parity)
        int disk1 = data1;
        int disk2 = data2;
        int parityDisk = disk1 ^ disk2; // XOR parity
        
        System.out.printf("Disk 1: %d, Disk 2: %d, Parity Disk: %d\n", disk1, disk2, parityDisk);
        
        // Simulate Disk 1 crashing!
        System.out.println("CRASH! Disk 1 destroyed.");
        // Recover Disk 1 using XOR on the remaining disks!
        int recoveredDisk1 = disk2 ^ parityDisk;
        System.out.println("Recovered Disk 1 Data: " + recoveredDisk1); // Will perfectly match data1
    }

    public static void raid6() {
        System.out.println("\n--- RAID 6 (DOUBLE PARITY) ---");
        System.out.println("Uses advanced Galois Field mathematics to compute two separate parity blocks (P and Q).");
        System.out.println("Allows the array to survive TWO simultaneous disk failures!");
    }

    public static void main(String[] args) {
        int[] data = {1, 2, 3, 4, 5, 6};
        raid0(data);
        raid1(data);
        
        // XOR demo for RAID 5
        raid5(42, 99); 
        
        raid6();
    }
}
