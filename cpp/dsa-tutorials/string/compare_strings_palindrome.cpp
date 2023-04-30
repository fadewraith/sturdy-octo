#include<iostream>
using namespace std;

int main() {
    char a[] = "painter";
    char b[] = "Painting";
    char str[] = "madam";
    int i, j;
    bool palindrome;

    // to compare whether 2 strings are qual or not
    for(i = 0, j = 0; a[i] !='\0' && b[i] != '\0'; i++, j++) {
        if(a[i] != a[j]) break;
        if(a[i] == b[j]) cout << "strings are equal" << endl;
        else if(a[i] < b[j]) cout << "strings are smaller" << endl;
        else cout << "strings are greater" << endl;
    }

    for(j = 0; str[j] != '\0'; j++) { }

    // to check whether given string is palindrome or not
    i = 0; j = j - 1;
    while(i < j) {
        if(str[i] != str[j]) {
            palindrome = false;
            break;
        } else {
            palindrome = true;
        }
        i++;
        j--;
    }

    palindrome ? cout << "given string is a palindrome" : cout << "strings are not palindrome";

    // find a duplicate in a string
    return 0;
}
