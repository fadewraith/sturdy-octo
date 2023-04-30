#include<iostream>
using namespace std;
//time complexity = O(n)

int main() {
    int num[] = { 1, 2, 3, 4, 5, 7, 8, 9, 10 }, sum = 0;
    // cout << num[num.length() - 1];
    int length = sizeof(num) / sizeof(num[0]);
    sum = (num[length - 1] * (num[length - 1] + 1)) / 2;
    int missing_num = 0;
    for(int i = 0; i < length; i++) {
        sum -= num[i];
    }
    cout << sum;
    return 0;
}
