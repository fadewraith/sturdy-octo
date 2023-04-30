#include<iostream>
using namespace std;
//time complexity - O(n)

int main() {
    int num[] = { 3, 4, 5, 6, 6, 7, 8, 9, 9, 9, 10 };
    int length = sizeof(num) / sizeof(num[0]);
    int lastDuplicate = 0;
    for(int i = 0; i < length - 1; i++) {
        if(num[i] == num[i + 1] && lastDuplicate != num[i]) {
            cout << num[i] << " ";
            lastDuplicate  = num[i];
        }
    }
    return 0;
}
