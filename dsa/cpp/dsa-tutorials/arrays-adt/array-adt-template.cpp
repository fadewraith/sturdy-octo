#include<iostream>
using namespace std;

template<class T>
class Array {

    private:
        T *A; int size; int length;

    public:
        Array() {
            size = 10;
            length = 0;
            A = new T[size];
        }
        Array(int sz) {
            size = sz;
            length = 0;
            A = new T[size];
        }
        ~Array() {
            delete []A;
        }
        void Display();
        void Append(T x);
        void Insert(int index, T x);
        T Delete(int index);
        T LinearSearch(T key);
        T BinarySearch(T key);
        T Get(int index);
        void Set(int index, T x);
        T Max();
        T Min();
        T Sum();
        T Avg();
        void Reverse();
        void Reverse2();
        void InsertSort(T x);
        int isSorted();
        void Rearrange();
//        Array* Merge(Array arr2);
//        Array* Union(Array arr2);
//        Array* Difference(Array arr2);
//        Array* Intersection(Array arr2);
};

template<class T>
void Array<T>::Display() {
    int i;
    cout << "\nElements are\n";
    for(i = 0; i < length; i++) {
        cout << A[i] << ", ";
    }
}


template<class T>
void Array<T>::Insert(int index, T x) {
    if(index >= 0 && index <= length) {
        for(int i = length; i > index; i--) {
            A[i] = A[i - 1];
        }
        A[index] = x;
        length++;
    }
}

template<class T>
T Array<T>::Delete(int index) {
    T x = 0, i;
    if(index >= 0 && index < length) {
        x = A[index];
        for(i = index; i < length - 1; i++) {
            A[i] = A[i + 1];
        }
        length--;
        return x;
    }
    return 0;
}


int main() {
    Array<char> arr(10);

    arr.Insert(0, 'a');
    arr.Insert(1, 'b');
    arr.Insert(2, 'c');
    arr.Display();

    return 0;
}
