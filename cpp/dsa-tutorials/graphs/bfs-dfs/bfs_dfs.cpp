#include<iostream>
#include "Queue.h"  // Include the header file for the queue implementation
using namespace std;

// Performs Breadth-First Search traversal on a graph represented by an adjacency matrix
void BFS(int G[][7], int start, int n) {
    int i = start, j;  // Start vertex for the traversal
    int visited[7] = {0};  // Array to track visited vertices, initialized to 0 (none visited yet)
    
    cout << i << ", ";  // Print the starting vertex
    visited[i] = 1;  // Mark the starting vertex as visited
    enqueue(i);  // Enqueue the starting vertex
    
    while(!isEmpty()) {  // Continue traversal until the queue is empty
        i = dequeue();  // Dequeue a vertex
        for(j = 1; j < n; j++) {  // Iterate through the adjacent vertices
            // Check if there is an edge between the current vertex and vertex j,
            // and if vertex j has not been visited yet
            if(G[i][j] == 1 && visited[j] == 0) {
                cout << j << ", ";  // Print the visited vertex
                visited[j] = 1;  // Mark vertex j as visited
                enqueue(j);  // Enqueue vertex j for further traversal
            }
        }
    }
    cout << endl;  // Print a newline after traversal is complete
}

// Performs Depth-First Search traversal on a graph represented by an adjacency matrix
void DFS(int G[][7], int start, int n) {
    static int visited[7] = {0};  // Static array to track visited vertices across recursive calls
    int j;
    
    if(visited[start] == 0) {  // Check if the current vertex has not been visited
        cout << start << ", ";  // Print the current vertex
        visited[start] = 1;  // Mark the current vertex as visited
        for(j = 1; j < n; j++) {  // Iterate through the adjacent vertices
            // Check if there is an edge between the current vertex and vertex j,
            // and if vertex j has not been visited yet
            if(G[start][j] == 1 && visited[j] == 0) {
                DFS(G, j, n);  // Recursively call DFS for vertex j
            }
        }
    }
}

// Main function
int main() {
    // Adjacency matrix representation of the graph
    int G[7][7] = {{0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 0, 0, 0},
                {0, 1, 0, 0, 1, 0, 0},
                {0, 1, 0, 0, 1, 0, 0},
                {0, 0, 1, 1, 0, 1, 1},
                {0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 1, 0, 0}};
                
    BFS(G, 5, 7);  // Perform BFS traversal starting from vertex 5
    DFS(G, 1, 7);  // Perform DFS traversal starting from vertex 1
    return 0;  // Return 0 to indicate successful execution
}
