#include<iostream>
using namespace std;
// time complexity - O(n), while loop is negligible

int main() {
    int num[] = { 3, 4, 5, 6, 6, 7, 8, 9, 9, 9, 10 }, j = 0;
    int length = sizeof(num) / sizeof(num[0]);
    for(int i = 0; i < length - 1; i++) {
        if(num[i] == num[i + 1]) {
            j = i; // or j = i + 1
            while(num[i] == num[j]) j++;
            cout << num[i] << " is appearing " << j - i << " times\n";
            i = j - 1;
        }
    }
    return 0;
}
