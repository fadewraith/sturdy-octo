#include<iostream>
#include "Queue.h"
#include "Stack.h"
using namespace std;

struct Node *root = NULL;

void createTree() {
    struct Node *p, *t; // p & t are temporary pointers
    int x; // variable for reading the data
    struct Queue q;
    create(&q, 100); // creating a queue with storage of 100
    cout << "Enter root value: ";
    cin >> x;
    root = (struct Node *)malloc(sizeof(struct Node)); // creating a root node
    root->data = x;
    root->lchild = root->rchild = NULL;
    enqueue(&q, root);

    // use to enter the left child & rt child value and then storing it in a queue
    while(!isEmpty(q)) {
        p = dequeue(&q); // for every node, take a node from the queue
        cout << "enter left child of " << p->data << ": ";
        cin >> x;
        if(x != -1) {
            t = (struct Node *)malloc(sizeof(struct Node));
            t->data = x;
            t->lchild = t->rchild = NULL;
            p->lchild = t;
            enqueue(&q, t);
        }

        cout << "enter right child of " << p->data << ": ";
        cin >> x;
        if(x != -1) {
            t = (struct Node *)malloc(sizeof(struct Node));
            t->data = x;
            t->lchild = t->rchild = NULL;
            p->rchild = t;
            enqueue(&q, t);
        }
    }

}

void preOrder(struct Node *p) {
    if(p) {
        cout << p->data << " ";
        preOrder(p->lchild);
        preOrder(p->rchild);
    }
}

void postOrder(struct Node *p) {
    if(p) {
        postOrder(p->lchild);
        postOrder(p->rchild);
        cout << p->data << " ";
    }
}

void inOrder(struct Node *p) {
    if(p) {
        inOrder(p->lchild);
        cout << p->data << " ";
        inOrder(p->rchild);
    }
}

void iterative_pre(struct Node *p) { // passing pointer to root
    struct Stack stk; // needs stack
    stack_create(&stk, 100); // initializing the stack
    
    while(p || !isEmptyStack(stk)) {
        if(p) {
            cout << p->data << " ";
            push(&stk, p);
            p = p->lchild;
        } else {
            p = pop(&stk); // if p is null, pop out the addr and move p to the rt side
            p = p->rchild;
        }
    }
}

void iterative_in(struct Node *p) { // passing pointer to root
    struct Stack stk; // needs stack
    stack_create(&stk, 100); // initializing the stack
    
    while(p || !isEmptyStack(stk)) {
        if(p) {
            
            push(&stk, p);
            p = p->lchild;
        } else {
            p = pop(&stk); // if p is null, pop out the addr and move p to the rt side
            cout << p->data << " ";
            p = p->rchild;
        }
    }
}



int main() {

    createTree();
    preOrder(root);
    cout << endl;
    // iterative_pre(root);
    cout << endl;
    postOrder(root);
    cout << endl;
    // iterative_post(root);
    cout << endl;
    // inOrder(root);
    cout << endl;
    iterative_in(root);
    return 0;
}
