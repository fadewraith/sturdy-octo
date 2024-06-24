#include<iostream>
using namespace std;

int main() {

    char str[] = "How  are you?"; int word = 1; int v_count = 0; int c_count = 0;

    // count the num of vowels and consonants in a string
    for(int i = 0; str[i] != '\0'; i++) {
        if(str[i] == 'a' || str[i] == 'e' || str[i] == 'i' || str[i] == 'o' || str[i] == 'u' ) v_count++;
        else if((str[i] >= 65 && str[i] <= 90) || (str[i] >= 97 && str[i] <= 122)) c_count++;
    }

    cout << "No. of vowels is => " << v_count << " & no. of consonants is => " << c_count << endl;

    // count the number of words in a string, so if previous index is not a space, then only count the word, to ignore count for multiple extra space
    for(int i = 0; str[i] != '\0'; i++) {
        if(str[i] == ' ' && str[i - 1] != ' ') word++;
    }
    cout << "No. of words in string => " << str << " is => " << word;
}
