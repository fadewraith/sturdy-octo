#include<iostream>
// #include<cstring>
using namespace std;

struct Node {
    int data;
    struct Node *next;
} *top = NULL;

void push(int x) {
    struct Node *t;
    t=(struct Node*)malloc(sizeof(struct Node));
    
    if(t==NULL) cout << "stack is full\n";
    else {
        t->data=x;
        t->next=top;
        top=t;
    }
}

int pop() {
    struct Node *t;
    int x=-1;
    if(top==NULL) cout << "Stack is Empty\n";
    else {
        t=top;
        top=top->next;
        x=t->data;
        free(t);
    }
    return x;
}

void Display(){
    struct Node *p;
    p=top;
    while(p!=NULL) {
        cout << p->data;
        p=p->next;
    }
    cout << endl;
}

int isOperand(char x) {
    // if(x=='+' || x=='-' || x=='*' || x=='/') return 0;
    // else return 1;
    return (x == '+' || x == '-' || x == '*' || x == '/') ? 0 : 1;
}


int evaluate(char *postfix) {
    int i = 0, x1, x2, r;
    for(i = 0; postfix[i] != '\0'; i++) {
        // subtracting, so that we can get the real value inplace of char '3'. 
        //ASCII of '3' = 51, '0' = 48, 51-48 = 3
        if(isOperand(postfix[i])) push(postfix[i] - '0'); 
        else {
            x2 = pop(); x1 = pop();
            switch(postfix[i]) {
                case '+': r = x1 + x2; break;
                case '-': r = x1 - x2; break;
                case '*': r = x1 * x2; break;
                case '/': r = x1 / x2; break;
            }
            push(r);
        }
    }
    return top->data;
}

int  main() {
    // char *postfix = "234*+82/-";
    char postfix[] = "234*+82/-";
    cout << "result is: " << evaluate(postfix) << endl;
    return 0;
}