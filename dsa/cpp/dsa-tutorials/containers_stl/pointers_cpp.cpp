#include<iostream>
using namespace std;

int main() {
    // local variables goes to the stack of the memory
    // &x -> address of x
    int x = 1;
    cout << "address of x is: " << &x << endl;
    x = 10;
    cout << "address of x is: " << &x << endl;
    x = 100;
    cout << "address of x is: " << &x << endl;
    // pointer contains the address
    int *ptr = (int*)&x; // pointing to x, dereference using *
    cout << "my first pointer ptr is: " << ptr << endl;
    cout << "value pointed: " << *ptr << endl;
    cout << "=====================\n";
    *ptr = -100;
    cout << "pointer now points to: " << ptr << endl;
    cout << "value pointed by my pointer: " << *ptr << endl;
    cout << "value of variable x: " << x << endl;
    cout << "=====================\n";
    int *ptr2 = &x;
    char *name = "rachit";
    // pointer is storing the address on 64 bit machine, so 64 bits is 8 bytes, and o/p is 8
    // doesnt matter, it is pointing to what
    cout << "size of pointer to an integer: "<< sizeof(ptr2) << " bytes" << endl;
    cout << "size of pointer to character array: " << sizeof(name) << " bytes" << endl;
    cout << "=====================\n";
    // why do pointers have need a type - int*, char*, myclass*
    cout << "content of ptr: " << ptr << endl;
    cout << "content of ptr + 1: " << ptr + 1 << endl; // memory add + 4 bytes
    cout << "content of ptr + 2: " << ptr + 2 << endl;
    cout << "=====================\n";
    cout << "content of name: " << name << endl;
    cout << "content of name + 1: " << name + 1 << endl; // char takes 1 byte of space
    cout << "content of name + 2: " << name + 2 << endl;
    cout << "=====================\n";
    // arr becomes a pointer to the first element of the array
    int arr[3] = { 10, 31, 999 }; // here array acutally becomes a pointer
    cout << "content of arr: " << arr << ", " << *arr << endl;
    cout << "content of arr + 1: " << arr + 1 << ", " << *(arr + 1) << endl;
    cout << "content of arr + 2: " << arr + 2 << ", " << *(arr + 2) << endl;
    cout << "=====================\n";
    int *other = arr; // equality operation, creating the copy of the pointer O(1)
    cout << "content of other: " << other << ", " << *other << endl;
    cout << "content of other + 1: " << other + 1 << ", " << *(other + 1) << endl; 
    cout << "content of other + 2: " << other + 2 << ", " << *(other + 2) << endl;
    cout << "=====================\n";
    struct Real {
        int real, img;
        void out() { cout << real << " + " << img << "i" << endl; }
    };
    Real num = { 2, 3 };
    num.out();
    cout << "=====================\n";
    Real* p = &num; // p starts pointing to num var,which is object of our struct
    p->out();
    (*p).out();
    cout << p->real << endl;
    cout << p->img << endl;
    cout << "=====================\n";
    Real num2 = *p;
    num2.real = 13;
    num2.out();
    p->out();
    cout << "=====================\n";
    p = nullptr;
    cout <<p << endl;
    cout << "=====================\n";
    Real newNumber = *p; // will throw error
    // cout << newNumber << endl;
}