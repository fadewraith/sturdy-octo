package os.diskscheduling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * DISK SCHEDULING ALGORITHMS
 * 
 * WHAT IT IS:
 * OS algorithms to schedule I/O requests to the disk. The goal is to minimize 
 * the total "Seek Time" (the distance the mechanical disk head has to move).
 */
public class DiskScheduling {

    public static void fcfs(int[] requests, int head) {
        System.out.println("--- FCFS ---");
        int totalSeek = 0;
        for (int req : requests) {
            totalSeek += Math.abs(req - head);
            head = req;
        }
        System.out.println("Total Seek Distance: " + totalSeek);
    }

    public static void sstf(int[] requests, int head) {
        System.out.println("--- SSTF (Shortest Seek Time First) ---");
        boolean[] visited = new boolean[requests.length];
        int totalSeek = 0;
        int completed = 0;

        while (completed < requests.length) {
            int minDiff = Integer.MAX_VALUE;
            int nextIdx = -1;

            for (int i = 0; i < requests.length; i++) {
                if (!visited[i]) {
                    int diff = Math.abs(requests[i] - head);
                    if (diff < minDiff) {
                        minDiff = diff;
                        nextIdx = i;
                    }
                }
            }
            visited[nextIdx] = true;
            totalSeek += minDiff;
            head = requests[nextIdx];
            completed++;
        }
        System.out.println("Total Seek Distance: " + totalSeek);
    }

    // --------------------------------------------------------
    // SCAN-family helpers. These SIMULATE the head movement step by step,
    // so edge cases (all requests on one side of the head, request at the
    // head position, etc.) are handled correctly.
    // --------------------------------------------------------

    // Requests at or above the head, ascending
    private static List<Integer> upper(int[] requests, int head) {
        List<Integer> list = new ArrayList<>();
        for (int r : requests) if (r >= head) list.add(r);
        Collections.sort(list);
        return list;
    }

    // Requests below the head, ascending
    private static List<Integer> lower(int[] requests, int head) {
        List<Integer> list = new ArrayList<>();
        for (int r : requests) if (r < head) list.add(r);
        Collections.sort(list);
        return list;
    }

    private static void printResult(List<Integer> path, int totalSeek) {
        System.out.println("Head path: " + path);
        System.out.println("Total Seek Distance: " + totalSeek);
    }

    /**
     * SCAN (Elevator): move in one direction serving requests, go all the way
     * to the END of the disk, then reverse and serve the rest.
     */
    public static void scan(int[] requests, int head, int diskSize, boolean directionUp) {
        System.out.println("--- SCAN (Elevator) ---");
        List<Integer> up = upper(requests, head), down = lower(requests, head);
        Collections.reverse(down); // served from high to low when moving down
        List<Integer> path = new ArrayList<>();
        path.add(head);
        int pos = head, totalSeek = 0;

        // up is ascending, down is descending - exactly the order they are met
        List<Integer> first = directionUp ? up : down;
        List<Integer> second = directionUp ? down : up;

        for (int r : first) { totalSeek += Math.abs(r - pos); pos = r; path.add(r); }
        // Textbook SCAN always travels to the disk edge before reversing
        // (that is the difference from LOOK, which turns at the last request).
        int edge = directionUp ? diskSize - 1 : 0;
        if (pos != edge) { totalSeek += Math.abs(edge - pos); pos = edge; path.add(edge); }
        for (int r : second) { totalSeek += Math.abs(r - pos); pos = r; path.add(r); }
        printResult(path, totalSeek);
    }

    /**
     * C-SCAN: move UP serving requests, go to the end, JUMP back to cylinder 0
     * (the jump is counted as head movement here), then continue upward.
     */
    public static void cscan(int[] requests, int head, int diskSize) {
        System.out.println("--- C-SCAN (Circular SCAN) ---");
        List<Integer> up = upper(requests, head), down = lower(requests, head);
        List<Integer> path = new ArrayList<>();
        path.add(head);
        int pos = head, totalSeek = 0;

        for (int r : up) { totalSeek += Math.abs(r - pos); pos = r; path.add(r); }
        if (pos != diskSize - 1) {                                                         // always to the end
            totalSeek += Math.abs((diskSize - 1) - pos); pos = diskSize - 1; path.add(pos);
        }
        if (!down.isEmpty()) {
            totalSeek += diskSize - 1; pos = 0; path.add(0);                               // jump to start
            for (int r : down) { totalSeek += Math.abs(r - pos); pos = r; path.add(r); }
        }
        printResult(path, totalSeek);
    }

    /**
     * LOOK: like SCAN, but only goes as far as the last REQUEST in each
     * direction (not to the physical end of the disk).
     */
    public static void look(int[] requests, int head, boolean directionUp) {
        System.out.println("--- LOOK ---");
        List<Integer> up = upper(requests, head), down = lower(requests, head);
        Collections.reverse(down);
        List<Integer> first = directionUp ? up : down;
        List<Integer> second = directionUp ? down : up;
        List<Integer> path = new ArrayList<>();
        path.add(head);
        int pos = head, totalSeek = 0;

        for (int r : first) { totalSeek += Math.abs(r - pos); pos = r; path.add(r); }
        for (int r : second) { totalSeek += Math.abs(r - pos); pos = r; path.add(r); }
        printResult(path, totalSeek);
    }

    /**
     * C-LOOK: move UP to the last request, jump to the LOWEST pending request
     * (jump counted as head movement), then continue upward.
     */
    public static void clook(int[] requests, int head) {
        System.out.println("--- C-LOOK ---");
        List<Integer> up = upper(requests, head), down = lower(requests, head);
        List<Integer> path = new ArrayList<>();
        path.add(head);
        int pos = head, totalSeek = 0;

        for (int r : up) { totalSeek += Math.abs(r - pos); pos = r; path.add(r); }
        for (int r : down) { totalSeek += Math.abs(r - pos); pos = r; path.add(r); }
        printResult(path, totalSeek);
    }

    public static void main(String[] args) {
        int[] requests = {82, 170, 43, 140, 24, 16, 190};
        int head = 50;
        int diskSize = 200; // 0 to 199

        fcfs(requests, head);
        sstf(requests, head);
        scan(requests, head, diskSize, true);
        cscan(requests, head, diskSize);
        look(requests, head, true);
        clook(requests, head);

        // Edge case: every request is above the head -> no reversal needed
        System.out.println("\n=== All requests above head (10) ===");
        int[] allAbove = {40, 20, 90};
        scan(allAbove, 10, diskSize, true); // 10 -> 20 -> 40 -> 90 -> 199 = 189 (goes to the edge)
        look(allAbove, 10, true);           // 10 -> 20 -> 40 -> 90 = 80 (stops at last request)
    }
}
