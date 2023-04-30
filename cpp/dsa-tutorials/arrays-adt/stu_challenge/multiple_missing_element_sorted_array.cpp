#include<iostream>
using namespace std;
// time = O(n), while loop is negligible because its just filling gap
int main() {

    int num[] = { 6, 7, 8, 10, 11, 12, 15 };
    int length = sizeof(num) / sizeof(num[0]);
    int diff = num[0];

    for(int i = 0; i < length; i++) {
        if(num[i] - i != diff) {
            while(diff < num[i] - i) {
                cout << diff + i << ", ";
                diff++;
            }
        }
    }

    return 0;
}
