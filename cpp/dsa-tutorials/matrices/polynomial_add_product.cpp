#include<iostream>
#include<math.h>
using namespace std;

struct Term {
    int coeff, expo;
};

struct Polynomial {
    int n;
    struct Term *t;
};

void create(struct Polynomial *p) {
    cout << "enter the number of polynomial: ";
    cin >> p->n;
    // p->terms=(struct Term*)malloc(p->n*sizeof(struct Term));
    p->t = new Term[p->n];
    for(int i = 0; i < p->n; i++) {
        cout << "enter the coefficient and exponent: " << i + 1 << endl;
        cin >> p->t[i].coeff >> p->t[i].expo;
    }
}

int multiply(struct Polynomial *p, int x) {
    int sum = 0;
    for(int i = 0; i < p->n; i++) {
        sum += p->t[i].coeff * pow(x, p->t[i].expo);
    }
    return sum;
}

struct Polynomial * add(struct Polynomial *p1, struct Polynomial *p2) {
    int i = 0; int j = 0; int k = 0;
    struct Polynomial *sum;
    // sum=(struct Poly*)malloc(sizeof(struct Poly));
    // sum->terms=(struct Term *)malloc((p1->n+p2->n)*sizeof(struct Term));
    sum = new Polynomial[sizeof(struct Polynomial)];
    sum->t = new Term[p1->n + p2->n];
    while(i < p1->n && j < p2->n) {
        if(p1->t[i].expo > p2->t[j].expo)
            sum->t[k++] = p1->t[i++];
        else if(p2->t[j].expo > p1->t[i].expo)
            sum->t[k++] = p2->t[j++];
        else {
            sum->t[k].expo = p1->t[i].expo;
            sum->t[k++].coeff = p1->t[i++].coeff + p2->t[j++].coeff;
        }
    }
    for(; i < p1->n; i++) sum->t[k++] = p1->t[i++];
    for(; j < p2->n; j++) sum->t[k++] = p2->t[j++];
    sum->n = k;
    return sum;
}

void display(struct Polynomial p) {
    int i;
    for(i = 0; i < p.n; i++) {
        cout << p.t[i].coeff << "x" << p.t[i].expo << " + ";
    }
    cout << "\n";
}


int main() {
    int x = 4;
    struct Polynomial p1, p2, *p3;
    create(&p1);
    create(&p2);
    p3 = add(&p1, &p2);
    // cout << "product of a polynomial for x = " << x << " is " << multiply(&p1, x);
    cout << "\n";
    display(p1);
    cout << "\n";
    display(p2);
    cout << "\n";
    display(*p3);
    return 0;
}
