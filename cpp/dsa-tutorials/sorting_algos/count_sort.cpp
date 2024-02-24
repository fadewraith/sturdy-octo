#include<iostream>
using namespace std;

/*
 * Function: findMax
 * ------------------
 * Finds the maximum element in an integer array.
 * 
 * Parameters:
 * A: Integer array to search.
 * n: Size of the array.
 * 
 * Time Complexity: O(n) - traverses the entire array once.
 * Space Complexity: O(1) - uses constant space for variables.
 * Adaptable: No - the algorithm's time complexity does not change based on input.
 * Stable: N/A - stability is not applicable for finding the maximum element.
 */
int findMax(int A[], int n) {
    int max = INT32_MIN; // Initialize max to the smallest possible integer value.
    for(int i = 0; i < n; i++) { // Iterate through the array.
        if(A[i] > max) { // If the current element is greater than max.
            max = A[i]; // Update max.
        }
    }
    return max; // Return the maximum element.
}

/*
 * Function: count_sort
 * ---------------------
 * Performs counting sort on an integer array.
 * 
 * Parameters:
 * A: Integer array to be sorted.
 * n: Size of the array.
 * 
 * Time Complexity: O(n + k) - n for iterating through the array, k for initializing countArr.
 * Space Complexity: O(k) - uses additional space for the countArr.
 * Adaptable: No - the algorithm's time complexity does not change based on input.
 * Stable: Yes - counting sort is a stable sorting algorithm.
 */
void count_sort(int A[], int n) {
    int i, j, max, *countArr;
    max = findMax(A, n); // Find the maximum element in the array.
    countArr = (int *)malloc(sizeof(int)*(max+1)); // Allocate memory for countArr based on the range of elements.
    for(i = 0; i < max + 1; i++) { // Initialize countArr elements to 0.
        countArr[i] = 0;
    }
    for(i = 0; i < n; i++) { // Count occurrences of each element in the array.
        countArr[A[i]]++;
    }
    i = 0; j = 0;
    while(j < max + 1) { // Reconstruct the array based on counts stored in countArr.
        if(countArr[j] > 0) {
            A[i++] = j;
            countArr[j]--;
        } else {
            j++;
        }
    }
}


int main() {
    
    int A[] = { 11, 13, 7, 12, 16, 9, 24, 5, 10, 3 }, n = 10;
    
    count_sort(A, n);
    
    for(int i = 0; i < n; i++) {
        cout << A[i];
        if(i < n - 1) cout << ", ";
    }
    return 0;
}