#include<iostream>
using namespace std;

struct Stack {
    int size; // size of the stack
    int top; // pointing to the topmost element of the stack
    int *S; // array for storing elements
};

void create(struct Stack *st) {
    cout << "Enter size: ";
    cin >> st->size;
    st->top = -1;
    st->S = (int *)malloc(st->size * sizeof(int));
    // st->S = new int[st->size * sizeof(int)];
}

void display(struct Stack st) {
    for(int i = st.top; i >= 0; i--) {
        cout << st.S[i];
        if(i > 0) cout << " <- ";
    }
    cout << endl;
}

void push(struct Stack *st, int x) {
    // checking is space is there or not
    if(st->top == st->size - 1)
        cout << "Stack overflow\n";
    else {
        st->top++;
        st->S[st->top] = x;
    }
}

int pop(struct Stack *st) {
    int x = -1;
    if(st->top == -1) {
        cout << "Stack underflow\n";
    } else {
        x = st->S[st->top--];
    }
    return x;
}

int peek(struct Stack st, int index) {
    int x = -1;
    if((st.top - index + 1) < 0)
        cout << "Invalid index\n";
    x = st.S[st.top-index+1];
    return x;
}

int isEmpty(struct Stack st) {
    // if(st.top == -1) return 1;
    // return 0;
    return st.top == -1 ? 1 : 0;
}

int isFull(struct Stack st) {
    return st.top == st.size - 1;
}

int stackTop(struct Stack st) {
    // if(!isEmpty(st))
    //     return st.S[st.top];
    // return -1;
    return !isEmpty(st) ? st.S[st.top] : -1;
}

int main() {
    struct Stack st;
    create(&st);
    push(&st, 10);
    push(&st, 20);
    push(&st, 30);
    push(&st, 40);
    push(&st, 50);
    // cout << pop(&st) << endl;
    cout << peek(st, 0) << endl;
    display(st);
    return 0;
}