#include<iostream>
using namespace std;

// example of converting the class into template

template<class T>
class Arithematic {
    private:
//        int a;
//        int b;
        T a;
        T b;
    public:
        Arithematic(T a, T b);
        T add();
        T sub();
};
        template<class T>
        Arithematic<T>::Arithematic(T a, T b) { // scope resolution operator is used to use the functions outside the class
            this->a = a; // this is the pointer to the current object in the cpp
            this->b = b;
        }
        template<class T>
        T Arithematic<T>::add() {
            return a + b;
        }
        template<class T>
        T Arithematic<T>::sub() {
            T c;
            c = a - b;
            return c;
        }

int main() {

    // creating an object
//    Arithematic<int> ar(10, 5);
//    Arithematic<float> ar(10.56, 9.87);
    Arithematic<char> ar('A', 'B');
//    Arithematic<double> ar(10.356, 25.124);
    cout << "Add: " << (int)ar.add() << endl; // type caste has been used to demonstrate for character, for add if submission will go out of 127, it will be overflow and will give negatice result
    cout << "Sub: " << (int)ar.sub() << endl;


    return 0;

}
