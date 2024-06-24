#include<iostream>
using namespace std;

/*
a. if global variable is not taken, then all the functions will take stack as parameter
b. insertion and deletion is taking at beginning because of T.C. O(1)
c. insertion and deletion at the end will take O(n)
*/

struct Node {
    int data;
    struct Node* next;
} *top = NULL; // top = NULL is not recommended, we should have a pointer inside the main function

void push(int x) {
    
    struct Node *t;
    t = (struct Node *)malloc(sizeof(struct Node));
    
    // t = new Node[sizeof(struct Node)];
    
    // check is stack is full, if t = NULL, then it means heap is full
    if(t == NULL) cout << "stack is full\n"; 
    else {
        t->data = x;
        t->next = top;
        top = t;
    }
    
}

int pop() {
    
    struct Node *t;
    int x = -1;
    
    // check if top is NULL< then stack is empty
    if(top == NULL) cout << "Stack is empty\n";
    else {
        t = top;
        top = top->next;
        x = t->data;
        free(t);
    }
    return x;
}


void display() {
    struct Node *p;
    p = top;
    while(p != NULL) {
        cout << p->data;
        if(p->next) cout << ", ";
        p = p->next;
    }
    cout << "\n";
}

int peek(int pos) {
    struct Node *t = top;
    for(int i = 0; t != NULL && i < pos - 1; i++) {
        t = t->next;
    }
    return t != NULL ? t->data : -1;
}

int isEmpty() {
    return top ? 0 : -1;
}

int stackTop() {
    return top ? top->data : -1;
}


int isFull() {
    
    struct Node *t;
    t = (struct Node *)malloc(sizeof(struct Node));
    // cout << t << endl;
    // t = new Node[sizeof(struct Node);
    int r = t ? 1 : 0;
    free(t);
    return r;
}

int main() {
    push(10);
    push(20);
    push(30);
    push(40);
    push(50);
    display();
    cout << isFull() << endl;
    cout << isEmpty() << endl;
    cout << stackTop() << endl;
    cout << peek(3) << endl;
    cout << pop();
    return 0;
}