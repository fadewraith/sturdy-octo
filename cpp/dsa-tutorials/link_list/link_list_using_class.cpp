#include<iostream>
using namespace std;

class Node {
    public:
        int data;
        Node *next;
};

class LinkedList {
    private:
        Node *first;
    
    public:
        LinkedList() {
            first = NULL;
        }
        LinkedList(int A[], int n); // creating a link list
        ~LinkedList();
        
        void display();
        void insert(int index, int x);
        int del(int index);
        int length();
};

LinkedList::LinkedList(int A[], int n) {
    Node *last, *t;
    int i = 0;
    
    first = new Node;
    first->data = A[0];
    first->next = NULL;
    last = first;
    
    for(i = 1; i < n; i++) { // creating remaining nodes
        t = new Node;
        t->data = A[i];
        t->next = NULL;
        last->next = t;
        last = t;
    }
}

LinkedList::~LinkedList() {
    Node *p = first;
    while(first) {
        first = first->next;
        delete p;
        p = first;
    }
}

void LinkedList::display() {
    Node *p = first;
    while(p) {
        cout << p->data << ", ";
        p = p->next;
    }
    cout << endl;
}

int LinkedList::length() {
    Node *p = first;
    int len = 0;
    while(p) {
        len++;
        p = p->next;
    }
    return len;
}

void LinkedList::insert(int index, int x) {
    Node *t, *p = first;
    
    if(index < 0 || index > length()) return;
    // creating a new node
    t = new Node;
    t->data = x;
    t->next = NULL;
    
    if(index == 0) {
        t->next = first;
        first = t;
    } else {
        for(int i = 0; i < index - 1; i++) {
            p = p->next;
        }
        t->next = p->next;
        p->next = t;
    }
}

int LinkedList::del(int index) {
    Node *p, *q = NULL;
    int x = -1;
    if(index < 1 || index > length()) return -1;
    if(index == 1) {
        p = first;
        first = first->next;
        x = p->data;
        delete p;
    } else {
        p = first;
        for(int i = 0; i < index - 1; i++) {
            q = p;
            p = p->next;
        }
        q->next = p->next;
        x = p->data;
        delete p;
    }
    return x;
}

int main() {
    int A[] = { 1, 2, 3, 4, 5 };
    LinkedList l(A, 5);
    l.insert(3, 10);
    // cout << l.del(2);
    cout << endl;
    l.display();
    cout << l.length();
    return 0;
}
