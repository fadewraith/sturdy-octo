#include<iostream>
using namespace std;

struct Node {
    int data;
    struct Node *next;
} *HEAD;

void create(int A[], int n) {
    int i;
    struct Node *t, *last; // temporary pointer for creating a Node, last will help in inserting node @ last
    // create first node and make HEAD point on that node, using HEAD poiinter creating a new node
    // HEAD = (struct Node*)malloc(sizeof(struct Node));
    HEAD = new Node[sizeof(struct Node)];
    HEAD->data = A[0];
    HEAD->next = HEAD;
    last = HEAD;
    
    for(int i = 1; i < n; i++) {
        // t = (struct Node*)malloc(sizeof(struct Node)); // always creating a new node
        t = new Node[sizeof(struct Node)];
        t->data = A[i];
        t->next = last->next;
        last->next = t;
        last = t;
    }
}

void display(struct Node *h) {
    do {
        cout << h->data << ", ";
        h = h->next;
    } while(h != HEAD);
    
    // // recursive display
    // static int flag = 0;
    // if(h != HEAD || flag == 0) {
    //     flag = 1;
    //     cout << h->data << ", ";
    //     display(h->next);
    // }
    // flag = 0;
}

int length(struct Node *p) {
    int len = 0;
    do {
        len++;
        p = p->next;
    } while(p != HEAD);
    return len;
}

void insert(struct Node *p, int index, int x) {
    
    struct Node *t;
    
    if(index < 0 || index > length(p)) return;
    
    if(index == 0) {
        
        t = (struct Node *)malloc(sizeof(struct Node)); // node is created 
        // t = new Node[sizeof(struct Node)];
        t->data = x; // and data is set in this new node
        if(HEAD == NULL) { // if it is a first node, HEAD should point upon t
            HEAD = t;
            HEAD->next = HEAD;
        } else {
            while(p->next != HEAD) p = p->next;
            p->next = t;
            t->next = HEAD; // point HEAD to t->next
            HEAD = t; // moving HEAD to new node t
        }
        
    } else {
        
        for(int i = 0; i < index - 1; i++) p = p->next;
        t = (struct Node *)malloc(sizeof(struct Node)); // node is created 
        // t = new Node[sizeof(struct Node)];
        t->data = x;
        t->next = p->next;
        p->next = t;
        
    }
}

int del(struct Node *p, int index) {
    
    struct Node *q;
    int x;
    
    if(index < 1 || index > length(HEAD)) return -1;
    
    if(index == 1) {
        
        while(p->next != HEAD) {
            p = p->next;
        }
        
        x = HEAD->data;
        
        if(p == HEAD) { // this condition is to check if it is the last node
            
            free(HEAD);
            HEAD = NULL;
            
        } else {
            p->next = HEAD->next;
            free(HEAD);
            HEAD = p->next;
        }
        
    } else {
        
        for(int i = 0; i < index - 2; i++) {
            p = p->next;
        }
        
        q = p->next;
        p->next = q->next;
        x = q->data;
        free(q);
        
    }
    return x;
}

int main() {
    int A[] = { 2, 3, 4, 5, 6 };
    int size = sizeof(A)/sizeof(A[0]);
    create(A, size);
    // insert(HEAD, 5, 10);
    cout << del(HEAD, 0) << endl;
    display(HEAD);
    return 0;
}
