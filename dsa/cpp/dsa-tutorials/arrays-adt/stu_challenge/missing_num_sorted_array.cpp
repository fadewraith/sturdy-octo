#include<iostream>
using namespace std;
//time complexity = O(n)

int main() {
    int num[] = { 6, 7, 8, 10, 11, 12 };
    int length = sizeof(num) / sizeof(num[0]);
    int diff = num[0] - 0;
    for(int i = 0; i < length; i++) {
        if(num[i] - i != diff) {
            cout << diff + i;
            break;
        }
    }
    return 0;
}
