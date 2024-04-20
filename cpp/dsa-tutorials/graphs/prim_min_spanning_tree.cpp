#include<iostream> // Including the input/output stream library.
#define V 8
#define I 32767 // Defining a constant 'I' representing infinity.

using namespace std; // Using the standard namespace.

int cost [V][V] { // Declaring a 2D array 'cost' to represent the graph's adjacency matrix.
            {I, I, I, I, I, I, I, I}, // Initializing with infinity values.
            {I, I, 25, I, I, I, 5, I}, // Assigning edge weights between vertices.
            {I, 25, I, 12, I, I, I, 10},
            {I, I, 12, I, 8, I, I, I},
            {I, I, I, 8, I, 16, I, 14},
            {I, I, I, I, 16, I, 20, 18},
            {I, 5, I, I, I, 20, I, I},
            {I, I, 10, I, 14, 18, I, I},
};

int near[8] = { I, I, I, I, I, I, I, I }; // Initializing 'near' array with infinity values.
int t[2][6]; // Declaring a 2D array 't' to store edges of the minimum spanning tree.

int main() { // Main function where the algorithm begins.

    int i, j, k, u, v, n = 7, min = I; // Initializing variables for iteration and minimum cost.

    for(i = 1; i <= n; i++) { // Looping through vertices to find the minimum edge.
        for(j = i; j <= n; j++) {
            if(cost[i][j] < min) { // If the edge cost is less than current minimum.
                min = cost[i][j]; u = i; v = j; // Update minimum cost and corresponding vertices.
            }
        }
    }
    t[0][0] = u; t[1][0] = v; // Storing the minimum edge in the 't' array.
    near[u] = near[v] = 0; // Updating 'near' array for selected vertices.

    for(i = 1; i <= n; i++) { // Looping through vertices to update 'near' array.
        if(near[i] != 0) {
            if(cost[i][u] < cost[i][v]) {
                near[i] = u; // Updating nearest vertex to 'u'.
            } else {
                near[i] = v; // Updating nearest vertex to 'v'.
            }
        }
    }

    for(i = 1; i < n - 1; i++) { // Looping to select remaining vertices for the tree.
        min = I; // Resetting minimum cost.
        for(j = 1; j <= n; j++) { // Finding the next nearest vertex.
            if(near[j] != 0 && cost[j][near[j]] < min) {
                k = j; // Updating the nearest vertex.
                min = cost[j][near[j]]; // Updating the minimum cost.
            }
        }
        t[0][i] = k; // Storing the selected edge in the 't' array.
        t[1][i] = near[k];
        near[k] = 0; // Updating 'near' array for the selected vertex.

        for(j = 1; j <= n; j++) { // Updating 'near' array for remaining vertices.
            if(near[j] != 0 && cost[j][k] < cost [j][near[j]]) {
                near[j] = k; // Updating nearest vertex.
            }
        }
    }

    for(i = 0; i < n - 1; i++) { // Looping to output the edges of the minimum spanning tree.
        cout << "( " << t[0][i] << ", " << t[1][i] << " )" << endl; // Printing each edge.
    }
}

