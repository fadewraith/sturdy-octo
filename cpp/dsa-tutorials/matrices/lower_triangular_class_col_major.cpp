#include<iostream>
using namespace std;

// using col major formula
class LowerTriangularMatrix {
    private:
        int *A;
        int n;
        
    public:
        LowerTriangularMatrix() {
            n = 2;
            A = new int[2 * (2 + 1) / 2];
        }
        LowerTriangularMatrix(int n) {
            this->n = n;
            A = new int[n * (n + 1) / 2];
        }
        ~LowerTriangularMatrix() {
            delete []A;
        }
        
        void set(int i, int j, int x);
        int get(int i, int j);
        void display();
        int dimension() {
            return n;
        }
};

void LowerTriangularMatrix::set(int i, int j, int x) {
    if(i >= j) {
        A[n * (j - 1) - (j - 2) * (j - 1) / 2 + i - j] = x;
    }
    
}

int LowerTriangularMatrix::get(int i, int j) {
    if(i >= j) {
        return  A[n * (j - 1) - (j - 2) * (j - 1) / 2 + i - j];
    } else {
        return 0;
    }
}

void LowerTriangularMatrix::display() {
    for(int i = 1; i <= n; i++) {
        for(int j = 1; j <= n; j++) {
            if(i >= j) {
                cout << A[n * (j - 1) - (j - 2) * (j - 1) / 2 + i - j] << " ";
            } else {
                cout << "0 ";
            }
        }
        cout << endl;
    }
    
}

int main() {
    int d;
    cout << "enter dimensions: ";
    cin >> d;
    LowerTriangularMatrix ltm(d);
    int x;
    cout << "enter all elements\n";
    for(int i = 1; i <= d; i++) {
        for(int j = 1; j <= d; j++) {
            cin >> x;
            ltm.set(i, j, x);
        }
    }
    cout << endl;
    ltm.display();
    cout << ltm.dimension();
    
    return 0;
}
