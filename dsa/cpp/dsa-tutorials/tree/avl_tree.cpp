#include<iostream>
using namespace std;

struct Node {
    struct Node *lchild, *rchild;
    int data;
    // for AVL tree, we should maintain balance factor for every node
    // we will maintain height for every node, in this way we can get the height of the left subtree & right subtree
    int height; // height of a node
} *root = NULL;

// to calc the height of each node and whichever is greater, assign that one to p
int node_height(struct Node *p) {
    // hl = height of left subtree, hr = height of right subtree
    int hl, hr;
    hl = p && p->lchild ? p->lchild->height : 0;
    hr = p && p->rchild ? p->rchild->height : 0;
    return hl > hr ? hl + 1 : hr + 1;
}

// to calc the balanced factor
int balanced_factor(struct Node *p) {
    // hl = height of left subtree, hr = height of right subtree
    int hl, hr;
    hl = p && p->lchild ? p->lchild->height : 0;
    hr = p && p->rchild ? p->rchild->height : 0;
    
    return hl - hr;
}

// ll rotation
struct Node *ll_rotation(struct Node *p) {
    struct Node *pl = p->lchild;
    struct Node *plr = pl->rchild;
    
    pl->rchild = p;
    p->lchild = plr;
    p->height = node_height(p);
    pl->height = node_height(pl);
    
    if(root == p) {
        root = pl;
    }
    
    return pl;
}

struct Node *lr_rotation(struct Node *p) {
    struct Node *pl = p->lchild;
    struct Node *plr = pl->rchild;
    
    pl->rchild = plr->lchild;
    p->lchild = plr->rchild;
    
    plr->lchild = pl;
    plr->rchild = p;
    
    pl->height = node_height(pl);
    p->height = node_height(p);
    plr->height = node_height(plr);
    
    if(root == p) {
        root = plr;
    }
    
    return plr;
}

struct Node *rr_rotation(struct Node *p) {
    return NULL;
}

struct Node *rl_rotation(struct Node *p) {
    return NULL;
}

struct Node * rec_insert(struct Node *p, int key) { 
    // in bst, always element is inserted in the leaf node, so when it will be on the leaf node, the height will be 1
    // takes pointer to the root node & key to be inserted
    // when p becomes NULL, that time we have to insert a new node
    struct Node *t = NULL;
    if(p == NULL) {
        t = (struct Node *)malloc(sizeof(struct Node));
        t->data = key;
        // height starts from 0, we can take either 0 or 1
        t->height = 1;
        t->lchild = t->rchild = NULL;
        return t;
    }
    if(key < p->data) {
        p->lchild = rec_insert(p->lchild, key);
    } else if(key > p->data) {
        p->rchild = rec_insert(p->rchild, key);
    }
    
    // @returning time, we should update the height of every node
    // p->height = left_height > right_height ? left_height : right_height
    // whichever height will be greater, that will be the ht of the p
    p->height = node_height(p);
    
    // checking balanced factor whether to perform LL or LR rotation
    // 2 = imbalanced and it is imbalanced on l.h.s. coz its b.f. is positive 2.
    // and if p->lchild = 1, its on l.h.s coz its positive 1 and we'll perform LL rotation
    if(balanced_factor(p) == 2 && balanced_factor(p->lchild) == 1) {
        return ll_rotation(p);
    } else if(balanced_factor(p) == 2 && balanced_factor(p->lchild) == -1) {
        return lr_rotation(p);
    } else if(balanced_factor(p) == -2 && balanced_factor(p->rchild) == -1) {
        return rr_rotation(p);
    } else if(balanced_factor(p) == -2 && balanced_factor(p->rchild) == 1) {
        return rl_rotation(p);
    }
    
    return p; // if they're equal, we dont have to do anything
}

int main() {
    root = rec_insert(root, 10);
    rec_insert(root, 5);
    rec_insert(root, 2);
    return 0;
}