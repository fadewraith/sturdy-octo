#include<iostream>
using namespace std;

int main() {
    // char name[] = { 'J', 'o', 'h', 'n', '\0' };
    char name[] = "John"; int i;
    // // code to count the length of a string
    // for(i = 0; name[i] != '\0'; i++) { }
    // cout << i;
    // code to change the case of a string
    /*
    logic is ASCII value of A = 65, Z = 90 & a = 97, z = 122 & the difference between A & a = 32(97 - 65), Z & z = 32(122 - 90)
    A => 65 + 32 = 97, we get 'a'
    */
    char str[] = "WeLcOmE";
    for(int i = 0; str[i] != '\0'; i++) {
        if(str[i] >= 'a' && str[i] <= 122) str[i] -= 32;
        else if(str[i] >= 65 && str[i] <= 90) str[i] += 32;
    }
    cout << str;
}
