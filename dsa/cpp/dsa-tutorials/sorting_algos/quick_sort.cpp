#include<iostream>
using namespace std;

// Function to swap the values of two integers using pointers.
void swap(int *x, int *y) {
    int temp = *x;
    *x = *y;  
    *y = temp;
}

// Function to partition the array around a pivot element and return the index of the pivot.
int partition(int A[], int l, int h) {
    
    int pivot = A[l];  // Choose the first element as the pivot.
    int i = l, j = h;  // Initialize two pointers i and j.
    
    // Repeat until pointers i and j cross each other.
    do {
        // Move the pointer i to the right until an element greater than the pivot is found.
        do {
            i++;
        } while (A[i] <= pivot);
        
        // Move the pointer j to the left until an element less than or equal to the pivot is found.
        do {
            j--;
        } while (A[j] > pivot);
        
        // Swap the elements at positions i and j if i is less than j.
        if (i < j) swap(&A[i], &A[j]);

    } while (i < j);  // Repeat until pointers i and j cross each other.
    
    // Swap the pivot element with the element at index j (partitioning step).
    swap (&A[l], &A[j]);
    
    // Return the index of the pivot element.
    return j;
}

// Function to recursively sort the array using the Quick Sort algorithm.
void quick_sort(int A[], int l, int h) {
    int j;  // Declare a variable to store the index of the pivot element.
    
    // Check if the lower bound is less than the higher bound.
    if (l < h) {
        // Perform partitioning and obtain the index of the pivot element.
        j = partition(A, l, h);
        
        // Recursively sort the left subarray.
        quick_sort(A, l, j);
        
        // Recursively sort the right subarray.
        quick_sort(A, j+1, h);
    }
}


int main() {
    
    int A[] = { 11, 13, 7, 12, 16, 9, 24, 5, 10, 3 }, n = 10;
    
    quick_sort(A, 0, n);
    
    for(int i=0; i < n; i++) {
        cout << A[i];  
        if(i < n - 1) cout << ", ";
    }
    cout << endl;
    
    return 0;
}

