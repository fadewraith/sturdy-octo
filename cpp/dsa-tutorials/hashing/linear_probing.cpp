// Simple hash table implementation in C++

#include <iostream>
#define SIZE 10
using namespace std;

// Function to calculate hash index
int calcHash(int key) {
    return key % SIZE; // Returning the hash index
}

// Linear probing to handle collisions
int probe(int H[], int key) {
    int index = calcHash(key); // Calculating the hash index for the given key
    int i = 0;
    // for wuadratic probing, use H[(index + i * i) % SIZE], wherever you're using i along with index, use i * i and it will become a wuadratic probing
    while (H[(index + i) % SIZE] != 0) // Looping until an empty slot is found in the hash table
        i++; // Incrementing the counter
    return (index + i) % SIZE; // Returning the index of the empty slot
}

// Insertion into the hash table
void Insert(int H[], int key) {
    int index = calcHash(key); // Calculating the hash index for the given key
    if (H[index] != 0) // Checking if the slot is already occupied
        index = probe(H, key); // Handling collision by linear probing
    H[index] = key; // Storing the key in the hash table at the calculated index
}

// Search function to find key in hash table
int Search(int H[], int key) {
    int index = calcHash(key); // Calculating the hash index for the given key
    int i = 0;
    while (H[(index + i) % SIZE] != key) // Looping until the key is found in the hash table
        i++;
    return (index + i) % SIZE; // Returning the index of the found key
}

// Display function to show elements stored at each index
void display(int H[]) {
    cout << "Hash Table Contents:" << endl;
    for (int i = 0; i < SIZE; i++) {
        if (H[i] != 0) {
            cout << "Index " << i << ": " << H[i] << endl;
        } else {
            cout << "Index " << i << ": Empty" << endl;
        }
    }
    cout << endl;
}

// display fn with edge case - 
// Display function to show elements stored at each index
void display(int H[]) { // Function to display elements stored at each index in the hash table
    cout << "Hash Table Contents:" << endl; // Outputting a header to indicate the beginning of hash table contents
    
    bool isEmpty = true; // Flag to track if the hash table is empty
    for (int i = 0; i < SIZE; i++) { // Looping through each index of the hash table
        if (H[i] != 0) { // Checking if the element at the current index is not equal to 0 (i.e., it's not empty)
            cout << "Index " << i << ": " << H[i] << endl; // Outputting the index and its corresponding element
            isEmpty = false; // Updating the flag to indicate that the hash table is not empty
        }
    }
    
    if (isEmpty) { // Checking if the hash table is empty
        cout << "Hash Table is empty." << endl; // Outputting a message indicating that the hash table is empty
    }
}




int main() {
    int HT[SIZE] = { 0 };
    int key = 35;
    Insert(HT, 12);
    Insert(HT, 25);
    Insert(HT, 35);
    Insert(HT, 26);
    display(HT); // Call to display function
    cout << "Key: " << key << ", found at, index: " << Search(HT, key);
    return 0;
}
