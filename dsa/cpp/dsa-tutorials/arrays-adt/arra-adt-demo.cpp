#include<iostream>
using namespace std;

struct Array {
    //int *A;
    int A[20];
    int size;
    int length;
};

void Display(struct Array arr) {
    int i;
    cout << "Elements are: ";
    for(i = 0; i < arr.length; i++) {
        cout << arr.A[i] << ", ";
    }
    cout << endl;
}

//should be call by address type as it is going to modify the address
void Append(struct Array *arr, int x) {
    if(arr->length < arr->size) {
        arr->A[arr->length++] = x;
    }
}

//insertion
void Insert(struct Array *arr, int index, int x) {
    if(index >= 0 && index <= arr->length) {
        for(int i = arr->length; i > index; i--) {
            arr->A[i] = arr->A[i - 1];
        }
        arr->A[index] = x;
        arr->length++;
    }
}

//deletion
int Delete(struct Array *arr, int index) {
    int x = 0;
    if(index >= 0 && index < arr->length) {
        x = arr->A[index];
        for(int i = index; i < arr->length - 1; i++) {
            arr->A[i] = arr->A[i+1];
        }
        arr->length--;
        return x;
    }
}

void swap(int *x, int *y) {
    int temp = *x;
    *x = *y;
    *y = temp;
}
//passsing by value, its enough
/*int LinearSearch(struct Array arr, int key) {
    for(int i = 0; i < arr.length; i++) {
        if(key == arr.A[i]) {
            return i;
        }
    }
    return -1;
}*/

//this is the improved linear search
int LinearSearch(struct Array *arr, int key) {
    for(int i = 0; i < arr->length; i++) {
        if(key == arr->A[i]) {
            //swap(&arr->A[i], &arr->A[i - 1]); // improved linear search by transposition
            //return i-1;
            swap(&arr->A[i], &arr->A[0]); // improved linear search by moving to front/end
            return 0;
        }
    }
    return -1;
}

int BinarySearch(struct Array arr, int key) {
    int l, mid, h;
    l = 0;
    h = arr.length - 1;
    while(l <= h) {
        mid = (l + h) / 2;
        if(key == arr.A[mid])
            return mid;
        else if(key < arr.A[mid])
            h = mid - 1;
        else
            l = mid + 1;
    }
    return -1;
}

int RecBinarySearch(int a[], int l, int h, int key) {
    int mid;
    if(l <= h) {
        mid = (l + h) / 2;
        if(key == a[mid])
            return mid;
        else if(key < a[mid])
            return RecBinarySearch(a, l, mid - 1, key);
        else
            return RecBinarySearch(a, mid + 1, h, key);
    }
    return -1;
}

//it should be call by value, coz its not going to modify the array
int Get(struct Array arr, int index) {
    if(index >= 0 && index < arr.length)
        return arr.A[index];
    return -1;
}

void Set(struct Array *arr, int index, int x) {
    if(index >= 0 && index < arr->length)
        arr->A[index] = x;
}


int Max(struct Array arr) {
    int max = arr.A[0];
    for(int i = 1; i < arr.length; i++) {
        if(arr.A[i] > max) {
            max = arr.A[i];
        }
    }
    return max;
}

int Min(struct Array arr) {
    int min = arr.A[0];
    for(int i = 1; i < arr.length; i++) {
        if(arr.A[i] < min) {
            min = arr.A[i];
        }
    }
    return min;
}

int Sum(struct Array arr) {
    int s = 0;
    for(int i = 0; i < arr.length; i++) {
        s += arr.A[i];
    }
    return s;
}

float Avg(struct Array arr) {
    return (float)Sum(arr) / arr.length;
}

//reverse an array
void Reverse(struct Array *arr) {
    int *B = new int[arr->length];
    int i, j;
    for(i = arr->length - 1, j = 0; i >= 0; i--, j++) {
        B[j] = arr->A[i];
    }
    for(i = 0; i < arr->length; i++)
        arr->A[i] = B[i];
}

void Reverse2(struct Array *arr) {
    for(int i = 0, j = arr->length - 1; i < j; i++, j--) {
        swap(&arr->A[i], &arr->A[j]);
    }
}

//inserting in the sorted array
void InsertInSorted(struct Array *arr, int x){
    int i = arr->length - 1;
    if(arr->length == arr->size)
        return;
    while(i >= 0 && arr->A[i] > x) {
        arr->A[i + 1] = arr->A[i];
        i--;
    }
    arr->A[i + 1] = x;
    arr->length++;
}

//check if the array is sorted
int isSorted(struct Array arr) {
    for(int i = 0; i < arr.length - 1; i++) {
        if(arr.A[i] > arr.A[i + 1])
            return 0;
    }
    return 1;
}

//function to move all positives at the right and all negatives to the left
void NegativePositiveLeftRight(struct Array *arr) {
    int i = 0, j = arr->length - 1;
    while(i < j) {
//        while(arr->A[i] > 0) // this will move all positive to the left side
        while(arr->A[i] < 0)
            i++;
//        while(arr->A[j] <= 0) // this will move all positive to the right side
        while(arr->A[j] >= 0)
            j--;
        if(i < j)
            swap(&arr->A[i], &arr->A[j]);
    }
}

//function to merge 2 sorted arrays
struct Array* Merge(struct Array *arr1, struct Array *arr2) {
    int i = 0, j = 0, k = 0;
    struct Array *arr3 = (struct Array *)malloc(sizeof(struct Array));
    while(i < arr1->length && j < arr2->length) {
        if(arr1->A[i] < arr2->A[j])
            arr3->A[k++] = arr1->A[i++];
        else
            arr3->A[k++] = arr2->A[j++];
    }
    for(; i < arr1->length; i++)
        arr3->A[k++] = arr1->A[i];
    for(; j < arr2->length; j++)
        arr3->A[k++] = arr2->A[j];
    arr3->length = arr1->length + arr2->length;
    arr3->size = 10;

    return arr3;
}

//to find the union of 2 arrays - union for sorted array is same as merging
struct Array* Union(struct Array *arr1, struct Array *arr2) {
    int i = 0, j = 0, k = 0;
    struct Array *arr3 = (struct Array *)malloc(sizeof(struct Array));
    while(i < arr1->length && j < arr2->length) {
        if(arr1->A[i] < arr2->A[j])
            arr3->A[k++] = arr1->A[i++];
        else if(arr2->A[j] < arr1->A[i]) // if arr2 is less than arr1, then this step
            arr3->A[k++] = arr2->A[j++];
        else { // if both the elmenets are equal then copy from anyone and increment by 1
            arr3->A[k++] = arr1->A[i++];
            j++;
        }
    }
    for(; i < arr1->length; i++)
        arr3->A[k++] = arr1->A[i];
    for(; j < arr2->length; j++)
        arr3->A[k++] = arr2->A[j];
    arr3->length = k; // will be equal to length of 3 array, coz length cannot be same
    arr3->size = 10;

    return arr3;
}

//intersection on sorted array
struct Array* Intersection(struct Array *arr1, struct Array *arr2) {
    int i = 0, j = 0, k = 0;
    struct Array *arr3 = (struct Array *)malloc(sizeof(struct Array));
    while(i < arr1->length && j < arr2->length) {
        if(arr1->A[i] < arr2->A[j])
            i++; // just move to the next element, dont copy it
        else if(arr2->A[j] < arr1->A[i]) // if arr2 is less than arr1, then this step
            j++;
        else if(arr1->A[i] == arr2->A[j]) { // if both the elmenets are equal then copy from anyone and increment by 1
            arr3->A[k++] = arr1->A[i++];
            j++;
        }
    }

    arr3->length = k; // will be equal to length of 3rd array, coz length cannot be same
    arr3->size = 10;

    return arr3;
}

//difference of 2 arrays on sorted arrays
struct Array* Difference(struct Array *arr1, struct Array *arr2) {
    int i = 0, j = 0, k = 0;
    struct Array *arr3 = (struct Array *)malloc(sizeof(struct Array));
    while(i < arr1->length && j < arr2->length) {
        if(arr1->A[i] < arr2->A[j])
            arr3->A[k++] = arr1->A[i++];
        else if(arr2->A[j] < arr1->A[i]) // if arr2 is less than arr1, then this step
            j++; // dont copy the second array element, simply increment it
        else { // if both the elmenets are equal then dont copy, increment i and j
            i++; j++;
        }
    }
    for(; i < arr1->length; i++)
        arr3->A[k++] = arr1->A[i];
    arr3->length = k; // will be equal to length of 3 array, coz length cannot be same
    arr3->size = 10;

    return arr3;
}

int main() {
    struct Array arr = { { 1, 2, 3, 4, 5 }, 10, 5 };
    struct Array arr1 = {{ -6, 3, -8, 10, 5, -7, -9, 12, -4, 2 }, 20, 10};
    struct Array arr2 = { { 1, 3, 4, 5, 6}, 10, 5 };
    struct Array arr3 = { { 2, 7, 8, 9, 10 }, 10, 5 };
    struct Array arr5 = { { 2, 5, 8, 9, 10 }, 10, 5 };
    struct Array *arr4 = Merge(&arr2, &arr3);
    cout << "Merge 2 arrays => ";
    Display(*arr4);
    struct Array *arr6 = Union(&arr2, &arr5);
    cout << "Union of 2 arrays on sorted => ";
    Display(*arr6);
    struct Array *arr7 = Intersection(&arr2, &arr5);
    cout << "Intersection of 2 arrays on sorted => ";
    Display(*arr7);
    struct Array *arr8 = Difference(&arr2, &arr5);
    cout << "Difference on sorted arrays => ";
    Display(*arr8);
    /*struct Array arr;
    int n, i;
    cout << "Enter the size of an array: ";
    cin >> arr.size;
    arr.A = new int[5];
    arr.length = 0;
    cout << "Enter number of numbers: ";
    cin >> n;

    cout << "Enter all numbers" << endl;
    for(i = 0; i < n; i++) {
        cin >> arr.A[i];
    }
    arr.length = n; */

    Display(arr);
    Append(&arr, 10);
    Display(arr);
    Insert(&arr, 2, 99);
    Display(arr);
    cout << "deleted array is: " << Delete(&arr, 2) << endl;
    Display(arr);
//    cout << LinearSearch(arr, 3) << endl;
    cout << "linear search: " << LinearSearch(&arr, 3) << endl;
    Display(arr);
    cout << "binary search: " << BinarySearch(arr, 5) << endl;
    cout << "recursive binary search is: " << RecBinarySearch(arr.A, 0, arr.length, 99) << endl;
    cout << "get the array: "<< Get(arr, 5) << endl;
    Set(&arr, 0, 17);
    Display(arr);
    cout << "min value: "<< Min(arr) << endl;
    cout << "max value: "<< Max(arr) << endl;
    cout << "sum of array: " << Sum(arr) << endl;
    cout << "avg of an array: " << Avg(arr) << endl;
    Reverse(&arr);
    Display(arr);
    Reverse2(&arr);
    Display(arr);
    InsertInSorted(&arr, 20);
    Display(arr);
    InsertInSorted(&arr, 0);
    Display(arr);
    cout << "is sorted: " << isSorted(arr) << endl;
    Display(arr1);
    NegativePositiveLeftRight(&arr1);
    Display(arr1);




    return 0;
}
