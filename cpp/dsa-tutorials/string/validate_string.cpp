#include<iostream>
using namespace std;

// function to validate a string
int validate(char *name) {

    for(int i = 0; name[i] != '\0'; i++) {
        if(!(name[i] >= 65 && name[i] <=90) && !(name[i] >= 97 && name[i] <=122) && !(name[i] >= 48 && name[i] <=57))
            return 0;
    }

    return 1;
}

int main() {

    char name[] = "Anil321!";
    validate(name) ? cout << name <<" is a valid name" : cout << "not a valid name";
    return 0;
}
