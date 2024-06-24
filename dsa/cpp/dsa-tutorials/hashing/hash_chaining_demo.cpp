#include<iostream>
#include "Chains.h"
using namespace std;

int calculateHash(int key) {
    return key % 10; // Calculate the hash value by taking the modulo of the key with 10
}

void insert(struct Node *H[], int key) {
    int index = calculateHash(key); // Calculate the index in the hash table using the hash function
    sortedInsert(&H[index], key); // Insert the key into the linked list at the calculated index in the hash table
}


int main() {
    
    struct Node *HT[10];
    struct Node *temp;
    int i;
    
    for(i = 0; i < 10; i++) {
        HT[i] = NULL;
    }
    
    insert(HT, 12);
    insert(HT, 22);
    insert(HT, 42);
    
    // temp = search(HT[calculateHash(22)], 22);
    temp = search(HT[calculateHash(21)], 21);
    cout << temp->data;
    return 0;
}