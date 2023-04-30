#include<iostream>
using namespace std;

int main() {

    char name[] = "python";
    char rev_name[10];
    int i, j;

    // for(i = 0; name[i] != '\0'; i++) { } // counting lenght of string, use for method 1
    for(j = 0; name[j] != '\0'; j++) { } // use for method 2

    i = 0;
    j = j - 1;

    // method 1

    // for(j = 0; i >= 0; i--, j++) { // reversing the string
    //     rev_name[j] = name[i];
    // }

    // rev_name[j] = '\0';

    // cout << rev_name;


    // method 2


    while(i < j) {
        char temp = name[i];
        name[i] = name[j];
        name[j] = temp;
        i++;
        j--;
    }

    cout << name;

    return 0;
}
