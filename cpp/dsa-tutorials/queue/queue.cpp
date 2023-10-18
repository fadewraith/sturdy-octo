#include<iostream>
using namespace std;

struct Queue {
    int size, front, rear, *Q;
};

void create(struct Queue *q, int size) {
    q->size = size;
    q->front = q->rear = -1;
    q->Q = (int *)malloc(q->size * sizeof(int));
}

void enqueue(struct Queue *q, int x) {
    if(q->rear == q->size - 1) cout << "Queue is full\n";
    else {
        q->rear++;
        q->Q[q->rear] = x;
    }
}

int dequeue(struct Queue *q) {
    int x = -1;
    if(q->front == q->rear) cout << "Queue is empty\n";
    else {
        q->front++;
        x = q->Q[q->front];
    }
    return x;
}

void display(struct Queue q) {
    for(int i = q.front+1; i <= q.rear; i++) {
        cout << q.Q[i];
        if(i < q.rear) cout << " <- ";
    }
    cout << endl;
}

int main() {
    struct Queue q;
    create(&q, 5);
    enqueue(&q, 10);
    enqueue(&q, 9);
    enqueue(&q, 8);
    enqueue(&q, 7);
    enqueue(&q, 6);
    enqueue(&q, 6);
    display(q);
    cout << dequeue(&q) << endl;
    // dequeue(&q);
    // dequeue(&q);
    // dequeue(&q);
    // dequeue(&q);
    // dequeue(&q);
    display(q);
    return 0;
}
