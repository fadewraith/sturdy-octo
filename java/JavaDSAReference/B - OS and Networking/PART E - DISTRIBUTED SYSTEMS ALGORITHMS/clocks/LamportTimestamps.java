package distributed.clocks;

public class LamportTimestamps {
    
    static class Process {
        String name;
        int clock;

        public Process(String name) {
            this.name = name;
            this.clock = 0;
        }

        public void localEvent(String eventName) {
            clock++;
            System.out.println(name + " performed local event '" + eventName + "'. Logical clock: " + clock);
        }

        public int sendEvent(String eventName) {
            clock++;
            System.out.println(name + " sending message '" + eventName + "'. Logical clock: " + clock);
            return clock;
        }

        public void receiveEvent(String eventName, int receivedTimestamp) {
            clock = Math.max(clock, receivedTimestamp) + 1;
            System.out.println(name + " received message '" + eventName + "' with timestamp " + receivedTimestamp + ". Logical clock updated to: " + clock);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Lamport Timestamps Simulation ===");
        Process p1 = new Process("P1");
        Process p2 = new Process("P2");
        Process p3 = new Process("P3");

        // P1 performs a local event
        p1.localEvent("A");

        // P1 sends a message to P2
        int ts1 = p1.sendEvent("Msg to P2");
        p2.receiveEvent("Msg from P1", ts1);

        // P2 performs a local event
        p2.localEvent("B");

        // P3 performs a local event independently
        p3.localEvent("C");

        // P2 sends a message to P3
        int ts2 = p2.sendEvent("Msg to P3");
        p3.receiveEvent("Msg from P2", ts2);

        System.out.println("\nFinal Clocks:");
        System.out.println("P1: " + p1.clock);
        System.out.println("P2: " + p2.clock);
        System.out.println("P3: " + p3.clock);
    }
}
