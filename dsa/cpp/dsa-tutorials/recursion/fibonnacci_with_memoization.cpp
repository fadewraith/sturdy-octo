#include<iostream>
using namespace std;

//using loop
int fib(int n) {
	int t0 = 0, t1 = 1, s = 0, i;
	if(n <= 1) return n;
	for(i = 2; i <= n; i++) {
		s = t0 + t1;
		t0 = t1;
		t1 = s;
	}
	return s;
}

//using recursion
int rfib(int n) {
	if(n <= 1) return n;
	return rfib(n - 2) + rfib(n - 1);
}

//using memoization
int F[10];
int mFib(int n) {
	if(n <= 1) {
		F[n] = n;
		return n;
	} else {
		if(F[n-2] == -1)
			F[n-2] = mFib(n - 2);
		if(F[n-1] == -1)
			F[n-1] = mFib(n - 1);
		return F[n-2] + F[n-1];
	}
}

int main() {
	//cout << fib(10);
	int i;
	for(i = 0; i < 10; i++) {
		F[i] = -1;
	}
	cout << "mfib => " << mFib(10);
	return 0;
}
