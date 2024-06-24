#include<iostream>
using namespace std;
// time complexity = O(n * n)

int main() {
    int num[] = { 8, 3, 6, 4, 6, 5, 6, 8, 2, 7 };
    int length = sizeof(num) / sizeof(num[0]);
    for(int i = 0; i < length; i++) {
        int count = 1;
        if(num[i] != -1) {
            for(int j = i + 1; j < length; j++) {
                if(num[i] == num[j]) {
                    count++;
                    num[j] = -1;
                }
            }
        }
        if(count > 1) cout << num[i] << " is " << count << " times\n";
    }
    return 0;
}
