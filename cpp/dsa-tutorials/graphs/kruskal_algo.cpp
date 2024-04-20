// commented ones's have bugs
#include<iostream>

#define I 32767 // Infinity
#define V 7 // # of vertices in Graph
#define E 9 // # of edges in Graph

using namespace std;

int edges[3][9] = {
    { 1, 1, 2, 2, 3, 4, 4, 5, 5 },
    { 2, 6, 3, 7, 4, 5, 7, 6, 7 },
    { 25, 5, 12, 10, 8, 16, 14, 20, 18 }
};

int custom_set[8] = { -1, -1, -1, -1, -1, -1, -1, -1 };
int included[9] = { 0 };
int t[2][V - 1]; // Adjusted array size to hold V-1 edges

// int edges[3][9] = {
//     { 1, 1,  2,  2, 3,  4,  4,  5,  5 },
//     { 2, 6,  3,  7, 4,  5,  7,  6,  7 },
//     { 25, 5, 12, 10, 8, 16, 14, 20, 18 }
// };

// int custom_set[8] = { -1, -1, -1, 1, -1, -1, -1, -1 };
// int included[9] = { 0 };
// int t[2][6];


// void custom_union(int u, int v) {
//     if(custom_set[u] < custom_set[v]) {
//         custom_set[u] += custom_set[v];
//         custom_set[v] = u;
//     } else {
//         custom_set[v] += custom_set[u];
//         custom_set[u] = v;
//     }
// }

// recursive method, this method has no bug
int custom_find(int u) {
    if (custom_set[u] < 0)
        return u;
    return custom_set[u] = custom_find(custom_set[u]);
}

// no bug
// int custom_find(int u) {
//     int x = u, v = 0;
//     while(custom_set[x] > 0) {
//         x = custom_set[x];
//     }
    
//     while(u != x) {
//         v = custom_set[u];
//         custom_set[u] = x;
//         u = v;
//     }
    
//     return x;
// }

void custom_union(int u, int v) {
    if (custom_set[u] < custom_set[v]) {
        custom_set[u] += custom_set[v];
        custom_set[v] = u;
    } else {
        custom_set[v] += custom_set[u];
        custom_set[u] = v;
    }
}

int main() {
    int i = 0, j, k, u, v, min, e = 9;
    // int i = 0, j, k, u, v, min = I, n = 7, e = 9; // n = vertices, e = edges

    while (i < V - 1) { // Check for expected number of edges
        min = I;
        k = -1; // Initialize k
        for (j = 0; j < e; j++) {
            if (included[j] == 0 && edges[2][j] < min) {
                min = edges[2][j];
                u = edges[0][j];
                v = edges[1][j];
                k = j;
            }
        }

        if (k == -1)
            break; // No more edges available

        if (custom_find(u) != custom_find(v)) {
            t[0][i] = u;
            t[1][i] = v;
            custom_union(custom_find(u), custom_find(v));
            i++;
        }

        included[k] = 1;
    }

    for (i = 0; i < V - 1; i++) {
        cout << "(" << t[0][i] << ", " << t[1][i] << ")" << endl;
    }
    
    // while(i < n - 1) {
    //     min = I; k = -1;
    //     for(j = 0; j < e; j++) {
    //         if(included[j] == 0 && edges[2][j] < min) {
    //             min = edges[2][j];
    //             u = edges[0][j];
    //             v = edges[1][j];
    //             k = j;
    //         }
    //     }
        
    //      if (k == -1)
    //         break; // No more edges available
        
    //     if(custom_find(u) != custom_find(v)) {
    //         t[0][i] = u;
    //         t[1][i] = v;
    //         custom_union(custom_find(u), custom_find(v));
    //         i++;
    //     }
        
    //     included[k] = 1;
        
    // }
    
    // for(i = 0; i < n - 1; i++) {
    //     cout << "(" << t[0][i] << ", " << t[1][i] <<  ")" << endl;
    // }

    return 0;
}
