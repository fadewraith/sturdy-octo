#include<iostream>
using namespace std;

void swap(int *x, int *y) {
    int temp = *x;
    *x = *y;
    *y = temp;
}

/*
   Function to perform Bubble Sort algorithm on an array of integers.

   Bubble Sort repeatedly compares adjacent elements in the array and swaps them if they are in the wrong order,
   moving the larger elements towards the end of the array with each pass.

   Parameters:
   - A[]: Array of integers to be sorted.
   - n: Size of the array.

   The algorithm optimizes by:
   - Setting a flag to track if any swaps occur in a pass.
   - Exiting early if no swaps are made in a pass, indicating the array is already sorted.

   Complexity:
   - Best Case: O(n) - when the array is already sorted.
   - Average Case: O(n^2)
   - Worst Case: O(n^2)

   Note: Bubble Sort is not efficient for large datasets but is simple to implement and understand.
*/


/*
   Function to perform Bubble Sort algorithm on an array of integers.
*/
void bubble_sort(int A[], int n) {  // Define function taking an array A[] and its size n as parameters.

    int flag = 0;  // Initialize a flag to track whether any swaps occurred during a pass.

    for(int i = 0; i < n - 1; i++) {  // Iterate through the array, excluding the last element.

        flag = 0;  // Reset the flag at the start of each pass.

        for(int j = 0; j < n - 1 - i; j++) {  // Iterate through the unsorted portion of the array.

            if(A[j] > A[j+1]) {  // If the current element is greater than the next element.

                swap(&A[j], &A[j+1]);  // Swap the current element with the next element.

                flag = 1;  // Set the flag to indicate a swap occurred.

            }
        }

        if(flag == 0) break;  // If no swaps occurred in a pass, exit the loop as the array is sorted.
    }
}


/*
   Function to perform Insertion Sort algorithm on an array of integers.

   Time Complexity:
   - Best Case: O(n) - When the array is already sorted.
   - Average Case: O(n^2)
   - Worst Case: O(n^2)

   Adaptability:
   - Insertion Sort is not adaptive as it doesn't take advantage of any pre-existing order in the input data.

   Stability:
   - Insertion Sort is stable as it preserves the relative order of equal elements.
*/
void insertion_sort(int A[], int n) {  // Define function taking an array A[] and its size n as parameters.
    
    int x, j;  // Declare variables for the current element and its previous index.

    for(int i = 1; i < n; i++) {  // Iterate through the array starting from the second element.

        j = i - 1;  // Set j to the previous index.
        x = A[i];  // Store the value of the current element.

        while(j > -1 && A[j] > x) {  // Iterate backwards while the previous value is greater than the current value.

            A[j+1] = A[j];  // Shift the element at index j to the right.
            j--;  // Decrement j to check the previous element.
        }

        A[j+1] = x;  // Insert the current element at its correct position in the sorted sequence.
    }
}

/*
   Function to perform Selection Sort algorithm on an array of integers.

   Time Complexity:
   - Best Case: O(n^2)
   - Average Case: O(n^2)
   - Worst Case: O(n^2)

   Adaptability:
   - Selection Sort is not adaptive as it doesn't take advantage of any pre-existing order in the input data.

   Stability:
   - Selection Sort is not stable as it may change the relative order of equal elements.
*/
void selection_sort(int A[], int n) {  // Define function taking an array A[] and its size n as parameters.
    
    int i, j, k;  // Declare variables for iterations and tracking the minimum element index.

    for(i = 0; i < n - 1; i++) {  // Iterate through the array for each pass.

        for(j = k = i; j < n; j++) {  // Iterate through the unsorted portion of the array for each pass.

            if(A[j] < A[k]) {  // If the current element is smaller than the current minimum.

                k = j;  // Update the index of the current minimum element.
            }
        }

        swap(&A[i], &A[k]);  // Swap the current minimum element with the element at the beginning of the unsorted portion.
    }
}


int main() {
    
    int A[] = { 3, 7, 9, 10, 6, 5, 12, 4, 11, 2 }, n = 10;
    
//    bubble_sort(A, n);
//    insertion_sort(A, n);
    selection_sort(A, n);
    
    for(int i = 0; i < n; i++) {
        cout << A[i];
        if(i < n - 1) cout << ", ";
    }
    cout << endl;
    
    return 0;
}
