#include<iostream>
using namespace std;

int power(int x, int y) {
	if(y == 0) return 1;
	//return x * power(x, y - 1);
	if(y % 2 == 0)
		return power(x * x, (y / 2));
	//else if(y % 2 != 0)
	return x * power(x * x, (y - 1) / 2);
}

int main() {
	cout << power(2, 4);
	return 0;
}
