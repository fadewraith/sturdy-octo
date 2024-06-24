#ifndef QUEUECPP_H_INCLUDED
#define QUEUECPP_H_INCLUDED
#include<iostream>
using namespace std;

// this node will be for a tree
class Node {
    public:
        Node *lchild;
        int data;
        Node *rchild;
};

class Queue { // this will store the addresses of the tree
    private:
        int front, rear, size;
        Node **Q;
    public:
        Queue() {
            front = rear = -1;
            size = 10;
            Q = new Node*[size];
        }

        Queue(int size) {
            front = rear = -1;
            this->size = size;
            Q = new Node*[this->size];
        }

        void enqueue(Node *x);
        Node* dequeue();
        int isEmpty() {
            return front == rear;
        }
};

void Queue::enqueue(Node *x) {
    if(rear == size - 1) cout << "queue is full\n";
    else Q[++rear] = x;
}

Node *Queue::dequeue() {
    Node* x = NULL;
    if(front == rear) cout << "queue is empty\n";
    else {
        x = Q[front + 1];
        front++;
    }
    return x;

}

#endif // QUEUECPP_H_INCLUDED
