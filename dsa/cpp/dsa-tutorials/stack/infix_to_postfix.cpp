#include<iostream>
#include<cstring>
using namespace std;

struct Node {
  char data;
  struct Node *next;
} *top = NULL;

void push (char x) {
  struct Node *t;
  t = (struct Node *) malloc (sizeof (struct Node));
  if (t == NULL)
    cout << "stack is full\n";
  else {
      t->data = x;
      t->next = top;
      top = t;
  }
}


char pop () {
    
  struct Node *t;
  char x = -1;

  if (top == NULL)
    cout << "Stack is Empty\n";
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
        if(p->next) cout << " --> ";
        p = p->next;
    }
    cout << endl;
}

int isBalanced(char *expr) {
 int i;

 for(i=0;expr[i]!='\0';i++) {
     if(expr[i]=='(') push(expr[i]);
     else if(expr[i]==')') {
         if(top==NULL)
         return 0;
         pop();
     }
 }
 if(top==NULL) return 1;
 else return 0;
}

int precedence(char x) {
    if(x == '+' || x == '-') return 1; // precedence is from lowest to highest
    else if(x == '*' || x == '/') return 2;
    return 0;
}

int isOperand(char x) {
    if(x == '+' || x == '-' || x == '*' || x == '/') return 0;
    else return 1;
}

char *infix2postfix(char *infix) {
    int i=0,j=0;
    char *postfix;
    int len = strlen(infix);
    postfix = (char *)malloc((len + 1) * sizeof(char));
    
    while(infix[i] != '\0') {
        if(isOperand(infix[i])) {
            postfix[j++] = infix[i++];
        } else { // if its not operand, check the precedences
            if(precedence(infix[i]) > precedence(top->data)) {
                push(infix[i++]);
            } else {
                postfix[j++] = pop();
            }
        }
    }
    
    while(top != NULL) {
        postfix[j++] = pop();
    }
    postfix[j] = '\0';
    return postfix;
}


int main () {
    // char *infix = "a+b*c";
    char infix[] = "a+b*c-d/e"; // this declaration is working only
    // const char *infix = "a+b*c";
    // initially stack is empty, so it may give error when we are checking for precedence(top->data), so we're doing this - 
    push('#');
    char *postfix = infix2postfix(infix);
    cout << postfix;
  return 0;
}
