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

    public static void scan(int[] requests, int head, int diskSize, boolean directionUp) {
        System.out.println("--- SCAN (Elevator) ---");
        List<Integer> reqList = new ArrayList<>();
        for (int r : requests) reqList.add(r);
        
        reqList.add(head); // Add current head to sort around it
        // Add end bounds for SCAN
        reqList.add(0);
        reqList.add(diskSize - 1);
        
        Collections.sort(reqList);
        int headIdx = reqList.indexOf(head);
        
        int totalSeek = 0;
        
        if (directionUp) {
            // Go up to disk size, then down
            totalSeek += Math.abs((diskSize - 1) - head); // Distance to top
            totalSeek += Math.abs((diskSize - 1) - reqList.get(1)); // Distance from top to lowest request
        } else {
            // Go down to 0, then up
            totalSeek += Math.abs(head - 0); // Distance to bottom
            totalSeek += Math.abs(reqList.get(reqList.size() - 2) - 0); // Distance from 0 to highest request
        }
        System.out.println("Total Seek Distance: " + totalSeek);
    }

    public static void cscan(int[] requests, int head, int diskSize) {
        System.out.println("--- C-SCAN (Circular SCAN) ---");
        // Always goes UP, then immediately jumps back to 0 without servicing, then goes UP again.
        List<Integer> reqList = new ArrayList<>();
        for (int r : requests) reqList.add(r);
        reqList.add(head);
        reqList.add(0);
        reqList.add(diskSize - 1);
        
        Collections.sort(reqList);
        int headIdx = reqList.indexOf(head);
        
        int totalSeek = 0;
        
        // Go up to the top
        totalSeek += Math.abs((diskSize - 1) - head);
        // Jump to 0 (cost depends on model, standard assumption includes this massive jump)
        totalSeek += (diskSize - 1); 
        // Go up to highest request before original head
        totalSeek += Math.abs(reqList.get(headIdx - 1) - 0);
        
        System.out.println("Total Seek Distance: " + totalSeek);
    }

    public static void look(int[] requests, int head, boolean directionUp) {
        System.out.println("--- LOOK ---");
        // Like SCAN, but stops at the furthest request instead of going to the very edge of the disk!
        List<Integer> reqList = new ArrayList<>();
        for (int r : requests) reqList.add(r);
        reqList.add(head);
        Collections.sort(reqList);
        
        int headIdx = reqList.indexOf(head);
        int totalSeek = 0;
        
        if (directionUp) {
            totalSeek += Math.abs(reqList.get(reqList.size() - 1) - head);
            totalSeek += Math.abs(reqList.get(reqList.size() - 1) - reqList.get(0));
        } else {
            totalSeek += Math.abs(head - reqList.get(0));
            totalSeek += Math.abs(reqList.get(reqList.size() - 1) - reqList.get(0));
        }
        System.out.println("Total Seek Distance: " + totalSeek);
    }

    public static void clook(int[] requests, int head) {
        System.out.println("--- C-LOOK ---");
        // Like C-SCAN, but stops at furthest requests instead of edges.
        List<Integer> reqList = new ArrayList<>();
        for (int r : requests) reqList.add(r);
        reqList.add(head);
        Collections.sort(reqList);
        
        int headIdx = reqList.indexOf(head);
        int totalSeek = 0;
        
        // Go to highest request
        totalSeek += Math.abs(reqList.get(reqList.size() - 1) - head);
        // Jump to lowest request
        totalSeek += Math.abs(reqList.get(reqList.size() - 1) - reqList.get(0));
        // Go to highest request before head
        if (headIdx > 0) {
            totalSeek += Math.abs(reqList.get(headIdx - 1) - reqList.get(0));
        }
        
        System.out.println("Total Seek Distance: " + totalSeek);
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
    }
}
