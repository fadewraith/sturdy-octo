#include<iostream>
using namespace std;

int main() {

    int arr[] = { 5, 8, 3, 9, 6, 2, 10, 7, -1, 4 };
    int k = 10;

    int length = sizeof(arr) / sizeof(arr[0]);

    for(int i = 0; i < length - 1; i++) {

        for(int j = i + 1; j < length; j++) {
            if(arr[i] + arr[j] == k) {
                cout << arr[i] << " + " << arr[j] << " = " << k << endl;
            }
        }

    }

    return 0;
}
