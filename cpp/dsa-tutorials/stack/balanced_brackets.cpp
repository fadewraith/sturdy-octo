#include<iostream>
using namespace std;

struct Node {
    char data;
    struct Node* next;
} *top = NULL;

void push(char x) {
    
    struct Node *t;
    t = (struct Node *)malloc(sizeof(struct Node));
    
    // t = new Node[sizeof(struct Node)];
    
    // check is stack is full, if t = NULL, then it means heap is full
    if(t == NULL) cout << "stack is full\n"; 
    else {
        t->data = x;
        t->next = top;
        top = t;
    }
    
}

char pop() {
    
    struct Node *t;
    char x = -1;
    
    // check if top is NULL< then stack is empty
    if(top == NULL) cout << "Stack is empty\n";
    else {
        t = top;
        top = top->next;
        x = t->data;
        free(t);
    }
    return x;
}


void display() {
    struct Node *p;
    p = top;
    while(p != NULL) {
        cout << p->data;
        if(p->next) cout << ", ";
        p = p->next;
    }
    cout << "\n";
}

int peek(int pos) {
    struct Node *t = top;
    for(int i = 0; t != NULL && i < pos - 1; i++) {
        t = t->next;
    }
    return t != NULL ? t->data : -1;
}

int isEmpty() {
    return top ? 0 : -1;
}

int stackTop() {
    return top ? top->data : -1;
}


int isFull() {
    
    struct Node *t;
    t = (struct Node *)malloc(sizeof(struct Node));
    // cout << t << endl;
    // t = new Node[sizeof(struct Node);
    int r = t ? 1 : 0;
    free(t);
    return r;
}

int isBalanced(char *exp) {
// int isBalanced(const char *exp) {
    for(int i = 0; exp[i] != '\0'; i++) {
        if(exp[i] == '(') push(exp[i]);
        else if(exp[i] == ')') {
            if(top == NULL) return 0;
            pop();
        }
    }
    return top == NULL ? 1 : 0;
}



int main() {
    // const char *exp = "(a+b)*(c-d))";
    // char *exp= "((a+b)*(c-d))"; // making it as pointer, will make it as a string
    char exp[] = "(a+b)*(c-d))"; // making it as pointer, will make it as a string
    cout << isBalanced(exp);
    return 0;
}