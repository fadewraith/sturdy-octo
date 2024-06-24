#include<iostream>
using namespace std;

int main() {
    char str[] = "madam";
    int i, j, k;

    for(i = 0; str[i] != '0'; i++) { }
    k = i - 1;

    for(i = 0; str[i] != '\0' ; i++) {
        for(j = i + 1; str[j] != '\0'; j++) {
            if(str[i] == str[j]) {
                cout << str[i] << endl;
            }
        }
    }





    return 0;
}
