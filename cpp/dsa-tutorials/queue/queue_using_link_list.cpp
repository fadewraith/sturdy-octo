#include<iostream>
using namespace std;

struct Node {
    int data;
    struct Node *next;
} *front = NULL, *rear = NULL;

void enqueue(int x) {
    struct Node *t;
    t = (struct Node *)malloc(sizeof(struct Node));
    // if heap is full, means we cannot insert any Node
    if(t == NULL) cout << "queue is full\n";
    else {
        t->data = x;
        t->next = NULL;
        // if Queue is empty
        if(front == NULL) front = rear = t;
        else { // if Queue has atleast 1 element
            rear->next = t;
            rear = t;
        }
    }
}

int dequeue() {
    int x = -1;
    struct Node *t;
    // check empty condition
    if(front == NULL) cout << "Queue is empty\n";
    else {
        x = front->data;
        t = front;
        front = front->next;
        free(t);
    }
    return x;
}

void display() {
    struct Node *p = front;
    while(p) {
        cout << p->data;
        if(p->next) cout << " <- ";
        p = p->next;
    }
    cout << endl;
}

int main() {
    enqueue(10);
    enqueue(20);
    enqueue(30);
    display();
    cout << dequeue() << endl;
    cout << dequeue() << endl;
    display();
    enqueue(40);
    display();
    cout << dequeue() << endl;
    display();
    return 0;
}
