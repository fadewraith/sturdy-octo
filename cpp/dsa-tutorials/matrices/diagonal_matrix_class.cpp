#include<iostream>
using namespace std;

class DiagonalMatrix {

	private:
		int n;
		int *A;

	public:
		DiagonalMatrix() {
			int n = 2;
			A = new int[2];
		}


		DiagonalMatrix(int n);
		void set(int i, int j, int x);
		int get(int i, int j);
		void display(int n);
		~DiagonalMatrix();
};

DiagonalMatrix::DiagonalMatrix(int n) {
	this->n = n;
	A = new int[n];
}

void DiagonalMatrix::set(int i, int j, int x) {
	if(i == j)
		A[i - 1] = x;
}

int DiagonalMatrix::get(int i, int j) {
	if(i == j)
		return A[i - 1];
	else
		return 0;
}

void DiagonalMatrix::display(int n) {
	for(int i = 0; i < n; i++) {
		for(int j = 0; j < n; j++) {
			if(i == j)
				cout << A[i] << " ";
			else
				cout << "0 ";
		}
		cout << endl;
	}
}

DiagonalMatrix::~DiagonalMatrix() {
    delete []A;
    cout << "destroyed the matrix";
}

int main(){
	DiagonalMatrix d(3);
	d.set(1, 1, 9);
	d.set(2, 2, 7);
	d.set(3, 3, 5);
	cout << d.get(2, 2) << endl;
	d.display(3);
	return 0;
}
