#ifndef STACK_H_INCLUDED
#define STACK_H_INCLUDED
#include<iostream>
#include "Queue.h"
using namespace std;

struct Stack {
    int size;
    int top;
    struct Node **S;
};

void stack_create(struct Stack *st, int size) {
    st->size = size;
    st->top = -1;
    st->S = (struct Node **)malloc(st->size * sizeof(struct Node *));
}

void push(struct Stack *st, struct Node *x) {
    if(st->top == st->size - 1) cout << "stack overflow\n";
    else st->S[++st->top] = x;
}

struct Node *pop(struct Stack *st) {
    struct Node *x = NULL;
    if(st->top == -1) cout << "stack underflow\n";
    // else x = st->S[--st->top]; // incorrect
    else x = st->S[st->top--];
    return x;
}

int isEmptyStack(struct Stack st) {
    return (st.top == -1) ? 1 : 0;
}

int isFullStack(struct Stack st) {
    return st.top == st.size - 1;
}


#endif // QUEUE_H_INCLUDED
