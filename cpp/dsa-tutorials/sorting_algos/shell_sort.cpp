#include<iostream>
using namespace std;

/** 
 * Function: shell_sort
 * ---------------------
 * Sorts an array of integers using the Shell Sort algorithm.
 * Shell Sort is an in-place comparison sort algorithm that sorts elements by moving elements at certain intervals (called gaps) towards their correct positions.
 * 
 * Complexity Analysis:
 * - Time Complexity:
 *     - Best Case: O(n log n) when the input array is already sorted.
 *     - Average Case: Depends on the gap sequence used, but often better than O(n^2). or O(n log^2 n)
 *     - Worst Case: O(n^2) when the gap sequence is inefficient or the input array is in reverse order.
 * - Space Complexity: O(1) (in-place algorithm)
 * - Adaptable: Yes, based on gap sequence, it can be primary number also and gap / 2 also
 * - Stable: No
 */

void shell_sort(int A[], int n) {
    int gap, i, j, temp;
    
    // Calculate gap sequence
    for (gap = n / 2; gap >= 1; gap /= 2) {
        
        // Traverse array with a certain gap
        for (i = gap; i < n; i++) {
            temp = A[i]; // Store the current element
            j = i - gap; // Initialize the comparison index
            
            // Shift elements towards their correct positions within the gap
            while (j >= 0 && A[j] > temp) {
                A[j + gap] = A[j];
                j = j - gap;
            }
            
            A[j + gap] = temp; // Place the current element at its correct position in the gap
        }
    }
}


int main() {
    
    int A[] = { 11, 13, 7, 12, 16, 9, 24, 5, 10, 3 }, n = 10;
    
    shell_sort(A, n);
    
    for(int i = 0; i < n; i++) {
        cout << A[i];
        if(i < n - 1) cout << ", ";
    }

    cout << endl;
    return 0;
}