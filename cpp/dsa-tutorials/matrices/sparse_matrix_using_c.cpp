#include<iostream>
#include<stdlib.h>
using namespace std;

struct Element {
    int i, j, x;
};

struct Sparse {
    int m, n, num; struct Element *e;
};

void create(struct Sparse *s) {
    cout << "enter the dimensions: ";
    cin >> s->m >> s->n;
    cout << "enter number of non zero elements: ";
    cin >> s->num;
    // s->e = (struct Element *)malloc(s->num*sizeof(struct Element));
    s->e = new Element[s->num];
    cout << "enter non zero elements: ";
    for(int i = 0; i < s->num; i++) {
        cin >> s->e[i].i >> s->e[i].j >> s->e[i].x;
    }
}

void display(struct Sparse s) {
    int i, j, k = 0;
    for(i = 0; i < s.m; i++) {
        for(j = 0; j < s.n; j++) {
            if(i == s.e[k].i && j == s.e[k].j) { // k will be tracking the elements and its index
                cout << s.e[k++].x << " ";
            } else {
                cout << "0 ";
            }
        }
        cout << "\n";
    }
}

struct Sparse * add(struct Sparse *s1, struct Sparse *s2) {
    int i, j, k;
    i = j = k = 0;
    struct Sparse *sum;
    // sum = (struct Sparse *)malloc(sizeof(struct Sparse));
    sum = new Sparse[sizeof(struct Sparse)];
    // sum->e = (struct Element *)malloc((s1->num + s2->num) * sizeof(struct Element));
    sum->e = new Element[s1->num + s2->num];
    
    while(i < s1->num && j < s2->num) {
        // doing for row 
        if(s1->e[i].i < s2->e[j].i) {
            sum->e[k++] = s1->e[i++];
        } else if(s1->e[i].i > s2->e[j].i) {
            sum->e[k++] = s2->e[j++];
        } else {
            // doing it for col number
            if(s1->e[i].j < s2->e[j].j) {
                sum->e[k++] = s1->e[i++];
            } else if(s1->e[i].j > s2->e[j].j) {
                sum->e[k++] = s2->e[j++];
            } else {
                sum->e[k] = s1->e[i];
                sum->e[k++].x = s1->e[i++].x + s2->e[j++].x;
            }
        }
    }
    
    // while loop will terminate when anyone of the matrix has finished
    // rest is copying the rest of the elements from the remaining matrix 
    // so the remaining matrix be either the first onematrix or the second matrix
    // so who has not reach the end, we should copy them
    for(; i < s1->num; i++) sum->e[k++] = s1->e[i];
    // doing the same thing for the second matrix
    for(; j < s2->num; j++) sum->e[k++] = s2->e[j];
    // at last for the sum matrix, we should mention the m, n and num
    sum->m = s1->m;
    sum->n = s1->n;
    sum->num = k; // k gives the number of non zero elements
    
    return sum;
   
    
}

int main() {
    // for s3, taken as a pointer, bcoz add function will add 2 matrices and returns the address of a sum matrix - 
    // that will be created in heap
    struct Sparse s1, s2, *s3;
    create(&s1);
    create(&s2);
    
    s3 = add(&s1, &s2);
    cout << "first matrix\n";
    display(s1);
    cout << "second matrix\n";
    display(s2);
    cout << "sum matrix\n";
    display(*s3);
    return 0;
}
