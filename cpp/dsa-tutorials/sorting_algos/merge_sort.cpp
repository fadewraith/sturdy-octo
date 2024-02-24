#include<iostream>
using namespace std;

/*
 * Function: merge
 * ----------------------------
 * Merge two sorted subarrays A[low...mid] and A[mid+1...high] into a single sorted subarray A[low...high].
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 */
void merge(int A[], int low, int mid, int high) {
    int i = low, j = mid + 1, k = low;
    int B[100]; // size should be larger than the original array
    
    // Merge elements from A[low...mid] and A[mid+1...high] into B[]
    while(i <= mid && j <= high) {
        if(A[i] < A[j]) {
            B[k++] = A[i++];
        } else {
            B[k++] = A[j++];
        }
    }
    
    // Copy remaining elements from A[low...mid] into B[]
    for(; i <= mid; i++) {
        B[k++] = A[i];
    }
    
    // Copy remaining elements from A[mid+1...high] into B[]
    for(; j <= high; j++) {
        B[k++] = A[j];
    }
    
    // Transfer elements from B[] back to A[]
    for(i = low; i <= high; i++) {
        A[i] = B[i];
    }
}

/*
 * Function: iterative_merge_sort
 * ----------------------------
 * Sorts an array A[] of size n using iterative merge sort algorithm.
 * Time Complexity: O(n log n)
 * Space Complexity: O(n)
 */
void iterative_merge_sort(int A[], int n) {
    int pass, low, high, mid, i;
    
    // Perform merge sort in bottom-up manner with increasing subarray size
    for(pass = 2; pass <= n; pass *= 2) {
        for(i = 0; i + pass - 1 < n; i += pass) {
            low = i; high = i + pass - 1;
            mid = (low + high) / 2;
            merge(A, low, mid, high);
        }
        
        // Merge remaining elements if any
        if(n - i > pass/2) {
            low = i; high = i + pass - 1;
            mid = (low + high) / 2;
            merge(A, low, mid, n - 1);
        }
    }
    
    // Merge remaining elements if array size is odd
    if(pass/2 < n) {
        merge(A, 0, (pass/2) - 1, n - 1);
    }
}

/**
 * Recursive function to perform merge sort on an integer array.
 * 
 * Time Complexity:
 *     - Best Case: O(n log n), when the array is already sorted or nearly sorted.
 *     - Average Case: O(n log n), since the array is recursively divided into halves.
 *     - Worst Case: O(n log n), occurs when the array is reversed or sorted in reverse order.
 * 
 * Space Complexity:
 *     - O(n), where n is the number of elements in the array due to the recursion stack.
 * 
 * Stability:
 *     - Merge sort is stable, meaning it preserves the relative order of equal elements.
 * 
 * @param A: Integer array to be sorted.
 * @param low: Starting index of the subarray to be sorted.
 * @param high: Ending index of the subarray to be sorted.
 */
void recursive_merge_sort(int A[], int low, int high) {
    int mid;
    if (low < high) {
        mid = (low + high) / 2;
        recursive_merge_sort(A, low, mid);
        recursive_merge_sort(A, mid + 1, high);
        merge(A, low, mid, high); // Merges the sorted subarrays A[low..mid] and A[mid+1..high].
    }
}



int main() {
    
    int A[] = { 11, 13, 7, 12, 16, 9, 24, 5, 10, 3, -1 }, n = 11;
    
    // iterative_merge_sort(A, n);
    recursive_merge_sort(A, 0, n - 1);
    
    for(int i = 0; i < n; i++) {
        cout << A[i];
        if(i < n - 1) cout << ", ";
    }
    return 0;
}
