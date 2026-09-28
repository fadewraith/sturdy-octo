package graph;

// Importing ONLY our custom data structures (Zero java.util.* built-ins!)
import array.MyArrayList;
import hashmap.chaining.MyHashMap;
import set.hashset.MyHashSet;
import queue.linkedlistbased.LinkedListQueue;
import stack.linkedlistbased.LinkedListStack;
import graph.common.State;

/**
 * GRAPH (Adjacency List)
 * 
 * What it is:
 * A non-linear data structure consisting of nodes (vertices) and edges that connect them.
 * This implementation uses an Adjacency List, which is heavily preferred for sparse graphs 
 * over an Adjacency Matrix due to immense memory savings.
 * 
 * Approach/Strategy (The Ultimate Custom Codebase Test):
 * We strictly obey the rule of NO Java built-ins. 
 * - The Adjacency List is built using our `MyHashMap` where keys are Vertices and 
 *   values are our `MyArrayList` of neighboring Vertices.
 * - BFS uses our `LinkedListQueue`.
 * - DFS Iterative uses our `LinkedListStack`.
 * - Cycle Detection and Visited tracking use our `MyHashSet` and `MyHashMap`.
 * 
 * Time/Space Complexity (V = Vertices, E = Edges):
 * Operation             | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * addVertex             | O(1) avg        | O(1)             | HashMap put
 * addEdge               | O(1) avg        | O(1)             | ArrayList append
 * BFS / DFS             | O(V + E)        | O(V)             | Traverses all nodes and edges
 * Cycle Detect (Undir)  | O(V + E)        | O(V)             | DFS or BFS based
 * Cycle Detect (Dir)    | O(V + E)        | O(V)             | 3-color DFS state tracking
 * Topological Sort      | O(V + E)        | O(V)             | Kahn's algorithm (In-Degree)
 * 
 * Real-world analogy:
 * A social network (Facebook). Vertices are People. Edges are Friendships (Undirected).
 * Or Twitter: Vertices are People. Edges are "Follows" (Directed).
 */
public class Graph<T> {

    // The Adjacency List: Maps a vertex to a list of its adjacent vertices
    private final MyHashMap<T, MyArrayList<T>> adjList;
    private final boolean isDirected;

    public Graph(boolean isDirected) {
        this.adjList = new MyHashMap<>();
        this.isDirected = isDirected;
    }

    // --------------------------------------------------------
    // 1. CORE GRAPH OPERATIONS
    // --------------------------------------------------------

    public void addVertex(T vertex) {
        if (!adjList.containsKey(vertex)) {
            adjList.put(vertex, new MyArrayList<>());
        }
    }

    public void addEdge(T source, T destination) {
        // Ensure both vertices exist in the graph
        addVertex(source);
        addVertex(destination);

        // Add edge from source to destination
        adjList.get(source).add(destination);

        // If undirected, add the reverse edge as well
        if (!isDirected) {
            adjList.get(destination).add(source);
        }
    }

    public void removeEdge(T source, T destination) {
        if (adjList.containsKey(source)) {
            adjList.get(source).remove(destination); // Note: Our MyArrayList removes by value if we pass the object
        }
        if (!isDirected && adjList.containsKey(destination)) {
            adjList.get(destination).remove(source);
        }
    }

    // --------------------------------------------------------
    // 2. BFS (Breadth-First Search)
    // --------------------------------------------------------

    public void bfs(T startVertex) {
        if (!adjList.containsKey(startVertex)) return;

        System.out.print("BFS from " + startVertex + ": ");
        MyHashSet<T> visited = new MyHashSet<>();
        LinkedListQueue<T> queue = new LinkedListQueue<>();

        visited.add(startVertex);
        queue.enqueue(startVertex);

        while (!queue.isEmpty()) {
            T current = queue.dequeue();
            System.out.print(current + " ");

            MyArrayList<T> neighbors = adjList.get(current);
            for (int i = 0; i < neighbors.size(); i++) {
                T neighbor = neighbors.get(i);
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.enqueue(neighbor);
                }
            }
        }
        System.out.println();
    }

    // --------------------------------------------------------
    // 3. DFS (Depth-First Search) - Iterative & Recursive
    // --------------------------------------------------------

    public void dfsIterative(T startVertex) {
        if (!adjList.containsKey(startVertex)) return;

        System.out.print("DFS (Iterative) from " + startVertex + ": ");
        MyHashSet<T> visited = new MyHashSet<>();
        LinkedListStack<T> stack = new LinkedListStack<>();

        stack.push(startVertex);

        while (!stack.isEmpty()) {
            T current = stack.pop();

            if (!visited.contains(current)) {
                System.out.print(current + " ");
                visited.add(current);

                MyArrayList<T> neighbors = adjList.get(current);
                // Push in reverse order so they are popped in the "correct" left-to-right order
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    T neighbor = neighbors.get(i);
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
        System.out.println();
    }

    public void dfsRecursive(T startVertex) {
        if (!adjList.containsKey(startVertex)) return;
        
        System.out.print("DFS (Recursive) from " + startVertex + ": ");
        MyHashSet<T> visited = new MyHashSet<>();
        dfsRecHelper(startVertex, visited);
        System.out.println();
    }

    private void dfsRecHelper(T current, MyHashSet<T> visited) {
        visited.add(current);
        System.out.print(current + " ");

        MyArrayList<T> neighbors = adjList.get(current);
        for (int i = 0; i < neighbors.size(); i++) {
            T neighbor = neighbors.get(i);
            if (!visited.contains(neighbor)) {
                dfsRecHelper(neighbor, visited);
            }
        }
    }

    // --------------------------------------------------------
    // 4. CYCLE DETECTION (Undirected)
    // --------------------------------------------------------
    // Algorithm: DFS. If we visit a visited node that is NOT our direct parent, there is a cycle.
    
    public boolean detectCycleUndirected() {
        if (isDirected) throw new IllegalStateException("Call detectCycleDirected() for directed graphs.");
        
        MyHashSet<T> visited = new MyHashSet<>();
        MyArrayList<T> allVertices = adjList.keySet();

        // Check all components of a potentially disconnected graph
        for (int i = 0; i < allVertices.size(); i++) {
            T vertex = allVertices.get(i);
            if (!visited.contains(vertex)) {
                if (cycleUndirectedHelper(vertex, visited, null)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean cycleUndirectedHelper(T current, MyHashSet<T> visited, T parent) {
        visited.add(current);
        
        MyArrayList<T> neighbors = adjList.get(current);
        for (int i = 0; i < neighbors.size(); i++) {
            T neighbor = neighbors.get(i);
            
            if (!visited.contains(neighbor)) {
                if (cycleUndirectedHelper(neighbor, visited, current)) {
                    return true;
                }
            } else if (!neighbor.equals(parent)) {
                // If the neighbor is visited AND it is not the parent we just came from -> CYCLE!
                return true;
            }
        }
        return false;
    }

    // --------------------------------------------------------
    // 5. CYCLE DETECTION (Directed)
    // --------------------------------------------------------
    // Algorithm: 3-Color DFS using the State enum. 
    // If we encounter a node currently in the VISITING state (in the current recursion stack), cycle found.

    public boolean detectCycleDirected() {
        if (!isDirected) throw new IllegalStateException("Call detectCycleUndirected() for undirected graphs.");
        
        MyHashMap<T, State> stateMap = new MyHashMap<>();
        MyArrayList<T> allVertices = adjList.keySet();
        
        // Initialize all to UNVISITED
        for (int i = 0; i < allVertices.size(); i++) {
            stateMap.put(allVertices.get(i), State.UNVISITED);
        }

        for (int i = 0; i < allVertices.size(); i++) {
            T vertex = allVertices.get(i);
            if (stateMap.get(vertex) == State.UNVISITED) {
                if (cycleDirectedHelper(vertex, stateMap)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean cycleDirectedHelper(T current, MyHashMap<T, State> stateMap) {
        stateMap.put(current, State.VISITING); // Add to recursion stack
        
        MyArrayList<T> neighbors = adjList.get(current);
        for (int i = 0; i < neighbors.size(); i++) {
            T neighbor = neighbors.get(i);
            State neighborState = stateMap.get(neighbor);
            
            if (neighborState == State.UNVISITED) {
                if (cycleDirectedHelper(neighbor, stateMap)) {
                    return true;
                }
            } else if (neighborState == State.VISITING) {
                // Back-edge detected!
                return true;
            }
        }
        
        stateMap.put(current, State.VISITED); // Remove from recursion stack
        return false;
    }

    // --------------------------------------------------------
    // 6. TOPOLOGICAL SORT (Kahn's Algorithm / BFS-Based)
    // --------------------------------------------------------
    // Only valid for Directed Acyclic Graphs (DAGs).
    // Algorithm: Track in-degrees of all nodes. Enqueue nodes with in-degree 0.
    // Dequeue, add to sorted result, and decrement in-degrees of neighbors. 

    public MyArrayList<T> topologicalSort() {
        if (!isDirected) throw new IllegalStateException("Topological sort only valid for Directed graphs.");
        
        MyHashMap<T, Integer> inDegree = new MyHashMap<>();
        MyArrayList<T> allVertices = adjList.keySet();
        
        // 1. Initialize in-degrees to 0
        for (int i = 0; i < allVertices.size(); i++) {
            inDegree.put(allVertices.get(i), 0);
        }
        
        // 2. Calculate actual in-degrees
        for (int i = 0; i < allVertices.size(); i++) {
            T u = allVertices.get(i);
            MyArrayList<T> neighbors = adjList.get(u);
            for (int j = 0; j < neighbors.size(); j++) {
                T v = neighbors.get(j);
                inDegree.put(v, inDegree.get(v) + 1);
            }
        }
        
        // 3. Queue nodes with in-degree 0
        LinkedListQueue<T> queue = new LinkedListQueue<>();
        for (int i = 0; i < allVertices.size(); i++) {
            T v = allVertices.get(i);
            if (inDegree.get(v) == 0) {
                queue.enqueue(v);
            }
        }
        
        // 4. Process queue
        MyArrayList<T> sortedOrder = new MyArrayList<>();
        int processedCount = 0;
        
        while (!queue.isEmpty()) {
            T current = queue.dequeue();
            sortedOrder.add(current);
            processedCount++;
            
            MyArrayList<T> neighbors = adjList.get(current);
            for (int i = 0; i < neighbors.size(); i++) {
                T neighbor = neighbors.get(i);
                int updatedDegree = inDegree.get(neighbor) - 1;
                inDegree.put(neighbor, updatedDegree);
                
                if (updatedDegree == 0) {
                    queue.enqueue(neighbor);
                }
            }
        }
        
        // 5. If we didn't process all nodes, there is a cycle (not a DAG)
        if (processedCount != allVertices.size()) {
            throw new IllegalStateException("Graph contains a cycle. Topological sort is not possible.");
        }
        
        return sortedOrder;
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- GRAPH DEMO (UNDIRECTED) ---");
        Graph<String> unDirGraph = new Graph<>(false);
        unDirGraph.addEdge("A", "B");
        unDirGraph.addEdge("A", "C");
        unDirGraph.addEdge("B", "D");
        unDirGraph.addEdge("C", "D");
        
        unDirGraph.bfs("A"); // Expected: A B C D
        unDirGraph.dfsRecursive("A"); 
        unDirGraph.dfsIterative("A"); 
        
        System.out.println("Cycle detected (Undirected)? " + unDirGraph.detectCycleUndirected()); // true (A-B-D-C)
        unDirGraph.removeEdge("C", "D");
        System.out.println("Cycle detected after removing C-D? " + unDirGraph.detectCycleUndirected()); // false


        System.out.println("\n--- GRAPH DEMO (DIRECTED & TOPOLOGICAL SORT) ---");
        // DAG for course prerequisites: 
        // Math -> Physics
        // Math -> CS
        // CS -> AI
        // Physics -> AI
        Graph<String> dirGraph = new Graph<>(true);
        dirGraph.addEdge("Math", "Physics");
        dirGraph.addEdge("Math", "CS");
        dirGraph.addEdge("CS", "AI");
        dirGraph.addEdge("Physics", "AI");
        
        System.out.println("Cycle detected (Directed)? " + dirGraph.detectCycleDirected()); // false
        
        System.out.println("Topological Sort (Valid course order): " + dirGraph.topologicalSort().toString()); 
        // Expected: [Math, Physics, CS, AI] or [Math, CS, Physics, AI]
        
        System.out.println("\nAdding cycle: AI -> Math (Student must take AI before Math ?!)");
        dirGraph.addEdge("AI", "Math");
        
        System.out.println("Cycle detected (Directed)? " + dirGraph.detectCycleDirected()); // true
        try {
            dirGraph.topologicalSort();
        } catch (IllegalStateException e) {
            System.out.println("Topological Sort correctly failed: " + e.getMessage());
        }
    }
}
