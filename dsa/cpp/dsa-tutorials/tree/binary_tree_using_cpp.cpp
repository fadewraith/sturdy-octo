#include<iostream>
#include "QueueCpp.h"
using namespace std;

class Tree {
    Node *root; // this is default private, if we dont specify the access modifier
    public: // we are going to use it outside of the class, thats why used as a public
        // Node *root;
        Tree() {
            root = NULL;
        }
        void create_tree();
        // if we make the Node *root as private, then in that case , we will use this method
        void pre_order() { pre_order(root); }
        void pre_order(Node *p);
        void post_order() { post_order(root); }
        void post_order(Node *p);
        void in_order() { in_order(root); }
        void in_order(Node *p);
        void level_order() { level_order(root); }
        void level_order(Node *p);
        int height() { return height(root); }
        int height(Node *p);
};

void Tree::create_tree() {
    Node *p, *t = NULL; // p & t are temporary pointers
    int x; // variable for reading the data
    Queue q(100);

    cout << "Enter root value: ";
    cin >> x;
    root = new Node; // creating a root node
    root->data = x;
    root->lchild = root->rchild = NULL;
    q.enqueue(root);

    // use to enter the left child & rt child value and then storing it in a queue
    while(!q.isEmpty()) {
        p = q.dequeue(); // for every node, take a node from the queue
        cout << "enter left child of " << p->data << ": ";
        cin >> x;
        if(x != -1) {
            t = new Node;
            t->data = x;
            t->lchild = t->rchild = NULL;
            p->lchild = t;
            q.enqueue(t);
        }

        cout << "enter right child of " << p->data << ": ";
        cin >> x;
        if(x != -1) {
            t = new Node;
            t->data = x;
            t->lchild = t->rchild = NULL;
            p->rchild = t;
            q.enqueue(t);
        }
    }

}

void Tree::pre_order(struct Node *p) {
    if(p) {
        cout << p->data << " ";
        pre_order(p->lchild);
        pre_order(p->rchild);
    }
}

void Tree::post_order(Node *p) {
    if(p) {
        post_order(p->lchild);
        post_order(p->rchild);
        cout << p->data << " ";
    }
}

void Tree::in_order(struct Node *p) {
    if(p) {
        in_order(p->lchild);
        cout << p->data << " ";
        in_order(p->rchild);
    }
}

void Tree::level_order(struct Node *p) {
    Queue q(100);
    cout << root->data;
    q.enqueue(root);

    while(!q.isEmpty()) {
        root = q.dequeue();
        if(root->lchild) {
            cout << root->lchild->data;
            q.enqueue(root->lchild);
        }
        if(root->rchild) {
            cout << root->rchild->data;
            q.enqueue(root->rchild);
        }
    }
}

//int count(struct Node *root) {
//    if(root) {
//        return count(root->lchild) + count(root->rchild) + 1;
//    }
//    return 0;
//}

int Tree::height(struct Node *root) {
    int x = 0, y = 0;
    if(root == 0) return 0;
    x = height(root->lchild);
    y = height(root->rchild);
    if(x > y) return x+1;
    else return y+1;
}

int main() {
    Tree t;
    t.create_tree();
    cout << "pre order: ";
    // t.pre_order(t.root);
    t.pre_order();
    cout << endl;
    cout << "in order: ";
    // t.in_order(t.root);
    t.in_order();
    cout << endl;
    cout << "post order: ";
    // t.post_order(t.root);
    t.post_order();
    return 0;
}
