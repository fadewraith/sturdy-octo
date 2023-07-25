#include<iostream>
using namespace std;
// #include<limits.h>

struct Node {
    int data;
    struct Node *next; // self referential pointer
} *first = NULL, *second = NULL, *third = NULL;

void create(int A[], int n) {
    int i;
    struct Node *temp, *last;
    // first = (struct Node *)malloc(sizeof(struct Node));
    first = new Node[sizeof(struct Node)];
    first->data = A[0];
    first->next = NULL;
    last = first;
    // rest of the nodes creating using the for loop
    for(i = 1; i < n; i++) {
        // temp = (struct Node *)malloc(sizeof(struct Node));
        temp = new Node[sizeof(struct Node)];
        temp->data = A[i];
        temp->next = NULL;
        last->next = temp; // used to point the previous last node's next to the new node 
        last = temp; // here current node will become the last node
    }
}

void create2(int A[], int n) {
    int i;
    struct Node *temp, *last;
    // first = (struct Node *)malloc(sizeof(struct Node));
    second = new Node[sizeof(struct Node)];
    second->data = A[0];
    second->next = NULL;
    last = second;
    // rest of the nodes creating using the for loop
    for(i = 1; i < n; i++) {
        // temp = (struct Node *)malloc(sizeof(struct Node));
        temp = new Node[sizeof(struct Node)];
        temp->data = A[i];
        temp->next = NULL;
        last->next = temp; // used to point the previous last node's next to the new node 
        last = temp; // here current node will become the last node
    }
}

int max(struct Node *p) {
    // int m = INT32_MIN;
    // while(p) {
    //     if(p->data > m)
    //         m = p->data;
    //     p = p->next;
    // }
    // return m;
    int x = 0;
    if(p == 0)
        return INT32_MIN;
    x = max(p->next);
    return x > p->data ? x : p->data;
}

void display(struct Node *p) {
    while(p != NULL) {
        cout << p->data << " -> ";
        p = p->next;
    }
//     if(p != NULL) {
//         cout << p->data << ", ";
//         displayRecursively(p->next);
//     }
}

int add(struct Node *p) {
    int result = 0;
    // while(p != 0) {
    //     result += p->data;
    //     p = p->next;
    // }
    // return result;
    if(p == 0)
        return 0;
    return p->data + add(p->next);
}

struct Node * lin_search(struct Node *p, int key) {
    struct Node *q = NULL;
    // improved linear_search
    while(p != NULL) {
        if(key == p->data) {
            q->next = p->next;
            p->next = first;
            first = p;
            return p;
        }
        q = p;
        p = p->next;
    }
    return NULL;
    
    // loop
    // while(p != NULL) {
    //     if(key == p->data) return p;
    //     p = p->next;
    // }
    // return NULL;
    
    // recursion
    // if(p == NULL) return NULL;
    // if(key == p->data) return p;
    // return lin_search(p->next, key);
}

int count(struct Node *p) {
    int c = 0;
    // while(p) {
    //     c++;
    //     p = p->next;
    // }
    // return c;
    if(p == 0)
        return 0;
    return 1 + count(p->next);
}

void insert(struct Node *p, int index, int x) {
    struct Node *t;
    if(index < 0 || index > count(p)) return;
    t = (struct Node *)malloc(sizeof(struct Node));
    t = new Node[sizeof(struct Node)];
    t->data = x;
    if(index == 0) {
        t->next = first;
        first = t;
    } else {
        for(int i = 0; i < index; i++) {
            p = p->next;
        }
        t->next = p->next;
        p->next = t;
    }
}

void sortedInsert(struct Node *p, int x) {
    struct Node * t, *q = NULL;
    t = (struct Node *)malloc(sizeof(struct Node));
    // t = new Node[sizeof(struct Node)];
    t->data = x;
    t->next = NULL;
    
    if(first == NULL) { // checking if its a first node
        first = t;
    } else {
        while(p && p->data < x) {
            q = p;
            p = p->next;
        }
        if(p == first) // checking if its the first node, then insert the new node before the first node
        {
            t->next = first;
            first = t;
        } else // if p is not first
        {
            t->next = q->next;
            q->next = t;
        }
    }
}

int del(struct Node *p, int index) {
    struct Node *q; // tail pointer
    int x = -1;
    
    if(index < 1 || index > count(p)) {
        return -1;
    }
    if(index == 1){
        q = first;
        x = first->data;
        first = first->next;
        delete q;
        return x;
    } else {
        for(int i = 0; i < index - 1; i++) {
            q = p;
            p = p->next;
        }
        q->next = p->next;
        x = p->data;
        delete p;
        return x;
    }
}

int isSorted(struct Node *p) {
    int x = INT32_MIN;
    while(p != NULL)  {
        if(p->data < x) {
            return 0;
        }
        x = p->data;
        p = p->next;
    }
    return 1;
}

void removeDuplicates(struct Node *p) {
    struct Node *q = p->next;
    while(q != NULL) {
        if(p->data != q->data) {
            p = q;
            q = q->next;
        } else {
            p->next = q->next;
            free(q);
            q = p->next;
        }
    }
}

void reverseList(struct Node *p) {
    // reversing using element
    /* struct Node *q = p;
    int *A; int i = 0;
    A = (int *)malloc(sizeof(int)*count(p));
    // A = new int[sizeof(int) * count(p)];
    while(q != NULL) {
        A[i] = q->data;
        q = q->next;
        i++;
    }
    q = p; i--;
    while(q != NULL) {
        q->data = A[i];
        q = q->next;
        i--;
    } */
    
    //reversing using sliding pointers
    struct Node *q = NULL, *r = NULL;
    while(p != NULL) {
        r = q;
        q = p;
        p = p->next;
        q->next = r;
    }
    first = q;
    
}

void reverseRecursive(struct Node *q, struct Node *p) { // reversing using recursion
    if(p) {
        reverseRecursive(p, p->next);
        p->next = q;
    } else {
        first = q;
    }
}

void concat(struct Node*p, struct Node *q) {
    third = p;
    while(p->next != NULL) { // it will stop on last node
        p = p->next;
    }
    p->next = q;
}

void merge(struct Node *p, struct Node *q) {
    
    struct Node *last;
    
    if(p->data < q->data) {
        
        last = third = p;
        p = p->next;
        third->next = NULL;
        
    } else {
        
        last = third = q;
        q = q->next;
        third->next = NULL;
        
    }
    
    while(p && q) {
        
        if(p->data < q->data) {
            
            last->next = p;
            last = p;
            p = p->next;
            last->next = NULL;
            
        } else {
            
            last->next = q;
            last = q;
            q = q->next;
            last->next = NULL;
            
        }
    }
    
    if(p) last->next = p;
    if(q) last->next = q;
    
    /* if(p) {
        last->next = p;
    } else {
        last->next = q;
    } */
    
}

int isLoop(struct Node *f) {
    struct Node *p, *q;
    p = q = f;
    do {
        p = p->next;
        q = q->next; // q will take 2 steps
        // q ? q = q->next : q; // but 2nd step is conditional because of NULL
        q = q ? q->next : q; // but 2nd step is conditional because of NULL 
    } while (p && q && p!=q);
    return p == q ? 1 : 0;
}



int main() {
    // creating temp pointers to form a loop in a list
    struct Node *t1, *t2;
    int A[] = { 3, 5, 7, 10, 15 }; // will be used to insert in the sorted linked list
    // int A[] = { 10, 20, 20, 20, 30, 40, 50, 50 }; 
    // int B[] = { 2, 6, 8, 12, 13, 16, 19 };
    int size = sizeof(A)/sizeof(A[0]);
    create(A, size);
    // t1 = first->next->next; // this can be done using while loop also, t1 is pointing on 7
    // t2 = first->next->next->next->next; // this can be done using while loop also, t2 on 15
    // t2->next = t1; // here t2->next = NULL, so for creating a loop pointing it on t1
    // create2(B, 7);
    // display(first);
    // struct Node *temp;
    // // int32_t nums[3]={2,4,3};
    // // cout << ( nums[0] << nums[1] << nums[2] );
    // cout << endl << "sum is: " << add(first);
    // cout << endl << "length is: " << count(first);
    // cout << endl << "max is: " << max(first);
    // temp = lin_search(first, 7);
    // temp ? cout << endl << "key is: " << temp->data : cout << endl << "key not found";
    // cout << endl;
    // display(first);
    cout << endl;
    // sortedInsert(first, 20);
    // insert(first, 8, 57);
    // display(first);
    // insert(first, 6, 59);
    // cout << endl;
    // display(second);
    // cout << endl;
    // cout << del(first, 4);
    // cout << endl;
    // display(first);
    // isSorted(first) ? cout << "list is sorted\n" : cout << "list is not sorted\n";
    // removeDuplicates(first);
    // display(first);
    // reverseList(first);
    // reverseRecursive(NULL, first);
    // concat(first, second);
    // display(first);
    // cout << endl;
    // merge(first, second);
    // display(third);
    cout << endl;
    isLoop(first) ? cout << "list contains loop" : cout << "contains no loop";
    cout << endl;
    return 0;
}

