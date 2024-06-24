#include<iostream>
using namespace std;

// structure for BST
struct Node {
    struct Node *lchild; // self referential pointer
    int data;
    struct Node *rchild;
} *root = NULL;

// to insert in BST
void insert(int key) {
    
    struct Node *t = root;
    struct Node *r, *p; // where p is for creating a new node, r is a tail pointer which will follow t
    
    if(root == NULL) { // means this is the very first node
        p = (struct Node *)malloc(sizeof(struct Node));
        p->data = key;
        p->lchild = p->rchild = NULL;
        root = p;
        return; // after creating the first node, return
    }
    
    while(t != NULL) {
        r = t; // r will be following t
        if(key < t->data) {
            t = t->lchild;
        } else if(key > t->data) {
            t = t->rchild;
        } else { // condition is key is equals, do nothing and return. we're not going to create a node in this case
            // in this case, t will become null and r will be point at some case, where we have to insert a new node at left or right child
            return; 
        }
    }
    
    // creating a new node
    p = (struct Node *)malloc(sizeof(struct Node));
    p->data = key;
    p->lchild = p->rchild = NULL;
    
    if(key < r->data) {
        r->lchild = p;
    } else {
        r->rchild = p;
    }
    
}

// performing inorder traversal, for confirming the BST as it will give the o/p in sorted order
void inorder(struct Node *p) {
    if(p) {
        inorder(p->lchild);
        cout << p->data << ", ";
        inorder(p->rchild);
    }
}

struct Node * search(int key) {
    struct Node *t = root;
    while(t != NULL) {
        if(key == t->data) return t;
        else if(key < t->data) t = t->lchild;
        else t = t->rchild;
    }
    return NULL;
}

struct Node * rec_insert(struct Node *p, int key) { 
    // takes pointer to the root node & key to be inserted
    // when p becomes NULL, that time we have to insert a new node
    struct Node *t = NULL;
    if(p == NULL) {
        t = (struct Node *)malloc(sizeof(struct Node));
        t->data = key;
        t->lchild = t->rchild = NULL;
        return t;
    }
    if(key < p->data) {
        p->lchild = rec_insert(p->lchild, key);
    } else if(key > p->data) {
        p->rchild = rec_insert(p->rchild, key);
    }
    return p; // if they're equal, we dont have to do anything
}

// to check the height of the left/right sub tree, whichever is greater
int height(struct Node *p) {
    int x, y;
    if(p == NULL) return 0;
    x = height(p->lchild);
    y = height(p->rchild);
    return x > y ? x + 1 : y + 1;
}

struct Node * inorder_predecessor(struct Node *p) {
    while(p && p->rchild != NULL) {
        p = p->rchild;
    }
    return p;
}

struct Node * inorder_successor(struct Node *p) {
    while(p && p->lchild != NULL) {
        p = p->lchild;
    }
    return p;
}

struct Node *del(struct Node *p, int key) {
    
    struct Node *q;
    
    if(p == NULL) return NULL; // p == NULL, we have to del nothing
    // case for the leaf node
    if(p->lchild == NULL && p->rchild == NULL) {
        if(p == root) root = NULL;
        free(p);
        return NULL; // means node is deleted
    }
    
    
    if(key < p->data) {
        p->lchild = del(p->lchild, key);
    } else if(key > p->data) {
        p->rchild = del(p->rchild, key);
    } else {
        // we can delete it by replacing it with inorder predecessor/successor
        // here we will decide(inorder predecessor/successor) by the ht of the left & right sub tree
        // if the height of left/right(anyone of them) is more, we'll delete from that sub tree
        // and if they're equal, we can delete from any side
        if(height(p->lchild) > height(p->rchild)) {
            // for deleting from left side, we need inorder predecessor
            q = inorder_predecessor(p->lchild);
            // copying p->data to q->data and del node q
            p->data = q->data; // copy the data
            // in this process the last data, i.e. node will be deleted, not the actual one
            p->lchild = del(p->lchild, q->data); // after copying, delete it from that place
        } else {
            // for deleting from right side, we need inorder successor
            q = inorder_successor(p->rchild);
            // copying p->data to q->data and del node q
            p->data = q->data; // copy the data
            // in this process the last data, i.e. node will be deleted, not the actual one (actual means the key which is to be deleted)
            p->rchild = del(p->rchild, q->data); // after copying, delete it from that place
        }
    }
    return p;
}

int main() {
    struct Node *temp;
    // insert(10);
    // insert(5);
    // insert(20);
    // insert(8);
    // insert(30);
    root = rec_insert(root, 50);
    rec_insert(root, 10);
    rec_insert(root, 40);
    rec_insert(root, 20);
    rec_insert(root, 30);
    inorder(root);
    cout << endl;
    del(root, 30);
    inorder(root);
    cout << endl;
    temp = search(20);
    if(temp != NULL) cout << "element is found: " << temp->data << endl;
    else cout << "element not found" << endl;
    return 0;
}
