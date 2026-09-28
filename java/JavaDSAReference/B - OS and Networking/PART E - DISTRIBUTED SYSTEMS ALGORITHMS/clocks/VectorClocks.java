package distributed.clocks;

import java.util.Arrays;

public class VectorClocks {

    static class Process {
        String name;
        int processId;
        int[] vector;

        public Process(String name, int processId, int numProcesses) {
            this.name = name;
            this.processId = processId;
            this.vector = new int[numProcesses];
        }

        public void localEvent(String eventName) {
            vector[processId]++;
            System.out.println(name + " performed local event '" + eventName + "'. Vector: " + Arrays.toString(vector));
        }

        public int[] sendEvent(String eventName) {
            vector[processId]++;
            System.out.println(name + " sending message '" + eventName + "'. Vector: " + Arrays.toString(vector));
            return Arrays.copyOf(vector, vector.length);
        }

        public void receiveEvent(String eventName, int[] receivedVector) {
            vector[processId]++;
            for (int i = 0; i < vector.length; i++) {
                vector[i] = Math.max(vector[i], receivedVector[i]);
            }
            System.out.println(name + " received message '" + eventName + "' with vector " + Arrays.toString(receivedVector) + ". Vector updated to: " + Arrays.toString(vector));
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Vector Clocks Simulation ===");
        int numProcesses = 3;
        Process p0 = new Process("P0", 0, numProcesses);
        Process p1 = new Process("P1", 1, numProcesses);
        Process p2 = new Process("P2", 2, numProcesses);

        // P0 local event
        p0.localEvent("A");

        // P0 sends to P1
        int[] vecMsg1 = p0.sendEvent("M1");
        p1.receiveEvent("M1 from P0", vecMsg1);

        // P1 local event
        p1.localEvent("B");

        // P2 local event (concurrent with P0 and P1)
        p2.localEvent("C");

        // P2 sends to P1
        int[] vecMsg2 = p2.sendEvent("M2");
        p1.receiveEvent("M2 from P2", vecMsg2);

        // P1 sends to P0
        int[] vecMsg3 = p1.sendEvent("M3");
        p0.receiveEvent("M3 from P1", vecMsg3);

        System.out.println("\nFinal Vectors:");
        System.out.println("P0: " + Arrays.toString(p0.vector));
        System.out.println("P1: " + Arrays.toString(p1.vector));
        System.out.println("P2: " + Arrays.toString(p2.vector));
    }
}
