#include<iostream>
using namespace std;

struct Matrix {
    int *A;
    int n;
};

void set(struct Matrix *m, int i, int j, int x) {
    if(i >= j) {
        m->A[i * (i - 1) / 2 + j - 1] = x; // using row major formula to set the value for lwerlower triangle matrix
    }
}

int get(struct Matrix m,  int i, int j) {
    if(i >= j) {
        return m.A[i * (i - 1) / 2 + j - 1];
    } else
        return 0;
}

void display(struct Matrix m) {
    int i, j; // looping from index 1, so that we can properly use the formula, without changing the formula
    for(i = 1; i <= m.n; i++) {
        for(j = 1; j <= m.n; j++) {
            if(i >= j)
                cout << m.A[i * (i - 1) / 2 + j - 1] << " ";
            else
                cout  << "0 ";
        }
        cout << endl;
    }
}

int main() {
    struct Matrix m;
    cout << "\nenter dimension: ";
    cin >> m.n;
    m.A = new int[m.n * (m.n + 1) / 2];
    // m.A = (int *)malloc(m.n*(m.n + 1) / 2 * sizeof(int));
    cout << "\nenter all elements: ";
    int i, j, x;
    for(i = 1; i <= m.n; i++) {
        for(j = 1; j <= m.n; j++) {
            cin >> x;
            set(&m, i, j, x);
        }
    }
    cout << "\n\n";
    display(m);
    
    
    return 0;
}
