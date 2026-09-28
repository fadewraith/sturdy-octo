package graph.common;

/**
 * State enum used specifically for Directed Graph Cycle Detection 
 * and advanced DFS traversal algorithms.
 */
public enum State {
    UNVISITED,
    VISITING,   // Currently in the recursion stack
    VISITED     // Fully processed and removed from recursion stack
}
