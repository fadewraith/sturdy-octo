#ifndef Queue_h
#define Queue_h

#include<iostream>
using namespace std;

struct Node {
    int data;           // Data stored in the node
    struct Node *next;  // Pointer to the next node in the queue
} *front = NULL, *rear = NULL;  // Pointers to the front and rear of the queue

// Adds an element to the end of the queue
// Time complexity: O(1)
void enqueue(int x) {
    struct Node *t;  // Temporary pointer to create a new node
    t = (struct Node *)malloc(sizeof(struct Node));  // Allocate memory for the new node
    if(t == NULL) {
        cout << "Queue is full\n";  // Display error message if memory allocation fails
    } else {
        t->data = x;  // Set the data of the new node
        t->next = NULL;  // Set the next pointer of the new node to NULL
        if(front == NULL) {
            front = rear = t;  // If the queue is empty, set both front and rear to the new node
        } else {
            rear->next = t;  // Otherwise, append the new node to the end of the queue
            rear = t;  // Update the rear pointer to point to the new node
        }
    }
}

// Removes and returns the element at the front of the queue
// Time complexity: O(1)
int dequeue() {
    int x = -1;  // Default value for the case when the queue is empty
    struct Node *t;  // Temporary pointer to hold the front node to be dequeued
    if(front == NULL) {
        cout << "Queue is empty\n";  // Display error message if the queue is empty
    } else {
        x = front->data;  // Retrieve the data from the front node
        t = front;  // Store the front node in a temporary pointer
        front = front->next;  // Move the front pointer to the next node
        free(t);  // Free the memory of the dequeued node
    }
    return x;  // Return the dequeued element
}

// Checks if the queue is empty
// Time complexity: O(1)
int isEmpty() {
    return front == NULL;  // Returns true if the front pointer is NULL (indicating an empty queue)
}

#endif


/*

Depth-First Search (DFS) and Breadth-First Search (BFS) are both algorithms used to traverse or search trees or graphs. However, they have different strategies and characteristics:

1. **Strategy**:
   - DFS explores as far as possible along each branch before backtracking. It traverses one branch of a tree down to its deepest level before moving to explore another branch.
   - BFS explores all neighbor nodes at the present depth before moving on to the nodes at the next depth level.

2. **Order of traversal**:
   - DFS starts from a selected node (often referred to as the root in a tree) and explores as far as possible along each branch before backtracking. It explores nodes depth-wise.
   - BFS starts from a selected node and explores all of its neighbors at the present depth before moving on to the nodes at the next depth level. It explores nodes level by level.

3. **Data structure used**:
   - DFS typically uses a stack data structure to keep track of the nodes to explore.
   - BFS typically uses a queue data structure to keep track of the nodes to explore.

4. **Applications**:
   - DFS is often preferred for topological sorting, solving puzzles with a single solution, and exploring paths that may be deep.
   - BFS is often preferred for finding the shortest path in unweighted graphs, finding the shortest path between two nodes in a graph, and exploring all nodes at a particular depth before moving deeper.

5. **Completeness and Optimality**:
   - DFS may not find the shortest path in a graph since it doesn't guarantee that it explores all possible paths.
   - BFS guarantees to find the shortest path between two nodes in an unweighted graph. If all edges have the same weight, BFS is also optimal for finding the shortest path in weighted graphs.

In summary, DFS explores deeper into a graph before backtracking, while BFS explores breadth-wise, level by level. Their choice depends on the problem requirements and the characteristics of the graph or tree being traversed.

*/