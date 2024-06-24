#ifndef QUEUE_H_INCLUDED
#define QUEUE_H_INCLUDED
#include<iostream>
using namespace std;

struct Node {
    struct Node *lchild;
    int data;
    struct Node *rchild;
};

struct Queue {
    int size, front, rear;
    struct Node **Q; // first pointer is of type node and second pointer is for array
};

bool isFull(struct Queue *q) {
    return (q->rear + 1) % q->size == q->front ? true : false;
}

bool isEmpty(struct Queue *q) {
    return q->front == q->rear ? true : false;
}

void create(struct Queue *q, int size) {
    q->size = size;
    q->front = q->rear = 0;
    q->Q = (struct Node **)malloc(q->size * sizeof(struct Node *));
}

void enqueue(struct Queue *q, Node * x) {
    if(isFull(q)) cout << "queue is full\n";
    else {
        q->rear = (q->rear + 1) % q->size;
        q->Q[q->rear] = x;
    }
}

struct Node * dequeue(struct Queue *q) {
    struct Node *x = NULL;
    if(isEmpty(q)) cout << "queue is empty\n";
    else {
        q->front = (q->front + 1) % q->size;
        x = q->Q[q->front];
    }
    return x;
}

int isEmpty(struct Queue q) {
    return q.front == q.rear;
}

#endif // QUEUE_H_INCLUDED
