#include<iostream>
using namespace std;

double e(int x, int n) {
	// using recursion
	/* 
	static double s = 1;
	if(n == 0) return s;
	else {
		s = 1 + ((x * s) / n);
	}
	return e(x, n - 1);
	*/
	
	// using for loop
	/* 
	double s = 1;
	for(;n > 0; n--) {
		s = 1 + ((x * s) / n);
	}
	return s;
	*/
	
	double s = 1, num = 1, den = 1; int i;
	for(i = 1; i < n; i++) {
		num *= x;
		den *= i;
		s += num / den;
	}
	return s;
	
	
}

int main() {
	double res = e(4, 10);
	cout << "new => " << res;
	return 0;
}
