#include <iostream>
using namespace std;

double e(int x, int n) {
	static double p = 1, f = 1, r;
	if(n == 0) return 1;
	else {
		r = e(x, n - 1);
		p *= x;
		f *= n;
	}
	return r + (p/f);
}

int main() {
	double res = e(4, 10);
	cout << res;
	return 0;
}

