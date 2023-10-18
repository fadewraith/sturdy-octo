#include<iostream>
using namespace std;

struct Queue {
    int size, front, rear, *Q;
};

void create(struct Queue *q, int size) {
    q->size = size;
    q->front = q->rear = 0;
    q->Q = (int *)malloc(q->size * sizeof(int));
}

bool isFull(struct Queue *q) {
    return ((q->rear+1)%q->size == q->front) ? true : false;
}

bool isEmpty(struct Queue *q) {
    return q->front == q->rear ? true : false;
}

void display(struct Queue q) {
    int i = (q.front + 1) % q.size;
    
    do {
        cout << q.Q[i];
        i = (i + 1) % q.size;
        if(i != q.front && q.Q[i]) cout << " <- ";
    } while (i != (q.rear + 1) % q.size);
    
    cout << endl;
}

void enqueue(struct Queue *q, int x) {
    if(isFull(q)) cout << "queue is full\n";
    else {
        q->rear = (q->rear + 1) % q->size;
        q->Q[q->rear] = x;
    }
}

int dequeue(struct Queue *q) {
    int x = -1;
    if(isEmpty(q)) cout << "queue is empty\n";
    else {
        q->front = (q->front + 1) % q->size;
        x = q->Q[q->front];
    }
    return x;
}

int main() {
    struct Queue q;
    create(&q, 4);
    enqueue(&q, 10);
    enqueue(&q, 20);
    enqueue(&q, 30);
    enqueue(&q, 40);
    display(q);
    cout << dequeue(&q) << endl;
    enqueue(&q, 40);
    display(q);
    cout << dequeue(&q) << endl;
    enqueue(&q, 50);
    display(q);
    cout << dequeue(&q) << endl;
    dequeue(&q);
    return 0;
}
