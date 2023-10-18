#include<iostream>
using namespace std;

class Queue {
    
    private:
        int front, rear, size, *Q;
        
    public:
        Queue() {
            front = rear = -1;
            size = 10;
            Q = new int[size];
        }
        
        Queue(int size) {
            front = rear = -1;
            this->size = size;
            Q = new int[this->size];
        }
        
        void enqueue(int x);
        int dequeue();
        void display();
        bool isFull();
        bool isEmpty();
        ~Queue();
};

void Queue::enqueue(int x) {
    if(rear == size - 1) cout << "Queue is full\n";
    else {
        rear++;
        Q[rear] = x;
    }
}

int Queue::dequeue() {
    int x = -1;
    if(front == rear) cout << "Queue is empty\n";
    else {
        x = Q[front + 1];
        front++;
    }
    return x;
}

void Queue::display() {
    for(int i = front + 1; i <= rear; i++) {
        cout << Q[i];
        if(i < rear) cout << " <- " << flush;
    }
    cout << endl;
}

bool Queue::isEmpty() {
    if (front == rear){
        return true;
    }
    return false;
}
 
bool Queue::isFull() {
    if (rear == size-1){
        return true;
    }
    return false;
}

Queue::~Queue() {
    delete [] Q;
}

int main() {
    Queue q(5);
    q.enqueue(10);
    q.enqueue(9);
    q.enqueue(8);
    q.enqueue(7);
    q.enqueue(6);
    q.enqueue(6);
    q.display();
    cout << q.dequeue() << endl;
    cout << q.dequeue() << endl;
    cout << q.dequeue() << endl;
    cout << q.dequeue() << endl;
    cout << q.dequeue() << endl;
    cout << q.dequeue() << endl;
    q.display();
    return 0;
}
