package net.tcp;

public class TCPConnectionStateMachine {
    public enum State {
        CLOSED, LISTEN, SYN_SENT, SYN_RECEIVED, ESTABLISHED, FIN_WAIT_1, FIN_WAIT_2, TIME_WAIT, CLOSE_WAIT, LAST_ACK
    }

    private State currentState;

    public TCPConnectionStateMachine() {
        this.currentState = State.CLOSED;
    }

    public void performThreeWayHandshake() {
        System.out.println("Starting 3-way handshake...");
        transitionTo(State.LISTEN);
        System.out.println("Client sends SYN");
        transitionTo(State.SYN_RECEIVED);
        System.out.println("Server sends SYN-ACK");
        System.out.println("Client sends ACK");
        transitionTo(State.ESTABLISHED);
        System.out.println("Connection established.");
    }

    public void performTeardown() {
        if (currentState != State.ESTABLISHED) {
            System.out.println("Connection is not established.");
            return;
        }
        System.out.println("Starting connection teardown...");
        System.out.println("Initiator sends FIN");
        transitionTo(State.FIN_WAIT_1);
        System.out.println("Receiver sends ACK");
        transitionTo(State.FIN_WAIT_2);
        System.out.println("Receiver sends FIN");
        transitionTo(State.CLOSED);
        System.out.println("Connection closed.");
    }

    private void transitionTo(State nextState) {
        System.out.println("State transition: " + currentState + " -> " + nextState);
        currentState = nextState;
    }

    public static void main(String[] args) {
        TCPConnectionStateMachine tcp = new TCPConnectionStateMachine();
        tcp.performThreeWayHandshake();
        tcp.performTeardown();
    }
}
