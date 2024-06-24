#include<iostream>
using namespace std;

class Array {
    //data members
    private:
        int *A;
        int size;
        int length;
        void swap(int *x, int *y);
    public:
        Array() {
            size = 10;
            length = 0;
            A = new int[size];
        }
        Array(int sz) {
            size = sz;
            length = 0;
            A = new int[size];
        }
        ~Array() {
            delete []A;
        }

        void Display();
        void Append(int x);
        void Insert(int index, int x);
        int Delete(int index);

        int LinearSearch(int key);
        int BinarySearch(int key);
        //int RecBinarySearch(int a[], int l, int h, int key);
        int Get(int index);
        void Set(int index, int x);
        int Max();
        int Min();
        int Sum();
        float Avg();
        void Reverse();
        void Reverse2();
        void InsertInSorted(int x);
        int isSorted();
        void NegativePositiveLeftRight();
        Array* Merge(Array arr2);
        Array* Union(Array arr2);
        Array* Intersection(Array arr2);
        Array* Difference(Array arr2);
};

void Array::Display() {
    int i;
    cout << "Elements are: ";
    for(i = 0; i < length; i++) {
        cout << A[i] << ", ";
    }
    cout << endl;
}

//should be call by address type as it is going to modify the address
void Array::Append(int x) {
    if(length < size) {
        A[length++] = x;
    }
}

//insertion
void Array::Insert(int index, int x) {
    if(index >= 0 && index <= length) {
        for(int i = length; i > index; i--) {
            A[i] = A[i - 1];
        }
        A[index] = x;
        length++;
    }
}

//deletion
int Array::Delete(int index) {
    int x = 0;
    if(index >= 0 && index < length) {
        x = A[index];
        for(int i = index; i < length - 1; i++) {
            A[i] = A[i+1];
        }
        length--;
        return x;
    }
}

//passsing by value, its enough
/*int Array::LinearSearch(int key) {
    for(int i = 0; i < arr.length; i++) {
        if(key == A[i]) {
            return i;
        }
    }
    return -1;
}*/

//this is the improved linear search
int Array::LinearSearch(int key) {
    for(int i = 0; i < length; i++) {
        if(key == A[i]) {
            //swap(&arr->A[i], &arr->A[i - 1]); // improved linear search by transposition
            //return i-1;
            swap(&A[i], &A[0]); // improved linear search by moving to front/end
            return 0;
        }
    }
    return -1;
}

void Array::swap(int *x, int *y) {
    int temp = *x;
    *x = *y;
    *y = temp;

}

int Array::BinarySearch(int key) {
    int l, mid, h;
    l = 0;
    h = length - 1;
    while(l <= h) {
        mid = (l + h) / 2;
        if(key == A[mid])
            return mid;
        else if(key < A[mid])
            h = mid - 1;
        else
            l = mid + 1;
    }
    return -1;
}

//int Array::RecBinarySearch(int l, int h, int key) {
//    int mid;
//    if(l <= h) {
//        mid = (l + h) / 2;
//        if(key == A[mid])
//            return mid;
//        else if(key < A[mid])
//            return RecBinarySearch(l, mid - 1, key);
//        else
//            return RecBinarySearch(mid + 1, h, key);
//    }
//    return -1;
//}

//it should be call by value, coz its not going to modify the array
int Array::Get(int index) {
    if(index >= 0 && index < length)
        return A[index];
    return -1;
}

void Array::Set(int index, int x) {
    if(index >= 0 && index < length)
        A[index] = x;
}


int Array::Max() {
    int max = A[0];
    for(int i = 1; i < length; i++) {
        if(A[i] > max) {
            max = A[i];
        }
    }
    return max;
}

int Array::Min() {
    int min = A[0];
    for(int i = 1; i < length; i++) {
        if(A[i] < min) {
            min = A[i];
        }
    }
    return min;
}

int Array::Sum() {
    int s = 0;
    for(int i = 0; i < length; i++) {
        s += A[i];
    }
    return s;
}

float Array::Avg() {
    return (float)Sum() / length;
}

//reverse an array
void Array::Reverse() {
    int *B = new int[length];
    int i, j;
    for(i = length - 1, j = 0; i >= 0; i--, j++) {
        B[j] = A[i];
    }
    for(i = 0; i < length; i++)
        A[i] = B[i];
}

void Array::Reverse2() {
    for(int i = 0, j = length - 1; i < j; i++, j--) {
        swap(&A[i], &A[j]);
    }
}

//inserting in the sorted array
void Array::InsertInSorted(int x){
    int i = length - 1;
    if(length == size)
        return;
    while(i >= 0 && A[i] > x) {
        A[i + 1] = A[i];
        i--;
    }
    A[i + 1] = x;
    length++;
}

//check if the array is sorted
int Array::isSorted() {
    for(int i = 0; i < length - 1; i++) {
        if(A[i] > A[i + 1])
            return 0;
    }
    return 1;
}

//function to move all positives at the right and all negatives to the left
void Array::NegativePositiveLeftRight() {
    int i = 0, j = length - 1;
    while(i < j) {
//        while(arr->A[i] > 0) // this will move all positive to the left side
        while(A[i] < 0)
            i++;
//        while(arr->A[j] <= 0) // this will move all positive to the right side
        while(A[j] >= 0)
            j--;
        if(i < j)
            swap(&A[i], &A[j]);
    }
}

//function to merge 2 sorted arrays
Array* Array::Merge(Array arr2) {
    int i = 0, j = 0, k = 0;
    Array *arr3 = new Array(length + arr2.length);
    while(i < length && j < arr2.length) {
        if(A[i] < arr2.A[j])
            arr3->A[k++] = A[i++];
        else
            arr3->A[k++] = arr2.A[j++];
    }
    for(; i < length; i++)
        arr3->A[k++] = A[i];
    for(; j < arr2.length; j++)
        arr3->A[k++] = arr2.A[j];
    arr3->length = length + arr2.length;

    return arr3;
}

//to find the union of 2 arrays - union for sorted array is same as merging
Array* Array::Union(Array arr2) {
    int i = 0, j = 0, k = 0;
    Array *arr3 = new Array(length + arr2.length);
    while(i < length && j < arr2.length) {
        if(A[i] < arr2.A[j])
            arr3->A[k++] = A[i++];
        else if(arr2.A[j] < A[i]) // if arr2 is less than arr1, then this step
            arr3->A[k++] = arr2.A[j++];
        else { // if both the elmenets are equal then copy from anyone and increment by 1
            arr3->A[k++] = A[i++];
            j++;
        }
    }
    for(; i < length; i++)
        arr3->A[k++] = A[i];
    for(; j < arr2.length; j++)
        arr3->A[k++] = arr2.A[j];
    arr3->length = k; // will be equal to length of 3 array, coz length cannot be same

    return arr3;
}

//intersection on sorted array
Array* Array::Intersection(Array arr2) {
    int i = 0, j = 0, k = 0;
    Array *arr3 = new Array(length + arr2.length);
    while(i < length && j < arr2.length) {
        if(A[i] < arr2.A[j])
            i++; // just move to the next element, dont copy it
        else if(arr2.A[j] < A[i]) // if arr2 is less than arr1, then this step
            j++;
        else if(A[i] == arr2.A[j]) { // if both the elmenets are equal then copy from anyone and increment by 1
            arr3->A[k++] = A[i++];
            j++;
        }
    }

    arr3->length = k; // will be equal to length of 3rd array, coz length cannot be same

    return arr3;
}

//difference of 2 arrays on sorted arrays
Array* Array::Difference(Array arr2) {
    int i = 0, j = 0, k = 0;
    Array *arr3 = new Array(length + arr2.length);
    while(i < length && j < arr2.length) {
        if(A[i] < arr2.A[j])
            arr3->A[k++] = A[i++];
        else if(arr2.A[j] < A[i]) // if arr2 is less than arr1, then this step
            j++; // dont copy the second array element, simply increment it
        else { // if both the elmenets are equal then dont copy, increment i and j
            i++; j++;
        }
    }
    for(; i < length; i++)
        arr3->A[k++] = A[i];
    arr3->length = k; // will be equal to length of 3 array, coz length cannot be same


    return arr3;
}



int main() {

    Array *arr1;
    int ch, sz;
    int x, index;

    cout << "Enter the size of an array: ";
    cin >> sz;
//    arr1.A = (int *)malloc(arr1.size * sizeof(int));
    arr1 = new Array(sz);
    do {
    cout << "\nMenu\n";
    cout << "1. Insert\n";
    cout << "2. Delete\n";
    cout << "3. Search\n";
    cout << "4. Sum\n";
    cout << "5. Display\n";
    cout << "6. Exit\n";
    cout << "Enter your choice: ";
    cin >> ch;

    switch(ch) {
        case 1: cout <<"Enter and element and index: ";
            cin >> x, index;
            arr1->Insert(index, x);
            break;
        case 2: cout <<"Enter index: ";
            cin >> index;
            x = arr1->Delete(index);
            cout << "Deleted element is: " << x;
            break;
        case 3: cout <<"Enter element to search: ";
            cin >> x;
            index = arr1->LinearSearch(x);
            break;
        case 4: cout << "Sum is: " << arr1->Sum();
            break;
        case 5: arr1->Display();
    }
    }while(ch < 6);
    return 0;
}
