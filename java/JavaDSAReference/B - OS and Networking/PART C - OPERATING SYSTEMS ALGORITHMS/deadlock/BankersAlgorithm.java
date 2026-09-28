package os.deadlock;

/**
 * BANKER'S ALGORITHM
 * 
 * WHAT IT IS:
 * A Deadlock Avoidance algorithm. 
 * Before granting a resource request, the OS simulates the allocation state.
 * If the resulting state is "Safe" (there exists a sequence of processes that can 
 * finish without deadlock), the request is granted. Otherwise, it is denied/delayed.
 * 
 * PSEUDOCODE:
 * Need[i,j] = Max[i,j] - Allocation[i,j]
 * Work = Available
 * Finish = [false, ...]
 * loop:
 *   find process P where Finish[P] == false AND Need[P] <= Work
 *   if found:
 *     Work += Allocation[P]
 *     Finish[P] = true
 *   else break
 * if all Finish are true -> SAFE STATE!
 */
public class BankersAlgorithm {

    private int numProcesses;
    private int numResources;
    private int[] available;
    private int[][] max;
    private int[][] allocation;
    private int[][] need;

    public BankersAlgorithm(int np, int nr, int[] avail, int[][] max, int[][] alloc) {
        this.numProcesses = np;
        this.numResources = nr;
        this.available = avail;
        this.max = max;
        this.allocation = alloc;
        this.need = new int[np][nr];
        
        // Calculate Need matrix
        for (int i = 0; i < np; i++) {
            for (int j = 0; j < nr; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }
    }

    public boolean isSafeState() {
        int[] work = new int[numResources];
        System.arraycopy(available, 0, work, 0, numResources);
        
        boolean[] finish = new boolean[numProcesses];
        int[] safeSequence = new int[numProcesses];
        int count = 0;

        while (count < numProcesses) {
            boolean found = false;
            for (int p = 0; p < numProcesses; p++) {
                if (!finish[p]) {
                    boolean canAllocate = true;
                    for (int r = 0; r < numResources; r++) {
                        if (need[p][r] > work[r]) {
                            canAllocate = false;
                            break;
                        }
                    }

                    if (canAllocate) {
                        for (int r = 0; r < numResources; r++) {
                            work[r] += allocation[p][r];
                        }
                        safeSequence[count++] = p;
                        finish[p] = true;
                        found = true;
                    }
                }
            }
            if (!found) {
                System.out.println("System is in an UNSAFE state! Deadlock possible.");
                return false;
            }
        }

        System.out.print("System is in a SAFE state. Safe sequence is: ");
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("P" + safeSequence[i] + (i != numProcesses - 1 ? " -> " : "\n"));
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("--- BANKER'S ALGORITHM DEMO ---");
        int np = 5; // P0 to P4
        int nr = 3; // A, B, C

        int[] available = {3, 3, 2}; // Available instances of A, B, C

        int[][] max = {
            {7, 5, 3}, // P0
            {3, 2, 2}, // P1
            {9, 0, 2}, // P2
            {2, 2, 2}, // P3
            {4, 3, 3}  // P4
        };

        int[][] alloc = {
            {0, 1, 0}, // P0
            {2, 0, 0}, // P1
            {3, 0, 2}, // P2
            {2, 1, 1}, // P3
            {0, 0, 2}  // P4
        };

        BankersAlgorithm ba = new BankersAlgorithm(np, nr, available, max, alloc);
        ba.isSafeState();
    }
}
