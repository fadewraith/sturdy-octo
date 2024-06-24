#include<iostream>
using namespace std;

struct Node {
    struct Node *prev;
    int data;
    struct Node *next;
} *first = NULL;

void create(int A[], int n) {
    
    struct Node *t, *last;
    
    first = (struct Node *)malloc(sizeof(struct Node));
    // first = new Node[sizeof(struct Node)];
    first->data = A[0];
    first->prev = first->next = NULL;
    last = first;
    
    for(int i = 1; i < n; i++) {
        t = (struct Node *)malloc(sizeof(struct Node));
        // t = new Node[sizeof(struct Node)];
        t->data = A[i];
        t->next = last->next;
        t->prev = last;
        last->next = t;
        last = t;
    }
    
    
}

void display(struct Node *p) {
    while(p) {
        cout << p->data << ", ";
        p = p->next;
    }
    cout << endl;
}

int length(struct Node *p) {
    int count = 0;
    
    while(p) {
        count++;
        p = p->next;
    }
    
    return count;
}

void insert(struct Node *p, int index, int x) {
    
    struct Node *t;
    
    
    if(index < 0 || index > length(p)) return;
    
    if(index == 0) { // special case, we have to insert before the first node
        t = (struct Node *)malloc(sizeof(struct Node));
        // t = new Node[sizeof(struct Node)];
        t->data = x;
        t->prev = NULL;
        t->next = first;
        first->prev = t;
        first = t;
    } else {
        for(int i = 0; i < index - 1; i++) {
            p = p->next;
        }
        t = (struct Node *)malloc(sizeof(struct Node));
        // t = new Node[sizeof(struct Node)];
        t->data = x;
        
        t->prev = p;
        t->next = p->next;
        if(p->next) {
            p->next->prev = t;
        }
        
        p->next = t;
        
        
    }
}

int del(struct Node *p, int index) {
    
    struct Node *t; int x = -1;
    
    if(index < 1 || index > length(p)) return -1;
    
    if(index == 1) {
        
        first = first->next;
        
        if(first) first->prev = NULL;
        
        x = p->data;
        free(p);
        
    } else {
        for(int i = 0; i < index - 1; i++) {
            p = p->next;
        }
        
        p->prev->next = p->next;
        
        if(p->next) p->next->prev = p->prev;
        
        x = p->data;
        free(p);
    }
    return x;
    
}

void reverse(struct Node *p) {
    
    struct Node *t;
    
    while(p != NULL) {
        // changing links
        t = p->next;
        p->next = p->prev;
        p->prev = t;
        p = p->prev; // previous will take it to the next node
        // checking is p is not NULL
        if(p != NULL && p->next == NULL) first = p; // if p's next is NULL, then its the last node and first should point on p
    }
}


int main() {
    int A[] = { 10, 20, 30, 40, 50 };
    create(A, 5);
    // insert(first, -7, 15);
    cout << "length is: " << length(first) << endl;
    display(first);
    reverse(first);
    // cout << del(first, 1) << endl;
    display(first);
    return 0;
}
