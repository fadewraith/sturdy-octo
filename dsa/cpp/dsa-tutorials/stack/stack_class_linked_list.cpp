#include<iostream>
using namespace std;

class Node {
    public:
        int data;
        Node *next;
};

class Stack {
    private:
        Node *top;
    
    public:
        Stack() {
            top = NULL;
        }
        void push(int x);
        int pop();
        void display();
};

void Stack::push(int x) {
    Node *t = new Node;
    if(t == NULL) cout << "Stack is full\n";
    else {
        t->data = x;
        t->next = top;
        top = t;
    }
}

int Stack::pop() {
    int x = -1;
    if(top == NULL) cout << "Stack is empty\n";
    else {
        x = top->data;
        Node *t = top;
        top = top->next;
        delete t;
    }
    return x;
}

void Stack::display() {
    Node *p = top;
    while(p != NULL) {
        cout << p->data;
        if(p->next) cout << ", ";
        p = p->next;
    }
    cout << endl;
}

int main() {
    Stack s;
    s.push(10);
    s.push(20);
    s.push(30);
    s.display();
    cout << s.pop() << endl;
    return 0;
}