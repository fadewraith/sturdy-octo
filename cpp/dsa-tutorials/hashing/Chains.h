#ifndef Chains_h // Preprocessor directive: if Chains_h is not defined
#define Chains_h // Define Chains_h to prevent multiple inclusions of this header

#include<stdlib.h> // Include the standard library for memory allocation

struct Node { // Define a structure named Node
    int data; // Data field to store an integer value
    struct Node *next; // Pointer to the next Node in the list
};

void sortedInsert(struct Node **H, int x) { // Function definition: Insert a value in sorted order into a linked list
    
    struct Node *t, *q = NULL, *p = *H; // Declare Node pointers: t, q, p

    t = (struct Node *)malloc(sizeof(struct Node)); // Allocate memory for a new Node
    t->data = x; // Set the data of the new Node to the given value
    t->next = NULL; // Set the next pointer of the new Node to NULL
    
    if(*H == NULL) *H = t; // If the list is empty, set the head pointer to the new Node
    else { // Otherwise, insert the new Node in sorted order
        while(p && p->data < x) { // Traverse the list until reaching the correct position
            q = p; // q points to the Node before p
            p = p->next; // Move to the next Node
        }
        if(p == *H) { // If the new Node should be inserted at the beginning of the list
            t->next = *H; // Set the next pointer of the new Node to the current head
            *H = t; // Update the head pointer to the new Node
        } else { // Otherwise, insert the new Node between q and p
            t->next = q->next; // Set the next pointer of the new Node to p
            q->next = t; // Set the next pointer of q to the new Node
        }
    }
}

struct Node *search(struct Node *p, int key) { // Function definition: Search for a key in a linked list
    while(p != NULL) { // Traverse the list until reaching the end
        if(key == p->data) { // If the key is found
            return p; // Return a pointer to the Node containing the key
        }
        p = p->next; // Move to the next Node
    }
    return NULL; // Return NULL if the key is not found
}

#endif // End of preprocessor directive
