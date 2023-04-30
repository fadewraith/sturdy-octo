#include<iostream>
using namespace std;

int main() {

    int arr[] = { 5, 8, 3, 9, 6, 2, 10, 7, -1, 4 };

    int min = arr[0], max = arr[0];

    int length = sizeof(arr) / sizeof(arr[0]);

    for(int i = 1; i < length; i++) {

        if(arr[i] < min) min = arr[i];
        else if(arr[i] > max) max = arr[i];

    }

    cout << "min is: " << min << endl;
    cout << "max is: " << max << endl;

    return 0;
}
