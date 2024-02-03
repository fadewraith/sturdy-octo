#include<iostream>
using namespace std;

// max heap demo
void insert_heap(int A[], int n) {
    // A is array of integers, n = size of an array
    int i = n, temp;
    temp = A[i]; // storing the last element in temp variable
    // use to move the element at its right pos
    while(i > 1 && temp > A[i/2]) {
        A[i] = A[i/2];
        i = i/2;
    }
    A[i] = temp;
}

int del(int A[], int n) {
    int i, j, x, temp, val;
    val = A[1]; // in heap, root element is deleted
    x = A[n];
    A[1] = A[n]; // storing the last element at the deleted root place
    A[n] = val; // moving deleted(root) element at the empty space
    i = 1; j = i * 2; // i = root, left child = 2*i, right child = 2*i + 1
    while(j <= n - 1) { // n - 1, coz at last element, we store the root(deleted) element
        if(j < n - 1 && A[j+1] > A[j]) j = j + 1; //
        if(A[i] < A[j]) { // swapping, in case current root is less than its left child
            temp = A[i];
            A[i] = A[j];
            A[j] = temp;
            i = j; // next root
            j = 2 * j; // next left child
        } else break;
    }
    return val;
}

int main() {
    
    int H[] = { 0,14,15,5,20,30,8,40 };
    
    for(int i=2;i<=7;i++) {
        insert_heap(H,i);
    }
    
    for(int i = 1; i <= 7; i++) {
        cout << H[i] << " ";
    }
    cout << endl;
    
    for(int i = 7; i > 1; i--) {
        del(H, i);
    }
    
    for(int i = 1; i <= 7; i++) {
        cout << H[i] << " ";
    }
    cout << endl;
    return 0;
}
